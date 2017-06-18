package com.nic.streamprocessor;

import java.util.HashMap;

public class Node extends Source implements PipeTarget
{
	protected String[] expectedInputs = {};
	protected HashMap<String, Float> inputValues;
	
	public Node()
	{
		inputValues = new HashMap<String, Float>();
	}

	public boolean setInput(String channel, float value)
	{
		if(!inputValues.containsKey(channel))
		{
			inputValues.put(channel, value);
			if(checkHasAllExpectedInputs())
				processStep();
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean drain()
	{
		if(processDrain())
		{
			return drainOutputs();
		}
		else
		{
			return false;
		}
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
		
	}
	
	protected boolean processDrain()
	{
		return true;
	}
	
}
