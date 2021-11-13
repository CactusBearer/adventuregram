package com.cactusbearer;
import java.util.ArrayList;

public class Editor
{
   private boolean godMode;
   private Narrator nar;
   private World world;
   
   
	public Editor(boolean isGod, Narrator narrator, World theWorld){
     godMode=isGod;
     nar=narrator;
     world=theWorld;
   }
   
   
   public void doSomething(GameCommand command){
	   /*update World state according to the specifications of the Command object parameter
      passed through. This may result in additional internal commands being processed. When
      complete, calls the narrator to describe result by passing the final list through the narrateResult() method.*/
      ArrayList<GameCommand> executed=new ArrayList<GameCommand>(); /*I don't know how recursion is going to be implemented here,
                                                   in theory it's all added to this list but I don't see how that works*/
      executed.add(command);
      if(command.action==Enums.command.MOVE){//check if move
         /*check what's being moved, check where it's being moved, find references, change*/
         Item item=world.getItem(command.dObject);
         Room room=world.getRoom(command.iObject);
         world.changeLocation(item,room);
      }
      
      nar.narrateResult(executed);
   }
}