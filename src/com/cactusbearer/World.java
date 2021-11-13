package com.cactusbearer;
import java.util.*;

public class World
{
	private HashMap<String,Room> rooms;//associates unique ID of rooms to object reference
	private HashMap<String,Item> items;//^^^
	private HashMap<String,GameCharacter> characters;//^^^
	private int turnNumber; 
	private HashMap<Object,Object> locationMap;//maps item to the first holder in which it is found; if more general containment is desirable, lookup can be repeated until the room is achieved.

   
   //constructor
   public World(){
      rooms=new HashMap<>(); 
      characters=new HashMap<>();
      items=new HashMap<>();
      turnNumber=0; //CHANGE MAYBE
      locationMap=new HashMap<>();
   }
   //methods
   public static void testPrint(){
      System.out.print("e\n");
   }
   public void addRoom(Room room, String roomName){
	   /*registers the room to the rooms dictionary at the included roomID, done upon initialization of room. 
      All game object IDs are known expected values, these addBlank commands are just setting them as such in
      the dictionaries.*/
      rooms.put(roomName,room);
   }
   public void addItem(Item item, String itemName, Object holder){
      /*registers the item to the rooms dictionary at the included itemID and records the location of the item
      in the locationMap with the holder. Done upon initialization of item*/
      items.put(itemName,item);
      locationMap.put(item,holder);
   }
   public void addChar(GameCharacter gchar, String charName, Room room){
      /*registers the character to the characters dictionary at the included charID, done upon
      initialization of the character.*/
      characters.put(charName,gchar);
      locationMap.put(gchar,room);
   }
   
   public ArrayList<Object> getHeld(Object holder){
      ArrayList<Object> retVal=new ArrayList();
      Set<Object> allKeys=locationMap.keySet();
      Iterator<Object> myIter=allKeys.iterator();
      while(myIter.hasNext()){
         Object tempKey=myIter.next();
         if(holder==locationMap.get(tempKey)){
            retVal.add(tempKey);
         }
      }
      return retVal;
   }
   
   public Object locateGameObject(Object containedName){
	   /*refers to locationMap and returns the game object of the first holder of the item object*/
      return locationMap.get(containedName);
   }
   public boolean changeLocation(Object object, Object newHolder){
	   /*updates the locationMap to associate the object of objectID to the newContainerID. Returns true on success.*/
      /*only works item -> room rn*/
      locationMap.put(object,newHolder);
      /*if proper location check*/ return true;
   }	
	public Room getRoom(String room){
	   /*returns a reference to the room object identified by roomID*/
      return rooms.get(room);
   }
   public Item getItem(String item){
	   /*returns a reference to the item object identified by itemID*/
      return items.get(item);
   }
	public GameCharacter getCharacter(String gchar){
	   /*returns a reference to the character object identified by charID*/
      return characters.get(gchar);
   }
   
   public void printRoom(Room room){
      System.out.print("room: "+room.getName()+"\nitems in "+room.getName()+": ");  
      Iterator<Object> myIter;
      myIter=getHeld(room).iterator();
      while(myIter.hasNext()){
         Object thing=myIter.next();
         if(thing instanceof Item) System.out.println(((Item) thing).getName());
         else if(thing instanceof GameCharacter) System.out.println(((GameCharacter) thing).getName());
      }   
   }
   
   public void printAllRooms(){
      Iterator<Room> myIter = rooms.values().iterator();
      while(myIter.hasNext()){
         printRoom(myIter.next());
      }
   }
   
}