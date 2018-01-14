# BatchFromMarsPorject 1.2.0
>This libarary provides some models of data processing that shoud make it easy to develop batch programs. Currently, it provides some simple models like sorting, file reading, and writing to help you reach the specific data processing purposes. The idea of this library is making business logic and progress clear and maintainable like the code below. Please check the javadoc to get a complete overview of this library and to get an idea of which models you should use in your programs.

        batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
	                   .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		           .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)
		           .sort("4,6,1,3")
		           .map((s) -> s.substring(0, 4))
		           .filter((s) -> s.equals("0000"))
		           .execute();

# Example
Please check more sample code in this dierctory to get a complete overview of this library.

Sort Task
------------
The `sort` task sorts the datas by simple orders which can be arranged easily.  Sample code below are showing how to sort data with 2 sorting condiction (substring 4 to 6 of records sorted with descending order and 1 to 3 sorted with ascending one). 

       batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		          .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)
		          .sort("4,6,D,1,3,A")
		          .execute();

If the sorting orders are not specified, they will be assigned as ascending orders by default. Just like the code below (both substring 4 to 6 and 1 to 3 of records sorted with ascending order).


       batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		          .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)
		          .sort("4,6,1,3")
		          .execute();

Map Task
--------------
Extend `CompareComponent` class and implement `getKeyFromInput1(String inputData)` to indicate the key form input 1, `getKeyFromInput2(String inputData)` to definit the key from input 2 and `getResultFormat(String inputData1, String inputData2)` to specify the format of the matching data you intend to output, then you can create a simple compare process. The sample code below tring to comapre two data and output format is input 1 + input 2. 

      
	batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		           .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)		 
		           .map((s) -> s.substring(0, 4))
		           .execute();


Merging data
------------
Extend `MergeSortComponent`class can reach the datas merging purpose. `MergeSortComponent` can merge two data and `MultiMergeSortComponent` can merge more than two. You should implement 'getMethod()' and `getSortKey(String inputData)` then you can create a merging process. 

			@Override
			protected int getMergeSortMethod() {
				return ASCENDING;
			}

			@Override
			protected String getKey(String inputData) {
				return inputData.substring(0, 2);
			}  

Data processing
---------------
Extend BatchComponentII class then you can create a simple process that could easily deal the data. Implement `excuteProcess(LinkedList<String> dataList)` and define how you intend to deal the data. Notice that dataList should always be checked that it is null or not. Sample code below is tring to use the stock ID from data to check the stock price through YahooFinanceAPI and output as a new format.

	@Override
	protected LinkedList<String> excuteProcess(LinkedList<String> dataList) {
		LinkedList<String> outputList = new LinkedList<>();
		String output1 = null;
	
		if (dataList.get(0) != null) {
		  String stockID = dataList.get(0).subString(0 , 4);
		  Stock stock = YahooFinance.get(stockID);
		  output1 = dataList.get(0)+","+ stock.getQuote().getPrice();
		} 
		
		outputList.add(output1);
		return outputList;
	}
