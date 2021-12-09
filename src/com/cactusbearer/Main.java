package com.cactusbearer;

public class Main {

	public static void main(String[] args) {
		System.out.println("main loaded");

		//Initialize main program structure classes
		World world = new World();
		Narrator nar = new Narrator(123456789, world);
		Editor editor = new Editor(true, nar, world);

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
		GameCommand movePickaxe = new GameCommand(null, Command.MOVE, "pickaxe", "bathroom", Direction.NONE); //world moves pickaxe to bathroom with no direction
		editor.doSomething(movePickaxe);
		GameCommand moveKnife = new GameCommand(null, Command.MOVE, "knife", "foyer", Direction.NONE);
		editor.doSomething(moveKnife);

		//check state to see changes
		world.printAllRooms();
	}
}
