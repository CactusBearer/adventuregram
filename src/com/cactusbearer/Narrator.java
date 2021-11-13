package com.cactusbearer;
import java.util.ArrayList;

public class Narrator
{
   private long chatId; // chat id’s may be more than 32 bits, but less than 52, so it fits best in a long 
   private World world;


	public Narrator(long chat, World theWorld){
      chatId=chat;
      world=theWorld;
   }

   
   public void narrateResult(ArrayList<GameCommand> commands){
      GameCommand current=null;
      for(int i=0;i<commands.size();i-=-1){
         current=commands.get(i);
         if(current.action==Enums.command.MOVE){
            System.out.print("\n"+current.subject+" moved "+current.dObject+" to the "+current.iObject);
         }
      }
   }

}