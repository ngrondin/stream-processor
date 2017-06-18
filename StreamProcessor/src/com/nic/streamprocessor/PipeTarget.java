package com.nic.streamprocessor;

public interface PipeTarget
{
	boolean setInput(String channel, float value);
	boolean drain();
}
