package com.batchfrommars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;
import com.batchfrommars.test.component.StepMerge;
import com.batchfrommars.test.component.StepMergeAscending;
import com.batchfrommars.test.component.TestBatchComponentII1;
import com.batchfrommars.test.component.TestBatchComponentII2;
import com.batchfrommars.test.component.TwoWayBatchComponenet;

public class MergeSortComponentTest {

	@Test
	public void testSingleMergeSortComponentDescending() throws Exception {
		StepMerge merge = new StepMerge();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();
		merge.addInputFileInformation(testInput1, testInput2);
		merge.addOutputFileInformation(testOutput);

		// set input1 data
		testInput1.writeFile("9487xxxx");
		testInput1.writeFile("9444xxxx");
		testInput1.writeFile("5487xxxx");
		testInput1.writeFile("3487xxxx");
		testInput1.writeFile("1487xxxx");

		// set input2 data
		testInput2.writeFile("9487bbbb");
		testInput2.writeFile("9487bbbb");
		testInput2.writeFile("6487bbbb");
		testInput2.writeFile("5487bbbb");
		testInput2.writeFile("1111bbbb");

		// start merge
		merge.activate();

		// testing
		assertFalse(testOutput.isEmpty());
		assertEquals("9487xxxx", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertEquals("9444xxxx", testOutput.readFile());
		assertEquals("6487bbbb", testOutput.readFile());
		assertEquals("5487xxxx", testOutput.readFile());
		assertEquals("5487bbbb", testOutput.readFile());
		assertEquals("3487xxxx", testOutput.readFile());
		assertEquals("1487xxxx", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertTrue(testOutput.isEmpty());

	}

	@Test
	public void testSingleMergeSortComponentAscending() throws Exception {
		StepMergeAscending merge = new StepMergeAscending();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		merge.addInputFileInformation(testInput1, testInput2);
		merge.addOutputFileInformation(testOutput);

		// set input1 data
		testInput1.writeFile("1487xxxx");
		testInput1.writeFile("3487xxxx");
		testInput1.writeFile("5487xxxx");
		testInput1.writeFile("9444xxxx");
		testInput1.writeFile("9487xxxx");

		// set input2 data
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("5487bbbb");
		testInput2.writeFile("6487bbbb");
		testInput2.writeFile("9487bbbb");
		testInput2.writeFile("9487bbbb");

		// start merge
		merge.activate();
		assertFalse(testOutput.isEmpty());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1487xxxx", testOutput.readFile());
		assertEquals("3487xxxx", testOutput.readFile());
		assertEquals("5487xxxx", testOutput.readFile());
		assertEquals("5487bbbb", testOutput.readFile());
		assertEquals("6487bbbb", testOutput.readFile());
		assertEquals("9444xxxx", testOutput.readFile());
		assertEquals("9487xxxx", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertTrue(testOutput.isEmpty());

	}

	@Test
	public void testTwoWayMergeSortComponent() throws Exception {
		StepMerge merge = new StepMerge();
		TestBatchComponentII1 batch1 = new TestBatchComponentII1();
		TestBatchComponentII2 batch2 = new TestBatchComponentII2();

		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		FileInformation temp1 = new QueueFile();
		FileInformation temp2 = new QueueFile();

		// set batch1
		batch1.addInputFileInformation(testInput1);
		batch1.addOutputFileInformation(temp1);

		// set batch2
		batch2.addInputFileInformation(testInput2);
		batch2.addOutputFileInformation(temp2);

		// set merge
		merge.addInputFileInformation(temp1, temp2);
		merge.addOutputFileInformation(testOutput);
		merge.addLastComponent(batch1, batch2);

		// set testing data
		// set input1 data
		testInput1.writeFile("9487xxxx");
		testInput1.writeFile("9444xxxx");
		testInput1.writeFile("5487xxxx");
		testInput1.writeFile("3487xxxx");
		testInput1.writeFile("1487xxxx");

		// set input2 data
		testInput2.writeFile("9487bbbb");
		testInput2.writeFile("9487bbbb");
		testInput2.writeFile("6487bbbb");
		testInput2.writeFile("5487bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");

		batch1.start();
		batch2.start();
		merge.start();

		batch1.join();
		batch2.join();
		merge.join();

		assertFalse(testOutput.isEmpty());
		assertEquals("9487xxxx", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertEquals("9444xxxx", testOutput.readFile());
		assertEquals("6487bbbb", testOutput.readFile());
		assertEquals("5487xxxx", testOutput.readFile());
		assertEquals("5487bbbb", testOutput.readFile());
		assertEquals("3487xxxx", testOutput.readFile());
		assertEquals("1487xxxx", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertTrue(testOutput.isEmpty());

	}

	@Test
	public void testOneWayMergeSortComponent() throws Exception {
		StepMerge merge = new StepMerge();
		TwoWayBatchComponenet componenet = new TwoWayBatchComponenet();

		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		FileInformation temp1 = new QueueFile();
		FileInformation temp2 = new QueueFile();

		// set component
		componenet.addInputFileInformation(testInput1, testInput2);
		componenet.addOutputFileInformation(temp1, temp2);

		// set merge
		merge.addInputFileInformation(temp1, temp2);
		merge.addOutputFileInformation(testOutput);
		merge.addLastComponent(componenet);

		
		// set input1 data
		testInput1.writeFile("9487xxxx");
		testInput1.writeFile("9444xxxx");
		testInput1.writeFile("5487xxxx");
		testInput1.writeFile("3487xxxx");
		testInput1.writeFile("1487xxxx");

		// set input2 data
		testInput2.writeFile("9487bbbb");
		testInput2.writeFile("9487bbbb");
		testInput2.writeFile("6487bbbb");
		testInput2.writeFile("5487bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		testInput2.writeFile("1111bbbb");
		
		componenet.start();
		merge.start();
		
		componenet.join();
		merge.join();
		
		assertFalse(testOutput.isEmpty());
		assertEquals("9487xxxx", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertEquals("9487bbbb", testOutput.readFile());
		assertEquals("9444xxxx", testOutput.readFile());
		assertEquals("6487bbbb", testOutput.readFile());
		assertEquals("5487xxxx", testOutput.readFile());
		assertEquals("5487bbbb", testOutput.readFile());
		assertEquals("3487xxxx", testOutput.readFile());
		assertEquals("1487xxxx", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertEquals("1111bbbb", testOutput.readFile());
		assertTrue(testOutput.isEmpty());

	}

}
