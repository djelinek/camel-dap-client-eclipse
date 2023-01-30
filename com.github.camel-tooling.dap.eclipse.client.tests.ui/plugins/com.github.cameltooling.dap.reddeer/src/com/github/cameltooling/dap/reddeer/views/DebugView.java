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
package com.github.cameltooling.dap.reddeer.views;

import java.util.List;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.debug.ui.views.breakpoints.Breakpoint;
import org.eclipse.reddeer.eclipse.debug.ui.views.breakpoints.BreakpointsView;
import org.eclipse.reddeer.eclipse.debug.ui.views.variables.VariablesView;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Debug View
 * 
 * @author fpospisi
 *
 */
public class DebugView extends WorkbenchView {

	/**
	 * Instantiates a new debug view.
	 */
	public DebugView() {
		super("Debug");
	}
		
	/**
	 * Selects configuration from available configurations in Debug view. 
	 * 
	 * @param configurationName Name of configuration.
	 * @param configurationType Type of configuration.
	 */
	public void selectConfiguration(String configurationName, String configurationType) {
		activate();
		List<TreeItem> selectTreeItems = new DefaultTree().getAllItems();
		for (TreeItem item : selectTreeItems) {
			if(item.getText().equals(configurationName + " [" + configurationType + "]")) {
				item.select();
				break;
			}
		}
	}
	
	/**
	 * Selects configuration from running configurations. 
	 * 
	 * @param configurationName Name of configuration.
	 * @param configurationType Type of configuration.
	 */
	public void terminateConfiguration(String configurationName, String configurationType) {
		selectConfiguration(configurationName, configurationType);	
		new ShellMenuItem(new WorkbenchShell(), "Run", "Terminate").select();	
	}
	
	
	/**
	 * Selects configuration from running configurations. 
	 * 
	 * @param configurationName Name of configuration.
	 * @param configurationType Type of configuration.
	 */
	public void disconnectConfiguration(String configurationName, String configurationType) {
		selectConfiguration(configurationName, configurationType);	
		new ShellMenuItem(new WorkbenchShell(), "Run", "Disconnect").select();
	}
	
	/**
	 * Cancels "Launching configuration" job. 
	 * 
	 * @param configurationName Name of configuration.
	 */
	public void cancelLaunchingJob(String configurationName) {
		Job[] currentJobs = Job.getJobManager().find(null);
		for (Job job: currentJobs) {
			if(job.getName().matches("Launching " + configurationName)) {
				job.cancel();
			}		
		}
	}
	
	/**
	 * TODO
	 * @return
	 */
	public static String getMessageBodyFromVariable() {
		//activate();
		VariablesView variables = new VariablesView();
		AbstractWait.sleep(TimePeriod.getCustom(5));
		variables.open();
		
		TreeItem msg = null;
		String message = null;

		for (TreeItem item : new DefaultTree().getItems()) {
			if (!item.getText().equals("Message")) {
				continue;
			}
			msg = item;
		}

		if (msg != null) {
			for (TreeItem item : msg.getItems()) {
				item.select();
				if (item.getText().equals("Body")) {
					item.select();
					message = item.getCell(1);
					break;
				}
			}
		}

		return message;
	}
	
	public void disableAllbreakpoints() {
		
		BreakpointsView bpv = new BreakpointsView();
		bpv.activate();
			
		List<TreeItem> items = new DefaultTree().getItems();
		for (TreeItem item : items) {
			bpv.getBreakpoint(item.getText()).disable();
		}
	}
	
	
	public void reopen() {
		close();
		open();
	}
}
