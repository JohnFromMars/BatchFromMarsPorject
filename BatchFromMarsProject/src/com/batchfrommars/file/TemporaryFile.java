package com.batchfrommars.file;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public class TemporaryFile implements FileInformation {
	// queue buffer
	private BlockingQueue<String> buffer;

	private String tempFileName;
	// constant area
	private static int DEFAULT_ALLOCATE = 300;
	private static int DEFAULT_WAITING_TIME = 5;

	public TemporaryFile(int allocate) {
		buffer = new ArrayBlockingQueue<String>(allocate);
		tempFileName = this.getClass().getGenericInterfaces().getClass().getSimpleName();
	}

	public TemporaryFile() {
		buffer = new ArrayBlockingQueue<String>(DEFAULT_ALLOCATE);
		tempFileName = String.valueOf(this.getClass().getSimpleName() + " " + this.hashCode());
	}

	@Override
	public String readFile() throws InterruptedException {

		String data = buffer.poll(DEFAULT_WAITING_TIME, TimeUnit.MILLISECONDS);
		return data;
	}

	@Override
	public void writeFile(String data) throws InterruptedException {

		if (data != null) {
			buffer.put(data);
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

	@Override
	public String toString() {
		return "TemporaryFile [tempFileName=" + tempFileName + "]";
	}

}
