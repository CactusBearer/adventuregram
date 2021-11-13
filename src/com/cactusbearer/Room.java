package com.cactusbearer;
public class Room
{
	private String longDesc; //printed out when in room for first time
	private String shortDesc; //when reentering room
	private boolean readLong; //whether to show the long description
	//private [some dictionary] doorLists; //set of references to door objects, associated to directions say by index or enum
	private String name;

   
	Room(String theName, String longDescription, String shortDescription/*, [some dictionary] dictOfDoors*/)
   {
     name=theName;
     longDesc=longDescription;
     shortDesc=shortDescription;
     readLong=true;
     //doorLists=dictOfDoors;
   }


   public String getLongDesc(){
	   return longDesc;
   }
   
	public String getShortDesc(){
   	return shortDesc;
   }
   
	public void beenInRoom(){
   	readLong=false;
   }
   
	public String getName(){
      return name;
	}
   
   /*public Door getDoor(enum direction)
   	//returns the door object in the requested direction */

}