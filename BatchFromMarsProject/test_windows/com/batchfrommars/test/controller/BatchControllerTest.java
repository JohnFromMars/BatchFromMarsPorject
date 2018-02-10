 package com.batchfrommars.test.controller;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.QueueFile;
import com.batchfrommars.util.LogLevel;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class BatchControllerTest {

	@Test
	public void testSort() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TTEESST1.txt", "BIG5", false)
				       .sort("4,6,1,3,d")
				       .execute();
	}
	
	@Test(expected = SyntaxException.class)  
	public void testSort2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogLevel.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TTEESST2.txt", "BIG5", false)
				       .sort("4,6,1,3,e")
				       .execute();
		
	}
	
//	@Test
	public void testSort3() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("testSort3", "D:/BatchFromMars", LogLevel.INFO)
				       .input("D:/BatchFromMars/SortData/sort3.txt", "UTF8")
				       .output("D:/BatchFromMars/SortData/TTEESST2.txt", "BIG5", false)
				       .sort("1,10,A")
				       .execute();
		
	}
	
	@Test
	public void testMultiController() throws Exception {
		BatchController step1 = new BatchController();
		BatchController step2 = new BatchController();
		BatchController step3 = new BatchController();
		
		FileInformation testInput1 = new QueueFile();
		FileInformation testInput2 = new QueueFile();

		testInput1.writeFile("1111xxxxx");
		testInput1.writeFile("4444sxdcf");
		testInput1.writeFile("000002222");
		testInput1.writeFile("000011111");
		testInput2.writeFile("4444sxdcf");
		
		testInput2.writeFile("4444sxdcf");
		testInput2.writeFile("000011111");
		testInput2.writeFile("111111111");
		testInput2.writeFile("4444sxdcf");

		
		step1.logger("testMultiController", "D:/BatchFromMars", LogLevel.INFO)
		     .input(testInput1)
		     .output("D:/BatchFromMars/multiController/multiCom1.txt", "UTF8", false)
		     .map(s->s+"###001")
	         .execute();
		
		step2.logger("testMultiController", "D:/BatchFromMars", LogLevel.INFO)
		     .input(testInput2)
		     .output("D:/BatchFromMars/multiController/multiCom2.txt", "UTF8", false)
		     .map(s->s+"###002")
		     .execute();
		
		step3.logger("testMultiController", "D:/BatchFromMars", LogLevel.INFO)
		     .input("D:/BatchFromMars/multiController/multiCom1.txt", "UTF8")
		     .input("D:/BatchFromMars/multiController/multiCom2.txt", "UTF8")
		     .output("D:/BatchFromMars/multiController/multiCom3.txt", "UTF8",false)
		     .compare(s->s.substring(0, 4), s->s.substring(0, 4), (s1,s2)->s1+","+s2)
		     .execute();
		
		
	}

}
