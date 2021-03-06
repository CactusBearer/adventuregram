package com.cactusbearer;
import java.util.*;
import java.util.function.Function;

public class World
{
	private HashMap<String,Room> rooms;//associates unique ID of rooms to object reference
    private HashMap<String, IConnection> connections;
	private HashMap<String,Item> items;//^^^
	private HashMap<String,GameCharacter> characters;//^^^
    private HashMap<String,IInteractable> interactables;
	private int turnNumber;
    private int numEndings;
    private GameCharacter player;
    private boolean gameActive;
	private HashMap<IGameObject,IContainer> locationMap;//maps item to the first holder in which it is found; if more general containment is desirable, lookup can be repeated until the room is achieved.


   /**
    * Initializes new World object
    * Precondition: none
    * Postcondition: World object initialized with blank maps for game object mapping
    */
   public World(){
      rooms=new HashMap<>(); 
      characters=new HashMap<>();
      items=new HashMap<>();
      interactables = new HashMap<>();
      turnNumber=0; //CHANGE MAYBE
      locationMap=new HashMap<>();
      connections = new HashMap<>();
      gameActive = true;
      numEndings = 4;
      System.out.println("World loaded"); //just for debugging purposes
   }

   //methods
   public int getNumEndings(){
      return numEndings;
   }

   public GameCharacter getPlayer(){
      return player;
   }

   public boolean gameActive(){
      return gameActive;
   }

   public void setGameActive(boolean gA){
      gameActive=gA;
   }

   public void setPlayer(GameCharacter player){
      this.player=player;
   }
   /**
    * Registers the passed Room to the room directory under the passed name
    * Precondition: roomName does not already exist as a key in the rooms HashMap
    * Postcondition: room is added to rooms HashMap with key roomName
    * @param room - Room reference stored as value in rooms HashMap
    * @param roomName - String name used as key to find room in rooms HashMap
    */
   public void addRoom(Room room, String roomName){
	   /*registers the room to the rooms dictionary at the included roomID, done upon initialization of room. 
      All game object IDs are known expected values, these addBlank commands are just setting them as such in
      the dictionaries.*/
      rooms.put(roomName,room);
      interactables.put(roomName,room);
      connections.put(roomName,room);
   }

   /**
    * Registers the passed Item to the item and location directories
    * Precondition: itemName does not already exist as a key in the items HashMap, and item does not already exist as a key in the locationMap HashMap
    * Postcondition: item is added to items HashMap with key itemName, and holder is added to locationMap HashMap with key item
    * @param item - Item reference stored as value in items Hashmap, and used as key to find holder in locationMap HashMap
    * @param itemName - String used as key to find item in items HashMap
    * @param holderName - holding Object reference stored as a value in the locationMap HashMap
    */
   public void addItem(Item item, String itemName, String holderName){
      /*registers the item to the rooms dictionary at the included itemID and records the location of the item
      in the locationMap with the holder. Done upon initialization of item*/
      items.put(itemName,item);
      interactables.put(itemName,item);
      locationMap.put(item,(IContainer) interactables.get(holderName));
   }

   /**
    * Registers the passed GameCharacter to the character and location directories
    * Precondition: charName does not already exist as a key in the characters HashMap, and gchar does not already exist as a key in the locationMap HashMap
    * Postcondition: gchar is added to characters HashMap with key charName, and room is added to locationMap HashMap with key gchar
    * @param gchar - GameCharacter reference stored as value in characters HashMap, and used as a key to find room in locationMap HashMap
    * @param charName - String used as key to find gchar in characters HashMap
    * @param roomName - Room reference stored as a value in the locationMap HashMap
    */
   public void addChar(GameCharacter gchar, String charName, String roomName){
      /*registers the character to the characters dictionary at the included charID, done upon
      initialization of the character.*/
      characters.put(charName,gchar);
      interactables.put(charName,gchar);
      locationMap.put(gchar,(Room) interactables.get(roomName));
   }

   /**
    * Returns an ArrayList of Objects held by the passed Object holder
    * Precondition: none
    * Postcondition: return ArrayList of Objects which are keys in the locationMap HashMap to passed holder value
    * @param holder - Object reference which is the value in the locationMap HashMap for which all keys are found
    * @return keyList - the ArrayList of all keys which have value holder in the locationMap HashMap
    */
   public ArrayList<IGameObject> getHeld(IContainer holder){
      ArrayList<IGameObject> retVal=new ArrayList<>(); //make ArrayList of retrieved values
      Set<IGameObject> allKeys=locationMap.keySet(); //make set of all keys in locationMap
      for(IGameObject key:allKeys){ //iterate through all keys in locationMap
         if(holder==locationMap.get(key)){ //if the passed holder is indeed holding the iterated key set
            retVal.add(key); //add the key which was true to the retrieved value ArrayList
         }
      }
      return retVal;
   }

   /**
    * returns the Object containing the passed Object containedName
    * Precondition: none
    * Postcondition: return Object which is the value in the locationMap HashMap for key containedName
    * @param containedName - Object reference which is the key in the locationMap HashMap
    * @return container - Object which is the value in the locationMap HashMap for the key containedName
    */
   public IContainer locateGameObject(IGameObject containedName){
	   /*refers to locationMap and returns the game object of the first holder of the item object*/
      return locationMap.get(containedName);
   }


   /**
    * updates the locationMap to asspcoate a new value, newHolder, to an already existing key, object
    * Precondition: object already exists as a key in the locationMap HashMap
    * Postcondition: returns true if newHolder is registered to the locationMap HashMap as the value to the key object, returns false if error processing
    * @param object - Object reference which is the key in the locationMap HashMap
    * @param newHolder - Object reference which is the value for the passed key in the locationMap HashMap
    * @return success - boolean value describing if the method executed as expected
    */
   public boolean changeLocation(IGameObject object, IContainer newHolder){
	   /*updates the locationMap to associate the object of objectID to the newContainerID. Returns true on success.*/
      /*only works item -> room rn*/
      //probably should check to see if already exists
      locationMap.put(object,newHolder);
      /*if proper location check*/ return true;
   }

   /**
    * returns the Room identified by its name
    * Precondition: the passed String exists as a key in the rooms HashMap
    * Postcondition: returns Room which is the value in rooms HashMap for key room
    * @param room - String which is the name of the Room for which it is a key in the rooms HashMap
    * @return Room - Room which is the value in the rooms HashMap for the key room
    */
   public Room getRoom(String room){
	   /*returns a reference to the room object identified by roomID*/
      return rooms.get(room);
   }

   public IGameObject getGameObject(String gObjectName){
      IGameObject reference=null;
      if(items.containsKey(gObjectName)) reference=items.get(gObjectName);
      else if(characters.containsKey(gObjectName)) reference=characters.get(gObjectName);
      return reference;
   }

   public IContainer getContainer(String containerName){
      IContainer reference=null;
      if(items.containsKey(containerName)) reference=null;//items.get(containerName);
      else if(characters.containsKey(containerName)) reference = characters.get(containerName);
      else if(rooms.containsKey(containerName)) reference = rooms.get(containerName);
      return reference;
   }

   public IInteractable getInteractable(String itsName){
      return interactables.get(itsName);
   }

   /**
    * returns the Item identified by its name
    * Precondition: the passed String exists as a key in the items HashMap
    * Postcondition: returns Item which is the value in items HashMap for key item
    * @param item - String which is the name of the Item for which it is a key in the items HashMap
    * @return Item - Item which is the value in the items HashMap for the key item
    */
   public Item getItem(String item){
	   /*returns a reference to the item object identified by itemID*/
      return items.get(item);
   }

   /**
    * returns the GameCharacter identified by its name
    * Precondition: the passed String exists as a key for the characters HashMap
    * Postcondition: returns GameCharacter which is the value in characters HashMap for key gchar
    * @param gchar - String which is the name of the GameCharacter for which it is a key in the characters HashMap
    * @return GameCharacter - GameCharacter which is the value in the characters HashMap for the key gchar
    */
   public GameCharacter getChar(String gchar){
	   /*returns a reference to the character object identified by charID*/
      return characters.get(gchar);
   }

   public IConnection getCon(String conchar){
      return connections.get(conchar);
   }

   public void addCon(IConnection con, String conName){
      connections.put(conName,con);
   }

   /**
    * prints out the name of a room and the game Objects it contains
    * Precondition: none
    * Postcondition: name value of passed Room object printed and the name values of the objects contained by that room are also printed
    * @param room - Room reference for which the name and names of contained objects are printed
    */
   public void printRoom(Room room) {
      //System.out.printf("room: %s\n", room.getName());
      ArrayList<IGameObject> items = new ArrayList<>();
      ArrayList<IGameObject> chars = new ArrayList<>();
      ArrayList<IGameObject> containers = new ArrayList<>();
      for(IGameObject thing:getHeld(room)){
         if(thing.inContext()){
            if(thing.getType()==GameObjectType.ITEM_CONTAINER) containers.add(thing);
            else if(thing.getType()==GameObjectType.GAME_CHARACTER) chars.add(thing);
            else if(thing.getType()==GameObjectType.ITEM) items.add(thing);
         }
      }
      //System.out.print("items in " +room.getName() + ": ");
      //System.out.println();

      String iNoneCheck = "";
      String cNoneCheck = "";
      String kNoneCheck = "";
      if(items.size()==0) iNoneCheck = "none";
      if(chars.size()==0) cNoneCheck = "none";
      if(containers.size()==0) kNoneCheck = "none";

      System.out.printf("items in %s: %s%s\n", room.getName(),
              String.join(", ", items.stream().map(IGameObject::getName).toList()), iNoneCheck); //Christoph is scaring me*/
      System.out.printf("characters in %s: %s%s\n", room.getName(),
              String.join(", ", chars.stream().map(IGameObject::getName).toList()), cNoneCheck);
      System.out.printf("containers in %s: %s%s\n", room.getName(),
              String.join(", ", containers.stream().map(IGameObject::getName).toList()), kNoneCheck);
   }

   public void printConnections(Room room) {
      System.out.println("From here you can go:");
      if(room.getConnection(Direction.NORTH).inContext()) System.out.println("NORTH to the "+((Room) room.getConnection(Direction.NORTH)).getName());
      if(room.getConnection(Direction.SOUTH).inContext()) System.out.println("SOUTH to the "+((Room) room.getConnection(Direction.SOUTH)).getName());
      if(room.getConnection(Direction.EAST).inContext()) System.out.println("EAST to the "+((Room) room.getConnection(Direction.EAST)).getName());
      if(room.getConnection(Direction.WEST).inContext()) System.out.println("WEST to the "+((Room) room.getConnection(Direction.WEST)).getName());
      if(room.getConnection(Direction.UP).inContext()) System.out.println("UP to the "+((Room) room.getConnection(Direction.UP)).getName());
      if(room.getConnection(Direction.DOWN).inContext()) System.out.println("DOWN to the "+((Room) room.getConnection(Direction.DOWN)).getName());
   }
   /**
    * prints out the names and names of contained Objects for all Rooms
    * Precondition: all Rooms registered in the rooms HashMap
    * Postcondition: name values for all Rooms and name values for all objects contained within those rooms are printed such that all rooms can be seen with what objects they contain
    */
   public void printAllRooms(){ //useful for testing and debugging!
      /*rooms.values().forEach(this::printRoom);*/ //he's scaring me again

      for(Room room:rooms.values()) printRoom(room);
      System.out.println();
   }
   
}