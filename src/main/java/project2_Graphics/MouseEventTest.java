package project2_Graphics;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
 
class MyFrame extends JFrame implements MouseListener, MouseMotionListener
{
	int startx;
	int starty;
	int endx;
	int endy;
	
    JFrame frm = new JFrame();
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    boolean pressed = false;
    public MyFrame()
    {
        //기본적인 프레임 셋팅
        frm.setTitle("마우스 이벤트 핸들링");
        frm.setLayout(new GridLayout(3,0));
        frm.setBounds(120, 120, 300, 100);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        //컴포넌트 추가
        frm.add(label1);
        frm.add(label2);
        frm.add(label3);
        //이벤트 핸들러 추가
        frm.addMouseListener(this);
        frm.addMouseMotionListener(this);
    }
    @Override
    public void mouseDragged(MouseEvent e)
    {
        label1.setText("마우스 상태 : 드레그");
        label2.setText(" X : " + e.getX() + "Y : " + e.getY());
        label3.setText("마우스 버튼 : "+e.getButton());
        
        //g.drawLine(startx, starty, e.getX(), e.getY());
        
    }
    @Override
    public void mouseMoved(MouseEvent e)
    {
        label1.setText("마우스 상태 : 이동");
        label2.setText(" X : " + e.getX() + "Y : " + e.getY());
        label3.setText("마우스 버튼 : "+e.getButton());
        
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        label1.setText("마우스 상태 : 클릭");
        label2.setText(" X : " + e.getX() + "Y : " + e.getY());
        label3.setText("마우스 버튼 : "+e.getButton());
    }
    @Override
    public void mouseEntered(MouseEvent e) 
    {
        label1.setText("마우스 상태 : 컴포넌트 안 진입");
        label2.setText(" X : " + e.getX() + "Y : " + e.getY());
        label3.setText("마우스 버튼 : "+e.getButton());
    }
    @Override
    public void mouseExited(MouseEvent e) 
    {
        label1.setText("마우스 상태 : 컴포넌트 밖");
        label2.setText(" X : " + e.getX() + "Y : " + e.getY());
        label3.setText("마우스 버튼 : "+e.getButton());
    }
    @Override
    public void mousePressed(MouseEvent e) 
    {
        label1.setText("마우스 상태 : 눌림");
        label2.setText(" X : " + e.getX() + "Y : " + e.getY());
        label3.setText("마우스 버튼 : "+e.getButton());
        startx = e.getX();
        starty = e.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {
        label1.setText("마우스 상태 : 풀림");
        label2.setText(" X : " + e.getX() + "Y : " + e.getY());
        label3.setText("마우스 버튼 : "+e.getButton());
        endx = e.getX();
        endy = e.getY();
        Graphics g = getGraphics();
        g.drawLine(startx, starty, endx, endy);
    }
    
}
 
public class MouseEventTest 
{
    public static void main(String[] args) 
    {
        MyFrame my = new MyFrame();
    }
}
