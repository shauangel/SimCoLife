package com.simcolife.game.littlegame;

import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.simcolife.game.Player;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.event.Event;
import com.simcolife.game.event.EventType;
import com.simcolife.tools.Calender;

public class MidtermExamScreen extends ScreenAdapter {
	SimCoGame game;
	Player player;
    private int INIT_BULLET_SIZE = 50;
    private int INIT_CHARACTER_SIZE = 120;
    private Character character;
    private Array<Bullet> bullets;
    private long startTime;
    private long lastDropTime;
    private long elapsed;
    
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
	private GameState currentState;
	private String fontCharacters;
	
	public static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/ExamMusic.mp3"));

	private Texture characterImg;
    private Texture backgroundImg = new Texture(Gdx.files.internal("exam/MidtermBgImg.png"));
    private Texture gameResultImg = new Texture(Gdx.files.internal("exam/ExamScoreBgImg.png"));
    private Texture bulletImg = new Texture(Gdx.files.internal("exam/MidtermEnemyImg.png"));
    private Texture tutorial = new Texture(Gdx.files.internal("exam/MidtermTutorialImg.png"));
    
    private void setFont(int fontSize,String fontCharacters) {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		fontParameter = new FreeTypeFontParameter();
		fontParameter.size = fontSize;
		fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + fontCharacters;
		font = fontGenerator.generateFont(fontParameter);
    }
	public MidtermExamScreen(SimCoGame game,Player player) {
		this.player = player;
		this.game = game;
		SimCoGame.formalMusic.pause();
		currentState = GameState.START_MENU;
		fontCharacters = "按SPACE離開剩餘時間：得期中考試成績獲得學分";
		setFont(25, fontCharacters);
		
		character = new Character(650,INIT_CHARACTER_SIZE/2, 100, 500,INIT_CHARACTER_SIZE);
		bullets = new Array<Bullet>();
		switch (player.getGender()) {
		case 'M':
			characterImg = new Texture(Gdx.files.internal("exam/CharacterMaleImg.png"));
			break;
		case 'F':
			characterImg = new Texture(Gdx.files.internal("exam/CharacterFemaleImg.png"));
			break;
		default:
			break;
	}
		music.play();
		music.setLooping(true);
	}
	
	public Event getGameEvent() {
    	Event midtermEvent = new Event("Midterm", "big", "MidtermExam"
    			, 0, 0, 0, Math.round(character.getHp()/10), 0, 3, EventType.SOSO, 0);
    	return midtermEvent;
    }

	@Override
	public void render(float delta) {
		game.batch.begin();
	    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	if(currentState == GameState.START_MENU) {
			startTime = TimeUtils.nanoTime();
			game.batch.draw(tutorial,0,0);
			font.setColor(Color.BLUE);
			font.getData().setScale(2);
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				startTime = TimeUtils.nanoTime();
				currentState = GameState.GAME;
			}
		}else if(currentState == GameState.GAME_OVER){
			game.batch.draw(gameResultImg,0,0);
			font.setColor(Color.RED);
			font.getData().setScale(4);
			font.draw(game.batch, "期中考試成績："+ character.getHp()+"  分\n" 
					+ "獲得學分：" + Integer.toString(Math.round(character.getHp()/10)), 300, 590);
			font.setColor(Color.BLACK);
			font.draw(game.batch, "按SPACE離開", 300, 300);
			if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				dispose();
				music.stop();
				Timer.schedule(new Task() {
					@Override
					public void run() {
						game.simcolife.getCurrPlayer().setAllStatics(getGameEvent());
						Calender.timeConvert(game.simcolife.getCurrPlayer().getTime());
					}
				}, 0.5f);
				game.setScreen(game.simcolife);
			}
		}
		else if(currentState == GameState.GAME){
			game.batch.draw(backgroundImg,0,0);
			game.batch.draw(characterImg,character.getImgX(),character.getImgY());
			font.draw(game.batch, "得分：" + Integer.toString(character.getHp()), 1050, 770);	
			font.draw(game.batch, "剩餘時間：" + Long.toString(20 - elapsed/1000000000), 10, 770);
			elapsed = TimeUtils.timeSinceNanos(startTime);
			controlPlayer();
		    drawEnemyBullets();
			lastDropTime = autoDrop(lastDropTime);
		    bulletsFly();
		    isOver();
		}
    	game.batch.end();
	}
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}
	@Override
	public void dispose () {
	    characterImg.dispose();
	    backgroundImg.dispose();
	    gameResultImg.dispose();
	    bulletImg.dispose();
	    tutorial.dispose();
		super.dispose();
	}
	private void drawEnemyBullets() {
		for(Bullet bullet: bullets) {
			game.batch.draw(bulletImg, bullet.getImgX(), bullet.getImgY());
		}
	}
	private long autoDrop(long lastDropTime) {
		if(TimeUtils.nanoTime() - lastDropTime > 250000000) {
	    	Bullet bullet = new Bullet(MathUtils.random(0, 1300 - INIT_BULLET_SIZE) , 780,MathUtils.random(4, 12)*100,5,INIT_BULLET_SIZE
	    			);
	        bullets.add(bullet);
	        return TimeUtils.nanoTime();
		}
		else 
			return lastDropTime;
	}
	private void bulletsFly() {
		for (Iterator<Bullet> iter = bullets.iterator(); iter.hasNext(); ) {
	    	Bullet bullet = iter.next();
	    	bullet.setY( bullet.getY() - bullet.getSpeed() * Gdx.graphics.getDeltaTime());
	    	if(bullet.getY() + bullet.getSize() < 0) iter.remove();
	    	if(character.hitBy(bullet)) {
	    		character.setHp(character.getHp()-5);
	    		iter.remove();
          }
	    }
	}
	private void controlPlayer() {
		if(Gdx.input.isKeyPressed(Keys.LEFT) && character.getX() > 0 + character.getSize()) {
			character.setX(character.getX() - character.getSpeed()* Gdx.graphics.getDeltaTime());
		}
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)&& character.getX() < 1300 - character.getSize()) {
	    	character.setX(character.getX() + character.getSpeed()* Gdx.graphics.getDeltaTime());
	    }
	}
	private void isOver() {
	    if(20 - elapsed/1000000000 <= 0 || character.getHp() == 0) {
	    	currentState = GameState.GAME_OVER;
	    }
	}
}
