package com.github.cameltooling.dap.ui.tests;

import org.eclipse.reddeer.common.wait.AbstractWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.eclipse.ui.perspectives.DebugPerspective;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.eclipse.reddeer.workbench.impl.editor.DefaultEditor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.github.cameltooling.dap.reddeer.dialog.DebugConfigurationDialog;
import com.github.cameltooling.dap.reddeer.utils.CreateNewEmptyFile;
import com.github.cameltooling.dap.reddeer.utils.JavaProjectFactory;
import com.github.cameltooling.dap.ui.tests.utils.EditorManipulator;

/**
 * 
 * @author fpospisi
 */
@OpenPerspective(DebugPerspective.class)
@RunWith(RedDeerSuite.class)
public class OpenPlatform {

	private static final String PROJECT_NAME = "attaching-dap";
	private static final String CAMEL_CONTEXT = "camel-context.xml";

	public static final String RESOURCES_CONTEXT_PATH = "resources/camel-context-cbr.xml";
 
	/*
	 * Prepares test environment. Creates Java project, XML camel context and
	 * default Apache Camel Textual Debug configuration.
	 */
	@BeforeClass
	public static void setupTestEnvironment() {
		//create debug configuration
		DebugConfigurationDialog.createCTD("debug_conf");
		
		//create project with camel context
		JavaProjectFactory.create(PROJECT_NAME);
		new ProjectExplorer().selectProjects(PROJECT_NAME);
		
		
	}
	
	/*
	 * Tests if Apache Camel Textual Debug is started properly. 
	 */
	@Test
	public void testAttachingDebugger() {
		AbstractWait.sleep(TimePeriod.getCustom(1000000));
	}
}
