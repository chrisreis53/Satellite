package satellite;

import java.util.ArrayList;
import java.util.List;

public class TrackerThread{

	//trackerThread.setSatellite(curSat);
	//(new Thread(new trackerThread())).start();
	private static List<tracker> threadList = new ArrayList<tracker>();
	
	
	public static void startTrack(String sat, String gs){
		int satNum = SatelliteDB.getSatIndex(sat);
		SatelliteTrack satellite = SatelliteDB.sat(satNum);
		threadList.add(new tracker(satellite));
		threadList.get(threadList.size()-1).start();
	}
	
	public void stopTrack(String sat){
		for(int i = 0;i<threadList.size();i++){
			if(threadList.get(i).name.equals(sat)){
				threadList.get(i).stop();
			}
		}
	}
	
	private void searchThread(String sat){
		for(int i = 0;i<threadList.size();i++){
			if(sat.trim().equals(threadList.get(i).name)){
				//threadList.get(i).stopTrack(sat);;
			}
		}
	}
	
	private class tracker extends Thread{
		
		public tracker(SatelliteTrack satellite){
			sat = satellite;
			name = satellite.getTLE().getName();
		}
		public String name;
		SatelliteTrack sat;
		int seconds = 0;
		public void run(){
				while(true){
				System.out.println("Tracking " + sat.getTLE().getName() + " for: "+ seconds +" seconds");
				seconds++;
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
