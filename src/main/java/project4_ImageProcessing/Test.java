package project4_ImageProcessing;


import java.awt.Color; 
import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.IOException; 

import javax.imageio.ImageIO; 


public class Test { 

	public static void main(String[] args) { 
		long st = System.nanoTime(); 
		String path = "이미지 경로"; 
		BufferedImage bi = getImage(path); 
		double[][] ar = im2ar(bi); 
		double[][] filterBlur = 
				{{0.088 , 0.107 , 0.088}, 
				 {0.107 , 0.214 , 0.107},
				 {0.088 , 0.107 , 0.088}}; 
		ar = convolution(ar, filterBlur); 
		double[][] filterEdge = { { 1, 1, 1 }, { 1, -8, 1 }, { 1, 1, 1 } }; 
		ar = convolution(ar, filterEdge); 
		ar = arrayInColorBound(ar); 
		ar = arrayColorInverse(ar); 
		BufferedImage output = ar2im(ar); 

		//여기서 Show는 직접 제작한 라이브러리입니다.
		//여러분은 이미지파일을 저장하도록 프로그래밍을 해서 확인해보세요.

		//Show.getFrame(output).setVisible(true); 
		System.out.println("End : " + (System.nanoTime() - st) / 1000000000.0 + "s"); 
	} 

	public static BufferedImage getImage(String path) { 
		try { 
			return ImageIO.read(new File(path)); 
		} catch (IOException e) { 
			return null; 
		} 
	} 

	public static double[][] im2ar(BufferedImage bi) { 
		double[][] output = new double[bi.getHeight()][bi.getWidth()]; 
		for (int y = 0; y < bi.getHeight(); y++) { 
			for (int x = 0; x < bi.getWidth(); x++) { 
				Color c = new Color(bi.getRGB(x, y)); 
				output[y][x] += c.getRed(); 
				output[y][x] += c.getGreen(); 
				output[y][x] += c.getBlue(); 
				output[y][x] /= 3.0; 
			} 
		} 
		return output; 
	} 

	public static BufferedImage ar2im(double[][] ar) { 
		BufferedImage output = new BufferedImage(ar[0].length, ar.length, BufferedImage.TYPE_INT_BGR); 
		for (int y = 0; y < ar.length; y++) { 
			for (int x = 0; x < ar[y].length; x++) { 
				output.setRGB(x, y, new Color((int) ar[y][x], (int) ar[y][x], (int) ar[y][x]).getRGB()); 
			} 
		} 
		return output; 
	} 

	public static double[][] convolution(double[][] map, double[][] filter) { 
		int c = 0; 
		if (filter.length % 2 == 1) { 
			int w = filter.length / 2; 
			double[][] output = new double[map.length][map[0].length]; 
			for (int y = 0; y < map.length; y++) { 
				for (int x = 0; x < map[y].length; x++) { 
					for (int i = 0; i < filter.length; i++) { 
						for (int j = 0; j < filter[i].length; j++) { 
							try { 
								output[y][x] += map[y - i + w][x - j + w] * filter[i][j]; 
								c++; 
							} catch (ArrayIndexOutOfBoundsException e) { 

							} 
						} 
					} 
				} 
			} 
			System.out.println(c + "번의 연산을 하였습니다."); 
			return output; 
		} else { 
			return null; 
		} 
	} 

	public static double[][] getFilter(int size) { 
		int x = size / 2; 
		double[][] output = new double[size][size]; 
		double sum = 0; 
		for (int i = 0; i < size; i++) { 
			for (int j = 0; j < size; j++) { 
				output[i][j] = 1.0 / (1 + Math.pow(Math.pow(i - x, 2) + Math.pow(j - x, 2), 0.5)); 
				sum += output[i][j]; 
			} 
		} 
		for (int i = 0; i < size; i++) { 
			for (int j = 0; j < size; j++) { 
				output[i][j] /= sum; 
			} 
		} 
		return output; 
	} 

	public static double[][] arrayInColorBound(double[][] ar) { 
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
} 