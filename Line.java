package hack.fwd;

/* Straight line class */
public class Line {
	protected boolean isVertical;
	protected double m;
	protected double b;
	protected Coordinate a;
	
	/* Constructor given two coordinates */
	public Line(Coordinate c1, Coordinate c2) {
		if (c1.getX() == c2.getX()) {
			// if line is vertical, does not have slope or unique y-intercept
			isVertical = true;
			a = c1;
		}
		else {
			// otherwise calculate slope and y-intercept
			if (c1.getX() < c2.getX()) {
				m = (c2.getY() - c1.getY()) / (c2.getX() - c1.getX());
			}
			else {
				m = (c1.getY() - c2.getY()) / (c1.getX() - c2.getX());
			}
			b = c1.getY() - m * c1.getX();
			isVertical = false;
		}
	}
	
	/* Constructor given slope and y-intercept */
	public Line(double slope, double yIntercept) {
		isVertical = false;
		m = slope;
		b = yIntercept;
	}
	
	public double getSlope() {
		return m;
	}
}
