package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import utils.TextHandling;

public class Day3 {
	
	private static long startTime; 
	private static long endTime;
	private static ArrayList<String> commandList;
	private static String inputLine;
	private static int gammaRate; 
	private static int epsilonRate;

	
	public static int solutionPart1() {
		gammaRate = 0; 
		epsilonRate = 0;
		
		ArrayList<String> binaryStrings = new ArrayList<String>(Collections.nCopies(commandList.get(0).length(), ""));
		
		
		for(String binaryString : commandList) {
			for(int j = 0; j < binaryStrings.size(); j++) {
				binaryStrings.set(j,binaryStrings.get(j) + binaryString.charAt(j));
			}
		}
		String gammaRateString = "";
		String epsilonRateString = "";
		for(String column : binaryStrings) {
			Character maxOccurentChar = TextHandling.getMaxOccuringChar(column);
			gammaRateString += maxOccurentChar;
			epsilonRateString += (maxOccurentChar == '1')? '0' : '1';
		}
		
		
		gammaRate = Integer.parseInt(gammaRateString, 2);
		epsilonRate = Integer.parseInt(epsilonRateString, 2);
		
		
		return gammaRate*epsilonRate;
	}
	
	
	public static Character occurentBinaryCharacter(String occurentMethod, HashMap<String, String> binaryMap) {
		String binaryChunk = "";
		Character mostOccurent = '1';
		
		
		for(String binaryString : binaryMap.values()) {
			binaryChunk += binaryString.charAt(0);
		}
		long oneAmounts = TextHandling.getOccuranceOfCharacter(binaryChunk, '1');
		long zeroAmounts = TextHandling.getOccuranceOfCharacter(binaryChunk, '0');
		if(oneAmounts == zeroAmounts) {
			return (occurentMethod.equals("max"))? '1' : '0';
		}
		
		mostOccurent = (oneAmounts > zeroAmounts)? '1' : '0';
		
		if(occurentMethod.equals("max")) {
			return mostOccurent;
		} else {
			return (mostOccurent == '1')? '0' : '1';
		}
	}
	
	public static String filterOnCharacter(String method, ArrayList<String> binaryLists) {
		HashMap<String, String> binaryMap = new HashMap<>();
		
		String binaryFinalMapKey = "";
		
		for(String bineryString : binaryLists) {
			binaryMap.put(bineryString, bineryString);
		}
		
		while(binaryMap.size()>1) {
			Character targetCharacter = occurentBinaryCharacter(method, binaryMap);
			ArrayList<String> mapKeys = new ArrayList<String>(binaryMap.keySet());
			for(String binaryMapKey : mapKeys) {
				String binaryMapValue = binaryMap.get(binaryMapKey);
				if(binaryMapValue.charAt(0)==targetCharacter) {
					binaryMap.put(binaryMapKey, binaryMapValue.substring(1));
					binaryFinalMapKey = binaryMapKey;
				} else {
					binaryMap.remove(binaryMapKey);
				}
			}
		}
		
		return binaryFinalMapKey;
	}
	
	public static int solutionPart2() {
		String oxygenGeneratorRating = filterOnCharacter("max", commandList);
		String co2ScrubbingRating = filterOnCharacter("min", commandList);
		
		int oxygenRate = Integer.parseInt(oxygenGeneratorRating, 2);
		int co2nRate = Integer.parseInt(co2ScrubbingRating, 2);
		
		return oxygenRate*co2nRate;
	}
	
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputfiles/inputDay3.txt");

		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		
		commandList = new ArrayList<>();
		
        while ((inputLine = inFile.readLine()) != null) {
        	commandList.add((inputLine));
        	}
        startTime = System.nanoTime();
        System.out.println("The multiplication of depth and horizontal is: " +solutionPart1());
        endTime = System.nanoTime();
        System.out.println("Part one of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        startTime = System.nanoTime();
        System.out.println("The multiplication of depth and horizontal is: " +solutionPart2());        
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
}
