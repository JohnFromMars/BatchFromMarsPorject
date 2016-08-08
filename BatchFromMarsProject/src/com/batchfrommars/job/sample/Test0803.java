package com.batchfrommars.job.sample;

import com.batchfrommars.component.sample.JobTest0803Step1;
import com.batchfrommars.component.sample.JobTest0803Step2;
import com.batchfrommars.component.sample.JobTest0803Step3;
import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;
import com.batchfrommars.file.TemporaryFile;

public class Test0803 {

	public static void main(String[] args) throws InterruptedException {

		JobTest0803Step1 step1 = new JobTest0803Step1();
		FileInformation inputStep1 = new PhysicalFile("INPUT", "d://666.txt", "UTF8", false);
		FileInformation outputStep1 = new TemporaryFile(1000);
		step1.setInputFile(inputStep1);
		step1.setOutputFile(outputStep1);

		JobTest0803Step2 step2 = new JobTest0803Step2();
		FileInformation outputStep2 = new TemporaryFile(1000);
		step2.setInputFile(outputStep1);
		step2.setOutputFile(outputStep2);
		step2.setLastComponent(step1);

		JobTest0803Step3 step3 = new JobTest0803Step3();
		FileInformation outputStep3 = new PhysicalFile("OUTPUT", "d://test2.txt", "UTF8", true);
		step3.setInputFile(outputStep2);
		step3.setOutputFile(outputStep3);
		step3.setLastComponent(step2);

		step1.start();
		step2.start();
		step3.start();

		step3.join();
		step1.join();
		step2.join();
		
		System.out.println(step1.getCount());
		System.out.println(step2.getCount());
		System.out.println(step3.getCount());

		
	}

}
