package XML;

import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.xml.sax.*;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import XML.*;


/**
 * <h3>TEST</h3>
 * <h2>
 * <SatelliteDB>
 * 	<Constillation>
 * 		<Name>
 * 		<Satellite>
 * 			<Name>
 * 			<TLE>
 * 				<line1>
 * 				<line2>
 * 			<img>
 * 			<LaunchDate>
 * 				<LaunchDate String>
 * 				<LaunchDate int>
 * 			<Mission>
 * 		</Satellite>
 * 	</Constillation>
 * <SatellliteDB>
 * </h2>
 * 
 * 
 * @author Christopher
 *
 */
public class SatelliteXMLDB{
	final static String NOAA = "NOAA"; 
	final static String STATIONS = "STATIONS"; 
	final static String GPS = "GPS"; 
	final static String OTHER = "OTHER"; 
	
	private String dbName;
	
	public SatelliteXMLDB(){
	}
	
	public SatelliteXMLDB(String name){
		dbName = name;
		this.startXMLDB(name);
	}
	
	public SatelliteXMLDB(String name,String[] args){
		dbName = name;
		this.startXMLDB(name);
		for(int i = 0;i<args.length;i++){
			if(args[i].equals(NOAA)){
				
			}else if(args[i].equals(STATIONS)){
				
			}else if(args[i].equals(GPS)){
				
			}else{
				System.err.println("Not sure what "+args[i]+" is.");
			}
		}
	}

	public void startXMLDB(String name){
		dbName = name;
		File f = new File(name);
		if(f.exists() && !f.isDirectory()) { 
		    System.err.println("File: "+name+" already exisits.");
		}else{
		
			try{
			 PrintWriter writer = new PrintWriter(name, "UTF-8");
		     StringWriter stringWriter = new StringWriter();
	
		     XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();	
		     XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);
	
		     xMLStreamWriter.writeStartDocument();
		     xMLStreamWriter.writeStartElement("SatelliteDB");
		     
		     xMLStreamWriter.writeEndElement();
		     xMLStreamWriter.writeEndDocument();
	
		     xMLStreamWriter.flush();
		     xMLStreamWriter.close();
	
		     String xmlString = stringWriter.getBuffer().toString();
	
		     stringWriter.close();
	
		     System.out.println(xmlString);
		     writer.print(xmlString);
		     writer.close();
		     
			} catch (XMLStreamException e) {
				     e.printStackTrace();
			} catch (IOException e) {
				     e.printStackTrace();
			}
		}
	}
	
	public String getFileName(){
		return this.dbName;
	}
	
	public void addConstellation(String name){
		
		try {
	         XMLInputFactory factory = XMLInputFactory.newInstance();
	         XMLEventReader eventReader =
	            factory.createXMLEventReader(
	               new FileReader(this.dbName));
	         SAXBuilder saxBuilder = new SAXBuilder();
	         Document document = saxBuilder.build(new File(this.dbName));
	         Element rootElement = document.getRootElement();
	         List<Element> studentElements = rootElement.getChildren("SatelliteDB");
	         while(eventReader.hasNext()){
	            XMLEvent event = eventReader.nextEvent();
	            switch(event.getEventType()){
	               case XMLStreamConstants.START_ELEMENT:
	               StartElement startElement = event.asStartElement();
	               String qName = startElement.getName().getLocalPart();

	               if (qName.equalsIgnoreCase("student")) {				        	
	                  Iterator<Attribute> attributes = startElement.getAttributes();
	                  String rollNo = attributes.next().getValue();				           
	                  if(rollNo.equalsIgnoreCase("393")){     	 
	                     //get the student with roll no 393				                 
	                     for(int i=0;i < studentElements.size();i++){
	                        Element studentElement = studentElements.get(i);
	                        if(studentElement.getAttribute("rollno").getValue().equalsIgnoreCase("393")){
	                           studentElement.removeChild("marks");
	                           studentElement.addContent(new Element("marks").setText("80"));
	                        }
	                    }
	                  }
	               }       
	               break;
	            }		    
	         }
	         XMLOutputter xmlOutput = new XMLOutputter();
	         // display xml
	         xmlOutput.setFormat(Format.getPrettyFormat());
	         xmlOutput.output(document, System.out); 
	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (XMLStreamException e) {
	         e.printStackTrace();
	      } catch (JDOMException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		
	}
	
	public static void addSatellite(String name, String tle1, String tle2, String img, Date launchDate, String mission){
		
	}

	public static void main(String[] args){
		
		String[] satellites = {NOAA,GPS,OTHER};
		SatelliteXMLDB SatelliteDB = new SatelliteXMLDB("SatelliteDB.xml", satellites);
		SatelliteDB.addConstellation("GPS");
		

	}   

}
