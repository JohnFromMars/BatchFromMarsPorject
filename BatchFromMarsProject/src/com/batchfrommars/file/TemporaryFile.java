package com.batchfrommars.file;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TemporaryFile implements FileInformation {
	private BlockingQueue<String> buffer;

	public TemporaryFile(int allocate) {
		buffer = new ArrayBlockingQueue<String>(allocate);
	}

	public TemporaryFile() {
		buffer = new ArrayBlockingQueue<String>(2000);
	}

	public String readFile() {
		String data = null;
		try {
			data = buffer.poll(5, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			System.out.println("interuptted");
		}
		return data;
	}

	public void writeFile(String data) {
		if (data != null) {
			
			try {
				buffer.put(data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEmpty() {
		return buffer.isEmpty();
	}

	public void closeFile() {
	}

}
