package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constants.UI.PauseButtons.*;
import static utils.Constants.UI.URMbuttons.*;
import static utils.Constants.UI.VolButtons.*;

public class PauseOverlay {
	
	private Playing playing;
	
	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;
	
	private SoundButton musicBtn, sfxBtn;
	
	private UrmButton menuB, replayB, unpauseB;
	
	private VolumeButton VolumeButton;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundBttns();
		createUrmButtons();
		createVolButton();
	}
	
	private void createVolButton() {
		int vX = (int)(324*Game.SCALE);
		int vY = (int)(265*Game.SCALE);
		VolumeButton  = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}

	private void createUrmButtons() {
		int menuX = (int)(335* Game.SCALE);
		int replayX = (int)(392* Game.SCALE);
		int unpauseX = (int)(449* Game.SCALE);
		int bY = (int)(310*Game.SCALE);
		menuB = new UrmButton(menuX, bY, URM_SIZE - (int)(10*Game.SCALE), URM_SIZE - (int)(10*Game.SCALE), 2);
		replayB = new UrmButton(replayX, bY, URM_SIZE - (int)(10*Game.SCALE), URM_SIZE - (int)(10*Game.SCALE), 1);
		unpauseB = new UrmButton(unpauseX, bY, URM_SIZE - (int)(10*Game.SCALE), URM_SIZE - (int)(10*Game.SCALE), 0);

	
		
	}

	private void createSoundBttns() {
		int soundX = (int)(440* Game.SCALE);
		int musicY = (int)(145* Game.SCALE);
		int sfxY = (int)(190* Game.SCALE);
		musicBtn = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxBtn = new SoundButton (soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BAKCGROUND);
		bgW = (int) (backgroundImg.getWidth()*Game.SCALE - 40*Game.SCALE);
		bgH = (int) (backgroundImg.getHeight()*Game.SCALE - 40*Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW/2;
		bgY = (int)(40 * Game.SCALE);
	}

	public void update() {
		musicBtn.update();
		sfxBtn.update();
		menuB.update();
		replayB.update();
		unpauseB.update();
		VolumeButton.update();
	}
	
	public void draw(Graphics g) {
		//Pause background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		//Sound buttons
		musicBtn.draw(g);
		sfxBtn.draw(g);
		
		//URM buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		//Volume slider
		VolumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(VolumeButton.isMousePressed()) {
			VolumeButton.changeX(e.getX());
		}
	}
	
	
	public void mousePressed(MouseEvent e) {

		if(isIn(e, musicBtn)) {
			musicBtn.setMousePressed(true);
		}else if(isIn(e, sfxBtn)) {
			sfxBtn.setMousePressed(true);
		}else if(isIn(e, menuB)) {
			menuB.setMousePressed(true);
		}else if(isIn(e, replayB)) {
			replayB.setMousePressed(true);
		}else if(isIn(e, unpauseB)) {
			unpauseB.setMousePressed(true);
		}else if(isIn(e, VolumeButton)) {
			VolumeButton.setMousePressed(true);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(e, musicBtn)) {
			if(musicBtn.isMousePressed()) {
				musicBtn.setMuted(!musicBtn.isMuted());
			}}
		else if(isIn(e, sfxBtn)) {
			if(sfxBtn.isMousePressed()) {
				sfxBtn.setMuted(!sfxBtn.isMuted());
			}}
		else if(isIn(e, menuB)) {
			if(menuB.isMousePressed()) {
				
				Gamestate.state =Gamestate.MENU;
				playing.unpauseGame();
			}}
		else if(isIn(e, replayB)) {
			if(replayB.isMousePressed()) {
				System.out.print("Reset");
			}}
		else if(isIn(e, unpauseB)) {
			if(unpauseB.isMousePressed()) {
				playing.unpauseGame();
			}}
		musicBtn.resetBools();
		sfxBtn.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		VolumeButton.resetBools();
		
	}

	
	public void mouseMoved(MouseEvent e) {
		musicBtn.setMouseHover(false);
		sfxBtn.setMouseHover(false);
		menuB.setMouseHover(false);
		replayB.setMouseHover(false);
		unpauseB.setMouseHover(false);
		VolumeButton.setMouseHover(false);
		
		if(isIn(e, musicBtn)) {
			musicBtn.setMouseHover(true);
		}else if(isIn(e, sfxBtn)) {
			sfxBtn.setMouseHover(true);
		}else if(isIn(e, menuB)) {
			menuB.setMouseHover(true);
		}else if(isIn(e, replayB)) {
			replayB.setMouseHover(true);
		}else if(isIn(e, unpauseB)) {
			unpauseB.setMouseHover(true);
		}else if(isIn(e, VolumeButton)) {
			VolumeButton.setMouseHover(true);
		}
	}
	
	
	
	
	private boolean isIn(MouseEvent e,PauseButton b) {
		
		return b.getBounds().contains(e.getX(),e.getY());
			
		}

}
