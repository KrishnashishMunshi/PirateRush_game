package entities;

import static utils.Constants.Directions.DOWN;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.Directions.RIGHT;
import static utils.Constants.Directions.UP;
import static utils.Constants.PlayerConstants.GetSpriteAmount;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import utils.LoadSave;

public class Player extends entity{

	private BufferedImage[][] animations; //2d array so that all the each animation slides in one dimension, and all the animation types in another dimension
	private int aniTick, aniIndex, aniSpeed =15;
	private int playerAction = IDLE;
	private boolean left, up,right, down, jump;
	private boolean moving = false, attacking = false;;
	private float playerSpeed=1.0F * Game.SCALE;
	private int[][] lvlData;
	private float XDrawOffset  = 21 * Game.SCALE;
	private float YDrawOffset  = 4 * Game.SCALE;
	
	//Jumping/Gravity
	private float airSpeed = 0F;
	private float gravity = 0.04F * Game.SCALE;
	private float jumpSpeed = -2.25F * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5F * Game.SCALE;
	private boolean inAir = false;
	
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, (int)20*Game.SCALE, (int)27* Game.SCALE);

	}
	
	
	public void update() {
		updatePos();
		
		updateAnimationTick();
		setAnimation();
		
		
	}
	
	public void render(Graphics g, int xLvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - XDrawOffset) - xLvlOffset, (int)(hitbox.y - YDrawOffset), width, height, null);
	drawHitbox(g);  //this is not working well after implementing sidescroll using xLvloffset

		
	}
	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
		
	}



	
	
	
	
	private void setAnimation() {
		
		int startAni = playerAction;
		
		if(moving) {
			playerAction = RUNNING;
		}else {
			playerAction = IDLE;
		}
		
		if (inAir) {
			if(airSpeed<0) {
				playerAction = JUMP;
			}else {
				playerAction = FALLING;
			}
		}
		if(attacking) {
			playerAction = ATTACK;
		}
		
		if(startAni != playerAction) {
			resetAnimation();
			
		}
	}
	
	private void resetAnimation() {
		aniTick = 0;
		aniIndex = 0;
		
	}


	private void updatePos() {
		
		moving = false;
		
		
		if(jump) {
			jump();
		}
//		if(!left && !right && !inAir) {
//			return;
//		}
		if(!inAir) {
			if((!left && !right) || (right && left))
				return;
				
		}
		float xSpeed = 0;
		if(left ) {
			xSpeed -= playerSpeed;
		}
		if(right) {
			xSpeed += playerSpeed;
		}
		if(!inAir) {
			if(!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		}
		if(inAir) {
			
			if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height,lvlData)) {//just checking for collision in Y-axis here
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXpos(xSpeed);
			}else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed>0) {
					resetInAir();
				}else {
					airSpeed = fallSpeedAfterCollision;
				}
				updateXpos(xSpeed);
			}
			
		}else {
			updateXpos(xSpeed);
		}
		moving = true;
		
//		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
//		
//			hitbox.x += xSpeed;
//			hitbox.y += ySpeed;
//			moving = true;
//			
//		}
		
	}

	private void jump() {
		if(inAir) {
			return;
		}
		inAir = true;
		airSpeed = jumpSpeed;
		
	}


	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}


	private void updateXpos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			
				hitbox.x += xSpeed;
				
			}	else {
				hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
			}
	}


	private void loadAnimations() {
			
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
			animations = new BufferedImage[9][6];  //2darray[y][x]

			for (int j=0; j<animations.length; j++) {
				for(int i=0;i<animations[j].length; i++) {
					animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
					}
			
			}
		
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}


	public boolean isLeft() {
		return left;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public boolean isUp() {
		return up;
	}


	public void setUp(boolean up) {
		this.up = up;
	}


	public boolean isRight() {
		return right;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public boolean isDown() {
		return down;
	}


	public void setDown(boolean down) {
		this.down = down;
	}


	public void resetDirBooleans() {
		left = false;
		right = false;
		down = false;
		up = false;
		
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking=attacking;
	}
	
	public void setJump(boolean jump) {
		this.jump=jump;
	}
}
