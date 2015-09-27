package satellite;
import java.util.Date;

public class satPosition{

	private final double lat;
	private final double lon;
	private final double alt;
	private final Date time;

	public satPosition(double lat, double lon, double alt, Date time) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.alt = alt;
		this.time = time;
	}

	public double getLat() {
		return lat / (Math.PI * 2.0) * 360;
	}

	public double getLon() {
		return lon / (Math.PI * 2.0) * 360;
	}
	
	public double getAlt() {
		return alt;
	}
	
	public Date getTime() {
		return time;
	}

	@Override
	public String toString() {
		double degLat = lat / (Math.PI * 2.0) * 360;
		double degLon = lon / (Math.PI * 2.0) * 360;
		
		return "Position [lat=" + degLat + ", lon=" + degLon + ", alt = " + alt + ", time= " + time +"]";
	}
	
}
