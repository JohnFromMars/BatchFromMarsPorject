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

public class PhysicalFile implements FileInformation {
	// member area
	private String ioType;
	private String filePath;
	private String encoding;
	private boolean append;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	// constant area
	public static final String OUTPUT = "OUTPUT";
	public static final String INPUT = "INPUT";
	public static final String OPERATE = "OPERATE";

	public PhysicalFile(String ioType, String filePath, String encoding, boolean append) {

		this.ioType = ioType;
		this.filePath = filePath;
		this.encoding = encoding;
		this.append = append;

		try {
			if (ioType.equals(INPUT)) {

				this.bufferedReader = new BufferedReader(
						new InputStreamReader(new FileInputStream(filePath), encoding));
			} else if (ioType.equals(OUTPUT)) {
				this.bufferedWriter = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(filePath, append), encoding));
			}

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String readFile() {
		String data = null;
		if (!isEmpty()) {
			try {
				data = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	public void writeFile(String data) {
		if (data != null) {

			try {
				this.bufferedWriter.write(data);
				this.bufferedWriter.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isEmpty() {
		try {
			return (!bufferedReader.ready());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public void closeFile() {
		if (this.ioType.equals(INPUT)) {
			try {
				// this.scanner.close();
				this.bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (this.ioType.equals(OUTPUT)) {
			try {
				this.bufferedWriter.flush();
				this.bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void deleteFile() {
		// System.out.println(filePath+" to delete");
		// File file = new File(filePath);
		//
		// System.out.println(file.delete());
		Path path = Paths.get(filePath);
		try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
