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

import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.github.cameltooling.dap.reddeer.dialog.DebugConfigurationDialog;
import com.github.cameltooling.dap.reddeer.utils.ImportIntoWorkspace;

/**
 * TODO
 * 
 * @author fpospisi
 */
@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
public class CreatingConfigurationsTest {

	private static final String PROJECT_NAME = "creating-configurations-test";

	public static final String RESOURCES_BUILDER_PATH = "resources/my-route.xml";

	public static final String PROJECT_FOLDER_PATH = "resources/main-xml";

	public static final String MVN_CONF = "mvn_conf";
	public static final String CTD_CONF = "ctd_conf";
	public static final String GROUPED_CONF = "grouped_conf";

	@BeforeClass
	public static void setupTestEnvironment() {
		ImportIntoWorkspace.importFolder(PROJECT_FOLDER_PATH);
	}

	/*
	 * Maven
	 */
	@Test
	public void testCreatingMavenConfiguration() {
		DebugConfigurationDialog.createMaven(MVN_CONF, "${workspace_loc:/main-xml}");
	}

	/*
	 * Camel Textual Debug
	 */
	@Test
	public void testCreatingCTDConfiguration() {
		DebugConfigurationDialog.createCTD(CTD_CONF);
	}

	/*
	 * Grouped
	 */
	@Test
	public void testCreatingGLConfigurations() {
		DebugConfigurationDialog.createLaunchGroup(GROUPED_CONF, CTD_CONF, MVN_CONF);
	}
}
