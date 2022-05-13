package com.cactusbearer;

public interface IConnection{
	boolean isValid();
	String blockedExplanation();
	boolean inContext();
	String getName();
}
