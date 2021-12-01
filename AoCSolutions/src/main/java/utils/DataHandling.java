package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
	
public class DataHandling {

	private static String inputLine;
	
	public static boolean isNumeric(String str) { 
	  try {  
	    Double.parseDouble(str);  
	    return true;
	  } catch(NumberFormatException e){  
	    return false;  
	  }  
	}

// Generic function to convert List of 
	// String to List of Integer 
	public static <T, U> List<U> 
	convertStringListToIntList(List<T> listOfString, 
	                           Function<T, U> function) 
	{ 
	    return listOfString.stream() 
	        .map(function) 
	        .collect(Collectors.toList()); 
	} 

	public static void printArrayList(ArrayList<String> list, int startindex, int endindex) {
		for (int i = startindex; i < endindex; i++) {
			System.out.println(list.get(i));
		}
	}
	
	
	public static boolean arrContains(final int[] arr, final int key) {
	    return Arrays.stream(arr).anyMatch(i -> i == key);
	}
	
	public static int minIndex (ArrayList<Integer> list) {
		  return list.indexOf(Collections.min(list)); }
	
	public static int maxWordLengthInList(ArrayList<String> inputString) {
		int max = 0;
		for(String word : inputString) {
			max = (word.length() > max) ? word.length() : max;
		}
		return max;
	}
	
	public static boolean stringContainsItemFromList(String inputStr, String[] items) {
	    return Arrays.stream(items).anyMatch(inputStr::contains);
	}
	
	public static ArrayList<String> readStringsBySeparator(String fileName) throws IOException{
		ArrayList<String> newList = new ArrayList<>();
		URL inputURL = DataHandling.class.getClassLoader().getResource(fileName);
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		while ((inputLine = inFile.readLine()) != null) {
        	inputLine = inputLine.replace(".", "");
        	newList.add(inputLine);
        }
		
		return newList;
	}
	
	public static ArrayList<String> readStringsBySeparatorRaw(String fileName) throws IOException{
		ArrayList<String> newList = new ArrayList<>();
		URL inputURL = DataHandling.class.getClassLoader().getResource(fileName);
		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		while ((inputLine = inFile.readLine()) != null) {
        	newList.add(inputLine);
        }
		
		return newList;
	}
	
	public static ArrayList<String> parallelList(ArrayList<ArrayList<String>> bigArrayList){
		ArrayList<String> testList = new ArrayList<>();
		String testString = "";
		String testXString = "";
		String testYString = "";
		if(bigArrayList.size() > 1) {
			for(int i = 0; i < bigArrayList.get(0).size(); i++) {
				testXString = bigArrayList.get(0).get(i) + testString;
				for(int j = 0; j < bigArrayList.get(1).size(); j++) {
					testYString =testXString + bigArrayList.get(1).get(j);
					if(bigArrayList.size() == 3) {
						for(int k = 0; k < bigArrayList.get(2).size(); k++) {
							testList.add(testYString + bigArrayList.get(2).get(k));
						} 
					} else {
						testList.add(testYString);
					}
				}
			}
			
		} else {
			if(bigArrayList.size() == 1) {
			testList = bigArrayList.get(0);
			}
		}
		return testList;
		
	}
	
}
