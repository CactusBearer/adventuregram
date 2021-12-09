package com.cactusbearer;
public class GameCommand
{
   	public String subject;
	public Command action; //maybe not INT as I’m told enums are a thing that give a way to store data with fixed set of values
	public String dObject;
	public String iObject;
	public Direction direction; //also likely enum
	//public enum status;//SUCCESS/FAIL/REQUESTED
   	//all public? wack
   	//strings are names of things

	/**
	 * Initializes a GameCommand object
	 * Precondition: valid parameters passed
	 * Postcondition: GameCommand object initialized with passed parameters
	 * @param subjectName - String passed to subject of GameCommand object
	 * @param theAction - Command enum passed to action of GameCommand object
	 * @param dObjectName - String passed to dObject of GameCommand object
	 * @param iObjectName - String passed to iObject of GameCommand object
	 * @param theDirection - Direction enum passed to direction of GameCommand object
	 */
	public GameCommand(String subjectName, Command theAction, String dObjectName, String iObjectName, Direction theDirection){
      	subject=subjectName;
      	action=theAction;
      	dObject=dObjectName;
      	iObject=iObjectName;
      	direction=theDirection;
      	//status=REQUESTED;
   	}
}