package satellite;



public class trackerThread implements Runnable{

	static SatelliteTrack sat;
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
	
	public static void setSatellite(SatelliteTrack satellite){
		sat = satellite;
	}

}
