 package com.batchfrommars.test.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;

import java.util.logging.Logger;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.LogUtil;
import com.batchfrommars.file.QueueFile;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;

public class BatchControllerTest {

	@Test
	public void testSort() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", Level.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TTEESST1.txt", "BIG5", false)
				       .sort("4,6,1,3,d")
				       .execute();
	}
	
	@Test(expected = SyntaxException.class)  
	public void testSort2() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("BatchControllerTest", "D:/BatchFromMars", Level.FINEST)
				       .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
				       .output("D:/BatchFromMars/TTEESST2.txt", "BIG5", false)
				       .sort("4,6,1,3,e")
				       .execute();
		
	}
	
//	@Test
	public void testSort3() throws Exception {
		BatchController batchController = new BatchController() {
		};

		batchController.logger("testSort3", "D:/BatchFromMars", Level.INFO)
				       .input("D:/BatchFromMars/SortData/sort3.txt", "UTF8")
				       .output("D:/BatchFromMars/SortData/TTEESST2.txt", "BIG5", false)
				       .sort("1,10,A")
				       .execute();
		
	}
	
	@Test
	public void testMultiController() throws Exception {
		
		Logger logger = LogUtil.getLogger("testMultiController", "D:/BatchFromMars", Level.INFO);
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

		
		step1.logger(logger)
		     .input(testInput1)
		     .output("D:/BatchFromMars/multiController/multiCom1.txt", "UTF8", false)
		     .map(s->s+"###001")
	         .execute();
		
		step2.logger(logger)
		     .input(testInput2)
		     .output("D:/BatchFromMars/multiController/multiCom2.txt", "UTF8", false)
		     .map(s->s+"###002")
		     .execute();
		
		step3.logger(logger)
		     .input("D:/BatchFromMars/multiController/multiCom1.txt", "UTF8")
		     .input("D:/BatchFromMars/multiController/multiCom2.txt", "UTF8")
		     .output("D:/BatchFromMars/multiController/multiCom3.txt", "UTF8",false)
		     .compare(s->s.substring(0, 4), s->s.substring(0, 4), (s1,s2)->s1+","+s2)
		     .execute();
		
		
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
	
	@Test
	public void testPhysicalFileWithHeaderAndFooter() throws Exception {
		BatchController batchController = new BatchController() {
		};
		//@formatter:off
		batchController.logger("testPhysicalFileWithHeaderAndFooter", "D:/BatchFromMars", Level.FINEST)
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
		batchController.logger("testPhysicalFileWithHeaderAndFooter", "D:/BatchFromMars", Level.FINEST)
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
		batchController.logger("testPhysicalFileWithfooter", "D:/BatchFromMars", Level.FINEST)
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
		batchController.logger("testNoInput", "D:/BatchFromMars", Level.FINEST)
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
		batchController.logger("testNoInput2", "D:/BatchFromMars", Level.FINEST)
			 	       .output("D:/BatchFromMars/TestNoInput2.txt", "BIG5", false)
			 	       .sort("4,6,1,3")
			 	       .header(header)
				       .footer("footer...\r\noh oh oh!")
				       .map((s) -> s + "cc")
				       .filter((s) -> s.equals("0000"))
				       .execute();
		//@formatter:on
	}

}
