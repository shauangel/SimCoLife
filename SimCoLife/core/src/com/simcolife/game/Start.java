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
import com.simcolife.tools.TimeMachine;
import com.simcolife.tools.Tutorial;

public class Start extends ScreenAdapter {
	
	public static final int TITLE_X = 150;
	public static final int TITLE_Y = SimCoLife.WINDOW_HEIGHT-350;
	public static final int BUTTON_X = 500;
	
	SimCoGame game;
	private Stage stage;
	private Texture bg = new Texture(Gdx.files.internal("FormalBgImg.png"));
	private Texture title = new Texture(Gdx.files.internal("start/StartTitleImg.png"));
	
//------------------------------------button texture-------------------------------------
	private Texture start = new Texture(Gdx.files.internal("start/StartButtonStartImg.png"));
	private Texture tutorial = new Texture(Gdx.files.internal("start/StartButtonTutorialImg.png"));
	private Texture quit = new Texture(Gdx.files.internal("start/StartButtonQuitImg.png"));
	private Texture startPress = new Texture(Gdx.files.internal("start/StartPressedStartImg.png"));
	private Texture tutorialPress = new Texture(Gdx.files.internal("start/StartPressedTutorialImg.png"));
	private Texture quitPress = new Texture(Gdx.files.internal("start/StartPressedQuitImg.png"));
	
//----------------------------------image button------------------------------------
	private ImageButton startButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(start)), new TextureRegionDrawable(new TextureRegion(startPress)));
	private ImageButton tutorialButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(tutorial)), new TextureRegionDrawable(new TextureRegion(tutorialPress)));
	private ImageButton quitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(quit)), new TextureRegionDrawable(new TextureRegion(quitPress)));
	private ImageButton timeMachineButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("TimeMachineButton.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("TimeMachineButton.png")))));
	
	
	public Start(SimCoGame game) {
		this.game = game;
		setStage();
		SimCoGame.formalMusic.play();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(bg, 0, 0);
		game.batch.draw(title, TITLE_X, TITLE_Y);
		
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
//---------------------start button---------------------
		startButton.setPosition(BUTTON_X, 300);
		startButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Preparation(game));
			}
		});
		
		stage.addActor(startButton);
		
//---------------------tutorial button---------------------
		tutorialButton.setPosition(BUTTON_X, 185);
		tutorialButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Tutorial(game, true));
			}
		});
		stage.addActor(tutorialButton);

//---------------------quit button---------------------
		quitButton.setPosition(BUTTON_X, 70);
		quitButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
			
		});
		stage.addActor(quitButton);
		
//--------------------time machine------------------------
		timeMachineButton.setPosition(750+TITLE_X, SimCoLife.WINDOW_HEIGHT-350);
		timeMachineButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new TimeMachine(game));
			}
		});
		stage.addActor(timeMachineButton);
	}
	
	@Override
	public void dispose() {
		bg.dispose();
		title.dispose();
		start.dispose();
		tutorial.dispose();
		quit.dispose();
		startPress.dispose();
		tutorialPress.dispose();
		quitPress.dispose();
	}
	
	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}

}
