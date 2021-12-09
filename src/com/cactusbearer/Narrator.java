package com.cactusbearer;
import java.util.ArrayList;

public class Narrator
{
   private long chatId; // chat id’s may be more than 32 bits, but less than 52, so it fits best in a long 
   private World world;

   /**
    * Initializes new Narrator object
    * Precondition: valid parameters passed
    * Postcondition: Narrator object initialized with passed parameters
    * @param chat - long passed to chatId of Narrator object
    * @param theWorld - World object Narrator will interact with
    */
   public Narrator(long chat, World theWorld){
      chatId=chat;
      world=theWorld;
      System.out.println("Narrator loaded");
   }

   /**
    * Prints out a human-readable copy of commands executed by the Editor
    * Precondition: for commands passed through, the action attribute must exist in the narrator's register (which should be always considering it's now an enum)
    * Postcondition: Each GameCommand in the ArrayList passed through will have been narrated as to have described what happened
    * @param commands - ArrayList of commands the Narrator will process and narrate results of
    */
   public void narrateResult(ArrayList<GameCommand> commands){
      GameCommand current=null;
      for(int i=0;i<commands.size();i-=-1){
         current=commands.get(i);
         if(current.action==Command.MOVE){
            System.out.println(current.subject+" moved "+current.dObject+" to the "+current.iObject);
         }
      }
   }

}