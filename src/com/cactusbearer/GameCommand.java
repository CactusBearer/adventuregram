package com.cactusbearer;
public class GameCommand
{
   public String subject;
	public Enums.command action; //maybe not INT as I’m told enums are a thing that give a way to store data with fixed set of values
	public String dObject;
	public String iObject;
	public Enums.direction direction; //also likely enum 
	//public enum status;//SUCCESS/FAIL/REQUESTED
   //all public? wack
   //strings are names of things
   //actions: 0 move
   //direction: negative no direction
   
   public GameCommand(String subjectName, Enums.command theAction, String dObjectName, String iObjectName, Enums.direction theDirection){
      subject=subjectName;
      action=theAction;
      dObject=dObjectName;
      iObject=iObjectName;
      direction=theDirection;
      //status=REQUESTED;
   }
}