package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
	
	private static long startTime; 
	private static long endTime;
	private static List<String> commandList;
	private static String inputLine;
	private static int horizontal;
	private static int depth;
	private static int aim;

	
	public static int solutionPart1() {
		horizontal = 0;
		depth = 0;
		
		for(String uboatCommand : commandList) {
			
			String[] command = uboatCommand.split(" ");
			String action = command[0];
			int actionValue = Integer.parseInt(command[1]);
			if(action.equals("forward")) {
				horizontal += actionValue;
			} else {
				depth += (action.equals("up"))? actionValue:-actionValue;
			}
		}
		
		return horizontal*depth;
	}
	
	public static int solutionPart2() {
		horizontal = 0;
		depth = 0;
		aim = 0;
		
		for(String uboatCommand : commandList) {
			String[] command = uboatCommand.split(" ");
			String action = command[0];
			int actionValue = Integer.parseInt(command[1]);
			if(action.equals("forward")) {
				horizontal += actionValue;
				depth += actionValue*aim;
			} else {
				aim += (action.equals("up"))? -actionValue:actionValue;
			}
		}
		
		return horizontal*depth;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day1.class.getClassLoader().getResource("inputfiles/inputDay2.txt");

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
