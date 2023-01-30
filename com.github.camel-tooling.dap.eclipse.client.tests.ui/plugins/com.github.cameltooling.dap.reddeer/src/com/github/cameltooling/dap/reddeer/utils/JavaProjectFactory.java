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
package com.github.cameltooling.dap.reddeer.utils;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.jdt.ui.wizards.JavaProjectWizard;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.wst.jsdt.ui.wizards.JavaProjectWizardFirstPage;
import org.eclipse.reddeer.workbench.handler.EditorHandler;

/**
 * Creates new Java project.
 *
 * @author fpospisi
 */
public class JavaProjectFactory {

	/**
	 * Creates new Java project.
	 *
	 * @param name Name of project.
	 */
	public static void create(String name) {
		JavaProjectWizard javaWiz = new JavaProjectWizard();
		javaWiz.open();
		JavaProjectWizardFirstPage javaWizPage = new JavaProjectWizardFirstPage(javaWiz);
		javaWizPage.setName(name);
		javaWiz.finish(TimePeriod.DEFAULT);
	}

	/**
	 * Removes all projects.
	 */
	public static void deleteAllProjects() {
		EditorHandler.getInstance().closeAll(true);
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		pe.deleteAllProjects(false);
		pe.activate();
	}

}
