package com.nic.streamprocessor.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;

import com.nic.streamprocessor.streamtargets.MemoryStreamTarget;

import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SeriesVisualiserFrame extends JFrame
{
	private MemoryStreamTarget target;
	private SeriesVisualiserPanel contentPane;


	/**
	 * Create the frame.
	 */
	public SeriesVisualiserFrame(MemoryStreamTarget t)
	{
		target = t;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new SeriesVisualiserPanel(target);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		contentPane.add(scrollBar, BorderLayout.SOUTH);
	}



	


}
