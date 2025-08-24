package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import static utils.Constants.UI.URMbuttons.*;

public class UrmButton extends PauseButton{

	private BufferedImage[] imgs;
	private int rowIndex, index;
	private boolean mouseHover, mousePressed;
	
	public UrmButton(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadImgs();
		
	}
	
	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
		imgs = new BufferedImage[3];
		for(int i=0;i<imgs.length;i++) {
			imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowIndex*URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
		}
		
	}

	public void update() {
		index = 0;
		if(mouseHover)
			index = 1;
		if(mousePressed)
			index = 2;
		}
	
	public void draw(Graphics g) {
		
		g.drawImage(imgs[index], x, y, width, height,null);	
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
