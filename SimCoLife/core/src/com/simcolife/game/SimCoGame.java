package com.simcolife.game;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.simcolife.game.event.EventImporter;
import com.simcolife.save.RecordImporter;
import com.simcolife.tools.Settings;

public class SimCoGame extends Game {
	public RecordImporter records;
	public Skin skin;
	public AssetManager manager = new AssetManager();
	public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
   
//--------------------screens--------------------
    public SimCoLife simcolife;
    public Settings setting;
    public ArrayList<Player> playerList = new ArrayList<Player>();
	
//--------------------music----------------------
    public static Music formalMusic;
    public static Music summerMusic;
    public static Music winterMusic;
    
    
    @Override
    public void create () {
    	skin = new Skin(Gdx.files.internal("style/comic-ui.json"));
    	records = new RecordImporter();
    	records.findRecordFile();
//----------------------Music-------------------------
    	formalMusic = Gdx.audio.newMusic(Gdx.files.internal("music/FormalMusic.mp3"));
    	formalMusic.setLooping(true);
    	summerMusic = Gdx.audio.newMusic(Gdx.files.internal("music/SummerMusic.mp3"));
    	summerMusic.setLooping(true);
    	winterMusic = Gdx.audio.newMusic(Gdx.files.internal("music/WinterMusic.mp3"));
    	winterMusic.setLooping(true);
//-------------------create screens----------------------------
    	simcolife = new SimCoLife(this);
    	setting = new Settings(this);
        batch = new SpriteBatch();
        loadInfo();
        setScreen(new Start(this));
    }
	
    
    
    @Override
    public void render() {
		super.render();
    }
    
    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        simcolife.dispose();
        summerMusic.dispose();
        formalMusic.dispose();
        winterMusic.dispose();
    }



	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void addPlayerList(Player n) {
		this.playerList.add(n);
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}
    
    public void loadInfo() {
    	EventImporter test = new EventImporter();
		try {
			test.readCSV(EventImporter.RANDOM_EVENT);
			test.readCSV(EventImporter.CHOICE_EVENT);
			test.readCSV(EventImporter.PET_EVENT);
			test.readCSV(EventImporter.SUMMER_EVENT);
			test.readCSV(EventImporter.TWA_EVENT);
			test.readCSV(EventImporter.WINTER_EVENT);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
	
	

}
