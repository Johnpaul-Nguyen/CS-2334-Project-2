import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TripPoint {
	private double lat;
    private double lon;
    private int time;
    private static ArrayList<TripPoint> trip = new ArrayList<TripPoint>();

    public TripPoint(int time, double lat, double lon){
        this.time = time;
        this.lat = lat;
        this.lon = lon;
        trip.add(this);
    }

    public int getTime(){
        return time;
    }

    public double getLat(){
        return lat; 
    }

    public double getLon(){
        return lon;
    }

    public static ArrayList<TripPoint> getTrip(){
    	ArrayList<TripPoint> result = new ArrayList<TripPoint>();
    	
    	for(int i = 0; i < trip.size(); i++) {
    		result.add(trip.get(i));
    	}
    	
    	return result;
    }

    public static double haversineDistance(TripPoint a, TripPoint b){
    	final double EARTH_RADIUS = 6371.0;
    	
    	double haversine;
    	double firstPart;
    	double secondPart;
    	double sqrt;
    	double arcsin;
    	
    	firstPart = Math.pow(Math.sin(Math.toRadians((b.getLat() - a.getLat()) / 2)), 2.0) * Math.pow(Math.cos(Math.toRadians((b.getLon() - a.getLon()) / 2)), 2.0);
    	secondPart = Math.pow(Math.cos(Math.toRadians((b.getLat() + a.getLat()) / 2)), 2.0) * Math.pow(Math.sin(Math.toRadians((b.getLon() - a.getLon()) / 2)), 2.0);
    	sqrt = Math.sqrt(firstPart + secondPart);
    	arcsin = Math.asin(sqrt);
    	haversine = 2 * EARTH_RADIUS * arcsin;
    	
    	return haversine;
    	
    }

    public static double avgSpeed(TripPoint a, TripPoint b){
    	
    	double result = 0.0;
    	double timeTot = 0.0;
    	
    	if (a.getTime() < b.getTime()){    	
    		timeTot = (double) (b.getTime() - a.getTime()) / 60.0;
    	}
    	else {
    		timeTot = (double) (a.getTime() - b.getTime()) / 60.0;
    	}
    	
    	result = haversineDistance(a, b) / timeTot;
    	
    	return result;
    }

    public static double totalTime(){
        double time = 0.0;

        for (int i = 0; i < trip.size() - 1; i++){
        	time = time + trip.get(i + 1).getTime() - trip.get(i).getTime();
        }

        double resultTime = time / 60.0;
        
        return resultTime;
    }

    public static double totalDistance(){
    	double totalDis = 0.0;
        double disBetween;
        
        for (int i = 0; i < trip.size() - 1; i++) {
        	disBetween = haversineDistance(trip.get(i), trip.get(i + 1));
        	
        	totalDis = totalDis + disBetween;
        }
        return totalDis;
    }

    public static void readFile(String filename) throws FileNotFoundException{
    	Scanner sc = new Scanner(new File(filename));
        int time;
        double lat;
        double lon;
        
        sc.nextLine();
        
        while(sc.hasNext()) {
	        
        	String line = sc.nextLine();
        	int firstComma = line.indexOf(",");
        	int secondComma = line.lastIndexOf(",");
        	
        	String strTime = "";
        	String strLat = "";
        	String strLon = "";
        	
        	for (int i = 0; i < firstComma; i++) {
        		strTime = strTime + line.charAt(i);	
        	}
        	for (int i = firstComma + 1; i < secondComma; i++) {
        		strLat = strLat + line.charAt(i);
        	}
        	for (int i = secondComma + 1; i < line.length(); i++) {
        		strLon = strLon + line.charAt(i);
        	}
        	
	        time = Integer.parseInt(strTime);
	        lat = Double.parseDouble(strLat);
	        lon = Double.parseDouble(strLon);
                
	        new TripPoint(time, lat, lon);
	        
        }
        
        sc.close();
    }
}
