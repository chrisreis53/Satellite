package satellite;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import predict4java.*;


public class SatelliteDB {
 	
	ArrayList<Satellite> satelliteDB = new ArrayList<Satellite>();
	boolean databaseSet = false;
	GroundStationPosition groundstation;
	final static GroundStationPosition ALBUQUERQUE = new GroundStationPosition(35.0873191, -106.6376107, 5500, "Albuquerque");
	final static GroundStationPosition STORMLAKE = new GroundStationPosition(42.6436, 95.2019, 1440,"Storm Lake"); 
	
	public boolean downloadTLE(String in_url, String file){
		
		URL url;
		
		try {
			url = new URL(in_url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		PrintWriter writer = null;
		try {
				writer = new PrintWriter(file, "UTF-8");
			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			System.out.println("Data from: " + url);
			while ((inputLine = in.readLine()) != null){
				//System.out.println(inputLine);
				writer.println(inputLine);
			}
			in.close();
			writer.close();
			return true;
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.err.println("Unsupported Encoding...");
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public void TLEReader(String file){
		
		//TODO
		FileReader in = null;
		try {
			in = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(in);
	    
	    String line;
	    
	    try {
			while((line = br.readLine()) != null) {
//				if(line.startsWith("1") || line.startsWith("2")){
//					System.out.println("Uhhhh this shouldn't happen");
//				}else{
//					System.out.println(line);
//				}
				String[] TLEs = {line,br.readLine(),br.readLine()};
				TLE data = new TLE(TLEs);
				satelliteDB.add(SatelliteFactory.createSatellite(data));
//	        System.out.println(line);
//	        System.out.println(br.readLine());
//	        System.out.println(br.readLine());
			}
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void setGroundstation(GroundStationPosition gPos){
		groundstation = gPos;
		}
	
	public void getSatellites_GPS(){
		downloadTLE("https://celestrak.com/NORAD/elements/gps-ops.txt","gps.txt");
		TLEReader("gps.txt");
		databaseSet = true;
	}
	
	public void getSatellites_Stations(){
		downloadTLE("https://celestrak.com/NORAD/elements/stations.txt","stations.txt");
		TLEReader("stations.txt");
		databaseSet = true;
	}
	
	public void getSatellites_NOAA(){
		downloadTLE("https://celestrak.com/NORAD/elements/noaa.txt","noaa.txt");
		TLEReader("noaa.txt");
		databaseSet = true;
	}
	
	public int getSatIndex(String sat){
		
		for(int i=0;i<satelliteDB.size();i++){
			if(sat.equals(satelliteDB.get(i).getTLE().getName())){
				return i;
			}
		}
		
		return -1;
		
	}
	
	public int getSize(){
		return satelliteDB.size();
	}

	public Satellite getSat(int selectedSatellite) {
		return satelliteDB.get(selectedSatellite);
	}
	
	public boolean satExist(String string){
		int selectedSatellite = this.getSatIndex(string);
		if(selectedSatellite>=0){
			//System.out.println(this.getSat(selectedSatellite).getTLE().getName() + " Exists!");
			return true;
		}else{
			//System.out.println("Satellite does not exisit");
			return false;
		}
	}
	
     
	public static void main(String[] args) throws InterruptedException {
		
		SatelliteDB gpsSats = new SatelliteDB();
		gpsSats.getSatellites_GPS();
		gpsSats.setGroundstation(ALBUQUERQUE);
		
		SatelliteDB stationSats = new SatelliteDB();
		stationSats.getSatellites_Stations();
		stationSats.setGroundstation(STORMLAKE);
		
		SatelliteDB noaaSats = new SatelliteDB();
		noaaSats.getSatellites_NOAA();
		noaaSats.setGroundstation(ALBUQUERQUE);
		
		//Date now = new Date();
		
		System.out.println(gpsSats.satExist("ISS (ZARYA)"));
		System.out.println(stationSats.satExist("ISS (ZARYA)"));
		
//		int selectedSat = stationSats.getSatIndex("ISS (ZARYA)");
//		Satellite trackedSat = stationSats.getSat(selectedSat);
		
		ArrayList<satPosition> orbit;
		for(int i = 0; i<noaaSats.getSize(); i++){
			orbit = satOrbit.getDoubleOrbit(noaaSats.getSat(i));
			//Paths.setOrbit(orbit);
			Paths.setPosition(orbit, noaaSats.getSat(i).getTLE().getName());
		}
		for(int i = 0; i<gpsSats.getSize(); i++){
			orbit = satOrbit.getDoubleOrbit(gpsSats.getSat(i));
			//Paths.setOrbit(orbit);
			Paths.setPosition(orbit, gpsSats.getSat(i).getTLE().getName());
		}
		
		//WorldWind App

		Paths.start("World Wind Paths - Test", Paths.AppFrame.class);
		
	}

}
