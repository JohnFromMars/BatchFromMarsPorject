package com.batchfrommars.util;

public class SortObject {
	private Integer start;
	private Integer end;
	private Integer order;

	public SortObject(int start, int end, int order) {
		this.start = start;
		this.end = end;
		this.order = order;
	}

	public SortObject() {

	}

	public Integer getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "SortObject [start=" + start + ", end=" + end + ", order=" + order + "]";
	}

}
