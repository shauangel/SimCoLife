package com.simcolife.tools;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Dice {

	public static final int MAX_NUM = 12;
	public static final int MIN_NUM = 2;
	public static final Texture NUM02 = new Texture(Gdx.files.internal("dice/DiceNumber02Img.png"));
	public static final Texture NUM03 = new Texture(Gdx.files.internal("dice/DiceNumber03Img.png"));
	public static final Texture NUM04 = new Texture(Gdx.files.internal("dice/DiceNumber04Img.png"));
	public static final Texture NUM05 = new Texture(Gdx.files.internal("dice/DiceNumber05Img.png"));
	public static final Texture NUM06 = new Texture(Gdx.files.internal("dice/DiceNumber06Img.png"));
	public static final Texture NUM07 = new Texture(Gdx.files.internal("dice/DiceNumber07Img.png"));
	public static final Texture NUM08 = new Texture(Gdx.files.internal("dice/DiceNumber08Img.png"));
	public static final Texture NUM09 = new Texture(Gdx.files.internal("dice/DiceNumber09Img.png"));
	public static final Texture NUM10 = new Texture(Gdx.files.internal("dice/DiceNumber10Img.png"));
	public static final Texture NUM11 = new Texture(Gdx.files.internal("dice/DiceNumber11Img.png"));
	public static final Texture NUM12 = new Texture(Gdx.files.internal("dice/DiceNumber12Img.png"));
	public static final Texture NUM_NONE = new Texture(Gdx.files.internal("dice/DiceNumberNoneImg.png"));
	public static final Texture[] NUMBER_LIST = {NUM02, NUM03, NUM04, NUM05, NUM06, NUM07, NUM08, NUM09, NUM10, NUM11, NUM12};
	public static Texture DICE_NUM = NUM_NONE;
	
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

	public static void setDiceNumber(int number) {
		if(number >= 2 && number <= 12) {
			Dice.DICE_NUM = NUMBER_LIST[number-2];
		}
		else {
			Dice.DICE_NUM = NUM_NONE;
		}
	}
}
