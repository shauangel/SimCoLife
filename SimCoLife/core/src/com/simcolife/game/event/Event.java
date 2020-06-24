package com.simcolife.game.event;


public class Event {

	private EventType type;
	private String name;
	private EventType category;
	private String describe;
	private int health;
	private int talent;
	private int relationship;
	private int money;
	private int score;
	private int kimoji;
	private int time;
	
	public Event(EventType t) {
		this.type = t;
	}
	
	public Event(String name, String category, String describe, int health, int relationship, int money, int score, int kimoji, int time, EventType t, int talent) {
		this.name = name;
		setCategory(category);
		this.describe = describe;
		this.health = health;
		this.relationship = relationship;
		this.money = money;
		this.score = score;
		this.kimoji = kimoji;
		this.time = time;
		this.type = t;
		this.setTalent(talent);
		
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EventType getCategory() {
		return category;
	}
	public void setCategory(String category) {
		if(category.equals("good")) {
			this.category = EventType.GOOD;
		}
		else if(category.equals("normal")) {
			this.category = EventType.SOSO;
		}
		else {
			this.category = EventType.BAD;
		}
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
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
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}






	public EventType getType() {
		return type;
	}



	public void setType(EventType type) {
		this.type = type;
	}
	public int getTalent() {
		return talent;
	}
	public void setTalent(int talent) {
		this.talent = talent;
	}
	
	@Override
	public String toString() {
		return this.name + ": " + this.describe;
	}


	
}
