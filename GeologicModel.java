package hack.fwd;

import java.util.*;

public class GeologicModel {
	private LineSegment spatialLine;
	private ArrayList<ModelCoord> air;
	private ArrayList<ModelCoord> sed;
	private ArrayList<ModelCoord> base;
	private double nStations;
	private double dx;
	private ArrayList<Coordinate> stations = new ArrayList();
	
	/* Constructor for geologic model class - assumes 100 gravity stations
	 * 	PRE:	ArrayList a - list of coordinates for air
	 * 			ArrayList s - list of coordinates for sedimentary section
	 * 			ArrayList b - list of coordinates for basement
	 *  POST:	geologic model object built */
	public GeologicModel(LineSegment ls, ArrayList<ModelCoord> a, ArrayList<ModelCoord> s, ArrayList<ModelCoord> b) {
		spatialLine = ls;
		air = a;
		sed = s;
		base = b;
		nStations = 100;
		dx = spatialLine.getLength() / nStations;
		buildStations();
	}
	
	/* Constructor for geologic model class - user gives number of gravity stations
	 * 	PRE:	ArrayList a- list of coordinates for air
	 * 			ArrayList s - list of coordinates for sedimentary section
	 * 			ArrayList b - list of coordinates for basement
	 *  POST:	geologic model object built */
	public GeologicModel(LineSegment ls, ArrayList<ModelCoord> a, ArrayList<ModelCoord> s, ArrayList<ModelCoord> b, double ns) {
		spatialLine = ls;
		air = a;
		sed = s;
		base = b;
		nStations = ns;
		dx = spatialLine.getLength() / nStations;
		buildStations();
	}
	
	public void buildStations() {
		
		double deltaX = dx * Math.cos(Math.atan(spatialLine.getSlope()));
		double deltaY = dx * Math.sin(Math.atan(spatialLine.getSlope()));
		
		for (int i = 0; i < nStations; i++) {
			double xtemp;
			double ytemp;
			
			if (i == 0) {
				xtemp = spatialLine.getEndpoint(true).getX();
				ytemp = spatialLine.getEndpoint(true).getY();
			}
			else {
				xtemp = spatialLine.getEndpoint(true).getX() + i * deltaX;
				ytemp = spatialLine.getEndpoint(true).getY() + i * deltaY;	
			}
			 
			stations.add(new Coordinate(xtemp, ytemp, 0));
			System.out.println(i);
			System.out.println(xtemp);
			System.out.println(ytemp);
			System.out.println(0);
			System.out.println("\n");
		}
	}
	
	/* build air model - ground-based measurements */
	public void buildAir() {
		air.add(new ModelCoord(-30000, 0));
		air.add(new ModelCoord(30000, 0));
		air.add(new ModelCoord(30000, 0));
		air.add(new ModelCoord(-30000, 0));
	}
	
	/* build air model - airborne measurements */
	public void buildAir(double altitude) {
		// approximate infinity with 30000 units
		air.add(new ModelCoord(-30000, -altitude));
		air.add(new ModelCoord(30000, -altitude));
		air.add(new ModelCoord(30000, 0));
		air.add(new ModelCoord(-30000, 0));
	}
	
	/* build sedimentary model */
	public void buildSed() {
		// approximate infinity with 30000 units
		sed.add(new ModelCoord(-30000, 0));
		sed.add(new ModelCoord(30000, 0));
		sed.add(new ModelCoord(30000, spatialLine.getEndpoint(false).getZ()));
		sed.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(false).getZ()));
		sed.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(true).getZ()));
		sed.add(new ModelCoord(-30000, spatialLine.getEndpoint(true).getZ()));
	}
	
	/* build basement model */
	public void buildBase() {
		// approximate infinity with 30000 units
		base.add(new ModelCoord(-30000, spatialLine.getEndpoint(true).getZ()));
		base.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(true).getZ()));
		base.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(false).getZ()));
		base.add(new ModelCoord(30000, spatialLine.getEndpoint(false).getZ()));
		base.add(new ModelCoord(30000, 30000));
		base.add(new ModelCoord(-30000, 30000));
	}
}
