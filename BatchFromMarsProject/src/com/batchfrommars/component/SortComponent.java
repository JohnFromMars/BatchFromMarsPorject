package com.batchfrommars.component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.batchfrommars.file.FileList;
import com.batchfrommars.util.CompareUtil;

/**
 * Extend SortComponent and implement getKeys() and getOrders(), then this class
 * can be able to work.
 * 
 * @author user
 *
 */
public abstract class SortComponent extends ComponentII {
	protected final static int ASCESNDING = 1;
	protected final static int DESCESNDING = -1;

	protected abstract ArrayList<Object> getKeys(String data);

	protected abstract ArrayList<Integer> getOrders();

	// public SortComponent(Logger logger, ArrayList<Integer> orders,
	// ArrayList<Object> keys) {
	// super();
	// }
	//
	// public SortComponent() {
	// super();
	// }

	@Override
	protected void act() throws Exception {
		File tempFile = null;

		if (inputFileList.size() != 1) {
			logger.severe("inputFileList.size() should be 1 but was " + inputFileList.size());

		} else {

			for (int i = 0; i < getSortRound(); i++) {

				List<File> files = sortFileBatch(inputFileList, i, tempFile);
				tempFile = mergeFiles(files, i);

				if (i == getLastRound()) {
					doOutput(tempFile, outputFileList);
				}
			}
		}
	}

	/**
	 * This method sort input file
	 * 
	 * @param fileList
	 * @param comparator
	 * @return
	 * @throws Exception
	 */
	public List<File> sortFileBatch(FileList fileList, int i, File file) throws Exception {
		logger.finest("In sortFileBatch which round=" + (i + 1) + ", total round=" + getSortRound());

		long blockSize = estimateSizeOfBlock();
		long currentBlockSize = 0;
		List<File> files = new ArrayList<File>();
		List<String> tmpList = new ArrayList<>();
		BufferedReader bReader = getBufferReader(i, file);

		// first round, read the data from inputFileList
		logger.finest("Checking condition isEmpty()=" + isEmpty(i, fileList, bReader)
				+ ", isSomeLastComponentsRunning()=" + isSomeLastComponentsRunning());

		while (!isEmpty(i, fileList, bReader) || isSomeLastComponentsRunning()) {

			// String data = fileList.get(0).readFile();
			String data = readFile(i, fileList, bReader);

			if (data != null) {
				tmpList.add(data);
				currentBlockSize += data.length();
				logger.finest("data != null, adding data to tmplist, currentblocksize=" + currentBlockSize);
			}

			if (currentBlockSize >= blockSize) {
				files.add(sortAndSaveFile(tmpList, getComparator(i)));
				currentBlockSize = 0;
				blockSize = estimateSizeOfBlock();
				tmpList.clear();
			}
		}

		// if tmplist still got some data
		if (tmpList.size() > 0) {
			files.add(sortAndSaveFile(tmpList, getComparator(i)));
			tmpList.clear();
		}

		return files;
	}

	/**
	 * 
	 * @param sortList
	 * @param comparator
	 * @return
	 * @throws IOException
	 */
	public File sortAndSaveFile(List<String> sortList, Comparator<String> comparator) throws IOException {
		// create temp file
		File file = File.createTempFile(String.valueOf(this.hashCode()), ".tempFile");
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		logger.finest("Temp file " + file.getAbsolutePath() + " has been created.");
		file.deleteOnExit();

		// sort the list
		Collections.sort(sortList, comparator);
		logger.finest("List has been sorted");

		// write out the list to temp file
		try {

			for (String s : sortList) {
				logger.finest("Write out Sting=" + s + " to temp file.");
				bWriter.write(s);
				bWriter.newLine();
			}

		} finally {
			logger.finest("Closing bufferWriter...");
			bWriter.close();
		}

		return file;
	}

	/**
	 * 
	 * @param files
	 * @param x
	 * @return
	 * @throws IOException
	 */
	public File mergeFiles(List<File> files, int x) throws IOException {

		if (files.size() == 0) {

			logger.finest("files.size() == 0, return an empty file");
			File file = File.createTempFile(String.valueOf(this.hashCode()), ".tempFile");
			file.deleteOnExit();
			return file;

		} else if (files.size() == 1) {

			logger.finest("files.size() == 1, return files.get(0)");
			return files.get(0);

		} else if (files.size() == 2) {

			logger.finest("files.size() == 2, return merge(files.get(0), files.get(1), x)");
			return merge(files.get(0), files.get(1), x);

		} else {

			logger.finest("files.size() > 2, devide file list and merge sublist");
			return merge(mergeFiles(files.subList(0, files.size() / 2), x),
					mergeFiles(files.subList(files.size() / 2, files.size()), x), x);
		}
	}

	/**
	 * 
	 * @param file1
	 * @param file2
	 * @param i
	 * @return
	 * @throws IOException
	 */
	public File merge(File file1, File file2, int i) throws IOException {
		logger.finest("In merge method, which file1=" + file1.getAbsolutePath() + ", file2=" + file2.getAbsolutePath());

		final int BUFFER_SIZE = 2048;
		int compare = 0;

		File file = File.createTempFile(String.valueOf(this.hashCode()), ".tempFile");
		file.deleteOnExit();

		BufferedReader bReader1 = new BufferedReader(new FileReader(file1), BUFFER_SIZE);
		BufferedReader bReader2 = new BufferedReader(new FileReader(file2), BUFFER_SIZE);
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));

		try {

			String s1 = bReader1.readLine();
			String s2 = bReader2.readLine();

			while (file1 != null && file2 != null) {

				if (s1 == null && s2 == null) {
					break;

				} else if (s1 != null && s2 != null) {
					compare = CompareUtil.compare(getKeys(s1).get(i), getKeys(s2).get(i)) * getOrders().get(i);

				} else if (s1 == null && s2 != null) {
					compare = 1;

				} else if (s1 != null && s2 == null) {
					compare = -1;
				}

				if (compare <= 0) {
					bWriter.write(s1);
					bWriter.newLine();
					logger.finest("Write out string s1=" + s1);
					s1 = bReader1.readLine();

				} else if (compare > 0) {
					bWriter.write(s2);
					bWriter.newLine();
					logger.finest("Write out string s2=" + s2);
					s2 = bReader2.readLine();
				}
			}

		} finally {
			bReader1.close();
			bReader2.close();
			bWriter.close();
			file1.delete();
			file2.delete();
		}

		return file;
	}

	/**
	 * 
	 * @return
	 */
	public long estimateSizeOfBlock() {
		long blockSize = 0;
		long maxSize = Runtime.getRuntime().maxMemory() / 3;
		long minSize = Runtime.getRuntime().freeMemory();

		if (minSize < maxSize) {
			blockSize = (maxSize + minSize) / 2;

		} else {
			blockSize = maxSize;
		}

		logger.info("EstimateSizeOfBlock, block size=" + blockSize + ",  max block size=" + maxSize
				+ ", min block size=" + minSize);

		return blockSize;
	}

	/**
	 * 
	 * @param i
	 * @param fileList
	 * @param bufferedReader
	 * @return
	 * @throws IOException
	 */
	public boolean isEmpty(int i, FileList fileList, BufferedReader bufferedReader) throws IOException {

		if (i == 0) {
			return fileList.get(0).isEmpty();

		} else {
			return !bufferedReader.ready();
		}
	}

	/**
	 * if it is first round,read from inputFileList.if not, read from temp file
	 * 
	 * @param i
	 * @param fileList
	 * @param bufferedReader
	 * @return
	 * @throws Exception
	 */
	public String readFile(int i, FileList fileList, BufferedReader bufferedReader) throws Exception {

		if (i == 0) {
			return fileList.get(0).readFile();

		} else {
			return bufferedReader.readLine();
		}
	}

	/***
	 * 
	 * @param i
	 * @param fileList
	 * @param bufferedReader
	 * @throws Exception
	 */
	public void writeFile(int i, FileList fileList, BufferedWriter bufferedWriter, String data) throws Exception {

		if (i == getLastRound()) {
			fileList.get(0).writeFile(data);

		} else {
			bufferedWriter.write(data);
			bufferedWriter.newLine();
		}
	}

	/**
	 * 
	 * @param file
	 * @param fileList
	 * @throws Exception
	 */
	public void doOutput(File file, FileList fileList) throws Exception {
		logger.finest("doOutput start...fileList=" + fileList.toString());

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String s = null;

		try {
			while (reader.ready()) {
				s = reader.readLine();
				fileList.writeToAllFile(s);
				logger.finest("doOutput write out string=" + s + " to fileList");
			}

		} finally {
			reader.close();
			logger.finest("doOutput finish...");
		}
	}

	/**
	 * This method return comparator with getKeys and getMethods.
	 * 
	 * @param i
	 * @return
	 */
	public Comparator<String> getComparator(int i) {

		Comparator<String> comparator = new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return CompareUtil.compare(getKeys(o1).get(i), getKeys(o2).get(i)) * getOrders().get(i);
			}
		};

		return comparator;
	}

	/**
	 * 
	 * @param i
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	public BufferedReader getBufferReader(int i, File file) throws FileNotFoundException {
		if (i == 0) {
			return null;

		} else if (i != 0 && file != null) {
			logger.finest("Round=" + (i + 1) + " return BufferReader which file=" + file.getAbsolutePath());
			return new BufferedReader(new FileReader(file));

		} else {
			return null;
		}
	}

	/**
	 * this method return how many round sort to do
	 * 
	 * @return
	 */
	public int getSortRound() {
		return getOrders().size();
	}

	/**
	 * this method return last round number
	 * 
	 * @return
	 */
	public int getLastRound() {
		return getOrders().size() - 1;
	}
}
