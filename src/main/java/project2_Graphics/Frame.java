package project2_Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//전체 프레임
public class Frame extends JFrame{

	public static final int DEFAULT = 0;
	public static final int LINE = 1;
	public static final int RECT = 2;
	public static final int CIRCLE = 3;
	public static final int POLYLINE = 4;
	public static final int SKETCH = 5;
	public static final int ERASE = 6;
	public static final int ERASER = 7;
	public static final int UNDO = 8;
	public static final int REDO = 9;




	//포인트 받아오는 곳
	Point start =null;
	Point end =null;
	int minx;
	int miny;
	
	int width;
	int height;
	Point mouse = new Point(0,0);

	//프레임 안에 있는 요소들
	JPanel optionPanel = new JPanel();
	Canvas canvas = new Canvas();
	JMenuBar menubar = new JMenuBar();
	JToolBar toolbar = new JToolBar("Options");
	SpinnerNumberModel sizemodel = new SpinnerNumberModel(1, 1, 50, 1);
	JSpinner spinner = new JSpinner(sizemodel);
	ExamplePanel examplepanel = new ExamplePanel();
	JPanel endbarpanel = new JPanel();
	JLabel xycoord;
	JLabel mode;




	//작업선택용 옵션
	int option = 0;
	int mousepressed = 0;

	int psize = 0;

	//각 도형들의 포인트를 저장해 놓는 벡터

	Vector<Point> sketSP = new Vector<Point>();
	ShapeRepository newshape;
	Stack<ShapeRepository> shape = new Stack<ShapeRepository>();
	Stack<ShapeRepository> redoshape = new Stack<ShapeRepository>();

	//폴리라인을 위한 벡터
	int [] tempX = new int [40];
	int [] tempY = new int [40];

	//설정을 위한 변수들
	Color mypencolor = Color.black;
	Color myfillcolor = Color.white;

	Font myfont;
	int thick;
	int eraserthick = 15;

	Dimension dim = new Dimension(1000,700);
	Dimension dim1 = new Dimension(1000, 50);
	Dimension dim2 = new Dimension(1000,570);
	Dimension dim3 = new Dimension(1000, 30);

	//프레임 설정해주는 곳
	public Frame() {


		//프레임 설정
		setLocation(800,100);
		setTitle("Jin's Studio");
		setSize(dim);
		setLayout(null);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//아이템들 위치 선정
		add(canvas);
		add(toolbar);
		add(endbarpanel);
		setJMenuBar(menubar);
		toolbar.setLocation(0,0);
		endbarpanel.setSize(dim3);
		endbarpanel.setBackground(Color.gray);
		endbarpanel.setLocation(0,620);
		endbarpanel.setLayout(new BoxLayout(endbarpanel, BoxLayout.X_AXIS));
		canvas.setSize(dim2);
		canvas.setBackground(Color.white);
		canvas.setLocation(0,50);

		xycoord = new JLabel("[x, y] = [" + mouse.getX() + "] [" +mouse.getY() + "]");
		xycoord.setPreferredSize(new Dimension(200, 40));
		mode = new JLabel("[Mode] = [DEFAULT]");
		endbarpanel.add(xycoord);
		endbarpanel.add(mode);
		
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

		//툴바 설정
		toolbar.setBackground(Color.gray);
		toolbar.setSize(dim1);
		toolbar.setLayout(new FlowLayout());

		JLabel preview = new JLabel("미리보기");
		preview.setPreferredSize(new Dimension(60,40));
		
		toolbar.add(preview);
		toolbar.add(examplepanel);
		toolbar.addSeparator();
		JButton[] optionbtn = new JButton[5]; 
		optionbtn[0] = new JButton("l");
		optionbtn[1] = new JButton("▢");
		optionbtn[2] = new JButton("◯");
		optionbtn[3] = new JButton(">");
		optionbtn[4] = new JButton("⌇");
		for(int i = 0; i<optionbtn.length; i++) {
			toolbar.add(optionbtn[i]);
			optionbtn[i].setPreferredSize(new Dimension(40,40));
			//			optionbtn[i].setLocation(40*i, 0);
			optionbtn[i].addActionListener(new ButtonAction());
		}
		toolbar.addSeparator();

		JButton[] setbtn = new JButton[6]; 
		setbtn[0] = new JButton("Undo");
		setbtn[1] = new JButton("Redo");
		setbtn[2] = new JButton("지우개");
		setbtn[3] = new JButton("지우기");
		setbtn[4] = new JButton("펜색");
		setbtn[5] = new JButton("채우기색");


		for(int i = 0; i<setbtn.length; i++) {
			toolbar.add(setbtn[i]);
			setbtn[i].setPreferredSize(new Dimension(60,40));
			//			setbtn[i].setLocation(50*(i+optionbtn.length), 0);
			setbtn[i].addActionListener(new ButtonAction());
		}

		JLabel thickLabel = new JLabel("두께");
		thickLabel.setPreferredSize(new Dimension(40,40));
		//				thickLabel.setLocation(40*(optionbtn.length)+50*(setbtn.length)+50, 0);
		JLabel colorLabel = new JLabel("색상");
		colorLabel.setPreferredSize(new Dimension(40,40));
		




		//툴바에 추가
		//		toolbar.add(colorLabel);
		toolbar.add(thickLabel);

		toolbar.add(spinner);

		//스피너에 리스너 설
		spinner.addChangeListener(new ChangeDetector());



		//		//옵션 패널 설정
		//		optionPanel.setLayout(new GridLayout(1, 6));
		//		optionPanel.setPreferredSize(dim1);

		//옵션 패널 버튼 추가

	}

	class ChangeDetector implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			thick = (Integer)spinner.getValue();
			examplepanel.repaint();
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
			Graphics2D g2 =(Graphics2D)g;
			 if(option == ERASE) {
				
				newshape.myfillcolor = Color.white;
				newshape.start = new Point(0, 0);
				newshape.end = new Point(1000, 500);
				newshape.option = option;
				shape.add(newshape);
			}
			System.out.println(shape.size());
			for(int i= 0 ; i<shape.size(); i++) {
				g2.setPaint(shape.get(i).myfillcolor);
				g2.setStroke(new BasicStroke(shape.get(i).thick,BasicStroke.CAP_ROUND,0));
				switch(shape.get(i).option) {
				case LINE : 
					g2.setPaint(shape.get(i).mypencolor);
					g2.drawLine(shape.get(i).start.x, shape.get(i).start.y, shape.get(i).end.x, shape.get(i).end.y);
					break;
				case RECT :
					g2.fillRect(shape.get(i).minx, shape.get(i).miny, shape.get(i).width, shape.get(i).height);
					g2.setPaint(shape.get(i).mypencolor);
					g2.drawRect(shape.get(i).minx, shape.get(i).miny, shape.get(i).width, shape.get(i).height);
					break;
				case CIRCLE :
					g2.fillOval(shape.get(i).minx, shape.get(i).miny, shape.get(i).width, shape.get(i).height);
					g2.setColor(shape.get(i).mypencolor);
					g2.drawOval(shape.get(i).minx, shape.get(i).miny, shape.get(i).width, shape.get(i).height);
					break;
				case POLYLINE :
					System.out.println("size : " + shape.get(i).size  + " option : " + shape.get(i).option);
					g2.setPaint(shape.get(i).mypencolor);
					g2.drawPolyline(shape.get(i).array_x, shape.get(i).array_y, shape.get(i).size);
					break;
				case SKETCH : case ERASER :
					g2.setPaint(shape.get(i).mypencolor);
					for(int j = 1; j< shape.get(i).sketchSP.size(); j++) {
						g2.drawLine(shape.get(i).sketchSP.get(j-1).x, shape.get(i).sketchSP.get(j-1).y, shape.get(i).sketchSP.get(j).x, shape.get(i).sketchSP.get(j).y);
					}
					break;
				case ERASE :
					g2.setPaint(shape.get(i).myfillcolor);
					g2.fillRect(shape.get(i).start.x, shape.get(i).start.y, Math.abs(shape.get(i).end.x-shape.get(i).start.x), Math.abs(shape.get(i).end.y-shape.get(i).start.y));
					
				}
			}

			//그림자 그리기
			if(start != null) {
				
				if(option == ERASER) {
					g2.setColor(Color.white);
					g2.setStroke(new BasicStroke(eraserthick,BasicStroke.CAP_ROUND,0));
				}
				else {
					g2.setPaint(myfillcolor);
					g2.setStroke(new BasicStroke(thick,BasicStroke.CAP_ROUND,0));

				}
				if(option == LINE ) {
					g2.setPaint(mypencolor);
					g2.drawLine(start.x, start.y, end.x, end.y);
				}
				else if(option == RECT) {
					g2.fillRect(minx, miny, width, height);
					g2.setPaint(mypencolor);
					g2.drawRect(minx, miny, width, height);
				}
				else if(option == CIRCLE) {
					g2.fillOval(minx, miny, width, height);
					g2.setPaint(mypencolor);
					g2.drawOval(minx, miny, width, height);
				}
				else if(option == POLYLINE) {
					g2.setPaint(mypencolor);
					g2.drawPolyline(tempX, tempY, psize+1);
				}
				else if(option == SKETCH) {
					g2.setPaint(mypencolor);
					for(int i = 1; i < sketSP.size(); i++) {

						g2.drawLine(sketSP.get(i-1).x, sketSP.get(i-1).y, sketSP.get(i).x, sketSP.get(i).y);
					}
				}
				else if(option == ERASER) {
					for(int i = 1; i < sketSP.size(); i++) {

						g2.drawLine(sketSP.get(i-1).x, sketSP.get(i-1).y, sketSP.get(i).x, sketSP.get(i).y);
					}
					
					g2.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,0));
					g2.setColor(Color.black);
					if(end != null)
					g2.drawOval(end.x - eraserthick/2, end.y - eraserthick/2, eraserthick, eraserthick);

				}
				

			}

		}
		public void swap(Point start, Point end) {
			int startx = start.x;
			int starty = start.y;
			int endx = end.x;
			int endy = end.y;
			int temp;

			if(startx >= endx) {
				temp = startx;
				startx = endx;
				endx = temp;
			}
			if(starty >= endy) {
				temp = starty;
				starty = endy;
				endy = temp;
			} 
			start.x = startx;
			start.y = starty;
			end.x = endx;
			end.y = endy;
		}

		//마우스 리스너 클래스
		class MyMouseListener extends MouseAdapter implements MouseMotionListener{
			int temp = 0;
			int makeinstance = 0;
			ShapeRepository newshape;
			ShapeRepository tempshape;

			//마우스 프레스 됐을 때
			public void mousePressed(MouseEvent e) {
//				if(option == DEFAULT) {
//					int i = shape.size();
//					int x = e.getX();
//					int y = e.getY();
//					while(shape.isEmpty() == false) {
//						
//						tempshape = shape.get(i-1);
//						int sx = tempshape.start.x;
//						int sy = tempshape.start.y;
//						int ex = tempshape.end.x;
//						int ey = tempshape.end.y;
//						if(tempshape.option == LINE) {
//							
//						}
//					}
//				}
				if(makeinstance == 0 && option != DEFAULT) {
					redoshape.removeAllElements();
					newshape = new ShapeRepository();
					if(option == ERASER) {
						newshape.mypencolor = Color.white;
						newshape.thick = eraserthick;
					}
					
						
					else {
						newshape.mypencolor = mypencolor;
						newshape.myfillcolor = myfillcolor;
						newshape.thick = thick;
					}
					newshape.option = option;
					makeinstance = 1;
					System.out.println("new object");
				}

				if(mousepressed == 0 && option != 0) {
					System.out.println("마우스 눌렸다 " + option);

					mousepressed += 1;
				}
				else mousepressed = 0;

				if(option == POLYLINE) {
					if(mousepressed == 1) {
//						newshape.array_x[psize] = e.getX();
//						newshape.array_y[psize] = e.getY();
						tempX[psize] = e.getX();
						tempY[psize] = e.getY();
					}
					psize++;
//					newshape.array_x[psize] = e.getX();
//					newshape.array_y[psize] = e.getY();
					tempX[psize] = e.getX();
					tempY[psize] = e.getY();

					if(e.getButton()==MouseEvent.BUTTON3) {
						System.out.println("psize : " + psize);
						newshape.size = psize;
						for(int i = 0 ; i < psize ; i++) {
							newshape.array_x[i] = tempX[i];
							newshape.array_y[i] = tempY[i];
						}
						shape.add(newshape);
						makeinstance = 0;
						psize = 0;
						option = DEFAULT;
						repaint();
					}
				}
				if(e.getButton()==MouseEvent.BUTTON3) {
					option = DEFAULT;
				}

				else if(option == SKETCH || option == ERASER) {
					newshape.sketchSP.add(e.getPoint());
					sketSP.add(e.getPoint());
				}
				String t = "";
				if(option == 0) t = "DEFAULT";
				else if(option == 1) t = "LINE";
				else if(option == 2) t = "RECTANGLE";
				else if(option == 3) t = "CIRCLE";
				else if(option == 4) t = "POLYLINE";
				else if(option == 5) t = "SKETCH";
				else if(option == 6) t = "ERASE";
				else if(option == 7) t = "ERASER";
				else if(option == 8) t = "UNDO";
				else if(option == 9) t = "REDO";
				
				mode.setText("[Mode] = [" + t + "]");
				start = e.getPoint();
			}

			//마우스 릴리즈 됐을 때
			public void mouseReleased(MouseEvent e) {
				if(option != DEFAULT)shape.add(newshape);
				
				end = e.getPoint();
				if(option == ERASER) end = null;
				makeinstance = 0;
				if(option == RECT || option == CIRCLE) {
					minx = (int)Math.min(start.getX(), end.getX());
					miny = (int)Math.min(start.getY(), end.getY());
					width = (int)Math.abs(start.getX()- end.getX());
					height = (int)Math.abs(start.getY()- end.getY());
					
					newshape.minx = minx;
					newshape.miny = miny;
					
					newshape.width = width;
					newshape.height = height;
				}
				else if(option == LINE) {
					
					newshape.start = start;
					newshape.end = end;
				}
				else if(option == SKETCH || option == ERASER) {

					sketSP.removeAllElements();
				}

				repaint();
			}



			@Override
			//마우스 드레그 됐을 때
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				if(option == SKETCH || option == ERASER) {
					newshape.sketchSP.add(e.getPoint());
					sketSP.add(e.getPoint());
				}
				end = e.getPoint();
				if(option == RECT || option == CIRCLE) {
					minx = (int)Math.min(start.getX(), end.getX());
					miny = (int)Math.min(start.getY(), end.getY());

					width = (int)Math.abs(start.getX()- end.getX());
					height = (int)Math.abs(start.getY()- end.getY());
				}
				repaint();
			}

			@Override
			//마우스 움직일 때
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				mouse = e.getPoint();
				
				xycoord.setText("[x, y] = [" + mouse.getX() + "] [" +mouse.getY() + "]");
				if(option == POLYLINE) {
					if(psize > 0) {
//						newshape.array_x[psize] = e.getX();
//						newshape.array_y[psize] = e.getY();
						tempX[psize] = e.getX();
						tempY[psize] = e.getY();

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
			mousepressed = 0;
			newshape = new ShapeRepository();
			String temp = myButton.getText();
			
			
			if(temp.equals("l")) {
				if(option == LINE) option = DEFAULT;
				else option = LINE;
				new Canvas();
			}
			else if(temp.equals("▢")) {
				if(option == RECT)option = DEFAULT;
				else option = RECT;
				new Canvas();
			}
			else if(temp.equals("◯")) {
				if(option == CIRCLE)option = DEFAULT;
				else option = CIRCLE;
				new Canvas();
			}
			else if(temp.equals(">")) {
				if(option == POLYLINE)option = DEFAULT;
				else option = POLYLINE;
				new Canvas();

			}
			else if(temp.equals("⌇")) {
				if(option == SKETCH)option = DEFAULT;
				option = SKETCH;
				new Canvas();
			}
			else if(temp.equals("펜색")) {
				mypencolor = JColorChooser.showDialog(null, "색선정", Color.blue);
				examplepanel.repaint();
			}

			else if(temp.equals("채우기색")) {
				myfillcolor = JColorChooser.showDialog(null, "색선정", Color.blue);
				examplepanel.repaint();
			}

			else if(temp.equals("지우개")) {
				if(option == ERASER) option = DEFAULT;
				else {
					option = ERASER;
					JFrame eraser = new JFrame("지우개");
					eraser.setVisible(true);
					eraser.setSize(250,100);
					eraser.setLocation(200,200);


					JSlider eraserSize = new JSlider(JSlider.HORIZONTAL, 0, 50, 15);
					eraserSize.setMajorTickSpacing(10);
					eraserSize.setMinorTickSpacing(1);
					eraserSize.setPaintTicks(true);
					eraserSize.setPaintLabels(true);

					eraser.add(eraserSize);

					eraserSize.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							// TODO Auto-generated method stub
							JSlider source = (JSlider)e.getSource();
							if(!source.getValueIsAdjusting()) {
								eraserthick = (int)source.getValue();

							}

						}
					});

				}
				new Canvas();
			}

			else if(temp.equals("지우기")) {
				option = ERASE;
				canvas.repaint();
				
			}
			
			else if(temp.equals("Undo")) {
				option = UNDO;
				if(shape.isEmpty()==false)
				redoshape.push(shape.pop());
				canvas.repaint();
			}
			else if(temp.equals("Redo")) {
				option = REDO;
				if(redoshape.isEmpty() == false) shape.push(redoshape.pop()); 

				canvas.repaint();
			}
			String t = "";
			if(option == 0) t = "DEFAULT";
			else if(option == 1) t = "LINE";
			else if(option == 2) t = "RECTANGLE";
			else if(option == 3) t = "CIRCLE";
			else if(option == 4) t = "POLYLINE";
			else if(option == 5) t = "SKETCH";
			else if(option == 6) t = "ERASE";
			else if(option == 7) t = "ERASER";
			else if(option == 8) t = "UNDO";
			else if(option == 9) t = "REDO";
			
			mode.setText("[Mode] = [" + t + "]");
		}
	}

	class ExamplePanel extends JPanel{
		ExamplePanel(){
			setPreferredSize(new Dimension(40, 40));
			setLayout(new FlowLayout());
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 =(Graphics2D)g;

			g2.setPaint(myfillcolor);
			g2.fillRect(10, 10, 20, 20);
			g2.setPaint(mypencolor);
			g2.drawRect(10, 10, 20, 20);
			g2.setStroke(new BasicStroke(thick,BasicStroke.CAP_ROUND,0));

		}
	}
	
}






