package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;

@RunWith(Parameterized.class)
public class OriginalMergeArrangementTest {

	@Parameterized.Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[1000][0]);
	}

	@Test
	public void testMerge1() throws Exception {
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		testInput1.writeFile("000011111");
		testInput1.writeFile("000002222");
		testInput1.writeFile("1111xxxxx");
		testInput1.writeFile("4444sxdcf");

		testInput2.writeFile("000011111");
		testInput2.writeFile("111111111");
		testInput2.writeFile("4444sxdcf");

		//@formatter:off
	    batchController.input(testInput1)
	                   .input(testInput2)
	                   .output(testOutput)
	                   .merge()
	                   .execute();
	   //@formatter:on
		assertFalse(testOutput.isEmpty());
		assertEquals("000011111", testOutput.readFile());
		assertEquals("000002222", testOutput.readFile());
		assertEquals("1111xxxxx", testOutput.readFile());
		assertEquals("4444sxdcf", testOutput.readFile());
		assertEquals("000011111", testOutput.readFile());
		assertEquals("111111111", testOutput.readFile());
		assertEquals("4444sxdcf", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}

	@Test
	public void testMergeWithThreeInput() throws Exception {
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testInput3 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		testInput1.writeFile("000011111");
		testInput1.writeFile("000002222");
		testInput1.writeFile("1111xxxxx");
		testInput1.writeFile("4444sxdcf");

		testInput2.writeFile("000011111");
		testInput2.writeFile("111111111");
		testInput2.writeFile("4444sxdcf");

		testInput3.writeFile("aaaaaaaaa");
		testInput3.writeFile("bbbbbbbbb");

		//@formatter:off
	    batchController.input(testInput1)
	                   .input(testInput2)
	                   .input(testInput3)
	                   .output(testOutput)
	                   .merge()
	                   .execute();
	   //@formatter:on
		assertFalse(testOutput.isEmpty());
		assertEquals("000011111", testOutput.readFile());
		assertEquals("000002222", testOutput.readFile());
		assertEquals("1111xxxxx", testOutput.readFile());
		assertEquals("4444sxdcf", testOutput.readFile());
		assertEquals("000011111", testOutput.readFile());
		assertEquals("111111111", testOutput.readFile());
		assertEquals("4444sxdcf", testOutput.readFile());
		assertEquals("aaaaaaaaa", testOutput.readFile());
		assertEquals("bbbbbbbbb", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}

	@Test(expected = InputMismatchException.class)
	public void testMergeInput() throws Exception {
		BatchController batchController = new BatchController();
		//@formatter:off
		batchController.merge()
		               .execute();
		 //@formatter:on
	}

//	@Test
	@Test(expected = UnsupportedOperationException.class)
	public void testMergeAndCompare() throws Exception {
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();
		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
		               .merge()
		               .compare((s)->s, (s)->s, (s1,s2)->s1+s2)
		               .execute();
		//@formatter:on
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testTwoMerge() throws Exception{
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();
		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
		               .merge()
		               .merge()
		               .execute();
		//@formatter:on
		
	}
	
//	@Test
	@Test(expected = UnsupportedOperationException.class)
	public void testMergeTaskIsNotTheFirst() throws Exception{
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();
		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
		               .compare((s)->s, (s)->s, (s1,s2)->s1)
		               .merge()
		               .execute();
		//@formatter:on
		
	}
//	@Test
	@Test(expected = UnsupportedOperationException.class)
	public void testMergeTaskIsNotTheFirst2() throws Exception{
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();
		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
		               .map((s)->s+"xx")
		               .merge()
		               .execute();
		//@formatter:on
		
	}
}
