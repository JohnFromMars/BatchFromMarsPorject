package com.batchfrommars.util;

import java.util.logging.Logger;

import com.batchfrommars.component.MergeSortComponent;

public class OriginalMergeArrangement {

	public void arrangeMergeTask() {
		MergeSortComponent mergeSortComponent = createMergeSortComponent();
	}

	private MergeSortComponent createMergeSortComponent() {
		MergeSortComponent mergeSortComponent = new MergeSortComponent() {

			@Override
			protected Logger getLoggger() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected int getMergeSortMethod() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			protected Object getKey(String data) {
				// TODO Auto-generated method stub
				return null;
			}
		};

		return mergeSortComponent;
	}

}
