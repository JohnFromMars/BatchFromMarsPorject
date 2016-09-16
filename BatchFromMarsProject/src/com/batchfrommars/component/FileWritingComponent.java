package com.batchfrommars.component;

import java.util.ArrayList;

/**
 * this component can only be used in sort component
 * 
 * @author JohnFromMars
 * @date 2016年8月27日
 * @remark 2016年8月27日
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
			outputFileList.closeFile();
		}
	

}
