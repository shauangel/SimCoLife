package com.simcolife.game.event;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.tools.Calender;

public class StartEvent extends ScreenAdapter {

	private SimCoGame game;
	private Stage stage;
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private Texture pic = new Texture(Gdx.files.internal("card/StartHomeImg.png"));
	
	public StartEvent(SimCoGame game) {
		this.game = game;
		setStage();
	//------------------font-----------------------
		glyphLayout = new GlyphLayout();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		this.fontParameter = new FreeTypeFontParameter();
        this.fontParameter.size = 52;
        this.fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "溫暖家，馬麻的愛按下回到遊戲";
        font = fontGenerator.generateFont(this.fontParameter);
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(Cards.BOARD, 0, 0);
		game.batch.draw(pic, 450, 300);
		font.getData().setScale(1f);
		font.setColor(Color.BLACK);
		glyphLayout.setText(font, "溫暖的家，馬麻的愛");
		font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 200);
		glyphLayout.reset();
		font.getData().setScale(0.5f);
		glyphLayout.setText(font, "按下SPACE回到遊戲");
		font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 50);
		game.batch.end();
	}
	
	public void setStage() {
		stage = new Stage();
		stage.addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.SPACE) {
					Cards.SOUND.play();
					game.setScreen(game.simcolife);
					Timer.schedule(new Task() {
						
						@Override
						public void run() {
							game.simcolife.getCurrPlayer().setTime(game.simcolife.getCurrPlayer().getTime()-1);
							Calender.timeConvert(game.simcolife.getCurrPlayer().getTime());
							Cards.SOUND.stop();
						}
					}, 0.5f);
					StartEvent.this.dispose();
				}
				return super.keyDown(event, keycode);
			}
			
		});
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void hide() {
		super.hide();
		this.dispose();
		Gdx.input.setInputProcessor(null);
	}
	@Override
	public void dispose() {
		super.dispose();
		
	}
	
	@Override
	public String toString() {
		return "start";
	}
	
}
