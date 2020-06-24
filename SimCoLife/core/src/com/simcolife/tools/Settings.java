package com.simcolife.tools;

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
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simcolife.game.Ending;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.game.event.Cards;
import com.simcolife.game.littlegame.FinalExamScreen;
import com.simcolife.game.littlegame.MidtermExamScreen;
import com.simcolife.game.littlegame.PetGameScreen;

public class Settings extends ScreenAdapter {
	SimCoGame game;
	private Stage stage;
	private static final int MVOLUME_Y = 535;
	private static final int SVOLUME_Y = 275;
	private Texture unchecked = new Texture(Gdx.files.internal("tools/SettingImg.png"));
	private Texture checked = new Texture(Gdx.files.internal("tools/SettingPressedImg.png"));
	private Texture bg = new Texture(Gdx.files.internal("tools/ToolsBGImg.png"));
	private Texture back = new Texture(Gdx.files.internal("tools/MenuButtonReturn.png"));
	private Texture backPress = new Texture(Gdx.files.internal("tools/MenuPressedReturn.png"));
	private Texture cross = new Texture(Gdx.files.internal("tools/SettingCrossImg.png"));
	
	private ImageButton musicHigh = new ImageButton(new TextureRegionDrawable(new TextureRegion(unchecked)),null,new TextureRegionDrawable(new TextureRegion(checked)));
	private ImageButton musicMedium = new ImageButton(new TextureRegionDrawable(new TextureRegion(unchecked)),null, new TextureRegionDrawable(new TextureRegion(checked)));
	private ImageButton musicLow = new ImageButton(new TextureRegionDrawable(new TextureRegion(unchecked)),null, new TextureRegionDrawable(new TextureRegion(checked)));
	private ImageButton soundHigh = new ImageButton(new TextureRegionDrawable(new TextureRegion(unchecked)),null, new TextureRegionDrawable(new TextureRegion(checked)));
	private ImageButton soundMedium = new ImageButton(new TextureRegionDrawable(new TextureRegion(unchecked)),null, new TextureRegionDrawable(new TextureRegion(checked)));
	private ImageButton soundLow = new ImageButton(new TextureRegionDrawable(new TextureRegion(unchecked)),null, new TextureRegionDrawable(new TextureRegion(checked)));
	private ImageButton backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(back)), new TextureRegionDrawable(new TextureRegion(backPress)));
	private ImageButton musicOnOff = new ImageButton(new TextureRegionDrawable(new TextureRegion(checked)),null, new TextureRegionDrawable(new TextureRegion(cross)));
	private ImageButton soundOnOff = new ImageButton(new TextureRegionDrawable(new TextureRegion(checked)),null, new TextureRegionDrawable(new TextureRegion(cross)));
	
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
	
    public Settings(SimCoGame game) {
		this.game = game;
		setFont();
		musicMedium.setChecked(true);
		soundMedium.setChecked(true);
		setStage();
	}
    public void setFont() {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 52;
        fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "音樂效量大中小";
        font = fontGenerator.generateFont(this.fontParameter);
	}
	
    private void setStage() {
		stage = new Stage();
		ButtonGroup<ImageButton> musicGroup = new ButtonGroup<ImageButton>();
		ButtonGroup<ImageButton> soundGroup = new ButtonGroup<ImageButton>();
//-----------------------------------音樂------------------------------------//		
		musicHigh.setPosition(300, MVOLUME_Y);
		musicHigh.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMusicVolume(1.5f);
			}
		});
		stage.addActor(musicHigh);
		musicGroup.add(musicHigh);
		
		musicMedium.setPosition(600,MVOLUME_Y);
		musicMedium.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMusicVolume(0.5f);
			}
		});
		stage.addActor(musicMedium);
		musicGroup.add(musicMedium);
		musicLow.setPosition(900,MVOLUME_Y);
		musicLow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setMusicVolume(0.2f);
			}
		});
		stage.addActor(musicLow);
		musicGroup.add(musicLow);
		//音樂開關
		musicOnOff.setPosition(140, 605);
		musicOnOff.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				stopAllMusic();
			}
		});
		stage.addActor(musicOnOff);
		musicGroup.add(musicOnOff);
//-----------------------------------音效------------------------------------//
		soundHigh.setPosition(300, SVOLUME_Y);
		soundHigh.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSoundVolume(1.5f);
			}
		});
		stage.addActor(soundHigh);
		soundGroup.add(soundHigh);
		soundMedium.setPosition(600,SVOLUME_Y);
		soundMedium.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSoundVolume(0.5f);
			}
		});
		stage.addActor(soundMedium);
		soundGroup.add(soundMedium);
		soundLow.setPosition(900, SVOLUME_Y);
		soundLow.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setSoundVolume(0.2f);
			}
		});
		stage.addActor(soundLow);
		soundGroup.add(soundLow);
		
		//音效開關
		soundOnOff.setPosition(140, 355);
		soundOnOff.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stopAllSound();
			}
		});
		stage.addActor(soundOnOff);
		soundGroup.add(soundOnOff);
		
		backButton.setPosition(Menu.BUTTON_X+75, 100);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.simcolife);
			}
		});
		stage.addActor(backButton);
	}
    
	public void setMusicVolume(float v) {
		FinalExamScreen.music.setVolume(v);
		MidtermExamScreen.music.setVolume(v);
		PetGameScreen.music.setVolume(v);
		SimCoGame.formalMusic.setVolume(v);
		SimCoGame.winterMusic.setVolume(v);
		SimCoGame.summerMusic.setVolume(v);
		Ending.endingMusic.setVolume(v);
	}
	
	public void stopAllMusic() {
		FinalExamScreen.music.setVolume(0);
		MidtermExamScreen.music.setVolume(0);
		PetGameScreen.music.setVolume(0);
		SimCoGame.formalMusic.setVolume(0);
		SimCoGame.winterMusic.setVolume(0);
		SimCoGame.summerMusic.setVolume(0);
		Ending.endingMusic.setVolume(0);
	}
	public void stopAllSound() {
		FinalExamScreen.SOUND_VOLUME = 0;
		SimCoLife.allowance.setVolume(0);
		SimCoLife.dice.setVolume(0);
		Cards.SOUND.setVolume(0);
	}
	public void setSoundVolume(float v) {
		FinalExamScreen.SOUND_VOLUME = v;
		SimCoLife.allowance.setVolume(v);
		SimCoLife.dice.setVolume(v);
		Cards.SOUND.setVolume(v);
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.draw(bg,0,0);
		font.getData().setScale(1f);
		font.draw(game.batch, "音樂音量",200,650);
		font.draw(game.batch, "音效音量",200,400);
		font.setColor(Color.WHITE);
		font.getData().setScale(0.8f);
		font.draw(game.batch, "大",360,MVOLUME_Y + 45);
		font.draw(game.batch, "中",660,MVOLUME_Y + 45);
		font.draw(game.batch, "小",960,MVOLUME_Y + 45);
		font.draw(game.batch, "大",360,SVOLUME_Y + 45);
		font.draw(game.batch, "中",660,SVOLUME_Y + 45);
		font.draw(game.batch, "小",960,SVOLUME_Y + 45);
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
	}
	
	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void dispose() {
		unchecked.dispose();
		checked.dispose();
		bg.dispose();
		back.dispose();
		backPress.dispose();
	}

}
