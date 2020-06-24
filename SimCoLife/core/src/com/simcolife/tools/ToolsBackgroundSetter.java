package com.simcolife.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.simcolife.tools.Calender.ImportantEvent;

public class ToolsBackgroundSetter {

	public static Texture toolsBG;
	public static final Texture WINTER = new Texture(Gdx.files.internal("tools/ToolsWinterBGImg.png"));
	public static final Texture SUMMER = new Texture(Gdx.files.internal("tools/ToolsSummerBGImg.png"));
	public static final Texture FORMAL = new Texture(Gdx.files.internal("tools/ToolsBGImg.png"));
	
	public static void setBG(ImportantEvent i) {
		
		switch(i) {
			case WINTER:
				toolsBG = ToolsBackgroundSetter.WINTER;
				break;
			case SUMMER:
				toolsBG = ToolsBackgroundSetter.SUMMER;
				break;
			case NONE:
				toolsBG = ToolsBackgroundSetter.FORMAL;
				break;
			default:
				toolsBG = ToolsBackgroundSetter.FORMAL;
				break;
		}
	}
	
}
