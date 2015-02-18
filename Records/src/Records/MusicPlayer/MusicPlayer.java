package Records.MusicPlayer;

/**
 *
 * @author Alexander
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import net.sourceforge.jaad.spi.javasound.AACAudioFileReader;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class MusicPlayer.
 */
public class MusicPlayer extends Thread {

	/**
	 * Gets the elapsed.
	 *
	 * @return the elapsed
	 */
	private static float getElapsed() {
		return elapsed;
	}

	/**
	 * Gets the most current location.
	 *
	 * @return the MostCurrentLocation
	 */
	private static String getMostCurrentLocation() {
		return MostCurrentLocation;
	}

	/**
	 * Gets the play preview.
	 *
	 * @param Path
	 *            the path
	 * @return the play preview
	 */
	private static String getPlayPreview(String Path) {
		System.out.println("MusicPlayer.getPlayPreview(" + Path + ")");
		String previewRedirect = null;
		try {
			URLConnection con = new URL(Path).openConnection();
			// System.out.println("Orignal URL: " + con.getURL());
			con.connect();
			try (InputStream is = con.getInputStream()) {
				URL Redirect = con.getURL();
				previewRedirect = Redirect.toString();
				// System.out.println("Redirected URL: " + Redirect.toString());
				is.close();
			} catch (IOException ex) {
				Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE,
						null, ex);
			}
			PlayerUserInterface.setStatus("PLAYING");
		} catch (MalformedURLException ex) {
			Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return previewRedirect;
	}

	/**
	 * Sets the elapsed.
	 *
	 * @param aElapsed
	 *            the elapsed to set
	 */
	private static void setElapsed(float aElapsed) {
		elapsed = aElapsed;
	}

	/**
	 * Sets the most current location.
	 *
	 * @param aMostCurrentLocation
	 *            the MostCurrentLocation to set
	 */
	static void setMostCurrentLocation(String aMostCurrentLocation) {
		MostCurrentLocation = aMostCurrentLocation;
	}

	/**
	 * Sets the pause time.
	 *
	 * @param aPauseTime
	 *            the PauseTime to set
	 */
	static void setPauseTime(long aPauseTime) {
		PauseTime = aPauseTime;
	}

	/**
	 * Sets the resume time.
	 *
	 * @param aResumeTime
	 *            the ResumeTime to set
	 */
	static void setResumeTime(long aResumeTime) {
		ResumeTime = aResumeTime;
	}

	/**
	 * Sets the time waste.
	 *
	 * @param aTimeWaste
	 *            the TimeWaste to set
	 */
	static void setTimeWaste(long aTimeWaste) {
		TimeWaste = aTimeWaste;
	}

	/** The playback line. */
	private SourceDataLine playbackLine;

	/** The sources. */
	private String filename;

	private String sources;

	/** The get time. */
	private boolean timing = false;

	/** The UR l_ artist. */
	private static String EndDur, MostCurrentLocation, Dur, Time;

	/** The Duration timer. */
	private int DurationTimer;

	/** The percent. */
	private static float elapsed, percent;

	/** The Time waste. */
	private static long startTime = 0, PauseTime, ResumeTime, TimeWaste;

	/**
	 * Instantiates a new music player.
	 *
	 * @param CurrentSongPath
	 *            the current song path
	 * @param Location
	 *            the location
	 */
	MusicPlayer(String CurrentSongPath, String Location) {
		this.setDurationTimer(0);
		this.filename = CurrentSongPath;
		this.sources = Location;
		getDuration();
	}

	/**
	 * Close.
	 */
	public void close() {
		System.out.println("MusicPlayer.close()");

		if (playbackLine != null) {
			playbackLine.stop();
			playbackLine.drain();
			playbackLine.close();
			playbackLine = null;
		}
		setTiming(false);
		RecordsMain.getUI().PlayTime.setText("0:00 of 0:00");
		RecordsMain.getUI().ProgBar.setValue(0);
	}

	/**
	 * Gets the ais.
	 *
	 * @param soundFile
	 *            the sound file
	 * @return the ais
	 */
	private AudioInputStream getAIS(File soundFile) {
		System.out.println("MusicPlayer.getAIS(" + soundFile.getAbsolutePath()
				+ ")");

		AudioInputStream IS = null;
		if (!(sources.equals("Directory"))) {
			String theURL = getPlayPreview(filename);
			try {
				startTime = System.currentTimeMillis();
				IS = new AACAudioFileReader().getAudioInputStream(new URL(
						theURL));
			} catch (UnsupportedAudioFileException | IOException ex) {
				Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		} else {
			if (filename.contains("mp3")) {
				try {
					startTime = System.currentTimeMillis();
					IS = new MpegAudioFileReader()
							.getAudioInputStream(soundFile);
				} catch (UnsupportedAudioFileException | IOException ex) {
					Logger.getLogger(MusicPlayer.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			} else if (filename.contains("m4a")) {
				try {
					startTime = System.currentTimeMillis();
					IS = new AACAudioFileReader()
							.getAudioInputStream(soundFile);
				} catch (UnsupportedAudioFileException | IOException ex) {
					Logger.getLogger(MusicPlayer.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
		return IS;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	private void getDuration() {
		System.out.println("MusicPlayer.getDuration()");

		if (!(getMostCurrentLocation().equals("Directory"))) {
			setDurationTimer(30);
		} else {
			try {
				AudioFile audioFile = AudioFileIO.read(new File(filename));
				setDurationTimer(audioFile.getAudioHeader().getTrackLength());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int Min = getDurationTimer() / 60;
		int Sec = getDurationTimer() % 60;
		String Secs;

		if (Sec < 10) {
			Secs = "0" + Sec;
		} else {
			Secs = "" + Sec;
		}
		EndDur = Min + ":" + Secs;
	}

	private int getDurationTimer() {
		return DurationTimer;
	}

	/**
	 * Gets the play time.
	 *
	 * @return the play time
	 */
	private void getPlayTime() {
		System.out.println("MusicPlayer.getPlayTime()");

		if (setTiming(true)) {
			setElapsed((System.currentTimeMillis() - startTime) / 1000);
			setElapsed(getElapsed() - TimeWaste);
			percent = (getElapsed() * 100) / getDurationTimer();
			int Min = (int) getElapsed() / 60;
			int Sec = (int) getElapsed() % 60;
			String Secs;

			if (Sec < 10) {
				Secs = "0" + Sec;
			} else {
				Secs = "" + Sec;
			}
			Dur = Min + ":" + Secs;
			Time = Dur + " of " + EndDur;
			RecordsMain.getUI().PlayTime.setText(Time);
			RecordsMain.getUI().ProgBar.setMinorTickSpacing(1);
			RecordsMain.getUI().ProgBar.setValue((int) percent);
		}
	}

	/**
	 * Pause playback.
	 */
	void pausePlayback() {
		System.out.println("MusicPlayer.pausePlayback()");
		if (playbackLine != null) {
			if ("PLAYING".equals(PlayerUserInterface.getStatus())) {
				playbackLine.stop();
			}
			setPauseTime(System.currentTimeMillis()); // time at Pause
			// UserInterfaceUI.PlayTime.setText(Time)
		}
	}

	/**
	 * Resume playback.
	 */
	void resumePlayback() {
		System.out.println("MusicPlayer.resumePlayback()");
		if (playbackLine != null) {
			if ("PAUSED".equals(PlayerUserInterface.getStatus())) {
				setResumeTime((System.currentTimeMillis() - PauseTime) / 1000); // Time
																				// at
																				// Resume
				setTimeWaste(TimeWaste + ResumeTime);
				playbackLine.start();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		System.out.println("MusicPlayer.run()");
		try {
			setTiming(true);
			AudioInputStream AIS = null;
			AudioFormat myFormat = null;
			File soundFile = new File(filename);

			AIS = getAIS(soundFile);
			myFormat = AIS.getFormat();

			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					myFormat, AudioSystem.NOT_SPECIFIED);

			if (!AudioSystem.isLineSupported(info)) {
				AudioFormat decodedFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						myFormat.getSampleRate(),
						16, // sample size in bits
						myFormat.getChannels(), myFormat.getChannels() * 2,
						myFormat.getSampleRate(), false);
				AIS = AudioSystem.getAudioInputStream(decodedFormat, AIS);
				myFormat = AIS.getFormat();
			}
			playbackLine = AudioSystem.getSourceDataLine(myFormat);
			playbackLine.open(myFormat);
			int frameSizeInBytes = myFormat.getFrameSize();
			int bufferLengthInFrames = playbackLine.getBufferSize() / 8;
			int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
			byte[] data = new byte[bufferLengthInBytes];
			playbackLine.start();

			PlayerUserInterface.setStatus("PLAYING");
			int nBytesRead;
			try {
				while ((nBytesRead = AIS.read(data)) != -1) {
					int numBytesRemaining = nBytesRead;
					getPlayTime();
					while (numBytesRemaining > 0) {
						numBytesRemaining -= playbackLine.write(data, 0,
								numBytesRemaining);
					}

				}
			} catch (IOException ex) {
				Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE,
						null, ex);
			}
			close();
		} catch (LineUnavailableException ex) {
			Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	private void setDurationTimer(int durationTimer) {
		DurationTimer = durationTimer;
	}

	public boolean isTiming() {
		return timing;
	}

	private boolean setTiming(boolean timing) {
		this.timing = timing;
		return timing;
	}
}
