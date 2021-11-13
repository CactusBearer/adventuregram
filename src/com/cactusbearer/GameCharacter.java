package com.cactusbearer;
public class GameCharacter
{
   private int level;
   private String name;
	private String desc;
	//private [some dictionary] actionResponse; //associates action attempted on character with game command to be passed back to Editor.
	//private Inventory inv;  
	private boolean player;
   private boolean alive;
   
   
   public GameCharacter(int levelValue, String theName, String description, /*[some dictionary] customResponse, Inventory inventory, */boolean playable){
      level=levelValue;
      name=theName;
      desc=description;
      /*actionResponse=customResponse;
      inv=inventory;*/
      player=playable;
      alive=true;
   }
   
   
	public int getLevel(){
      return level;
   }
   
	public GameCommand[] takeSpecialAction(int commandAction){
	   /*returns a list of special actions as found in actionResponse[]*/
      return null;
   }
   
	public String getName(){
   	return name;
   }
   
	public String getDesc(){
   	return desc;
   }
   
	/*public Inventory getInv(){
   	returns the inventory object of the character
   }*/
   
	public boolean isPlayer(){
	   return player;
   }

   public boolean isAlive(){
      return alive;
   }
}