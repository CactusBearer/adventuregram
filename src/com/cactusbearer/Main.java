package com.cactusbearer;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        World.testPrint();
        System.out.print("h\n");

        World world=new World();
        Narrator nar=new Narrator(123456789,world);
        Editor editor=new Editor(true,nar,world);

        Room foyer=new Room("foyer","a big room","a room");
        world.addRoom(foyer,foyer.getName());
        Room kitchen=new Room("kitchen",null,null);
        world.addRoom(kitchen,kitchen.getName());
        Room bathroom=new Room("bathroom",null,null);
        world.addRoom(bathroom,bathroom.getName());

        Item shovel=new Item(10,"shovel",false,false,false);
        world.addItem(shovel,shovel.getName(),foyer);
        Item knife=new Item(20,"knife",true,false,false);
        world.addItem(knife,knife.getName(),kitchen);
        Item pickaxe=new Item(10,"pickaxe",false,false,false);
        world.addItem(pickaxe,pickaxe.getName(),foyer);
        System.out.println(foyer.getShortDesc()+"\n"+shovel.getName());

        GameCharacter player=new GameCharacter(10,"player","the character with the user's hand up their spine",true);
        world.addChar(player,player.getName(),kitchen);

        world.printAllRooms();

        GameCommand movePickaxe=new GameCommand(null,Enums.command.MOVE,"pickaxe","bathroom",Enums.direction.NONE); //world moves pickaxe to bathroom with no direction
        editor.doSomething(movePickaxe);
        GameCommand moveKnife=new GameCommand(null,Enums.command.MOVE,"knife","foyer",Enums.direction.NONE);
        editor.doSomething(moveKnife);

        world.printAllRooms();
    }
}
