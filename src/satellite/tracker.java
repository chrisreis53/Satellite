package satellite;

public class tracker {

	public static void main(String[] args) {
		System.out.println("TEST");
		
		SatelliteDB satellites = new SatelliteDB("SatelliteDB");
		
		satellites.getSatellites_GPS();
		satellites.writeToXML();
	}

}
