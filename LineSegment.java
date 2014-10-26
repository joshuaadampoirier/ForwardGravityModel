package hack.fwd;

public class LineSegment extends Line {
	private Coordinate start;
	private Coordinate end;
	
	/* default constructor */
	public LineSegment() {
		super();
		// do nothing
	}
	
	public LineSegment(Coordinate s, Coordinate e) {
		super(s,e);
		start = s;
		end = e;	
	}
	
	/* retrieve the coordinate a distance d before this line segment */
	public Coordinate preCoord(double d) {
		if (isVertical) {
			if (start.getY() < end.getY()) {
				return new Coordinate(start.getX(), start.getY() - d, start.getZ());
			}
			else {
				return new Coordinate(start.getX(), end.getY() - d, start.getZ());
			}
		}
		else {
			if (start.getX() < end.getX()) {
				return new Coordinate(start.getX() - d * Math.cos(Math.atan(m)), start.getY() - d * Math.sin(Math.atan(m)), start.getZ());
			}
			else {
				return new Coordinate(end.getX() - d * Math.cos(Math.atan(m)), end.getY() - d * Math.sin(Math.atan(m)), start.getZ());
			}
		}
		
	}
	
	/* retrieve the coordinate distance d beyond this line segment */
	public Coordinate postCoord(double d) {
		// vertical line
		if (isVertical) {
			if (start.getY() < end.getY()) {
				return new Coordinate(start.getX(), end.getY() + d, end.getZ());
			}
			else {
				return new Coordinate(start.getX(), start.getY() + d, end.getZ());
			}
		}
		// non-vertical line
		else {
			if (start.getX() < end.getX()) {
				return new Coordinate(end.getX() + d * Math.cos(Math.atan(m)), end.getY() + d * Math.sin(Math.atan(m)), end.getZ());
			}
			else {
				return new Coordinate(start.getX() + d * Math.cos(Math.atan(m)), start.getY() + d * Math.sin(Math.atan(m)), end.getZ());
			}
		}
	}
	
	/* Compute horizontal length of line segment */
	public double getLength() {
		return (Math.sqrt((Math.pow(Math.abs(end.getX()-start.getX()), 2))+(Math.pow(Math.abs(end.getY()-start.getY()), 2))));
	}
	
	/* Retrieve either the start (input true) or end (input false) point of the line segment */
	public Coordinate getEndpoint(boolean s) {
		if (s) {
			return start;
		}
		else {
			return end;
		}
	}
}
