package com.batchfrommars.component.sample;

import com.batchfrommars.component.BatchComponent;

public class JobTest0803Step2 extends BatchComponent {
	private int count = 0;

	protected String excuteProcess(String data) {
		
			count++;
		
		
//		System.out.println(this.getClass().getSimpleName() +" "+ count);
		return data ;
	}

	public int getCount() {
		return count;
	}
	
	

}
