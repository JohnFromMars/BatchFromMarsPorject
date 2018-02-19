package com.batchfrommars.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TempFile implements FileInformation {

	// File file = File.createTempFile(String.valueOf(this.hashCode()),
	// ".tempFile");
	// BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
	private File file;
	private String ioType;
	private BufferedWriter bWriter;
	private BufferedReader bReader;

	public TempFile(String ioType, String fileName) throws IOException {
		this.ioType = ioType;
		this.file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName + ".tmp");
		file.deleteOnExit();
		System.out.println(file.getPath());
		if (ioType.equals("INPUT")) {
			// this.file = File.
			bReader = new BufferedReader(new FileReader(file));
		} else if (ioType.equals("OUTPUT")) {
			// this.file = File.createTempFile(fileName, ".txt");
			bWriter = new BufferedWriter(new FileWriter(file));
		} else {
			UnsupportedOperationException exception = new UnsupportedOperationException(
					"TempFile.java ioType ERROR ioType must be INPUT or OUTPUT");
			throw exception;
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String readFile() throws Exception {
		// TODO Auto-generated method stub
		String s1 = null;
		if (!isEmpty()) {
			s1 = bReader.readLine();
		}
		return s1;
	}

	@Override
	public void writeFile(String data) throws Exception {
		// TODO Auto-generated method stub
		bWriter.write(data);
		bWriter.newLine();

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		try {
			return (!bReader.ready());

		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void closeFile() throws Exception {
		// TODO Auto-generated method stub
		if (ioType.equals("INPUT")) {
			bReader.close();
		} else if (ioType.equals("OUTPUT")) {
			bWriter.close();
		}

	}

	@Override
	public void deleteFile() throws Exception {
		// TODO Auto-generated method stub
		this.file.delete();
	}

}
