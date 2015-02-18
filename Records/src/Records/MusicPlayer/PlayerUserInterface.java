package Records.MusicPlayer;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerUserInterface.
 *
 * @author Alexander
 */
public class PlayerUserInterface {

	/**
	 * Gets the music player.
	 *
	 * @return the mp4
	 */
	public static MusicPlayer getmusicPlayer() {
		return musicPlayer;
	}

	/**
	 * Gets the status.
	 *
	 * @return the Status
	 */
	public static String getStatus() {
		return Status;
	}

	/**
	 * Pause button.
	 */
	public static void PauseButton() {
		System.out.println("PlayerUserInterface.PauseButton()");
		if (getmusicPlayer() != null) {
			getmusicPlayer().pausePlayback();
			setStatus("PAUSED");
			System.out.println("Paused");
		}
	}

	/**
	 * Play button.
	 *
	 * @param CurrentSongPath
	 *            the current song path
	 * @param Location
	 *            the location
	 * @param Title
	 *            the title
	 */
	public static void PlayButton(String CurrentSongPath, String Location,
			String Title) {
		System.out.println("PlayerUserInterface.PlayButton(" + CurrentSongPath
				+ ", " + Location + ", " + Title + ")");

		System.out.println("Now playing " + CurrentSongPath);
		MusicPlayer.setPauseTime(0);
		MusicPlayer.setResumeTime(0);
		MusicPlayer.setTimeWaste(0);
		MusicPlayer.setMostCurrentLocation(Location);
		musicPlayer = new MusicPlayer(CurrentSongPath, Location);
		getmusicPlayer().start();
		setStatus("PLAYING");
	}

	/**
	 * Resume button.
	 */
	public static void ResumeButton() {
		System.out.println("PlayerUserInterface.PauseButton()");
		if (getmusicPlayer() != null) {
			getmusicPlayer().resumePlayback();
			PlayerUserInterface.setStatus("PLAYING");
			System.out.println("Resume");
		}
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	static void setStatus(String status) {
		System.out.println("PlayerUserInterface.setStatus(" + status + ")");
		Status = status;
	}

	/**
	 * Stop button.
	 */
	public static void StopButton() {
		System.out.println("PlayerUserInterface.StopButton()");
		if (getmusicPlayer() != null) {
			getmusicPlayer().close();
			PlayerUserInterface.setStatus("STOPPED");
			System.out.println("Stopped");
		}
	}

	/** The music player. */
	private static MusicPlayer musicPlayer = null;

	/** The Status. */
	private static String Status;
}
