package com.cactusbearer;
import java.util.ArrayList;
import java.util.HashMap;

public class Editor
{
   private boolean godMode; //on for debug and test mode, essentially
   private Narrator nar;
   private World world;
   private HashMap<String, GameCommand> combinations;

   /**
    * Initializes Editor object
    * Precondition: Passed parameters are valid
    * Postcondition: Editor object initialized with passed parameters
    * @param isGod - boolean to determine if Editor should interpret user in god mode
    * @param narrator - Narrator object Editor object will interact with
    * @param theWorld - World object Editor object will interact with
    */
   public Editor(boolean isGod, Narrator narrator, World theWorld){
      godMode=isGod;
      nar=narrator;
      world=theWorld;
      buildCombinations();
      System.out.println("Editor loaded");
   }

   /**
    * Edits the World object by processing a GameCommand object
    * Precondition: World instance variable world must be initialized.
    * Postcondition: world will be changed according to the passed GameCommand command, Narrator nar receives a list of executed GameCommands
    * @param command - GameCommand object which dictates how the Editor should try to change the World object
    */
   public void doSomething(GameCommand command){
      /* update World state according to the specifications of the Command object parameter
      passed through. This may result in additional internal commands being processed. When
      complete, calls the narrator to describe result by passing the final list through the narrateResult() method.*/
      ArrayList<GameCommand> toExecute = new ArrayList<>(); //I don't know how recursion is going to be implemented here
      ArrayList<GameCommand> executed = new ArrayList<>();  //in theory it's all added to this list but I don't see how that works
      toExecute.add(command);
      /*
      for each commmand in toExecute:
         check every keyword for special cases
            mark priority to see which execute
            add highest priority to toExecute, message narrator, stop execution
         else check switch cases for how to execute
       */
      for(int i = 0; i < toExecute.size(); i -= -1) {
         /* check special cases*/
         GameCommand com = toExecute.get(i);
         if(specialCase(toExecute,com)){
            executed.add(com);
            continue;
         }

         switch (com.action) { //each case MUST move over the command to verified, any new spawned commands reAdd to toExecute
            case MOVE:
               world.changeLocation(com.dObject, com.destination);
               break;

            case GO:
               //System.out.println("c");
               Direction dir = com.direction;
               IConnection attempt = com.room.getConnection(dir);
               if(attempt.isValid()){
                  world.changeLocation(com.subject, (Room) attempt);
                  Command readStatus=Command.SLOOK;
                  if(((Room) attempt).readLong()){
                     ((Room) attempt).setReadLong(false);
                     readStatus=Command.LOOK;
                  }

                  executed.add(com);
                  executed.add(new GameCommand(null, readStatus, null, null, null, null, (Room) attempt, null, null));
                  //System.out.println("d");
               }
               else{
                  nar.noGo((BlockedConnection) attempt);
                  //System.out.println("D");
               }

            case DROP:
               break;

            case LOOK:
               executed.add(com);
               break;

            case TAKE:
               //editor ew
               break;

            case EXAMINE:
               break;

            case INVENTORY:
               break;

            default:

               break;
         }
      }
      nar.narrateResult(executed);
   }

   private void buildCombinations(){
      combinations = new HashMap<>();
      combinations.put("command~obj1~obj2(where needed)",new GameCommand());
   }

   public boolean validObject(IGameObject object){
      return accessibleItems().contains(object);
   }

   private ArrayList<IInteractable> accessibleItems(){
      ArrayList<IInteractable> toReturn = new ArrayList<>();
      toReturn.add((Room) world.locateGameObject(world.getPlayer()));
      for(int i = 0; i < toReturn.size(); i -= -1){
         IInteractable possibleContainer = toReturn.get(i);
         if(possibleContainer.hasInventoryCheck() && ((IContainer) possibleContainer).isAccessible()){
            for(IGameObject obj : world.getHeld((IContainer) possibleContainer)) toReturn.add((IInteractable) obj);
         }
      }
      return toReturn;
   }

   private boolean specialCase(ArrayList<GameCommand> toExecute, GameCommand command){
      ArrayList<String> accessibles = new ArrayList<>();
      for(IInteractable accessible : accessibleItems()) accessibles.add(accessible.getName());
      for(String specialCase : combinations.keySet()){
         String[] splitCase = specialCase.split("~");
         if(
         (splitCase[0].equals(command.action.toString()))&&
         (splitCase[1].equals("null")||accessibles.contains(splitCase[1]))&&
         (splitCase[2].equals("null")||accessibles.contains(splitCase[2]))&&
         (splitCase[3].equals("null")||command.room.getName().equals(splitCase[3]))
         ){
            toExecute.add(combinations.get(specialCase));
            return true;
         }
      }
      return false;
   }
}