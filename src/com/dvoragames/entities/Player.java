package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.dvoragames.main.Game;
import com.dvoragames.world.Camera;
import com.dvoragames.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	
	public int lastDir = 1;
	
	private int frames = 0, maxFrames = 5,index = 0, maxIndex = 3;
	public boolean moved = false;
	public BufferedImage[] playerR;
	public BufferedImage[] playerL;
	public BufferedImage[] playerU;
	public BufferedImage[] playerD;
		
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		playerR = new BufferedImage[4];
		playerL = new BufferedImage[4];	
		playerU = new BufferedImage[4];
		playerD = new BufferedImage[4];	
		
		for(int i = 0; i<4; i++) {
			playerR[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		
		for(int i = 0; i<4 ; i++) {
			playerL[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
		}
		for(int i = 0; i<4; i++) {
			playerU[i] = Game.spritesheet.getSprite(32 + (i*16), 32, 16, 16);
		}
		
		for(int i = 0; i<4 ; i++) {
			playerD[i] = Game.spritesheet.getSprite(32 + (i*16), 48, 16, 16);
		}
	}
	
	public void tick(){
		depth = 1;
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=speed;
			lastDir = 1;
			moved = true;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=speed;
			lastDir = 2;
			moved = true;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
			lastDir = 3;
			moved = true;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
			lastDir = 4;
			moved = true;
		}
		
		if(moved) {
			moved = false;
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		
		collectFruit();
		
		if(Game.countFruit == Game.totalFruit) {
			World.restartGame();
		}
	}
	
	@SuppressWarnings("static-access")
	public void collectFruit() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Uva) {
				if(Entity.isColidding(this, current)) {
					Game.entities.remove(i);
					Game.points += 10;
					Game.countFruit++;
					return;
				}
			}else if(current instanceof Cacho) {
				if(Entity.isColidding(this, current)) {
					Enemy.ghost = true;
					Enemy.ghostTime = 0;
					Game.entities.remove(i);
					Game.points += 100;
					Game.countFruit++;
					return;
				}
			}else if(current instanceof Enemy) {
				if(Enemy.ghost == true) {
					Enemy.ghostFb = true;
					if(Entity.isColidding(this, current)) {
						((Enemy) current).dead = true;
						((Enemy) current).deadFb = true;
						((Enemy) current).ghostFb = false;
					}
				}
				else if(Enemy.ghost == false){
					if(Entity.isColidding(this, current)) {
						World.restartGame();
						return;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(lastDir == 1) {
			g.drawImage(playerR[index], this.getX() - Camera.x ,this.getY() - Camera.y,null);
		}else if (lastDir == 2) {
			g.drawImage(playerL[index], this.getX() - Camera.x ,this.getY() - Camera.y,null);
		}
		else if (lastDir == 3) {
			g.drawImage(playerU[index], this.getX() - Camera.x ,this.getY() - Camera.y,null);
		}else if (lastDir == 4) {
			g.drawImage(playerD[index], this.getX() - Camera.x ,this.getY() - Camera.y,null);
		}
	}

	


}
