package satellite;

import predict4java.*;
import java.util.ArrayList;
import java.util.Date;
import satellite.satPosition;

public class satOrbit {
	
	public static ArrayList<satPosition> getDayOrbit(Satellite trackedSat){
		ArrayList<satPosition> dayOrbit	= new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		for(int i = 0; i<8640;i++){						//10s of seconds in a day
			Date increment = new Date(timeNow + i*10000);
			trackedSat.calculateSatelliteVectors(increment);
			dayOrbit.add(new satPosition(
					trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}
		System.out.println("Calculated 24hr Orbit for: " + trackedSat.getTLE().getName());
		return dayOrbit;
	}

	public static ArrayList<satPosition> getSingleOrbit(Satellite trackedSat){
		//Array to store orbit
		ArrayList<satPosition> singleOrbit = new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		trackedSat.calculateSatelliteVectors(now);
		//Iterate through one orbit
		double meanMotion = trackedSat.getTLE().getMeanmo();
		double period = 8460/meanMotion;
		for(int i = 0;i<period;i++){						//Seconds in a day
			Date increment = new Date(timeNow  + i * 10000);
			trackedSat.calculateSatelliteVectors(increment);
			singleOrbit.add(new satPosition(
					trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}		
		System.out.println("Calculated Single Orbit for: " + trackedSat.getTLE().getName());
		return singleOrbit;
	}
	
	public static ArrayList<satPosition> getDoubleOrbit(Satellite trackedSat){
		//Array to store orbit
		ArrayList<satPosition> doubleOrbit = new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		trackedSat.calculateSatelliteVectors(now);
		//Iterate through two orbits
		double meanMotion = trackedSat.getTLE().getMeanmo();
		double period = 8460/meanMotion;
		for(int i = 0;i<period*2;i++){						//Seconds in a day
			Date increment = new Date(timeNow  + i * 10000);
			trackedSat.calculateSatelliteVectors(increment);
			doubleOrbit.add(new satPosition(
					trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}		
		System.out.println("Calculated Double Orbit for: " + trackedSat.getTLE().getName());
		return doubleOrbit;
	}

	public static ArrayList<satPosition> getTripleOrbit(Satellite trackedSat){
		//Array to store orbit
		ArrayList<satPosition> tripleOrbit = new ArrayList<satPosition>();
		Date now = new Date();
		long timeNow = now.getTime();
		trackedSat.calculateSatelliteVectors(now);
		//Iterate through two orbits
		double meanMotion = trackedSat.getTLE().getMeanmo();
		double period = 8460/meanMotion;
		for(int i = 0;i<period*3;i++){						//Seconds in a day
			Date increment = new Date(timeNow  + i * 10000);
			trackedSat.calculateSatelliteVectors(increment);
			tripleOrbit.add(new satPosition(
					trackedSat.calculateSatelliteGroundTrack().getLatitude(),
					trackedSat.calculateSatelliteGroundTrack().getLongitude(),
					trackedSat.calculateSatelliteGroundTrack().getAltitude(),
					trackedSat.calculateSatelliteGroundTrack().getTime()));
		}		
		System.out.println("Calculated Triple Orbit for: " + trackedSat.getTLE().getName());
		return tripleOrbit;
	}

}
