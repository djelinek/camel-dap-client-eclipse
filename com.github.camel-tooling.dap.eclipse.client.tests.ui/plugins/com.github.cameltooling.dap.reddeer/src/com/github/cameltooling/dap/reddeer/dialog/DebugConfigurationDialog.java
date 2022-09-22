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
package com.github.cameltooling.dap.reddeer.dialog;

import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.swt.widgets.List;

/**
 * 
 * @author fpospisi
 */
public class DebugConfigurationDialog {

	private static final String DEBUG_CONF = "Debug Configurations";
	
	private static final String CAMEL_TEXT_DEBUG = "Camel Textual Debug";
	private static final String MVN_DEBUG = "Maven Build";
	private static final String GROUPED_DEBUG = "Launch Group";
	
	private static final String DEBUG_BTN = "Debug";

	/**
	 * TODO
	 * 
	 * @param name
	 */
	public static void createCTD(String name) {
		// open debug configurations
		new ShellMenuItem(new WorkbenchShell(), "Run", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(CAMEL_TEXT_DEBUG).doubleClick();
		
		// set values
		new LabeledText(shell, "Name:").setText(name);
		
		// done
		new PushButton("Apply").click();
		new PushButton("Close").click();
	}

	/**
	 * TODO
	 * 
	 * @param name
	 * @param baseDirectory
	 */
	public static void createMaven(String name, String baseDirectory) {
		// open debug configurations
		new ShellMenuItem(new WorkbenchShell(), "Run", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(MVN_DEBUG).doubleClick();
		
		// set values  
		new LabeledText(shell, "Name:").setText(name);
		new LabeledText(shell, "Base directory:").setText(baseDirectory);
		new LabeledText(shell, "Goals:").setText("camel:run");
		new LabeledText(shell, "Profiles:").setText("camel:debug");
		new CheckBox(shell, "Skip Tests").click();
		
		//AbstractWait.sleep(TimePeriod.getCustom(1000));
		
		// done
		new PushButton("Apply").click();
		new PushButton("Close").click();	
	}
	
	
	/**
	 * You, sir, are a troublemaker. 
	 * 
	 * @param name
	 * @param conf1
	 * @param conf2
	 */
	public static void createLaunchGroup(String name, String conf1, String conf2) {

		new ShellMenuItem(new WorkbenchShell(), "Run", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(GROUPED_DEBUG).doubleClick();
		
		new LabeledText(shell, "Name:").setText(name);

		new PushButton(shell, "Add...").click();
		
		//AbstractWait.sleep(TimePeriod.getCustom(1000));
		
		new WaitUntil(new ShellIsAvailable("Add Launch Configuration"), TimePeriod.DEFAULT);
		//Shell activeShell = new DefaultShell("Add Launch Configuration");
		
		//shell = new DefaultShell("Add Launch Configuration");

		
		java.util.List<TreeItem> items = new DefaultTree().getAllItems();
		for (TreeItem item : items) {
			
			if (item.getText().equals(conf1)) {
				item.doubleClick();
				//AbstractWait.sleep(TimePeriod.getCustom(1000));
				new OkButton().click();
				//new WaitWhile(new ShellIsAvailable("Add Launch Configuration"), TimePeriod.DEFAULT);
				//AbstractWait.sleep(TimePeriod.getCustom(1000));
			}
		}
		
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		new PushButton(shell, "Close").click();
	}

	/**
	 * Run debugger.
	 *
	 * @param debugConfiguration used for debug
	 */
	public static void debug(String debugConfiguration) {
		new ShellMenuItem(new WorkbenchShell(), "Run", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(CAMEL_TEXT_DEBUG, debugConfiguration).select();
		new PushButton(DEBUG_BTN).click();
	}

	/**
	 * Run debugger for specified file in specified project.
	 *
	 * @param project where required file is located
	 * @param file to debug
	 * @param debugConfiguration used for debug
	 */
	public static void debugFile(String project, String file, String debugConfiguration) {
		DefaultProject proj = new ProjectExplorer().getProject(project);
		proj.getProjectItem(file).select();
		new ContextMenuItem("Debug As", "Debug Configurations...").select();
		new WaitUntil(new ShellIsAvailable(DEBUG_CONF), TimePeriod.DEFAULT);
		Shell shell = new DefaultShell(DEBUG_CONF);
		new DefaultTree(shell).getItem(CAMEL_TEXT_DEBUG, debugConfiguration).select();
		new PushButton(DEBUG_BTN).click();
	}
}
