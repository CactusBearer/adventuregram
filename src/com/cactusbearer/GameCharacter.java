package com.cactusbearer;

import java.util.ArrayList;

public class GameCharacter implements IGameObject, IContainer
{
   	private int level;
   	private String name;
	private String desc;
	//private [some dictionary] actionResponse; //associates action attempted on character with game command to be passed back to Editor.
	//private Inventory inv;  
	private boolean player;
   	private boolean alive;
   private boolean context;
   private boolean accessInventory;
   private boolean attackable;
   private String[][] dialogue;
   private int dialogueCounter;
   private int dialogueSet;



	public GameCharacter(){
		name="h";
	}
	/**
	 * Initializes a GameCharacter object
	 * Precondition: valid parameters passed
	 * Postcondition: GameCharacter object initialized with passed parameters, and boolean alive defaulted to true
	 * @param levelValue - int passed to level of GameCharacter object
	 * @param theName - String passed to name of GameCharacter object
	 * @param description - String passed to desc of GameCharacter object
	 * @param playable - boolean passed to determine if GameCharacter object is a player
	 */
	public GameCharacter(int levelValue, String theName, String description, /*[some dictionary] customResponse, Inventory inventory, */boolean playable, boolean attack, String[][] allDialogue){
		level=levelValue;
      	name=theName;
      	desc=description;
      	/*actionResponse=customResponse; //dictionary of some sort to map input commands to special reaction commands
      	inv=inventory;*/ //not coded yet
      	player=playable;
      	alive=true;
	    accessInventory = false;
		context=true;
		attackable=attack;
		dialogue=allDialogue;
		dialogueCounter=0;
		dialogueSet = 0;
   	}

	/**
	 * returns the value of instance variable level
	 * Precondition: none
	 * Postcondition: returns value of int level
	 * @return level - the level of the GameCharacter object
	 */
	public int getLevel(){
      return level;
   	}

	/**
	 * UNFINISHED - will help to dictate what special GameCommands get executed as a result to certain GameCommands interacting with this GameCharacter object
	 * Precondition: none
	 * Postcondition: returns array of GameCommand objects generated
	 * @param commandAction - the GameCommand to check what resultant GameCharacter action should be
	 * @return reactions - the array of GameCommand objects to be executed
	 */
	public GameCommand[] takeSpecialAction(GameCommand commandAction){
	  	/*returns a list of special actions as found in actionResponse[]*/
      	return null;
   	}

	/**
	 * returns the value of instance variable name
	 * Precondition: none
	 * Postcondition: returns value of String name
	 * @return name - the name of the GameCharacter object
	 */
	public String getName(){
   		return name;
   	}

	/**
	 * returns the value of instance variable desc
	 * Precondition: none
	 * Postcondition: returns value of String desc
	 * @return desc - the desc of the GameCharacter object
	 */
	public String getDesc(){
   		return desc;
   	}

	/**
	 * returns the value of instance variable inv
	 * Precondition: none
	 * Postcondition: returns Inventory inv
	 * @return inv - the Inventory inv of GameCharacter object
	 */
	/*public Inventory getInv(){
   		returns the inventory object of the character
   	}*/

	/**
	 * returns whether the GameCharacter is a player
	 * Precondition: none
	 * Postcondition: returns value of instance variable player
	 * @return player - a boolean instance variable of GameCharacter object
	 */
	public boolean isPlayer(){
	   	return player;
   	}

	/**
	 * returns whether the GameCharacter is alive
	 * Precondition: none
	 * Postcondition: returns value of instance variable player
	 * @return alive - the boolean instance variable of GameCharacter object
	 */
	public boolean isAlive(){
      	return alive;
   	}

   	public GameObjectType getType(){
		return GameObjectType.GAME_CHARACTER;
	}

	public int getCapacity(){
		return 5;
	}

	public boolean isAccessible(){
		return accessInventory;
	}

	public void setAccessInventory(boolean access){
		accessInventory=access;
	}

	public boolean hasInventoryCheck(){
		return true;
	}

	public boolean inContext(){
		return context;
	}

	public void setContext(boolean inContext){
		context=inContext;
	}

	public boolean portable(){
		return false;
	}

	public void setDialogueSet(int set){
		dialogueSet = set;
		dialogueCounter = 0;
	}

	public String getDialogue(){
		String toReturn = dialogue[dialogueSet][dialogueCounter];
		if(dialogueCounter<dialogue[dialogueSet].length-1) dialogueCounter-=-1;
		return toReturn;
	}
}