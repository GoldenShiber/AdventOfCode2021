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

import utils.Pair;

public class Day5 {
	
	private static long startTime; 
	private static long endTime;
	private static String inputLine;
	private static List<CoordinateLine> coordinateLines;
	private static List<Pair> usedPairList;

	static class CoordinateLine{
		private Pair from;
		private Pair to;
		
		public CoordinateLine() {
			from = new Pair();
			to = new Pair();
		}
		
		public CoordinateLine(Pair from, Pair to) {
			this.from = from;
			this.to = to;
		}
		
		public String getLineDirection() {
			if(from.getX() == to.getX()) {
				return "Horizontal";
			} else if(from.getY() == to.getY()) {
				return "Vertical";
			} else {
				return "Diagoanl";
			}
		}
		
		public Pair getFrom() {
			return from;
		}
		
		public Pair getTo() {
			return to;
		}
	}
	
	public static List<Pair> getCoordinateList(CoordinateLine line) {
		List<Pair> coordinates = new ArrayList<>();
		Pair from = line.getFrom();
		Pair to = line.getTo();
		int start = 0;
		int end = 0;
		if(line.getLineDirection().equals("Horizontal")) {
			start = Math.min(from.getY(), to.getY());
			end = Math.max(from.getY(), to.getY());
			
			for(int i = start; i<=end; i++) {
				coordinates.add(new Pair(from.getX(), i));
			}
		}	else if(line.getLineDirection().equals("Vertical")) {
			start = Math.min(from.getX(), to.getX());
			end = Math.max(from.getX(), to.getX());
			
			for(int i = start; i<=end; i++) {
				coordinates.add(new Pair(i, from.getY()));
			}
		}
		return coordinates;
	}
	
	// Just make a collection solution lmao :
	
	public static int lineOverlapse(CoordinateLine line1, CoordinateLine line2) {
		int overlapsingPoints = 0;
		List<Pair> line1Pairs = getCoordinateList(line1);
		List<Pair> line2Pairs = getCoordinateList(line2);
		for(Pair line1Pair : line1Pairs) {
			if(!usedPairList.contains(line1Pair)) {
				for(Pair line2Pair : line2Pairs) {
					if(line1Pair.equals(line2Pair)) {
						usedPairList.add(line1Pair);
						overlapsingPoints++;
						break;
					}
				}
			}

			
		}
		
		return overlapsingPoints;
	}
	
	public static int solutionPartOne() {
		usedPairList = new ArrayList<>();
		int overLapLines = 0;
		for(int i = 0; i < coordinateLines.size()-1; i++) {
			for(int j = i+1; j < coordinateLines.size(); j++) {
				overLapLines+=lineOverlapse(coordinateLines.get(i), coordinateLines.get(j));
			}
		}
		return overLapLines;
	}
	
	public static void main(String[] args) throws IOException {
		URL inputURL = Day5.class.getClassLoader().getResource("inputfiles/inputDay5.txt");
		
		coordinateLines = new ArrayList<>();

		BufferedReader inFile = new BufferedReader(
				new InputStreamReader(inputURL.openStream()));
		
        while ((inputLine = inFile.readLine()) != null) {
        	String[] pairInformation = inputLine.split(" -> ");
        	String[] fromPairInformation = pairInformation[0].split(",");
        	String[] toPairInformation = pairInformation[1].split(",");
        	Pair from = new Pair(Integer.valueOf(fromPairInformation[0]), Integer.valueOf(fromPairInformation[1]));
        	Pair to = new Pair(Integer.valueOf(toPairInformation[0]), Integer.valueOf(toPairInformation[1]));
        	coordinateLines.add(new CoordinateLine(from, to));
        	}
        startTime = System.nanoTime();
        // 7436 is wrong
        System.out.println("The first winner has been drawn and the points are: " +solutionPartOne());
        endTime = System.nanoTime();
        System.out.println("Part one of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        startTime = System.nanoTime();
//        System.out.println("The last winner has been drawn and the points are: " +solutionPart2());        
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
	
}
