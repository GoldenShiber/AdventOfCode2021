package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import utils.DataHandling;

public class Day1 {
	
	private static long startTime; 
	private static long endTime;
	private static List<Integer> sonarList;
	private static String inputLine;

	
	public static int solutionPart1() {
		int previous = 0;
		boolean hasStarted = false;
		int increaseCounter = 0;
		
		for(int sonarSweep : sonarList) {
			if(hasStarted) {
				if(sonarSweep > previous) {
					increaseCounter++;
				}
				previous = sonarSweep;
			} else {
				hasStarted = true; 
				previous = sonarSweep;
			}
		}
		
		return increaseCounter;
	}
	
	public static int solutionPart2() {
		int previous = 0;
		int sonarSum = 0;
		boolean hasStarted = false;
		int increaseSumCounter = 0;
		List<Integer> subList = new ArrayList<>();
		int max = 0;
		
		for(int i = 1; i < sonarList.size(); i++) {
			max = (i == sonarList.size()-1)? max = sonarList.size()-2:i+2;
			subList = sonarList.subList(i-1, max);
			sonarSum = subList.stream().mapToInt(Integer::intValue).sum();
			if(hasStarted) {
				if(sonarSum > previous) {
					increaseSumCounter++;
				}
				previous = sonarSum;
			} else {
				hasStarted = true; 
				previous = sonarSum;
			}
		}
		
		return increaseSumCounter;
	}
	
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputfiles/inputDay1.txt");

		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		
		sonarList = new ArrayList<>();
		
        while ((inputLine = inFile.readLine()) != null) {
        	if (DataHandling.isNumeric(inputLine)){
        		sonarList.add(Integer.parseInt(inputLine));
        		}
        	 {
        	}
        	}
        startTime = System.nanoTime();
        System.out.println("The number of increases are: " +solutionPart1());
        endTime = System.nanoTime();
        System.out.println("Part one of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        startTime = System.nanoTime();
        System.out.println("The number of sum increases are: " +solutionPart2());        
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
}
