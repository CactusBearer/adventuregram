package com.cactusbearer;

import java.util.HashMap;

public class Room implements IContainer, IConnection, IInteractable {
	private String longDesc; //printed out when in room for first time
	private String shortDesc; //when reentering room
	private boolean readLong; //whether to show the long description
	//private [some dictionary] doorLists; //set of references to door objects, associated to directions say by Direction enum
	private String name;
	private HashMap<Direction,IConnection> connections;

	/**
	 * Initializes new Room object
	 * Precondition: valid parameters passed
	 * Postcondition: Room object initialized with passed parameters and boolean readLong defaulted to true
	 * @param theName - String passed to name of Room object
	 * @param longDescription - String passed to longDesc of Room object
	 * @param shortDescription - String passed to shortDesc of Room object
	 */
	Room(String theName, String longDescription, String shortDescription/*, [some dictionary] dictOfDoors*/) {
		name = theName;
		longDesc = longDescription;
		shortDesc = shortDescription;
		readLong = true;
		connections = new HashMap<>();
		//doorLists=dictOfDoors;
	}

	/**
	 * returns the value of instance variable longDesc
	 * Precondition: none
	 * Postcondition: returns value of String longDesc
	 * @return longDesc - the longDesc of Room object
	 */
	public String getLongDesc() {
		return longDesc;
	}

	/**
	 * returns the value of instance variable shortDesc
	 * Precondition: none
	 * Postcondition: returns value of String shortDesc
	 * @return shortDesc - the shortDesc of Room object
	 */
	public String getShortDesc() {
		return shortDesc;
	}

	/**
	 * Sets the value of instance variable readLong to false
	 * Precondition: none
	 * Postcondition: sets value of readLong to false
	 */
	public void setReadLong(boolean val) { //used after walking to a room, so it doesn't keep reading you the long description
		readLong = val;
	}

	public boolean readLong(){
		return readLong;
	}
	/**
	 * returns the value of instance variable name
	 * Precondition: none
	 * Postcondition: returns value of String name
	 * @return name - the name of Room object
	 */
	public String getName() {
		return name;
	}

	/**
	 * UNFINISHED - returns the Door object which is in the direction passed through
	 * Precondition: valid Direction passed through, not Direction.NONE
	 * Postcondition: returns Door in passed direction
	 * @return door - the Door object in the requested direction from the room
	 */
  /*public Door getDoor(Direction direction){
   	//returns the door object in the requested direction
	}*/
	public IConnection getConnection(Direction dir){
		return connections.get(dir);
	}

	public void setConnection(Direction dir, IConnection con){
		connections.put(dir,con);
	}

	public int getCapacity(){
		return 99;
	}

	public boolean isValid(){
		return true;
	}

	public String blockedExplanation(){
		return "this never should appear. something has gone terribly wrong and it's a miracle this program hasn't crashed";
	}

	public boolean isAccessible(){
		return true;
	}

	public boolean inContext(){
		return true;
	}

	public boolean hasInventoryCheck(){
		return true;
	}
}