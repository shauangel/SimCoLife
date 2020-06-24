package com.simcolife.game.littlegame;

import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.simcolife.game.Player;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.event.Event;
import com.simcolife.game.event.EventType;
import com.simcolife.tools.Calender;

public class PetGameScreen extends ScreenAdapter {
	SimCoGame game;
	Player player;
	
	private Stage stage;
	public static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/PetGameMusic.mp3"));
	private Texture backgroundImg = new Texture(Gdx.files.internal("pet/PetGameBgImg.png"));
	private Texture textBackgroundImg = new Texture(Gdx.files.internal("pet/PetGameEndBgImg.png"));
	private Texture commandLineImg = new Texture(Gdx.files.internal("pet/PetGameCommandLineImg.png"));
	private Texture judgeLineImg = new Texture(Gdx.files.internal("pet/PetGameJudgeLineImg.png"));
	private Texture petImg = new Texture(Gdx.files.internal("pet/ChoosePet01Img.png"));;
	private Texture food = new Texture(Gdx.files.internal("pet/PetGameButton00Img.png"));
	private Texture toy = new Texture(Gdx.files.internal("pet/PetGameButton01Img.png"));
	private Texture bath = new Texture(Gdx.files.internal("pet/PetGameButton02Img.png"));
	private Texture tutorial = new Texture(Gdx.files.internal("pet/PetGameTutorialImg.png"));
	private Texture foodPress = new Texture(Gdx.files.internal("pet/PetGameButton00PressedImg.png"));
	private Texture toyPress = new Texture(Gdx.files.internal("pet/PetGameButton01PressedImg.png"));
	private Texture bathPress = new Texture(Gdx.files.internal("pet/PetGameButton02PressedImg.png"));
	private Texture moodImg;

	private Array<Texture> moodImgs = new Array<Texture>();
	private ImageButton foodButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(food)), new TextureRegionDrawable(new TextureRegion(foodPress)), new TextureRegionDrawable(new TextureRegion(foodPress)));
	private ImageButton toyButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(toy)), new TextureRegionDrawable(new TextureRegion(toyPress)), new TextureRegionDrawable(new TextureRegion(toyPress)));
	private ImageButton bathButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(bath)), new TextureRegionDrawable(new TextureRegion(bathPress)), new TextureRegionDrawable(new TextureRegion(bathPress)));
    private int gameScore = 0;
    private GameState currentState = GameState.START_MENU;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    
    private final long GAMETIME = 40;
    private long startTime;
    private long lastCommandTime;
    private long elapsed;
    private long commandInterval;
    private String resultString;
    
	public PetGameScreen(SimCoGame game,Player player) {
		this.game = game;
		SimCoGame.formalMusic.pause();
		SimCoGame.summerMusic.pause();
		SimCoGame.winterMusic.pause();
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 100;
		fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "奇摩子才藝肝指數 按SPACE離開剩餘時間：得分";
		font = fontGenerator.generateFont(fontParameter);
		for(int i = 0;i<3;i++) {
			moodImgs.add(new Texture(Gdx.files.internal("pet/PetGameMood0"+ Integer.toString(i)+"Img.png")));
		}
		PetCommand.setCommandsArray(new Array<PetCommand>());
		setStage();

		switch (player.getMyPet().getSelected()) {
		case E_CHICK:
			petImg = new Texture(Gdx.files.internal("pet/ChoosePet01Img.png"));
			break;
		case RABBIT:
			petImg = new Texture(Gdx.files.internal("pet/ChoosePet02Img.png"));
			break;
		case GIRAFFE:
			petImg = new Texture(Gdx.files.internal("pet/ChoosePet03Img.png"));
			break;
		case CAT:
			petImg = new Texture(Gdx.files.internal("pet/ChoosePet04Img.png"));
			break;
		default:
			break;
		}
		music.play();
		music.setLooping(true); 
	}
	private void setStage() {
		stage = new Stage();

		foodButton.setPosition(350, 80);
		foodButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScore += hitJudge(PetCommand.getCommandsArray(),PetCommand.Type.FOOD);
				foodButton.setChecked(false);
			}
		});
		stage.addActor(foodButton);
		toyButton.setPosition(575, 80);
		toyButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScore += hitJudge(PetCommand.getCommandsArray(),PetCommand.Type.TOY);
				toyButton.setChecked(false);
			}
		});
		stage.addActor(toyButton);
		bathButton.setPosition(800, 80);
		bathButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScore += hitJudge(PetCommand.getCommandsArray(),PetCommand.Type.BATH);
				bathButton.setChecked(false);
			}
		});
		stage.addActor(bathButton);
		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keyCode) {
				switch (keyCode) {
					case Keys.Z:
						foodButton.setChecked(true);
						gameScore += hitJudge(PetCommand.getCommandsArray(),PetCommand.Type.FOOD);
						break;
					case Keys.X:
						toyButton.setChecked(true);
						gameScore += hitJudge(PetCommand.getCommandsArray(),PetCommand.Type.TOY);
						break;
					case Keys.C:
						bathButton.setChecked(true);
						gameScore += hitJudge(PetCommand.getCommandsArray(),PetCommand.Type.BATH);
						break;
					default:
						break;
				}
				return super.keyDown(event, keyCode);
			}
			@Override
			public boolean keyUp(InputEvent event, int keyCode) {
				switch (keyCode) {
					case Keys.Z:
						foodButton.setChecked(false);
						break;
					case Keys.X:
						toyButton.setChecked(false);
						break;
					case Keys.C:
						bathButton.setChecked(false);
						break;
					default:
						break;
				}
				return super.keyUp(event, keyCode);
			}
			});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		switch (currentState) {
		case START_MENU:
			game.batch.begin();
			game.batch.draw(tutorial,0,0);
			font.setColor(Color.BLUE);
			game.batch.end();
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
				moodImg = new Texture(Gdx.files.internal("pet/PetGameMood00Img.png"));
				startTime = TimeUtils.nanoTime();
				lastCommandTime = TimeUtils.nanoTime();
				currentState = GameState.GAME;
				commandInterval = TimeUtils.nanoTime();
			}
			break;
		case GAME_OVER:
			game.batch.begin();
			game.batch.draw(textBackgroundImg,0,0);
			font.setColor(Color.BLUE);
			font.draw(game.batch, "得分：" + Integer.toString(gameScore)
			+ "\n\n" + resultString , 540, 550);
			font.setColor(Color.BLACK);
			font.draw(game.batch, "按SPACE離開", 980, 100);
			if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				game.setScreen(game.simcolife);
				Timer.schedule(new Task() {
					@Override
					public void run() {
						game.simcolife.getCurrPlayer().setAllStatics(getGameEvent());
						Calender.timeConvert(game.simcolife.getCurrPlayer().getTime());
					}
				}, 0.5f);
			}
			game.batch.end();
			break;
		case GAME:
			elapsed = TimeUtils.timeSinceNanos(startTime);
			game.batch.begin();
			game.batch.draw(backgroundImg,0,0);
			game.batch.draw(commandLineImg, 0, 575);
			game.batch.draw(judgeLineImg, 150, 575);
			game.batch.draw(petImg, 555, 255);
			game.batch.draw(moodImg,745,450);
			font.draw(game.batch, "得分：" + Integer.toString(gameScore), 1050, 770);
			font.draw(game.batch, "剩餘時間：" + Long.toString(GAMETIME - toSecond(elapsed)), 10, 770);
			font.setColor(Color.BLUE);
			font.getData().setScale((float) 0.5);
			font.draw(game.batch, "Z",410,50);
			font.draw(game.batch, "X",635,50);
			font.draw(game.batch, "C",860,50);
			drawCommands(PetCommand.getCommandsArray());
			game.batch.end();
			if(toSecond(TimeUtils.timeSinceNanos(commandInterval)) < 3) {
				lastCommandTime = PetCommand.autoCommand(lastCommandTime);
			}
			else if(toSecond(TimeUtils.timeSinceNanos(commandInterval)) == 4){
				commandInterval = TimeUtils.nanoTime();
			}
			moveCommands(PetCommand.getCommandsArray());
			if (GAMETIME - toSecond(elapsed) <= 0) {
				setResultString();
				currentState = GameState.GAME_OVER;
			}
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
			break;
		default:
			break;
		}
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void dispose() {
		backgroundImg.dispose();
		textBackgroundImg.dispose();
		commandLineImg.dispose();
		judgeLineImg.dispose();
		petImg.dispose();
		food.dispose();
		toy.dispose();
		bath.dispose();
		tutorial.dispose();
		foodPress.dispose();
		toyPress.dispose();
		bathPress.dispose();
		moodImg.dispose();
		for(Texture t: moodImgs)
			t.dispose();
		stage.dispose();
		music.dispose();
		font.dispose();
	}
	
	@Override
	public void hide() {
		music.dispose();
		this.dispose();
		Gdx.input.setInputProcessor(null);
	}

	
	private void drawCommands(Array<PetCommand> commands) {
		for(PetCommand c:commands) {
			switch (c.getType()) {
			case FOOD:
				game.batch.draw(food,c.getX(),c.getY());
				break;
			case TOY:
				game.batch.draw(toy,c.getX(),c.getY());
				break;
			case BATH:
				game.batch.draw(bath,c.getX(),c.getY());
				break;
			default:
				break;
			}
		}
	}
	
	
	private void moveCommands(Array<PetCommand> commands) {
			 for (Iterator<PetCommand> iter = commands.iterator(); iter.hasNext(); ) {
			    	PetCommand c  = iter.next();
			    	c.setX(c.getX() - c.getSpeed() * Gdx.graphics.getDeltaTime());
			    	if(c.getX()<-100) {
			    		moodImg = moodImgs.get(2);
			    		iter.remove();			    		
			    	}

			}
	}
	
	private int hitJudge(Array<PetCommand> commands,PetCommand.Type judgeType) {
		int score = 0;
		if(!commands.isEmpty() && commands.get(0).getX() <= 150 + 20) {
			PetCommand target = commands.get(0);
			if(judgeType == target.getType()) {
				game.batch.begin();
				if((target.getX() >= 95 && target.getX()<=120)) {
					moodImg = moodImgs.get(1);
					score +=10;	
				}
				else if((target.getX() >= 55 && target.getX() <= 140)){
					moodImg = moodImgs.get(0);
					score +=5;
				}
				else {
					moodImg = moodImgs.get(2);
					score +=0;
				}
				commands.removeIndex(0);
				game.batch.end();
				return score;
			}
		}
		return 0;
	}
	private long toSecond(long nanosecond) {
		return nanosecond/1000000000;
	}
	private void setResultString() {
		if (gameScore < 100) {
			resultString = "奇摩子 + 5";
		}
		else if(gameScore>200 && gameScore <= 300) {
			resultString = "肝指數 - 5\n奇摩子 + 5";
		}
		else if(gameScore>300 && gameScore <= 350) {
			resultString = "肝指數 - 5\n奇摩子 + 10";
		}
		else {
			resultString = "肝指數 - 5\n奇摩子 + 10\n才藝 + 5";
		}
	}
	public Event getGameEvent() {
		if (gameScore < 100) {
			
			Event petEvent = new Event("PetGame", "pet", "petgame"
	    			, 0, 0, -1000, 0, 5, 2, EventType.PET, 0);
			return petEvent;
		}
		else if(gameScore>200 && gameScore <= 300) {
			Event petEvent = new Event("PetGame", "pet", "petgame"
	    			, -5, 0, -1000, 0, 5, 2, EventType.PET, 0);
			return petEvent;
		}
		else if(gameScore>300 && gameScore <= 350) {
			Event petEvent = new Event("PetGame", "pet", "petgame"
	    			, -5, 0, -1000, 0, 10, 2, EventType.PET, 0);
			return petEvent;
		}
		else {
			Event petEvent = new Event("PetGame", "pet", "petgame"
	    			, -5, 0, -1000, 0, 10, 2, EventType.PET, 5);
			return petEvent;
		}
		
	}
}
