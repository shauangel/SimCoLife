package com.simcolife.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.simcolife.game.Pet.PetName;
import com.simcolife.tools.Warning;

public class Characters extends ScreenAdapter {

	private SimCoGame game;
	private Stage stage;
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
	private Player player = new Player();
	
//--------------------------------textures-----------------------------------
	private Texture bg = new Texture(Gdx.files.internal("CharacterBGImg.png"));
	private Texture petList = new Texture(Gdx.files.internal("choose/ChooseBottomListImg.png"));
	private TextureRegionDrawable selected = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChooseButtonImg.png"))));
	private TextureRegionDrawable selectPressed = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChoosePressedImg.png"))));
	
//--------------------------------button----------------------------------
	private ImageButton maleButton = new ImageButton(Player.MALE, null, Player.MALE_PRESS);
	private ImageButton femaleButton = new ImageButton(Player.FEMALE, null, Player.FEMALE_PRESS);
	private ImageButton selectButton = new ImageButton(selected, selectPressed);
	private ImageButton returnButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/MenuPressedReturn.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("tools/MenuButtonReturn.png")))));
	private ArrayList<ImageButton> pets = new ArrayList<ImageButton>();

//--------------------------------other variable----------------------------------
	
	public Characters(SimCoGame game) {
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
        this.fontParameter = new FreeTypeFontParameter();
        this.fontParameter.size = 50;
        this.fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "請選擇角色與寵物";
		this.game = game;
		font = fontGenerator.generateFont(this.fontParameter);
		
		initPetList();
		setStage();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(bg, 0, 0);
		game.batch.draw(petList, 0, 0);
		font.getData().setScale(1f);
		font.setColor(Color.BLACK);
		font.draw(game.batch, "請選擇角色與寵物", 475, 600);
		
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
	}
	
	private void initPetList() {
		
		ImageButton pet00Button = new ImageButton(Pet.PET_00, null, Pet.PET_PRESS_00);
		ImageButton pet01Button = new ImageButton(Pet.PET_01, null, Pet.PET_PRESS_01);
		ImageButton pet02Button = new ImageButton(Pet.PET_02, null, Pet.PET_PRESS_02);
		ImageButton pet03Button = new ImageButton(Pet.PET_03, null, Pet.PET_PRESS_03);
		ImageButton pet04Button = new ImageButton(Pet.PET_04, null, Pet.PET_PRESS_04);
		
		pets.add(pet00Button);
		pets.add(pet01Button);
		pets.add(pet02Button);
		pets.add(pet03Button);
		pets.add(pet04Button);
	}
	
	private void petsSetFalse() {
		for(ImageButton i : pets) {
			i.setChecked(false);
		}
	}
	
	
	
	private void setStage() {
		stage = new Stage();
		returnButton.setPosition((SimCoLife.WINDOW_WIDTH-150)/2, 250);
		returnButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Preparation(game));
			}
		});
		stage.addActor(returnButton);
//-----------------------------male-----------------------------------
		maleButton.setPosition(100, SimCoLife.WINDOW_HEIGHT/4+30);
		maleButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				maleButton.setChecked(true);
				femaleButton.setChecked(false);
				player.setGender('M');
			}
			
		});
		stage.addActor(maleButton);
		
//-----------------------------female-----------------------------------
		femaleButton.setPosition(SimCoLife.WINDOW_WIDTH-430, SimCoLife.WINDOW_HEIGHT/4+30);
		femaleButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				maleButton.setChecked(false);
				femaleButton.setChecked(true);
				player.setGender('F');
			}
			
		});
		stage.addActor(femaleButton);
		
//-----------------------------pet00-----------------------------------
		pets.get(0).setPosition(45, 12.5f);
		pets.get(0).addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				petsSetFalse();
				pets.get(0).setChecked(true);
				player.setMyPet(new Pet(PetName.NONE));
			}
			
		});
		stage.addActor(pets.get(0));
		
//-----------------------------pet01-----------------------------------
		pets.get(1).setPosition(305, 12.5f);
		pets.get(1).addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				petsSetFalse();
				pets.get(1).setChecked(true);
				player.setMyPet(new Pet(PetName.E_CHICK));
			}
			
		});
		stage.addActor(pets.get(1));
		
//-----------------------------pet02-----------------------------------
		pets.get(2).setPosition(565, 12.5f);
		pets.get(2).addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				petsSetFalse();
				pets.get(2).setChecked(true);
				player.setMyPet(new Pet(PetName.RABBIT));
			}
			
		});
		stage.addActor(pets.get(2));
		
//-----------------------------pet03-----------------------------------
		pets.get(3).setPosition(825, 12.5f);
		pets.get(3).addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				petsSetFalse();
				pets.get(3).setChecked(true);
				player.setMyPet(new Pet(PetName.GIRAFFE));
			}
			
		});
		stage.addActor(pets.get(3));
		
//-----------------------------pet04-----------------------------------
		pets.get(4).setPosition(1085, 12.5f);
		pets.get(4).addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				petsSetFalse();
				pets.get(4).setChecked(true);
				player.setMyPet(new Pet(PetName.CAT));
			}
			
		});
		stage.addActor(pets.get(4));
		
//-----------------------------select-----------------------------------
		selectButton.setPosition(500, 400);
		selectButton.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				char gender = player.getGender();
				boolean pet = false;
				for(ImageButton i : pets) {
					pet = i.isChecked();
					if(pet) {
						break;
					}
				}
				
				
				if(gender == 'X' || !pet) {
					final Warning warn = new Warning("", game.skin, "未選擇角色/寵物按SPACE繼續");
					LabelStyle labelStyle1 = new LabelStyle(warn.getFont(), Color.RED);
					LabelStyle labelStyle2 = new LabelStyle(warn.getFont(), Color.BLACK);
					Label label1 = new Label("未選擇角色/寵物", labelStyle1);
					label1.setAlignment(Align.center);
					Label label2 = new Label("按SPACE繼續", labelStyle2);
					label2.setAlignment(Align.center);
					label1.setPosition((500-label1.getWidth())/2, 160);
					label2.setPosition((500-label2.getWidth())/2, 50);
					warn.getContentTable().addActor(label1);
					warn.getContentTable().addActor(label2);
					warn.addListener(new InputListener() {
						@Override
						public boolean keyDown(InputEvent event, int keycode) {
							if(keycode == Keys.SPACE) {
								warn.remove();
							}
							return super.keyDown(event, keycode);
							
						};
					});
					warn.show(stage);
					
				}
				else {
					game.addPlayerList(player);
					game.simcolife.setCurrPlayer(game.playerList.get(game.playerList.size()-1));
					game.setScreen(game.simcolife);
				}
			}
			
		});
		stage.addActor(selectButton);
		
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
	
	public void dispose() {
		super.dispose();
	}

	
}
