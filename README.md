# BatchFromMarsPorject 1.2.0
>This library provides some models of data processing that should make it easy to develop batch programs. Currently, it provides some simple models like sorting, file reading, and writing to help you reach specific data processing purposes. The idea of this library is making business logic and progress clear and maintainable like the code below. Please check the JavaDoc to get a complete overview of this library and to get an idea of which models you should use in your programs.

        batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
	                   .input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		           .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)
		           .sort("4,6,1,3")
		           .map((s) -> s.substring(0, 4))
		           .filter((s) -> s.equals("0000"))
		           .execute();

# Features 
Please check more sample code in this directory to get a complete overview of this library.

Sort Task
------------
The `sort` task sorts the data by simple orders which can be arranged easily.  Sample codes below are showing how to sort data with 2 sorting condiction (substring 4 to 6 of records sorted with descending order and 1 to 3 sorted with ascending one). 

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
The `map` task transforms data. It supports Lambda, making business logic clear. The codes below are demonstrating how to convert each data into its substring 0 to 4.

      
       batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		          .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)		 
		          .map((s) -> s.substring(0, 4))
		          .execute();

Filter Task
--------------
The `filter` task picks the data out with specifies condition which represented by Lambda. The codes below are illustrating how to pick the data equal to the string "0000".
      
       batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		          .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)		 
		          .filter((s) -> s.equals("0000"))
		          .execute();


Setting logger
--------------
The `logger` task allows you to set the properties of logger including log name, address and logging level. Just like the example below.

       batchController.logger("BatchControllerTest", "D:/BatchFromMars", LogeLevel.FINEST)
       

Execute the process
---------------
Ther are there ways to activate the process - execute, count and sum. Both of them start the process with different functions. The codes below are showing how to execute the batch controller normally.

       batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		          .output("D:/BatchFromMars/TestFooterAndHeader.txt", "BIG5", false)		 
		          .filter((s) -> s.equals("0000"))
		          .execute();
			  
The codes below are showing how to add the specified area of data(substring between 0 and 4) and get the sum of BigDecimal.

       BigDecimal decimal = batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		                               .logger("TestSum", "D:/BatchFromMars", LogeLevel.FINEST)
		                               .sum((s) -> s.substring(0, 4));

The codes below are showing how to count the number of data with certain condition whether substring 0 to 4 is greater than 1 and return Integer of the total number which confers to the condition.

       Integer count = batchController.input("D:/BatchFromMars/SortData/sort1.txt", "UTF8")
		                          .output("D:/BatchFromMars/SortData/test.txt", "UTF8")
		                          .logger("testCount2", "D:/BatchFromMars", LogeLevel.FINEST)
		                          .count((s) -> Integer.valueOf(s.substring(0, 4)) > 1);
