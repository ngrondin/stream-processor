package com.nic.streamprocessor.nodes;

import com.nic.streamprocessor.Node;

public class Arithmetic extends Node
{
	public Arithmetic()
	{
		setExpectedInputs(new String[] {"term1", "term2"});
	}
	
	
	protected void processStep()
	{
		float val = inputValues.get("term1") + inputValues.get("term2");
		setOutput("result", val);
		pushOutputs();
		clearOutputs();
	}

	protected boolean processDrain()
	{
		return true;
	}

}
