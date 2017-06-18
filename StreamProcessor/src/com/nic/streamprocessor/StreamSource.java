package com.nic.streamprocessor;

public abstract class StreamSource extends Source
{
	public StreamSource()
	{
		
	}
	
	public abstract boolean nextStep();
}
