package com.simcolife.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.simcolife.game.Preparation;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.tools.Warning;

public class RecordReader extends ScreenAdapter {
	
	private SimCoGame game;
	private Stage stage;
	private int choose;
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
  //-----------------Texture-------------------
  	private Texture bg = new Texture(Gdx.files.internal("save/SaveBgImg.png"));
  	private Texture block = new Texture(Gdx.files.internal("save/SaveBlockImg.png"));
  	private Texture blockSelected = new Texture(Gdx.files.internal("save/SaveBlockPressedImg.png"));
  	private Texture female = new Texture(Gdx.files.internal("save/SaveFemaleAvatar.png"));
	private Texture male = new Texture(Gdx.files.internal("save/SaveMaleAvatar.png"));
  	
  //-------------------Buttons-----------------
  	private ImageButton confirmButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("save/SaveButtonImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("save/SaveButtonPressedImg.png")))));
  	private ImageButton returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("save/SaveButtonReturnImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("save/SavePressedReturnImg.png")))));
  	private ImageButton[] blockList = new ImageButton[5];
  	
	public RecordReader(SimCoGame game) {
		this.game = game;
		setStage();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		this.fontParameter = new FreeTypeFontParameter();
        this.fontParameter.size = 52;
        this.fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "日";
        font = fontGenerator.generateFont(this.fontParameter);
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(bg, 0, 0);
		showSavedInfo();
		
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	public void setStage() {
		stage = new Stage();
		confirmButton.setPosition(SimCoLife.WINDOW_WIDTH-100, SimCoLife.WINDOW_HEIGHT-180);
		confirmButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(RecordImporter.archiveList[choose] != null) {
					ArchiveFormat choice = RecordImporter.loadRecord(choose);
					game.simcolife.setCurrPlayer(choice.createPlayer());
					SimCoLife.route = choice.createRoute();
					game.setScreen(game.simcolife);
					SimCoLife.playerNow = choice.getPlayerNow();
					game.simcolife.nextBlock = SimCoLife.playerNow;
				}
				else {
					final Warning warn = new Warning("", game.skin, "此處空空如也");
					LabelStyle style1 = new LabelStyle(warn.getFont(), Color.RED);
					Label label1 = new Label("此處空空如也", style1);
					label1.setPosition((500-label1.getWidth())/2, 160);
					ImageButton yes = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogButtonYesImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogPressedYesImg.png")))));
					yes.setPosition((500-yes.getWidth())/2, 55);
					yes.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							warn.remove();
						};
					});
					warn.addActor(label1);
					warn.addActor(yes);
					warn.show(stage);
				}
			}
			
		});
		stage.addActor(confirmButton);
		
		returnButton.setPosition(0, SimCoLife.WINDOW_HEIGHT-180);
		returnButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Preparation(game));
			}
			
		});
		stage.addActor(returnButton);
		setBlockButtons();
	}
	
	public void setBlockButtons() {
		for(int i=0; i<5; i++) {
			blockList[i] = new ImageButton(new TextureRegionDrawable(new TextureRegion(block)), null, new TextureRegionDrawable(new TextureRegion(blockSelected)));
			blockList[i].setPosition(100, SimCoLife.WINDOW_HEIGHT-(180+i*140));
			stage.addActor(blockList[i]);
		}
		blockList[0].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setUnchecked(0);
				choose = 0;
			}
		});
		
		
		blockList[1].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setUnchecked(1);
				choose = 1;
			}
		});
		
		blockList[2].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setUnchecked(2);
				choose = 2;
			}
		});
		
		blockList[3].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setUnchecked(3);
				choose = 3;
			}
		});
		
		blockList[4].addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setUnchecked(4);
				choose = 4;
			}
		});
		
	}
	
	private void setUnchecked(int ind) {
		for(int i=0; i<5; i++) {
			blockList[i].setChecked(false);
		}
		blockList[ind].setChecked(true);
	}

	private void showSavedInfo() {
		for(int i=0; i<5; i++) {
			if(RecordImporter.archiveList[i] != null) {
				font.getData().setScale(1f);
				font.draw(game.batch, RecordImporter.archiveList[i].getSaveTime(), 100, 680-i*140, 1100, Align.center, false);
				font.draw(game.batch, String.format("%d", RecordImporter.archiveList[i].getTime()) + "日", 100, 680-i*140, 1000, Align.right, false);
				if(RecordImporter.archiveList[i].getGender() == 'F') {
					game.batch.draw(female, 120, 600-i*140);
				}
				else {
					game.batch.draw(male, 120, 600-i*140);
				}
			}
		}
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
		super.dispose();
	}
	
	

}
