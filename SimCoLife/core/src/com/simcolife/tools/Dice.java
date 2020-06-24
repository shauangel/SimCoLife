package com.simcolife.tools;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Dice {

	public static final int MAX_NUM = 12;
	public static final int MIN_NUM = 2;
	
	private final TextureRegionDrawable diceFormal = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DiceImg.png"))));
	private final TextureRegionDrawable dicePressed = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DicePressedImg.png"))));
	
	public static int rollDice() {
		Random r = new Random();
//		return 10;
		return MIN_NUM + r.nextInt(MAX_NUM-1);
	}
	
	public TextureRegionDrawable getDiceFormal() {
		return diceFormal;
	}
	
	public TextureRegionDrawable getDicePressed() {
		return dicePressed;
	}

}
