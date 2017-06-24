package com.nic.streamprocessor.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.nic.streamprocessor.streamtargets.MemoryStreamTarget;

@SuppressWarnings("serial")
public class SeriesVisualiserPanel extends JPanel implements MouseWheelListener
{
	protected MemoryStreamTarget target;
	protected float xScale;
	protected float yScale;
	protected int start;
	
	
	public SeriesVisualiserPanel(MemoryStreamTarget t)
	{
		target = t;
		xScale = 0.5f;
		yScale = 5f;
		start = 0;
		addMouseWheelListener(this);
	}
	
	public void xZoom(int xz)
	{
		xScale *= Math.pow(1.1d, (double)xz);
		repaint();
	}

	public void yZoom(int yz)
	{
		yScale *= Math.pow(1.1d, (double)yz);
		repaint();
	}

	public void scroll(int s)
	{
		start += (s / xScale);
		if(start < 0)
			start = 0;
		repaint();
	}
	
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		String[] channels = target.getChannels();
		int width = getWidth();
		int height = getHeight();
		int zeroLine = height / 2;
		float i = 0f;
		float x = 0f;
		float lastx = 0f;
		int lasty = zeroLine;
		Color color = null;
		
		for(int c = 0; c < channels.length; c++)
		{
			ArrayList<Float> serie = target.getSeries(channels[c]);
			if(c == 0) color = Color.blue;
			if(c == 1) color = Color.RED;
			if(c == 2) color = Color.GREEN;
			if(c == 3) color = Color.YELLOW;
			g.setColor(color);
			i = (float)start;
			x = 0f;
			lastx = 0f;
			lasty = zeroLine;
			
			while(x < width &&  i < serie.size())
			{
				int y = zeroLine - (int)(yScale * target.getSeries(channels[c]).get((int)i));
				g2.drawLine((int)lastx, lasty, (int)x, y);
				lastx = x;
				lasty = y;
				if(xScale >= 1f)
				{
					x += xScale;
					i += 1f;
				}
				else
				{
					x += 1f;
					i += 1f / xScale;
				}
			}
			
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		int amt = e.getWheelRotation();
		if(e.isShiftDown())
			scroll(100*amt);
		else if(e.isControlDown())
			xZoom(-amt);
		else if(e.isAltDown())
			yZoom(-amt);
		else
			scroll(10*amt);		
	}
}
