/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.UserInterface;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class AlbumCoverReplacer.
 *
 * @author Alexander
 */
class AlbumCoverReplacer extends Thread {

	/**
	 * Sets the image.
	 *
	 * @param FilePath
	 *            the new image
	 */
	private static void setImage(String FilePath) {
		System.out.println("AlbumCoverReplacer.setImage(" + FilePath + ")");
		BufferedImage img;
		Image newimg;
		try {
			img = ImageIO.read(new File(FilePath));
			newimg = img.getScaledInstance(174, 174,
					java.awt.Image.SCALE_SMOOTH);
			Icon images = new ImageIcon(newimg);
			RecordsMain.getUI().albumCover.setIcon(images);
		} catch (IOException ex) {
			Logger.getLogger(AlbumCoverReplacer.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/** The location. */
	private final String location;

	/** The image. */
	private String image;

	/** The previous image. */
	private String previousImage;

	/** The running. */
	private static boolean running;

	/**
	 * Instantiates a new album cover replacer.
	 *
	 * @param loc
	 *            the loc
	 * @param pic
	 *            the pic
	 */
	AlbumCoverReplacer(String loc, String pic) {
		System.out.println("AlbumCoverReplacer.AlbumCoverReplacer(" + loc
				+ ", " + pic + ")");
		location = loc;
		image = pic;
		previousImage = "";
	}

	/**
	 * Close.
	 */
	void close() {
		System.out.println("AlbumCoverReplacer.close()");
		running = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		System.out.println("AlbumCoverReplacer.run()");
		running = true;
		while (running) {
			if (!location.contains("Directory")) {
				if (location.contains("Billboard")) {
					image = "/Records/Images/Billboard-Icon.png";
				} else if (location.contains("Hypem")) {
					image = "/Records/Images/Hype-Machine-Icon.png";
				} else if (location.contains("Itunes")) {
					image = "/Records/Images/ITunes-Icon.png";
				} else if (location.contains("UK40")) {
					image = "/Records/Images/UK40-Icon.png";
				} else if (location.contains("Shazam")) {
					image = "/Records/Images/Shazam-Icon.png";
				}
				if (!previousImage.equals(image)) {
					setImage(image);
					previousImage = image;
					if (!RecordsMain.getUI().itunesDownloadButton.isVisible()) {
						RecordsMain.getUI().itunesDownloadButton
								.setVisible(false);
						RecordsMain.getUI().amazonDownloadButton
								.setVisible(false);
					}
				}
			}
			setImage(image);
			running = false;
		}
	}
}
