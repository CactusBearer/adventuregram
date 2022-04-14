package com.cactusbearer;

public class BlockedConnection implements  IConnection{
	private String explanation;

	public  BlockedConnection(String explanation){
		this.explanation=explanation;
	}

	public boolean isValid(){
		return false;
	}

	public String blockedExplanation(){
		return explanation;
	}
}
