package hack.fwd;

import java.util.*;

public class ForwardMain {

	public static final double G = 6.674e-8;
	
	/* assume flat surface, vertical fault halfway between two MT sites
	 * 	PRE:	double x1, y1, z1 - 		location of basement for MT site 1
	 * 			double x2, y2, z2			location of basement for MT site 2
	 * 			double rhoBase, rhoSed		density of basement, and sedimentary section */
	public ForwardMain(Coordinate c1, Coordinate c2, double rhoBase, double rhoSed) {
		Coordinate m = c1.getMidpoint(c2);
		LineSegment ls = new LineSegment(c1, c2);
		Coordinate pre = ls.preCoord(2);
		Coordinate post = ls.postCoord(2);
		
		System.out.println(pre.getX());
	}
	
	/* assume flat surface, vertical fault halfway between two MT sites
	 * 	PRE:	double x1, y1, z1
	 * 			double x2, y2, z2 
	 *  POST:	unknown so far */
	public ForwardMain(Coordinate c1, Coordinate c2) {
		final double rhoBase = 3.0;
		final double rhoSed = 2.67;
		
		Coordinate m = c1.getMidpoint(c2);
		LineSegment ls = new LineSegment(c1, c2);
		Coordinate pre = ls.preCoord(2);
		Coordinate post = ls.postCoord(2);
		ArrayList a = new ArrayList();
		ArrayList s = new ArrayList();
		ArrayList b = new ArrayList();
		ls = new LineSegment(pre, post);
		GeologicModel geo = new GeologicModel(ls, a, s, b);
		
		//System.out.println(post.getX());
	}
	
	public static void main(String[] args) {
		Coordinate c1 = new Coordinate(5,  5,  5000);
		Coordinate c2 = new Coordinate(10,10,4000);
		
		ForwardMain m = new ForwardMain(c1, c2);		
	}
}
