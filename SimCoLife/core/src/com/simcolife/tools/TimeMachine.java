package com.simcolife.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simcolife.game.Characters;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.game.Start;
import com.simcolife.tools.Calender.ImportantEvent;

public class TimeMachine extends ScreenAdapter {
	
	private SimCoGame game;
	private Stage stage;
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
	
//---------------------Button-------------------------
	private ButtonGroup<ImageButton> group;
	private ImageButton oneYear = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingImg.png")))), null, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingPressedImg.png")))));
	private ImageButton twoYears = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingImg.png")))), null, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingPressedImg.png")))));
	private ImageButton threeYears = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingImg.png")))), null, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingPressedImg.png")))));
	private ImageButton fourYears = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingImg.png")))), null, new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/SettingPressedImg.png")))));
	private ImageButton selectedButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/TimeMachineSelectImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/TimeMachineSelectPressedImg.png")))));
	private ImageButton returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/TimeMachineReturnImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/TimeMachineReturnPressedImg.png")))));
	
	
	public TimeMachine(SimCoGame game) {
		this.game = game;
		setStage();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
        this.fontParameter = new FreeTypeFontParameter();
        this.fontParameter.size = 72;
        this.fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "帶哎呀被你發現勒霹靂卡霹靂拉拉小貓喵喵小狗汪汪神奇魔力小空間穿越時光　第一年二三四";
        font = fontGenerator.generateFont(this.fontParameter);
        glyphLayout = new GlyphLayout();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		ToolsBackgroundSetter.setBG(ImportantEvent.NONE);
	
		game.batch.begin();
		game.batch.draw(ToolsBackgroundSetter.toolsBG, 0, 0);
		
		font.getData().setScale(0.7f);
		glyphLayout.setText(font, "哎呀被你發現勒...");
		font.draw(game.batch, glyphLayout, 50, SimCoLife.WINDOW_HEIGHT-glyphLayout.height+10);
		glyphLayout.reset();
		font.getData().setScale(68/72f);
		glyphLayout.setText(font, "霹靂卡霹靂拉拉小貓喵喵小狗汪汪");
		font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 600);
		glyphLayout.reset();
		glyphLayout.setText(font, "神奇魔力小空間帶你穿越時光");
		font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 500);
		glyphLayout.reset();
		font.getData().setScale(40/72f);
		glyphLayout.setText(font, "　　　SimCo第一年　　　SimCo第二年　　　SimCo第三年　　　SimCo第四年　　　");
		font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 300);
		
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	public void setButtons() {
		
		group = new ButtonGroup<ImageButton>(oneYear, twoYears, threeYears, fourYears);
		
		oneYear.setPosition(50, 255);
		stage.addActor(oneYear);
		
		twoYears.setPosition(350, 255);
		stage.addActor(twoYears);
		
		threeYears.setPosition(650, 255);
		stage.addActor(threeYears);
		
		fourYears.setPosition(950, 255);
		stage.addActor(fourYears);
	}
	
	
	public void setStage() {
		stage = new Stage();
		selectedButton.setPosition(400, 80);
		selectedButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Calender.MAX_TIME = (4 - group.getCheckedIndex())*360;
				game.setScreen(new Characters(game));
			}
		});
		stage.addActor(selectedButton);
		
		returnButton.setPosition(800, 80);
		returnButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Start(game));
			}
		});
		stage.addActor(returnButton);
		
		setButtons();
	}
	
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
