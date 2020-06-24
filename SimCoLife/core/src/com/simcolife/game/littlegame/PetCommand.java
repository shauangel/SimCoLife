package com.simcolife.game.littlegame;

import java.security.SecureRandom;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class PetCommand {
	public static enum Type{
		FOOD,TOY,BATH;
	}
	private static Array<PetCommand> commands;
	private static final int SIZE = 150;
	private Type commandType;
	private float x;
	private float y;
	private int speed;
	private static SecureRandom random = new SecureRandom();
	
	public PetCommand(Type ctype,float x,float y,int speed) {
		commandType = ctype;
		this.x = x;
		this.y = y;
		this.speed = speed;
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
	
	public int getSize() {
		return SIZE;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getSpeed() {
		return speed;
	}
	public float getImgX() {
		return x - SIZE/2;
	}
	public float getImgY() {
		return y - SIZE/2;
	}
	public void setType(Type type) {
		commandType = type;
	}
	public Type getType() {
		return commandType;
	}
	public static void setCommandsArray(Array<PetCommand> commandArray) {
		commands = commandArray;
	}
	public static Array<PetCommand> getCommandsArray(){
		return commands;
	}
	public static Long autoCommand(Long lastCommandTime) {
		Type type = Type.BATH;
		int r = random.nextInt(3);
		switch (r) {
		case 0:
			type = Type.FOOD;
			break;
		case 1:
			type = Type.BATH;
			break;
		case 2:
			type = Type.TOY;
			break;
		default:
			break;
		}
		Long now = TimeUtils.nanoTime();
		if(TimeUtils.timeSinceNanos(lastCommandTime) > 450000000) {
	    	PetCommand newCommand = new PetCommand(type,1300,575,500);
	    	commands.add(newCommand);
	        return now;			
		}
		else
			return lastCommandTime;

	}
}
