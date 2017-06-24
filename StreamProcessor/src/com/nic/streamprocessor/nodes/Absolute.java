package com.nic.streamprocessor.nodes;

import com.nic.streamprocessor.Node;

public class Absolute extends Node
{
	public Absolute()
	{
		super();
		setExpectedInputs(new String[] {"input"});
	}
		
	protected void processStep()
	{

		setOutput("output", Math.abs(getInput("input")));
		pushOutputs();
		clearOutputs();
	}

	protected boolean processDrain()
	{
		return true;
	}

}
