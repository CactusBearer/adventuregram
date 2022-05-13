package com.cactusbearer;

public interface IGameObject extends IInteractable{
	String getName();
	boolean hasInventoryCheck();
	GameObjectType getType();
}
