package com.simcolife.game;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
import com.simcolife.game.Pet.PetName;
import com.simcolife.game.event.BlankEvent;
import com.simcolife.game.event.Cards;
import com.simcolife.game.event.ChoiceEvent;
import com.simcolife.game.event.EventType;
import com.simcolife.game.event.HospitalEvent;
import com.simcolife.game.event.PetEvent;
import com.simcolife.game.event.RandomEvent;
import com.simcolife.game.event.StartEvent;
import com.simcolife.game.event.TwaEvent;
import com.simcolife.game.littlegame.FinalExamScreen;
import com.simcolife.game.littlegame.MidtermExamScreen;
import com.simcolife.game.littlegame.PetGameScreen;
import com.simcolife.tools.Calender;
import com.simcolife.tools.Dice;
import com.simcolife.tools.Menu;
import com.simcolife.tools.Tutorial;
import com.simcolife.tools.Warning;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class SimCoLife extends ScreenAdapter {
	
	public static final int WINDOW_WIDTH = 1300;
	public static final int WINDOW_HEIGHT = 780;
	public static final int BLOCK_START_X = 80;
	public static final int BLOCK_START_Y = 163;
	public static final int BLOCKS_MAX = 39;
	public static final int BLOCK_WIDTH = 76;
	public static final int DICE_WIDTH = 156;
	public static final int BUTTON_WIDTH = 45;
	public static final int BUTTON_INTERVAL = 20;
	
	
	private SimCoGame game;
	private Stage stage;
	
//--------------------------------Other Variables------------------------------------
	private Dice gameDice = new Dice();
	private float timeCounter = 0f;
	private final float period = 0.2f;
	private Player currPlayer;

//-------------------------------sound---------------------------------
	public static Music allowance;
	public static Music dice;
	
//--------------------------font------------------------------
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
	
//--------------------route information-----------------------
	public static EventType[] routeList = new EventType[36];
	public static ArrayList<Block> route = new ArrayList<Block>();
	public static int playerNow;
	public int nextBlock;

//----------------------------Window's Composition Texture---------------------------
	private Texture bottom = new Texture(Gdx.files.internal("BottomListImg.png"));

//----------------------------------- dice button -----------------------------------
	private ImageButton diceButton = new ImageButton(gameDice.getDiceFormal(), gameDice.getDicePressed());
		
//----------------------------------- menu button -----------------------------------
	private Texture menu = new Texture(Gdx.files.internal("ButtonMenuImg.png"));
	private Texture menuPress = new Texture(Gdx.files.internal("ButtonPressedMenuImg.png"));
	private ImageButton menuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(menu)), new TextureRegionDrawable(new TextureRegion(menuPress)));
		
//--------------------------------- setting button ----------------------------------
	private Texture setting = new Texture(Gdx.files.internal("ButtonSettingImg.png"));
	private Texture settingPress = new Texture(Gdx.files.internal("ButtonPressedSettingImg.png"));
	private ImageButton settingButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(setting)), new TextureRegionDrawable(new TextureRegion(settingPress)));
		
//--------------------------------- tutorial button ---------------------------------
	private Texture tutorial = new Texture(Gdx.files.internal("ButtonTutorialImg.png"));
	private Texture tutorialPress = new Texture(Gdx.files.internal("ButtonPressedTutorialImg.png"));
	private ImageButton tutorialButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(tutorial)), new TextureRegionDrawable(new TextureRegion(tutorialPress)));

//------------------------------pet button--------------------------
	private ImageButton petGameButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pet/PetGameButton.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pet/PetGameButtonPressed.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("pet/PetGameButtonDisable.png")))));
//--------------------------BG-------------------------------
	public static final Texture FORMAL = new Texture(Gdx.files.internal("FormalBgImg.png"));
	public static final Texture WINTER = new Texture(Gdx.files.internal("WinterBgImg.png"));
	public static final Texture SUMMER = new Texture(Gdx.files.internal("SummerBgImg.png"));
	
	
	public SimCoLife(SimCoGame g) {
		this.game = g;
		Block.setBlocks(routeList);
		initRoute(route);
		setFont();
		setStage();
		Calender.timeConvert(Calender.MAX_TIME);
		allowance = Gdx.audio.newMusic(Gdx.files.internal("music/getAllowance.mp3"));
		dice = Gdx.audio.newMusic(Gdx.files.internal("music/RollDiceSound.mp3"));
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(currPlayer.getMyPet().getSelected() == PetName.NONE) {
			petGameButton.setDisabled(true);
			petGameButton.setChecked(true);
		}
		
		if(currPlayer.getHealth() > 90) {
			this.nextBlock = 20;
			SimCoLife.playerNow = 20;
			triggerEvent();
		}
		
		if(currPlayer.getKimoji() < 20) {
			this.nextBlock = 20;
			SimCoLife.playerNow = 20;
			triggerEvent();
		}
		
		game.batch.begin();
		
		switch(currPlayer.getCurrentSeason()) {
			case NONE:
				triggerMusic(SimCoGame.winterMusic, false);
				triggerMusic(SimCoGame.summerMusic, false);
				triggerMusic(SimCoGame.formalMusic, true);
				game.batch.draw(FORMAL, 0, 0);
				break;
			case WINTER:
				triggerMusic(SimCoGame.formalMusic, false);
				triggerMusic(SimCoGame.winterMusic, true);
				game.batch.draw(WINTER, 0, 0);
				break;
			case SUMMER:
				triggerMusic(SimCoGame.formalMusic, false);
				triggerMusic(SimCoGame.summerMusic, true);
				game.batch.draw(SUMMER, 0, 0);
				break;
			default:
				triggerMusic(SimCoGame.winterMusic, false);
				triggerMusic(SimCoGame.summerMusic, false);
				triggerMusic(SimCoGame.formalMusic, true);
				game.batch.draw(FORMAL, 0, 0);
		}
		
		game.batch.draw(bottom, 0, 0);
		game.batch.draw(currPlayer.getAvatar(), 0, 0);
		drawCalendar();
		
//--------------------------------draw map------------------------------------
		for(Block i : route) {
			game.batch.draw(i.getTexture(), i.getX(), i.getY());
		}
//--------------------------------player moving------------------------------------
		game.batch.draw(currPlayer.getChoosed(), route.get(playerNow).getX(), route.get(playerNow).getY());
		
		if(playerNow != nextBlock) {
			timeCounter += Gdx.graphics.getDeltaTime();
			if(timeCounter > period) {
				setPlayerNow();
				timeCounter -= period;
				triggerEvent();
			}
		}
		
//------------------------------write player's information--------------------------------	
		font.getData().setScale(0.5f);
		font.setColor(Color.BLACK);
		font.draw(game.batch, "肝指數：" + "    " + String.format("%-,7d", currPlayer.getHealth()), 240, 130);
		font.draw(game.batch, "夯指數：" + "    " + String.format("%-,7d", currPlayer.getRelationship()), 240, 90);
		font.draw(game.batch, "奇摩子：" + "    " + String.format("%-,7d", currPlayer.getKimoji()), 240, 50);
		font.draw(game.batch, "才　藝：" + "    " + String.format("%-,7d", currPlayer.getTalent()), 700, 130);
		font.draw(game.batch, "學　分：" + "    " + String.format("%-,7d", currPlayer.getScore()), 700, 90);
		font.draw(game.batch, "零用錢：" + "    " + String.format("%-,7d", currPlayer.getMoney()), 700, 50);

		game.batch.end();
		
		if(currPlayer.isExamTime()) {
			determineTest();
		}
		
		if(currPlayer.getTime() == 0) {
			game.setScreen(new Ending(game));
		}
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	public void setStage() {
		stage = new Stage();
//------------------------buttons------------------------------
		diceButton.setPosition(WINDOW_WIDTH-DICE_WIDTH, 0);
		diceButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(SimCoLife.playerNow == SimCoLife.this.nextBlock) {
					dice.play();
					Timer.schedule(new Task() {
						@Override
						public void run() {
							setNextBlock(Dice.rollDice());
							dice.stop();
						}
					}, 0.2f);
				}
			}
		});
		stage.addActor(diceButton);
//------------------menu------------------------
		menuButton.setPosition(WINDOW_WIDTH-(BUTTON_WIDTH+BUTTON_INTERVAL)*3, WINDOW_HEIGHT-BUTTON_WIDTH);
		menuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Menu(game));
				
			}
			
			
		});
		stage.addActor(menuButton);
//------------------setting-----------------------
		settingButton.setPosition(WINDOW_WIDTH-(BUTTON_WIDTH+BUTTON_INTERVAL)*2, WINDOW_HEIGHT-BUTTON_WIDTH);
		settingButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(game.setting);
			}
		});
		stage.addActor(settingButton);
//--------------------tutorial-------------------------
		tutorialButton.setPosition(WINDOW_WIDTH-(BUTTON_WIDTH+BUTTON_INTERVAL), WINDOW_HEIGHT-BUTTON_WIDTH);
		tutorialButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new Tutorial(game, false));
			}
		});
		stage.addActor(tutorialButton);
//---------------------pet---------------------------
		
		petGameButton.setPosition(830, 200);
		petGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				petGameButton.setChecked(false);
				
				final Warning warn = new Warning("", game.skin, "寵物遊樂園，進入一次花費元二日遊，票價");
				LabelStyle style1 = new LabelStyle(warn.getFont(), Color.RED);
				Label label1 = new Label("寵物遊樂園二日遊，票價1000元", style1);
				label1.setPosition((500-label1.getWidth())/2, 160);
				ImageButton yes = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogButtonYesImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogPressedYesImg.png")))));
				ImageButton no = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogButtonNoImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogPressedNoImg.png")))));
				yes.setPosition((500-yes.getWidth())/2-100, 55);
				yes.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						if(currPlayer.getMoney() >= 1000) {
							warn.remove();
							game.setScreen(new PetGameScreen(game, currPlayer));
						}
						else {
							warn.remove();
							final Warning money = new Warning("", game.skin, "您沒錢入場：Ｄ");
							LabelStyle style1 = new LabelStyle(money.getFont(), Color.RED);
							Label label1 = new Label("您沒錢入場：Ｄ", style1);
							label1.setPosition((500-label1.getWidth())/2, 160);
							ImageButton sure = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogButtonYesImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogPressedYesImg.png")))));
							sure.setPosition((500-sure.getWidth())/2, 55);
							sure.addListener(new ClickListener() {
								@Override
								public void clicked(InputEvent event, float x, float y) {
									money.remove();
								};
							});
							money.addActor(label1);
							money.addActor(sure);
							money.show(stage);
						}
					};
				});
				no.setPosition((500-no.getWidth())/2+100, 55);
				no.addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent event, float x, float y) {
						warn.remove();
					};
				});
				warn.addActor(label1);
				warn.addActor(yes);
				warn.addActor(no);
				warn.show(stage);
			}
		});
		stage.addActor(petGameButton);
	}
	public void setFont() {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 52;
        fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + Cards.INFO;
        font = fontGenerator.generateFont(this.fontParameter);
	}
	 
	@Override
	public void dispose () {
		super.dispose();
	}
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	@Override
	public void show() {	
		Gdx.input.setInputProcessor(stage);
	}
	
	public Player getCurrPlayer() {
		return currPlayer;
	}
	
	public void determineTest() {
		switch(currPlayer.getCurrentSeason()) {
			case MIDTERM:
				game.setScreen(new MidtermExamScreen(game, currPlayer));
				currPlayer.setExamTime(false);
				break;
			case FINAL:
				game.setScreen(new FinalExamScreen(game, currPlayer));
				currPlayer.setExamTime(false);
				currPlayer.setVacationTime(true);
				break;
			default:
				break;
		}
	}
	
	//建造block路徑
	public static void initRoute(ArrayList<Block> routeInput) {
		int currentX = BLOCK_START_X, currentY = BLOCK_START_Y;
		int currentCat = 0;
		
		for(int i=0; i<= BLOCKS_MAX; i++) {
			routeInput.add(new Block(currentX, currentY));
			if((i>=0 && i<=6) || (i>=13 && i<=19)) {
				currentX += BLOCK_WIDTH;
			}
			else if(i >= 7 && i<= 12) {
				currentY += BLOCK_WIDTH;
			}
			else if((i>=20 && i<=22) || (i>=37 && i<39)) {
				currentY -= BLOCK_WIDTH;
			}
			else if(i>=23 && i<=36) {
				currentX -= BLOCK_WIDTH;
			}
			
			if(i == 0) {
				routeInput.get(i).setInfo(EventType.START);
			}
			else if(i == 20) {
				routeInput.get(i).setInfo(EventType.HOSPITAL);
			}
			else if(i == 10 || i == 30) {
				routeInput.get(i).setInfo(EventType.TWA);
			}
			else {
				routeInput.get(i).setInfo(routeList[currentCat]);
				currentCat++;
			}
		}
	}
	//set which block is the next position
	//roll dice once cost 1 day
	private void setNextBlock(int movement) {
		this.nextBlock = (nextBlock+movement)%(SimCoLife.BLOCKS_MAX+1);
		currPlayer.setTime(currPlayer.getTime()-1);
		Calender.timeConvert(currPlayer.getTime());
	}
	//set the player to the next block
	private void setPlayerNow() {
		SimCoLife.playerNow = (SimCoLife.playerNow+1)%(SimCoLife.BLOCKS_MAX+1);
		if(SimCoLife.playerNow == 0) {
			allowance.play();
			Timer.schedule(new Task() {
				@Override
				public void run() {
					currPlayer.setMoney(currPlayer.getMoney() + 5000);
					allowance.stop();
				}
			}, 0.5f);
		}
	}
//	public Texture createPixmap() {
//		Pixmap pixmap = new Pixmap(550, 110, Pixmap.Format.RGBA8888);
//		pixmap.setColor(Color.BLACK);
//		pixmap.drawRectangle(0, 0, 550, 110);
//		Texture t = new Texture(pixmap);
//		pixmap.dispose();
//		return t;
//	}

	
	//draw calendar information
	private void drawCalendar() {
		Calender.timeConvert(currPlayer.getTime());
		game.batch.draw(Calender.CALENDER_BG, 20, WINDOW_HEIGHT-130);
		game.batch.draw(Calender.n_1, Calender.LOCATION01, WINDOW_HEIGHT-125);
		game.batch.draw(Calender.n_2, Calender.LOCATION02, WINDOW_HEIGHT-125);
		game.batch.draw(Calender.n_3, Calender.LOCATION03, WINDOW_HEIGHT-125);
		game.batch.draw(Calender.n_4, Calender.LOCATION04, WINDOW_HEIGHT-125);
		game.batch.draw(Calender.n_5, Calender.LOCATION05, WINDOW_HEIGHT-125);
	}
	
	public void setCurrPlayer(Player currPlayer) {
		this.currPlayer = currPlayer;
	}
	
	//after player reach the target block, trigger event and decide which type of event is it
	private void triggerEvent() {
		
		if(playerNow == nextBlock) {
			Cards.setBoard(currPlayer.getCurrentSeason());
		//player arrived target block, wait 0.5sec then trigger event
			Timer.schedule(new Task() {
				
				@Override
				public void run() {
					switch(route.get(playerNow).getEvent().getType()) {
						case RANDOM:
							game.setScreen(new RandomEvent(game));
							break;
						case CHOICE:
							game.setScreen(new ChoiceEvent(game, route.get(playerNow).getChoice()));
							break;
						case BLANK:
							game.setScreen(new BlankEvent(game));
							break;
						case HOSPITAL:
							game.setScreen(new HospitalEvent(game));
							break;
						case START:
							game.setScreen(new StartEvent(game));
							break;
						case TWA:
							game.setScreen(new TwaEvent(game));
							break;
						case PET:
							game.setScreen(new PetEvent(game));
							break;
						default:
							break;
					}
				}
			}, 0.2f);
		}
	}
	
	public static void triggerMusic(Music music, boolean play) {
		if(play) {
			if(!music.isPlaying()) {
				music.play();
			}
		}
		else {
			if(music.isPlaying()) {
				music.stop();
			}
		}
	}
	
}
