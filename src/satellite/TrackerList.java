package satellite;

import java.util.ArrayList;
import java.util.List;

public class TrackerList {

	private static List<SatelliteTrack> threadList = new ArrayList<SatelliteTrack>();
	
	public static void startTrack(String sat, String gs){
		int satNum = SatelliteDB.getSatIndex(sat);
		SatelliteTrack satellite = SatelliteDB.sat(satNum);
		threadList.add(satellite);
	}
	
	public static void stopTrack(String sat){
		for(int i = 0;i<threadList.size();i++){
			if(threadList.get(i).getTLE().getName().equals(sat)){
				threadList.remove(i);
			}
		}
	}
	
	public static satPosition getSatPos(String sat){
		int satNum = searchThread(sat);
		return threadList.get(satNum).getPosition();
	}
	
	public static List<satPosition> getTracks(){
		List<satPosition> positions = new ArrayList<satPosition>();
		for(int i =0;i<threadList.size();i++){
			positions.add(threadList.get(i).getPosition());
		}
		return positions;
	}
	
	private static int searchThread(String sat){
		for(int i = 0;i<threadList.size();i++){
			if(sat.trim().equals(threadList.get(i).getTLE().getName())){
				return i;
			}
		}
		return -1;
	}
	
}
