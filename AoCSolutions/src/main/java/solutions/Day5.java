package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.Pair;

public class Day5 {
	
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
		private int mValue = 0;
		private int length = 0;
		
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
		
		public int getKDirection() {
			return kDirection;
		}
		
		public int getMValue() {
			return mValue;
		}
		
		public int getLength() {
			return length;
		}
		
		public int calculateX(int y) {
			return (y-mValue)/kDirection;
		}
		
		public String getLineDirection() {
			if(from.getY() == to.getY()) {
				return "Horizontal";
			} else if(from.getX() == to.getX()) {
				return "Vertical";
			} else {
				return "Diagonal";
			}
		}
		
		public void updateCoordinates(){
			coordinates = new ArrayList<>();
			int start = 0;
			int end = 0;
			if(!lineDirection.equals("Diagonal")) {
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
			else {
				kDirection=(to.getY()-from.getY())/(to.getX()-from.getX());
				mValue = to.getY() - to.getX()*kDirection;
				length = Math.abs(to.getX()-from.getX());
				int[] xValues = {from.getX(), to.getX()};
				List<Integer> xCoordinates = IntStream.rangeClosed(Math.min(xValues[0], xValues[1]), Math.max(xValues[0], xValues[1])).boxed().collect(Collectors.toList());
				for(int xCoordinate : xCoordinates) {
					coordinates.add(mValue+xCoordinate*kDirection);
				}
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
		
		List<Integer> overlapseLines = new ArrayList<>();
		if(line1.getLineDirection().equals("Vertical")) {
			if(line1.getLineDirection().equals(line2.getLineDirection()) && line1.getFrom().getX()==line2.getFrom().getX()) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
			} else if(line2.getLineDirection().equals("Horizontal") && line2.getCoordinates().contains(line1.getFrom().getX())
					&& line1.getCoordinates().contains(line2.getFrom().getY())){
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> coord == line2.getFrom().getY()).toList();
			}
			for(int yCoord : overlapseLines) {
				addToMap(line1.getFrom().getX(), yCoord);
			}
		} else if(line1.getLineDirection().equals("Horizontal")) {
			if(line1.getLineDirection().equals(line2.getLineDirection()) && line1.getFrom().getY()==line2.getFrom().getY()) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
			} else if(line2.getLineDirection().equals("Vertical") && line2.getCoordinates().contains(line1.getFrom().getY())
					&& line1.getCoordinates().contains(line2.getFrom().getX())){
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> coord == line2.getFrom().getX()).toList();
			}
			for(int xCoord : overlapseLines) {
				addToMap(xCoord, line1.getFrom().getY());
			}
		}
	}
	
public static void lineOverlapseWithDiagonal(CoordinateLine line1, CoordinateLine line2) {
		
		List<Integer> overlapseLines = new ArrayList<>();
		if(line1.getLineDirection().equals("Vertical")) {
			if(line1.getLineDirection().equals(line2.getLineDirection()) && line1.getFrom().getX()==line2.getFrom().getX()) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
			} else if((line2.getLineDirection().equals("Horizontal")) && line2.getCoordinates().contains(line1.getFrom().getX())
					&& line1.getCoordinates().contains(line2.getFrom().getY())){
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> coord == line2.getFrom().getY()).toList();
			} else if(line2.getLineDirection().equals("Diagonal") ) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
				if(!overlapseLines.isEmpty()) {
					for(int y : overlapseLines) {
						if(line1.getFrom().getX() == line2.calculateX(y)) {
							addToMap(line2.calculateX(y), y);
							break;
						}
					}
				}
			}
			if(!overlapseLines.isEmpty() && !line2.getLineDirection().equals("Diagonal")) {
					for(int y : overlapseLines) {
						addToMap(line1.getFrom().getX(), y);
					}
			}
		} else if(line1.getLineDirection().equals("Horizontal")) {
			if(line1.getLineDirection().equals(line2.getLineDirection()) && line1.getFrom().getY()==line2.getFrom().getY()) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
			} else if(line2.getLineDirection().equals("Vertical") && line2.getCoordinates().contains(line1.getFrom().getY())
					&& line1.getCoordinates().contains(line2.getFrom().getX())){
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> coord == line2.getFrom().getX()).toList();
				}
			 else if(line2.getLineDirection().equals("Diagonal") && line2.getCoordinates().contains(line1.getFrom().getY()) && line1.coordinates.contains(line2.calculateX(line1.getFrom().getY()))) {
				addToMap(line2.calculateX(line1.getFrom().getY()), line1.getFrom().getY());
			}
			if(!overlapseLines.isEmpty()) {
				for(int x : overlapseLines) {
					addToMap(x, line1.getFrom().getY());
				}
		}
		} else if(line1.getLineDirection().equals("Diagonal")){
			if(line2.getLineDirection().equals("Vertical")) {
				overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
				if(!overlapseLines.isEmpty()) {
					for(int y : overlapseLines) {
						if(line2.getFrom().getX() == line1.calculateX(y)) {
							addToMap(line1.calculateX(y), y);
							break;
						}
					}
				}
			} else if(line2.getLineDirection().equals("Horizontal") && line1.getCoordinates().contains(line2.getFrom().getY()) && line2.coordinates.contains(line1.calculateX(line2.getFrom().getY()))) {
				addToMap(line1.calculateX(line2.getFrom().getY()), line2.getFrom().getY());
			} else if(line2.getLineDirection().equals("Diagonal")){
				if(line1.getKDirection()!= line2.getKDirection()) {
					overlapseLines = line1.getCoordinates().stream()
						.filter(coord -> line2.getCoordinates().contains(coord)).toList();
					if(!overlapseLines.isEmpty() ) {
						for(int y : overlapseLines) {
							if(line2.calculateX(y) == line1.calculateX(y)) {
								addToMap(line1.calculateX(y), y);
								break;
							}
						}
					}
				} else {
					if(line1.getMValue() == line2.getMValue()) {
					overlapseLines = line1.getCoordinates().stream()
							.filter(coord -> line2.getCoordinates().contains(coord)).toList();
					if(!overlapseLines.isEmpty()) {
						for(int y : overlapseLines) {
							addToMap(line1.calculateX(y), y);
						}
					}
					}
				}
			 }
	}
}
	
	public static void addToMap(int x, int y) {
		if(coordinateMap.get(y)== null) {
			Map<Integer, Integer> xCoordMap = new HashMap<>();
			coordinateMap.put(y, xCoordMap);
		}
		if(coordinateMap.get(y).get(x) == null) {
			coordinateMap.get(y).put(x, 1);
		} else {
			coordinateMap.get(y).put(x, coordinateMap.get(y).get(x)+1);
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
	
	public static int solutionPartTwo() {
		coordinateMap = new HashMap<>();
		var wrapper = new Object(){ int ordinal = 0; };
		for(int i = 0; i < coordinateLines.size()-1; i++) {
			for(int j = i+1; j < coordinateLines.size(); j++) {
				lineOverlapseWithDiagonal(coordinateLines.get(i), coordinateLines.get(j));
			}
		}
		coordinateMap.values().stream().forEach(m -> wrapper.ordinal+=m.values().size());
		return wrapper.ordinal;
	}
	
	public static void main(String[] args) throws IOException {
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
        System.out.println("The last winner has been drawn and the points are: " +solutionPartTwo());        
        endTime = System.nanoTime();
        System.out.println("Part two of the problem took "+ ((endTime-startTime)/100000) + " milliseconds");
        
        inFile.close();
	}
}
