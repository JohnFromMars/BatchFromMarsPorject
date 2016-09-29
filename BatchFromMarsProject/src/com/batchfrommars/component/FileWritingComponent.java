package com.batchfrommars.component;

import java.util.ArrayList;



/**
 * Notice that this component can only be used in sort component
 * 
 * @author JohnFromMars
 * @date 2016-09-17
 */
public abstract class FileWritingComponent extends ComponentII {

	protected abstract ArrayList<String> getWritingData();

	@Override
	protected void act() {
		
			ArrayList<String> dataList = new ArrayList<>();
			dataList = getWritingData();

//			System.out.println("in file writing data list size :" + dataList.size());

			for (String item : dataList) {
				outputFileList.writeToAllFile(item);
			}
			dataList.clear();
		}
	

}
