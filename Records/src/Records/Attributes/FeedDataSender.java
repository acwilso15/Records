/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Records.Attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedDataSender.
 *
 * @author Alexander
 */
public class FeedDataSender extends Thread {

  /** The Paths. */
  private ArrayList<FeedDataPaths> Paths;

  /** The save data. */
  private FeedDataSaver saveData;

  /** Instantiates a new feed data sender. */
  public FeedDataSender() {
    System.out.println("FeedDataSender.run()");
    saveData = new FeedDataSaver();
    Paths = new ArrayList<FeedDataPaths>();
    
    // Meaning: Paths.add(new FeedDataKeys(UrlPath, Location, xpathTitle, xpathArtist, InType,
    // RssString, numOfSongs, numVar, Order));
    //extractWebDataToArray(String site, String jsonDocSelect, String jsonDocAttribute,int numOfSongs, String jsonToken, String location, String InType, int numVar, String xPathTitle, String xPathArtist) {  
    
    Paths.add(new FeedDataPaths("Billboard-Pop", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://www.billboard.com/rss/charts/hot-100",null, null, 100, null, "Billboard-Pop", "XML", 2, "rss/channel/item/title", "rss/channel/item/artist"),100));
    Paths.add(new FeedDataPaths("Billboard-EDM", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://www.billboard.com/rss/charts/dance-electronic-songs",null, null, 25, null, "Billboard-Pop", "XML", 2, "rss/channel/item/title", "rss/channel/item/artist"),25));
    Paths.add(new FeedDataPaths("Billboard-Country", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://www.billboard.com/rss/charts/country-songs",null, null, 25, null, "Billboard-Country", "XML", 2, "rss/channel/item/title", "rss/channel/item/artist"),25));
    Paths.add(new FeedDataPaths("Billboard-RnB", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://www.billboard.com/rss/charts/r-b-hip-hop-songs",null, null, 25, null, "Billboard-RnB", "XML", 2, "rss/channel/item/title", "rss/channel/item/artist"),25));
    Paths.add(new FeedDataPaths("Hypem", "Artist_Title", WebsiteDataExtractor.extractWebDataToArray("http://hypem.com/feed/popular/feed.xml",null, null, 20, null, "Hypem", "XML", 1, "rss/channel/item/title", "rss/channel/item/artist"),20));
    Paths.add(new FeedDataPaths("Itunes-Pop", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://itunes.apple.com/us/rss/topsongs/limit=100/xml",null, null, 40, null, "Itunes-Pop", "XML", 1, "feed/entry/title", "feed/entry/title"),40));
    Paths.add(new FeedDataPaths("Itunes-Country", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://itunes.apple.com/us/rss/topsongs/limit=100/genre=6/xml",null, null, 40, null, "Itunes-Country", "XML", 1, "feed/entry/title", "feed/entry/title"),40));
    Paths.add(new FeedDataPaths("Itunes-RnB", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://itunes.apple.com/us/rss/topsongs/limit=100/genre=15/xml",null, null, 40, null, "Itunes-RnB", "XML", 1, "feed/entry/title", "feed/entry/title"),40));
    Paths.add(new FeedDataPaths("Itunes-EDM", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://itunes.apple.com/us/rss/topsongs/limit=100/genre=7/xml",null, null, 40, null, "Itunes-EDM", "XML", 1, "feed/entry/title", "feed/entry/title"),40));

    Paths.add(new FeedDataPaths("UK40", "Title_Artist", WebsiteDataExtractor.extractWebDataToArray("http://www.bbc.co.uk/radio1/chart/singles","div[class = p-f p-f-v1 p-f-variant-small p-f-p_playlister  p-f-lang-en-gb]", "data-title", 40, "||", "UK40", "JSON", 2, null, null),40));
    Paths.add(new FeedDataPaths("Shazam", "Artist_Title", WebsiteDataExtractor.extractWebDataToArray("http://www.shazam.com/charts/us_top_100","img[itemprop=image]", "alt", 100, "-", "Shazam", "JSON", 2, null, null),100));
    Paths.add(new FeedDataPaths("Beatport", "Artist_Title", WebsiteDataExtractor.extractWebDataToArray("http://classic.beatport.com/top-100","span[class = play-queue play-queue-med-small]", "data-json", 100, "-", "Beatport", "JSON", 2, null, null),100));
  }

  @Override
  public void run() {
    System.out.println("FeedDataSender.startExtraction()");
    for (FeedDataPaths key : Paths) {
      saveData.readFeeds(key);
    }
    Collection c = RecordsMain.getFeedSongHash().values();
    
    //obtain an Iterator for Collection
    Iterator itr = c.iterator();
   
    //iterate through HashMap values iterator
    while(itr.hasNext()){
      ((FeedDataStore) itr.next()).insertFeedData();
    }
    System.out.println("RSS Import Complete!");
  }
}
