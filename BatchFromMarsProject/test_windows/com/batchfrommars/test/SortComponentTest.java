package com.batchfrommars.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;
import com.batchfrommars.file.QueueFile;
import com.batchfrommars.test.component.StepDelayBatch;
import com.batchfrommars.test.component.StepSort;
import com.batchfrommars.test.component.StepSortManyRound;
import com.batchfrommars.test.component.StepSortObject;
import com.batchfrommars.test.component.StepSortSplitFile;

public class SortComponentTest {

	@Test
	public void testNoInput() {
		StepSort sort = new StepSort();
		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		sort.addInputFileInformation(testInput);
		sort.addOutputFileInformation(testOutput);

		sort.activate();
		
		assertTrue(testOutput.isEmpty());
	}

	@Test
	public void testGetRunTimeMemoryUsage() throws Exception {
		StepSort sort = new StepSort();
		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0001xxxx");
		testInput.writeFile("0100xxxx");
		testInput.writeFile("0120xxxx");
		testInput.writeFile("0030xxxx");
		testInput.writeFile("0034xxxx");
		testInput.writeFile("0250xxxx");
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0010xxxx");
		testInput.writeFile("1240xxxx");
		testInput.writeFile("1000xxxx");
		testInput.writeFile("0500xxxx");
		testInput.writeFile("8500xxxx");
		testInput.writeFile("0022xxxx");
		testInput.writeFile("0440xxxx");

		sort.addInputFileInformation(testInput);
		sort.addOutputFileInformation(testOutput);

		sort.activate();

		assertEquals("8500xxxx", testOutput.readFile());
		assertEquals("1240xxxx", testOutput.readFile());
		assertEquals("1000xxxx", testOutput.readFile());
		assertEquals("0500xxxx", testOutput.readFile());
		assertEquals("0440xxxx", testOutput.readFile());
		assertEquals("0250xxxx", testOutput.readFile());
		assertEquals("0120xxxx", testOutput.readFile());
		assertEquals("0100xxxx", testOutput.readFile());
		assertEquals("0034xxxx", testOutput.readFile());
		assertEquals("0030xxxx", testOutput.readFile());
		assertEquals("0022xxxx", testOutput.readFile());
		assertEquals("0010xxxx", testOutput.readFile());
		assertEquals("0001xxxx", testOutput.readFile());
		assertEquals("0000xxxx", testOutput.readFile());
		assertEquals("0000xxxx", testOutput.readFile());

	}

	@Test
	public void testDelaySort() throws Exception {
		StepSort sort = new StepSort();
		StepDelayBatch batch = new StepDelayBatch();

		FileInformation testInput = new QueueFile();
		FileInformation temp = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0001xxxx");
		testInput.writeFile("0100xxxx");
		testInput.writeFile("0120xxxx");
		testInput.writeFile("0030xxxx");
		testInput.writeFile("0034xxxx");
		testInput.writeFile("0250xxxx");
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0010xxxx");
		testInput.writeFile("1240xxxx");
		testInput.writeFile("1000xxxx");
		testInput.writeFile("0500xxxx");
		testInput.writeFile("8500xxxx");
		testInput.writeFile("0022xxxx");
		testInput.writeFile("0440xxxx");

		batch.addInputFileInformation(testInput);
		batch.addOutputFileInformation(temp);
		sort.addInputFileInformation(temp);
		sort.addOutputFileInformation(testOutput);
		sort.addLastComponent(batch);

		batch.start();
		sort.start();

		batch.join();
		sort.join();

		assertFalse(testOutput.isEmpty());
		assertEquals("8500xxxx", testOutput.readFile());
		assertEquals("1240xxxx", testOutput.readFile());
		assertEquals("1000xxxx", testOutput.readFile());
		assertEquals("0500xxxx", testOutput.readFile());
		assertEquals("0440xxxx", testOutput.readFile());
		assertEquals("0250xxxx", testOutput.readFile());
		assertEquals("0120xxxx", testOutput.readFile());
		assertEquals("0100xxxx", testOutput.readFile());
		assertEquals("0034xxxx", testOutput.readFile());
		assertEquals("0030xxxx", testOutput.readFile());
		assertEquals("0022xxxx", testOutput.readFile());
		assertEquals("0010xxxx", testOutput.readFile());
		assertEquals("0001xxxx", testOutput.readFile());
		assertEquals("0000xxxx", testOutput.readFile());
		assertEquals("0000xxxx", testOutput.readFile());
		assertTrue(testOutput.isEmpty());
	}

	@Test
	public void testPhysicalFile() throws Exception {
		StepSort sort = new StepSort();
		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new PhysicalFile(PhysicalFile.OUTPUT, "d:/test.txt", "UTF8", false);

		// Set input
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0001xxxx");
		testInput.writeFile("0100xxxx");
		testInput.writeFile("0120xxxx");
		testInput.writeFile("0030xxxx");
		testInput.writeFile("0034xxxx");
		testInput.writeFile("0250xxxx");
		testInput.writeFile("0000xxxx");
		testInput.writeFile("0010xxxx");
		testInput.writeFile("1240xxxx");
		testInput.writeFile("1000xxxx");
		testInput.writeFile("0500xxxx");
		testInput.writeFile("8500xxxx");
		testInput.writeFile("0022xxxx");
		testInput.writeFile("0440xxxx");

		sort.addInputFileInformation(testInput);
		sort.addOutputFileInformation(testOutput);

		sort.activate();

		testOutput.closeFile();
		testInput.closeFile();
		
		FileInformation assertInput = new PhysicalFile(PhysicalFile.INPUT, "d:/test.txt", "UTF8", false);

		assertFalse(assertInput.isEmpty());
		assertEquals("8500xxxx", assertInput.readFile());
		assertEquals("1240xxxx", assertInput.readFile());
		assertEquals("1000xxxx", assertInput.readFile());
		assertEquals("0500xxxx", assertInput.readFile());
		assertEquals("0440xxxx", assertInput.readFile());
		assertEquals("0250xxxx", assertInput.readFile());
		assertEquals("0120xxxx", assertInput.readFile());
		assertEquals("0100xxxx", assertInput.readFile());
		assertEquals("0034xxxx", assertInput.readFile());
		assertEquals("0030xxxx", assertInput.readFile());
		assertEquals("0022xxxx", assertInput.readFile());
		assertEquals("0010xxxx", assertInput.readFile());
		assertEquals("0001xxxx", assertInput.readFile());
		assertEquals("0000xxxx", assertInput.readFile());
		assertEquals("0000xxxx", assertInput.readFile());
		assertTrue(assertInput.isEmpty());

		assertInput.closeFile();

	}

	@Test
	public void testTwoRoundSort() throws Exception {
		StepSortManyRound sort = new StepSortManyRound();
		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input
		testInput.writeFile("0000AA00");
		testInput.writeFile("0001BB01");
		testInput.writeFile("0000CC02");
		testInput.writeFile("0001DD03");
		testInput.writeFile("0003EE04");
		testInput.writeFile("0002EE05");
		testInput.writeFile("0003DD06");
		testInput.writeFile("0000HH07");
		testInput.writeFile("0002AA08");
		testInput.writeFile("0001xx08");
		testInput.writeFile("0003AA01");
		testInput.writeFile("0002ZZ02");
		testInput.writeFile("0000BB05");
		testInput.writeFile("0003BB00");
		testInput.writeFile("0001NN01");
		testInput.writeFile("0002NN09");
		testInput.writeFile("0001NN09");
		testInput.writeFile("0001NN08");
		testInput.writeFile("0005NN07");
		testInput.writeFile("0001NN07");
		testInput.writeFile("0002NN08");
		testInput.writeFile("0002NN09");
		testInput.writeFile("0002NN04");
		testInput.writeFile("0002NN06");
		testInput.writeFile("0000AA05");
		testInput.writeFile("0001BB00");
		testInput.writeFile("0000CC00");
		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");

		sort.addInputFileInformation(testInput);
		sort.addOutputFileInformation(testOutput);
		sort.activate();

		assertFalse(testOutput.isEmpty());
		assertEquals("0005NN07", testOutput.readFile());
		assertEquals("0003AA01", testOutput.readFile());
		assertEquals("0003BB00", testOutput.readFile());
		assertEquals("0003DD06", testOutput.readFile());
		assertEquals("0003DD01", testOutput.readFile());
		assertEquals("0003EE04", testOutput.readFile());
		assertEquals("0003EE03", testOutput.readFile());
		assertEquals("0002AA08", testOutput.readFile());
		assertEquals("0002EE05", testOutput.readFile());
		assertEquals("0002EE02", testOutput.readFile());
		assertEquals("0002NN09", testOutput.readFile());
		assertEquals("0002NN09", testOutput.readFile());
		assertEquals("0002NN08", testOutput.readFile());
		assertEquals("0002NN06", testOutput.readFile());
		assertEquals("0002NN04", testOutput.readFile());
		assertEquals("0002ZZ02", testOutput.readFile());
		assertEquals("0001BB01", testOutput.readFile());
		assertEquals("0001BB00", testOutput.readFile());
		assertEquals("0001DD03", testOutput.readFile());
		assertEquals("0001DD00", testOutput.readFile());
		assertEquals("0001NN09", testOutput.readFile());
		assertEquals("0001NN08", testOutput.readFile());
		assertEquals("0001NN07", testOutput.readFile());
		assertEquals("0001NN01", testOutput.readFile());
		assertEquals("0001xx08", testOutput.readFile());
		assertEquals("0000AA05", testOutput.readFile());
		assertEquals("0000AA00", testOutput.readFile());
		assertEquals("0000BB05", testOutput.readFile());
		assertEquals("0000CC02", testOutput.readFile());
		assertEquals("0000CC00", testOutput.readFile());
		assertEquals("0000HH07", testOutput.readFile());
		assertEquals("0000HH00", testOutput.readFile());
		assertTrue(testOutput.isEmpty());

	}


	public void testSortLargData() throws UnsupportedEncodingException, FileNotFoundException {
		StepSort sort = new StepSort();
		FileInformation testInput = new PhysicalFile(PhysicalFile.INPUT, "e:/BatchFromMars/SortData/sort3.txt", "UTF8",
				false);
		FileInformation testOutput = new PhysicalFile(PhysicalFile.OUTPUT, "e:/BatchFromMars/SortData/sort4.txt",
				"UTF8", false);

		sort.addInputFileInformation(testInput);
		sort.addOutputFileInformation(testOutput);

		long start = System.currentTimeMillis();
		sort.activate();
		long end = System.currentTimeMillis();

		System.out.println((end - start) / 1000 + " second");
	}

	public void testSortSplitFile() throws UnsupportedEncodingException, FileNotFoundException {
		StepSortSplitFile sort = new StepSortSplitFile();
		FileInformation testInput = new PhysicalFile(PhysicalFile.INPUT, "e:/BatchFromMars/SortData/sort5.txt", "UTF8",
				false);
		FileInformation testOutput = new PhysicalFile(PhysicalFile.OUTPUT, "e:/BatchFromMars/SortData/sort6.txt",
				"UTF8", false);

		sort.addInputFileInformation(testInput);
		sort.addOutputFileInformation(testOutput);

		long start = System.currentTimeMillis();
		sort.activate();
		long end = System.currentTimeMillis();

		System.out.println((end - start) / 1000 + " second");

	}

	public void testSortObject() throws UnsupportedEncodingException, FileNotFoundException {
		StepSortObject sort = new StepSortObject();
		FileInformation testInput = new PhysicalFile(PhysicalFile.INPUT, "e:/BatchFromMars/SortData/sort3.txt", "UTF8",
				false);
		FileInformation testOutput = new PhysicalFile(PhysicalFile.OUTPUT, "e:/BatchFromMars/SortData/sort4.txt",
				"UTF8", false);

		sort.addInputFileInformation(testInput);
		sort.addOutputFileInformation(testOutput);

		long start = System.currentTimeMillis();
		sort.activate();
		long end = System.currentTimeMillis();

		System.out.println((end - start) / 1000 + " second");

	}

}
