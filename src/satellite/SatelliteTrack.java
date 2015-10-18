package satellite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import predict4java.*;

public class SatelliteTrack {

	private String name;
	private String tle1;
	private String tle2;
	private Satellite satellite;
	private PassPredictor passPredictor;
	// private SatPassTime satPassTime;
	private Boolean isVisibleGUI = true;
	private long uFreq = 0;
	private long dFreq = 0;
	private String image = "/satellite.png";	//not implemented
	private String trackColor;					//not implemented
	private ArrayList<satPosition> dayOrbit;
	private ArrayList<satPosition> singleOrbit;
	private ArrayList<satPosition> doubleOrbit;
	private ArrayList<satPosition> tripleOrbit;

	/**
	 * Constructs the Satellite Track object
	 * 
	 * @param name
	 * @param tleData
	 */
	public SatelliteTrack(String[] tleData) {
		TLE tle = new TLE(tleData);
		this.name = tleData[0];
		this.tle1 = tleData[1];
		this.tle2 = tleData[2];
		this.satellite = SatelliteFactory.createSatellite(tle);

		// Calculate Vectors (Must be done before any other calculations as they
		// are based off this!)
		this.satellite.calculateSatelliteVectors(new Date());

		// Calculate Orbits
		//this.dayOrbit = getDayOrbit(this.satellite);
		//this.singleOrbit = getSingleOrbit(this.satellite);
		//this.doubleOrbit = getDoubleOrbit(this.satellite);
		//this.tripleOrbit = getTripleOrbit(this.satellite);

	}

	public void setTLE(String[] tleData) {
		TLE tle = new TLE(tleData);
		this.satellite = SatelliteFactory.createSatellite(tle);
	}

	public TLE getTLE() {
		return this.satellite.getTLE();
	}

	public String getConstillation() {
		return "TBD";
	}

	public long getDopplarFrequency(long freq) {

		try {
			return passPredictor.getDownlinkFreq(freq, new Date());
		} catch (SatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;

	}

	public void setUplinkFreq(long freq) {
		this.uFreq = freq;
	}
	
	public void setDownlinkFreq(long freq) {
		this.dFreq = freq;
	}

	public long getUplinkFreq() {
		return uFreq;
	}

	public long getDownlinkFreq() {
		return dFreq;
	}
	
	public String getTLE1(){
		return tle1;
	}

	public String getTLE2(){
		return tle2;
	}
	
	public Boolean isVisibleGUI() {
		return isVisibleGUI;
	}

	public ArrayList<satPosition> getDayOrbit() {
		return dayOrbit;
	}

	public ArrayList<satPosition> getSingleOrbit() {
		return singleOrbit;
	}

	public ArrayList<satPosition> getDoubleOrbit() {
		return doubleOrbit;
	}

	public ArrayList<satPosition> getTripleOrbit() {
		return tripleOrbit;
	}
	
	public void updateOrbits(){
		dayOrbit = getDayOrbit(this.satellite);
		singleOrbit = getSingleOrbit(this.satellite);
		doubleOrbit = getDoubleOrbit(this.satellite);
		tripleOrbit = getTripleOrbit(this.satellite);
	}

	public List<SatPassTime> get24hrPasses(GroundStationPosition gpos) {

		updateSatPassPredictor(gpos);

		List<SatPassTime> passes = null;
		try {
			passes = passPredictor.getPasses(new Date(), 24, false);
		} catch (SatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return passes;
	}

	public List<SatPassTime> getPassesForTime(GroundStationPosition gpos, int hours) {
		updateSatPassPredictor(gpos);

		List<SatPassTime> passes = null;
		try {
			passes = passPredictor.getPasses(new Date(), hours, false);
		} catch (SatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return passes;
	}

	private void updateSatPassPredictor(GroundStationPosition gspos) {
		if (!this.satellite.willBeSeen(gspos)) {
			System.err.println("Satellite will not be seen - updateSatPassTime()");
		} else {
			try {
				passPredictor = new PassPredictor(this.satellite.getTLE(), gspos);
			} catch (IllegalArgumentException | SatNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private ArrayList<satPosition> getDayOrbit(Satellite trackedSat) {
		ArrayList<satPosition> dayOrbit = new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		for (int i = 0; i < 8640; i++) { // 10s of seconds in a day
			Date increment = new Date(timeNow + i * 10000);
			trackedSat.calculateSatelliteVectors(increment);
			dayOrbit.add(new satPosition(trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}
		System.out.println("Calculated 24hr Orbit for: " + trackedSat.getTLE().getName());
		return dayOrbit;
	}

	private ArrayList<satPosition> getSingleOrbit(Satellite trackedSat) {
		// Array to store orbit
		ArrayList<satPosition> singleOrbit = new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		trackedSat.calculateSatelliteVectors(now);
		// Iterate through one orbit
		double meanMotion = trackedSat.getTLE().getMeanmo();
		double period = 8460 / meanMotion;
		for (int i = 0; i < period; i++) { // Seconds in a day
			Date increment = new Date(timeNow + i * 10000);
			trackedSat.calculateSatelliteVectors(increment);
			singleOrbit.add(new satPosition(trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}
		System.out.println("Calculated Single Orbit for: " + trackedSat.getTLE().getName());
		return singleOrbit;
	}

	private ArrayList<satPosition> getDoubleOrbit(Satellite trackedSat) {
		// Array to store orbit
		ArrayList<satPosition> doubleOrbit = new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		trackedSat.calculateSatelliteVectors(now);
		// Iterate through two orbits
		double meanMotion = trackedSat.getTLE().getMeanmo();
		double period = 8460 / meanMotion;
		for (int i = 0; i < period * 2; i++) { // Seconds in a day
			Date increment = new Date(timeNow + i * 10000);
			trackedSat.calculateSatelliteVectors(increment);
			doubleOrbit.add(new satPosition(trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}
		System.out.println("Calculated Double Orbit for: " + trackedSat.getTLE().getName());
		return doubleOrbit;
	}

	private ArrayList<satPosition> getTripleOrbit(Satellite trackedSat) {
		// Array to store orbit
		ArrayList<satPosition> tripleOrbit = new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		trackedSat.calculateSatelliteVectors(now);
		// Iterate through two orbits
		double meanMotion = trackedSat.getTLE().getMeanmo();
		double period = 8460 / meanMotion;
		for (int i = 0; i < period * 3; i++) { // Seconds in a day
			Date increment = new Date(timeNow + i * 10000);
			trackedSat.calculateSatelliteVectors(increment);
			tripleOrbit.add(new satPosition(trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}
		System.out.println("Calculated Triple Orbit for: " + trackedSat.getTLE().getName());
		return tripleOrbit;
	}

}
