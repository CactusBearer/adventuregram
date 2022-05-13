package com.cactusbearer;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("main loaded");
		try (Scanner input = new Scanner(System.in)) {

			//Initialize main program structure classes
			World world = new World();
			resetRooms(world);
			resetChars(world);
			resetItems(world);
			GameCharacter player = new GameCharacter(1, "player", "a lost adventurer", true,false, null);
			player.setContext(false);
			player.setAccessInventory(true);
			world.addChar(player, player.getName(), "startArea");
			world.setPlayer(player);

			Narrator nar = new Narrator(123456789, world);
			Editor editor = new Editor(true, nar, world);
			Interpreter interpreter = new Interpreter(123456789, editor, world);

			interpreter.parseInput("look");

			while (world.gameActive()) {
				String line = input.nextLine().strip();
				if (line.equals("EXIT")) break;
				interpreter.parseInput(line);

			}
			System.out.println("thanks for playing aha");
			input.nextLine();
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
		String[][] smithDialogue = {
				{"Shop's closed. Can't trust anyone in this town... anyone could be a cultist","Can't even trust YOU, traveller, now can I?","If I could just find myself an ally for one night..."},
				{"To stop the cult, we'll need to gain entrance to their meetings. I've figured out they're after that amulet, SHOW it to some people, see what you can learn."},
				{"This is only the second time I've seen those robes... I'll bring you justice, I'm so close...","Hmm? oh I'm sorry, it just brings back memories. GIVE them here, let's get started"},
				{"The cult is notoriously secretive, by revealing their leader to their following, we'll... ehm.. well I'm sure it'll do something. Anyway, watch this"},
				{"FOR THE LOVE OF EVERYTHING HOLY give ME THE amulet"}
				};
		String[][] priestDialogue = {{"splish splash I was taking a bath", "something something saturday night","oh! I'm terribly sorry, do you need any spiritual guidance or healing services?","yes?"}};
		String[][] potionmasterDialogue = {
				{"Closing shop for the day, sorry to disappoint. It's been hard getting supplies in a time like this"},
				{"mmm thankk you for the drinnk freind","*hic*","mmm barkeep you did itt again..."},
				{"bah whhatever I'll need that later","noww where did I put my sp- *hic* spare set..."}
		};
		String[][] mayorDialogue = {{"Oh hello traveler! A dark time you find yourself in our town","A mysterious cult has this town in its grasp, and we're at a loss at what to do","For everyone's safety, we shut our gate for the night, so no cultists try to escape","oh what to do, what to do..."}};
		String[][] scholarDialogue = {{"my purpose here is beyond you and this story, fool","I mean aha I love books don't you? If you EXAMINE a container like the bookshelf you can see what it stores!","Try to EXAMINE the bookshelf to see what it's holding! :)"}};
		String[][] barkeepDialogue = {
				{"Welcome to barkeep's boozehouseTM, we have BEVERAGEs available to BUY for just a coin!", "Most of the town are regulars, so I know a little about everyone here","And of course you can forget your woes with a BEVERAGE, which you can BUY with a coin"},
				{"A BEVERAGE from here GIVEn to the right person can keep them occupied, always available to BUY here for a coin"}
				};

		GameCharacter smith = new GameCharacter(1,"smith","the town's smith, complete with a sooty apron",false,false, smithDialogue);
		GameCharacter priest = new GameCharacter(1,"priest","the local priest, also serves as the town's healer",false,false, priestDialogue);
		GameCharacter potionmaster = new GameCharacter(1,"potionmaster","",false,false, potionmasterDialogue);
		GameCharacter mayor = new GameCharacter(1,"mayor","",false,false, mayorDialogue);
		GameCharacter scholar = new GameCharacter(1,"scholar","",false,false, scholarDialogue);
		GameCharacter barkeep = new GameCharacter(1,"barkeep","",false,false, barkeepDialogue);


		world.addChar(smith, smith.getName(), "blacksmith");
		world.addChar(priest, priest.getName(), "temple");
		world.addChar(potionmaster, potionmaster.getName(), "alchemist");
		world.addChar(mayor, mayor.getName(), "townSquare");
		world.addChar(scholar, scholar.getName(), "library");
		world.addChar(barkeep, barkeep.getName(), "tavern");
	}

	private static void resetItems(World world){
		Item sign = new Item(0,"sign","\"Welcome to Hastings, Nebraska,\" it reads. \"Turn back while you still can!\" From this you understand what exactly you're capable of\n" +
				"GO [direction] - move in that direction. Shortenable to just the direction\n" +
				"EXAMINE [item/container] - observe an item more closely\n" +
				"LOOK - look around the room you're in\n" +
				"INVENTORY - check what items you're carrying. Shortenable to INV\n" +
				"TALK [character] - see what a character has to say. not always the same, so try multiple times\n" +
				"TAKE [item] - add an item to your inventory\n" +
				"SHOW [item] [character] - show an item to a character (wow)\n" +
				"GIVE [item] [character] - I think you got this one\n" +
				"BUY [item] - buy an item from a vendor\n" +
				"HELP - read out this command list",false, true);


		ItemContainer fountain = new ItemContainer("fountain", "the centerpiece of the town, with surprisingly clear water flowing through it",5,true);
		//ItemContainer chest = new ItemContainer("chest","a wooden container, fastened together with nails and chain",5,true);
		ItemContainer bookshelf = new ItemContainer("bookshelf","the literary range found in this town is modest compared to the capital city, but you have no time for books' pleasures",5,true);

		Item baseball = new Item(0,"baseball", "its red seams are faded, but still hold", true,true);
		Item coin = new Item(0,"coin", "a small gold piece, imprinted with an intricate design",true, true);
		//Item sword = new Item(1,"sword", "a short but sturdy bronze sword. It could use polishing",true, true);
		Item robes = new Item(0,"robes","long dark robes, ornamented with strange symbols",true,true);
		Item amulet = new Item(0,"amulet","you feel a dark power pulsating from its bizarre twisted shape",true,true);
		Item potion = new Item(5,"potion","an amnesia potion bought from the alchemist shop. Nobody knows how it tastes",true,true);
		Item notes = new Item(0,"notes","scribbles from the self-proclaimed \"Unholy Undoer\" clearly in the smith's handwriting",true,true);
		Item beverage = new Item(0,"beverage","a boozy concoction from the barkeep, beloved by his regulars",true,true);
		Item firstTimeInArmoryFlag = new Item(0,"firstTimeInArmoryFlag","a developer's took to avoid coding better",false,false);
		Item drunkPotionmasterFlag = new Item(0,"drunkPotionmasterFlag","h",false,false);
		Item notDrunkPotionmasterFlag = new Item(0,"notDrunkPotionmasterFlag","h",false,false);
		Item talkAlleyFirstFlag = new Item(0,"talkAlleyFirstFlag","h",false,false);
		Item passageOpenFlag = new Item(0, "passageOpenFlag", "h", false, false);
		Item finalChoiceFlag = new Item(0, "finalChoiceFlag", "h", false, false);
		Item lookCryptFlag = new Item(0,"lookCryptFlag","h",false,false);

		world.addItem(sign, sign.getName(),"startArea");
		world.addItem(fountain, fountain.getName(), "townSquare");
		//world.addItem(chest, chest.getName(), "townSquare");
		world.addItem(bookshelf, bookshelf.getName(), "library");
		//world.addItem(baseball, baseball.getName(), "townEdge");
		world.addItem(coin, coin.getName(), "fountain");
		//world.addItem(sword, sword.getName(), "chest");
		world.addItem(robes, robes.getName(), "alchemist");
		world.addItem(amulet, amulet.getName(), "smith");
		//buworld.addItem(potion, potion.getName(), "alchemist");
		world.addItem(notes, notes.getName(), "bookshelf");
		world.addItem(beverage, beverage.getName(), "tavern");
		world.addItem(firstTimeInArmoryFlag, firstTimeInArmoryFlag.getName(), "storage");
		world.addItem(drunkPotionmasterFlag, drunkPotionmasterFlag.getName(), "storage");
		world.addItem(notDrunkPotionmasterFlag, notDrunkPotionmasterFlag.getName(), "alchemist");
		world.addItem(talkAlleyFirstFlag, talkAlleyFirstFlag.getName(), "storage");
		world.addItem(passageOpenFlag, passageOpenFlag.getName(), "storage");
		world.addItem(finalChoiceFlag, finalChoiceFlag.getName(), "storage");
		world.addItem(lookCryptFlag, lookCryptFlag.getName(), "secretWatchpoint");
	}

	private static void resetRooms(World world){
		Room startArea = new Room("startArea","Your path through the woods ends here, at a SIGN introducing the walled town before you. You think you should EXAMINE it further","lol you'll never see this");
		Room startRoad = new Room("startRoad","you'll never see this","I think");
		startRoad.setContext(false);
		BlockedConnection woods = new BlockedConnection("woods","The darkness of the thicket and some wild noises lead you to reconsider going to the woods");

		Room townEdge = new Room("townEdge","The town's welcoming street is flanked by the its two major artisans, a blacksmith and alchemist","The artisanal district");
		Room townSquare = new Room("townSquare", "The central plaza of this town, ornamented with a majestic fountain, and surrounded by the common areas","The main plaza");
		Room blacksmith = new Room("blacksmith","Some of the smith's finer works adorn the walls. Quality work if you have the gold for a contract. The forge must be out back","The blacksmith shop");
		Room alchemist = new Room("alchemist","A strange smell persists in the potionmaster's shop, despite his best efforts. A dirty work, alchemy","The potion shop");
		Room tavern = new Room("tavern","Every town has its tavern, and this town is no different. Music, spirits, and debauchery are all found in excess here","A lively common area for drinking");
		Room library = new Room("library","The hidden pride of the town of Hastings. Its library is unmatched for towns of the same size. If you came earlier in the day, you're sure you'd see many more scrolling through its collection","The town's wealth of knowledge");
		Room sewer = new Room("sewer","was that a rat?","ew");
		Room alley = new Room("alley","The torches here don't seem to shine as bright, but every town needs a good scary backroad. At least it's right by the temple","A backstreet by the temple");
		Room temple = new Room("temple","The air has a soothing quality to it here. Both spiritual worship and healing magic is practiced in this holy place","A holy place of worship and healing");
		Room crypt = new Room("crypt","ooga","booga");
		Room secretWatchpoint = new Room("secretWatchpoint", "A hollowed out section of the town's wall overlooking the crypt", "A watchpoint over the crypt");
		Room armory = new Room("armory","Many weapons and tools adorn the walls of this baseball, as do many notes and strings. The smith knows how to draw a conspiracy all right","the smith's storage and vigilante headquarters");
		Room storage = new Room("storage","now technically this should be an inaccessible room","and these will never be read");
		BlockedConnection gate = new BlockedConnection("gate","The gate's shut. No way out, you'd better get comfortable");
		BlockedConnection sky = new BlockedConnection("sky","You still don't have any wings, and the available ladders are about 300000ft short");
		BlockedConnection ground = new BlockedConnection("ground","Much to your relief, the ground forbids you from passing through it");
		BlockedConnection wall = new BlockedConnection("wall","The walls appear solid, and unless you want a concussion, I wouldn't test it");
		BlockedConnection townWall = new BlockedConnection("townWall", "The impressive walls surrounding this town seem impassable, at least from this view. You're stuck in town, at least for now");
		BlockedConnection insideWall = new BlockedConnection("insideWall", "The stone composing the town wall is a little too solid to pass through");
		BlockedConnection ceiling = new BlockedConnection("ceiling","you look up and...\nARGH where did the sky go?!? Oh wait. You're inside, it's just the ceiling");
		BlockedConnection closedFountain = new BlockedConnection("closedFountain","You stick your head in the fountain, but see nothing other than lots of water and a some coins");
		BlockedConnection lockedTemple = new BlockedConnection("lockedTemple","The priest must've locked the way into the temple from the other side");
		BlockedConnection alleyBlock = new BlockedConnection("alleyBlock","This hardly feels like a time for second thoughts. Besides, your movement might alert them to your location");
		BlockedConnection breakingWall = new BlockedConnection("breakingWall","This part of the town wall is actually less impressive... and a lot more crumbly. In a pinch, you might be able to slide out and escape this town");
		BlockedConnection cryptBlock = new BlockedConnection("cryptBlock","Well, you can see the cultists in the crypt plain as day from here, but I wouldn't want to jump down onto them unprepared JUST yet");

		startArea.setConnection(Direction.NORTH,woods);
		startArea.setConnection(Direction.SOUTH,woods);
		startArea.setConnection(Direction.EAST,townEdge);
		startArea.setConnection(Direction.WEST,startRoad);
		startArea.setConnection(Direction.UP,sky);
		startArea.setConnection(Direction.DOWN,ground);
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
		alley.setConnection(Direction.NORTH,temple);
		alley.setConnection(Direction.SOUTH,wall);
		alley.setConnection(Direction.EAST,townWall);
		alley.setConnection(Direction.WEST,townSquare);
		alley.setConnection(Direction.UP,sky);
		alley.setConnection(Direction.DOWN,ground);
		temple.setConnection(Direction.NORTH,crypt);
		temple.setConnection(Direction.SOUTH,alley);
		temple.setConnection(Direction.EAST,wall);
		temple.setConnection(Direction.WEST,wall);
		temple.setConnection(Direction.UP,ceiling);
		temple.setConnection(Direction.DOWN,ground);
		crypt.setConnection(Direction.NORTH,wall);
		crypt.setConnection(Direction.SOUTH,lockedTemple);
		crypt.setConnection(Direction.EAST,wall);
		crypt.setConnection(Direction.WEST,wall);
		crypt.setConnection(Direction.UP,ceiling);
		crypt.setConnection(Direction.DOWN,ground);
		armory.setConnection(Direction.NORTH,wall);
		armory.setConnection(Direction.SOUTH,wall);
		armory.setConnection(Direction.EAST,wall);
		armory.setConnection(Direction.WEST,wall);
		armory.setConnection(Direction.UP,blacksmith);
		armory.setConnection(Direction.DOWN,ground);
		secretWatchpoint.setConnection(Direction.NORTH,insideWall);
		secretWatchpoint.setConnection(Direction.SOUTH,alleyBlock);
		secretWatchpoint.setConnection(Direction.EAST,breakingWall);
		secretWatchpoint.setConnection(Direction.WEST,insideWall);
		secretWatchpoint.setConnection(Direction.UP,insideWall);
		secretWatchpoint.setConnection(Direction.DOWN,cryptBlock);

		//world.addRoom(startRoad, startRoad.getName());
		world.addRoom(startArea, startArea.getName());
		world.addRoom(townEdge, townEdge.getName());
		world.addRoom(townSquare, townSquare.getName());
		world.addRoom(blacksmith, blacksmith.getName());
		world.addRoom(alchemist, alchemist.getName());
		world.addRoom(tavern, tavern.getName());
		world.addRoom(library, library.getName());
		world.addRoom(alley, alley.getName());
		world.addRoom(temple, temple.getName());
		world.addRoom(crypt, crypt.getName());
		world.addRoom(storage, storage.getName());
		world.addRoom(armory, armory.getName());
		world.addRoom(secretWatchpoint, secretWatchpoint.getName());

		world.addCon(woods, woods.getName());
		world.addCon(gate, gate.getName());
		world.addCon(sky, sky.getName());
		world.addCon(ground, ground.getName());
		world.addCon(wall, wall.getName());
		world.addCon(townWall, townWall.getName());
		world.addCon(insideWall, insideWall.getName());
		world.addCon(ceiling, ceiling.getName());
		world.addCon(closedFountain, closedFountain.getName());
		world.addCon(lockedTemple, lockedTemple.getName());
		world.addCon(alleyBlock, alleyBlock.getName());
		world.addCon(breakingWall, breakingWall.getName());
		world.addCon(cryptBlock, crypt.getName());
	}
}
