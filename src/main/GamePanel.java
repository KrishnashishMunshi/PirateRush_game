package main;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;



import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;


public class GamePanel extends JPanel{

	private MouseInputs mouseInputs;
	private KeyboardInputs keyboardInputs;
	private int frames=0;
	private long lastCheck=0;
	
	private float xDelta = 100, yDelta = 100;
	
	private BufferedImage img;
	private BufferedImage[][] animations;    //2d array so that all the each animation slides in one dimension, and all the animation types in another dimension
	private int aniTick, aniIndex, aniSpeed =15;
	private int playerAction = IDLE;
	private int playerDir = -1;
	private boolean moving = false;
	private Game game;
	
	public GamePanel(Game game) {

		this.game = game;
		mouseInputs = new MouseInputs(this);
		keyboardInputs = new KeyboardInputs(this);
		setPanelSize();
		addKeyListener(keyboardInputs);
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	

	private void setPanelSize() {
		Dimension size  = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}
	



	public void updateGame() {
				
	}

	public void paintComponent(Graphics g) {    //Gets called automatically whenever we start the game(call a JPanel object)
		super.paintComponent(g);				//enable jpanel's own paint component method to do its work and reset the panel for our painting 
		
		game.render(g);
		
		}
	
	public Game getGame() {
		return game;
	}

}
