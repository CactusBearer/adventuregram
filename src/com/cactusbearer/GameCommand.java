package com.cactusbearer;
public class GameCommand
{
   	public GameCharacter subject;
	public Command action; //maybe not INT as I’m told enums are a thing that give a way to store data with fixed set of values
	public IGameObject dObject;
	public IContainer destination;
	public IGameObject iObject;
	public Direction direction; //also likely enum, haha no longer exist
	public Room room;
	public Error errorCode;
	public String[] notes;
	//public enum status;//SUCCESS/FAIL/REQUESTED
   	//all public? wack
   	//strings are names of things. no longer exist haha

	/**
	 * Initializes a GameCommand object
	 * Precondition: valid parameters passed
	 * Postcondition: GameCommand object initialized with passed parameters
	 * @param subjectRef - String passed to subject of GameCommand object
	 * @param theAction - Command enum passed to action of GameCommand object
	 * @param dObjectRef - String passed to dObject of GameCommand object
	 * @param iObjectRef - String passed to iObject of GameCommand object
	 * 	 */
	public GameCommand(GameCharacter subjectRef, Command theAction, IGameObject dObjectRef, IContainer destinationRef, IGameObject iObjectRef, Direction dir, Room roomRef, Error error, String[] strings){
      	subject =subjectRef;
      	action =theAction;
      	dObject= dObjectRef;
      	iObject= iObjectRef;
	  	destination	=	destinationRef;
	    direction = dir;
		room = roomRef;
		errorCode = error;
		notes = strings;
   	}

   public GameCommand(String toRead){
		subject = null;
		action = Command.ADMIN_READ;
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
		room = null;
		errorCode = null;
		notes = new String[]{toRead};
   }
   public GameCommand(){
		subject = null;
		action = Command.ERROR;
		dObject = null;
		destination = null;
		iObject = null;
		direction = null;
		room = null;
		errorCode = null;
		notes = null;

   }
}