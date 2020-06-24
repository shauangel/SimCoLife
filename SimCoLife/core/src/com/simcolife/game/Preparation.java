package com.simcolife.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simcolife.save.RecordReader;

public class Preparation extends ScreenAdapter {

	private SimCoGame game;
	private Stage stage;
	private Texture bg = new Texture(Gdx.files.internal("FormalBgImg.png"));
	
//---------------------button-----------------------
	private Texture newGame = new Texture(Gdx.files.internal("PrepareNewButtonImg.png"));
	private Texture readGame = new Texture(Gdx.files.internal("PrepareReadButtonImg.png"));
	private Texture newGamePress = new Texture(Gdx.files.internal("PrepareNewPressedImg.png"));
	private Texture readGamePress = new Texture(Gdx.files.internal("PrepareReadPressedImg.png"));
	
//---------------------------image button-------------------------------
	private ImageButton newGameButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(newGame)), new TextureRegionDrawable(new TextureRegion(newGamePress)));
	private ImageButton readGameButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(readGame)), new TextureRegionDrawable(new TextureRegion(readGamePress)));
	private ImageButton returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/MenuButtonReturn.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/MenuPressedReturn.png")))));
	
	public Preparation(SimCoGame game) {
		this.game = game;
		setStage();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(bg, 0, 0);
		
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		bg.dispose();
		newGame.dispose();
		readGame.dispose();
		newGamePress.dispose();
		readGamePress.dispose();
	}
	
	private void setStage() {
		stage = new Stage();
		newGameButton.setPosition(500, 550);
		newGameButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Characters(game));
			}
			
		});
		stage.addActor(newGameButton);
		
		
		readGameButton.setPosition(500, 400);
		readGameButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new RecordReader(game));
			}
		});
		stage.addActor(readGameButton);
		
		returnButton.setPosition((SimCoLife.WINDOW_WIDTH-150)/2, 250);
		returnButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Start(game));
			}
		});
		stage.addActor(returnButton);
	}
	
}
