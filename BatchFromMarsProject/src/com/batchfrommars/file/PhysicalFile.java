package com.batchfrommars.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public class PhysicalFile implements FileInformation {
	// member area
	private String ioType;
	private String filePath;
	private String encoding;
	private boolean append;
	private boolean closed;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	// constant area
	public static final String OUTPUT = "OUTPUT";
	public static final String INPUT = "INPUT";
	public static final String OPERATE = "OPERATE";

	public PhysicalFile(String ioType, String filePath, String encoding, boolean append)
			throws UnsupportedEncodingException, FileNotFoundException {

		this.ioType = ioType;
		this.filePath = filePath;
		this.encoding = encoding;
		this.append = append;
		this.closed = false;

		if (ioType.equals(INPUT)) {
			this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));

		} else if (ioType.equals(OUTPUT)) {
			this.bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(filePath, append), encoding));
		}

	}

	@Override
	public String readFile() throws IOException {
		String data = null;

		if (!isEmpty()) {
			data = bufferedReader.readLine();
		}

		return data;
	}

	@Override
	public void writeFile(String data) throws IOException {

		if (data != null) {
			this.bufferedWriter.write(data);
			this.bufferedWriter.newLine();
		}
	}

	@Override
	public boolean isEmpty() {
		try {
			return (!bufferedReader.ready());

		} catch (IOException e) {
			return false;
		}

	}

	@Override
	public void closeFile() throws IOException {

		if (this.ioType.equals(INPUT) && closed != true) {
			this.bufferedReader.close();
			closed = true;

		} else if (this.ioType.equals(OUTPUT) && closed != true) {
			this.bufferedWriter.flush();
			this.bufferedWriter.close();
			closed = true;
		}
	}

	@Override
	public void deleteFile() throws IOException {
		Path path = Paths.get(filePath);
		Files.delete(path);
	}

	public String getIoType() {
		return ioType;
	}

	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public String toString() {
		return "PhysicalFile [ioType=" + ioType + ", filePath=" + filePath + ", encoding=" + encoding + ", append="
				+ append + "]";
	}
}
