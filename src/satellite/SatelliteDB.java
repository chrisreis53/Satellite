package satellite;

import java.io.BufferedReader;
import java.io.File;
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
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import predict4java.*;


public class SatelliteDB {
 	
	ArrayList<SatelliteTrack> satelliteDB = new ArrayList<SatelliteTrack>();
	boolean databaseSet = false;
	String name = "SatelliteDB";
	GroundStationPosition groundstation;
	final static GroundStationPosition ALBUQUERQUE = new GroundStationPosition(35.0873191, -106.6376107, 5500, "Albuquerque");
	final static GroundStationPosition STORMLAKE = new GroundStationPosition(42.6436, 95.2019, 1440,"Storm Lake"); 
	
	public SatelliteDB(String name){
		this.name = name;
	}
	
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
				String line1 = in.readLine();
				String line2 = in.readLine();
				String[] args1 = {inputLine,line1,line2};
				System.out.println(args1);
				satelliteDB.add(new SatelliteTrack(args1));
				System.out.println(inputLine);
				writer.println(inputLine);
			}
			in.close();
			writer.close();
			return true;
			
		} catch (UnsupportedEncodingException e) {
			System.err.println("Unsupported Encoding...");
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
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
				//satelliteDB.add(SatelliteFactory.createSatellite(data));
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
		//TLEReader("gps.txt");
		databaseSet = true;
	}
	
	public void getSatellites_Stations(){
		downloadTLE("https://celestrak.com/NORAD/elements/stations.txt","stations.txt");
		//TLEReader("stations.txt");
		databaseSet = true;
	}
	
	public void getSatellites_NOAA(){
		downloadTLE("https://celestrak.com/NORAD/elements/noaa.txt","noaa.txt");
		//TLEReader("noaa.txt");
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

//	public Satellite getSat(int selectedSatellite) {
//		return satelliteDB.get(selectedSatellite);
//	}
	
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

	public void writeToXML(){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("satelliteDatabase");
			doc.appendChild(rootElement);
			
			Element date = doc.createElement("Created");
			date.appendChild(doc.createTextNode(new Date().toString()));
			rootElement.appendChild(date);
			
			for(int i = 0;i<satelliteDB.size();i++){
				Element satellite = doc.createElement("Satellite");
				//satellite.appendChild(doc.createTextNode(satelliteDB.get(i).getTLE().getName()));
				
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(satelliteDB.get(i).getTLE().getName()));
				satellite.appendChild(name);
				
				Element tle1 = doc.createElement("TLE1");
				tle1.appendChild(doc.createTextNode(satelliteDB.get(i).getTLE1()));
				satellite.appendChild(tle1);
				
				Element tle2 = doc.createElement("TLE2");
				tle2.appendChild(doc.createTextNode(satelliteDB.get(i).getTLE1()));
				satellite.appendChild(tle2);
				
				rootElement.appendChild(satellite);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(name + ".xml"));
			
			transformer.transform(source, result);
			
			System.out.print("Wrote to XML");
			
		}catch (ParserConfigurationException pce){
			pce.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
