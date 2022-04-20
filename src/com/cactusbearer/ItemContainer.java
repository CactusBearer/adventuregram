package com.cactusbearer;

public class ItemContainer extends Item implements IContainer{
	private int capacity;
	private boolean accessible;

	public ItemContainer(String theName, String desc/*boolean isWeapon, boolean isFood, boolean isTreasure, [some dictionary] customInteractions*/, int capacity, boolean accessible){
		super(0,theName,desc,/*isWeapon,isFood,isTreasure,*/false/*,customInteractions*/);
		this.capacity=capacity;
		this.accessible=accessible;
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
