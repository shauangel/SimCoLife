package com.simcolife.game;

import java.security.SecureRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.simcolife.game.event.Event;
import com.simcolife.game.event.EventType;

public class Block {
	
	public static final String[] category = new String[] {"BlockStartImg.png", "BlockRandomImg.png", "BlockChoiceImg.png", "BlockSpaceImg.png", "BlockHospitalImg.png", "BlockTwaImg.png", "BlockPetImg.png"};
	public static final EventType[] typeList = {EventType.START, EventType.RANDOM, EventType.CHOICE, EventType.BLANK, EventType.HOSPITAL, EventType.TWA, EventType.PET};
	private EventType currType;
	private Texture texture;
	private Event e;
	private int x;
	private int y;
	private int choice = -1;
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setTexture(EventType target) {
		int count = 0;
		for(EventType i:typeList) {
			if(i.equals(target)) {
				this.texture = new Texture(Gdx.files.internal(category[count]));
				this.currType = typeList[count];
				break;
			}
			count++;
		}
		
	}
	
	public void setInfo(EventType t) {
		setEvent(t);
		setTexture(t);
	}
	
	public void setEvent(EventType t) {
		this.e = new Event(t);
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public Event getEvent() {
		return this.e;
	}
	
	@Override
	public String toString() {
		return "x: " + getX() + " y: " + getY();
	}
	
	public static void setBlocks(EventType[] blockList) {
		SecureRandom randomNumbers = new SecureRandom();
		for(int i=0; i<36; i++) {
			if(i < 17) {
				blockList[i] = EventType.RANDOM;
			}
			else if(i>=17 && i <20) {
				blockList[i] = EventType.PET;
			}
			else if(i >= 20 && i < 30) {
				blockList[i] = EventType.CHOICE;
			}
			else {
				blockList[i] = EventType.BLANK;
			}
		}
		
		for (int first = 0; first < blockList.length; first++) {
			
			int second = randomNumbers.nextInt(blockList.length-1);

			EventType temp = blockList[first];
			blockList[first] = blockList[second];
			blockList[second] = temp;
		}
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public EventType getCurrType() {
		return currType;
	}

	public void setCurrType(EventType currType) {
		this.currType = currType;
	}
	
	
}