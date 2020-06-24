package com.simcolife.game.littlegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy extends Character {
	public Enemy(float x ,float y,int hp,int speed,int size) {
		super(x, y, hp, speed,size);
	}
	
	public void autoFight(Character character) {
	    if(character.isShooting()) {
	    	if(character.getX() > getX()) {
	    		setX( (character.getX() - 310 - getX())*Gdx.graphics.getDeltaTime() +  getX());
	    	}
	    	else {
	    		setX( (character.getX() + 310 - getX())*Gdx.graphics.getDeltaTime() +  getX());
	    	}
	    	setX( (character.getX() - getX()) * Gdx.graphics.getDeltaTime() +  getX());
	    }
	    else {
	    	setX( (character.getX() - getX()) * Gdx.graphics.getDeltaTime() +  getX());
	    }
	}
	public Long autoShoot(Long lastShootTime) {
		Long now = TimeUtils.nanoTime();
		if(now - lastShootTime > 450000000) {
	    	Bullet bullet = new Bullet(getX() , 780 - getSize(),700,5,50);
	        bullets.add(bullet);
	        return now;			
		}
		else
			return lastShootTime;

	}
}
