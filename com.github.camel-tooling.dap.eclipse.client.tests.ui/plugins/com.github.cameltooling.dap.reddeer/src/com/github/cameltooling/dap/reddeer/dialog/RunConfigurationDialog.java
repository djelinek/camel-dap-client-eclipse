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

import java.util.List;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.LabeledText;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolBar;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;

/**
 * TODO
 * @author fpospisi
 */
public class RunConfigurationDialog {

	private static final String RUN_CONF = "Run Configurations";
	
	private static final String MVN_RUN = "Maven Build";

	/**
	 * TODO
	 * @param name
	 * @param baseDirectory
	 */
	public static void createMaven(String name, String baseDirectory) {
		openRunConfigurations();
		createNewConfiguration(MVN_RUN);

		// set values
		Shell shell = new DefaultShell(RUN_CONF);
		new LabeledText(shell, "Name:").setText(name);
		new LabeledText(shell, "Base directory:").setText(baseDirectory);
		new LabeledText(shell, "Goals:").setText("compile camel:run");
		new LabeledText(shell, "Profiles:").setText("camel.debug");
		new CheckBox(shell, "Skip Tests").click();

		saveChangesAndExit();
	}
	
	/**
	 * TODO
	 * @param configurationType
	 */
	private static void createNewConfiguration(String configurationType) {
		new DefaultTree(new DefaultShell(RUN_CONF)).getItem(configurationType).doubleClick();
	}

	/**
	 * TODO
	 * @param configurationType
	 * @param runConfiguration
	 */
	public static void run(String configurationType, String runConfiguration) {
		openRunConfigurations();

		new DefaultTree(new DefaultShell(RUN_CONF)).getItem(configurationType, runConfiguration).select();
		new PushButton("Run").click();
	}
	
	/**
	 * TODO
	 */
	public static void removeAllConfigurations() {
		openRunConfigurations();

		List<TreeItem> items = new DefaultTree().getAllItems();
		for (TreeItem item : items) {		
			item.select();
			
			DefaultToolBar toolbar = new DefaultToolBar(0);
			if (new DefaultToolItem(toolbar, 4).isEnabled()) { // checks if it's created configuration
				deleteSelectedConfiguration();
			}
		}
		
		new PushButton("Close").click();
	}
	
	/**
	 * TODO
	 */
	private static void deleteSelectedConfiguration() {
		DefaultToolBar toolbar = new DefaultToolBar(0);
		new DefaultToolItem(toolbar, 4).click();
		new PushButton("Delete").click();
	}
	
	/**
	 * TODO
	 */
	private static void openRunConfigurations() {
		new ShellMenuItem(new WorkbenchShell(), "Run", "Run Configurations...").select();
		new WaitUntil(new ShellIsAvailable(RUN_CONF), TimePeriod.DEFAULT);
	}
	
	/**
	 * TODO
	 */
	private static void saveChangesAndExit() {
		new PushButton("Apply").click();
		new PushButton("Close").click();
	}
}
