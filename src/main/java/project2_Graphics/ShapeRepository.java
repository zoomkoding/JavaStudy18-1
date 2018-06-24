package project2_Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

public class ShapeRepository {
	int option;
	Point start;
	Point end;
	int [] array_x = new int [40];
	int [] array_y = new int [40];
	int size;
	ArrayList<Point> sketchSP = new ArrayList<Point>();	
	
	Color mypencolor;
	Color myfillcolor;
	int thick;
}
