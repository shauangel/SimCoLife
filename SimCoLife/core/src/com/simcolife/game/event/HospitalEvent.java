package com.simcolife.game.event;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.tools.Calender;

public class HospitalEvent extends ScreenAdapter {

//---------------------------other variable----------------------------
	private SimCoGame game;
	private Stage stage;
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private Texture pic;

    
//---------------------------hospital Event----------------------------
    public static final String[] PIC_LIST = {"Phlebotomy", "Candy", "Caretaker"};
    public static final String[] WORD_LIST = {"捐個血，神清氣爽", "你有一顆棒棒肝，醫生給你吃糖糖", "陪陪老人家，十大好青年"};
    public static final Event[] EVENT_LIST = {
    		new Event("", "", "", -10, 0, 0, 0, 5, 1, EventType.HOSPITAL, 0), 
    		new Event("", "", "", 0, 15, 0, 0, 5, 1, EventType.HOSPITAL, 0), 
    		new Event("", "", "", 0, 0, 0, 0, 5, 1, EventType.HOSPITAL, 15)}; 
    public static final Event DEAD_LIVER = new Event("", "", "", 0, 0, 0, 0, -10, 3, EventType.HOSPITAL, -20);
    public static final Event DEPRESSED = new Event("", "", "", 20, 0, -2000, 0, 100, 3, EventType.HOSPITAL, 0);
    private String currentEvent;
    private int ind = 0;
    
	public HospitalEvent(SimCoGame game) {
		this.game = game;
	//------------------------set font--------------------------
		glyphLayout = new GlyphLayout();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		this.fontParameter = new FreeTypeFontParameter();
        this.fontParameter.size = 100;
        this.fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "你爆肝了捐個血，神清氣爽你有一顆棒肝醫生給你吃糖陪老人家十大好青年按回到遊戲下在醫院休養了三日憂鬱";
        font = fontGenerator.generateFont(this.fontParameter);
        
        setStage();
        setEvent();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		if(game.simcolife.getCurrPlayer().getHealth() <= 90 && game.simcolife.getCurrPlayer().getKimoji() > 20) {
			game.batch.draw(Cards.BOARD, 0, 0);   //background
			game.batch.draw(pic, 450, 300);
			font.getData().setScale(52/100f);
			font.setColor(Color.BLACK);
			glyphLayout.setText(font, currentEvent);
			font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 200);
		}
		else if(game.simcolife.getCurrPlayer().getHealth() > 90){
			font.getData().setScale(1f);
			font.setColor(Color.RED);
			glyphLayout.setText(font, "...在醫院休養了三日...");
			font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, (SimCoLife.WINDOW_HEIGHT-glyphLayout.height)/2+250);
			glyphLayout.reset();
			glyphLayout.setText(font, "你爆肝了");
			font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, (SimCoLife.WINDOW_HEIGHT-glyphLayout.height)/2);
		}
		else {
			font.getData().setScale(1f);
			font.setColor(Color.RED);
			glyphLayout.setText(font, "...在醫院休養了三日...");
			font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, (SimCoLife.WINDOW_HEIGHT-glyphLayout.height)/2+250);
			glyphLayout.reset();
			glyphLayout.setText(font, "你憂鬱了");
			font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, (SimCoLife.WINDOW_HEIGHT-glyphLayout.height)/2);
		}
		
		glyphLayout.reset();
		font.getData().setScale(26/100f);
		font.setColor(Color.BLACK);
		glyphLayout.setText(font, "按下SPACE回到遊戲");
		font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 50);
		game.batch.end();
	
		stage.act(Gdx.graphics.getDeltaTime());
	}
	
	public void setStage() {
		stage = new Stage();
		stage.addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.SPACE) {
					Cards.SOUND.play();
					game.simcolife.getCurrPlayer().setHealth(game.simcolife.getCurrPlayer().getHealth()-40);
					if(game.simcolife.getCurrPlayer().getHealth() > 90) {
						game.simcolife.getCurrPlayer().setAllStatics(DEAD_LIVER);
						game.setScreen(game.simcolife);
					}
					else if(game.simcolife.getCurrPlayer().getKimoji() < 20) {
						game.simcolife.getCurrPlayer().setAllStatics(DEPRESSED);
						game.setScreen(game.simcolife);
					}
					else {
						game.setScreen(game.simcolife);
						Timer.schedule(new Task() {
							@Override
							public void run() {
								game.simcolife.getCurrPlayer().setAllStatics(EVENT_LIST[ind]);
								Calender.timeConvert(game.simcolife.getCurrPlayer().getTime());
								Cards.SOUND.stop();
							}
						}, 0.5f);
					}
					
				}
				return super.keyDown(event, keycode);
			}
			
		});
	}
	
	//set event
	public void setEvent() {
		Random r = new Random();
		ind = r.nextInt(PIC_LIST.length);
		pic = new Texture(Gdx.files.internal("card/Hospital" + PIC_LIST[ind] + "Img.png"));
		currentEvent = WORD_LIST[ind];
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public String toString() {
		return "hospital";
	}
	
}
