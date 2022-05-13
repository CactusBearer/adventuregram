package com.cactusbearer;

public interface IGameObject extends IInteractable{
	String getName();
	boolean hasInventoryCheck();
	public GameObjectType getType();
}
