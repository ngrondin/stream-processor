package com.nic.streamprocessor;

import java.util.LinkedList;
import java.util.Queue;

public class Pipe
{

	protected PipeTarget target;
	protected String targetChannel;
	protected Queue<Float> queue;
	
	public Pipe(Source src, String srcChannel, PipeTarget trg, String trgChannel)
	{
		target = trg;
		targetChannel = trgChannel;
		src.connectOutputPipe(this, srcChannel);
		queue = new LinkedList<Float>();
	}
	
	public void push(float value)
	{
		queue.add(value);
		float val = queue.peek();
		if(target.setInput(targetChannel, val))
			queue.remove();
	}
	
	public boolean drain()
	{
		if(queue.size() > 0)
		{
			float val = queue.peek();
			if(target.setInput(targetChannel, val))
				queue.remove();
			return false;
		}
		else
		{
			return target.drain();
		}
	}
}
