package satellite;

public class tracker extends Thread{
	
	public tracker(SatelliteTrack satellite){
		this.sat = satellite;
		this.name = satellite.getTLE().getName();
		this.image = satellite.getImage();
		this.color = satellite.getTrackColor();
	}

	public String name;
	public SatelliteTrack sat;
	String image;
	String color;
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
	
	public satPosition getPos(){
		satPosition satPos = new satPosition(sat.getPosition().getLat(),sat.getPosition().getLon(),sat.getPosition().getAlt(),sat.getTLE().getName());
		return satPos;
	}
}
