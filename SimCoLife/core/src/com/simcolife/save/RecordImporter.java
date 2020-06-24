package com.simcolife.save;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.simcolife.game.Block;
import com.simcolife.game.Player;
import com.simcolife.save.ArchiveFormat.BlockInfo;
import com.simcolife.game.Pet.PetName;
import com.simcolife.game.event.EventType;
import com.simcolife.tools.Calender.ImportantEvent;

//匯入資料
public class RecordImporter {
	public static ArchiveFormat[] archiveList = new ArchiveFormat[5];
//---------------------file handle---------------------------
	public static final String FILENAME = "RecordData.json";
	public static Json json = new Json();
	public static JsonReader jsonReader = new JsonReader();
	public static FileHandle fileHandle = Gdx.files.local(FILENAME);
	public static SimpleDateFormat sdf;
	
	public RecordImporter() {
		initialList();
		sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	}
	
	//load in all saved data
	public void findRecordFile() {
		if(fileHandle.exists()) {
			String input = fileHandle.readString();
			JsonValue currRecord = jsonReader.parse(input);
			//import data
			for(int i=0; i<5; i++) {
				createRecord(currRecord.get(i), i);
			}
		}
		//create new file
		else {
			try {
				fileHandle.file().createNewFile();
				String test = json.toJson(archiveList);
				fileHandle.writeString(test, false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//reset archiveList
	public void initialList() {
		for(int i=0; i<5; i++) {
			archiveList[i] = null;
		}
	}

	//convert JSON string into object 
	public void createRecord(JsonValue input, int ind) {
		if(!input.isNull()) {
			ImportantEvent currentSeason = Enum.valueOf(ImportantEvent.class, input.getString("currentSeason"));
			boolean examTime = Boolean.valueOf(input.getString("examTime"));
			boolean vacationTime = Boolean.valueOf(input.getString("vacationTime"));
			int time = Integer.parseInt(input.getString("time"));
			char gender = input.getString("gender").charAt(0);
			int health = Integer.parseInt(input.getString("health"));
			int relationship = Integer.parseInt(input.getString("relationship"));
			int money = Integer.parseInt(input.getString("money"));
			int talent = Integer.parseInt(input.getString("talent"));
			int score = Integer.parseInt(input.getString("score"));
			int kimoji = Integer.parseInt(input.getString("kimoji"));
			int playerNow = Integer.parseInt(input.getString("playerNow"));
			String saveTime = input.getString("saveTime");
			PetName pet = Enum.valueOf(PetName.class, input.getString("pet"));
			ArrayList<BlockInfo> route = new ArrayList<BlockInfo>();
			JsonValue routeJSON = input.get("route");
			for(JsonValue i : routeJSON) {
				EventType type = Enum.valueOf(EventType.class, i.getString("type"));
				int choice = Integer.parseInt(i.getString("choice"));
				route.add(new ArchiveFormat.BlockInfo(type, choice));
			}
			ArchiveFormat create = new ArchiveFormat(currentSeason, examTime, vacationTime, time, gender, health, relationship, money, talent, score, kimoji, pet, route, saveTime, playerNow);
			archiveList[ind] = create;
		}
		else {
			archiveList[ind] = null;
		}
	}

	//write new data into JSON file
	public static void saveRecord(int ind, Player player, ArrayList<Block> route, int playerNow) {
		String allList;
		if(player == null && route== null) {
			archiveList[ind] = null;
		}
		else {
			ArchiveFormat create = new ArchiveFormat(player, route, playerNow);
			archiveList[ind] = create;
		}
		allList = json.toJson(archiveList);
		fileHandle.writeString(allList, false);
	}

	//read old data to game
	public static ArchiveFormat loadRecord(int ind) {
		return archiveList[ind];
	}

}
