package com.simcolife.game.littlegame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

//玩家、敵人的abstract class
public class Character {
	private int hp;
	private float x;
	private float y;
	private int speed;
	private int size;
	public Array<Bullet> bullets;

	public Character(float x ,float y,int hp,int speed,int size) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.speed = speed;
		this.size = size;
		bullets = new Array<Bullet>();
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getHp() {
		return hp;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return this.size;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getSpeed() {
		return speed;
	}
	public float getImgX() {
		return x - size/2;
	}
	public float getImgY() {
		return y - size/2;
	}
	public boolean hitBy(Bullet bullet) {
		return bullet.getDistance(this) <= this.size/2 + bullet.getSize()/2;
	}
	public boolean isShooting() {
		return !(this.bullets.isEmpty());
	}

	public void bulletsFly(Character other,boolean goUp) {
	    for (Iterator<Bullet> iter = bullets.iterator(); iter.hasNext(); ) {
	    	Bullet bullet = iter.next();
	    	if(goUp) {
		    	bullet.setY( bullet.getY() + bullet.getSpeed() * Gdx.graphics.getDeltaTime());
	    	}
	    	else {
		    	bullet.setY( bullet.getY() - bullet.getSpeed() * Gdx.graphics.getDeltaTime());	
	    	}
	    	if(bullet.getY() + bullet.getSize() < 0 && goUp == false) iter.remove();
	    	else if(bullet.getY() + bullet.getSize() > 1300 && goUp == true) iter.remove();
	    	if(other.hitBy(bullet)) {
	    		other.setHp(other.getHp()-bullet.getScore());
	    		iter.remove();
          }
	    }
	}
}
