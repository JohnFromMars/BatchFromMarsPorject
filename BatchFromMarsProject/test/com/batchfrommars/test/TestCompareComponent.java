package com.batchfrommars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.TemporaryFile;
import com.batchfrommars.test.component.StepCompare;

public class TestCompareComponent {

	@Test
	public void testSingleCompareComponent() {
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

}
