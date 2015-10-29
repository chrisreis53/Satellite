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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import predict4java.*;


public class SatelliteDB {
 	
	static ArrayList<SatelliteTrack> database = new ArrayList<SatelliteTrack>();
	static boolean databaseSet = false;
	static String name = "SatelliteDB";
	static GroundStationPosition groundstation;
	final static GroundStationPosition ALBUQUERQUE = new GroundStationPosition(35.0873191, -106.6376107, 5500, "Albuquerque");
	final static GroundStationPosition STORMLAKE = new GroundStationPosition(42.6436, 95.2019, 1440,"Storm Lake"); 
	
	public SatelliteDB(String name){
		this.name = name;
	}
	
	public static boolean downloadTLE(String in_url, String file){
		
		URL url;
		
		try {
			url = new URL(in_url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		try {

			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			System.out.println("Data from: " + url);
			while ((inputLine = in.readLine()) != null){
				
				String line1 = in.readLine();
				String line2 = in.readLine();
				String[] args1 = {inputLine,line1,line2};
				
				if(satExist(args1[0])){
					System.out.println(inputLine + " updated!");
					int num = getSatIndex(inputLine);
					database.get(num).setTLE(args1);
				}else{
					System.out.println(args1[0] + " created!");
					SatelliteTrack sat = new SatelliteTrack(args1);
					sat.setConstillation(file);
					database.add(sat);
				}
				
			}
			in.close();
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
	
	public static void TLEReader(String file){
		
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
				//database.add(SatelliteFactory.createSatellite(data));
//	        System.out.println(line);
//	        System.out.println(br.readLine());
//	        System.out.println(br.readLine());
			}
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void setGroundstation(GroundStationPosition gPos){
		groundstation = gPos;
		}
	
	public static void getSatellites_GPS(){
		downloadTLE("https://celestrak.com/NORAD/elements/gps-ops.txt","GPS");
		//TLEReader("gps.txt");
		databaseSet = true;
	}
	
	public static void getSatellites_Stations(){
		downloadTLE("https://celestrak.com/NORAD/elements/stations.txt","Stations");
		//TLEReader("stations.txt");
		databaseSet = true;
	}
	
	public static void getSatellites_NOAA(){
		downloadTLE("https://celestrak.com/NORAD/elements/noaa.txt","NOAA");
		//TLEReader("noaa.txt");
		databaseSet = true;
	}
	
	public static void getSatellites_Iridium(){
		downloadTLE("https://celestrak.com/NORAD/elements/iridium.txt","Iridium");
		//TLEReader("noaa.txt");
		databaseSet = true;
	}
	
	public static void getSatellites_GEO(){
		downloadTLE("https://celestrak.com/NORAD/elements/geo.txt","Geostationary");
		//TLEReader("noaa.txt");
		databaseSet = true;
	}
	
	public static int getSatIndex(String sat){
		
		for(int i=0;i<database.size();i++){
			sat = sat.trim();
			if(sat.equals(database.get(i).getTLE().getName())){
				return i;
			}
		}
		
		return -1;
		
	}
	
	public static int getSize(){
		return database.size();
	}

	public static SatelliteTrack sat(int i){
		SatelliteTrack satTrack = database.get(i);
		return satTrack;
	}
	
	public static boolean satExist(String string){
		int selectedSatellite = getSatIndex(string);
		if(selectedSatellite>=0){
			//System.out.println(this.getSat(selectedSatellite).getTLE().getName() + " Exists!");
			return true;
		}else{
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
			
			for(int i = 0;i<database.size();i++){
				Element satellite = doc.createElement("Satellite");
				//satellite.appendChild(doc.createTextNode(database.get(i).getTLE().getName()));
				
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(database.get(i).getTLE().getName()));
				satellite.appendChild(name);
				
				Element tle1 = doc.createElement("TLE1");
				tle1.appendChild(doc.createTextNode(database.get(i).getTLE1()));
				satellite.appendChild(tle1);
				
				Element tle2 = doc.createElement("TLE2");
				tle2.appendChild(doc.createTextNode(database.get(i).getTLE2()));
				satellite.appendChild(tle2);
				
				//Uplink Frequency used
				Element uFreq = doc.createElement("UplinkFreq");
				uFreq.appendChild(doc.createTextNode(Long.toString(database.get(i).getUplinkFreq())));
				satellite.appendChild(uFreq);
				
				//Downlink Frequency
				Element dFreq = doc.createElement("DownlinkFreq");
				dFreq.appendChild(doc.createTextNode(Long.toString(database.get(i).getDownlinkFreq())));
				satellite.appendChild(dFreq);
				
				//GUI Visible?
				Element guiVisible = doc.createElement("GUIVisible");
				guiVisible.appendChild(doc.createTextNode(database.get(i).isVisibleGUI().toString()));
				satellite.appendChild(guiVisible);
				
				//Image
				Element satImage = doc.createElement("satImage");
				satImage.appendChild(doc.createTextNode(database.get(i).getImage()));
				satellite.appendChild(satImage);
				
				//TrackColor
				Element trackColor = doc.createElement("trackColor");
				trackColor.appendChild(doc.createTextNode(database.get(i).getTrackColor()));
				satellite.appendChild(trackColor);
				
				//Constellation
				Element constellation = doc.createElement("constellation");
				constellation.appendChild(doc.createTextNode(database.get(i).getConstillation()));
				satellite.appendChild(constellation);
				
				//Constellation
				Element info = doc.createElement("info");
				constellation.appendChild(doc.createTextNode(database.get(i).getInfo()));
				satellite.appendChild(info);
				
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

	public void readXML(){
		
		try {
			File fXmlFile = new File("satelliteDB.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("Satellite");
			System.out.println("There are " + nList.getLength() + " satellites in the Database");
			System.out.println("----------------------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					//Read in elements
					String name = eElement.getElementsByTagName("name").item(0).getTextContent();
					String TLE1 = eElement.getElementsByTagName("TLE1").item(0).getTextContent();
					String TLE2 = eElement.getElementsByTagName("TLE2").item(0).getTextContent();
					String uFreq = eElement.getElementsByTagName("UplinkFreq").item(0).getTextContent();
					String dFreq = eElement.getElementsByTagName("DownlinkFreq").item(0).getTextContent();
					String trackColor = eElement.getElementsByTagName("trackColor").item(0).getTextContent();
					String constellation = eElement.getElementsByTagName("constellation").item(0).getTextContent();
					String image = eElement.getElementsByTagName("satImage").item(0).getTextContent();
					String guiVisible = eElement.getElementsByTagName("GUIVisible").item(0).getTextContent();
					String info = eElement.getElementsByTagName("info").item(0).getTextContent();
					
					System.out.println(name);
					System.out.println(TLE1);
					System.out.println(TLE2);
					
					//Build Satellite
					String[] tle = {name,TLE1,TLE2};
					
					SatelliteTrack sat = new SatelliteTrack(tle);
					sat.setDownlinkFreq(Long.parseLong(dFreq));
					sat.setUplinkFreq(Long.parseLong(uFreq));
					sat.setTrackColor(trackColor);
					sat.setConstillation(constellation);
					sat.setImage(image);
					sat.setVisibleGUI(Boolean.valueOf(guiVisible));
					sat.setInfo(info);
					database.add(sat);
					
					//Debug print out
					System.out.println("Satellite name : " + sat.getTLE().getName());
					System.out.println("TLE1 : " + sat.getTLE1());
					System.out.println("TLE2 : " + sat.getTLE2());
					System.out.println("Uplink Frequency : " + sat.getUplinkFreq());
					System.out.println("Downlink Frequency : " + sat.getDownlinkFreq());
					System.out.println("Track Color : " + sat.getDownlinkFreq());
					System.out.println("Constellation : " + sat.getDownlinkFreq());
					System.out.println("Image : " + sat.getDownlinkFreq());
					System.out.println("Visible in GUI : " + sat.getDownlinkFreq());
					System.out.println("Info : "+sat.getInfo());

				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
