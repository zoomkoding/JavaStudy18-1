package project1;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Frame {
	Stack<String> stack = new Stack<String>();
	int pre = -1;
	int op = 0;
	public Frame() {
		prepareFrame();
	}

	private void prepareFrame() {
		stack.push("0");
		JFrame frame = new JFrame("Calculator");
		frame.setVisible(true);
		frame.setSize(240, 320);
		frame.setLocation(800, 500);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		JLabel label = new JLabel("0");
		panel.setLayout(null);
		frame.add(label);
		label.setBounds(0, 0, 240, 100);
		label.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		frame.add(panel);
		JButton[] btn = new JButton[16];
		
		for(int i = 0 ; i<10; i++) {

			btn[i] = new JButton(String.valueOf(i));
			if(i == 0) 
				btn[0].setBounds(0, 250, 60, 50);
			else
				btn[i].setBounds(0+60*((i-1)%3), 300-50*((i-1)/3+2), 60, 50);

			panel.add(btn[i]);

			btn[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {

					JButton myButton = (JButton)e.getSource();
					String temp = myButton.getText();
					if(op == 0) {
						double num = Double.parseDouble(temp);
						double i = Double.parseDouble(stack.pop());
						num += i * 10;
						temp = String.valueOf(num);
						while(temp.contains(".") && (temp.endsWith("0") || temp.endsWith("."))) {	
							temp = temp.substring(0, temp.length()-1);
						}
						stack.push(temp);
						label.setText(temp);
					}
					else if(op == 1){
						stack.push(temp);
						label.setText(temp);
						op = 0;
					}					
					else {
						stack.pop();
						stack.push(temp);
						label.setText(temp);
						op = 0;
					}
				}
			});
		}
		btn[10] = new JButton("+");
		btn[11] = new JButton("-");
		btn[12] = new JButton("*");
		btn[13] = new JButton("/");
		btn[14] = new JButton(".");
		btn[15] = new JButton("=");

		for(int i = 10; i<16; i++) {
			btn[i].setBounds(180, 100+50*(i%10), 60, 50);
			panel.add(btn[i]);
			btn[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {


					JButton myButton = (JButton)e.getSource();
					String temp = myButton.getText();
					String top;

					if(temp.equals("*") || temp.equals("/")) {
						if(pre == 1) stack.push(temp);

						else {

							cal(false);
							top = stack.pop();
							label.setText(top);
							stack.push(top);
							stack.push(temp);

						}
						pre = 2;
						op = 1;


					}

					else if(temp.equals("+") || temp.equals("-")) {
						pre = 1;
						cal(true);
						top = stack.pop();
						label.setText(top);
						stack.push(top);
						stack.push(temp);
						op = 1;
						
					}
					
					
					else if(temp.equals("=")) {
						cal(true);
						top = stack.pop();
						label.setText(top);
						stack.push(top);
						init();
					}
				}
				

			});


		}


		for(int i = 14 ; i<btn.length; i++) {
			btn[i].setBounds(60+60*(i-14), 250, 60, 50);
			panel.add(btn[i]);
		}

	}

	public void cal(boolean go) {
		int o = 0;
		double num2 = Double.parseDouble(stack.pop());
		double num1 = -1;
		while(!stack.isEmpty()) {
			String temp = stack.pop();
			if((temp.equals("-") || temp.equals("+")) && go == false) {
				stack.push(temp);
				break;
			}
			if(temp.equals("+") || temp.equals("-") || temp.equals("/") || temp.equals("*")) {
				
				if(o == 1) num2 = num1 + num2;
				else if(o == 2) num2 = num1 - num2;
				else if(o == 3) num2 = num1 * num2;
				else if(o == 4) num2 = num1 / num2;
				
				if(temp.equals("+")) o = 1;
				else if(temp.equals("-")) o = 2;
				else if(temp.equals("*")) o = 3;
				else if(temp.equals("/")) o = 4;
				


			}
			else num1 = Double.parseDouble(temp); 
		}
		if(o == 1) num1 += num2;
		else if(o == 2) num1 -= num2;
		else if(o == 3) num1 *= num2;
		else if(o == 4) num1 /= num2;
		else if(o == 0) num1 = num2;
		
		String temp = String.valueOf(num1);
		while(temp.contains(".") && (temp.endsWith("0") || temp.endsWith("."))) {	
			temp = temp.substring(0, temp.length()-1);
		}
		stack.push(temp);
	}

	public void init() {
		pre = -1;
		op = 2;
		
	}
}
