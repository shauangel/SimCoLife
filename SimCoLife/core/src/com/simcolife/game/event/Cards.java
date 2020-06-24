package com.simcolife.game.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.simcolife.tools.Calender.ImportantEvent;

public class Cards {
	
	public static final String INFO = "肝指數夯零用錢才藝　奇摩子耗費時間：天學分按下回到遊戲確定選擇並";
	public static final int CARD_Y = 50;
	public static final Texture CHOICE = new Texture(Gdx.files.internal("card/EventChoiceImg.png"));
	public static final Texture TWA = new Texture(Gdx.files.internal("card/EventTwaImg.png"));
	public static final Texture PET = new Texture(Gdx.files.internal("card/EventPetImg.png"));
	public static final Texture GOOD = new Texture(Gdx.files.internal("card/EventSmileImg.png"));
	public static final Texture SOSO = new Texture(Gdx.files.internal("card/EventSosoImg.png"));
	public static final Texture BAD = new Texture(Gdx.files.internal("card/EventCryImg.png"));
	public static final Texture CARD_BG = new Texture(Gdx.files.internal("card/EventCardImg.png"));
	public static final Texture CARD_BACK_BG = new Texture(Gdx.files.internal("card/EventCardBackImg.png"));
	public static Texture BOARD;
	public static final Music SOUND = Gdx.audio.newMusic(Gdx.files.internal("music/button.mp3"));
	
	
	public static void setBoard(ImportantEvent season) {
		switch(season) {
			case NONE:
				BOARD = new Texture(Gdx.files.internal("card/CardFormalImg.png"));
				break;
			case WINTER:
				BOARD = new Texture(Gdx.files.internal("card/CardWinterImg.png"));
				break;
			case SUMMER:
				BOARD = new Texture(Gdx.files.internal("card/CardSummerImg.png"));
				break;
			default:
				BOARD = new Texture(Gdx.files.internal("card/CardFormalImg.png"));
				break;
		}
	}

}
