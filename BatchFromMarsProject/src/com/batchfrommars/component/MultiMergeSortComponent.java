package com.batchfrommars.component;

import java.util.ArrayList;

import com.batchfrommars.file.FileInformation;
import com.batchfrommars.file.PhysicalFile;
import com.batchfrommars.file.TemporaryFile;

/**
 * 
 * input can only be ordered physical files with any amount
 * output can be physical or temporary file with any amount
 * 
 * 
 * @author JohnFromMars
 * @date 2016年8月22日
 * @remark 2016年8月22日
 */
public abstract class MultiMergeSortComponent extends ComponentII {
	private ArrayList<Object> ObjectList;
	private ArrayList<MergeSortComponent> componentList;

	protected abstract String getSortKey(String inputData);

	protected abstract int getMethod();

	public MultiMergeSortComponent() {

		ObjectList = new ArrayList<>();
		componentList = new ArrayList<>();
	}

	protected void act() {
		try {
			mergeSortFile();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void mergeSortFile() throws InterruptedException {

		int inputCount = inputFileList.size();

		if (inputFileList.size() % 2 != 0) {
			inputCount--;
		}

		for (int i = 0; i < inputCount; i += 2) {
			MergeSortComponent mergeSortComponent = newMergeSortComponent();
			mergeSortComponent.addInputFileInformation(inputFileList.get(i), inputFileList.get(i + 1));
			ObjectList.add(mergeSortComponent);
			componentList.add(mergeSortComponent);
		}

		if (inputFileList.size() % 2 != 0) {
			System.out.println("ObjectList.add(inputFileList.getLast()");
			ObjectList.add(inputFileList.getLast());
		}

		while (ObjectList.size() > 1) {
			System.out.println("objectlist " + ObjectList.size());
			generateComponent(ObjectList.size());
		}

		componentList.get(componentList.size() - 1).setOutputFileList(this.outputFileList);

		System.out.println("merge sort amount :" + componentList.size());
		for (MergeSortComponent item : componentList) {
			System.out.println("input " + item.inputFileList);
			System.out.println("output " + item.outputFileList);
			System.out.println("last component " + item.getLastComponentList());
			item.start();
		}

		for (MergeSortComponent item : componentList) {
			item.join();
		}

		inputFileList.closeFile();

	}

	private MergeSortComponent newMergeSortComponent() {

		return new MergeSortComponent() {

		
			protected int getMergeSortMethod() {
				// TODO Auto-generated method stub
				return getMethod();
			}

			
			protected String getKey(String data) {
				// TODO Auto-generated method stub
				return getSortKey(data);
			}
		};
	}

	private void generateComponent(int size) {

		int tempSize = size;
		ArrayList<Object> tempObjectList = new ArrayList<>();
		// 用肌術偶數去分，肌術-1
		if (size % 2 != 0) {
			tempSize--;
		}
		System.out.println(ObjectList.get(0).getClass().getGenericSuperclass());
		System.out.println(ObjectList.get(1).getClass());
		// System.out.println(ObjectList.get(2).getClass().getGenericSuperclass());
		System.out.println(PhysicalFile.class);
		for (int i = 0; i < tempSize; i += 2) {
			if (ObjectList.get(i).getClass().getGenericSuperclass().equals(MergeSortComponent.class)
					&& ObjectList.get(i + 1).getClass().getGenericSuperclass().equals(MergeSortComponent.class)) {

				FileInformation temp1 = new TemporaryFile();
				FileInformation temp2 = new TemporaryFile();
				MergeSortComponent mergeSortComponent = newMergeSortComponent();
				MergeSortComponent tempSorrt1 = (MergeSortComponent) ObjectList.get(i);
				MergeSortComponent tempSorrt2 = (MergeSortComponent) ObjectList.get(i + 1);
				tempSorrt1.addOutputFileInformation(temp1);
				tempSorrt2.addOutputFileInformation(temp2);
				mergeSortComponent.addInputFileInformation(temp1, temp2);
				mergeSortComponent.addLastComponent(tempSorrt1, tempSorrt2);
				tempObjectList.add(mergeSortComponent);
				componentList.add(mergeSortComponent);
				System.out.println("generate 1 merge type1");

			} else if (ObjectList.get(i).getClass().getGenericSuperclass().equals(MergeSortComponent.class)
					&& ObjectList.get(i + 1).getClass().equals(PhysicalFile.class)) {

				FileInformation temp1 = new TemporaryFile();
				FileInformation temp2 = (FileInformation) ObjectList.get(i + 1);
				MergeSortComponent mergeSortComponent = newMergeSortComponent();
				MergeSortComponent tempSorrt1 = (MergeSortComponent) ObjectList.get(i);
				tempSorrt1.addOutputFileInformation(temp1);
				mergeSortComponent.addInputFileInformation(temp1, temp2);
				mergeSortComponent.addLastComponent(tempSorrt1);
				tempObjectList.add(mergeSortComponent);
				componentList.add(mergeSortComponent);
				System.out.println("generate 1 merge type2");

			}
		}

		if (size % 2 != 0) {
			tempObjectList.add(ObjectList.get(tempSize));
		}

		ObjectList.clear();
		ObjectList = tempObjectList;

	}

}
