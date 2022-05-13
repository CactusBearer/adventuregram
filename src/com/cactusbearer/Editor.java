package com.cactusbearer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Editor
{
   private boolean godMode; //on for debug and test mode, essentially
   private Narrator nar;
   private World world;
   private HashMap<String, GameCommand[]> combinations;

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
            continue;
         }

         switch (com.action) { //each case MUST move over the command to verified, any new spawned commands reAdd to toExecute
            case HELP:
               executed.add(com);
               break;
            case SET_CONTEXT:
               com.room.setContext(Boolean.parseBoolean(com.notes[0]));
               break;
            case SHOW:
               executed.add(com);
               break;
            case ADVANCE_DIALOGUE:
               com.subject.setDialogueSet(Integer.parseInt(com.notes[0]));
               break;
            case BUY:
               world.changeLocation(com.dObject, com.subject);
               world.changeLocation(com.iObject, com.destination);
               executed.add(com);
               break;
            case USE:
               executed.add(com);
               break;
            case CHANGE_CONNECTION:
               com.room.setConnection(com.direction,world.getCon(com.notes[0]));
               break;
            case GIVE:
               executed.add(com);
               break;
            case TALK:
               executed.add(com);
               executed.add(new GameCommand("\""+((GameCharacter) com.dObject).getDialogue()+"\""));
               break;

            case ADMIN_READ:
               executed.add(com);
               break;

            case WIN:
               executed.add(com);
               world.setGameActive(false);
               break;

            case MOVE:
               /*System.out.print(com.subject);
               System.out.print(com.dObject);
               System.out.println( com.destination);*/
               world.changeLocation(com.dObject, com.destination);
               executed.add(com);
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
               break;

            case DROP:
               world.changeLocation(com.dObject,com.destination);
               executed.add(com);
               break;

            case LOOK:
               executed.add(com);
               break;

            case TAKE:
               //editor ew
               world.changeLocation(com.dObject,world.getPlayer());
               executed.add(com);
               break;

            case PUT:
               world.changeLocation(com.dObject,com.destination);
               executed.add(com);
               break;

            case EXAMINE:
               executed.add(com);
               break;

            case INVENTORY:
               executed.add(com);
               break;

            case ERROR:
               executed.add(com);
               break;

            default:
               System.out.println("sadness. nothing happened from this command");
               break;

         }
         if (com.action == Command.WIN) break;
      }
      nar.narrateResult(executed);
   }

   private void buildCombinations(){
      combinations = new HashMap<>();
      combinations.put("command~obj1~obj2(where needed)",new GameCommand[]{new GameCommand()});
      combinations.put("GO~null~finalChoiceFlag~secretWatchpoint~DOWN~END", new GameCommand[]{
               new GameCommand("You hop down to the crypt, and the shudder from your landing surprises the cultists, who all turn to you\n" +
                       "\"Quickly now! GIVE me the AMULET so I can destroy it! We can't let anyone USE it!\" the smith cries as he wrestles free from the cultists.\n" +
                       "Unfortunately, he's only free because they're advancing on you!"),
               new GameCommand(null, Command.MOVE, world.getPlayer(), world.getRoom("crypt"), null, null, null, null, null),
               new GameCommand(world.getChar("smith"), Command.ADVANCE_DIALOGUE, null, null, null, null, null, null, new String[]{"4"})
      });
      combinations.put("LOOK~null~null~crypt~null~END", new GameCommand[]{
               new GameCommand("Just as you suspected, there are in fact cultists here with you and the smith, and they're approaching you rapidly")
      });
      combinations.put("SHOW~null~null~crypt~null~END", new GameCommand[]{
              new GameCommand("Nobody seems to be in the mood for show-and-tell today")
      });
      combinations.put("GIVE~amulet~smith~crypt~null~END", new GameCommand[]{
               new GameCommand("As the cultists close in on you, you throw the amulet above their heads right to the smith."),
               new GameCommand(null, Command.WIN, null, null, null, null, null, null, new String[]{"He catches it in one and, and with the other pulls out a silver stake from his robes and pierces the amulet with all his might.\n" +
                       "A goo black as tar flows out the puncture, and the amulet crumbles to dust in his hand. The cultists cry out in anguish, and you and the smith round up the cultists who haven't fled\n" +
                       "You pull the hood off their leader. The mayor?? This is going to be an interesting trial","Destroy the Cult"})
      });
      combinations.put("USE~amulet~null~crypt~null~END", new GameCommand[]{
               new GameCommand(null, Command.WIN, null, null, null, null, null, null, new String[]{"and then YOU turn evil oopsie you unleashed a dark evil unto this world","Evil Unleashed"})
      });
      combinations.put("GO~null~finalChoiceFlag~secretWatchpoint~EAST~END", new GameCommand[]{
               new GameCommand(null, Command.WIN, null, null, null, null, null, null, new String[]{"You slip through the crumbling town wall and tumble down into some farmland, finally escaping this accursed town.\n" +
                      "This amulet is \"dark magic\" and \"of unknowable power?\" Sounds like a perfect reward for a night trapped in a town.\ngo You'll have to show it to your contacts when you get back to the city, figure out how much it's really worth","Opportunistic Deserter"})
      });
      combinations.put("TALK~smith~talkAlleyFirstFlag~null~null~h", new GameCommand[]{
               new GameCommand("the smith puts on the robes and glides over to the east wall. Did... did he just go through it? You walk over, and there's a narrow passage, obscured by shadow, presumably to the crypt"),
               new GameCommand(null,Command.CHANGE_CONNECTION,null,null,null,Direction.EAST,world.getRoom("alley"),null,new String[]{"secretWatchpoint"}),
               new GameCommand(null,Command.CHANGE_CONNECTION,null,null,null,Direction.EAST,world.getRoom("crypt"),null,new String[]{"secretWatchpoint"}),
               new GameCommand(null,Command.MOVE,world.getItem("talkAlleyFirstFlag"),world.getRoom("storage"),null,null,null,null,null),
               new GameCommand(null,Command.MOVE,world.getItem("passageOpenFlag"),world.getRoom("alley"),null,null,null,null,null)
      });
      combinations.put("GO~null~null~startArea~WEST~END", new GameCommand[]{
               new GameCommand(world.getPlayer(), Command.WIN, null, null, null, null, null, Error.NONE, new String[]{"Yeah perhaps on second thought, an accursed town isn't what you need right now. Maybe you'll go visit the hot springs instead", "Sane Traveller"})
               });
      combinations.put("GO~null~null~temple~NORTH~END", new GameCommand[]{
               new GameCommand("The priest yells over at you \"Crypt is OFF-LIMITS.\" You think it best to heed your instruction."),
               new GameCommand(null,Command.SET_CONTEXT,null,null,null,null, world.getRoom("crypt"),null,new String[]{"false"} )
      });
      combinations.put("GO~null~passageOpenFlag~alley~EAST~END", new GameCommand[]{
               new GameCommand("You shuffle through the dank narrow passage, and feel relieved you didn't sample the barkeep's cakes\n" +
                     "You settle into a squat hollow overlooking the crypt. This must be where the smith would watch cultist activity. Say... where is the smith?\n" +
                     "A gathering of cultists glides into view, unless there's a costume party you weren't invited to. All you can really do at this point is LOOK and see what happens"),
               new GameCommand(null, Command.MOVE, world.getPlayer(), world.getRoom("secretWatchpoint"),null,null,null,null,null)
      });
      combinations.put("LOOK~null~lookCryptFlag~secretWatchpoint~null~END", new GameCommand[]{
               new GameCommand("The cultists are arguing already, when suddenly they forcible unhood the most argumentative of the bunch, it's the smith!\n" +
                       "\"Argh! You fools! I don't have the amulet you're looking for, no man should wield that dark magic!\"\n" +
                       "\"Ah, but you fail to dream of just what we could do, how we could tap its unknowable power\" chimes the cultist adorned most intricately\n" +
                       "The smith looks up where you're hidden from view. \"You've all COME DOWN with a madness from the power to seek!\""),
               new GameCommand(null, Command.MOVE, world.getItem("lookCryptFlag"),world.getRoom("storage"),null,null,null,null,null),
               new GameCommand(null, Command.MOVE, world.getItem("finalChoiceFlag"), world.getRoom("secretWatchpoint"),null,null,null,null,null),
               new GameCommand(null, Command.MOVE, world.getChar("smith"), world.getRoom("crypt"), null, null, null, null, null)
      });
      combinations.put("LOOK~null~finalChoiceFlag~secretWatchpoint~null~END", new GameCommand[]{
               new GameCommand("\"I *said* you've all COME DOWN with a madness\"\n\"We heard you! Why are you repeating yourself!\"")
      });
      combinations.put("GO~null~null~startArea~EAST~h", new GameCommand[]{
               new GameCommand("You hear a loud clang and see they've closed the gate behind you. Guess you're in town for the night.")
               });
      combinations.put("GO~null~firstTimeInArmoryFlag~blacksmith~DOWN~h", new GameCommand[]{
               new GameCommand("\"Here, take this.\" The smith throws an amulet at you, and you just barely react in time to catch it and add it to your INVENTORY."),
               new GameCommand(null, Command.MOVE, world.getItem("amulet"), world.getPlayer(),null,null,null,null,null),
               new GameCommand(null, Command.MOVE, world.getItem("firstTimeInArmoryFlag"), world.getRoom("storage"),null,null,null,null,null)
               });
      combinations.put("TAKE~beverage~barkeep~null~null~END", new GameCommand[]{
              new GameCommand("You see the mace the barkeep keeps behind the counter and you think twice about stealing the beverage")
      });
      combinations.put("TAKE~robes~notDrunkPotionmasterFlag~null~null~END", new GameCommand[]{
               new GameCommand("\"HEY!\" The potionmaster hurries over to you and snatches the robes out of your hands and puts them away.\n\"These are my... special robes! not for customers!\"")
               });
      combinations.put("TAKE~robes~drunkPotionmasterFlag~null~null~h", new GameCommand[]{
               new GameCommand("The potionmaster haphazardly turns towards you, and starts to object, \"eh.. jusst return them okayy?\" before returning to their drink\nYou think it wise to keep these stored away from view until you get back to the smith"),
               new GameCommand(null, Command.MOVE, world.getItem("drunkPotionmasterFlag"), world.getRoom("storage"),null,null,null,null,null),
               new GameCommand(world.getChar("potionmaster"), Command.ADVANCE_DIALOGUE, null, null, null, null, null, null, new String[]{"2"})
               });
      combinations.put("SHOW~notes~smith~blacksmith~null~END", new GameCommand[]{
               new GameCommand("The smith's face softens and smiles when as you bring the notes from your pockets. \"Ah! So I see you've made yourself familiar with our cause.\"\nHe opens a hatch and motions for you to follow him DOWN"),
               new GameCommand(null, Command.CHANGE_CONNECTION,null,null,null,Direction.DOWN,world.getRoom("blacksmith"),null,new String[]{"armory"}),
               new GameCommand(null, Command.MOVE, world.getChar("smith"),world.getRoom("armory"),null,null,null,null,null),
               new GameCommand(world.getChar("smith"), Command.ADVANCE_DIALOGUE, null, null, null, null, null, null, new String[]{"1"}),
               new GameCommand(null, Command.MOVE, world.getItem("firstTimeInArmoryFlag"), world.getRoom("blacksmith"),null,null,null,null,null)
               });
      combinations.put("GIVE~notes~smith~blacksmith~null~END", new GameCommand[]{
              new GameCommand("The smith's face softens and smiles when as you bring the notes from your pockets. \"Ah! So I see you've made yourself familiar with our cause.\"\nHe opens a hatch and motions for you to follow him DOWN"),
              new GameCommand(null, Command.CHANGE_CONNECTION,null,null,null,Direction.DOWN,world.getRoom("blacksmith"),null,new String[]{"armory"}),
              new GameCommand(null, Command.MOVE, world.getChar("smith"),world.getRoom("armory"),null,null,null,null,null),
              new GameCommand(world.getChar("smith"), Command.ADVANCE_DIALOGUE, null, null, null, null, null, null, new String[]{"1"}),
              new GameCommand(null, Command.MOVE, world.getItem("firstTimeInArmoryFlag"), world.getRoom("blacksmith"),null,null,null,null,null)
      });
      combinations.put("SHOW~amulet~mayor~null~null~END", new GameCommand[]{
               new GameCommand("\"oh those are some strange symbols, maybe it's occult. The barkeep might know more about that\"")
               });
      combinations.put("SHOW~amulet~potionmaster~null~null~END", new GameCommand[]{
              new GameCommand("the potionmaster's eyes widen as you pull out the amulet to show him, but doesn't say anything")
      });
      combinations.put("SHOW~robes~barkeep~null~null~END", new GameCommand[]{
              new GameCommand("the barkeep hurriedly closes your bag as you open it to show him the robes. \"You can't just go around showing those off! Be more careful!\"")
              });
      combinations.put("SHOW~robes~smith~armory~null~END", new GameCommand[]{
              new GameCommand("The smith whistles. \"Yeah, those are the real thing alright, GIVE them over and we'll get started\""),
              new GameCommand(world.getChar("smith"), Command.ADVANCE_DIALOGUE, null, null, null, null, null, null, new String[]{"2"}),
      });
      combinations.put("GIVE~robes~smith~armory~null~END", new GameCommand[]{
              new GameCommand(null, Command.MOVE, world.getItem("robes"), world.getChar("smith"), null, null, null, null, null),
              new GameCommand("He takes the robes, and folds them under his arm\n\"Meet me in the alley, I've good reason to believe they're meeting in the crypt, but the priest isn't letting anyone through, but I have a plan, don't worry\"\nHe grabs some things from the wall and exits"),
              new GameCommand(null, Command.MOVE,world.getChar("smith"),world.getRoom("alley"),null,null,null,null,null),
              new GameCommand(null, Command.MOVE,world.getItem("talkAlleyFirstFlag"),world.getRoom("alley"),null,null,null,null,null),
              new GameCommand(world.getChar("smith"), Command.ADVANCE_DIALOGUE, null, null, null, null, null, null, new String[]{"3"}),
      });
      combinations.put("SHOW~amulet~barkeep~null~null~END", new GameCommand[]{
              new GameCommand("\"working with the smith now, are you? Well, all I know is that you'll need some robes if you want to get anywhere close to the cult\""),
              new GameCommand(world.getChar("barkeep"), Command.ADVANCE_DIALOGUE,null,null,null,null,null,null,new String[]{"1"})
      });
      combinations.put("GIVE~beverage~potionmaster~null~null~END", new GameCommand[]{
              new GameCommand("\"Oh! is this for me? I do love barkeep's concoctions. Now if you'll excuse me...\"\nThe beverage is snatched out of your hand, and the potionmaster is thoroughly distracted with your gift"),
              new GameCommand(null, Command.MOVE, world.getItem("beverage"), world.getChar("potionmaster"),null,null,null,null,null),
              new GameCommand(null, Command.MOVE, world.getItem("drunkPotionmasterFlag"), world.getRoom("alchemist"),null,null,null,null,null),
              new GameCommand(null, Command.MOVE, world.getItem("notDrunkPotionmasterFlag"), world.getRoom("storage"),null,null,null,null,null),
              new GameCommand(world.getChar("potionmaster"), Command.ADVANCE_DIALOGUE,null,null,null,null,null,null,new String[]{"1"})
      });
   }

   public boolean validObject(IInteractable object){
      return accessibleItems().contains(object);
   }

   private ArrayList<IInteractable> accessibleItems(){
      ArrayList<IInteractable> toReturn = new ArrayList<>();
      toReturn.add((Room) world.locateGameObject(world.getPlayer()));
      //System.out.println(toReturn);
      for(int i = 0; i < toReturn.size(); i -= -1){
         IInteractable possibleContainer = toReturn.get(i);
         //System.out.println(possibleContainer.getName());
         if(possibleContainer.hasInventoryCheck() && ((IContainer) possibleContainer).isAccessible()){
            for(IGameObject obj : world.getHeld((IContainer) possibleContainer)) toReturn.add((IInteractable) obj);
         }
      }
      //for(IInteractable thing : toReturn) System.out.print(thing.getName()+" ");
      return toReturn;
   }

   private boolean specialCase(ArrayList<GameCommand> toExecute, GameCommand command){
      /* returns true if it should STOP execution */
      ArrayList<String> accessibles = new ArrayList<>();
      for(IInteractable accessible : accessibleItems()) accessibles.add(accessible.getName());
      for(String specialCase : combinations.keySet()){
         //System.out.println(specialCase);
         String[] splitCase = specialCase.split("~");
         if(
         (splitCase[0].equals(command.action.toString()))&&
         (splitCase[1].equals("null")||command.dObject.getName().equals(splitCase[1]))&&
         (splitCase[2].equals("null")||accessibles.contains(splitCase[2]))&&
         (splitCase[3].equals("null")||command.room.getName().equals(splitCase[3]))&&
         (splitCase[4].equals("null")||command.direction.toString().equals(splitCase[4]))
         ){
            toExecute.addAll(Arrays.asList(combinations.get(specialCase)));
            return splitCase[5].equals("END");
         }
      }
      return false;
   }

}