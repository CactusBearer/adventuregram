package com.cactusbearer;

public class ItemContainer extends Item implements IContainer{
	private int capacity;
	private boolean accessible;

	public ItemContainer(int attack, String theName, boolean isWeapon, boolean isFood, boolean isTreasure/*, [some dictionary] customInteractions*/, int capacity){
		super(attack,theName,isWeapon,isFood,isTreasure/*,customInteractions*/);
		this.capacity=capacity;
	}

	public int getCapacity(){
		return capacity;
	}

	public void setAccessible(boolean accesible) {
		this.accessible = accesible;
	}

	public boolean isAccessible(){
		return accessible;
	}

	public boolean hasInventoryCheck(){
		return true;
	}
}
