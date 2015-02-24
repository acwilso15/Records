package Records.MusicPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;

import Records.Main.RecordsMain;

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
   * @param location          the location
   * @param title          the title
   * @param artist the artist
   */
  public static void PlayButton(String location, String title, String artist) {
    ResultSet result = getPlayerSongPathway(title, artist, location);
    try {
      if (result.next()) {
        String currentSongPath = result.getString("FilePathway").replaceAll("~", "'");
        System.out.println("PlayerUserInterface.PlayButton(" + currentSongPath + ", " + location
            + ", " + title + ")");

        System.out.println("Now playing " + currentSongPath);
        MusicPlayer.setPauseTime(0);
        MusicPlayer.setResumeTime(0);
        MusicPlayer.setTimeWaste(0);
        MusicPlayer.setMostCurrentLocation(location);
        musicPlayer = new MusicPlayer(currentSongPath, location);
        getmusicPlayer().start();
        setStatus("PLAYING");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
   *          the new status
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

  /**
   * Gets the player song pathway.
   *
   * @param song the song
   * @param artist the artist
   * @param Loc the loc
   * @return the player song pathway
   */
  public static ResultSet getPlayerSongPathway(String song, String artist, String Loc) {
    ResultSet result;
    if ("Directory".equals(Loc)) {
      result = RecordsMain.dba.getLibFilePathway(song, artist);
    } else {
      result = RecordsMain.dba.getFilePathway(song, artist);
    }
    return result;
  }

  /** The music player. */
  private static MusicPlayer musicPlayer = null;

  /** The Status. */
  private static String Status;
}
