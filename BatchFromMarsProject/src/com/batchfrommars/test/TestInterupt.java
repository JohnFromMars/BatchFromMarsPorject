package com.batchfrommars.test;

import com.batchfrommars.component.sample.JobTest0803Step1;
import com.batchfrommars.file.FileInformtion;
import com.batchfrommars.file.PhysicalFile;
import com.batchfrommars.file.TemporaryFile;

public class TestInterupt {

	public static void main(String[] args) {
		
		JobTest0803Step1 step1 = new JobTest0803Step1();
		FileInformtion inutStep1 = new TemporaryFile();
		FileInformtion outputStep1= new PhysicalFile("OUTPUT", "D://TESTINTERUPT.txt", "UTF8", false);
		step1.setInputFile(inutStep1);
		step1.setOutputFile(outputStep1);
		
		step1.start();

	}

}
