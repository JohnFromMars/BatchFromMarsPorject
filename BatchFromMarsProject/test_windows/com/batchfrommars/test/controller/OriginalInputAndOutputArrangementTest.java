package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.ListFile;

public class OriginalInputAndOutputArrangementTest {
	@Test
	public void testListInput() throws Exception {
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new ListFile();
		FileInformation testInput2 = new ListFile();
		FileInformation testOutput = new ListFile();

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
	public void testListInput2() throws Exception {
		BatchController batchController = new BatchController();
		List<String> testInput1 = new ArrayList<>();
		List<String> testInput2= new ArrayList<>();
		FileInformation testOutput = new ListFile();
		
		testInput1.add("000011111");
		testInput1.add("000002222");
		testInput1.add("1111xxxxx");
		testInput1.add("4444sxdcf");
		
		testInput2.add("000011111");
		testInput2.add("111111111");
		testInput2.add("4444sxdcf");
		
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
	public void testListInput3() throws Exception {
		BatchController batchController = new BatchController();
		List<String> testInput1 = new ArrayList<>();
		List<String> testInput2= new ArrayList<>();
		List<String> testOutput= new ArrayList<>();

		
		testInput1.add("000011111");
		testInput1.add("000002222");
		testInput1.add("1111xxxxx");
		testInput1.add("4444sxdcf");
		
		testInput2.add("000011111");
		testInput2.add("111111111");
		testInput2.add("4444sxdcf");
		
		//@formatter:off
	    batchController.input(testInput1)
	                   .input(testInput2)
	                   .output(testOutput)
	                   .merge()
	                   .execute();
	   //@formatter:on
		assertFalse(testOutput.isEmpty());
		assertEquals("000011111", testOutput.get(0));
		assertEquals("000002222", testOutput.get(1));
		assertEquals("1111xxxxx", testOutput.get(2));
		assertEquals("4444sxdcf", testOutput.get(3));
		assertEquals("000011111", testOutput.get(4));
		assertEquals("111111111", testOutput.get(5));
		assertEquals("4444sxdcf", testOutput.get(6));
	
	

	}
}
