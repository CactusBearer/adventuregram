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

		String[] split = input.split(" ");

		subject = world.getPlayer();
		room = (Room) world.locateGameObject(subject);
		errorCode = Error.NONE;
		notes = null;

		//if comma at end of split[0] assign to gamecharacter, for player giving other gamechars commands
		switch (split[0].toUpperCase()){
			case "MOVE":
				parseMove(split);
				break;
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
			case "PUT":
				parsePut(split);
				break;
			case "DROP":
				parseDrop(split);
				break;
			case "INVENTORY":
				parseInventory(split);
				break;
			case "EXAMINE", "CHECK":
				parseExamine(split);
				break;
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

	private void parseLook(String[] split){
		action = Command.LOOK;
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
	}

	private void parseGo(String[] split){
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
				direction = Direction.ERROR;
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

	/*private void parse(String[] split){
		action = ;
		dObject = null;
		iObject = null;
		destination = null;
		direction = null;
	} template */

}
