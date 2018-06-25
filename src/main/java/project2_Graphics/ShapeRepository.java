package project2_Graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

public class ShapeRepository {
	int option;
	
	int minx;
	int miny;
	Point start;
	Point end;
	int width;
	int height;
	int [] array_x = new int [40];
	int [] array_y = new int [40];
	int size;
	ArrayList<Point> sketchSP = new ArrayList<Point>();	
	
	int moved = 0;
	
	Color mypencolor;
	Color myfillcolor;
	int thick;
}
