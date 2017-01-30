package com.batchfrommars.test.beans;

import java.util.LinkedHashMap;

import com.batchfrommars.component.FixedParser;

public class TestBean extends FixedParser {
	private String s1;
	private String s2;
	private String s3;
	private String s4;

	@Override
	protected LinkedHashMap<String, Integer> getFields() {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("s1", 9);
		

		return map;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

	public String getS3() {
		return s3;
	}

	public void setS3(String s3) {
		this.s3 = s3;
	}

	public String getS4() {
		return s4;
	}

	public void setS4(String s4) {
		this.s4 = s4;
	}

	@Override
	public String toString() {
		return "TestBean [s1=" + s1 + ", s2=" + s2 + ", s3=" + s3 + ", s4=" + s4 + "]";
	}
}
