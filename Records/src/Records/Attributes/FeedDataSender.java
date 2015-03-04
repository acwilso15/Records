/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedDataSender.
 *
 * @author Alexander
 */
public class FeedDataSender extends Thread {

  /** The keys. */
  private ArrayList<FeedDataPaths> keys;

  /** The save data. */
  private FeedDataSaver saveData;

  /**
   * Instantiates a new feed data sender.
   */
  public FeedDataSender() {
    System.out.println("FeedDataSender.run()");
    saveData = new FeedDataSaver();
    keys = new ArrayList<>();

    // Meaning: keys.add(new FeedDataKeys(UrlPath, Location, xpathTitle, xpathArtist, InType, RssString, numOfSongs, numVar, Order));

    keys.add(new FeedDataPaths("http://www.billboard.com/rss/charts/hot-100", "Billboard-Pop",
        "rss/channel/item/title", "rss/channel/item/artist", "XML", null, 100, 2, null));

    keys.add(new FeedDataPaths("http://www.billboard.com/rss/charts/dance-electronic-songs",
        "Billboard-EDM", "rss/channel/item/title", "rss/channel/item/artist", "XML", null, 25, 2, null));
    
    keys.add(new FeedDataPaths("http://www.billboard.com/rss/charts/country-songs",
        "Billboard-Country", "rss/channel/item/title", "rss/channel/item/artist", "XML", null, 25, 2, null));

    keys.add(new FeedDataPaths("http://www.billboard.com/rss/charts/r-b-hip-hop-songs",
        "Billboard-RnB", "rss/channel/item/title", "rss/channel/item/artist", "XML", null, 25, 2, null));

    keys.add(new FeedDataPaths("http://hypem.com/feed/popular/feed.xml", "Hypem",
        "rss/channel/item/title", "rss/channel/item/title", "XML", null, 20, 1, "Artist_Title"));

    keys.add(new FeedDataPaths("https://itunes.apple.com/us/rss/topsongs/limit=100/xml",
        "Itunes-Pop", "feed/entry/title", "feed/entry/title", "XML", null, 40, 1, "Title_Artist"));

    keys.add(new FeedDataPaths("https://itunes.apple.com/us/rss/topsongs/limit=100/genre=6/xml",
        "Itunes-Country", "feed/entry/title", "feed/entry/title", "XML", null, 40, 1, "Title_Artist"));

    keys.add(new FeedDataPaths("https://itunes.apple.com/us/rss/topsongs/limit=100/genre=15/xml",
        "Itunes-RnB", "feed/entry/title", "feed/entry/title", "XML", null, 40, 1, "Title_Artist"));

    keys.add(new FeedDataPaths("https://itunes.apple.com/us/rss/topsongs/limit=100/genre=7/xml",
        "Itunes-EDM", "feed/entry/title", "feed/entry/title", "XML", null, 40, 1, "Title_Artist"));

    keys.add(new FeedDataPaths("http://www.bbc.co.uk/radio1/chart/singles", "UK40", null,
        null, "String", WebsiteDataExtractor.extractWebDataToArray(
            "http://www.bbc.co.uk/radio1/chart/singles", "div[class = p-f p-f-v1 p-f-variant-small p-f-p_playlister  p-f-lang-en-gb]",
            "data-title", 40, "||", "UK40"), 40, 2, "Title_Artist"));

    keys.add(new FeedDataPaths("http://www.shazam.com/charts/us_top_100", "Shazam", null,
        null, "String", WebsiteDataExtractor.extractWebDataToArray(
            "http://www.shazam.com/charts/us_top_100", "img[itemprop=image]", "alt", 100, "-", "Shazam"), 100, 2, "Artist_Title"));

    keys.add(new FeedDataPaths("http://classic.beatport.com/top-100", "Beatport", null, null,
        "String", WebsiteDataExtractor.extractWebDataToArray("http://classic.beatport.com/top-100",
            "span[class = play-queue play-queue-med-small]", "data-json", 100, "-", "Beatport"), 100, 2, "Artist_Title"));

  }
  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  @Override
  public void run() {
    System.out.println("FeedDataSender.startExtraction()");
    for (FeedDataPaths key : keys) {
      saveData.readFeeds(key);
    }
    for (int y = 0; y < saveData.getFeedData().size(); y++) {
      saveData.getFeedData().get(y).insertFeedData();
    }
    System.out.println("RSS Import Complete!");
  }
}
