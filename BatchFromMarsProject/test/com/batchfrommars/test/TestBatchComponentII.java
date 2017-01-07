package com.batchfrommars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.test.component.StepTestBatchComponent;
import com.batchfrommars.test.component.TestBatchComponentII1;
import com.batchfrommars.test.component.TestBatchComponentII2;
import com.batchfrommars.test.component.TestBatchComponentII3;

public class TestBatchComponentII {

	@Test
	public void testStepBatchComponent() {
		StepTestBatchComponent component = new StepTestBatchComponent();
		FileInformation testInput = new TemporaryFile();
		FileInformation testOuputDev = new TemporaryFile();
		FileInformation testOuputUat = new TemporaryFile();
		FileInformation testOuputPrd = new TemporaryFile();

		testInput.writeFile("prd.xxxxxxxxx1");
		testInput.writeFile("uat.xxxxxxxxx2");
		testInput.writeFile("dev.xxxxxxxxx3");
		testInput.writeFile("prd.xxxxxxxxx4");
		testInput.writeFile("prd.xxxxxxxxx5");
		testInput.writeFile("dev.xxxxxxxxx6");
		testInput.writeFile("prd.xxxxxxxxx7");
		testInput.writeFile("prd.xxxxxxxxx8");
		testInput.writeFile("dev.xxxxxxxxx9");

		component.addInputFileInformation(testInput);
		component.addOutputFileInformation(testOuputDev, testOuputUat, testOuputPrd);

		component.activate();

		assertEquals("dev amount should be 3", 3, component.getDevCount());
		assertEquals("uat amount should be 1", 1, component.getUatCount());
		assertEquals("prd amount should be 5", 5, component.getPrdCount());

		assertFalse("testOuputDev shouldn't be empty ", testOuputDev.isEmpty());
		assertEquals("dev data should be th same", "dev.xxxxxxxxx3", testOuputDev.readFile());
		assertEquals("dev data should be th same", "dev.xxxxxxxxx6", testOuputDev.readFile());
		assertEquals("dev data should be th same", "dev.xxxxxxxxx9", testOuputDev.readFile());
		assertTrue("testOuputDev should be empty after readfile 3 times", testOuputDev.isEmpty());

		assertFalse("testOuputDev shouldn't be empty ", testOuputUat.isEmpty());
		assertEquals("dev data should be th same", "uat.xxxxxxxxx2", testOuputUat.readFile());
		assertTrue("testOuputDev should be empty after readfile 3 times", testOuputUat.isEmpty());
		assertNull("result of reading empty temp file should be null", testOuputUat.readFile());

		assertFalse("testOuputDev shouldn't be empty ", testOuputPrd.isEmpty());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx1", testOuputPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx4", testOuputPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx5", testOuputPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx7", testOuputPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx8", testOuputPrd.readFile());
		assertTrue("testOuputDev should be empty after readfile 3 times", testOuputPrd.isEmpty());
		assertNull("result of reading empty temp file should be null", testOuputPrd.readFile());

	}

	@Test
	public void testMultipleBatchComponent() throws InterruptedException {

		StepTestBatchComponent component = new StepTestBatchComponent();
		TestBatchComponentII1 componentDev = new TestBatchComponentII1();
		TestBatchComponentII2 componentUat = new TestBatchComponentII2();
		TestBatchComponentII3 componentPrd = new TestBatchComponentII3();

		FileInformation testInput = new TemporaryFile();
		FileInformation testOuputDev = new TemporaryFile(1);
		FileInformation testOuputUat = new TemporaryFile(1);
		FileInformation testOuputPrd = new TemporaryFile(1);

		FileInformation testDev = new TemporaryFile();
		FileInformation testUat = new TemporaryFile();
		FileInformation testPrd = new TemporaryFile();

		testInput.writeFile("prd.xxxxxxxxx1");
		testInput.writeFile("uat.xxxxxxxxx2");
		testInput.writeFile("dev.xxxxxxxxx3");
		testInput.writeFile("prd.xxxxxxxxx4");
		testInput.writeFile("prd.xxxxxxxxx5");
		testInput.writeFile("dev.xxxxxxxxx6");
		testInput.writeFile("prd.xxxxxxxxx7");
		testInput.writeFile("prd.xxxxxxxxx8");
		testInput.writeFile("dev.xxxxxxxxx9");

		component.addInputFileInformation(testInput);
		component.addOutputFileInformation(testOuputDev, testOuputUat, testOuputPrd);

		componentDev.addInputFileInformation(testOuputDev);
		componentDev.addOutputFileInformation(testDev);
		componentDev.addLastComponent(component);

		componentUat.addInputFileInformation(testOuputUat);
		componentUat.addOutputFileInformation(testUat);
		componentUat.addLastComponent(component);

		componentPrd.addInputFileInformation(testOuputPrd);
		componentPrd.addOutputFileInformation(testPrd);
		componentPrd.addLastComponent(component);

		component.start();
		componentDev.start();
		componentUat.start();
		componentPrd.start();

		component.join();
		componentDev.join();
		componentUat.join();
		componentPrd.join();

		assertEquals("dev should be 3", 3, componentDev.getCount());
		assertEquals("uat should be 1", 1, componentUat.getCount());
		assertEquals("dev should be 5", 5, componentPrd.getCount());

		assertFalse("testDev shouldn't be empty ", testDev.isEmpty());
		assertEquals("dev data should be th same", "dev.xxxxxxxxx3", testDev.readFile());
		assertEquals("dev data should be th same", "dev.xxxxxxxxx6", testDev.readFile());
		assertEquals("dev data should be th same", "dev.xxxxxxxxx9", testDev.readFile());
		assertTrue("testDev should be empty after readfile 3 times", testDev.isEmpty());
		
		assertFalse("testUat shouldn't be empty ", testUat.isEmpty());
		assertEquals("dev data should be th same", "uat.xxxxxxxxx2", testUat.readFile());
		assertTrue("testUat should be empty after readfile 3 times", testUat.isEmpty());
		assertNull("result of reading empty temp file should be null", testUat.readFile());
		
		assertFalse("testPrd shouldn't be empty ", testPrd.isEmpty());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx1", testPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx4", testPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx5", testPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx7", testPrd.readFile());
		assertEquals("dev data should be th same", "prd.xxxxxxxxx8", testPrd.readFile());
		assertTrue("testPrd should be empty after readfile 3 times", testPrd.isEmpty());
		assertNull("result of reading empty temp file should be null", testPrd.readFile());
	}

}
