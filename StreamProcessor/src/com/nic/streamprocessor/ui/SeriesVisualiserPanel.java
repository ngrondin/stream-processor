package com.nic.streamprocessor.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.nic.streamprocessor.streamtargets.MemoryStreamTarget;


@SuppressWarnings("serial")
public class SeriesVisualiserPanel extends JPanel implements MouseWheelListener, MouseListener
{
	protected MemoryStreamTarget target;
	protected float xScale;
	protected float yScale;
	protected int start;
	
	protected Color[] colorArray = new Color[]{Color.BLUE, Color.RED, Color.ORANGE, Color.MAGENTA, Color.BLACK, Color.CYAN};
	protected ArrayList<ClickEvent> clickEvents;
	protected long activeChannelMask;
	
	class ClickEvent
	{
		public int x;
		public int y;
		public int r;
		public String function;
		public int param;
		
		public ClickEvent(int x1, int y1, int r1, String f, int p)
		{
			x = x1;
			y = y1;
			r = r1;
			function = f;
			param = p;
		}
	}
	
	public SeriesVisualiserPanel(MemoryStreamTarget t)
	{
		xScale = 0.5f;
		yScale = 5f;
		start = 0;
		clickEvents = new ArrayList<ClickEvent>();
		addMouseWheelListener(this);
		addMouseListener(this);
		if(t != null)
			setTarget(t);
	}
	
	public void setTarget(MemoryStreamTarget t)
	{
		target = t;
		activeChannelMask = (1 << (target.getChannels().length + 1)) - 1;
		float maxAbsY = 0f;
		for(int c = 0; c < target.getChannels().length; c++)
		{
			String channel = target.getChannels()[c];
			ArrayList<Float> serie = target.getSeries(channel);
			for(int i = 0; i < serie.size(); i++)
				if(Math.abs(serie.get(i)) > maxAbsY)
					maxAbsY = Math.abs(serie.get(i));
		}
		int height = getHeight();
		if(height == 0)
			height = 400;
		yScale = ((float)height / 2f) / maxAbsY;
	}
	
	public void setActiveChannelMask(long m)
	{
		activeChannelMask = m;
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
	
	public void toggleChannel(int c)
	{
		activeChannelMask ^= (1 << c);
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
	
		clickEvents.clear();
		
		for(int c = 0; c < channels.length; c++)
		{
			ArrayList<Float> serie = target.getSeries(channels[c]);
			g.setColor(colorArray[c % 6]);
			int labelHeight = height - 20 *(target.getChannels().length - c + 1);
			g2.drawString(channels[c], 20, labelHeight);
			clickEvents.add(new ClickEvent(14, labelHeight - 4, 8, "channel", c));

			if((activeChannelMask & (1 << c)) > 0)
			{
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
				g2.fillArc(10, labelHeight - 8, 8, 8, 0, 360);
			}
		}
		
		g2.setColor(Color.GRAY);
		g2.setFont(new Font("Arial", Font.BOLD, 20));
		g2.drawString("+", width - 30, height - 50);
		g2.drawArc(width - 34, height - 68, 20, 20, 0, 360);
		clickEvents.add(new ClickEvent(width - 30, height - 65, 10, "zoom", 10));
		g2.drawString("-", width - 28, height - 30);
		g2.drawArc(width - 34, height - 46, 20, 20, 0, 360);
		clickEvents.add(new ClickEvent(width - 30, height - 45, 10, "zoom", -10));
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

	public void mouseClicked(MouseEvent arg0) 
	{
		for(int i = 0; i < clickEvents.size(); i++)
		{
			int dx = clickEvents.get(i).x - arg0.getX();
			int dy = clickEvents.get(i).y - arg0.getY();
			int d = (int)Math.sqrt((dx*dx)+(dy*dy));
			if(d < clickEvents.get(i).r)
			{
				if(clickEvents.get(i).function.equals("zoom"))
				{
					xZoom(clickEvents.get(i).param);
				}
				else if(clickEvents.get(i).function.equals("channel"))
				{
					toggleChannel(clickEvents.get(i).param);
				}
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) 
	{
		
	}

	public void mouseExited(MouseEvent arg0) 
	{
		
	}

	public void mousePressed(MouseEvent arg0) 
	{
		
	}

	public void mouseReleased(MouseEvent arg0) 
	{
		
	}
}
