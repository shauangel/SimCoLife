SimCoGame game;
	Player player;
	private int INIT_BULLET_SIZE = 50;
    private int INIT_CHARACTER_SIZE = 120;
    private int INIT_ENEMY_SIZE = 120;
    public static float SOUND_VOLUME = 0.5f;
    private int gameScore = 0;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private String fontCharacters;
	private GameState currentGameState = GameState.START_MENU;
	private Texture characterImg;
	private Texture enemyImg = new Texture(Gdx.files.internal("exam/FinalEnemyImg.png"));
	private Texture backgroundImg = new Texture(Gdx.files.internal("exam/MidtermBgImg.png"));
	private Texture playerBulletImg = new Texture(Gdx.files.internal("exam/PlayerBulletImg.png"));
	private Texture enemyBulletImg = new Texture(Gdx.files.internal("exam/EnemyBulletImg.png"));
	private Texture gameResultImg = new Texture(Gdx.files.internal("exam/ExamScoreBgImg.png"));
	private Texture startMenuImg = new Texture(Gdx.files.internal("exam/FinalTutorialImg.png"));
	
	public static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/ExamMusic.mp3"));
	public static Sound shootSound = Gdx.audio.newSound(Gdx.files.internal("music/ShootingSound.mp3"));
	
    private Character character;
    private Enemy enemy;
    private long startTime;
    private long lastAttackTime;
    private long elapsed;
    
    private void setFont(int fontSize,String fontCharacters) {
    	fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		fontParameter = new FreeTypeFontParameter();
		fontParameter.size = fontSize;
		fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + fontCharacters;
		font = fontGenerator.generateFont(fontParameter);
    }

	public FinalExamScreen(SimCoGame game,Player player) {
		this.game = game;
		SimCoGame.formalMusic.pause();
		this.player = player;
		fontCharacters = "按SPACE離開剩餘時間：得期末考試成績獲得學分";
		setFont(100, fontCharacters);
		
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
		enemy = new Enemy(325, 780 - INIT_ENEMY_SIZE/2, 100, 600,INIT_ENEMY_SIZE);
		character = new Character(650,INIT_CHARACTER_SIZE/2, 100, 500,INIT_CHARACTER_SIZE);
		
		character.bullets = new Array<Bullet>();
		enemy.bullets = new Array<Bullet>();
		
		music.play();
		music.setLooping(true); 
	}


	@Override
	public void render(float delta) {
		game.batch.begin();
	    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);    
	    switch(currentGameState) {
	    case START_MENU:
	    	game.batch.draw(startMenuImg,0,0);
			if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
				startTime = TimeUtils.nanoTime();
				lastAttackTime = TimeUtils.nanoTime();
				currentGameState = GameState.GAME;
			}
	    	break;
	    case GAME_OVER:
	    	game.batch.draw(gameResultImg,0,0);
			font.setColor(Color.RED);
			font.draw(game.batch, "期末考試成績："+ gameScore +"  分\n" 
					+ "獲得學分：" + Integer.toString(Math.round(gameScore/10)), 300, 490);
			
			font.setColor(Color.BLACK);
			font.draw(game.batch, "按SPACE離開", 300, 300);
			if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
				this.dispose();
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
			font.setColor(Color.BLACK);
			game.batch.draw(backgroundImg,0,0);
			game.batch.draw(characterImg,character.getImgX(),character.getImgY());
			game.batch.draw(enemyImg, enemy.getImgX(), enemy.getImgY());
			font.draw(game.batch, "得分：" + Integer.toString(100 - enemy.getHp()), 1050, 770);	
			font.draw(game.batch, "剩餘時間：" + Long.toString(20 - elapsed/1000000000), 10, 770);
			font.setColor(Color.BLUE);
			font.getData().setScale((float) 0.5);
			font.draw(game.batch, Integer.toString(character.getHp()),character.getImgX(),character.getImgY() + INIT_CHARACTER_SIZE + 25);
			font.draw(game.batch, Integer.toString(enemy.getHp()),enemy.getImgX(),enemy.getImgY());
			elapsed = TimeUtils.timeSinceNanos(startTime);
			
		    enemy.autoFight(character);
		    character.bulletsFly(enemy, true);
		    enemy.bulletsFly(character, false);
		    drawCharacterBullets();
		    drawEnemyBullets();
			lastAttackTime = enemy.autoShoot(lastAttackTime);
		    controlPlayer();
		    isOver();   
	    	break;
	    default:
	    	break;
	    }
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}
	
	@Override
	public void dispose () {
		music.dispose();
		characterImg.dispose();
		enemyImg.dispose();
		backgroundImg.dispose();
		playerBulletImg.dispose();
		enemyBulletImg.dispose();
		gameResultImg.dispose();
		startMenuImg.dispose();
		shootSound.dispose();
		Gdx.input.setInputProcessor(null);
		super.dispose();
	}
    private void isOver() {
    	if (20 - elapsed/1000000000 <= 0 || character.getHp() == 0 ||enemy.getHp() == 0) {
			if(character.getHp()==0) {
				gameScore = 0;
			}
			else {
				gameScore = 100 - enemy.getHp();
			}
			currentGameState = GameState.GAME_OVER;
    	}
    }
    private void drawCharacterBullets() {
		for(Bullet bullet: character.bullets)
			game.batch.draw(playerBulletImg, bullet.getImgX(), bullet.getImgY());
    }
    private void drawEnemyBullets() {
		for(Bullet bullet: enemy.bullets)
			game.batch.draw(enemyBulletImg, bullet.getImgX(), bullet.getImgY());
    }
    public Event getGameEvent() {
    	Event finalEvent = new Event("Final", "big", "FinalExam"
    			, 0, 0, 0, Math.round(gameScore/10), 0, 3, EventType.SOSO, 0);
    	return finalEvent;
    }
    
    private void controlPlayer() {
    	if(Gdx.input.isKeyPressed(Keys.LEFT) && character.getX() > 0 + character.getSize()) {
			character.setX(character.getX() - character.getSpeed()* Gdx.graphics.getDeltaTime());
		}
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)&& character.getX() < 1300 - character.getSize()) {
	    	character.setX(character.getX() + character.getSpeed()* Gdx.graphics.getDeltaTime());
	    }
	    if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
	    	Bullet bullet = new Bullet(character.getX() , character.getSize(),700,5,INIT_BULLET_SIZE);
	        character.bullets.add(bullet);
	        shootSound.play(SOUND_VOLUME);
	        Timer.schedule(new Task() {
				@Override
				public void run() {
					shootSound.stop();
				}
			}, 0.1f);
	    }
    	
    }

}
