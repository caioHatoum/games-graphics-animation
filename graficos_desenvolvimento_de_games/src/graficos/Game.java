package graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	
	
	public static JFrame frame;
	private boolean isRunning;
	private Thread thread;
	private final int width = 160;
	private final int heigth = 120;
	private final int scale = 9;
	private BufferedImage img;
	private SpriteSheet sheet;
	private BufferedImage[] fire;
	private int x=0;
	private int xMax = 60;
	private int cur=0,curMax=2;
	
	
	public Game(){
		sheet = new SpriteSheet("/spritesheet.png");
		fire = new BufferedImage[2];
		fire[0]=sheet.getSprite(0, 0, 16, 16);
		fire[1]=sheet.getSprite(16, 0, 16, 16);
		setPreferredSize(new Dimension(width*scale,heigth*scale));
		initFrame();
		img = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
	}
	
	//inicia graficos
	private void initFrame() {
	frame = new JFrame("joguinho de magia");
		
		//add canvas
		frame.add(this);
		//janela nao redimensionavel
		frame.setResizable(false);
		frame.pack();
		//janela centro
		frame.setLocationRelativeTo(null);
		//finalizar game ao fechar
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//deixa janela visivel ao iniciar
		frame.setVisible(true);
	}
	
	//metodo start game
	public synchronized void start() {
		//inicia thread para execussão de mais de um codigo por x
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//metodo main
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	//atualização
	public void tick() {
		x++;
		if(x>xMax) {
			x=0;
			cur++;
			if(cur>=curMax) {
				cur=0;
			}
		}
		
	}
	//renderiza
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = img.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, width*scale, heigth*scale);
		
		//renderização do jogo\/
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(fire[cur],20,20,null);
		
		
		g.dispose();
		
		
		g= bs.getDrawGraphics();
		g.drawImage(img, 0, 0, width*scale,heigth*scale,null);
		bs.show();
		
		
	}
	
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta +=(now-lastTime)/ns;
			lastTime = now;
			if(delta>=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis()-timer>=1000) {
				System.out.println("FPS:"+frames);
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}
	
}
