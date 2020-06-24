package com.simcolife.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Calender {
	
	public enum ImportantEvent{
		MIDTERM, FINAL, WINTER, SUMMER, NONE
	}

	//--------------------------Texture of calendar---------------------------------
	public static final Texture CALENDER_BG = new Texture(Gdx.files.internal("calender/CalendarBGImg.png"));
	
	//--------------------------location of numbers------------------------------
	public static final int LOCATION01 = 146;
	public static final int LOCATION02 = 255;
	public static final int LOCATION03 = 303;
	public static final int LOCATION04 = 413;
	public static final int LOCATION05 = 460;
	
	//-------------------------other variable-------------------------
	public static int MAX_TIME = 1440;
	public static final int YEAR = 360;
	public static final ImportantEvent[] BIG_EVENT_LIST = {ImportantEvent.MIDTERM, ImportantEvent.FINAL, ImportantEvent.WINTER, ImportantEvent.MIDTERM, ImportantEvent.FINAL, ImportantEvent.SUMMER};
	
	//-------------------------changing text--------------------------
	public static Texture n_1;
	public static Texture n_2;
	public static Texture n_3;
	public static Texture n_4;
	public static Texture n_5;
	
	
//convert days to calendar numbers
	public static void timeConvert(int days) {
		int d = days%30;
		int m = (days/30)%12;
		int y = (days/30)/12;
		n_1 = new Texture(Gdx.files.internal("calender/Calendar" + String.format("%d", y) + "Img.png"));
		n_2 = new Texture(Gdx.files.internal("calender/Calendar" + String.format("%d", m/10) + "Img.png"));
		n_3 = new Texture(Gdx.files.internal("calender/Calendar" + String.format("%d", m%10) + "Img.png"));
		n_4 = new Texture(Gdx.files.internal("calender/Calendar" + String.format("%d", d/10) + "Img.png"));
		n_5 = new Texture(Gdx.files.internal("calender/Calendar" + String.format("%d", d%10) + "Img.png"));
		
	}
	
	public static ImportantEvent changeSeason(int days, boolean examTime, boolean vacation) {
		int pastDays = 360 - days%360;
		//during vacation >> depends when the vacation start or over
		if(vacation) {
			if(pastDays > 300) {
				return ImportantEvent.SUMMER;
			}
			else if(pastDays > 165) {
				return ImportantEvent.NONE;
			}
			else if(pastDays > 135){
				return ImportantEvent.WINTER;
			}
			else {
				return ImportantEvent.NONE;
			}
		}
		//during weekdays >> depends when to start the exam
		else if(pastDays > 300 && pastDays < 331) {
			return ImportantEvent.FINAL;
		}
		else if(pastDays > 232 && pastDays < 264) {
			return ImportantEvent.MIDTERM;
		}
		else if(pastDays > 135 && pastDays < 167) {
			return ImportantEvent.FINAL;
		}
		else if(pastDays > 67 && pastDays < 98) {
			return ImportantEvent.MIDTERM;
		}
		else {
			return ImportantEvent.NONE;
		}
	}
}
