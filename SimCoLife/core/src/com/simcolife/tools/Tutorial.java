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
import com.simcolife.tools.Calender.ImportantEvent;

public class Tutorial extends ScreenAdapter {
	
	private SimCoGame game;
	private Stage stage;

//------------------------------Button Texture---------------------------------
	private Texture left = new Texture(Gdx.files.internal("tools/TutorLeftButton.png"));
	private Texture leftPressed = new Texture(Gdx.files.internal("tools/TutorLeftPressed.png"));
	private Texture right = new Texture(Gdx.files.internal("tools/TutorRightButton.png"));
	private Texture rightPressed = new Texture(Gdx.files.internal("tools/TutorRightPressed.png"));
	
//-------------------------------Image Button--------------------------------
	private ImageButton leftButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(left)), null, new TextureRegionDrawable(new TextureRegion(leftPressed)));
	private ImageButton rightButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(right)), null, new TextureRegionDrawable(new TextureRegion(rightPressed)));
	private ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/MenuButtonReturn.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/MenuPressedReturn.png")))));

//-------------------------------tutorial Img------------------------------------
	private Texture[] tutorList = {new Texture(Gdx.files.internal("tools/Tutor00Img.png")), new Texture(Gdx.files.internal("tools/Tutor01Img.png")), new Texture(Gdx.files.internal("tools/Tutor02Img.png")), new Texture(Gdx.files.internal("tools/Tutor03Img.png"))};
	private int currentPage = 0;
	private boolean flag ;
	
	public Tutorial(SimCoGame game, boolean flag) {
		this.game = game;
		this.flag = flag;
		setStage();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		try {
			ToolsBackgroundSetter.setBG(game.simcolife.getCurrPlayer().getCurrentSeason());
		}
		catch (Exception e) {
			ToolsBackgroundSetter.setBG(ImportantEvent.NONE);
		}
		
		
		game.batch.begin();
		
		game.batch.draw(ToolsBackgroundSetter.toolsBG, 0, 0);
		game.batch.draw(tutorList[currentPage], 200, 200);
		
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	public void buttonStage() {
		if(getCurrentPage() == 0) {
			leftButton.setChecked(true);
			rightButton.setChecked(false);
		}
		else if(getCurrentPage() == tutorList.length-1) {
			leftButton.setChecked(false);
			rightButton.setChecked(true);
		}
		else {
			leftButton.setChecked(false);
			rightButton.setChecked(false);
		}
	}
	
	private void setStage() {
		stage = new Stage();

	//-----------------left Button-----------------
		leftButton.setPosition(100, 380);
		leftButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(getCurrentPage() > 0) {
					Tutorial.this.setCurrentPage(getCurrentPage() - 1);
				}
				else {
					Tutorial.this.setCurrentPage(0);
				}
				buttonStage();
			}
		});
		leftButton.setChecked(true);
		
		stage.addActor(leftButton);
		
	//-----------------right Button-----------------
		rightButton.setPosition(1100, 380);
		rightButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(getCurrentPage() < 3) {
					Tutorial.this.setCurrentPage(getCurrentPage() + 1);
				}
				else {
					Tutorial.this.setCurrentPage(tutorList.length-1);
				}
				buttonStage();
			}
		});
		stage.addActor(rightButton);
		
	//------------------return Button--------------------
		backButton.setPosition((SimCoLife.WINDOW_WIDTH-backButton.getWidth())/2, 60);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(flag) {
					game.setScreen(new Start(game));
				}
				else {
					game.setScreen(game.simcolife);
				}
			}
		});
		stage.addActor(backButton);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
	}
	
	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}

	
	public int getCurrentPage() {
		return currentPage;
	}

	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
}
