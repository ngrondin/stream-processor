package com.nic.streamprocessor;

import java.util.HashMap;

public abstract class Node extends Source implements PipeTarget
{
	private String[] expectedInputs = {};
	protected HashMap<String, Float> inputValues;
	
	public Node()
	{
		inputValues = new HashMap<String, Float>();
	}
	
	protected void setExpectedInputs(String[] ei)
	{
		expectedInputs = ei;
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
	
	protected abstract void processStep();
	
	protected abstract boolean processDrain();
	
}
