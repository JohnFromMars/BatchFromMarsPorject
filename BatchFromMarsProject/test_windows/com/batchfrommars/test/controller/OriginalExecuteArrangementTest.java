package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;
import com.batchfrommars.util.LogLevel;

@RunWith(Parameterized.class)
public class OriginalExecuteArrangementTest {

	@Parameterized.Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[20][0]);
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
				       .logger("testMultipleTasks", "D:/BatchFromMars", LogLevel.FINEST)
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
				       .logger("testMultipleTasks2", "D:/BatchFromMars", LogLevel.FINEST)
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
				       .logger("testHeader", "D:/BatchFromMars", LogLevel.FINEST)
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
				       .logger("testFooter", "D:/BatchFromMars", LogLevel.FINEST)
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
				       .logger("testHeaderAndFooter", "D:/BatchFromMars", LogLevel.FINEST)
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
	public void testPhysicalFileWithHeaderAndFooter() throws Exception {
		BatchController batchController = new BatchController() {
		};
		//@formatter:off
		batchController.logger("testPhysicalFileWithHeaderAndFooter", "D:/BatchFromMars", LogLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)
				       .header("header...")
				       .footer("footer...")
				       .sort("4,6,1,3")
				       .execute();
		//@formatter:on
	}

	@Test
	public void testPhysicalFileWithHeader() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		batchController.logger("testPhysicalFileWithHeaderAndFooter", "D:/BatchFromMars", LogLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TestHeader.txt", "BIG5", false)
				       .header("header...")
				       .sort("4,6,1,3")
				       .execute();
		//@formatter:on
	}

	@Test
	public void testPhysicalFileWithfooter() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		batchController.logger("testPhysicalFileWithfooter", "D:/BatchFromMars", LogLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
			           .output("D:/BatchFromMars/TestFooter.txt", "BIG5", false)
			           .footer("footer...")
			           .sort("4,6,1,3")
			           .execute();
		//@formatter:on
	}

	@Test
	public void testNoInput() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		batchController.logger("testNoInput", "D:/BatchFromMars", LogLevel.FINEST)
				       .output("D:/BatchFromMars/TestNoInput.txt", "BIG5", false).sort("4,6,1,3").header("header...")
				       .footer("footer...")
				       .map((s) -> s.substring(0, 4))
				       .filter((s) -> s.equals("0000"))
				       .execute();
		//@formatter:on
	}

	@Test
	public void testNoInput2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		//@formatter:off
		String header = "出表日期:" + new Date().toString() + "\r\n"
		              + "出表單位:資訊處";
		//@formatter:on

		//@formatter:off
		batchController.logger("testNoInput2", "D:/BatchFromMars", LogLevel.FINEST)
			 	       .output("D:/BatchFromMars/TestNoInput2.txt", "BIG5", false)
			 	       .sort("4,6,1,3")
			 	       .header(header)
				       .footer("footer...\r\noh oh oh!")
				       .map((s) -> s + "cc")
				       .filter((s) -> s.equals("0000"))
				       .execute();
		//@formatter:on
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
				       .logger("testMultipleTasks3", "D:/BatchFromMars", LogLevel.FINEST)
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
				       .logger("testMultipleTasks4", "D:/BatchFromMars", LogLevel.FINEST)
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
				       .logger("testCompareMultipleTasks", "D:/BatchFromMars", LogLevel.FINEST)
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
				       .logger("testCompareMultipleTasks2", "D:/BatchFromMars", LogLevel.FINEST)
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

}
