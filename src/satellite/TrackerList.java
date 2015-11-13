package satellite;

import java.util.ArrayList;
import java.util.List;

public class TrackerList {

	private static List<tracker> threadList = new ArrayList<tracker>();
	
	public static void startTrack(String sat, String gs){
		int satNum = SatelliteDB.getSatIndex(sat);
		SatelliteTrack satellite = SatelliteDB.sat(satNum);
		threadList.add(new tracker(satellite));
		threadList.get(threadList.size()-1).start();
	}
	
	public static void stopTrack(String sat){
		for(int i = 0;i<threadList.size();i++){
			if(threadList.get(i).name.equals(sat)){
				threadList.get(i).stop();
			}
		}
	}
	
	public static satPosition getSatPos(String sat){
		int satNum = searchThread(sat);
		return threadList.get(satNum).sat.getPosition();
	}
	
	private static int searchThread(String sat){
		for(int i = 0;i<threadList.size();i++){
			if(sat.trim().equals(threadList.get(i).name)){
				return i;
			}
		}
		return -1;
	}
	
}
