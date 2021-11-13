package com.cactusbearer;
public class Item
{
   private int attackValue;
	private String name;
	private boolean weapon;
	private boolean food;
	private boolean treasure;
	//private [some dictionary] customResponse; //the Editor will query the items for this particular table, and see if the action it’s about to do regarding this item triggers a special effect (additional game commands).


   public Item(int attack, String theName, boolean isWeapon, boolean isFood, boolean isTreasure/*, [some dictionary] customInteractions*/)
   {
     attackValue=attack;
     name=theName;
     weapon=isWeapon;
     food=isFood;
     treasure=isTreasure;
     //customResponse=customInteractions;
   }

   
   public int getAttack(){
   	return attackValue;
   }
   
	public String getName(){
   	return name;
   }
      
	public boolean isWeapon(){
   	return weapon;
   }
      
	public boolean isFood(){
   	return food;
	}
   
   public boolean isTreasure(){
   	return treasure;
   }
	/*public Command[] takeSpecialAction(int commandAction)
   	returns a list of game commands determined by what the customResponse dictionary instance variable dictates
   */
}