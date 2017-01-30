package com.batchfrommars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.test.component.StepCompare;
import com.batchfrommars.test.component.TestBatchComponentII1;
import com.batchfrommars.test.component.TestBatchComponentII2;
import com.batchfrommars.test.component.TwoWayBatchComponenet;

public class CompareComponentTest {

	@Test
	public void testSingleCompareComponent() throws Exception {
		StepCompare compare = new StepCompare();
		FileInformation testInput1 = new TemporaryFile();
		FileInformation testInput2 = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();

		// setting input1 data
		testInput1.writeFile("0000xxxx01");
		testInput1.writeFile("0011xxxx02");
		testInput1.writeFile("0011xxxx02");
		testInput1.writeFile("0041xxxx02");
		testInput1.writeFile("0099xxxx02");
		testInput1.writeFile("0211xxxx02");

		// setting input2 data
		testInput2.writeFile("0000xxxx01");
		testInput2.writeFile("0000xxxx01");
		testInput2.writeFile("0011xxxx01");
		testInput2.writeFile("0021xxxx01");
		testInput2.writeFile("0031xxxx01");
		testInput2.writeFile("0041xxxx01");
		testInput2.writeFile("0051xxxx01");
		testInput2.writeFile("0211xxxx01");

		compare.addInputFileInformation(testInput1, testInput2);
		compare.addOutputFileInformation(testOutput);

		compare.activate();

		assertFalse("testOutput should not be empty", testOutput.isEmpty());
		assertEquals("0000xxxx01,0000xxxx01", testOutput.readFile());
		assertEquals("0011xxxx02,0011xxxx01", testOutput.readFile());
		assertEquals("0041xxxx02,0041xxxx01", testOutput.readFile());
		assertEquals("0211xxxx02,0211xxxx01", testOutput.readFile());
		assertTrue("testoUTPUT SHOULD BE EMPTY", testOutput.isEmpty());

	}

	@Test
	public void testTwoWayCompareComponent() throws Exception {
		StepCompare compare = new StepCompare();
		TwoWayBatchComponenet batchComponenet = new TwoWayBatchComponenet();

		FileInformation testInput1 = new TemporaryFile();
		FileInformation testInput2 = new TemporaryFile();
		FileInformation testOutput = new TemporaryFile();
		// temp
		FileInformation testInputA = new TemporaryFile();
		FileInformation testInputB = new TemporaryFile();
		// setting input1 data
		testInput1.writeFile("0000xxxx01");
		testInput1.writeFile("0011xxxx02");
		testInput1.writeFile("0011xxxx02");
		testInput1.writeFile("0041xxxx02");
		testInput1.writeFile("0099xxxx02");
		testInput1.writeFile("0211xxxx02");

		// setting input2 data
		testInput2.writeFile("0000xxxx01");
		testInput2.writeFile("0000xxxx01");
		testInput2.writeFile("0011xxxx01");
		testInput2.writeFile("0021xxxx01");
		testInput2.writeFile("0031xxxx01");
		testInput2.writeFile("0041xxxx01");
		testInput2.writeFile("0051xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");

		batchComponenet.addInputFileInformation(testInput1, testInput2);
		batchComponenet.addOutputFileInformation(testInputA, testInputB);

		compare.addInputFileInformation(testInputA, testInputB);
		compare.addOutputFileInformation(testOutput);
		compare.addLastComponent(batchComponenet);

		batchComponenet.start();
		compare.start();

		batchComponenet.join();
		compare.join();

		assertFalse("testOutput should not be empty", testOutput.isEmpty());
		assertEquals("0000xxxx01,0000xxxx01", testOutput.readFile());
		assertEquals("0011xxxx02,0011xxxx01", testOutput.readFile());
		assertEquals("0041xxxx02,0041xxxx01", testOutput.readFile());
		assertEquals("0211xxxx02,0211xxxx01", testOutput.readFile());
		assertTrue("testoUTPUT SHOULD BE EMPTY", testOutput.isEmpty());

	}

	@Test
	public void testThreeWayCompareComponenet() throws Exception {
		StepCompare compare = new StepCompare();
		TestBatchComponentII1 componentII1 = new TestBatchComponentII1();
		TestBatchComponentII2 componentII2 = new TestBatchComponentII2();

		FileInformation testInput1 = new TemporaryFile();
		FileInformation testInput2 = new TemporaryFile();
		FileInformation testInputA = new TemporaryFile(1);
		FileInformation testInputB = new TemporaryFile(1);
		FileInformation testOutput = new TemporaryFile();

		// setting input1 data
		testInput1.writeFile("0000xxxx01");
		testInput1.writeFile("0011xxxx02");
		testInput1.writeFile("0011xxxx02");
		testInput1.writeFile("0041xxxx02");
		testInput1.writeFile("0099xxxx02");
		testInput1.writeFile("0211xxxx02");

		// setting input2 data
		testInput2.writeFile("0000xxxx01");
		testInput2.writeFile("0000xxxx01");
		testInput2.writeFile("0011xxxx01");
		testInput2.writeFile("0021xxxx01");
		testInput2.writeFile("0031xxxx01");
		testInput2.writeFile("0041xxxx01");
		testInput2.writeFile("0051xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");
		testInput2.writeFile("0211xxxx01");

		componentII1.addInputFileInformation(testInput1);
		componentII1.addOutputFileInformation(testInputA);

		componentII2.addInputFileInformation(testInput2);
		componentII2.addOutputFileInformation(testInputB);

		compare.addInputFileInformation(testInputA, testInputB);
		compare.addOutputFileInformation(testOutput);
		compare.addLastComponent(componentII1, componentII2);
		
		componentII1.start();
		componentII2.start();
		compare.start();
		
		componentII1.join();
		componentII2.join();
		compare.join();
		
		assertFalse("testOutput should not be empty", testOutput.isEmpty());
		assertEquals("0000xxxx01,0000xxxx01", testOutput.readFile());
		assertEquals("0011xxxx02,0011xxxx01", testOutput.readFile());
		assertEquals("0041xxxx02,0041xxxx01", testOutput.readFile());
		assertEquals("0211xxxx02,0211xxxx01", testOutput.readFile());
		assertTrue("testoUTPUT SHOULD BE EMPTY", testOutput.isEmpty());

	}

}
