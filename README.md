# BatchFromMarsPorject 1.1.2
>This libarary provides some models of data processing that shoud make it easy to develop batch programs. Currently, it provides some simple models like sorting, comparing, merging, file reading, and writing to help you reach the specific data processing purposes. Please check the javadoc to get a complete overview of this library and to get an idea of which models you should use in your programs.

# Example
Please check more sample code in this dierctory to get a complete overview of this library.

Sorting data
------------
Extend `SortComponent` calss and implement `getInputKey(String inputData)` to get specific key from data and `getSortMethod()` to decide ascending or descending order. Sample code below are showing how to sort data with 2 sorting condiction.

	@Override
	protected ArrayList<Object> getKeys(String inputData) {
		ArrayList<Object> keyTable = new ArrayList<>();
		keyTable.add(Integer.valueOf(inputData.substring(2, 6)));
		keyTable.add(inputData.substring(0, 1));
		return keyTable;
	}

	@Override
	protected ArrayList<Integer> getOrders() {
		ArrayList<Integer> sortMethod = new ArrayList<>();
		sortMethod.add(DESCESNDING);
		sortMethod.add(ASCESNDING);
		return sortMethod;
	}

Comparing data
--------------
Extend `CompareComponent` class and implement `getKeyFromInput1(String inputData)` to indicate the key form input 1, `getKeyFromInput2(String inputData)` to definit the key from input 2 and `getResultFormat(String inputData1, String inputData2)` to specify the format of the matching data you intend to output, then you can create a simple compare process. The sample code below tring to comapre two data and output format is input 1 + input 2. 

	@Override
	protected Object getKeyFromInput1(String inputData) {
		
		return inputData.substring(0,6);
	}

	@Override
	protected Object getKeyFromInput2(String inputData) {
		
		return inputData.substring(0,6);
	}

	@Override
	protected String getResultFormat(String inputData1, String inputData2) {

		return inputData1 + "," + inputData2;
	}

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
