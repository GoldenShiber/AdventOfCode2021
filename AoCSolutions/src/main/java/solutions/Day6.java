package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day6 {
	
	private static long startTime; 
	private static long endTime;
	private static String inputLine;
	private static List<Integer> integerList;
	private static List<Integer> newFisheList;
	private static List<Integer> newIntegerList;
	
	public static int fishLogic(int fish) {
		if(fish == 0) {
			newFisheList.add(8);
			return 6;
		}
		return fish-1;
	}
	
	public static void populateFishList() {
		newFisheList = new ArrayList<>();
		newIntegerList = integerList.stream()
				.map(f -> fishLogic(f)).collect(Collectors.toList());
		integerList = Stream.concat(newIntegerList.stream(), newFisheList.stream()).collect(Collectors.toList());
	}
	
	public static List<Integer> populateSpecificFishList(List<Integer> fishList) {
		newFisheList = new ArrayList<>();
		newIntegerList = fishList.stream()
				.map(f -> fishLogic(f)).collect(Collectors.toList());
		 return Stream.concat(newIntegerList.stream(), newFisheList.stream()).collect(Collectors.toList());
	}

	public static int solutionOne(int days) {
		for(int i = 0; i<days; i++) {
			populateFishList();
		}
		
		return integerList.size();
	}
	
	public static Long smartSolutionTwo(int days, List<Integer> fishList) {
		var wrapper = new Object(){ int ordinal = 0; };
		ArrayList<Long> fishSmartList = new ArrayList<>(9);
		for(int i = 0; i<9; i++) {
			fishSmartList.add((long) fishList.stream().filter(f -> f == wrapper.ordinal).count());
			wrapper.ordinal++;
		}
		for(int j = 0; j < days; j++) {
			long fishReproducing = fishSmartList.get(0);
			fishSmartList.set(0,(long) 0);
			ArrayList<Long> newSmartList=new ArrayList<Long>(Collections.nCopies(9,(long) 0));
			for(int k = 0; k<8; k++) {
				newSmartList.set(k, fishSmartList.get(k+1));
			}
			newSmartList.set(8, fishReproducing);
			newSmartList.set(6, newSmartList.get(6) + fishReproducing);
			fishSmartList = newSmartList;
		}
		
		return fishSmartList.stream().mapToLong(Long::longValue)
				.sum();
	}
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day5.class.getClassLoader().getResource("inputfiles/inputDay6.txt");
		List<String> openingList = new ArrayList<>();
		

		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		
        while ((inputLine = inFile.readLine()) != null) {
        		Collections.addAll(openingList,inputLine.split(","));
        	}
        integerList = openingList.stream().map(Integer::parseInt).collect(Collectors.toList());
        startTime = System.nanoTime();
        
        System.out.println("There are total of: " +solutionOne(80) + " fishes after 80 days.");
        endTime = System.nanoTime();
        System.out.println("Part one of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        integerList = openingList.stream().map(Integer::parseInt).collect(Collectors.toList());
        startTime = System.nanoTime();
        System.out.println("There are total of: " +smartSolutionTwo(256, integerList)+" after 256 days");        
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
}
