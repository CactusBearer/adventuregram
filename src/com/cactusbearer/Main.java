package com.cactusbearer;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("main loaded");
		try (Scanner input = new Scanner(System.in)) {

			//Initialize main program structure classes
			World world = new World();
			Narrator nar = new Narrator(123456789, world);
			Editor editor = new Editor(true, nar, world);
			Interpreter interpreter = new Interpreter(123456789, editor, world);

			resetRooms(world);
			resetChars(world);
			resetItems(world);
			GameCharacter player = new GameCharacter(1, "player", "a lost adventurer", true);
			player.setAccessInventory(true);
			world.addChar(player, player.getName(), "townEdge");
			world.setPlayer(player);
			world.getRoom("townEdge").setReadLong(false);
			interpreter.parseInput("look");

			while (!false) {
				String line = input.nextLine().strip();
				if (line.equals("EXIT")) break;
				interpreter.parseInput(line);
			}
		}
		/*
		//Initialize Rooms
		Room foyer = new Room("foyer", "a big room long desc", "a room short desc");
		world.addRoom(foyer, foyer.getName());
		Room kitchen = new Room("kitchen", null, null);
		world.addRoom(kitchen, kitchen.getName());
		Room bathroom = new Room("bathroom", null, null);
		world.addRoom(bathroom, bathroom.getName());

		//Initialize Items
		Item shovel = new Item(10, "shovel", false, false, false);
		world.addItem(shovel, shovel.getName(), foyer);
		Item knife = new Item(20, "knife", true, false, false);
		world.addItem(knife, knife.getName(), kitchen);
		Item pickaxe = new Item(10, "pickaxe", false, false, false);
		world.addItem(pickaxe, pickaxe.getName(), foyer);

		System.out.println(foyer.getShortDesc() + "\n" + shovel.getName()); //test some of the getters work

		//Initialize GameCharacters
		GameCharacter player = new GameCharacter(10, "player", "the character with the user's hand up their spine", true);
		world.addChar(player, player.getName(), kitchen);

		//check initial state
		world.printAllRooms();

		//do stuff
		GameCommand movePickaxe = new GameCommand("admin", Command.MOVE, "pickaxe", "bathroom", Direction.NONE); //world moves pickaxe to bathroom with no direction
		editor.doSomething(movePickaxe);
		GameCommand moveKnife = new GameCommand("admin", Command.MOVE, "knife", "foyer", Direction.NONE);
		editor.doSomething(moveKnife);
		interpreter.parseInput(input.nextLine());

		//check state to see changes
		world.printAllRooms();
		//*/
	}
	private static void resetChars(World world){

	}

	private static void resetItems(World world){
		ItemContainer chest = new ItemContainer("chest","a wooden container, fastened together with nails and chain",5,true);

		Item baseball = new Item(0,"baseball", "its red seams are faded, but still hold", true);
		Item coin = new Item(0,"coin", "a small gold piece, imprinted with an intricate design",true);
		Item sword = new Item(1,"sword", "a short but sturdy bronze sword. It could use polishing",true);

		world.addItem(chest, chest.getName(), "townSquare");
		world.addItem(baseball, baseball.getName(), "townEdge");
		world.addItem(coin, coin.getName(), "townSquare");
		world.addItem(sword, sword.getName(), "chest");

	}

	private static void resetRooms(World world){
		Room townEdge = new Room("townEdge","From here, you see a gate to the WEST...","a guard idly crushes a leaf beneath his boot");
		BlockedConnection gate = new BlockedConnection("The gate's shut. No way out until it's down");
		BlockedConnection sky = new BlockedConnection("You still don't have any wings, and the available ladders are about 300000ft short");
		BlockedConnection ground = new BlockedConnection("Much to your relief, the ground forbids you from passing through it");
		Room townSquare = new Room("townSquare", "The central plaza of this town, ornamented with...","man, bench, fountain");
		BlockedConnection closedFountain = new BlockedConnection("You stick your head in the fountain, but find no way down");
		Room blacksmith = new Room("blacksmith","ooga","booga");
		BlockedConnection wall = new BlockedConnection("The walls appear solid, and unless you want a concussion, I wouldn't test it");
		BlockedConnection ceiling = new BlockedConnection("you look up and...\nARGH where did the sky go?!? Oh wait. You're inside, it's just the ceiling");
		Room alchemist = new Room("alchemist","ooga","booga");
		Room tavern = new Room("tavern","ooga","booga");
		Room library = new Room("library","ooga","booga");
		Room sewer = new Room("sewer","was that a rat?","ew");
		Room alley = new Room("alley","ooga","booga");

		townEdge.setConnection(Direction.NORTH,blacksmith);
		townEdge.setConnection(Direction.SOUTH,alchemist);
		townEdge.setConnection(Direction.EAST,townSquare);
		townEdge.setConnection(Direction.WEST,gate);
		townEdge.setConnection(Direction.UP,sky);
		townEdge.setConnection(Direction.DOWN,ground);
		townSquare.setConnection(Direction.NORTH,tavern);
		townSquare.setConnection(Direction.SOUTH,library);
		townSquare.setConnection(Direction.EAST,alley);
		townSquare.setConnection(Direction.WEST,townEdge);
		townSquare.setConnection(Direction.UP,sky);
		townSquare.setConnection(Direction.DOWN,closedFountain);
		blacksmith.setConnection(Direction.NORTH,wall);
		blacksmith.setConnection(Direction.SOUTH,townEdge);
		blacksmith.setConnection(Direction.EAST,wall);
		blacksmith.setConnection(Direction.WEST,wall);
		blacksmith.setConnection(Direction.UP,ceiling);
		blacksmith.setConnection(Direction.DOWN,ground);
		alchemist.setConnection(Direction.NORTH,townEdge);
		alchemist.setConnection(Direction.SOUTH,wall);
		alchemist.setConnection(Direction.EAST,wall);
		alchemist.setConnection(Direction.WEST,wall);
		alchemist.setConnection(Direction.UP,ceiling);
		alchemist.setConnection(Direction.DOWN,ground);
		tavern.setConnection(Direction.NORTH,wall);
		tavern.setConnection(Direction.SOUTH,townSquare);
		tavern.setConnection(Direction.EAST,wall);
		tavern.setConnection(Direction.WEST,wall);
		tavern.setConnection(Direction.UP,ceiling);
		tavern.setConnection(Direction.DOWN,ground);
		library.setConnection(Direction.NORTH,townSquare);
		library.setConnection(Direction.SOUTH,wall);
		library.setConnection(Direction.EAST,wall);
		library.setConnection(Direction.WEST,wall);
		library.setConnection(Direction.UP,ceiling);
		library.setConnection(Direction.DOWN,ground);
		alley.setConnection(Direction.NORTH,wall);
		alley.setConnection(Direction.SOUTH,wall);
		alley.setConnection(Direction.EAST,wall);
		alley.setConnection(Direction.WEST,townSquare);
		alley.setConnection(Direction.UP,sky);
		alley.setConnection(Direction.DOWN,ground);

		world.addRoom(townEdge, townEdge.getName());
		world.addRoom(townSquare, townSquare.getName());
		world.addRoom(blacksmith, blacksmith.getName());
		world.addRoom(alchemist, alchemist.getName());
		world.addRoom(tavern, tavern.getName());
		world.addRoom(library, library.getName());
		world.addRoom(alley, alley.getName());
	}
}
