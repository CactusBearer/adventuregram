package com.cactusbearer;

public class Item implements IGameObject, IInteractable {
	protected int attackValue;
	protected String name;
	protected boolean weapon;
	protected boolean food;
	protected boolean treasure;
	//private [some dictionary] customResponse; //the Editor will query the items for this particular table, and see if the action it’s about to do regarding this item triggers a special effect (additional game commands).

	/**
	 * Initializes new Item object
	 * Precondition: Valid parameters passed
	 * Postcondition: Item object initialized with passed parameters
	 * @param attack - int passed to attackValue of Item object
	 * @param theName - String passed to name of Item object
	 * @param isWeapon - boolean to determine if Item object is weapon
	 * @param isFood - boolean to determine if Item object is food
	 * @param isTreasure - boolean to determine if Item object is food
	 */
	public Item(int attack, String theName, boolean isWeapon, boolean isFood, boolean isTreasure/*, [some dictionary] customInteractions*/) {
		attackValue = attack;
		name = theName;
		weapon = isWeapon;
		food = isFood;
		treasure = isTreasure;
		//customResponse=customInteractions;
	}

	/**
	 * returns the value of instance variable attackValue
	 * Precondition: none
	 * Postcondition: returns value of int attackValue
	 * @return attackValue - the attackValue of the Item object
	 */
	public int getAttack() {
		return attackValue;
	}

	/**
	 * returns the value of instance variable name
	 * Precondition: none
	 * Postcondititon: returns value of String name
	 * @return name - the name of the Item object
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns whether the Item object is a weapon
	 * Precondition: none
	 * Postcondition: returns value of instance variable weapon
	 * @return weapon - a boolean instance variable of Item object
	 */
	public boolean isWeapon() {
		return weapon;
	}

	/**
	 * returns whether the Item object is food
	 * Precondition: none
	 * Postcondition: returns value of instance variable food
	 * @return food - a boolean instance variable of Item object
	 */
	public boolean isFood() {
		return food;
	}

	/**
	 * returns whether the Item object is treasure
	 * Precondition: none
	 * Postcondition: returns value of instance variable treasure
	 * @return treasure - a boolean instance variable of Item object
	 */
	public boolean isTreasure() {
		return treasure;
	}

	/**
	 * UNFINISHED - will help to dictate what special GameCommands get executed as a result to certain GameCommands interacting with this Item object
	 * Precondition: none
	 * Postcondition: returns array of GameCommand objects generated
	 * @param commandAction - the GameCommand to check what resultant Item reactions should be
	 * @return reactions - the array of GameCommand objects to be executed
	 */
	public GameCommand[] takeSpecialAction(GameCommand commandAction){
   		/*returns a list of game commands determined by what the customResponse dictionary instance variable dictates*/
		return null;
   	}

   	public GameObjectType getType(){
		return GameObjectType.ITEM;
	}

	public boolean inContext(){
		return true;
	}

	public boolean hasInventoryCheck(){
		return false;
	}

}