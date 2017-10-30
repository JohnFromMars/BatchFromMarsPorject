package com.batchfrommars.test.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.batchfrommars.controller.BatchController;

public class BatchControllerTest {

	@Test
	public void test() throws UnsupportedEncodingException, FileNotFoundException {
		BatchController batchController = new BatchController() {
		};
		
		batchController.addInput("c://ss/s/s.txt", "UTF8")
		               .addOutput("c://ss/s/s.txt", "UTF8", true)
		               .filter()
		               .mapping()
		               .sort()
		               .addLogger("C://zz/zz/")
		               .execute();
	}

}
