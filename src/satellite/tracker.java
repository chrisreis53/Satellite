package satellite;

public class tracker {

	public static void main(String[] args) {

		SatelliteDB satellites = new SatelliteDB("SatelliteDB");
		
		satellites.readXML();
		
		if(satellites.satExist("PROGRESS-M 29M")){
			System.out.println("Satellite Exitis!");
		}
		
		satellites.getSatellites_GPS();
		satellites.getSatellites_NOAA();
		satellites.getSatellites_Stations();
		
		satellites.writeToXML();
		

	}

}
