package com.simcolife.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simcolife.game.event.Event;
import com.simcolife.tools.Calender;
import com.simcolife.tools.Calender.ImportantEvent;

public class Player {
	
	public static final int MAX_HEALTH = 100;
	public static final int MAX_RELATIONSHIP = 500;
	public static final int MAX_MONEY = 1000000;
	public static final int MAX_TALENT = 500;
	public static final int MAX_SCORE = 160;
	public static final int MAX_KIMOJI = 100;
	
	public static final TextureRegionDrawable MALE = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChooseMaleImg.png"))));
	public static final TextureRegionDrawable MALE_PRESS = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChooseMalePressedImg.png"))));
	public static final TextureRegionDrawable FEMALE = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChooseFemaleImg.png"))));
	public static final TextureRegionDrawable FEMALE_PRESS = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("choose/ChooseFemalePressedImg.png"))));

	
	
//------------------------all variables-------------------------
	public int eventCounter = 0;
	private ImportantEvent currentSeason = ImportantEvent.NONE;
	private boolean examTime = false;
	private boolean vacationTime = false;
	private int time = Calender.MAX_TIME;
	private char gender = 'X';
	private int health = 0;
	private int relationship = 0;
	private int money = 3000;
	private int talent = 0;
	private int score = 0;
	private int kimoji = 60;
	private Pet myPet = null;
	
//--------------------------Textures---------------------------	
	private Texture choosed;
	private Texture avatar;
	
	

	public Player(ImportantEvent currentSeason, boolean examTime, boolean vacationTime, int time, char gender,
			int health, int relationship, int money, int talent, int score, int kimoji) {
		this.currentSeason = currentSeason;
		this.examTime = examTime;
		this.vacationTime = vacationTime;
		this.time = time;
		setGender(gender);
		this.health = health;
		this.relationship = relationship;
		this.money = money;
		this.talent = talent;
		this.score = score;
		this.kimoji = kimoji;
	}

	public Player() {
		avatar = new Texture(Gdx.files.internal("AvatarFemaleImg.png"));
		choosed = new Texture(Gdx.files.internal("CharacterFemaleImg.png"));
		Random r = new Random();
		this.talent = r.nextInt(MAX_TALENT+1);
	}
	
	public Texture getAvatar() {
		return avatar;
	}

	public void setAvatar(Texture avatar) {
		this.avatar = avatar;
	}

	public Texture getChoosed() {
		return choosed;
	}

	public void setChoosed(Texture choosed) {
		this.choosed = choosed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if(health > MAX_HEALTH) {
			this.health = MAX_HEALTH;
		}
		else if(health < 0) {
			this.health = 0;
		}
		else {
			this.health = health;
		}
		
	}

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		if(relationship > MAX_RELATIONSHIP) {
			this.relationship = MAX_RELATIONSHIP;
		}
		else {
			this.relationship = relationship;
		}
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		if(money > MAX_MONEY) {
			this.money = MAX_MONEY;
		}
		else if(this.money < 0) {
			this.money = 0;
		}
		else {
			this.money = money;
		}
	}

	public int getTalent() {
		return talent;
	}

	public void setTalent(int talent) {
		if(talent > MAX_TALENT) {
			this.talent = MAX_TALENT;
		}
		else {
			this.talent = talent;
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if(score > MAX_SCORE) {
			this.score = MAX_SCORE;
		}
		else {
			this.score = score;
		}
	}

	public int getKimoji() {
		return kimoji;
	}

	public void setKimoji(int kimoji) {
		if(kimoji > MAX_KIMOJI) {
			this.kimoji = MAX_KIMOJI;
		}
		else {
			this.kimoji = kimoji;
		}
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
		if(gender == 'F') {
			setAvatar(new Texture(Gdx.files.internal("AvatarFemaleImg.png")));
			setChoosed(new Texture(Gdx.files.internal("CharacterFemaleImg.png")));
		}
		else {
			setAvatar(new Texture(Gdx.files.internal("AvatarMaleImg.png")));
			setChoosed(new Texture(Gdx.files.internal("CharacterMaleImg.png")));
		}
	}

	public Pet getMyPet() {
		return myPet;
	}

	public void setMyPet(Pet myPet) {
		this.myPet = myPet;
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		if(time < 0) {
			this.time = 0;
		}
		else {
			this.time = time;
		}
		setCurrentSeason(Calender.changeSeason(this.time, examTime, vacationTime));
		if(Calender.BIG_EVENT_LIST[eventCounter] == getCurrentSeason()) {
			switch(currentSeason) {
				case MIDTERM:
					examTime = true;
					break;
				case FINAL:
					examTime = true;
				default:
					break;
			}
			eventCounter = (eventCounter+1)%6;
		}
		else if(!vacationTime) {
			examTime = false;
			vacationTime = false;
			currentSeason = ImportantEvent.NONE;
		}
		else if(vacationTime && currentSeason == ImportantEvent.NONE){
			examTime = false;
			vacationTime = false;
		}
		System.out.println(getCurrentSeason() + " " + isExamTime() + " " + isVacationTime());
	}
	
	public ImportantEvent getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(ImportantEvent currentSeason) {
		this.currentSeason = currentSeason;
	}
	
	public void setAllStatics(Event e) {
		setHealth(this.health += e.getHealth());
		setKimoji(this.kimoji += e.getKimoji());;
		setMoney(this.money += e.getMoney());;
		setRelationship(this.relationship += e.getRelationship());;
		setTalent(this.talent += e.getTalent());;
		setScore(this.score += e.getScore());;
		setTime(this.time -= e.getTime());;
	}


	
	//-----------------Exam, Vacation determine-----------------------
	public boolean isExamTime() {
		return examTime;
	}

	public void setExamTime(boolean examTime) {
		this.examTime = examTime;
	}

	public boolean isVacationTime() {
		return vacationTime;
	}

	public void setVacationTime(boolean vacationTime) {
		this.vacationTime = vacationTime;
	}
}
