package com.me;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class getVerifyCode {
	String result = null;
	boolean ok = false;
	Frame frame = null;
	TextField text = null;
	Panel p = null;
	Image img = null;

	public getVerifyCode() {
		frame = new Frame();
		frame.setSize(500, 200);
		text = new TextField();
		text.setSize(100, 20);
		frame.add(text);
		frame.setLocation(500, 400);
		frame.setResizable(false);
		p = new Panel() {
			@Override
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, null);
				super.paint(g);
			}
		};
		p.setSize(100, 40);
		frame.add(p);
		// p.setLocation(0, 50);
		text.setLocation(400, 50);
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ok = true;
					text.removeKeyListener(this);
					result = text.getText();
					super.keyPressed(e);
				}
			}

		});
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				ok = true;
				super.windowClosing(e);
			}
		});
	}

	public String getcode(String url) {

		URL u = null;
		try {
			u = new URL(url);
			// img = frame.createImage((ImageProducer)u.getContent());
			img = ImageIO.read(u);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setVisible(true);
		frame.repaint();
		try {

			while (!ok) {
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.dispose();
		return result;
	}
}
