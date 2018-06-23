package project2_Graphics;

import java.awt.Point;
import java.util.ArrayList;

public class ShapeRepository {
	int option;
	Point start;
	Point end;
	int [] array_x = new int [20];
	int [] array_y = new int [20];
	int size;
	ArrayList<Point> sketchSP = new ArrayList<Point>();
	ArrayList<Point> sketchEP = new ArrayList<Point>();
	
	ShapeRepository(int option, Point start, Point end){
		this.option = option;
		this.start = start;
		this.end = end;
	}
	
	ShapeRepository(int option, int [] array_x, int [] array_y, int size){
		this.option = option;
		this.array_x = array_x.clone();
		this.array_y = array_y.clone();
		this.size = size;
		
	}

	ShapeRepository(int option, ArrayList<Point> sketchSP, ArrayList<Point> sketchEP){
		this.option = option;
		this.sketchSP.addAll(sketchSP);
		this.sketchEP.addAll(sketchEP);
	}
	
}
