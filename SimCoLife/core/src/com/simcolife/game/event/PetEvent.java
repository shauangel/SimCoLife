package com.simcolife.game.event;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.tools.Calender;

public class PetEvent extends ScreenAdapter {
	
	public final int NAME_X = 530;
	public final int NAME_Y = 606;
	public final int CARD_X = 500;
	//-----------------------------other variable--------------------------------
	private SimCoGame game;
	private Stage stage;
	private Event randomE;
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private Texture pic;
	private ImageButton card = new ImageButton(new TextureRegionDrawable(new TextureRegion(Cards.CARD_BG)), null, new TextureRegionDrawable(new TextureRegion(Cards.CARD_BACK_BG)));
		
		
		
		
	public PetEvent(SimCoGame game) {
		this.game = game;
		setStage();
	//-------get random event--------
		Random r = new Random();
		this.randomE = EventImporter.petList.get(r.nextInt(EventImporter.petList.size()));
		
	//-------create font---------
		glyphLayout = new GlyphLayout();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		this.fontParameter = new FreeTypeFontParameter();
        this.fontParameter.size = 52;
        this.fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + Cards.INFO + randomE.getName() + randomE.getDescribe();
        font = fontGenerator.generateFont(this.fontParameter);
    //-------create category pic--------
        pic = Cards.PET;

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(Cards.BOARD, 0, 0);
		font.getData().setScale(0.5f);
		glyphLayout.setText(font, "按下SPACE回到遊戲");
		font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 30);
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		game.batch.begin();
		if(!card.isChecked()) {
			font.setColor(Color.BLACK);
			font.getData().setScale(0.5f);
			font.draw(game.batch, randomE.getName(), NAME_X, NAME_Y);
			font.draw(game.batch, randomE.getDescribe(), NAME_X, Cards.CARD_Y+200, 250, Align.topLeft, true);
			game.batch.draw(pic, 520, Cards.CARD_Y+260);
		}
		else {
			font.setColor(Color.BLACK);
			font.getData().setScale(0.7f);
			font.draw(game.batch, "肝指數：" + String.format("%,d", randomE.getHealth()), NAME_X, Cards.CARD_Y+500);
			font.draw(game.batch, "夯指數：" + String.format("%,d", randomE.getRelationship()), NAME_X, Cards.CARD_Y+450);
			font.draw(game.batch, "才　藝：" + String.format("%,d", randomE.getTalent()), NAME_X, Cards.CARD_Y+400);
			font.draw(game.batch, "奇摩子：" + String.format("%,d", randomE.getKimoji()), NAME_X, Cards.CARD_Y+350);
			font.draw(game.batch, "零用錢：" + String.format("%,d", randomE.getMoney()), NAME_X, Cards.CARD_Y+300);
			font.draw(game.batch, "耗費時間：" + String.format("%,d", randomE.getTime()) + "　天", NAME_X, Cards.CARD_Y+100);
		}
		game.batch.end();
		
	}
	
	public void setStage() {
		stage = new Stage();
		card.setPosition(CARD_X, Cards.CARD_Y);
		card.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
			}
		});
		stage.addActor(card);
		stage.addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				
				if(keycode == Keys.SPACE) {
					Cards.SOUND.play();
					game.setScreen(game.simcolife);
					Timer.schedule(new Task() {
						
						@Override
						public void run() {
							game.simcolife.getCurrPlayer().setAllStatics(randomE);
							Calender.timeConvert(game.simcolife.getCurrPlayer().getTime());
							Cards.SOUND.stop();
						}
					}, 0.5f);
					PetEvent.this.dispose();
				}
				
				return false;
				
			};
			
		});
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void dispose() {
		super.dispose();
	}
	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}
	
	@Override
	public String toString() {
		return "pet";
	}
	

}
