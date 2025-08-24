package ui;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import static utils.Constants.UI.VolButtons.*;

public class VolumeButton extends PauseButton{
	
	private BufferedImage[] imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseHover, mousePressed;
	private int buttonX, minX, maxX;
	
	
	public VolumeButton(int x, int y, int width, int height) {
		super(x + (width-VOLUME_WIDTH), y, VOLUME_WIDTH, height);
		buttonX = x + (width-VOLUME_WIDTH);
		this.x = x;
		minX = x;
		maxX = x + (width-VOLUME_WIDTH);
		this.width =width;
		
		loadImgs();
		// TODO Auto-generated constructor stub
	}
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTON);
		imgs = new BufferedImage[3];
		for(int i = 0; i<imgs.length;i++) {
			imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
		}
		slider = temp.getSubimage(3*VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH,VOLUME_DEFAULT_HEIGHT);
	}

	public void update() {
		index = 0;
		if(mouseHover) {
			index = 1;
		}
		if(mousePressed) {
			index = 2;
		}
		
		}
	
	public void draw(Graphics g) {
		
		g.drawImage(slider, x, y,width, height, null);
		g.drawImage(imgs[index], buttonX, y, VOLUME_WIDTH, height, null);
	}
	
	public void changeX(int x) {
		if(x < minX) {
			buttonX = minX;
		
		}
		else if(x>maxX) {
			buttonX = maxX;
		
		}
		else {
			buttonX = x;
		
		}
		bounds.x = buttonX;
		
	}
	public void resetBools() {
		mouseHover = false;
		mousePressed =false;
	}

	public boolean isMouseHover() {
		return mouseHover;
	}

	public void setMouseHover(boolean mouseHover) {
		this.mouseHover = mouseHover;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	

}
