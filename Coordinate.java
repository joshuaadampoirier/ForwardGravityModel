package hack.fwd;

public class Coordinate {
	private double x;		// x-component of location
	private double y;		// y-component of location
	private double z;		// z-component of location
	private double gz;		// vertical component of the gravity (mGal)
	
	/*  Create coordinate object
	 * 	PRE:	double easting - x-component of the coordinate
	 * 			double northing - y-component of the coordinate
	 * 			double depth - z-component of the coordinate */
	public Coordinate(double easting, double northing, double depth) {
		x = easting;
		y = northing;
		z = depth;
	}
	
	/* Set gravity value */
	public void setGz(double g) {
		gz = g;
	}
	
	/* Retrieve gravity value */
	public double getGz() {
		return gz;
	}
	
	/* Retrieve x-coordinate */
	public double getX() {
		return x;
	}
	
	/* Retrieve y-coordinate */
	public double getY() {	
		return y;
	}
	
	/* Retrieve z-coordinate */
	public double getZ() {
		return z;
	}
	
	/* Compute midpoint between this Coordinate and a given Coordinate
	 * 	PRE:	Coordinate a - the coordinate to calculate the midpoint with this coordinate
	 * 	POST:	Coordinate of the midpoint is returned */
	public Coordinate getMidpoint(Coordinate a) {
		return new Coordinate((x+a.getX())/2, (y+a.getY())/2, (z+a.getZ())/2);
	}
}
