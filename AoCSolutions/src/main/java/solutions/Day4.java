package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Day4 {
	
	private static List<Integer> currentBingoNumberList;
	private static List<Integer> bingoNumberList;
	private static List<Integer> bingoLineCheckList;
	private static List<List<Integer>> bingoBoardList;
	private static List<List<Integer>> checkedNumberList;
	private static long startTime; 
	private static long endTime;
	private static String inputLine;

	public static boolean isBingo(List<Integer> bingoList, List<Integer> bingoIndexes) {
		for(int bingoIndex : bingoLineCheckList) {
			if(!currentBingoNumberList.contains(bingoList.get(bingoIndex))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkBingo(List<Integer> bingoList, List<Integer> checkedNumberList,  int number) {
		//Different checks depending where the number exists. 
		int index = bingoList.indexOf(number);
		
		if(index > -1) {
			checkedNumberList.add(number);
			
			//Diagonals checks for either sets of divideable by 4 or 6 
//			if(index%4 == 0) {
//				bingoLineCheckList = List.of(4, 8, 12, 16, 20);
//				if(isBingo(bingoList, bingoLineCheckList)) {
//					return true;
//				}
//			}
//			
//			if(index%6 == 0) {
//				bingoLineCheckList = List.of(0, 6, 12, 18, 24);
//				if(isBingo(bingoList, bingoLineCheckList)) {
//					return true;
//				}
//			}
			// Horisontal
			int horisontMult = ((int) index/5)*5;
			bingoLineCheckList = List.of(horisontMult, horisontMult+1, horisontMult+2, horisontMult+3, horisontMult+4);
			if(isBingo(bingoList, bingoLineCheckList)) {
				return true;
			}
			//Vertical
			int verticalBase = index%5;
			bingoLineCheckList = List.of(verticalBase, verticalBase+5, verticalBase+10, verticalBase+15, verticalBase+20);
			if(isBingo(bingoList, bingoLineCheckList)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	
	public static int solutionPart1() {
		currentBingoNumberList = new ArrayList<>();
		checkedNumberList.stream().forEach(c -> c.clear());
		for(int drawnNumber : bingoNumberList) {
			currentBingoNumberList.add(drawnNumber);
			for(int j = 0; j<bingoBoardList.size(); j++) {
				boolean bingoCheck = checkBingo(bingoBoardList.get(j), checkedNumberList.get(j), drawnNumber);
				if(bingoCheck) {
					int index = j;
					List<Integer> filterList = bingoBoardList.get(index).stream()
							.filter(c -> !checkedNumberList.get(index).contains(c)).toList();
					return filterList.stream().mapToInt(Integer::intValue).sum() * drawnNumber;
				}
			}
		}
		return 0;
	}
	
	public static int solutionPart2() {
		List<Integer> finishedBricks = new ArrayList<Integer>();
		checkedNumberList.stream().forEach(c -> c.clear());
		currentBingoNumberList = new ArrayList<>();
		int bingoValue = 9001;
		while(finishedBricks.size() < bingoBoardList.size()) {
			for(int drawnNumber : bingoNumberList) {
				currentBingoNumberList.add(drawnNumber);
				for(int j = 0; j<bingoBoardList.size(); j++) {
					if(!finishedBricks.contains(j)) {
						boolean bingoCheck = checkBingo(bingoBoardList.get(j), checkedNumberList.get(j), drawnNumber);
						if(bingoCheck) {
							bingoValue = drawnNumber;
							finishedBricks.add(j);
					}
					}
				}
				
			}
		}
		int index = finishedBricks.get(finishedBricks.size()-1);
		List<Integer> filterList = bingoBoardList.get(index).stream()
					.filter(c -> !checkedNumberList.get(index).contains(c)).toList();
		return filterList.stream().mapToInt(Integer::intValue).sum() * bingoValue;
	}
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputfiles/inputDay4.txt");

		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		
		ArrayList<Integer> tempBingoBrick = new ArrayList<>();
		bingoBoardList = new ArrayList<>();
		checkedNumberList = new ArrayList<>();
		
		boolean firstLineRead = false;
        while ((inputLine = inFile.readLine()) != null) {
        	if(!firstLineRead) {
        		String[] bingoNumbers = inputLine.split(",");
        		int[] bingoNumeros = Stream.of(bingoNumbers).mapToInt(Integer::parseInt).toArray();
        		Integer[] boxedArray = Arrays.stream(bingoNumeros) // IntStream
                        .boxed()                // Stream<Integer>
                        .toArray(Integer[]::new);
        		bingoNumberList = Arrays.asList(boxedArray);
        		firstLineRead = true;
        	} else {
        			if(inputLine.isEmpty() && tempBingoBrick.isEmpty()) {
        				
        			}
        			else if(!inputLine.isEmpty() ) {
        				String[] bingoBrickNumbers = inputLine.trim().replace("  ", " ").split(" ");
                		int[] bingoBrickNumeros = Stream.of(bingoBrickNumbers).mapToInt(Integer::parseInt).toArray();

        				Integer[] numberArray = Arrays.stream(bingoBrickNumeros) // IntStream
                                .boxed()                // Stream<Integer>
                                .toArray(Integer[]::new);
        				Collections.addAll(tempBingoBrick, numberArray);
        				
        			} else {
                		bingoBoardList.add(tempBingoBrick);
                		checkedNumberList.add(new ArrayList<>());
                		tempBingoBrick = new ArrayList<>();
        			}
        		}
        	}
        
        bingoBoardList.add(tempBingoBrick);
		checkedNumberList.add(new ArrayList<>());
		tempBingoBrick = new ArrayList<>();
        startTime = System.nanoTime();
        
        System.out.println("The first winner has been drawn and the points are: " +solutionPart1());
        endTime = System.nanoTime();
        System.out.println("Part one of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        startTime = System.nanoTime();
        System.out.println("The last winner has been drawn and the points are: " +solutionPart2());        
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
	
}
