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
import java.util.Map;

import utils.Pair;

public class Day5Better {
	
	private static long startTime; 
	private static long endTime;
	private static String inputLine;
	private static List<CoordinateLine> coordinateLines;
	private static Map<Integer, Map<Integer, Integer>> coordinateMap;

	static class CoordinateLine{
		private Pair from;
		private Pair to;
		private List<Integer> coordinates = new ArrayList<>();
		private String lineDirection;
		private int kDirection = 0;
		
		public CoordinateLine() {
			from = new Pair();
			to = new Pair();
			lineDirection = getLineDirection();
			updateCoordinates();
		}
		
		public CoordinateLine(Pair from, Pair to) {
			this.from = from;
			this.to = to;
			lineDirection = getLineDirection();
			updateCoordinates();
		}
		
		public String getLineDirection() {
			if(from.getY() == to.getY()) {
				return "Horizontal";
			} else if(from.getX() == to.getX()) {
				return "Vertical";
			} else {
				kDirection=(to.getY()-from.getY())/(to.getX()-from.getX());
				return "Diagonal";
			}
		}
		
		public void updateCoordinates(){
			coordinates = new ArrayList<>();
			int start = 0;
			int end = 0;
			if(lineDirection.equals("Horizontal")) {
				start = Math.min(from.getX(), to.getX());
				end = Math.max(from.getX(), to.getX());
			}	else if(lineDirection.equals("Vertical")) {
				start = Math.min(from.getY(), to.getY());
				end = Math.max(from.getY(), to.getY());
			}
			for(int i = start; i<=end; i++) {
				coordinates.add(i);
				
			}
		}
		
		public Pair getFrom() {
			return from;
		}
		
		public Pair getTo() {
			return to;
		}
		
		public List<Integer> getCoordinates(){
			return coordinates;
		}
	}
	
	public static void lineOverlapse(CoordinateLine line1, CoordinateLine line2) {
		
		//TODO if both lines are of same line: check if: 
		// If vertical -> X values is the same, go on and check. 
		// If Horizontal -> Y values is the same, go on and check 
		// Check in used locations if value != 0, Map(Map(X coord, Map(Y coord, amount)) if != 0 add overlapse, if not don't
		// if lineDirection are not equal ONLY check one time, if they are same, check for all the potential coordinats.
		List<Integer> overlapseLines = new ArrayList<>();
		if(line1.getLineDirection().equals("Vertical")) {
			if(line1.getLineDirection().equals(line2.getLineDirection()) && line1.getFrom().getX()==line2.getFrom().getX()) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
			} else if(!line1.getLineDirection().equals(line2.getLineDirection()) && line2.getCoordinates().contains(line1.getFrom().getX())
					&& line1.getCoordinates().contains(line2.getFrom().getY())){
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> coord == line2.getFrom().getY()).toList();
			}
			for(int yCoord : overlapseLines) {
				if(coordinateMap.get(yCoord)== null) {
					Map<Integer, Integer> xCoordMap = new HashMap<>();
					coordinateMap.put(yCoord, xCoordMap);
				}
				if(coordinateMap.get(yCoord).get(line1.getFrom().getX()) == null) {
					coordinateMap.get(yCoord).put(line1.getFrom().getX(), 1);
				} else {
					coordinateMap.get(yCoord).put(line1.getFrom().getX(), coordinateMap.get(yCoord).get(line1.getFrom().getX())+1);
				}
			}
		} else if(line1.getLineDirection().equals("Horizontal")) {
			if(line1.getLineDirection().equals(line2.getLineDirection()) && line1.getFrom().getY()==line2.getFrom().getY()) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
			} else if(!line1.getLineDirection().equals(line2.getLineDirection()) && line2.getCoordinates().contains(line1.getFrom().getY())
					&& line1.getCoordinates().contains(line2.getFrom().getX())){
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> coord == line2.getFrom().getX()).toList();
			}
			for(int xCoord : overlapseLines) {
				if(coordinateMap.get(line1.getFrom().getY())== null) {
					Map<Integer, Integer> xCoordMap = new HashMap<>();
					coordinateMap.put(line1.getFrom().getY(), xCoordMap);
				}
				if(coordinateMap.get(line1.getFrom().getY()).get(xCoord) == null) {
					coordinateMap.get(line1.getFrom().getY()).put(xCoord, 1);
				} else {
					coordinateMap.get(line1.getFrom().getY()).put(xCoord, coordinateMap.get(line1.getFrom().getY()).get(xCoord)+1);
				}
			}
		}
	}
	
	public static int solutionPartOne() {
		var wrapper = new Object(){ int ordinal = 0; };
		for(int i = 0; i < coordinateLines.size()-1; i++) {
			for(int j = i+1; j < coordinateLines.size(); j++) {
				lineOverlapse(coordinateLines.get(i), coordinateLines.get(j));
			}
		}
		coordinateMap.values().stream().forEach(m -> wrapper.ordinal+=m.values().size());
		return wrapper.ordinal;
	}
	
	public static void main(String[] args) throws IOException {
//		URL inputURL = Day5.class.getClassLoader().getResource("inputfiles/testInputDay5.txt");
		URL inputURL = Day5.class.getClassLoader().getResource("inputfiles/inputDay5.txt");
		
		coordinateLines = new ArrayList<>();
		coordinateMap = new HashMap<>();

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
        
        System.out.println("The first winner has been drawn and the points are: " +solutionPartOne());
        endTime = System.nanoTime();
        System.out.println("Part one of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        startTime = System.nanoTime();
//        System.out.println("The last winner has been drawn and the points are: " +solutionPart2());        
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
	// 6256 is too low
	// 7436 is wrong , too high
}
