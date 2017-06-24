package com.nic.streamprocessor.nodes;

import com.nic.streamprocessor.Node;

public class Arithmetic extends Node
{
	public Arithmetic()
	{
		super();
		setExpectedInputs(new String[] {"term1", "term2"});
	}
	
	public Arithmetic(String operation)
	{
		super();
		setExpectedInputs(new String[] {"term1", "term2"});
		setParameter("operation", operation);
	}
	
	
	protected void processStep()
	{
		float term1 = getInput("term1");
		float term2 = getInput("term2");
		String operation = getParameter("operation");
		float result = 0;
		switch(operation)
		{
			case "add":
				result = term1 + term2;
				break;
			case "substract":
				result = term1 - term2;
				break;
			case "multiply":
				result = term1 * term2;
				break;
			case "divide":
				result = term1 / term2;
				break;
		}
		setOutput("result", result);
		pushOutputs();
		clearOutputs();
	}

	protected boolean processDrain()
	{
		return true;
	}

}
