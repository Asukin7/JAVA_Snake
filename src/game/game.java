package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class game extends JFrame {

	private Point food = new Point();//食物

	private LinkedList<Point> sankeList = new LinkedList<Point>();//蛇身体
	private LinkedList<Point> fxList = new LinkedList<Point>();//蛇方向
	
	private int keyFlag = 1;//按键开关
	private int key = 37;//按键
	
	//初始化食物和蛇
	public void init() {
		food.setLocation(90, 90);
		sankeList.add(new Point(300, 300));
		fxList.add(new Point(37, 0));
		sankeList.add(new Point(330, 300));
		fxList.add(new Point(37, 37));
		sankeList.add(new Point(360, 300));
		fxList.add(new Point(37, 37));
		sankeList.add(new Point(390, 300));
		fxList.add(new Point(37, 37));
		sankeList.add(new Point(420, 300));
		fxList.add(new Point(37, 37));
		sankeList.add(new Point(450, 300));
		fxList.add(new Point(37, 37));
	}
	
	public game() {
		this.setTitle("贪吃蛇");
		this.setResizable(false);//禁止修改窗体大小
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击关闭时程序退出
		this.setLocationRelativeTo(null);//窗体居中显示
		this.setVisible(true);	
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(keyFlag == 1) {
					int keyTemp = e.getKeyCode();
					if(keyTemp >= 37 && keyTemp <= 40) {
						if(Math.abs(key - keyTemp) != 2) {
							keyFlag = 0;
							key = keyTemp;
						}
					}
				}
			}
		});
		
		this.init();
		
		new Thread(new MoveThread()).start();
	}

	public void paint(Graphics g) {
		Image img = createImage(1000, 1000);
		Graphics g0 = img.getGraphics();
		g0.setColor(Color.darkGray);
		g0.fillRect(0, 0, 1000, 1000);
		
		g0.translate(50, 50);
		g0.setColor(Color.WHITE);
		g0.drawRect(0, 0, 900, 900);
		
		g0.setColor(Color.ORANGE);
		for(int i = 0; i < sankeList.size(); i++) {
			switch (fxList.get(i).x) {
			case 37:
				g0.fillRect(sankeList.get(i).x + 10, sankeList.get(i).y + 8, 20, 14);
				break;
			case 38:
				g0.fillRect(sankeList.get(i).x + 8, sankeList.get(i).y + 10, 14, 20);
				break;
			case 39:
				g0.fillRect(sankeList.get(i).x, sankeList.get(i).y + 8, 20, 14);
				break;
			case 40:
				g0.fillRect(sankeList.get(i).x + 8, sankeList.get(i).y, 14, 20);
				break;
			default:
				break;
			}
			switch (fxList.get(i).y) {
			case 37:
				g0.fillRect(sankeList.get(i).x, sankeList.get(i).y + 8, 20, 14);
				break;
			case 38:
				g0.fillRect(sankeList.get(i).x + 8, sankeList.get(i).y, 14, 20);
				break;
			case 39:
				g0.fillRect(sankeList.get(i).x + 10, sankeList.get(i).y + 8, 20, 14);
				break;
			case 40:
				g0.fillRect(sankeList.get(i).x + 8, sankeList.get(i).y + 10, 14, 20);
				break;
			default:
				break;
			}
		}
		
		g0.setColor(Color.RED);
		g0.fillOval(food.x + 8, food.y + 8, 14, 14);

		g.drawImage(img, 0, 0, 1000, 1000, this);
	}

	class MoveThread implements Runnable {
		public void run() {
			while(true) {
				try {
					Thread.sleep(160);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				Point p = sankeList.getFirst().getLocation();
				Point p_fx = fxList.getFirst().getLocation();
				switch (key) {
				case 37:
					keyFlag = 1;
					p.x -= 30;
					fxList.getFirst().y = 37;
					p_fx.x = 37;
					p_fx.y = 0;
					break;
				case 38:
					keyFlag = 1;
					p.y -= 30;
					fxList.getFirst().y = 38;
					p_fx.x = 38;
					p_fx.y = 0;
					break;
				case 39:
					keyFlag = 1;
					p.x += 30;
					fxList.getFirst().y = 39;
					p_fx.x = 39;
					p_fx.y = 0;
					break;
				case 40:
					keyFlag = 1;
					p.y += 30;
					fxList.getFirst().y = 40;
					p_fx.x = 40;
					p_fx.y = 0;
					break;
				default:
					break;
				}
				
				if(p.x < 0 || p.x > 870 || p.y < 0 || p.y > 870 || sankeList.contains(p)) {
					JOptionPane.showMessageDialog(null, "游戏结束");
					break;
				}
				
				sankeList.addFirst(p);
				fxList.addFirst(p_fx);
				
				if(p.equals(food)) {
					Point xy;
					do {
					xy = new Point((int)(Math.random() * 30) * 30, (int)(Math.random() * 30) * 30);
					}while(sankeList.contains(xy));
					
					food.setLocation(xy);
				}
				else {
					sankeList.removeLast();
					fxList.removeLast();
				}
				
				game.this.repaint();
			}
		}
	}
	
	public static void main(String[] args) {
		new game();
	}

}
