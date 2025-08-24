package main;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Dimension;




import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;


public class Game implements Runnable{
	
private GameWindow gameWindow;
private GamePanel gamePanel;
private Thread gameThread;
private final int FPS_SET = 120; //set the fps for the game loop
private final int UPS_SET = 200; //set the ups for the game loop

private Playing playing;
private Menu menu;

public final static int TILES_DEFAULT_SIZE = 32;

public final static int TILES_IN_WIDTH= 26;
public final static int TILES_IN_HEIGHT = 14;

//At class level, before SCALE is initialized
private static float calculateSafeScale(float desiredScale) {
 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
 float maxScaleW = (screenSize.width * 0.9f) / (TILES_DEFAULT_SIZE * TILES_IN_WIDTH);
 float maxScaleH = (screenSize.height * 0.9f) / (TILES_DEFAULT_SIZE * TILES_IN_HEIGHT);
 float maxScale = Math.min(maxScaleW, maxScaleH);
 return Math.min(desiredScale, maxScale);
}

//Then use it to initialize SCALE
public final static float SCALE = calculateSafeScale(2.0F);
public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;






		public Game() {
			initClasses();
			
			gamePanel = new GamePanel(this);
			gameWindow = new GameWindow(gamePanel);
			gamePanel.requestFocus();
			
			startGameLoop();
			// In Game.java constructor or main
			}
		
		private void initClasses() {
			menu = new Menu(this);
			playing  = new Playing(this);
		
        
		}

		private void startGameLoop() {
			gameThread = new Thread(this);
			gameThread.start();
		}
		public void update() {
			
			
			switch(Gamestate.state) {
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTIONS:				
			case QUIT:
			default:
				System.exit(0);
				break;
			
			}
			
		}
		
		public void render(Graphics g) {
			
			
			switch(Gamestate.state) {
			case MENU:
				menu.draw(g);
				break;
			case PLAYING:
				playing.draw(g);
				break;
			default:
				break;
			
			}
			
		}

		@Override
		public void run() {   //gameloop
			
			double timePerFrame = 1000000000/ FPS_SET;   //We are calculating the no. of nanosecond that each frame should run for
			double timePerUpdate = 1000000000/ UPS_SET;  

			long lastFrame = System.nanoTime();	
		
			
			long previousTime = System.nanoTime();
			
			int frames = 0;
			int updates = 0;
			long lastCheck = System.currentTimeMillis();
			
			double deltaU = 0;
			double deltaF = 0;
			
			
			while(true) {
				long currentTime = System.nanoTime();
				
				deltaU += (currentTime - previousTime)/timePerUpdate;
				deltaF += (currentTime - previousTime)/timePerFrame;

				previousTime = currentTime;
				
				if(deltaU >= 1) {
					update();
					updates++;
					deltaU--;
				}
				// Cap delta to prevent spiral of death
		        if (deltaU > 2) {
		            deltaU = 0; // Reset if falling behind
		        }

		     // Sleep to reduce CPU usage (optional)
				// Instead of Thread.sleep(1):
				long remainingTime = (long)(timePerFrame - (System.nanoTime() - lastFrame));
				if (remainingTime > 1_000_000) {  // If >1ms left in the frame
				    try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  // Yield CPU
				} else if (remainingTime > 0) {
				    Thread.yield();    // Gentle yield for sub-millisecond waits
				}
				
				if(deltaF >= 1) {
					gamePanel.repaint();
					frames++;
					deltaF--;
				}
				if (deltaF > 2) {
		            deltaF = 0; // Reset if falling behind
		        }
				
				if(System.currentTimeMillis()-lastCheck >=1000) {  //fps counter
					lastCheck = System.currentTimeMillis();
					System.out.println("FPS: "+ frames + " | UPS: "+ updates);
					frames = 0;
					updates=0;
				}
				
			}
			
		}
		
		

		public void windowFocusLost() {
			if(Gamestate.state == Gamestate.PLAYING) {
				playing.getPlayer().resetDirBooleans();
			}
			
		}
		
		public Menu getMenu() {
			return menu;
		}
		public Playing getPlaying() {
			return playing;
		}

}
