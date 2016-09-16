package com.batchfrommars.file;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TemporaryFile implements FileInformation {
	// queue buffer
	private BlockingQueue<String> buffer;
	// constant area
	private static int DEFAULT_ALLOCATE = 2000;
	private static int DEFAULT_WAITING_TIME = 5;

	public TemporaryFile(int allocate) {
		buffer = new ArrayBlockingQueue<String>(allocate);
	}

	public TemporaryFile() {
		buffer = new ArrayBlockingQueue<String>(DEFAULT_ALLOCATE);
	}

	@Override
	public String readFile() {
		String data = null;
		try {
			data = buffer.poll(DEFAULT_WAITING_TIME, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public void writeFile(String data) {
		if (data != null) {

			try {
				buffer.put(data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isEmpty() {
		return buffer.isEmpty();
	}

	@Override
	public void closeFile() {
	}

	@Override
	public void deleteFile() {
	}

}
