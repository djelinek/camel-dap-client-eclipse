/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cameltooling.dap.ui.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasText;
import org.eclipse.reddeer.eclipse.core.resources.ProjectItem;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.cameltooling.dap.reddeer.dialog.DebugConfigurationDialog;
import com.github.cameltooling.dap.reddeer.dialog.DefaultEditorDialog;
import com.github.cameltooling.dap.reddeer.dialog.RunConfigurationDialog;
import com.github.cameltooling.dap.reddeer.editor.GenericEditor;
import com.github.cameltooling.dap.reddeer.utils.ImportIntoWorkspace;
import com.github.cameltooling.dap.reddeer.views.DebugView;




/**
 * TODO
 * @author fpospisi
 */
@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
public class DebuggingCamelJavaDSLTest {
	
	
	private static final String GTE = "Generic Text Editor";
	
	private static final String MVN_BUILD = "Maven Build";
	private static final String CAMEL_TEXT_DEBUG = "Camel Textual Debug";
	
	public static final String MVN_CONF = "mvn_conf";
	public static final String CTD_CONF = "ctd_conf";
	
	public static final String PROJECT_FOLDER_PATH = "resources/camel-examples/main";


	/**
	 * Sets Generic Editor as default editor for java. Imports project and creates
	 * run/debug configurations.
	 */
	@BeforeClass
	public static void setupTestEnvironment() {
		DefaultEditorDialog.setDefault("*.java", GTE);
	
		ImportIntoWorkspace.importFolder(PROJECT_FOLDER_PATH);
	
		RunConfigurationDialog.createMaven(MVN_CONF, "${workspace_loc:/main}");
		DebugConfigurationDialog.createCTD(CTD_CONF);
	}

	@Test
	public void JavaTest() {
		ProjectExplorer explorer = new ProjectExplorer();
		explorer.open();
		ProjectItem routeFile = explorer.getProject("main").getProjectItem("src", "main", "java", "org",
				"apache", "camel", "example", "MyRouteBuilder.java");
		routeFile.open();
		
		RunConfigurationDialog.run(MVN_BUILD, MVN_CONF);
		new WaitUntil(new ConsoleHasText("Hello how are you?"), TimePeriod.LONG);
		
		// Run CTD, wait until attached.
		DebugConfigurationDialog.debug(CAMEL_TEXT_DEBUG, CTD_CONF);
		new WaitUntil(new ConsoleHasText("\"command\":\"attach\",\"success\":true}"), TimePeriod.LONG);

		
		
		// Set breakpoint.
		GenericEditor editor = new GenericEditor();
		editor.activate();
		editor.setPosition(editor.getPosition(".log(\"${body}\")"));
		editor.setBreakpoint();
		
		// Check Message Body.
		assertEquals("Hello how are you?", DebugView.getMessageBodyFromVariable());

		// Step over two times.
		for (int i = 0; i < 2; i++) {
			AbstractWait.sleep(TimePeriod.getCustom(3));
			editor.stepOver();
		}

		// Add new breakpoint.
		editor.activate();
		editor.setBreakpoint();

		// Is Message Body changed?
		AbstractWait.sleep(TimePeriod.getCustom(3));
		
		if(DebugView.getMessageBodyFromVariable().equals("Bye World")) {
			new DebugView().restore();
		}
		
		assertEquals("Bye World", DebugView.getMessageBodyFromVariable());
	
	}	
}