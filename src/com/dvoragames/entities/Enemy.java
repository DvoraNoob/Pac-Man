package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.dvoragames.main.Game;
import com.dvoragames.world.AStar;
import com.dvoragames.world.Camera;
import com.dvoragames.world.Vector2i;
import com.dvoragames.world.World;


public class Enemy extends Entity{
	
	public static boolean ghost;
	public static boolean ghostFb;
	public boolean dead;
	public boolean deadFb;
	
	private int time = 0, maxTime = 50;
	public static int ghostTime = 0;
	public int deadTime = 0;


	private int ghostMTime = 1000;
	private int deadMTime = 10000;

		
	private int ghostFrames = 0, maxFrames = 20,index = 0, maxIndex = 1;
	private int deadFrames = 0;
	
	public int ix, iy;
	
	
	public Enemy(int x, int y, int width, int height,int speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		this.ix = x;
		this.iy = y;
	}
	
	public void tick(){
		depth = 1;
		if(dead == false) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
				path = AStar.findPath(Game.world, start, end);
			}
			
			if(new Random().nextInt(100) < 50)
				followPath(path);
				
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 100) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(Game.player.x/16)),((int)(Game.player.y/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
		}
		else if(dead == true) {
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
				Vector2i end = new Vector2i(((int)(ix/16)),((int)(iy/16)));
				path = AStar.findPath(Game.world, start, end);
			}
				
			if(new Random().nextInt(100) < 50)
				followPath(path);
					
			if(x % 16 == 0 && y % 16 == 0) {
				if(new Random().nextInt(100) < 100) {
					Vector2i start = new Vector2i(((int)(x/16)),((int)(y/16)));
					Vector2i end = new Vector2i(((int)(ix/16)),((int)(iy/16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
			
			if(x == ix && y == iy) {
				time++;
				if(time == maxTime) {
					dead = false;
					deadFb = false;
					time = 0;
				}
			}
		}
		
		if(deadFb) {
			deadFrames++;
			if(deadFrames == maxFrames) {
				deadFrames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		
		if(deadFb) {
			deadTime++;
			if(deadTime >= deadMTime) {
				deadTime = 0;
			}
		}
						
		if(ghostFb) {
			ghostFrames++;
			if(ghostFrames == maxFrames) {
				ghostFrames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		if(ghostFb) {
			ghostTime++;
			if(ghostTime >= ghostMTime) {
				ghostTime = 0;
				ghost = false;
				ghostFb = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(ghostFb) {
			g.drawImage(ENEMY_FB[index], this.getX(), this.getY(), null);
		}else if(deadFb) {
			g.drawImage(ENEMY_FB[index], this.getX(), this.getY(), null);
		}else {
			super.render(g);
		}
	}
}
