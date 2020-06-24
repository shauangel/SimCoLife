package com.simcolife.tools;

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
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.game.Start;
import com.simcolife.save.Save;

public class Menu extends ScreenAdapter {

	public static final int BUTTON_X = 500;
	
	SimCoGame game;
	private Stage stage;
	
//--------------------------------button Texture------------------------------------
	private Texture save = new Texture(Gdx.files.internal("tools/MenuButtonSave.png"));
	private Texture quit = new Texture(Gdx.files.internal("tools/MenuButtonQuit.png"));
	private Texture back = new Texture(Gdx.files.internal("tools/MenuButtonReturn.png"));
	private Texture savePress = new Texture(Gdx.files.internal("tools/MenuPressedSave.png"));
	private Texture quitPress = new Texture(Gdx.files.internal("tools/MenuPressedQuit.png"));
	private Texture backPress = new Texture(Gdx.files.internal("tools/MenuPressedReturn.png"));

//----------------------------------ImageButtons--------------------------------------
	private ImageButton saveButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(save)), new TextureRegionDrawable(new TextureRegion(savePress)));
	private ImageButton quitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(quit)), new TextureRegionDrawable(new TextureRegion(quitPress)));
	private ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)), new TextureRegionDrawable(new TextureRegion(backPress)));
	
	public Menu(SimCoGame game) {
		this.game = game;
		setStage();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		ToolsBackgroundSetter.setBG(game.simcolife.getCurrPlayer().getCurrentSeason());
		
		game.batch.begin();
		game.batch.draw(ToolsBackgroundSetter.toolsBG, 0, 0);
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	private void setStage() {
		stage = new Stage();
	
	//----------------------save button------------------------
		saveButton.setPosition(BUTTON_X, 550);
		saveButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Save(game));
			}
			
		});
		stage.addActor(saveButton);
		
		
	//----------------------quit button------------------------
		quitButton.setPosition(BUTTON_X, 400);
		quitButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SimCoLife.triggerMusic(SimCoGame.formalMusic, false);
				SimCoLife.triggerMusic(SimCoGame.summerMusic, false);
				SimCoLife.triggerMusic(SimCoGame.winterMusic, false);
				game.setScreen(new Start(game));
			}
			
		});
		stage.addActor(quitButton);
		
	//----------------------back button------------------------
		backButton.setPosition(BUTTON_X+75, 100);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.simcolife);
			}
			
		});
		stage.addActor(backButton);
		
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
}
