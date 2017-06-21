package com.nic.streamprocessor.streamtargets;

import java.io.FileWriter;
import java.io.IOException;

import com.nic.streamprocessor.StreamTarget;

public class FlatFileStreamTarget extends StreamTarget
{
	protected FileWriter fw;
	
	public FlatFileStreamTarget(String fileName, String[] ei) throws IOException
	{
		super(ei);
		fw = new FileWriter(fileName);
		for(int i = 0; i < ei.length; i++)
		{
			if(i > 0)
				fw.write(",");
			fw.write(ei[i]);
		}
		fw.write("\n");
	}
	
	protected void processStep()
	{
		try
		{
			for(int i = 0; i < expectedInputs.length; i++)
			{
				String key = expectedInputs[i];
				float val = inputValues.get(key);
				String valStr = null;
			    if(val == (long) val)
			    	valStr = String.format("%d",(long)val);
			    else
			    	valStr = String.format("%s",val);
				if(i > 0)
					fw.write(",");
				fw.write(valStr);
			}
			fw.write("\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void close() throws IOException
	{
		fw.close();
	}

}
