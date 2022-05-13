package com.cactusbearer;

public class BlockedConnection implements IConnection{
	private String explanation;
	private String name;

	public  BlockedConnection(String name, String explanation){
		this.name=name;
		this.explanation=explanation;
	}

	public boolean isValid(){
		return false;
	}

	public String blockedExplanation(){
		return explanation;
	}

	public boolean inContext(){
		return false;
	}

	public String getName(){
		return name;
	}
}
