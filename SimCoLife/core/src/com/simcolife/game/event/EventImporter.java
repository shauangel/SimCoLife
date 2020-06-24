package com.simcolife.game.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

//----read data for events----
public class EventImporter {
	
	public static ArrayList<Event> randomList = new ArrayList<Event>();
	public static ArrayList<Event> choiceList = new ArrayList<Event>();
	public static ArrayList<Event> winterList = new ArrayList<Event>();
	public static ArrayList<Event> summerList = new ArrayList<Event>();
	public static ArrayList<Event> twaList = new ArrayList<Event>();
	public static ArrayList<Event> petList = new ArrayList<Event>();
	
	public static final String RANDOM_EVENT = "random.csv";
	public static final String CHOICE_EVENT = "choose.csv";
	public static final String TWA_EVENT = "twa.csv";
	public static final String PET_EVENT = "pet.csv";
	public static final String WINTER_EVENT = "winter.csv";
	public static final String SUMMER_EVENT = "summer.csv";
	

	public void readCSV(String fileName) throws FileNotFoundException {
		int count = 0;
		try {
			File file = new File(fileName);
			InputStreamReader in = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader br = new BufferedReader(in);
			while(br.ready()) {
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line, ",");
				if(count > 0) {
					String name = st.nextToken();
					String category = st.nextToken();
					String type = st.nextToken();
					int health = Integer.parseInt(st.nextToken());
					int relationship = Integer.parseInt(st.nextToken());
					int money = Integer.parseInt(st.nextToken());
					int talent = Integer.parseInt(st.nextToken());
					int score = Integer.parseInt(st.nextToken());
					int kimoji = Integer.parseInt(st.nextToken());
					int time = Integer.parseInt(st.nextToken());
					String describe = st.nextToken();
					Event temp = new Event(name, category, describe, health, relationship, money, score, kimoji, time, Enum.valueOf(EventType.class, type), talent);
					addEventList(temp);
				}
				else {
					count++;
				}
			}
			br.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addEventList(Event e) {
		switch(e.getType()) {
			case RANDOM:
				randomList.add(e);
				break;
			case CHOICE:
				choiceList.add(e);
				break;
			case WINTER:
				winterList.add(e);
				break;
			case SUMMER:
				summerList.add(e);
				break;
			case TWA:
				twaList.add(e);
				break;
			case PET:
				petList.add(e);
				break;
			default:
				break;
		}
	}
	
	
}
