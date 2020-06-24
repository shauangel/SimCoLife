package com.simcolife.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Warning extends Dialog {

	private FreeTypeFontGenerator generater;
	private FreeTypeFontParameter parameter;
	private BitmapFont font;
	
	public Warning(String title, Skin skin, String allWords) {
		super(title, skin);
		generater = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + allWords;
		font = generater.generateFont(parameter);
		setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogBGImg.png")))));
		setPosition(400, 230);
		setSize(500, 300);
		setMovable(false);
		setResizable(false);
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}
	
	@Override
	public boolean remove() {
		this.generater.dispose();
		this.font.dispose();
		return super.remove();
	}
	
	

}
