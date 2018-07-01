package project4_ImageProcessing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame extends JFrame{
	public static final Dimension DIM_FRAME = new Dimension(1440,900);
	public static final Dimension DIM_MAINPANEL = new Dimension(1440,900);
	public static final Dimension DIM_DRAWLABEL1 = new Dimension(560,720);
	public static final Dimension DIM_DRAWLABEL2 = new Dimension(560,720);
	public static final Dimension DIM_BUTTON1 = new Dimension(120,50);



	public static final Point LOC_FRAME = new Point(0,0);
	public static final Point LOC_MAINPANEL = new Point(0, 0);
	public static final Point LOC_DRAWPANEL1 = new Point(20, 80);
	public static final Point LOC_DRAWPANEL2 = new Point(600, 80);

	//메인패널
	JPanel mainPanel = new JPanel();

	//메인패널 들어가는 친구들
	DrawPanel drawPanel1 = new DrawPanel();
	DrawPanel drawPanel2 = new DrawPanel();
	JLabel drawLabel = new JLabel();
	PreviewPanel previewPanel = new PreviewPanel();

	JButton drawBtn = new JButton("Load Image");
	JButton grayBtn = new JButton("Gray Scale");
	JButton edgeBtn = new JButton("Edge Detect");
	JButton lookBtn = new JButton("Look Up");
	JButton distBtn = new JButton("Distinct");
	JButton expBtn = new JButton("돋보기");
	JButton retBtn = new JButton("원래대로");
	JButton undoBtn = new JButton("Undo");
	JSlider brightSlider = new JSlider(JSlider.VERTICAL, -100, 100, 0);
	JSlider contrastSlider = new JSlider(JSlider.VERTICAL, 0, 200, 100);
	JSlider patchSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 3);


	//변수 친구들
	String filePath;
	BufferedImage defaultImage;
	BufferedImage newImage;
	BufferedImage changedImage;
	BufferedImage previewImage = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);
	double [][] red = new double[720][560];
	double [][] blue = new double[720][560];
	double [][] green = new double[720][560];

	double [][] outred = new double[720][560];
	double [][] outblue = new double[720][560];
	double [][] outgreen = new double[720][560];


	Stack<BufferedImage> imageRepo = new Stack<BufferedImage>();
	Stack<Integer> brightRepo = new Stack<Integer>();
	Stack<Float> contraRepo = new Stack<Float>();


	int bright = 0;
	float contrast = 100;
	int width;
	int height;
	int w = 560;
	int h = 720;
	int mx;
	int my;
	int patch = 3;

	int expwidth = 100;
	Ellipse2D.Float expcircle;
	public int expand;


	Frame(){
		this.setVisible(true);
		this.setSize(DIM_FRAME);
		this.setLocation(LOC_FRAME);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel.setSize(Frame.DIM_MAINPANEL);
		mainPanel.setLocation(Frame.LOC_MAINPANEL);
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(22,22,22));


		this.add(mainPanel);

		drawBtn.setSize(DIM_BUTTON1);
		drawBtn.setLocation(1300, 80);
		drawBtn.addActionListener(new OpenActionListener());

		grayBtn.setSize(DIM_BUTTON1);
		grayBtn.setLocation(1300, 140);
		grayBtn.addActionListener(new OpenActionListener());

		edgeBtn.setSize(DIM_BUTTON1);
		edgeBtn.setLocation(1300,200);
		edgeBtn.addActionListener(new OpenActionListener());

		lookBtn.setSize(DIM_BUTTON1);
		lookBtn.setLocation(1300,260);
		lookBtn.addActionListener(new OpenActionListener());
		
		distBtn.setSize(DIM_BUTTON1);
		distBtn.setLocation(1300,320);
		distBtn.addActionListener(new OpenActionListener());	
		
		expBtn.setSize(DIM_BUTTON1);
		expBtn.setLocation(1300,380);
		expBtn.addActionListener(new OpenActionListener());
		
		retBtn.setSize(DIM_BUTTON1);
		retBtn.setLocation(1300,440);
		retBtn.addActionListener(new OpenActionListener());
		
		undoBtn.setSize(DIM_BUTTON1);
		undoBtn.setLocation(1300,500);
		undoBtn.addActionListener(new OpenActionListener());

		mainPanel.add(drawPanel1);
		mainPanel.add(drawPanel2);
		mainPanel.add(previewPanel);
		mainPanel.add(drawBtn);
		mainPanel.add(grayBtn);
		mainPanel.add(edgeBtn);
		mainPanel.add(undoBtn);
		mainPanel.add(retBtn);
		mainPanel.add(expBtn);
		mainPanel.add(distBtn);
		mainPanel.add(lookBtn);
		mainPanel.add(brightSlider);
		mainPanel.add(contrastSlider);
		mainPanel.add(patchSlider);
	


		drawPanel2.setLocation(LOC_DRAWPANEL2);
		drawPanel2.setSize(Frame.DIM_DRAWLABEL2);
		drawPanel2.setBackground(new Color(35,35,35));

		//		drawPanel2.add(drawLabel2);

		drawLabel.setLocation(0,0);
		drawLabel.setSize(DIM_DRAWLABEL2);

		drawPanel2.add(drawLabel);

		drawPanel1.setLocation(Frame.LOC_DRAWPANEL1);
		drawPanel1.setSize(Frame.DIM_DRAWLABEL1);
		drawPanel1.setBackground(new Color(35,35,35));



		previewPanel.setLocation(1200, 650);
		previewPanel.setSize(150,150);
		previewPanel.setBackground(new Color(35,35,35));

		brightSlider.setLocation(1200, 80);
		brightSlider.setForeground(Color.white);
		brightSlider.setSize(30,300);
		brightSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					bright = (int)source.getValue();
					changedImage = deepCopy(briconChange());
					Graphics2D g = (Graphics2D)drawPanel2.getGraphics();
					g.drawImage(changedImage, 0, 0, null);
				}
			}
		});
		
		patchSlider.setLocation(1300, 560);
		patchSlider.setBackground(Color.white);
		patchSlider.setMinorTickSpacing(2);
		patchSlider.setSize(100,30);
		patchSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					System.out.println(patch);
					patch = (int)source.getValue();
				}
			}
		});

		contrastSlider.setLocation(1250, 80);
		contrastSlider.setSize(30,300);
		contrastSlider.setForeground(Color.white);
		contrastSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);
				JSlider source = (JSlider)e.getSource();
				try{
					if(!source.getValueIsAdjusting()) {
						contrast = (float)source.getValue();

						changedImage = deepCopy(briconChange());
						Graphics2D g = (Graphics2D)drawPanel2.getGraphics();
						g.drawImage(changedImage, 0, 0, null);


					}
				}catch(NullPointerException e1) {

				}
			}
		});

	}


	class DrawPanel extends JPanel{
		MyMouseListener ml = new MyMouseListener();
		DrawPanel(){
			this.addMouseMotionListener(ml);
		}


		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(changedImage, 0, 0, null);	
			if(expand == 1) {
				if(mx>25 && my>25 && mx+25<560 && my+25<720) {
					BufferedImage temp = changedImage.getSubimage(mx-25, my-25, 50, 50);
					Image image = temp.getScaledInstance(expwidth, expwidth, Image.SCALE_SMOOTH);
					expcircle = new Ellipse2D.Float(mx - expwidth/2, my - expwidth/2, expwidth, expwidth);
					g2.setClip(expcircle);
					g2.drawImage(image, mx-expwidth/2, my-expwidth/2, expwidth, expwidth, null);
				}
			}

		}
	}

	class PreviewPanel extends JPanel{
		MyMouseListener ml = new MyMouseListener();
		PreviewPanel(){
			this.addMouseMotionListener(ml);
		}

 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;

			Image image;
			if(changedImage != null) {
				if(mx <10) mx = 10;
				if(mx >530) mx = 530;
				if(my-10 < 0)	my = 10;
				if(my >710) my = 710;
				previewImage = changedImage.getSubimage(mx-10, my-10, 20, 20);
				image = previewImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

				g2.drawImage(image, 0, 0, null);
			}
			g2.setColor(Color.red);
			g2.drawLine(0, 75, 150, 75);
			g2.drawLine(75, 0, 75, 150);
		}
	}


	public BufferedImage briconChange() {
		// TODO Auto-generated method stub
		

		int red[][] = new int [newImage.getHeight()][newImage.getWidth()];
		int blue[][] = new int [newImage.getHeight()][newImage.getWidth()];
		int green[][] = new int [newImage.getHeight()][newImage.getWidth()];

		BufferedImage tempImage = deepCopy(newImage);

		for(int y = 0 ; y < newImage.getHeight(); y++) {
			for(int x = 0; x < newImage.getWidth(); x++) {
				Color c = new Color(newImage.getRGB(x, y));

				red[y][x] = c.getRed() + bright;
				blue[y][x] = c.getBlue() + bright;
				green[y][x] = c.getGreen() + bright;
				red[y][x] = Math.max(0, red[y][x]); 
				blue[y][x] = Math.max(0, blue[y][x]); 
				green[y][x] = Math.max(0, green[y][x]);
				red[y][x] = Math.min(255, red[y][x]); 
				blue[y][x] = Math.min(255, blue[y][x]); 
				green[y][x] = Math.min(255, green[y][x]);
				Color d = new Color(red[y][x], green[y][x], blue[y][x]);
				tempImage.setRGB(x, y, d.getRGB());
			}
		}

		RescaleOp rescale = new RescaleOp((float)(contrast/100), 0, null);
		return rescale.filter(tempImage, null);
	}


	class OpenActionListener implements ActionListener{


		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();


			JButton myButton = (JButton)e.getSource();
			String temp = myButton.getText();
			if(temp.equals("Load Image")) {
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG&GIF Images", "jpg", "gif", "jpeg");
				chooser.setFileFilter(filter);

				int ret = chooser.showOpenDialog(null);
				if(ret!= JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
					return;

				}	
				filePath=chooser.getSelectedFile().getPath();


				try {
					brightSlider.setValue(0);
					contrastSlider.setValue(100);
					bright = 0;
					contrast = 100;
					Image resizeImage;
					BufferedImage image;
					File input = new File(filePath);
					image = ImageIO.read(input);
					resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
					newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
					Graphics g = newImage.getGraphics();
					g.drawImage(resizeImage, 0, 0, null);
					g.dispose();
					
					
					defaultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

					changedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

					changedImage = deepCopy(newImage);
					defaultImage = deepCopy(newImage);
					

					Graphics g2 = changedImage.getGraphics();
					g2.drawImage(resizeImage, 0, 0, null);
					g2.dispose();

					Graphics g3 = defaultImage.getGraphics();
					g3.drawImage(resizeImage, 0, 0, null);
					g3.dispose();

					width = newImage.getWidth();
					height = newImage.getHeight();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Graphics2D g4 = (Graphics2D)drawPanel1.getGraphics();
				g4.drawImage(defaultImage, 0, 0, null);

				Graphics2D g5 = (Graphics2D)drawPanel2.getGraphics();
				g5.drawImage(newImage, 0, 0, null);
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);


			}
			else if(temp.equals("Gray Scale")) {
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);
				for(int i=0; i<height; i++) {
					for(int j=0; j<width; j++) {
						Color c = new Color(newImage.getRGB(j, i));
						int red = (int)(c.getRed() * 0.299);
						int green = (int)(c.getGreen() * 0.587);
						int blue = (int)(c.getBlue() * 0.114);
						Color newColor = new Color(red+green+blue, red+green+blue, red+green+blue);
						changedImage.setRGB(j, i, newColor.getRGB());
					}
				}
				Graphics2D g6 = (Graphics2D)drawPanel2.getGraphics();
				g6.drawImage(changedImage, 0, 0, null);
			}
			else if(temp.equals("Edge Detect")) {
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);
				float[] sharpen = new float[] {
					     0.0f, -1.0f, 0.0f,
					    -1.0f, 5.0f, -1.0f,
					     0.0f, -1.0f, 0.0f
					};
					Kernel kernel = new Kernel(3, 3, sharpen);
					ConvolveOp op = new ConvolveOp(kernel);
					changedImage = op.filter(changedImage, null);
				double[][] ar = toArray(changedImage); 
				double[][] filterBlur = 
					{{0.088 , 0.107 , 0.088}, 
					 {0.107 , 0.214 , 0.107},
			   	   	 {0.088 , 0.107 , 0.088}}; 
				ar = convolution(ar, filterBlur); 
				double[][] filterEdge = 
					{ { -1, -1, -1 }, 
					  { -1,  8, -1  }, 
					  { -1, -1, -1  }}; 
				ar = convolution(ar, filterEdge); 
				ar = arrayInColorBound(ar); 
				ar = arrayColorInverse(ar); 
				changedImage = deepCopy(toImage(ar)); 

				Graphics2D g7 = (Graphics2D)drawPanel2.getGraphics();
				g7.drawImage(changedImage, 0, 0, null);
			}
			
			else if(temp.equals("Look Up")) {
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);
				short[] data = new short[256];
				for (short i = 0; i < 256; i++) {
				    data[i] = (short) (255 - i);
				}

				LookupTable lookupTable = new ShortLookupTable(0, data);
				LookupOp op = new LookupOp(lookupTable, null);
				changedImage = op.filter(changedImage, null);
				Graphics2D g7 = (Graphics2D)drawPanel2.getGraphics();
				g7.drawImage(changedImage, 0, 0, null);
			}
			
			else if(temp.equals("Distinct")) {
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);
				float[] sharpen = new float[] {
				     0.0f, -1.0f, 0.0f,
				    -1.0f, 5.0f, -1.0f,
				     0.0f, -1.0f, 0.0f
				};
				Kernel kernel = new Kernel(3, 3, sharpen);
				ConvolveOp op = new ConvolveOp(kernel);
				changedImage = op.filter(changedImage, null);
				Graphics2D g7 = (Graphics2D)drawPanel2.getGraphics();
				g7.drawImage(changedImage, 0, 0, null);
			}
			
			else if(temp.equals("돋보기")) {
				if(expand == 1) expand =0;
				else expand = 1;

				new DrawPanel();
			}

			else if(temp.equals("원래대로")) {
				imageRepo.push(deepCopy(changedImage));
				brightRepo.push(bright);
				contraRepo.push(contrast);

				changedImage = deepCopy(defaultImage);
				newImage = deepCopy(defaultImage);
				Graphics2D g = (Graphics2D)drawPanel2.getGraphics();
				g.drawImage(changedImage, 0, 0, null);
				brightSlider.setValue(0);
				contrastSlider.setValue(100);
				bright = 0;
				contrast = 100;
			}
			else if(temp.equals("Undo")) {
				if(imageRepo.isEmpty() == false) {
					System.out.println("undo");
					changedImage = deepCopy(imageRepo.pop());
					Graphics2D g = (Graphics2D)drawPanel2.getGraphics();
					g.drawImage(changedImage, 0, 0, null);
					bright = brightRepo.pop();
					contrast = contraRepo.pop();
					brightSlider.setValue(bright);
					contrastSlider.setValue((int) contrast);
				}
			}




		}

	}

	class MyMouseListener implements MouseMotionListener, MouseListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			mx = e.getX();
			my = e.getY();

			toColorArray();
			
			previewPanel.repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mx = e.getX();
			my = e.getY();
			if(expand == 1) {
				drawPanel2.repaint();
			}
			previewPanel.repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			imageRepo.push(deepCopy(changedImage));
			brightRepo.push(bright);
			contraRepo.push(contrast);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	public void toColorArray() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				Color c = new Color(changedImage.getRGB(x, y));
				red[y][x] = c.getRed();
				blue[y][x] = c.getBlue();
				green[y][x] = c.getGreen();
				outred[y][x] = c.getRed();
				outblue[y][x] = c.getBlue();
				outgreen[y][x] = c.getGreen();
			}
		}



		for(int y = my - 3 ; y < my + 3; y++) {
			for(int x = mx - 3 ; x < mx + 3; x++) {

				outred[y][x] = 0;
				outgreen[y][x] = 0;
				outblue[y][x] = 0;
			}
		}

		for(int y = my - 3 ; y < my + 3; y++) {
			for(int x = mx - 3 ; x < mx + 3; x++) {
				for(int i = 0; i< patch; i++) {
					for(int j = 0; j < patch; j++) {
						outred[y][x] += red[y-1+i][x-1+j]/(patch*patch);
						outblue[y][x] += blue[y-1+i][x-1+j]/(patch*patch);
						outgreen[y][x] += green[y-1+i][x-1+j]/(patch*patch);
					}
				}

			}
		}

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				changedImage.setRGB(x, y, new Color((int) outred[y][x], (int) outgreen[y][x], (int) outblue[y][x]).getRGB());
			}
		}

		Graphics2D g7 = (Graphics2D)drawPanel2.getGraphics();
		g7.drawImage(changedImage, 0, 0, null);
	}

	public double[][] toArray(BufferedImage bi) {
		double [][] output = new double[bi.getHeight()][bi.getWidth()];
		for(int y = 0; y < bi.getHeight(); y++) {
			for(int x = 0; x < bi.getWidth(); x++) {
				Color c = new Color(bi.getRGB(x, y));
				output [y][x] = (c.getRed() + c.getBlue() + c.getGreen())/3;
			}
		}
		return output;
	}

	public static BufferedImage toImage(double[][] ar) { 
		BufferedImage output = new BufferedImage(ar[0].length, ar.length, BufferedImage.TYPE_INT_BGR); 
		for (int y = 0; y < ar.length; y++) { 
			for (int x = 0; x < ar[0].length; x++) { 
				output.setRGB(x, y, new Color((int) ar[y][x], (int) ar[y][x], (int) ar[y][x]).getRGB()); 
			} 
		} 
		return output; 
	} 

	public double[][] convolution(double map[][], double[][] filter){

		double output[][] = new double[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) { 
			for (int x = 0; x < map[y].length; x++) { 
				for (int i = 0; i < filter.length; i++) { 
					for (int j = 0; j < filter[i].length; j++) { 
						try { 
							output[y][x] += map[y - i + 1][x - j + 1] * filter[i][j]; 
						} catch (ArrayIndexOutOfBoundsException e) { 
						}
					}
				}
			}
		}
		return output;

	}

	public double[][] arrayInColorBound(double[][] ar){
		for (int i = 0; i < ar.length; i++) { 
			for (int j = 0; j < ar[i].length; j++) { 
				ar[i][j] = Math.max(0, ar[i][j]); 
				ar[i][j] = Math.min(225, ar[i][j]); 
			} 
		} 
		return ar; 
	}
	public static double[][] arrayColorInverse(double[][] ar) { 
		for (int i = 0; i < ar.length; i++) { 
			for (int j = 0; j < ar[i].length; j++) { 
				ar[i][j] = 255 - ar[i][j]; 
			} 
		} 
		return ar; 
	} 

	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

}