package com.batchfrommars.component;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class JobExcutor {
	private LinkedHashMap<Component, Integer> jobList;

	public JobExcutor() {
		jobList = new LinkedHashMap<Component, Integer>();
	}

	public void addJob(Component component) {
		jobList.put(component, 0);
	}

	public void excuteJobs() {
		for (Entry<Component, Integer> entry : jobList.entrySet()) {
			entry.getKey().start();
		}
	}

	public boolean isJobsRunning() {
		boolean isRuning = false;
		for (Entry<Component, Integer> entry : jobList.entrySet()) {
			isRuning = (isRuning || entry.getKey().isLastComponentRunning());
		}
		return isRuning;

	}

	public void closeAllFiles() {
		for (Entry<Component, Integer> entry : jobList.entrySet()) {
			entry.getKey().getInputFile().closeFile();
			entry.getKey().getOutputFile().closeFile();
		}
	}

	public void submit() {
		excuteJobs();
        System.out.println(isJobsRunning());
		while (isJobsRunning()) {
			for (Entry<Component, Integer> entry : jobList.entrySet()) {
				if (entry.getKey().getInputFile().isEmpty() && entry.getKey().outputFile.isEmpty()
						&& entry.getKey().isLastComponentRunning()) {
					System.out.println(
							entry.getKey().getName() + " input isempty: " + entry.getKey().getInputFile().isEmpty()
									+ " output is empty: " + entry.getKey().outputFile.isEmpty() + " last componenet"
									+ entry.getKey().isLastComponentRunning());
					entry.getKey().interrupt();
				}
			}
		}

		System.out.println("submit all");
		closeAllFiles();
	}

}
