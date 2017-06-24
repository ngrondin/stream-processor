package com.nic.streamprocessor.streamtargets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.nic.streamprocessor.StreamTarget;

public class MemoryStreamTarget extends StreamTarget
{
	protected HashMap<String, ArrayList<Float>> series;
	
	public MemoryStreamTarget(String[] ei) throws IOException
	{
		super(ei);
		series = new HashMap<String, ArrayList<Float>>();
		for(int i = 0; i < ei.length; i++)
			series.put(ei[i], new ArrayList<Float>());
	}
	
	protected void processStep()
	{
		for(int i = 0; i < expectedInputs.length; i++)
		{
			series.get(expectedInputs[i]).add(inputValues.get(expectedInputs[i]));
		}
	}
	
	public ArrayList<Float> getSeries(String channel)
	{
		return series.get(channel);
	}
	
	public String[] getChannels()
	{
		return expectedInputs;
	}
}
