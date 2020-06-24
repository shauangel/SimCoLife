package com.simcolife.save;

import java.util.ArrayList;
import java.util.Date;
import com.simcolife.game.Block;
import com.simcolife.game.Pet;
import com.simcolife.game.Pet.PetName;
import com.simcolife.game.Player;
import com.simcolife.game.SimCoLife;
import com.simcolife.game.event.EventType;
import com.simcolife.tools.Calender.ImportantEvent;

public class ArchiveFormat {
	
	//-----------------------Player Info--------------------------
	private ImportantEvent currentSeason;
	private boolean examTime;
	private boolean vacationTime;
	private int time;
	private char gender;
	private int health;
	private int relationship;
	private int money;
	private int talent;
	private int score;
	private int kimoji;
	private PetName pet;
	//----------------------map Info------------------------
	private ArrayList<BlockInfo> route = new ArrayList<BlockInfo>();
	private int playerNow;
	//-----------------other Info-------------------
	private String saveTime;
	

	
	public ArchiveFormat(ImportantEvent currentSeason, boolean examTime, boolean vacationTime, int time, char gender,
			int health, int relationship, int money, int talent, int score, int kimoji, PetName pet,
			ArrayList<BlockInfo> route, String saveTime, int playerNow) {
		this.currentSeason = currentSeason;
		this.examTime = examTime;
		this.vacationTime = vacationTime;
		this.time = time;
		this.gender = gender;
		this.health = health;
		this.relationship = relationship;
		this.money = money;
		this.talent = talent;
		this.score = score;
		this.kimoji = kimoji;
		this.pet = pet;
		this.route = route;
		this.saveTime = saveTime;
		this.playerNow = playerNow;
	}

	public ArchiveFormat(Player player, ArrayList<Block> route, int playerNow) {
		this.currentSeason = player.getCurrentSeason();
		this.examTime = player.isExamTime();
		this.vacationTime = player.isVacationTime();
		this.time = player.getTime();
		this.gender = player.getGender();
		this.health = player.getHealth();
		this.relationship = player.getRelationship();
		this.money = player.getMoney();
		this.talent = player.getTalent();
		this.score = player.getScore();
		this.kimoji = player.getKimoji();
		this.pet = player.getMyPet().getSelected();
		for(Block i : route) {
			this.route.add(new BlockInfo(i.getCurrType(), i.getChoice()));
		}
		this.saveTime = RecordImporter.sdf.format(new Date());
		this.playerNow = playerNow;
	}
	
	public Player createPlayer() {
		Player player = new Player(currentSeason, examTime, vacationTime, time, gender, health, relationship, money, talent, score, kimoji);
		player.setMyPet(new Pet(pet));
		return player;
	}
	
	public ArrayList<Block> createRoute() {
		ArrayList<Block> list = new ArrayList<Block>();
		SimCoLife.initRoute(list);
		for(int i=0; i<list.size(); i++) {
			list.get(i).setChoice(route.get(i).getChoice());
			list.get(i).setInfo(route.get(i).getType());
		}
		SimCoLife.playerNow = this.playerNow;
		return list;
	}
	
	public static class BlockInfo{
		private EventType type;
		private int choice;
		
		public BlockInfo(EventType type, int choice) {
			this.type = type;
			this.choice = choice;
		}

		public EventType getType() {
			return type;
		}

		public void setType(EventType type) {
			this.type = type;
		}

		public int getChoice() {
			return choice;
		}

		public void setChoice(int choice) {
			this.choice = choice;
		}
		
		
	}
	

	public ImportantEvent getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(ImportantEvent currentSeason) {
		this.currentSeason = currentSeason;
	}

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

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getRelationship() {
		return relationship;
	}

	public void setRelationship(int relationship) {
		this.relationship = relationship;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getTalent() {
		return talent;
	}

	public void setTalent(int talent) {
		this.talent = talent;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getKimoji() {
		return kimoji;
	}

	public void setKimoji(int kimoji) {
		this.kimoji = kimoji;
	}

	public PetName getPet() {
		return pet;
	}

	public void setPet(PetName pet) {
		this.pet = pet;
	}

	public ArrayList<BlockInfo> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<BlockInfo> route) {
		this.route = route;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public int getPlayerNow() {
		return playerNow;
	}

	public void setPlayerNow(int playerNow) {
		this.playerNow = playerNow;
	}
	
	
	

}
