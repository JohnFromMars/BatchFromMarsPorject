package com.batchfrommars.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.logging.Level;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;

@RunWith(Parameterized.class)
public class OriginalCompareArrangementTest {
	@Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[1][0]);
    }
	
	@Test
	public void testCompareTaskWithOrder() throws Exception {
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
		               .logger("testCompareTaskWithOrder", "D:/BatchFromMars", Level.FINEST)
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .execute();

		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals("000011111000011111", testOutput.readFile());
		assertEquals("4444sxdcf4444sxdcf", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}
	
	@Test(expected = UnsupportedOperationException.class)  
	public void testTwoCompareTask() throws Exception {
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
		               .logger("testTwoCompareTask", "D:/BatchFromMars", Level.FINEST)
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .execute();

		//@formatter:on

	}
	
	@Test(expected = InputMismatchException.class)  
	public void testOneInput() throws Exception {
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
		               .output(testOutput)
		               .logger("testOneInput", "D:/BatchFromMars", Level.FINEST)
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .execute();

		//@formatter:on

	}
	
	@Test(expected = UnsupportedOperationException.class)  
	public void testCompareNotTheFirstTask() throws Exception {
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
		               .logger("testCompareNotTheFirstTask", "D:/BatchFromMars", Level.FINEST)
		               .filter((s)->s.substring(0, 4).equals("0000"))
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .execute();

		//@formatter:on

	}
	
	public void testCompareIsTheFirstTask() throws Exception {
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
		               .logger("testCompareIsTheFirstTask", "D:/BatchFromMars", Level.FINEST)
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .filter((s)->s.substring(0, 4).equals("0000"))
		               .map((s)->s+"TEST")
		               .execute();

		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals("000011111000011111TEST", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
		
	}
	
	@Test
	public void testCompareTaskWithoutOrder() throws Exception {
		BatchController batchController = new BatchController();
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();
		FileInformation testOutput = new QueueFile();

		testInput1.writeFile("1111xxxxx");
		testInput1.writeFile("4444sxdcf");
		testInput1.writeFile("000002222");
		testInput1.writeFile("000011111");
		testInput2.writeFile("4444sxdcf");
		
		testInput2.writeFile("4444sxdcf");
		testInput2.writeFile("000011111");
		testInput2.writeFile("111111111");
		testInput2.writeFile("4444sxdcf");
		

		//@formatter:off
		batchController.input(testInput1)
		               .input(testInput2)
		               .output(testOutput)
		               .logger("testCompareTaskWithoutOrder", "D:/BatchFromMars", Level.FINEST)
		               .compare((s) -> s, (s) -> s, (s1, s2) -> s1 + s2)
		               .execute();

		//@formatter:on

		assertFalse(testOutput.isEmpty());
		assertEquals("000011111000011111", testOutput.readFile());
		assertEquals("4444sxdcf4444sxdcf", testOutput.readFile());
		assertFalse(!testOutput.isEmpty());
	}
	
	@Test
	public void testCompareWithRealFile() throws UnsupportedEncodingException, FileNotFoundException, Exception{
		BatchController batchController=new BatchController();
		
		//@formatter:off
		batchController.input("D:/BatchFromMars/TestCompareComponent/compare1.txt", "BIG5")
		               .input("D:/BatchFromMars/TestCompareComponent/compare2.txt", "BIG5")
		               .output("D:/BatchFromMars/TestCompareComponent/compareResult.txt","UTF8", false)
		               .logger("testCompareWithRealFile", "D:/BatchFromMars/", Level.FINEST)
		               .compare((s)->s.substring(0, 4), (s)->s.substring(0, 4), (s1,s2)->s1+"###"+s2)
		               .map((s) -> s + "###" + new Date().toString())
		               .execute();
		               
		//@formatter:on
	}
}
