package com.cactusbearer;
import java.util.ArrayList;

public class Editor
{
   private boolean godMode; //on for debug and test mode, essentially
   private Narrator nar;
   private World world;

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
      System.out.println("Editor loaded");
   }

   /**
    * Edits the World object by processing a GameCommand object
    * Precondition: World instance variable world must be initialized.
    * Postcondition: world will be changed according to the passed GameCommand command, Narrator nar receives a list of executed GameCommands
    * @param command - GameCommand object which dictates how the Editor should try to change the World object
    */
   public void doSomething(GameCommand command){
      /*update World state according to the specifications of the Command object parameter
      passed through. This may result in additional internal commands being processed. When
      complete, calls the narrator to describe result by passing the final list through the narrateResult() method.*/
      ArrayList<GameCommand> executed=new ArrayList<>(); /*I don't know how recursion is going to be implemented here,
                                                   in theory it's all added to this list but I don't see how that works*/
      executed.add(command);
      if(command.action==Command.MOVE){//check if move
         /*check what's being moved, check where it's being moved, find references, change*/
         Item item=world.getItem(command.dObject);
         Room room=world.getRoom(command.iObject);
         world.changeLocation(item,room);
      }
      
      nar.narrateResult(executed);
   }
}