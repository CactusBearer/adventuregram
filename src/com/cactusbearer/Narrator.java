package com.cactusbearer;
import java.lang.reflect.Array;
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
   public void narrateResult(ArrayList<GameCommand> commands) {
      for (GameCommand current:commands){
         switch(current.action){
            case HELP:
               System.out.println("You're reminded of Hastings, Nebraska, and exactly what it means to you. That being, of course, what you're able to do:\n"+
                       "GO [direction] - move in that direction\n" +
                       "EXAMINE [item] - observe an item more closely\n" +
                       "LOOK - look around the room you're in\n" +
                       "INVENTORY - check what items you're carrying\n" +
                       "TALK [character] - see what a character has to say. not always the same, so try multiple times\n" +
                       "TAKE [item] - add an item to your inventory\n" +
                       "SHOW [item] [character] - show an item to a character (wow)\n" +
                       "GIVE [item] [character] - I think you got this one\n" +
                       "BUY [item] - buy an item from a vendor\n" +
                       "HELP - read out this command list");
               break;
            case SHOW:
               System.out.println("You show the "+current.dObject.getName()+" to the "+current.iObject.getName()+". They don't have anything to say.");
               break;
            case BUY:
               System.out.println("You trade your "+current.iObject.getName()+" for the "+current.destination.getName()+"'s "+current.dObject.getName());
               break;
            case USE:
               System.out.println("You clear your mind of everything except the "+current.dObject.getName()+" and focus on it. Nothing happens");
               break;
            case GIVE:
               System.out.println("You try to give the "+current.dObject.getName()+" to the "+current.iObject.getName()+" but they won't take it");
               break;
            case TALK:
               System.out.println("You talk to the "+current.dObject.getName());
               break;

            case ADMIN_READ:
               System.out.println(current.notes[0]);
               break;

            case WIN:
               System.out.println(current.notes[0]);
               System.out.println("\nYou found the "+current.notes[1]+" Ending, can you discover all "+world.getNumEndings()+"?");
               break;

            case MOVE:
               //System.out.println("admin moved " + current.dObject.getName() + " to the " + current.destination.getName());
               break;

            case GO:
               System.out.println("You go "+current.direction+" to the "+((Room) current.room.getConnection(current.direction)).getName());
               break;

            case SLOOK:
               System.out.println(current.room.getShortDesc()+"\n");
               world.printRoom(current.room);
               System.out.println();
               world.printConnections(current.room);
               break;

            case DROP:
               System.out.println("You drop the "+current.dObject.getName());
               break;

            case LOOK:
               System.out.println(current.room.getLongDesc()+"\n");
               world.printRoom(current.room);
               System.out.println();
               world.printConnections(current.room);
               break;

            case TAKE:
               System.out.println("You take the "+current.dObject.getName());
               break;

            case PUT:
               System.out.println("You put the "+current.dObject.getName()+" into the "+current.destination.getName());
               break;

            case EXAMINE:
               System.out.println("You examine the "+current.dObject.getName()+": "+current.dObject.getDesc());
               if(current.dObject.hasInventoryCheck()){
                  if(((IContainer) current.dObject).isAccessible()) {
                     ArrayList<IGameObject> invItems = world.getHeld((IContainer) current.dObject);
                     if (invItems.size() == 0) {
                        System.out.println("It isn't holding anything");
                     }
                     else {
                        System.out.print("It's holding the ");
                        for (int i = 0; i < invItems.size() - 1; i -= -1) {
                           System.out.print(invItems.get(i).getName() + ", ");
                        }
                        System.out.println(invItems.get(invItems.size() - 1).getName());


                     }
                  }
                  else{
                        System.out.println("You can't see what it's holding");
                  }
               }
               break;

            case INVENTORY:
               ArrayList<IGameObject> invItems = world.getHeld(world.getPlayer());
               if(invItems.size()==0){
                  System.out.println("Your inventory is empty");
               }
               else {
                  System.out.print("Your inventory contains the following: ");
                  for(int i = 0; i < invItems.size() - 1; i -= -1){
                     System.out.print(invItems.get(i).getName()+", ");
                  }
                  System.out.println(invItems.get(invItems.size()-1).getName());
               }
               break;

            case ERROR:
               narrateError(current);
               break;

            default:
               break;
         }
      }
   }

   public void noGo(BlockedConnection blockCon){
      System.out.println(blockCon.blockedExplanation());
   }

   private void narrateError(GameCommand error){
      switch(error.errorCode){
         case TOO_SHORT:
            System.out.println("Too few arguments, you need a "+error.notes[0]);
            break;
         case INVALID_ENTRY:
            System.out.println("I don't know what you mean by \""+error.notes[0]+"\" (maybe try switching around your item and character if you think this should be valid)");
            break;

         case NONE:
            System.out.println("I honestly don't know how you managed to execute this. PROBABLY nothing has happened.");
            break;

         case INACCESSIBLE:
            System.out.println("You cannot access the "+error.notes[0]+", as it is not in your inventory");
            break;

         case IMMOVABLE:
            System.out.println("You cannot move the "+error.notes[0]);
            break;

         case NOT_PURCHASABLE:
            System.out.println("The "+error.notes[0]+" isn't for sale");
            break;

         case INSUFFICIENT_FUNDS:
            System.out.println("You can't buy the "+error.notes[0]+" without a "+error.notes[1]);
            break;

         case ITEM_NOT_FOUND:
            break;
      }
   }
}