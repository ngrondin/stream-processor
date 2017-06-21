package com.nic.streamprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Source
{
	public HashMap<String, Float> outputValues;
	public HashMap<String, ArrayList<Pipe>> outputPipes;

	public Source()
	{
		outputValues = new HashMap<String, Float>();
		outputPipes = new HashMap<String, ArrayList<Pipe>>();
	}
	
	public void connectOutputPipe(Pipe outputPipe, String channel)
	{
		ArrayList<Pipe> pipeList = outputPipes.get(channel);
		if(pipeList == null)
		{
			pipeList = new ArrayList<Pipe>();
			outputPipes.put(channel, pipeList);
		}
		pipeList.add(outputPipe);
	}
	
	public void setOutput(String channel, float value)
	{
		outputValues.put(channel, value);
	}
	
	public float getOutput(String channel)
	{
		return outputValues.get(channel);
	}
	
	protected void pushOutputs()
	{
		Iterator<String> channelIterator = outputValues.keySet().iterator();
		while(channelIterator.hasNext())
		{
			String channel = channelIterator.next();
			if(outputPipes.containsKey(channel))
			{
				Iterator<Pipe> pipeIterator = outputPipes.get(channel).iterator();
				while(pipeIterator.hasNext())
				{
					Pipe pipe = pipeIterator.next();
					pipe.push(outputValues.get(channel));
				}			
			}
		}
	}
	
	protected boolean drainOutputs()
	{
		boolean fullyDrained = true;
		Iterator<String> channelIterator = outputPipes.keySet().iterator();
		while(channelIterator.hasNext())
		{
			String channel = channelIterator.next();
			Iterator<Pipe> pipeIterator = outputPipes.get(channel).iterator();
			while(pipeIterator.hasNext())
			{
				Pipe pipe = pipeIterator.next();
				if(!pipe.drain())
					fullyDrained = false;
			}			
		}
		return fullyDrained;
	}
	
	protected void clearOutputs()
	{
		outputValues.clear();
	}
}
