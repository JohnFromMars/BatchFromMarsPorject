package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;

@RunWith(Parameterized.class)
public class OriginalExecuteArrangementTest {

	@Parameterized.Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[2][0]);
	}

	@Test
	public void testMultipleTasks() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
				       .filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0)
				       .sort("1,4,A")
				       .map((String s) -> s + ".txt")
				       .execute();
		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0002EE02.txt", testOutput.readFile());
		assertEquals("0003EE03.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testMultipleTasks2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
				       .map((String s) -> s + ".txt")
				       .sort("1,4,A")
				       .filter((s) -> s.substring(4, 6).equals("DD"))
				       .execute();
		//@formatter:on
		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testHeader() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();
		String header = "hello this is header !";

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
		               .header(header)
				       .map((String s) -> s + ".txt")
				       .sort("1,4,A")
				       .filter((s) -> s.substring(4, 6).equals("DD"))
				       .execute();

		//@formatter:on
		assertFalse(testOutput.isEmpty());
		assertEquals(header, testOutput.readFile());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testFooter() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();
		String footer = "hello this is footer !";

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
		               .footer(footer)
				       .map((String s) -> s + ".txt")
				       .sort("1,4,A")
				       .filter((s) -> s.substring(4, 6).equals("DD"))
				       .execute();
		//@formatter:on
		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertEquals(footer, testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testHeaderAndFooter() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();
		String footer = "hello this is footer !";
		String header = "hello this is header !";

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
		               .footer(footer)
		               .header(header)
				       .map((String s) -> s + ".txt")
				       .sort("1,4,A")
				       .filter((s) -> s.substring(4, 6)
				       .equals("DD"))
				       .execute();
		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals(header, testOutput.readFile());
		assertEquals("0001DD00.txt", testOutput.readFile());
		assertEquals("0003DD01.txt", testOutput.readFile());
		assertEquals(footer, testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	

	



	@Test
	public void testMultipleTasks3() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0003DD01");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
				       .filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0)
				       .sort("1,4,A")
				       .map((String s) -> s + ".txt")
				       .filter((s) -> s.substring(0, 4).equals("0003"))
				       .map((s) -> s + ".cc")
				       .execute();
		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals("0003EE03.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testMultipleTasks4() throws Exception {
		BatchController batchController = new BatchController() {
		};

		FileInformation testInput = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput.writeFile("0001DD00");
		testInput.writeFile("0003EE03");
		testInput.writeFile("0002EE02");
		testInput.writeFile("0003DD01");
		testInput.writeFile("0000HH00");
		testInput.writeFile("0000HH000");

		//@formatter:off
		batchController.input(testInput)
		               .output(testOutput)
				       .filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0)
				       .sort("1,4,A")
				       .map((String s) -> s + ".txt")
				       .filter((s) -> s.substring(0, 4).equals("0003"))
				       .map((s) -> s + ".cc")
				       .execute();

		//@formatter:on
		assertFalse(testOutput.isEmpty());
		assertEquals("0003EE03.txt.cc", testOutput.readFile());
		assertEquals("0003DD01.txt.cc", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}

	@Test
	public void testCompareMultipleTasks() throws Exception {

		BatchController batchController = new BatchController() {
		};

		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput1.writeFile("0004EE02");
		testInput1.writeFile("0001DD00");
		testInput1.writeFile("0003EE03");
		testInput1.writeFile("0000HH000");
		testInput1.writeFile("0002EE02");

		testInput2.writeFile("0008HH0003");
		testInput2.writeFile("0000HH0002");
		testInput2.writeFile("0001HH0004");
		testInput2.writeFile("0004HH0006");
		testInput2.writeFile("0002HH0001");
		testInput2.writeFile("0009HH0005");

		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
				       .compare((s)->s.substring(0, 4), (s) -> s.substring(6, 10), (s1,s2) -> s1 + "," + s2)
				       .filter((s) -> Integer.valueOf(s.substring(0, 4)) > 0)
				       .map((String s) -> s + ".txt")
				       .filter((s) -> s.substring(0, 4).equals("0003") || s.substring(0, 4).equals("0001"))
				       .map((s) -> s + ".cc")
				       .execute();
		//@formatter:on
		
		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00,0002HH0001.txt.cc", testOutput.readFile());
		assertEquals("0003EE03,0008HH0003.txt.cc", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
	
	@Test
	public void testCompareMultipleTasks2() throws Exception {

		BatchController batchController = new BatchController() {
		};

		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput1.writeFile("0004EE02");
		testInput1.writeFile("0001DD00");
		testInput1.writeFile("0003EE03");
		testInput1.writeFile("0000HH000");
		testInput1.writeFile("0002EE02");

		testInput2.writeFile("0008HH0003");
		testInput2.writeFile("0000HH0002");
		testInput2.writeFile("0001HH0004");
		testInput2.writeFile("0004HH0006");
		testInput2.writeFile("0002HH0001");
		testInput2.writeFile("0009HH0005");

		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
				       .compare((s)->s.substring(0, 4), (s) -> s.substring(6, 10), (s1,s2) -> s1 + "," + s2)
				       .map((s) -> s + ".cc")
				       .execute();
		//@formatter:on
		
		assertFalse(testOutput.isEmpty());
		assertEquals("0001DD00,0002HH0001.cc", testOutput.readFile());
		assertEquals("0002EE02,0000HH0002.cc", testOutput.readFile());
		assertEquals("0003EE03,0008HH0003.cc", testOutput.readFile());
		assertEquals("0004EE02,0001HH0004.cc", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
	
	@Test
	public void testMergeMultipleTasks1() throws Exception {

		BatchController batchController = new BatchController() {
		};

		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput1.writeFile("0004EE02aa");
		testInput1.writeFile("0001DD00aa");
		testInput1.writeFile("0003EE0dd3");
		testInput1.writeFile("0000HH000d");
		testInput1.writeFile("0002EE02dd");

		testInput2.writeFile("0008HH0003");
		testInput2.writeFile("0000HH0002");
		testInput2.writeFile("0001HH0004");
		testInput2.writeFile("0004HH0006");
		testInput2.writeFile("0002HH0001");
		testInput2.writeFile("0009HH0005");

		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
				       .merge()
				       .filter((s)->s.substring(4, 6).equals("EE"))
				       .map((s) -> s + ".cc")
				       .execute();
		//@formatter:on
		
		assertFalse(testOutput.isEmpty());
		assertEquals("0004EE02aa.cc", testOutput.readFile());
		assertEquals("0003EE0dd3.cc", testOutput.readFile());
		assertEquals("0002EE02dd.cc", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());

	}
	
	@Test
	public void testMergeMultipleTasks2() throws Exception {

		BatchController batchController = new BatchController() {
		};

		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput1.writeFile("0004EE02aa");
		testInput1.writeFile("0001DD00aa");
		testInput1.writeFile("0003EE0dd3");
		testInput1.writeFile("0000HH000d");
		testInput1.writeFile("0002EE02dd");

		testInput2.writeFile("0008HH0003");
		testInput2.writeFile("0000HH0002");
		testInput2.writeFile("0001HH0004");
		testInput2.writeFile("0004HH0006");
		testInput2.writeFile("0002HH0001");
		testInput2.writeFile("0009HH0005");

		//@formatter:off
		int count = batchController.input(testInput1)
		                           .input(testInput2)
		                           .output(testOutput)
		                           .header("test header")
		                           .footer("test footer")
				                   .merge()
				                   .filter((s)->s.substring(4, 6).equals("EE"))
		                           .map((s) -> s + ".cc")
		                           .sort("1,4,a")				       
			                       .count();
		//@formatter:on
		
		assertFalse(testOutput.isEmpty());
		assertEquals("test header", testOutput.readFile());
		assertEquals("0002EE02dd.cc", testOutput.readFile());
		assertEquals("0003EE0dd3.cc", testOutput.readFile());
		assertEquals("0004EE02aa.cc", testOutput.readFile());
		assertEquals("test footer", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
		assertEquals(3, count);
		
	}
	
	@Test
	public void testMergeMultipleTasks3() throws Exception {

		BatchController batchController = new BatchController() {
		};

		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		// Set input

		testInput1.writeFile("0004.50EE02aa");
		testInput1.writeFile("0001.33DD00aa");
		testInput1.writeFile("0003.40EE0dd3");
		testInput1.writeFile("0000.20HH000d");
		testInput1.writeFile("0002.11EE02dd");

		testInput2.writeFile("0008.33HH0003");
		testInput2.writeFile("0000.44HH0002");
		testInput2.writeFile("0001.55HH0004");
		testInput2.writeFile("0004.66HH0006");
		testInput2.writeFile("0002.77HH0001");
		testInput2.writeFile("0009.00HH0005");

		//@formatter:off
		BigDecimal sum = batchController.input(testInput1)
		                                .input(testInput2)
		                                .output(testOutput)
		                                .header("test header")
		                                .footer("test footer")
				                        .merge()
				                        .filter((s)->s.substring(7, 9).equals("EE"))
		                                .map((s) -> s + ".cc")
		                                .sort("1,4,a")				       
			                            .sum((s)->s.substring(0,7));
		//@formatter:on
		
		
		assertFalse(testOutput.isEmpty());
		assertEquals("test header", testOutput.readFile());
		assertEquals("0002.11EE02dd.cc", testOutput.readFile());
		assertEquals("0003.40EE0dd3.cc", testOutput.readFile());
		assertEquals("0004.50EE02aa.cc", testOutput.readFile());
		assertEquals("test footer", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
		assertEquals(new BigDecimal("10.01"), sum);
		
	}


}
