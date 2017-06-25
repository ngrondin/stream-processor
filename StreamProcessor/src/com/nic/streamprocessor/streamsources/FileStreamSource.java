package com.nic.streamprocessor.streamsources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.nic.streamprocessor.StreamSource;

public abstract class FileStreamSource extends StreamSource
{
	protected FileInputStream fis;
	
	public FileStreamSource(String fileName) throws FileNotFoundException
	{
		fis = new FileInputStream(fileName);
	}
}
