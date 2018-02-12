package com.batchfrommars.file;

import java.util.ArrayList;
import java.util.List;

public class ListFile implements FileInformation {
	private List<String> strings;

	public ListFile(List<String> strings) {
		this.strings = strings;
	}

	public ListFile() {
		strings = new ArrayList<>();
	}

	@Override
	public String readFile() throws Exception {
		String data = strings.get(0);
		strings.remove(0);
		return data;
	}

	@Override
	public void writeFile(String data) throws Exception {
		strings.add(data);
	}

	@Override
	public boolean isEmpty() {
		return strings.isEmpty();
	}

	@Override
	public void closeFile() throws Exception {
	}

	@Override
	public void deleteFile() throws Exception {
	}

}
