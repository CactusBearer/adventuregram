package com.cactusbearer;

public class Interpreter {
	private long chatId;
	private Editor editor;
	private World world;

	private IGameObject dObject;
	private IGameObject iObject;
	private GameCharacter subject;
	private Command action;
	private IContainer destination;
	private Direction direction;
	private Room room;
	private Error errorCode;
	private String[] notes;

	Interpreter(long chat, Editor theEditor, World theWorld)
	{
		chatId=chat;
		editor=theEditor;
		world=theWorld;
		System.out.println("Interpreter loaded");
	}
	/*these are what we expect to take from any given command for command: subject, dObject, iObject
	move: 		none,				gameobject, 			container
	go: 		character, 			room, 			none,
	look: 		character (self), 	room,	 		none,
	examine: 	character, 			gameobject, 	none,
	inventory: 	character (self), 	none, 			none,
	take: 		character, 			gameobject, 	none,
	drop: 		character, 			gameobject, 	implied Room
	put:		character,			gameobject,		item container
	 */
	public void parseInput(String input){
		/*
		1. split line into words, assumed correct structure, will throw back error message if not as expected
		2. determine command type, should be first word
		3. determine what types the nouns of the command are based on the command type and table above (separate command)
		4. get references to nouns based on their names (in prior command)
		5. check if noun references are valid for command, like if a necessary dObject is null
		6. construct command, pass it on
		*/
		String[] split = formatString(input).split(" ");

		subject = world.getPlayer();
		room = (Room) world.locateGameObject(subject);
		errorCode = Error.NONE;
		notes = null;

		//if comma at end of split[0] assign to gamecharacter, for player giving other gamechars commands
		switch (split[0].toUpperCase()){
			case "NORTH":
				action = Command.GO;
				direction = Direction.NORTH;
				break;
			case "SOUTH":
				action = Command.GO;
				direction = Direction.SOUTH;
				break;
			case "EAST":
				action = Command.GO;
				direction = Direction.EAST;
				break;
			case "WEST":
				action = Command.GO;
				direction = Direction.WEST;
				break;
			case "UP":
				action = Command.GO;
				direction = Direction.UP;
				break;
			case "DOWN":
				action = Command.GO;
				direction = Direction.DOWN;
				break;
			case "HELP":
				parseHelp(split);
				break;
			case "SHOW":
				parseShow(split);
				break;
			case "BUY":
				parseBuy(split);
				break;
			case "USE":
				parseUse(split);
				break;
			case "GIVE":
				parseGive(split);
				break;
			case "TALK":
				parseTalk(split);
				break;
			/*case "MOVE":
				parseMove(split);
				break;*/
			case "GO":
				//System.out.println("a");
				parseGo(split);
				break;
			case "LOOK":
				parseLook(split);
				//System.out.println("a");
				break;
			case "TAKE":
				parseTake(split);
				break;
			/*case "PUT":
				parsePut(split);
				break;*/
			/*case "DROP":
				parseDrop(split);
				break;*/
			case "INVENTORY", "INV":
				parseInventory(split);
				break;
			case "EXAMINE", "CHECK":
				parseExamine(split);
				break;
			/*case "CHECKSTORAGEPLS":
				world.printRoom(world.getRoom("storage"));*/
			default:
				//System.out.println("1");
				action=Command.ERROR;
				errorCode=Error.INVALID_ENTRY;
				notes = new String[]{split[0]};
				break;
		}

		GameCommand heck = new GameCommand(subject, action, dObject, destination, iObject, direction, room, errorCode, notes);
		editor.doSomething(heck);
	}

	private String formatString(String input){
		for(int i = 1; i < input.length()-2; i -= -1) if (input.substring(i-1,i+3).equalsIgnoreCase(" to ")){
			input = input.substring(0,i)+input.substring(i+3);
			i--;
		}
		for(int i = 1; i < input.length()-3; i -= -1) if (input.substring(i-1,i+4).equalsIgnoreCase(" the ")){
			input = input.substring(0,i)+input.substring(i+4);
			i--;
		}
		for(int i = 0; i < input.length()-1; i -= -1) if (input.substring(i,i+2).equalsIgnoreCase("  ")){
			input = input.substring(0,i)+input.substring(i+1);
			i--;
		}
		for(int i = 0; i < input.length(); i -= -1) if (input.substring(i,i+1).equalsIgnoreCase(",")){
			input = input.substring(0,i)+input.substring(i+1);
			i--;
		}
		return input;
	}

	private void parseLook(String[] split){
		action = Command.LOOK;
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parseHelp(String[] split){
		action = Command.HELP;
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parseGo(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"direction"};
			return;
		}
		action = Command.GO;
		dObject = null;
		iObject = null;
		destination = null;

		switch(split[1].toUpperCase()){
			case "NORTH":
				direction= Direction.NORTH;
				break;
			case "SOUTH":
				direction = Direction.SOUTH;
				break;
			case "EAST":
				direction = Direction.EAST;
				break;
			case "WEST":
				//System.out.println("b");
				direction = Direction.WEST;
				break;
			case "UP":
				direction = Direction.UP;
				break;
			case "DOWN":
				direction = Direction.DOWN;
				break;
			default:
				//error message sad
				action = Command.ERROR;
				errorCode = Error.INVALID_ENTRY;
				notes = new String[]{split[1]};
				break;
		}
	}

	private void parseMove(String[] split){
		action = Command.MOVE;
		dObject = world.getGameObject(split[1]);
		destination = world.getContainer(split[2]);
		iObject = null;
		direction = null;

	}

	private void parseTake(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item"};
			return;
		}
		action = Command.TAKE;
		String checkObject = split[1].toLowerCase();
		Item reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
			if(reference.portable()) {
				dObject = reference;
			}
			else{
				action = Command.ERROR;
				errorCode = Error.IMMOVABLE;
				notes = new String[]{split[1]};
				return;
			}
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			dObject = null;
		}
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parsePut(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item"};
			return;
		}
		else if(split.length==2){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"container"};
			return;
		}
		System.out.println("a");
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
		action = Command.PUT;
		String checkObject = split[1].toLowerCase();
		Item reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
			if(reference.portable()){
				dObject = reference;
				System.out.println("b");
			}
			else{
				System.out.println("c");
				action = Command.ERROR;
				errorCode = Error.IMMOVABLE;
				notes = new String[]{split[1]};
				return;
			}
		}
		else{
			System.out.println("d");
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
		System.out.println("e");
		checkObject = split[2].toLowerCase();
		IInteractable rooftop = world.getInteractable(checkObject);
		if(rooftop.hasInventoryCheck()&&((IContainer) rooftop).isAccessible()&&editor.validObject(rooftop)){
			destination = (IContainer) rooftop;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[2]};
			return;
		}
	}

	private void parseDrop(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item in inventory"};
			return;
		}
		dObject = null;
		iObject = null;
		destination = world.locateGameObject(world.getPlayer());
		direction = null;
		action = Command.DROP;
		String checkObject = split[1].toLowerCase();
		Item reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
			if(world.locateGameObject(reference)==world.getPlayer()) dObject = reference;
			else{
				action = Command.ERROR;
				errorCode = Error.INACCESSIBLE;
				notes = new String[]{split[1]};
				return;
			}
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
	}

	private void parseInventory(String[] split){
		action = Command.INVENTORY;
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parseExamine(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item"};
			return;
		}
		action = Command.EXAMINE;
		dObject = null;
		String checkObject = split[1].toLowerCase();
		Item reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
				dObject = reference;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parseTalk(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"character"};
			return;
		}
		action = Command.TALK;
		String checkObject = split[1].toLowerCase();
		IInteractable reference = world.getInteractable(checkObject);
		if(editor.validObject(reference)&&world.getChar(checkObject)==reference){
			dObject = (GameCharacter) reference;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parseGive(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item"};
			return;
		}
		else if(split.length==2){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"character"};
			return;
		}
		action = Command.GIVE;
		String checkObject = split[1].toLowerCase();
		IGameObject reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
			dObject = reference;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
		checkObject = split[2].toLowerCase();
		reference = world.getChar(checkObject);
		if(editor.validObject(reference)){
			iObject = reference;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[2]};
			return;
		}
		destination = null;
		direction = null;
	}

	private void parseUse(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item"};
			return;
		}
		action = Command.USE;
		String checkObject = split[1].toLowerCase();
		IGameObject reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
			dObject = reference;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parseBuy(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item"};
			return;
		}
		action = Command.BUY;
		String checkObject = split[1].toLowerCase();
		IGameObject reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
			if(reference.getName().equals("beverage")){
				if(world.getHeld(world.getPlayer()).contains(world.getItem("coin"))){
					dObject = reference;
					iObject = world.getItem("coin");
					destination = world.getChar("barkeep");
				}
				else{
					action = Command.ERROR;
					errorCode = Error.INSUFFICIENT_FUNDS;
					notes = new String[]{"beverage","coin"};
					return;
				}
			}
			else if(reference.getName().equals("potion")){
				if(world.getHeld(world.getPlayer()).contains(world.getItem("coin"))){
					dObject = reference;
					iObject = world.getItem("coin");
					destination = world.getChar("potionmaster");
				}
				else{
					action = Command.ERROR;
					errorCode = Error.INSUFFICIENT_FUNDS;
					notes = new String[]{"potion","coin"};
					return;
				}
			}
			else{
				action = Command.ERROR;
				errorCode = Error.NOT_PURCHASABLE;
				notes = new String[]{split[1]};
				return;
			}
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
		direction = null;
	}

	private void parseShow(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"item"};
			return;
		}
		else if(split.length==2){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{"character"};
			return;
		}
		action = Command.SHOW;
		String checkObject = split[1].toLowerCase();
		IGameObject reference = world.getItem(checkObject);
		if(editor.validObject(reference)){
			dObject = reference;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[1]};
			return;
		}
		checkObject = split[2].toLowerCase();
		reference = world.getChar(checkObject);
		if(editor.validObject(reference)){
			iObject = reference;
		}
		else{
			action = Command.ERROR;
			errorCode = Error.INVALID_ENTRY;
			notes = new String[]{split[2]};
			return;
		}
		destination = null;
		direction = null;
	}

	/*private void parse(String[] split){
		if(split.length==1){
			action = Command.ERROR;
			errorCode = Error.TOO_SHORT;
			notes = new String[]{""};
			return;
		}
		action = ;
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
	} template */

}
