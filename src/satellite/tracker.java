package satellite;

public class tracker extends Thread{
	
	public tracker(SatelliteTrack satellite){
		sat = satellite;
		name = satellite.getTLE().getName();
	}

	public String name;
	public SatelliteTrack sat;
	int seconds = 0;
	
	public void run(){
			while(true){
			System.out.println("Tracking " + sat.getTLE().getName() + " for: "+ seconds +" seconds");
			System.out.println(sat.getPosition().getLat() + " " + sat.getPosition().getLon());
			seconds++;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
