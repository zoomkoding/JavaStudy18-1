package project2_Graphics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

//전체 프레임
public class Frame extends JFrame{

	public static final int DEFAULT = 0;
	public static final int LINE = 1;
	public static final int RECT = 2;
	public static final int CIRCLE = 3;
	public static final int POLYLINE = 4;
	public static final int SKETCH = 5;

	//포인트 받아오는 곳
	Point start =null;
	Point end =null;

	//프레임 안에 있는 요소들
	JPanel optionPanel = new JPanel();
	Canvas canvas = new Canvas();
	JMenuBar menubar = new JMenuBar();

	//작업선택용 옵션
	int option = -1;

	int psize = 0;

	//각 도형들의 포인트를 저장해 놓는 벡터
	Vector<Point> lineSP = new Vector<Point>();
	Vector<Point> lineEP = new Vector<Point>();
	Vector<Point> rectSP = new Vector<Point>();
	Vector<Point> rectEP = new Vector<Point>();
	Vector<Point> circSP = new Vector<Point>();
	Vector<Point> circEP = new Vector<Point>();
	Vector<Point> sketSP = new Vector<Point>();
	Vector<Point> sketEP = new Vector<Point>();
	ArrayList<ShapeRepository> shape = new ArrayList<ShapeRepository>();

	//폴리라인을 위한 벡터
	int [] tempX = new int [10];
	int [] tempY = new int [10];

	//망작인데 빨리 수정이 시급함!!!!!!!!!!!
	ArrayList<Vector<Point>> sketchSP = new ArrayList<Vector<Point>>();
	ArrayList<Vector<Point>> sketchEP = new ArrayList<Vector<Point>>();
	ArrayList<Integer> p = new ArrayList<Integer>();
	ArrayList<int[]> polyX = new ArrayList<int[]>();
	ArrayList<int[]> polyY = new ArrayList<int[]>();

	//프레임 설정해주는 곳
	public Frame() {
		Dimension dim = new Dimension(400,600);
		Dimension dim1 = new Dimension(400,30);

		//프레임 설정
		setTitle("Jin's Studio");
		setLocation(600, 200);
		setPreferredSize(dim);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//아이템들 위치 선정
		add(optionPanel, "North");
		add(canvas);
		setJMenuBar(menubar);

		//메뉴를 위한 정보 입력
		JMenu file = new JMenu("File");
		menubar.add(file);
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		JMenuItem help = new JMenu("Help");
		menubar.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		exit.addActionListener(new ExitAction());

		//옵션 패널 설정
		optionPanel.setLayout(new GridLayout(1, 6));
		optionPanel.setPreferredSize(dim1);

		//옵션 패널 버튼 추가
		JButton[] button = new JButton[6]; 
		button[0] = new JButton("Line");
		button[1] = new JButton("Rect");
		button[2] = new JButton("Circle");
		button[3] = new JButton("PolyLine");
		button[4] = new JButton("Sketch");
		button[5] = new JButton("Extra");
		for(int i = 0; i<button.length; i++) {
			optionPanel.add(button[i]);
			button[i].addActionListener(new ButtonAction());
		}
	}

	//메뉴바 Exit버튼을 위한 액션 리스너
	class ExitAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			System.exit(0);
		}
	}

	//그림판 설정
	class Canvas extends JPanel {


		//리스너
		MyMouseListener ml = new MyMouseListener();

		//그림판 마우스 리스너 불러주기
		Canvas(){
			addMouseListener(ml);
			addMouseMotionListener(ml);
		}

		//새로 그림 그리는 곳 
		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			for(int i= 0 ; i<shape.size(); i++) {
				switch(shape.get(i).option) {
				case LINE : 
					g.drawLine(shape.get(i).start.x, shape.get(i).start.y, shape.get(i).end.x, shape.get(i).end.y);
					break;
				case RECT :
					g.drawRect(shape.get(i).start.x, shape.get(i).start.y, Math.abs(shape.get(i).end.x-shape.get(i).start.x), Math.abs(shape.get(i).end.y-shape.get(i).start.y));
					break;
				case CIRCLE :
					g.drawOval(shape.get(i).start.x, shape.get(i).start.y, shape.get(i).end.x-shape.get(i).start.x, shape.get(i).end.y-shape.get(i).start.y);
					break;
				case POLYLINE :
					g.drawPolyline(shape.get(i).array_x, shape.get(i).array_y, shape.get(i).size);
					break;
				case SKETCH :
					System.out.println(sketSP.size());
					for(int j = 1; j< shape.get(i).sketchSP.size(); j++) {
						g.drawLine(shape.get(i).sketchSP.get(j-1).x, shape.get(i).sketchSP.get(j-1).y, shape.get(i).sketchSP.get(j).x, shape.get(i).sketchSP.get(j).y);
					}
					break;
				}
			}

//			//라인 계속 그리기
//			if(lineEP != null)
//				for(int i = 0 ; i < lineEP.size(); i++) 
//					g.drawLine(lineSP.get(i).x, lineSP.get(i).y, lineEP.get(i).x, lineEP.get(i).y);			
//
//			//직사각형 계속 그리기
//			if(rectEP != null)
//				for(int i = 0 ; i < rectEP.size(); i++) 
//					g.drawRect(rectSP.get(i).x, rectSP.get(i).y, rectEP.get(i).x - rectSP.get(i).x, rectEP.get(i).y - rectSP.get(i).y);
//
//			//원 계속 그리기
//			if(circEP != null)
//				for(int i = 0 ; i < circEP.size(); i++)
//					g.drawOval(circSP.get(i).x, circSP.get(i).y, circEP.get(i).x - circSP.get(i).x, circEP.get(i).y - circSP.get(i).y);
//
//			//폴리라인 계속 그리기(수정필요)
//			if(!polyY.isEmpty()) {
//				//System.out.println("들어왔");
//				for(int i = 0 ; i < p.size(); i++) 
//					g.drawPolyline(polyX.get(i), polyY.get(i), p.get(i));	
//			}
//
//
//
//
//			//스케치 계속 그리기
//			if(sketchEP != null) {
//				for(int i = 0; i < sketchEP.size(); i++) 
//					for(int j = 0 ; j <sketchEP.get(i).size(); j++)
//						g.drawLine(sketchSP.get(i).get(j).x, sketchSP.get(i).get(j).y, sketchEP.get(i).get(j).x, sketchEP.get(i).get(j).y);
//			}







			//그림자 그리기
			if(start != null) {
				if(option == LINE) g.drawLine(start.x, start.y, end.x, end.y);
				else if(option == RECT) g.drawRect(start.x, start.y, end.x-start.x, end.y-start.y);
				else if(option == CIRCLE) g.drawOval(start.x, start.y, end.x-start.x, end.y-start.y);
				else if(option == POLYLINE) g.drawPolyline(tempX, tempY, psize+1);
				else if(option == SKETCH) {
					for(int i = 0; i < sketEP.size(); i++) {

						g.drawLine(sketSP.get(i).x, sketSP.get(i).y, sketEP.get(i).x, sketEP.get(i).y);
					}
				}
			}

		}

		//마우스 리스너 클래스
		class MyMouseListener extends MouseAdapter implements MouseMotionListener{
			int temp = 0;
			int mousepressed = 0;
			int newshapeoption;
			Point newshapestart;
			Point newshapeend;
			int [] newshapearray_x = new int [20];
			int [] newshapearray_y = new int [20];
			int newshapesize;
			
			ArrayList<Point> newshapesketchSP = new ArrayList<Point>();
			ArrayList<Point> newshapesketchEP = new ArrayList<Point>();
			

			//마우스 프레스 됐을 때
			public void mousePressed(MouseEvent e) {
				newshapeoption = option;
				if(mousepressed == 0) mousepressed += 1;
				else mousepressed = 0;
				System.out.println("마우스 눌렸다 " + option);
				if(option == LINE) {
					newshapestart = e.getPoint();
					lineSP.add(e.getPoint());
				}
				else if(option == RECT) {
					newshapestart = e.getPoint();
					rectSP.add(e.getPoint());
				}
				else if(option == CIRCLE) {
					newshapestart = e.getPoint();
					circSP.add(e.getPoint());
				}
				else if(option == POLYLINE) {
					newshapearray_x[psize] = e.getX();
					newshapearray_y[psize] = e.getY();
					tempX[psize] = e.getX();
					tempY[psize] = e.getY();
					psize++;
					newshapearray_x[psize] = e.getX();
					newshapearray_y[psize] = e.getY();
					tempX[psize] = e.getX();
					tempY[psize] = e.getY();
					
					if(e.getButton()==MouseEvent.BUTTON3) {
						newshapesize = psize;
						p.add(psize);
						polyX.add(tempX);
						polyY.add(tempY);
						shape.add(new ShapeRepository(newshapeoption, newshapearray_x, newshapearray_y, newshapesize));
						System.out.println(shape.get(0).option);
						psize = 0;
						option = DEFAULT;
					}
				}
				else if(option == SKETCH) {
					if(mousepressed == 1) {
						newshapesketchSP.add(e.getPoint());
						newshapesketchEP.add(e.getPoint());
						sketSP.add(e.getPoint());
					}
					else {
						

						shape.add(new ShapeRepository(newshapeoption, newshapesketchSP, newshapesketchEP));
						option = DEFAULT;
						sketchSP.add(sketSP);
						sketchEP.add(sketEP);
						sketSP.removeAllElements();
						sketEP.removeAllElements();
					}
				}
				start = e.getPoint();
			}

			//마우스 릴리즈 됐을 때
			public void mouseReleased(MouseEvent e) {
				if(option == LINE) {
					newshapeend = e.getPoint();
					lineEP.add(e.getPoint());
					
					shape.add(new ShapeRepository(newshapeoption, newshapestart, newshapeend));
				}
				else if(option == RECT) {
					newshapeend = e.getPoint();
					rectEP.add(e.getPoint());

					shape.add(new ShapeRepository(newshapeoption, newshapestart, newshapeend));
				}
				else if(option == CIRCLE) {
					newshapeend = e.getPoint();
					circEP.add(e.getPoint());
					
					shape.add(new ShapeRepository(newshapeoption, newshapestart, newshapeend));
				}

				end = e.getPoint();
				repaint();
			}



			@Override
			//마우스 드레그 됐을 때
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				end = e.getPoint();
				repaint();
			}

			@Override
			//마우스 움직일 때
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if(option == POLYLINE) {
					newshapearray_x[psize] = e.getX();
					newshapearray_y[psize] = e.getY();
					tempX[psize] = e.getX();
					tempY[psize] = e.getY();
					repaint();
				}
				else if(option == SKETCH) {

					if(mousepressed != 0) {
						newshapesketchSP.add(e.getPoint());
						newshapesketchEP.add(e.getPoint());
						sketEP.add(e.getPoint());
						sketSP.add(e.getPoint());
						repaint();
					}
				}

			}
		}
	}

	//버튼 액션 리스너 클래스
	class ButtonAction implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();
			if(temp.equals("Line")) {
				if(option == LINE) option = DEFAULT;
				else option = LINE;
				new Canvas();
			}
			else if(temp.equals("Rect")) {
				if(option == RECT)option = DEFAULT;
				else option = RECT;
				new Canvas();
			}
			else if(temp.equals("Circle")) {
				if(option == CIRCLE)option = DEFAULT;
				else option = CIRCLE;
				new Canvas();
			}
			else if(temp.equals("PolyLine")) {
				if(option == POLYLINE)option = DEFAULT;
				else option = POLYLINE;
				new Canvas();

			}
			else if(temp.equals("Sketch")) {
				if(option == SKETCH)option = DEFAULT;
				option = SKETCH;
				new Canvas();
			}
			else if(temp.equals("Extra")) {
				System.out.println("not decided");
			}
		}
	}
}






