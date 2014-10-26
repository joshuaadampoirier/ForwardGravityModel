package hack.fwd;

public class ModelCoord {
	private double x;
	private double z;
	
	public ModelCoord(double length, double depth) {
		x = length;
		z = depth;
	}
	
	public double getX() {
		return x;
	}
	
	public double getZ() {
		return z;
	}
}
