package com.nic.streamprocessor;

import java.util.HashMap;

public class StreamTarget implements PipeTarget
{
	protected String[] expectedInputs = {};
	protected HashMap<String, Float> inputValues;

	public StreamTarget(String[] ei)
	{
		expectedInputs = ei;
		inputValues = new HashMap<String, Float>();
	}
	
	public boolean setInput(String channel, float value)
	{
		if(!inputValues.containsKey(channel))
		{
			inputValues.put(channel, value);
			if(checkHasAllExpectedInputs())
			{
				processStep();
				inputValues.clear();
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean drain()
	{
		return true;
	}
	
	private boolean checkHasAllExpectedInputs()
	{
		if(inputValues.size() >= expectedInputs.length)
		{
			boolean missing = false;
			for(int i = 0; i < expectedInputs.length; i++)
				if(inputValues.get(expectedInputs[i]) == null)
					missing = true;
			if(!missing)
				return true;
		}
		return false;
	}
	
	protected void processStep()
	{
		for(int i = 0; i < expectedInputs.length; i++)
		{
			String key = expectedInputs[i];
			if(i > 0)
				System.out.print("\t");
			System.out.print(inputValues.get(key));
		}
		System.out.println();
	}
	

}
