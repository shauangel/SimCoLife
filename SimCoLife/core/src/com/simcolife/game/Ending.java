package com.simcolife.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Ending extends ScreenAdapter {
	SimCoGame game;
	private enum Page{
		DIPLOMA,ENDING
	}
	private Page currentPage;
	private Player currPlayer;
	private Texture diplomaBg = new Texture(Gdx.files.internal("ending/EndingImg1.png"));
	private Texture avatar;
	private Texture endingBg = new Texture(Gdx.files.internal("ending/EndingDescriptionImg.png"));
	
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    
    private String endingDescription;
    public static Music endingMusic = Gdx.audio.newMusic(Gdx.files.internal("music/EndingMusic.mp3"));
    
    public Ending(SimCoGame game) {
    	this.game = game;
    	SimCoLife.triggerMusic(SimCoGame.formalMusic, false);
    	SimCoLife.triggerMusic(SimCoGame.winterMusic, false);
    	SimCoLife.triggerMusic(SimCoGame.summerMusic, false);
    	currPlayer = game.simcolife.getCurrPlayer();
    	switch (currPlayer.getGender()) {
		case 'M':
			avatar = new Texture(Gdx.files.internal("ending/EndingMaleImg.png"));
			break;
		case 'F':
			avatar = new Texture(Gdx.files.internal("ending/EndingFemaleImg.png"));
			break;
		default:
			avatar = new Texture(Gdx.files.internal("ending/EndingFemaleImg.png"));
			break;
		}
    	//String name, String category, String describe, int health, int relationship, int money, int score, int kimoji, int time, EventType t, int talent
    	currPlayer.setHealth(50);
    	currPlayer.setKimoji(50);
    	currPlayer.setRelationship(100);
    	currPlayer.setMoney(10000);
    	currPlayer.setTalent(100);
    	currentPage = Page.DIPLOMA;
    	setFont();
    	endingMusic.play();
		endingMusic.setLooping(true); 
    }
	
	public void setFont() {
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 52;
        fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "　肝指數夯奇摩子才藝學分零用錢按下SPAVE繼續"
        		+ "孤獨a郎同學們合夥創業，唯獨自己孤身踏入職場" + "一帆風順畢業完直接被推薦進入大公司"
        		+ "流落街頭敗光零用錢，回家啃老" + "好野人畢業後，研究投資理財，成為大富豪" + "健康新人順利得到工作，為公司獻上新鮮的肝"
        		+ "一路好走頒發畢業證書時在頒獎台上倒下您擁有一個豐富美好的大學生活，R.I.P." +"常將自身的歡樂帶給別人，\n走到哪都是人氣王"
        		+ "明日之星才藝出眾，被挖掘成明日新星" + "挖喜快摟a逮哈ma大學過於鬱悶，立志研究使他人開心的方法，最後成為相聲大師"
        		+ "普通的畢業，普通的人生" + "平凡中的不平凡沒有額外技能，但在資工領域上頗有成就";
        font = fontGenerator.generateFont(this.fontParameter);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		switch (currentPage) {
			case DIPLOMA:
				game.batch.draw(diplomaBg, 0, 0);
				game.batch.draw(avatar,400,260);
				font.setColor(Color.BLACK);
				font.draw(game.batch, "肝指數：" + "    " + String.format("%-,7d", currPlayer.getHealth()), 650, 480);
				font.draw(game.batch, "夯指數：" + "    " + String.format("%-,7d", currPlayer.getRelationship()), 650, 430);
				font.draw(game.batch, "奇摩子：" + "    " + String.format("%-,7d", currPlayer.getKimoji()), 650, 380);
				font.draw(game.batch, "才　藝：" + "    " + String.format("%-,7d", currPlayer.getTalent()), 650, 330);
				font.draw(game.batch, "學　分：" + "    " + String.format("%-,7d", currPlayer.getScore()), 650, 280);
				font.draw(game.batch, "零用錢：" + "    " + String.format("%-,7d", currPlayer.getMoney()), 650, 230);
				font.draw(game.batch, "按下SPACE繼續",920,80);
				if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
					currentPage = Page.ENDING;
				}
				break;
			case ENDING:
				game.batch.draw(endingBg, 0, 0);
				getEnding();
				font.draw(game.batch,endingDescription,300,450);
				font.draw(game.batch, "按下SPACE繼續",920,80);
				if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
					game.setScreen(new Start(game));
					game.simcolife.dispose();
					game.simcolife = new SimCoLife(game);
					this.dispose();
				}
				break;
			default:
				break;
		}
		
		
		game.batch.end();
		
	}
	private void getEnding() {
		if(currPlayer.getRelationship()==0) {
			endingDescription = "同學們合夥創業，\n唯獨自己孤身踏入職場";
		}
		else if(currPlayer.getRelationship() >= 200) {
			endingDescription = "畢業完直接被推薦進入大公司";
		}
		else if(currPlayer.getMoney() <= 0) {
			endingDescription = "敗光零用錢，回家啃老";
		}
		else if(currPlayer.getMoney() >= 400000) {
					endingDescription = "研究投資理財，成為大富豪";
		}
		else if(currPlayer.getHealth() < 30) {
			endingDescription = "順利得到工作，為公司獻上新鮮的肝";
		}
		else if(currPlayer.getHealth() > 85) {
			endingDescription = "頒發畢業證書時在頒獎台上倒下\n您擁有一個豐富美好的大學生活，R.I.P.";
		}
		else if(currPlayer.getTalent() < 90){
			endingDescription = "沒有額外技能，但在資工領域上頗有成就";
		}
		else if(currPlayer.getTalent() >= 400) {
			endingDescription = "才藝出眾，被挖掘成明日新星";
		}
		else if(currPlayer.getKimoji() >= 85){
			endingDescription = "常將自身的歡樂帶給別人，\n走到哪都是人氣王";
		}
		else if(currPlayer.getKimoji() < 15) {
			endingDescription = "大學過於鬱悶，\n立志研究使他人開心的方法，\n最後成為相聲大師";
		}
		else {
			endingDescription = "普通的畢業，普通的人生";
		}
	}
	@Override
	public void show() {
		
	}
	
	@Override
	public void dispose() {
		diplomaBg.dispose();
		endingBg.dispose();
		avatar.dispose();
		endingMusic.dispose();
		super.dispose();
	}
	
	@Override
	public void hide() {
		super.hide();
		Gdx.input.setInputProcessor(null);
		this.dispose();
	}
}
