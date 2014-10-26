package hack.fwd;

import java.util.*;

public class GeologicModel {
	
	public static final double G = 6.674e-11;
	
	private LineSegment spatialLine;
	private ArrayList<ModelCoord> sed = new ArrayList();
	private ArrayList<ModelCoord> base = new ArrayList();
	private double nStations;
	private double dx;
	private ArrayList<Coordinate> stations = new ArrayList();
	private double rhoSed;
	private double rhoBase;
	
	/* Default constructor */
	public GeologicModel() {
		// do nothing
	}
	
	/* Constructor for geologic model class - assumes 100 gravity stations
	 * 	PRE:	ArrayList a - list of coordinates for air
	 * 			ArrayList s - list of coordinates for sedimentary section
	 * 			ArrayList b - list of coordinates for basement
	 *  POST:	geologic model object built */
	public GeologicModel(LineSegment ls, ArrayList<ModelCoord> s, ArrayList<ModelCoord> b, double rS, double rB) {
		spatialLine = ls;
		sed = s;
		base = b;
		nStations = 100;
		dx = spatialLine.getLength() / nStations;
		rhoSed = rS;
		rhoBase = rB;
		buildStations();
		buildSed();
		buildBase();
		calcGrav();
		
		/*
		System.out.println("Printing sedimentary block");
		printSed();
		System.out.println("Printing basement block");
		printBase(); */
		
		/*for (int i = 0; i < stations.size(); i++) {
			System.out.println(stations.get(i).getGz());
		}*/
	}
	
	/* Constructor for geologic model class - user gives number of gravity stations
	 * 	PRE:	ArrayList a- list of coordinates for air
	 * 			ArrayList s - list of coordinates for sedimentary section
	 * 			ArrayList b - list of coordinates for basement
	 *  POST:	geologic model object built */
	public GeologicModel(LineSegment ls, ArrayList<ModelCoord> s, ArrayList<ModelCoord> b, double rS, double rB, double ns) {
		spatialLine = ls;
		sed = s;
		base = b;
		nStations = ns;
		dx = spatialLine.getLength() / nStations;
		rhoSed = rS;
		rhoBase = rB;
		buildStations();
		buildSed();
		buildBase();
		//calcGrav();

		System.out.println("Printing sedimentary block");
		printSed();
		System.out.println("Printing basement block");
		printBase();
	}
	
	/* build measurement stations for the gravity readings */
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
			//System.out.println(i);
			//System.out.println(xtemp);
			//System.out.println(ytemp);
			//System.out.println(0);
			//System.out.println("\n");
		}
	}
	
	/* build sedimentary model */
	public void buildSed() {
		// approximate infinity with 30000 units
		sed.add(new ModelCoord(-100000, 0));
		sed.add(new ModelCoord(100000, 0));
		sed.add(new ModelCoord(100000, spatialLine.getEndpoint(false).getZ()));
		sed.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(false).getZ()));
		sed.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(true).getZ()));
		sed.add(new ModelCoord(-100000, spatialLine.getEndpoint(true).getZ()));
	}
	
	/* build basement model */
	public void buildBase() {
		// approximate infinity with 30000 units
		base.add(new ModelCoord(-100000, spatialLine.getEndpoint(true).getZ()));
		base.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(true).getZ()));
		base.add(new ModelCoord(spatialLine.getLength()/2, spatialLine.getEndpoint(false).getZ()));
		base.add(new ModelCoord(100000, spatialLine.getEndpoint(false).getZ()));
		base.add(new ModelCoord(100000, 30000));
		base.add(new ModelCoord(-100000, 30000));
	}
	
	public void printSed() {
		for (int i = 0; i < sed.size(); i++) {
			System.out.println("(" + sed.get(i).getX() + "," + sed.get(i).getZ() + ")");
		}
	}
	
	public void printBase() {
		for (int i = 0; i < base.size(); i++) {
			System.out.println("(" + base.get(i).getX() + "," + base.get(i).getZ() + ")");
		}
	}
	
	public void calcGrav() {
		for (int i = 0; i < stations.size(); i++) {
			System.out.println(forwardGravity(i)/0.00001);
			stations.get(i).setGz(forwardGravity(i)/0.00001);
		}
	}
	
	public double forwardGravity(int j) {
		double gS;
		double gB;
		double sumZ;
		double gz;
		
		/* build new temporary model-space coordinates arrays - resampling their location based on station i's
		location in model-space */ 
		ArrayList<ModelCoord> s = getModifiedCoord(j,sed);
		ArrayList<ModelCoord> b= getModifiedCoord(j,base);
		
		/* compute gravity component resulting from sedimentary section */
		sumZ = 0;
		for (int i = 0; i < s.size(); i++) {
			//System.out.println("Computing line integral along Sedimentary edge " + i);
			sumZ += lineIntegral(i,s);
		}
		gS = 2 * G * rhoSed * sumZ;
		//System.out.println("Computed sedimentary Gz is " + gS);
		
		/* compute gravity component resulting from basement section */
		sumZ = 0;
		for (int i = 0; i < b.size(); i++) {
			//System.out.println("Computing line integral along Basement edge " + i);
			sumZ += lineIntegral(i,b);
		}
		gB = 2 * G * rhoBase * sumZ;
		//System.out.println("Computed basement Gz is " + gB);
		
		/* sum the different components contributing to the gravity at this station location */
		gz = gS + gB;
		//System.out.println("Computed total Gz for station is " + gz);
		
		return gz;
	}
	
	/* Compute line integral along i-th edge of polygon */
	public double lineIntegral(int i, ArrayList<ModelCoord> mc) {
		int j;
		double B;
		
		// next vertex in polygon (second coordinate of the edge we're looking at)
		if (i < mc.size() - 1) j = i+1;
		else j = 0;
		//System.out.println(mc.size());
		
		// compute x and z values for i-th and j-th vertices 
		double xi = mc.get(i).getX();	
		double xj = mc.get(j).getX();
		double zi = mc.get(i).getZ();
		double zj = mc.get(j).getZ();
		//System.out.println("Line integral coords are (" + xi + "," + zi + ") and (" + xj + "," + zj + ")");
		
		// compute components of the line integral
		double theta_i = Math.atan(zi / Math.abs(xi));
		double theta_ip1 = Math.atan(zj / Math.abs(xj));
		double A = (xj - xi) * (xi*zj - xj*zi) / (Math.pow((xj-xi), 2) + (Math.pow((zj-zi), 2)));
		//System.out.println("A = " + A);
		if (xi == xj) B = (zj - zi) / 0.0001;
		else B = (zj - zi) / (xj - xi);
		System.out.println("zj-zi = " + (zj-zi) + "B = " + B);
		double ri_2 = Math.pow(xi, 2) + Math.pow(zi, 2);
		double rip1_2 = Math.pow(xj, 2) + Math.pow(zj, 2);
		//System.out.println("ri = " + ri_2 + " ri+1 = " + rip1_2);
		
		// compute and return value of the line integral along the i-th edge
		double Z = A * ((theta_ip1 - theta_i) + B * Math.log(Math.pow(ri_2,0.5)/Math.pow(rip1_2,0.5)));
		return Z;
	}
	
	/* create a modified array list of model-space coordinates using station j as the origin */
	public ArrayList<ModelCoord> getModifiedCoord(int j, ArrayList<ModelCoord> mc) {
		ArrayList<ModelCoord> t = new ArrayList();
		for (int i = 0; i < mc.size(); i++) {
			double x = (mc.get(i).getX() - stations.get(j).getX());
			double z = mc.get(i).getZ();
			ModelCoord temp = new ModelCoord(x,z);
			t.add(temp);
		}
		return t;
	}
}
