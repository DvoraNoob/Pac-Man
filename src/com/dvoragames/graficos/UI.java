package com.dvoragames.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.dvoragames.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("ARIAL", Font.CENTER_BASELINE,10));
		g.drawString("Pontos: "+Game.points, 10, 10);
		g.drawString("Frutas: "+Game.countFruit+"/"+Game.totalFruit, 200, 10);
	}
	
}
