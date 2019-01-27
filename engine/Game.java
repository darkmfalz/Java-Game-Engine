package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -7231590639889999102L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;
	
	private Handler handler;
	
	public Game() {
		//Object handler init
		handler = new Handler();
		//Key listener init
		this.addKeyListener(new KeyInput(handler));
		handler.addObject(new Player(WIDTH / 2, HEIGHT / 2, ID.Player));
		new Window(WIDTH, HEIGHT, "Game", this);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long prevTime = System.nanoTime();
		//Number of ticks in a second
		double numTicks = 60.0;
		double nsPerTick = 1000000000 / numTicks;
		double delta = 0;
		//For monitoring FPS
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long presTime = System.nanoTime();
			//Number of elapsed ticks
			delta += (presTime - prevTime) / nsPerTick;
			//Update time
			prevTime = presTime;
			//Perform specified number of ticks
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS:" + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		handler.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0,  0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		new Game();
	}

}
