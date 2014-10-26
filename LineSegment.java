package hack.fwd;

public class LineSegment extends Line {
	private Coordinate start;
	private Coordinate end;
	
	public LineSegment(Coordinate s, Coordinate e) {
		super(s,e);
		start = s;
		end = e;	
	}
	
	/* retrieve the coordinate a distance d before this line segment */
	public Coordinate preCoord(double d) {
		if (isVertical) {
			if (start.getY() < end.getY()) {
				return new Coordinate(start.getX(), start.getY() - d, 0);
			}
			else {
				return new Coordinate(start.getX(), end.getY() - d, 0);
			}
		}
		else {
			if (start.getX() < end.getX()) {
				return new Coordinate(start.getX() - d * Math.cos(Math.atan(m)), start.getY() - d * Math.sin(Math.atan(m)), 0);
			}
			else {
				return new Coordinate(end.getX() - d * Math.cos(Math.atan(m)), end.getY() - d * Math.sin(Math.atan(m)), 0);
			}
		}
		
	}
	
	/* retrieve the coordinate distance d beyond this line segment */
	public Coordinate postCoord(double d) {
		// vertical line
		if (isVertical) {
			if (start.getY() < end.getY()) {
				return new Coordinate(start.getX(), end.getY() + d, 0);
			}
			else {
				return new Coordinate(start.getX(), start.getY() + d, 0);
			}
		}
		// non-vertical line
		else {
			if (start.getX() < end.getX()) {
				return new Coordinate(end.getX() + d * Math.cos(Math.atan(m)), end.getY() + d * Math.sin(Math.atan(m)), 0);
			}
			else {
				return new Coordinate(start.getX() + d * Math.cos(Math.atan(m)), start.getY() + d * Math.sin(Math.atan(m)), 0);
			}
		}
	}
	
	public double getLength() {
		return (Math.sqrt((Math.pow(Math.abs(end.getX()-start.getX()), 2))+(Math.pow(Math.abs(end.getY()-start.getY()), 2))));
	}
	
	public Coordinate getEndpoint(boolean s) {
		if (s) {
			return start;
		}
		else {
			return end;
		}
	}
}
