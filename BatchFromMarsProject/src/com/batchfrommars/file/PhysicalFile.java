package com.batchfrommars.file;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class PhysicalFile implements FileInformtion {
	private String ioType;
	private String filePath;
	private String encoding;
	private boolean append;
	private Scanner scanner;
	private BufferedWriter bufferedWriter;

	public PhysicalFile(String ioType, String filePath, String encoding, boolean append) {
		this.ioType = ioType;
		this.filePath = filePath;
		this.encoding = encoding;
		this.append = append;

		try {
			if (ioType.equals("INPUT")) {
				this.scanner = new Scanner(new FileInputStream(filePath), encoding);
			} else if (ioType.equals("OUTPUT")) {
				this.bufferedWriter = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(filePath, append), encoding));
			}

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String readFile() {
		String data = this.scanner.nextLine();
		return data;
	}

	public void writeFile(String data) {
		try {
			this.bufferedWriter.write(data);
			this.bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isEmpty() {

		return (!scanner.hasNextLine());
	}

	public void closeFile() {
		if (this.ioType.equals("INPUT")) {
			this.scanner.close();
		} else if (this.ioType.equals("OUTPUT")) {
			try {
				this.bufferedWriter.flush();
				this.bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
