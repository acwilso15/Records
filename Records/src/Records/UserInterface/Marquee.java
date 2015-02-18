/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.UserInterface;

/**
 *
 * @author Alexander
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * Program which scrolls desired text across the screen in a marquee fashion.
 *
 * @author cbratton
 * @created May 9, 2004
 */
public class Marquee {

	// private static JFrame mainFrame = new JFrame();
	/** The marquee text. */
	private static String marqueeText;

	/** The marquee. */
	private Timer marquee = null;

	/**
	 * Constructor for the Marquee object.
	 *
	 * @param titleLine
	 *            the title line
	 */
	public Marquee(String titleLine) {
		System.out.println("Marquee.Marquee(" + titleLine + ")");
		marqueeText = titleLine + "        ";
		RecordsMain.getUI().CurrentSongLabel.setText(marqueeText);
	}

	/**
	 * Sets the text.
	 *
	 * @param titleLine
	 *            the new text
	 */
	public void setText(String titleLine) {
		System.out.println("Marquee.setText(" + titleLine + ")");
		marqueeText = titleLine + "        ";
	}

	/**
	 * The main program for the Marquee class.
	 */
	void start() {
		System.out.println("Marquee.start()");

		marquee = new Timer(400, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				char firstChar = marqueeText.charAt(0);
				marqueeText = marqueeText.substring(1, marqueeText.length())
						+ firstChar;
				RecordsMain.getUI().CurrentSongLabel.setText(marqueeText);
			}
		});
		marquee.start();
	}

	/**
	 * Stop moving.
	 */
	void stopMoving() {
		System.out.println("Marquee.stopMoving()");
		if (marquee != null) {
			marquee.stop();
		}
	}
}
