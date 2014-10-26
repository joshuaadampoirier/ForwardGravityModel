package hack.fwd;

import java.util.*;

public class ForwardMain {

	private GeologicModel geoMod = new GeologicModel();
	private LineSegment ls = new LineSegment();
	
	/* assume flat surface, vertical fault halfway between two MT sites
	 * 	PRE:	double x1, y1, z1 - 		location of basement for MT site 1
	 * 			double x2, y2, z2			location of basement for MT site 2
	 * 			double rhoBase, rhoSed		density of basement, and sedimentary section */
	public ForwardMain(Coordinate c1, Coordinate c2, double rhoAir, double rhoBase, double rhoSed) {
		// build line segment between two MT sites
		ls = new LineSegment(c1, c2);
		
		// expand the line segment on both sides by half the length
		Coordinate pre = ls.preCoord(ls.getLength()/2);
		Coordinate post = ls.postCoord(ls.getLength()/2);
		ls = new LineSegment(pre, post);
		
		// build air, sedimentary, and basement polygons to represent 2D geologic units
		ArrayList<ModelCoord> s = new ArrayList();
		ArrayList<ModelCoord> b = new ArrayList();
		
		// build geologic model
		geoMod = new GeologicModel(ls, s, b, rhoSed, rhoBase);
		
		System.out.println("Geologic model built");
	}
	
	/* assume flat surface, vertical fault halfway between two MT sites
	 * 	PRE:	double x1, y1, z1
	 * 			double x2, y2, z2 
	 *  POST:	unknown so far */
	public ForwardMain(Coordinate c1, Coordinate c2) {
		// initialize default density constants for air, sedimentary, and basement geologic units
		final double rhoSed = 2670;
		final double rhoBase = 3000;
		
		// build line segment between the two MT sites
		ls = new LineSegment(c1, c2);
		
		// expand the line segment on both sides by half the length
		Coordinate pre = ls.preCoord(ls.getLength()/2);
		Coordinate post = ls.postCoord(ls.getLength()/2);
		ls = new LineSegment(pre, post);
		
		// build air, sedimentary, and basement polygons to represent 2D geologic units
		ArrayList<ModelCoord> s = new ArrayList();
		ArrayList<ModelCoord> b = new ArrayList();
		
		// build geologic model
		geoMod = new GeologicModel(ls, s, b, rhoSed, rhoBase);
		
		System.out.println("Geologic model built");
	}
	
	public static void main(String[] args) {
		// default location of 2 MT sites (along with depth to basement)
		Coordinate c1 = new Coordinate(5000,  5000,  500);
		Coordinate c2 = new Coordinate(10000,10000,4000);
		
		// build forward model object
		ForwardMain m = new ForwardMain(c1, c2);		
	}
}
