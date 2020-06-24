package com.simcolife.game.littlegame;

public class Bullet {
	private float x;
	private float y;
	private int score;
	private int speed;
	private int size;
	
	public Bullet(float x,float y,int speed,int score,int size) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.score = score;
		this.size = size;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public int getSpeed() {
		return speed;
	}
	public int getScore() {
		return score;
	}
	public int getSize() {
		return size;
	}
	public double getDistance(Character character) {
		return Math.hypot(Math.abs(this.getX() - character.getX()),Math.abs(this.getY() - character.getY()));
	}
	public float getImgX() {
		return x - size/2;
	}
	public float getImgY() {
		return y - size/2;
	}
	
}
