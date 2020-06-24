package com.simcolife.game.event;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.simcolife.game.SimCoGame;
import com.simcolife.game.SimCoLife;
import com.simcolife.tools.Calender;
import com.simcolife.tools.Warning;

public class ChoiceEvent extends ScreenAdapter {
	
	public static final int CARD_X = 20;
	public static final int WORD_X = 50;
	public static final int WORD_Y = 606;
//---------------other variable-----------------
	private SimCoGame game;
	private Stage stage;
	private Stage warning;
	private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontParameter fontParameter;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
	private int choosedEvent = -1;
	private ArrayList<Event> choosingList = new ArrayList<Event>();
	private String totalWords;
	private int[] getIndex;
	
//-----------------------Img Button-------------------------
	private ImageButton[] allCards = {new ImageButton(new TextureRegionDrawable(new TextureRegion(Cards.CARD_BG)), null, new TextureRegionDrawable(new TextureRegion(Cards.CARD_BACK_BG))), new ImageButton(new TextureRegionDrawable(new TextureRegion(Cards.CARD_BG)), null, new TextureRegionDrawable(new TextureRegion(Cards.CARD_BACK_BG))), new ImageButton(new TextureRegionDrawable(new TextureRegion(Cards.CARD_BG)), null, new TextureRegionDrawable(new TextureRegion(Cards.CARD_BACK_BG))), new ImageButton(new TextureRegionDrawable(new TextureRegion(Cards.CARD_BG)), null, new TextureRegionDrawable(new TextureRegion(Cards.CARD_BACK_BG)))};
	
	
	
	public ChoiceEvent(SimCoGame game, int choice) {
		
		this.game = game;
		this.choosedEvent = choice;
		
		loadEvents();
		setStage();
		//-------------------set font------------------------
		glyphLayout = new GlyphLayout();
		this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("rttf.ttf"));
		this.fontParameter = new FreeTypeFontParameter();
        this.fontParameter.size = 52;
        this.fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + totalWords + Cards.INFO;
        font = fontGenerator.generateFont(this.fontParameter);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(Cards.BOARD, 0, 0);
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		game.batch.begin();
		font.getData().setScale(0.5f);
		if(choosingList.size() > 1) {
			glyphLayout.setText(font, "按下SPACE確定選擇並回到遊戲");
			font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 30);
			drawInfo(0, 0);
			drawInfo(1, 0);
			drawInfo(2, 0);
			drawInfo(3, 0);
		}
		else {
			glyphLayout.setText(font, "按下SPACE回到遊戲");
			font.draw(game.batch, glyphLayout, (SimCoLife.WINDOW_WIDTH-glyphLayout.width)/2, 30);
			drawInfo(0, 530-WORD_X);
		}
		game.batch.end();
		
		warning.act(Gdx.graphics.getDeltaTime());
		warning.draw();
	}
	
	public void setStage() {
		warning = new Stage();
		stage = new Stage();
		stage.addListener(new InputListener() {
			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.SPACE) {
					if(ChoiceEvent.this.choosedEvent > 0) {
						Cards.SOUND.play();
						SimCoLife.route.get(SimCoLife.playerNow).setChoice(choosedEvent);
						game.setScreen(game.simcolife);
						Timer.schedule(new Task() {
							@Override
							public void run() {game.simcolife.getCurrPlayer().setAllStatics(EventImporter.choiceList.get(choosedEvent));
								Calender.timeConvert(game.simcolife.getCurrPlayer().getTime());
								Cards.SOUND.stop();
							}
						}, 0.5f);
					}
					else {
						Gdx.input.setInputProcessor(warning);
						final Warning warn = new Warning("", game.skin, "請選擇一個事件，按下繼續");
						LabelStyle style1 = new LabelStyle(warn.getFont(), Color.RED);
						Label label1 = new Label("請選擇一個事件", style1);
						label1.setPosition((500-label1.getWidth())/2, 160);
						ImageButton yes = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogButtonYesImg.png")))), new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("DialogPressedYesImg.png")))));
						yes.setPosition((500-yes.getWidth())/2, 55);
						yes.addListener(new ClickListener() {
							@Override
							public void clicked(InputEvent event, float x, float y) {
								warn.remove();
								Gdx.input.setInputProcessor(stage);
							};
							
						});
						warn.addActor(label1);
						warn.addActor(yes);
						warn.show(warning);
					}
				}
				return super.keyDown(event, keycode);
			}
			
		});
		
		//
		if(choosingList.size() > 1) {
			
			//setting card event listener => when clicked n, except n every card turned unchecked
			allCards[0].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setAllUnchecked(0);
					ChoiceEvent.this.choosedEvent = ChoiceEvent.this.getIndex[0];
				}
			});
			allCards[1].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setAllUnchecked(1);
					ChoiceEvent.this.choosedEvent = ChoiceEvent.this.getIndex[1];
				}
			});
			allCards[2].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setAllUnchecked(2);
					ChoiceEvent.this.choosedEvent = ChoiceEvent.this.getIndex[2];
				}
			});
			allCards[3].addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					setAllUnchecked(3);
					ChoiceEvent.this.choosedEvent = ChoiceEvent.this.getIndex[3];
				}
			});
			
			//set card position, add to stage
			for(int i=0; i<4; i++) {
				allCards[i].setPosition(CARD_X+i*320, Cards.CARD_Y);
				stage.addActor(allCards[i]);
			}
		}
		else {
			allCards[0].setPosition(500, Cards.CARD_Y);
			stage.addActor(allCards[0]);
		}
		
	}
	
	//draw card information (front & back)
	private void drawInfo(int ind, int x) {
		font.setColor(Color.BLACK);
		if(!allCards[ind].isChecked()) {
			font.getData().setScale(0.5f);
			font.draw(game.batch, choosingList.get(ind).getName(), WORD_X+ind*320+x, WORD_Y);
			font.draw(game.batch, choosingList.get(ind).getDescribe(), WORD_X+ind*320+x, Cards.CARD_Y+200, 250, Align.topLeft, true);
			game.batch.draw(Cards.CHOICE, WORD_X-10+ind*320+x, Cards.CARD_Y+260);
		}
		else {
			font.getData().setScale(0.7f);
			font.draw(game.batch, "肝指數：" + String.format("%,d", choosingList.get(ind).getHealth()), WORD_X+ind*320+x, Cards.CARD_Y+500);
			font.draw(game.batch, "夯指數：" + String.format("%,d", choosingList.get(ind).getRelationship()), WORD_X+ind*320+x, Cards.CARD_Y+450);
			font.draw(game.batch, "才　藝：" + String.format("%,d", choosingList.get(ind).getTalent()), WORD_X+ind*320+x, Cards.CARD_Y+400);
			font.draw(game.batch, "奇摩子：" + String.format("%,d", choosingList.get(ind).getKimoji()), WORD_X+ind*320+x, Cards.CARD_Y+350);
			font.draw(game.batch, "零用錢：" + String.format("%,d", choosingList.get(ind).getMoney()), WORD_X+ind*320+x, Cards.CARD_Y+300);
			font.draw(game.batch, "耗費時間：" + String.format("%,d", choosingList.get(ind).getTime()) + "　天", WORD_X+ind*320+x, Cards.CARD_Y+100);
		}
		
	}
	
	//check whether the block has made a choice before
	private void loadEvents() {
		
		//if yes: get the chosen event
		if(choosedEvent > 0) {
			choosingList.add(EventImporter.choiceList.get(choosedEvent));
		}
		//if not: get four random event as option
		else {
			getIndex  = randomNum(EventImporter.choiceList.size());
			for(int i:getIndex) {
				choosingList.add(EventImporter.choiceList.get(i));
			}
		}
		for(Event i : choosingList) {
			totalWords += i.getName();
			totalWords += i.getDescribe();
		}
	}
	
	//create a list of not repeating number
	//get 4 different random numbers from 0~len-1
	private int[] randomNum(int len) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int[] eventIndex = new int[4];
		for(int i=0; i<len; i++) {
			list.add(i);
		}
		Random r = new Random();
		for(int i=0; i<4; i++) {
			int nextInd = r.nextInt(list.size());
			eventIndex[i] = list.get(nextInd);
			list.remove(nextInd);
		}
		return eventIndex;
	}
	
	//set all image button to unchecked
	public void setAllUnchecked(int ind) {
		for(ImageButton i : allCards) {
			i.setChecked(false);
		}
		allCards[ind].setChecked(true);
	}
	
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public String toString() {
		return "choice";
	}

}
