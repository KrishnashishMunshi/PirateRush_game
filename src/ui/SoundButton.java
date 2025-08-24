package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;
import static utils.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton{

	private BufferedImage[][] soundImages;
	private boolean mouseHover, mousePressed;
	private boolean muted;
	private int rowIndex, colIndex;
	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		loadSoundImages();
	}
	private void loadSoundImages() {
 
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
		soundImages = new BufferedImage[2][3];
		for(int i = 0; i<soundImages.length; i++) {
			for(int j =0; j<soundImages[i].length; j++) {
				soundImages[i][j] = temp.getSubimage(j*SOUND_SIZE_DEFAULT, i*SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
			}
		}
	}
	public void update() {
		if(muted) {
			rowIndex = 1;
		}else {
			rowIndex = 0;
		}
		
		colIndex = 0;
		
		if(mouseHover) {
			colIndex = 1;
		}
		if(mousePressed) {
			colIndex = 2;
		}
	}
	
	public void resetBools() {
		mouseHover = false;
		mousePressed = false;
	}
	public void draw(Graphics g) {
		g.drawImage(soundImages[rowIndex][colIndex], x, y, (int)(width- 5*Game.SCALE),(int) (height- 5*Game.SCALE), null);
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
	public boolean isMuted() {
		return muted;
	}
	public void setMuted(boolean muted) {
		this.muted = muted;
	}
	
}
