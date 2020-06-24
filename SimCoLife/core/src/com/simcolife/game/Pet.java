package com.simcolife.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Pet {
	
	
	public static final TextureRegionDrawable PET_00 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePet00Img.png"))));
	public static final TextureRegionDrawable PET_01 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePet01Img.png"))));
	public static final TextureRegionDrawable PET_02 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePet02Img.png"))));
	public static final TextureRegionDrawable PET_03 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePet03Img.png"))));
	public static final TextureRegionDrawable PET_04 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePet04Img.png"))));
	public static final TextureRegionDrawable PET_PRESS_00 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePetPressed00Img.png"))));
	public static final TextureRegionDrawable PET_PRESS_01 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePetPressed01Img.png"))));
	public static final TextureRegionDrawable PET_PRESS_02 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePetPressed02Img.png"))));
	public static final TextureRegionDrawable PET_PRESS_03 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePetPressed03Img.png"))));
	public static final TextureRegionDrawable PET_PRESS_04 = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePetPressed04Img.png"))));
	
	public enum PetName{
		GIRAFFE, CAT, RABBIT, E_CHICK, NONE
	}
	
	
	private PetName selected;

	public Pet(PetName x) {
		this.selected = x;
	}
	
	public PetName getSelected() {
		return selected;
	}

	public void setSelected(PetName selected) {
		this.selected = selected;
	}
	
	

}
