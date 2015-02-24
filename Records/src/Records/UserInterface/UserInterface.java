package Records.UserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Records.Attributes.CheckboxModifier;
import Records.Database.LibraryUpdaterImp;
import Records.Main.RecordsMain;
import Records.MusicPlayer.PlayerUserInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class UserInterface.
 */
public class UserInterface extends JFrame {

  /**
   * Gets the current path.
   *
   * @return the current path
   */
  private static String getCurrentPath() {
    return CurrentPath;
  }

  /**
   * Sets the current path.
   *
   * @param result
   *          the new current path
   */
  private static void setCurrentPath(ResultSet result) {
    try {
      if (result.next()) {
        CurrentPath = result.getString("FilePathway").replaceAll("~", "'");
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /** The content pane. */
  private JPanel contentPane;

  /** The title text field. */
  private JTextField titleTextField;

  /** The artist text field. */
  private JTextField artistTextField;

  /** The album text field. */
  private JTextField albumTextField;

  /** The genre text field. */
  private JTextField genreTextField;

  /** The location text field. */
  private JTextField locationTextField;

  /** The bpm text field. */
  private JTextField bpmTextField;

  /** The key text field. */
  private JTextField keyTextField;

  /** The Play time. */
  public JTextField PlayTime;

  /** The Search text field. */
  private JTextField SearchTextField;

  /** The user library. */
  private JTable userLibrary;

  /** The song pref table. */
  public JTable songPrefTable;

  /** The rss charts table. */
  public JTable rssChartsTable;

  /** The Current song. */
  private static String CurrentPath, CurrentSong;

  /** The Switch. */
  private AlbumCoverReplacer Switch;

  /** The Pause button. */
  private JButton PauseButton;

  /** The Play button. */
  private JButton PlayButton;

  /** The Select all check box. */
  private JCheckBox SelectAllCheckBox;

  /** The Billboard checkbox. */
  public JCheckBox BillboardCheckbox;

  /** The Billboard edm checkbox. */
  public JCheckBox BillboardEDMCheckbox;

  /** The Beatport checkbox. */
  public JCheckBox BeatportCheckbox;

  /** The Itunes edm checkbox. */
  public JCheckBox ItunesEDMCheckbox;

  /** The Billboard country checkbox. */
  public JCheckBox BillboardCountryCheckbox;

  /** The Itunes country checkbox. */
  public JCheckBox ItunesCountryCheckbox;

  /** The Billboard rn b checkbox. */
  public JCheckBox BillboardRnBCheckbox;

  /** The Itunes rn b checkbox. */
  public JCheckBox ItunesRnBCheckbox;

  /** The Hype checkbox. */
  public JCheckBox hypemCheckbox;

  /** The UK checkbox. */
  public JCheckBox UKCheckbox;

  /** The Itunes checkbox. */
  public JCheckBox ItunesCheckbox;

  /** The Shazam checkbox. */
  public JCheckBox ShazamCheckbox;

  /** The itunes download button. */
  JButton itunesDownloadButton;

  /** The amazon download button. */
  JButton amazonDownloadButton;

  /** The Based on. */
  public JLabel BasedOn;

  /** The tabbed pane_1. */
  public JTabbedPane recommendationsTabPane;

  /** The Current song label. */
  JLabel CurrentSongLabel;

  /** The chart song num. */
  public JLabel chartSongNum;

  /** The Prog bar. */
  public JSlider ProgBar;

  /** The album cover. */
  JLabel albumCover;

  /** The Num songs. */
  public JLabel NumSongs;

  /** The mn help. */
  private JMenu mnHelp;

  /** The mntm user guide. */
  private JMenuItem mntmUserGuide;

  /** The setting tab pane. */
  private JTabbedPane settingTabPane;

  /**
   * Creates new form UserInterface.
   */
  public UserInterface() {
    setResizable(false);
    initAllGUIComponents();
    setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "clickButton");

    getRootPane().getActionMap().put("clickButton", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        if (null != PlayerUserInterface.getStatus()) {
          switch (PlayerUserInterface.getStatus()) {
          case "PLAYING":
            PauseButton.doClick();
            // System.out.println("Play button clicked");
            break;
          case "PAUSED":
            PlayButton.doClick();
            // System.out.println("Pause button clicked");
            break;
          }
        }
      }
    });

    Action doNothing = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // do nothing
      }
    };

    getUserLibrary().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "doNothing");
    getUserLibrary().getActionMap().put("doNothing", doNothing);
    songPrefTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "doNothing");
    songPrefTable.getActionMap().put("doNothing", doNothing);
    rssChartsTable.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "doNothing");
    rssChartsTable.getActionMap().put("doNothing", doNothing);

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
    this.setLocation(x, y);
  }

  /**
   * Begin marquee.
   *
   * @param songLine
   *          the song line
   */
  private void beginMarquee(String songLine) {
    RecordsMain.marqTest.setText(songLine);
    if (!(songLine.length() > 26)) {
      RecordsMain.marqTest.stopMoving();
      CurrentSongLabel.setText(songLine);
    } else {
      RecordsMain.marqTest.stopMoving();
      RecordsMain.marqTest.start();
    }
  }

  /**
   * Gets the user library.
   *
   * @return the user library
   */
  public JTable getUserLibrary() {
    return userLibrary;
  }

  /**
   * Create the frame.
   */
  private void initAllGUIComponents() {
    setTitle("Records: The Premier Music Discovery Software");
    setPreferredSize(new Dimension(1341, 663));
    setMinimumSize(new Dimension(1341, 663));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1341, 732);

    initMenuBar();
    contentPane = new JPanel();
    contentPane.setBackground(new Color(30, 144, 255));
    contentPane.setPreferredSize(new Dimension(1341, 663));
    contentPane.setMinimumSize(new Dimension(1341, 663));
    contentPane.setBorder(null);
    setContentPane(contentPane);
    contentPane.setLayout(null);

    initSoftwareNameLbl();
    initAlbumCover();
    initSongTitleLbl();
    initSongArtistLbl();
    initSongAlbumLbl();
    initSongGenreLbl();
    initSongLocationLbl();
    initSongBPMLbl();
    initSongKeyLbl();

    initSongTitleField();
    initSongArtistField();
    initSongAlbumField();
    initSongGenreField();
    initSongLocationField();
    initSongBPMField();
    initSongKeyField();

    initAmazonDLButton();
    initItunesDLButton();

    initSongInfoFrameLbl();

    initCurrentSongLbl();

    initMediaSlider();
    initPlayTimerField();
    initPlayButton();
    initPauseButton();
    initStopButton();
    initMediaPlayerFrameLbl();

    initRecSettingslbl();

    initSelectAllCheckbox();

    initSettingsTabPane();
    initPopTab();
    initCountryTab();
    initRnBTab();
    initElectronicTab();

    initYourRecommendationsLbl();
    initNumberOfChartSongsLbl();
    initRecTabPane();
    initSongBasedOnLbl();
    initRecommendationFrameLbl();

    initYourLibraryLbl();
    initNumLibrarySongsLbl();
    initSearchBar();
    initUserLibrary();
    initUserLibraryFrameLbl();

    initFeedbackBtn();
  }

  /**
   * Inits the menu bar.
   */
  private void initMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    menuBar.setAlignmentX(Component.LEFT_ALIGNMENT);
    setJMenuBar(menuBar);
    initMenuFileItem(menuBar);
    initMenuHelpItem();
  }

  /**
   * Inits the menu file item.
   *
   * @param menuBar the menu bar
   */
  private void initMenuFileItem(JMenuBar menuBar) {
    JMenu mnNewMenu = new JMenu("File");
    menuBar.add(mnNewMenu);

    JMenuItem Import_Library_Menu_Item = new JMenuItem("Import Library");
    Import_Library_Menu_Item.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        LibraryUpdaterImp.startLibrary();
      }
    });
    mnNewMenu.add(Import_Library_Menu_Item);
  }

  /**
   * Inits the menu help item.
   */
  private void initMenuHelpItem() {
    mnHelp = new JMenu("Help");

    mntmUserGuide = new JMenuItem("User Guide");
    mntmUserGuide.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO add your handling code here:
        try {
          Runtime.getRuntime().exec(
              "rundll32 url.dll,FileProtocolHandler "
                  + "C:\\Users\\Alexander\\MuRec\\UserGuide.pdf");
        } catch (IOException ex) {
          JOptionPane.showMessageDialog(null, "Error"); // print the
          // error
        }
      }
    });
    mnHelp.add(mntmUserGuide);
  }

  /**
   * Inits the feedback btn.
   */
  private void initFeedbackBtn() {
    JButton feedbackBtn = new JButton("Feedback");
    feedbackBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        RecordsMain.dialog.setVisible(true);
      }
    });
    feedbackBtn.setBounds(1236, 21, 89, 23);
    contentPane.add(feedbackBtn);
  }

  /**
   * Inits the user library frame lbl.
   */
  private void initUserLibraryFrameLbl() {
    JLabel LibraryBox = new JLabel("");
    LibraryBox.setOpaque(true);
    LibraryBox.setBorder(new LineBorder(Color.DARK_GRAY, 4));
    LibraryBox.setBounds(20, 262, 650, 409);
    contentPane.add(LibraryBox);
  }

  /**
   * Inits the user library.
   */
  private void initUserLibrary() {
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(36, 308, 620, 351);
    contentPane.add(scrollPane);

    userLibrary = new JTable();
    getUserLibrary().addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent evt) {
        userLibraryKeyPressedAction(evt);
      }
    });
    getUserLibrary().addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        userLibraryMouseClickedAction(evt);
      }
    });

    getUserLibrary().setAutoCreateRowSorter(true);
    getUserLibrary().setAlignmentY(Component.TOP_ALIGNMENT);
    getUserLibrary().setModel(
        new DefaultTableModel(new Object[][] {}, new String[] { "Song Title", "Artist", "Album",
            "Genre", "Energy", "Dance", "Tone", "Tempo", "Key" }) {
          boolean[] columnEditables = new boolean[] { false, false, false, false, false, false,
              false, false, false };

          @Override
          public boolean isCellEditable(int row, int column) {
            return columnEditables[column];
          }
        });
    scrollPane.setViewportView(getUserLibrary());
  }

  /**
   * Inits the search bar.
   */
  private void initSearchBar() {
    SearchTextField = new JTextField();
    SearchTextField.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent evt) {
        // TODO add your handling code here:
        searchBarKeyPressedAction(evt);
      }
    });
    SearchTextField.setBorder(null);
    SearchTextField.setBounds(503, 274, 152, 17);
    contentPane.add(SearchTextField);
    SearchTextField.setColumns(10);

    JLabel search = new JLabel("Search:");
    search.setFont(new Font("Arial", Font.BOLD, 14));
    search.setBounds(440, 267, 60, 33);
    contentPane.add(search);

    JLabel SearchboxWhiteLabel = new JLabel("");
    SearchboxWhiteLabel.setBorder(new LineBorder(Color.DARK_GRAY, 2));
    SearchboxWhiteLabel.setOpaque(true);
    SearchboxWhiteLabel.setBackground(Color.WHITE);
    SearchboxWhiteLabel.setBounds(497, 271, 163, 23);
    contentPane.add(SearchboxWhiteLabel);

    JLabel SearchLabel = new JLabel("");
    SearchLabel.setOpaque(true);
    SearchLabel.setBackground(new Color(255, 153, 0));
    SearchLabel.setBorder(new LineBorder(Color.DARK_GRAY, 4));
    SearchLabel.setBounds(430, 262, 240, 40);
    contentPane.add(SearchLabel);
  }

  /**
   * Inits the num library songs lbl.
   */
  private void initNumLibrarySongsLbl() {
    NumSongs = new JLabel("");
    NumSongs.setFont(new Font("Arial", Font.BOLD, 12));
    NumSongs.setBounds(143, 267, 98, 33);
    contentPane.add(NumSongs);
  }

  /**
   * Inits the your library lbl.
   */
  private void initYourLibraryLbl() {
    JLabel yourLibraryLbl = new JLabel("Your Library");
    yourLibraryLbl.setFont(new Font("Arial", Font.BOLD, 14));
    yourLibraryLbl.setBounds(36, 267, 184, 33);
    contentPane.add(yourLibraryLbl);
  }

  /**
   * Inits the recommendation frame lbl.
   */
  private void initRecommendationFrameLbl() {
    JLabel RecommendationBox = new JLabel("");
    RecommendationBox.setOpaque(true);
    RecommendationBox.setBorder(new LineBorder(Color.DARK_GRAY, 4));
    RecommendationBox.setBounds(675, 93, 650, 578);
    contentPane.add(RecommendationBox);
  }

  /**
   * Inits the song based on lbl.
   */
  private void initSongBasedOnLbl() {
    BasedOn = new JLabel("");
    BasedOn.setHorizontalAlignment(SwingConstants.LEFT);
    BasedOn.setBounds(840, 287, 330, 20);
    contentPane.add(BasedOn);
  }

  /**
   * Inits the rss charts table.
   */
  private void initRssChartsTable() {
    rssChartsTable = new JTable();
    rssChartsTable.setAutoCreateRowSorter(true);
    rssChartsTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent evt) {
        rssChartTableKeyTypedAction(evt);
      }
    });
    rssChartsTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        rssChartTableMouseClickedAction(evt);
      }
    });
    rssChartsTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Song Title",
        "Artist", "Album", "Genre", "Tempo", "Key", "Source" }) {
      boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

      @Override
      public boolean isCellEditable(int row, int column) {
        return columnEditables[column];
      }
    });
  }

  /**
   * Inits the song pref table.
   */
  private void initSongPrefTable() {
    songPrefTable = new JTable();
    songPrefTable.setAutoCreateRowSorter(true);
    songPrefTable.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent evt) {
        // TODO add your handling code here:
        songPrefTableKeyPressedAction(evt);
      }
    });
    songPrefTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        // TODO add your handling code here:
        songPrefTableMouseClickedAction(evt);
      }
    });
    songPrefTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Song Title",
        "Artist", "Album", "Genre", "Tempo", "Key", "Source" }) {
      boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

      @Override
      public boolean isCellEditable(int row, int column) {
        return columnEditables[column];
      }
    });
  }

  /**
   * Inits the rec tab pane.
   */
  private void initRecTabPane() {
    recommendationsTabPane = new JTabbedPane(SwingConstants.TOP);
    recommendationsTabPane.setBounds(690, 284, 619, 375);
    contentPane.add(recommendationsTabPane);

    JScrollPane scrollPane_1 = new JScrollPane();
    JScrollPane scrollPane_2 = new JScrollPane();

    initSongPrefTable();
    initRssChartsTable();

    scrollPane_1.setViewportView(songPrefTable);
    scrollPane_2.setViewportView(rssChartsTable);

    recommendationsTabPane.addTab("Recommendations", null, scrollPane_1, null);
    recommendationsTabPane.addTab("Charts", null, scrollPane_2, null);
    recommendationsTabPane.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent arg0) {
        chartSongNumPluralSwitch();
      }
    });
  }

  /**
   * Inits the number of chart songs lbl.
   */
  private void initNumberOfChartSongsLbl() {
    chartSongNum = new JLabel("0 Songs");
    chartSongNum.setFont(new Font("Arial", Font.BOLD, 12));
    chartSongNum.setHorizontalTextPosition(SwingConstants.RIGHT);
    chartSongNum.setHorizontalAlignment(SwingConstants.RIGHT);
    chartSongNum.setBounds(1215, 287, 92, 20);
    contentPane.add(chartSongNum);
  }

  /**
   * Inits the your recommendations lbl.
   */
  private void initYourRecommendationsLbl() {
    JLabel yourRecommendationsLbl = new JLabel("Your Recommendations");
    yourRecommendationsLbl.setFont(new Font("Arial", Font.BOLD, 14));
    yourRecommendationsLbl.setBounds(690, 257, 184, 33);
    contentPane.add(yourRecommendationsLbl);
  }

  /**
   * Inits the itunes edm checkbox.
   */
  private void initItunesEDMCheckbox() {
    ItunesEDMCheckbox = new JCheckBox("Itunes EDM Top 40");
    ItunesEDMCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    ItunesEDMCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the beatport checkbox.
   */
  private void initBeatportCheckbox() {
    BeatportCheckbox = new JCheckBox("Beatport Top 100");
    BeatportCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    BeatportCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the billboard edm checkbox.
   */
  private void initBillboardEDMCheckbox() {
    BillboardEDMCheckbox = new JCheckBox("Billboard EDM Top 25");
    BillboardEDMCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    BillboardEDMCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the electronic tab.
   *
   * @return the j panel
   */
  private JPanel initElectronicTab() {
    JPanel ElectronicPane = new JPanel();
    FlowLayout flowLayout_3 = (FlowLayout) ElectronicPane.getLayout();
    flowLayout_3.setAlignment(FlowLayout.LEFT);
    ElectronicPane.setBackground(SystemColor.scrollbar);
    initBillboardEDMCheckbox();
    initBeatportCheckbox();
    initItunesEDMCheckbox();
    ElectronicPane.add(BillboardEDMCheckbox);
    ElectronicPane.add(BeatportCheckbox);
    ElectronicPane.add(ItunesEDMCheckbox);
    settingTabPane.addTab("Electronic Charts", null, ElectronicPane, null);
    return ElectronicPane;
  }

  /**
   * Inits the itunes rn b checkbox.
   */
  private void initItunesRnBCheckbox() {
    ItunesRnBCheckbox = new JCheckBox("Itunes R&B Top 40");
    ItunesRnBCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    ItunesRnBCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the billboard rn b checkbox.
   */
  private void initBillboardRnBCheckbox() {
    BillboardRnBCheckbox = new JCheckBox("Billboard R&B Top 25");
    BillboardRnBCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    BillboardRnBCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the rn b tab.
   */
  private void initRnBTab() {
    JPanel RnBPane = new JPanel();
    FlowLayout flowLayout_2 = (FlowLayout) RnBPane.getLayout();
    flowLayout_2.setAlignment(FlowLayout.LEFT);
    RnBPane.setBackground(SystemColor.scrollbar);
    initBillboardRnBCheckbox();
    initItunesRnBCheckbox();
    RnBPane.add(BillboardRnBCheckbox);
    RnBPane.add(ItunesRnBCheckbox);
    settingTabPane.addTab("R&B Charts", null, RnBPane, null);
  }

  /**
   * Inits the itunes country checkbox.
   */
  private void initItunesCountryCheckbox() {
    ItunesCountryCheckbox = new JCheckBox("Itunes Country Top 40");
    ItunesCountryCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    ItunesCountryCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the billboard country checkbox.
   */
  private void initBillboardCountryCheckbox() {
    BillboardCountryCheckbox = new JCheckBox("Billboard Country Top 25");
    BillboardCountryCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    BillboardCountryCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the country tab.
   */
  private void initCountryTab() {
    JPanel CountryPane = new JPanel();
    FlowLayout flowLayout_1 = (FlowLayout) CountryPane.getLayout();
    flowLayout_1.setAlignment(FlowLayout.LEFT);
    CountryPane.setBackground(SystemColor.scrollbar);
    initBillboardCountryCheckbox();
    initItunesCountryCheckbox();
    CountryPane.add(BillboardCountryCheckbox);
    CountryPane.add(ItunesCountryCheckbox);
    settingTabPane.addTab("Country Charts", null, CountryPane, null);
  }

  /**
   * Inits the uk checkbox.
   */
  private void initUKCheckbox() {
    UKCheckbox = new JCheckBox("UK Official Top 40 Chart");
    UKCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    UKCheckbox.setHorizontalTextPosition(SwingConstants.RIGHT);
    UKCheckbox.setHorizontalAlignment(SwingConstants.RIGHT);
    UKCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the hypem checkbox.
   */
  private void initHypemCheckbox() {
    hypemCheckbox = new JCheckBox("Hype Machine Top 40 Chart");
    hypemCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(final ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    hypemCheckbox.setHorizontalTextPosition(SwingConstants.RIGHT);
    hypemCheckbox.setHorizontalAlignment(SwingConstants.RIGHT);
    hypemCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the shazam checkbox.
   */
  private void initShazamCheckbox() {
    ShazamCheckbox = new JCheckBox("Shazam Top 100 Chart");
    ShazamCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(final ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    ShazamCheckbox.setHorizontalTextPosition(SwingConstants.RIGHT);
    ShazamCheckbox.setHorizontalAlignment(SwingConstants.RIGHT);
    ShazamCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the itunes checkbox.
   */
  private void initItunesCheckbox() {
    ItunesCheckbox = new JCheckBox("Itunes Top 40 Chart");
    ItunesCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        CheckboxModifier.locateRssCharts();
      }
    });
    ItunesCheckbox.setHorizontalTextPosition(SwingConstants.RIGHT);
    ItunesCheckbox.setHorizontalAlignment(SwingConstants.RIGHT);
    ItunesCheckbox.setBackground(SystemColor.scrollbar);
  }

  /**
   * Inits the billboard checkbox.
   */
  private void initBillboardCheckbox() {
    BillboardCheckbox = new JCheckBox("Billboard Top 100 Chart");
    BillboardCheckbox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent arg0) {
        CheckboxModifier.locateRssCharts();
      }
    });
    BillboardCheckbox.setBackground(SystemColor.scrollbar);
    BillboardCheckbox.setHorizontalTextPosition(SwingConstants.RIGHT);
    BillboardCheckbox.setHorizontalAlignment(SwingConstants.RIGHT);
  }

  /**
   * Inits the pop tab.
   */
  private void initPopTab() {
    JPanel popTab = new JPanel();
    FlowLayout flowLayout = (FlowLayout) popTab.getLayout();
    flowLayout.setAlignOnBaseline(true);
    flowLayout.setAlignment(FlowLayout.LEFT);
    popTab.setBackground(SystemColor.scrollbar);
    initBillboardCheckbox();
    initItunesCheckbox();
    initShazamCheckbox();
    initHypemCheckbox();
    initUKCheckbox();
    popTab.add(BillboardCheckbox);
    popTab.add(ItunesCheckbox);
    popTab.add(ShazamCheckbox);
    popTab.add(hypemCheckbox);
    popTab.add(UKCheckbox);
    settingTabPane.addTab("Pop Charts", null, popTab, null);
  }

  /**
   * Inits the settings tab pane.
   */
  private void initSettingsTabPane() {
    settingTabPane = new JTabbedPane(SwingConstants.TOP);
    settingTabPane.setBounds(690, 127, 620, 130);
    contentPane.add(settingTabPane);
  }

  /**
   * Inits the select all checkbox.
   */
  private void initSelectAllCheckbox() {
    SelectAllCheckBox = new JCheckBox("All Charts");
    SelectAllCheckBox.setOpaque(false);
    SelectAllCheckBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent evt) {
        selectCheckboxAllClickReaction();
      }

    });
    SelectAllCheckBox.setBounds(1230, 125, 75, 23);
    contentPane.add(SelectAllCheckBox);
  }

  /**
   * Inits the rec settingslbl.
   */
  private void initRecSettingslbl() {
    JLabel RecSettingsLabel = new JLabel("Recommendation Settings");
    RecSettingsLabel.setFont(new Font("Arial", Font.BOLD, 14));
    RecSettingsLabel.setBounds(690, 108, 184, 17);
    contentPane.add(RecSettingsLabel);
  }

  /**
   * Inits the media player frame lbl.
   */
  private void initMediaPlayerFrameLbl() {
    JLabel Media_Player_Box = new JLabel("");
    Media_Player_Box.setBorder(new LineBorder(Color.DARK_GRAY, 4));
    Media_Player_Box.setOpaque(true);
    Media_Player_Box.setBounds(675, 48, 650, 40);
    contentPane.add(Media_Player_Box);
  }

  /**
   * Inits the stop button.
   */
  private void initStopButton() {
    JButton stopButton = new JButton("");
    stopButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    stopButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        PlayerUserInterface.StopButton();
        beginMarquee("No current song playing");
      }
    });
    stopButton.setIcon(new ImageIcon(UserInterface.class
        .getResource("/Records/Images/Player Stop.png")));
    stopButton.setBounds(1285, 57, 24, 24);
    contentPane.add(stopButton);
  }

  /**
   * Inits the pause button.
   */
  private void initPauseButton() {
    JButton PauseButton = new JButton("");
    PauseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    PauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        PlayerUserInterface.PauseButton();
      }
    });
    PauseButton.setIcon(new ImageIcon(UserInterface.class
        .getResource("/Records/Images/Player Pause.png")));
    PauseButton.setBounds(1255, 57, 24, 24);
    contentPane.add(PauseButton);
  }

  /**
   * Inits the play button.
   */
  private void initPlayButton() {
    JButton PlayButton = new JButton("");
    PlayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    PlayButton.setIcon(new ImageIcon(UserInterface.class
        .getResource("/Records/Images/Player Play.png")));
    PlayButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        playReaction();
      }
    });
    PlayButton.setBounds(1225, 57, 24, 24);
    contentPane.add(PlayButton);
  }

  /**
   * Inits the play timer field.
   */
  private void initPlayTimerField() {
    PlayTime = new JTextField();
    PlayTime.setHorizontalAlignment(SwingConstants.CENTER);
    PlayTime.setBorder(null);
    PlayTime.setOpaque(false);
    PlayTime.setText("0:00 - 0:00");
    PlayTime.setBounds(1145, 53, 70, 30);
    contentPane.add(PlayTime);
    PlayTime.setColumns(10);
  }

  /**
   * Inits the media slider.
   */
  private void initMediaSlider() {
    ProgBar = new JSlider();
    ProgBar.setValue(0);
    ProgBar.setBounds(925, 55, 215, 28);
    contentPane.add(ProgBar);
  }

  /**
   * Inits the current song lbl.
   */
  private void initCurrentSongLbl() {
    CurrentSongLabel = new JLabel("No Current Song");
    CurrentSongLabel.setLabelFor(CurrentSongLabel);
    CurrentSongLabel.setFont(new Font("Arial", Font.BOLD, 12));
    CurrentSongLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    CurrentSongLabel.setHorizontalAlignment(SwingConstants.CENTER);
    CurrentSongLabel.setBounds(683, 53, 240, 30);
    contentPane.add(CurrentSongLabel);
  }

  /**
   * Inits the song info frame lbl.
   */
  private void initSongInfoFrameLbl() {
    JLabel Song_Info_Box = new JLabel("");
    Song_Info_Box.setOpaque(true);
    Song_Info_Box.setBorder(new LineBorder(Color.DARK_GRAY, 4));
    Song_Info_Box.setBounds(20, 48, 650, 208);
    contentPane.add(Song_Info_Box);
  }

  /**
   * Inits the itunes dl button.
   */
  private void initItunesDLButton() {
    itunesDownloadButton = new JButton("");
    itunesDownloadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    itunesDownloadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String song = titleTextField.getText();
        String artist = artistTextField.getText();
        String URLString = null;
        try {
          Desktop d = Desktop.getDesktop();
          ResultSet results = RecordsMain.dba.getPurchaseURL(song, artist);
          if (results.next()) {
            URLString = results.getString("Purchase_Link");
          }
          d.browse(new URI(URLString));

        } catch (IOException | URISyntaxException ex) {
          Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
          Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });
    itunesDownloadButton.setIcon(new ImageIcon(UserInterface.class
        .getResource("/Records/Images/buy_itunes.png")));
    itunesDownloadButton.setBounds(570, 220, 80, 22);
    contentPane.add(itunesDownloadButton);
  }

  /**
   * Inits the amazon dl button.
   */
  private void initAmazonDLButton() {
    amazonDownloadButton = new JButton("");
    amazonDownloadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    amazonDownloadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO add your handling code here:
        String song = titleTextField.getText().replaceAll(" ", "+");
        String artist = artistTextField.getText().replaceAll(" ", "+");
        try {
          Desktop d = Desktop.getDesktop();

          d.browse(new URI(
              "http://www.amazon.com/s/ref=sr_nr_p_lbr_music_artists__0?fst=as%3Aoff&rh=n%3A163856011%2Ck%3A"
                  + song + "+" + artist
                  + "%2Cp_n_feature_browse-bin%3A625151011%2Cp_lbr_music_artists_browse-bin%3A"
                  + artist + "&bbn=163856011&keywords=" + song + "+" + artist
                  + "&ie=UTF8&qid=1419431329&rnid=3458810011"));

        } catch (IOException | URISyntaxException ex) {
          Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });
    amazonDownloadButton.setIcon(new ImageIcon(UserInterface.class
        .getResource("/Records/Images/AmazonMP3.png")));
    amazonDownloadButton.setBounds(480, 220, 80, 22);
    contentPane.add(amazonDownloadButton);
  }

  /**
   * Inits the song key field.
   */
  private void initSongKeyField() {
    keyTextField = new JTextField();
    keyTextField.setEditable(false);
    keyTextField.setFont(new Font("Arial", Font.PLAIN, 12));
    keyTextField.setBounds(390, 220, 60, 20);
    contentPane.add(keyTextField);
    keyTextField.setColumns(10);
  }

  /**
   * Inits the song bpm field.
   */
  private void initSongBPMField() {
    bpmTextField = new JTextField();
    bpmTextField.setEditable(false);
    bpmTextField.setFont(new Font("Arial", Font.PLAIN, 12));
    bpmTextField.setBounds(280, 220, 60, 20);
    contentPane.add(bpmTextField);
    bpmTextField.setColumns(10);
  }

  /**
   * Inits the song location field.
   */
  private void initSongLocationField() {
    locationTextField = new JTextField();
    locationTextField.setEditable(false);
    locationTextField.setFont(new Font("Arial", Font.PLAIN, 12));
    locationTextField.setBounds(280, 190, 370, 20);
    contentPane.add(locationTextField);
    locationTextField.setColumns(10);
  }

  /**
   * Inits the song genre field.
   */
  private void initSongGenreField() {
    genreTextField = new JTextField();
    genreTextField.setEditable(false);
    genreTextField.setFont(new Font("Arial", Font.PLAIN, 12));
    genreTextField.setBounds(280, 160, 370, 20);
    contentPane.add(genreTextField);
    genreTextField.setColumns(10);
  }

  /**
   * Inits the song album field.
   */
  private void initSongAlbumField() {
    albumTextField = new JTextField();
    albumTextField.setEditable(false);
    albumTextField.setFont(new Font("Arial", Font.PLAIN, 12));
    albumTextField.setBounds(280, 130, 370, 20);
    contentPane.add(albumTextField);
    albumTextField.setColumns(10);
  }

  /**
   * Inits the song artist field.
   */
  private void initSongArtistField() {
    artistTextField = new JTextField();
    artistTextField.setEditable(false);
    artistTextField.setFont(new Font("Arial", Font.PLAIN, 12));
    artistTextField.setBounds(280, 100, 370, 20);
    contentPane.add(artistTextField);
    artistTextField.setColumns(10);
  }

  /**
   * Inits the song title field.
   */
  private void initSongTitleField() {
    titleTextField = new JTextField();
    titleTextField.setEditable(false);
    titleTextField.setFont(new Font("Arial", Font.PLAIN, 12));
    titleTextField.setBounds(280, 70, 370, 20);
    contentPane.add(titleTextField);
    titleTextField.setColumns(10);
  }

  /**
   * Inits the song key lbl.
   */
  private void initSongKeyLbl() {
    JLabel keyLabel = new JLabel("Key:");
    keyLabel.setFont(new Font("Arial", Font.BOLD, 14));
    keyLabel.setBounds(350, 220, 40, 20);
    contentPane.add(keyLabel);
  }

  /**
   * Inits the song bpm lbl.
   */
  private void initSongBPMLbl() {
    JLabel bpmLabel = new JLabel("BPM:");
    bpmLabel.setFont(new Font("Arial", Font.BOLD, 14));
    bpmLabel.setBounds(220, 220, 36, 20);
    contentPane.add(bpmLabel);
  }

  /**
   * Inits the song location lbl.
   */
  private void initSongLocationLbl() {
    JLabel locationLabel = new JLabel("Source:");
    locationLabel.setFont(new Font("Arial", Font.BOLD, 14));
    locationLabel.setBounds(220, 190, 58, 20);
    contentPane.add(locationLabel);
  }

  /**
   * Inits the song genre lbl.
   */
  private void initSongGenreLbl() {
    JLabel genreLabel = new JLabel("Genre:");
    genreLabel.setFont(new Font("Arial", Font.BOLD, 14));
    genreLabel.setBounds(220, 160, 52, 20);
    contentPane.add(genreLabel);
  }

  /**
   * Inits the song album lbl.
   */
  private void initSongAlbumLbl() {
    JLabel albumLabel = new JLabel("Album:");
    albumLabel.setFont(new Font("Arial", Font.BOLD, 14));
    albumLabel.setBounds(220, 130, 47, 20);
    contentPane.add(albumLabel);
  }

  /**
   * Inits the song artist lbl.
   */
  private void initSongArtistLbl() {
    JLabel artistLabel = new JLabel("Artist:");
    artistLabel.setFont(new Font("Arial", Font.BOLD, 14));
    artistLabel.setBounds(220, 100, 41, 20);
    contentPane.add(artistLabel);
  }

  /**
   * Inits the song title lbl.
   */
  private void initSongTitleLbl() {
    JLabel titleLabel = new JLabel("Title:");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
    titleLabel.setBounds(220, 70, 34, 20);
    contentPane.add(titleLabel);
  }

  /**
   * Inits the album cover.
   */
  private void initAlbumCover() {
    albumCover = new JLabel("");
    albumCover.setHorizontalTextPosition(SwingConstants.CENTER);
    albumCover.setHorizontalAlignment(SwingConstants.CENTER);
    albumCover.setBorder(new LineBorder(Color.DARK_GRAY, 3));
    albumCover.setIcon(new ImageIcon(UserInterface.class
        .getResource("/Records/Images/RecordsLogo.png")));
    albumCover.setBounds(36, 65, 174, 174);
    contentPane.add(albumCover);
  }

  /**
   * Inits the software name lbl.
   */
  private void initSoftwareNameLbl() {
    JLabel softwareNameLblPart1 = new JLabel("rec");
    softwareNameLblPart1.setBounds(20, 8, 46, 35);
    softwareNameLblPart1.setMaximumSize(new Dimension(46, 35));
    softwareNameLblPart1.setMinimumSize(new Dimension(46, 35));
    softwareNameLblPart1.setPreferredSize(new Dimension(46, 35));
    softwareNameLblPart1.setFont(new Font("Arial", Font.BOLD, 30));
    contentPane.add(softwareNameLblPart1);

    JLabel softwareNameLblPart2 = new JLabel("ords");
    softwareNameLblPart2.setPreferredSize(new Dimension(46, 35));
    softwareNameLblPart2.setMinimumSize(new Dimension(46, 35));
    softwareNameLblPart2.setMaximumSize(new Dimension(46, 35));
    softwareNameLblPart2.setFont(new Font("Arial", Font.PLAIN, 30));
    softwareNameLblPart2.setBounds(67, 8, 58, 35);
    contentPane.add(softwareNameLblPart2);
  }

  /**
   * Play reaction.
   */
  private void playReaction() {
    String song = titleTextField.getText();
    String artist = artistTextField.getText();
    String location = locationTextField.getText();

    if (PlayerUserInterface.getmusicPlayer() == null) {
      beginMarquee(song + " - " + artist);
      PlayerUserInterface.PlayButton(location, song, artist);
    } else {
      if (PlayerUserInterface.getStatus() != null) {
        switch (PlayerUserInterface.getStatus()) {
        case "PLAYING":
          PlayerUserInterface.StopButton();
          beginMarquee(song + " - " + artist);
          PlayerUserInterface.PlayButton(location, song, artist);
          break;
        case "PAUSED":
          PlayerUserInterface.ResumeButton();
          break;
        case "STOPPED":
          beginMarquee(song + " - " + artist);
          PlayerUserInterface.PlayButton(location, song, artist);
          break;
        }
      }
    }
  }

  /**
   * Retrieve db image path.
   *
   * @param album
   *          the album
   * @param image
   *          the image
   * @return the string
   * @throws SQLException
   *           the SQL exception
   */
  public String retrieveDBImagePath(String album, String image) throws SQLException {
    ResultSet results = RecordsMain.dba.getImagepath(album);
    if (results.next()) {
      image = results.getString("Image");
    }
    return image;
  }

  /**
   * Select checkbox all click reaction.
   */
  private void selectCheckboxAllClickReaction() {
    if (!(SelectAllCheckBox.isSelected())) {
      BillboardCheckbox.doClick();
      BillboardEDMCheckbox.doClick();
      BillboardCountryCheckbox.doClick();
      BillboardRnBCheckbox.doClick();
      hypemCheckbox.doClick();
      ItunesCheckbox.doClick();
      ItunesCountryCheckbox.doClick();
      ItunesRnBCheckbox.doClick();
      ItunesEDMCheckbox.doClick();
      UKCheckbox.doClick();
      ShazamCheckbox.doClick();
      BeatportCheckbox.doClick();
    } else {
      if (!BillboardCheckbox.isSelected()) {
        BillboardCheckbox.doClick();
      }
      if (!BillboardEDMCheckbox.isSelected()) {
        BillboardEDMCheckbox.doClick();
      }
      if (!BillboardCountryCheckbox.isSelected()) {
        BillboardCountryCheckbox.doClick();
      }
      if (!BillboardRnBCheckbox.isSelected()) {
        BillboardRnBCheckbox.doClick();
      }
      if (!hypemCheckbox.isSelected()) {
        hypemCheckbox.doClick();
      }
      if (!ItunesCheckbox.isSelected()) {
        ItunesCheckbox.doClick();
      }
      if (!ShazamCheckbox.isSelected()) {
        ShazamCheckbox.doClick();
      }
      if (!ItunesCountryCheckbox.isSelected()) {
        ItunesCountryCheckbox.doClick();
      }
      if (!ItunesRnBCheckbox.isSelected()) {
        ItunesRnBCheckbox.doClick();
      }
      if (!ItunesEDMCheckbox.isSelected()) {
        ItunesEDMCheckbox.doClick();
      }
      if (!UKCheckbox.isSelected()) {
        UKCheckbox.doClick();
      }
      if (!BeatportCheckbox.isSelected()) {
        BeatportCheckbox.doClick();
      }
    }
  }

  /**
   * Start album cover replacer.
   *
   * @param location
   *          the location
   * @param image
   *          the image
   */
  private void replaceAlbumCover(String location, String image) {
    if (Switch == null) {
      Switch = new AlbumCoverReplacer(location, image);
      Switch.start();
    } else {
      Switch.close();
      Switch = new AlbumCoverReplacer(location, image);
      Switch.start();
    }
  }

  /**
   * Key pressed action user library.
   *
   * @param evt
   *          the evt
   */
  private void userLibraryKeyPressedAction(KeyEvent evt) {
    String Danceability, Energy, Acousticness, song = null, artist = null, album, genre, bpm, theKey, location = null, image;
    if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_UP)) {
      setDownloadButtonVisibility(false);

      int row = getUserLibrary().getSelectedRow();
      song = getUserLibrary().getModel().getValueAt(row, 0).toString();
      String songView = getUserLibrary().getValueAt(row, 0).toString();
      if (row < getUserLibrary().getRowCount() - 1) {
        row = getUserLibrary().getSelectedRow() + 1;
      }
      if (evt.getKeyCode() == KeyEvent.VK_UP) {
        if (row < getUserLibrary().getRowCount() - 1) {
          row = getUserLibrary().getSelectedRow() - 1;
        }
      }
      ArrayList<String> list = TableAttributeAdjuster.AdjustAttributes(song, songView,
          getUserLibrary(), row);

      song = list.get(0).toString();
      artist = list.get(1).toString();
      album = list.get(2).toString();
      genre = list.get(3).toString();
      Energy = list.get(4).toString();
      Danceability = list.get(5).toString();
      Acousticness = list.get(6).toString();
      bpm = list.get(7).toString();
      theKey = list.get(8).toString();
      location = "Directory";
      image = "";

      BasedOn.setText("Based On: " + song + " by " + artist);

      try {
        image = retrieveDBImagePath(album, image);
        ResultSet result = RecordsMain.dba.getLibFilePathway(song, artist);
        if (result.next()) {
          setCurrentPath(result);
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }

      titleTextField.setText(song);
      artistTextField.setText(artist);
      albumTextField.setText(album);
      genreTextField.setText(genre);
      bpmTextField.setText(bpm);
      keyTextField.setText(theKey);
      locationTextField.setText(location);
      replaceAlbumCover(location, image);

      RecommendationTable.recommendations(song, theKey, bpm, genre, album, artist, location,
          Danceability, Energy, Acousticness);
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
      location = locationTextField.getText();
      song = titleTextField.getText();
      artist = artistTextField.getText();
      try {
        ResultSet result;
        if ("Directory".equals(location)) {
          result = RecordsMain.dba.getLibFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        } else {
          result = RecordsMain.dba.getFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        }

        if (PlayerUserInterface.getmusicPlayer() != null) {
          PlayerUserInterface.getmusicPlayer().close();
        }
        beginMarquee(song + " - " + artist);
        PlayerUserInterface.PlayButton(getCurrentPath(), location, song);

      } catch (SQLException ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Sets the user library.
   *
   * @param evt the evt
   */
  public void userLibraryMouseClickedAction(MouseEvent evt) {
    // String from = "LibTable";
    String Danceability, Energy, Acousticness, song, artist, location, image, album, genre, bpm, theKey;

    setDownloadButtonVisibility(false);
    int row = getUserLibrary().getSelectedRow();
    song = getUserLibrary().getModel().getValueAt(row, 0).toString();
    String songView = getUserLibrary().getValueAt(row, 0).toString();

    ArrayList<String> list = TableAttributeAdjuster.AdjustAttributes(song, songView,
        getUserLibrary(), row);

    song = list.get(0).toString();
    artist = list.get(1).toString();
    album = list.get(2).toString();
    genre = list.get(3).toString();
    Energy = list.get(4).toString();
    Danceability = list.get(5).toString();
    Acousticness = list.get(6).toString();
    bpm = list.get(7).toString();
    theKey = list.get(8).toString();

    location = "Directory";
    image = "";

    BasedOn.setText("Based On: " + song + " by " + artist);
    try {
      image = retrieveDBImagePath(album, image);

      ResultSet result = RecordsMain.dba.getLibFilePathway(song, artist);
      if (result.next()) {
        setCurrentPath(result);
      }

    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    RecommendationTable.recommendations(song, theKey, bpm, genre, album, artist, location,
        Danceability, Energy, Acousticness);

    titleTextField.setText(song);
    artistTextField.setText(artist);
    albumTextField.setText(album);
    genreTextField.setText(genre);
    bpmTextField.setText(bpm);
    keyTextField.setText(theKey);
    locationTextField.setText(location);
    replaceAlbumCover(location, image);

    RecommendationTable.recommendations(song, theKey, bpm, genre, album, artist, location,
        Danceability, Energy, Acousticness);

    if (evt.getClickCount() == 2) {

      try {
        ResultSet result = RecordsMain.dba.getLibFilePathway(song, artist);
        if (result.next()) {
          setCurrentPath(result);
        }
        if (PlayerUserInterface.getmusicPlayer() != null) {
          PlayerUserInterface.getmusicPlayer().close();
        }
        beginMarquee(song + " - " + artist);
        PlayerUserInterface.PlayButton(getCurrentPath(), location, song);

      } catch (SQLException ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Sets the download button visibility.
   *
   * @param isVisible the new download button visibility
   */
  private void setDownloadButtonVisibility(boolean isVisible) {
    itunesDownloadButton.setVisible(isVisible);
    amazonDownloadButton.setVisible(isVisible);
  }

  /**
   * Song pref table key pressed action.
   *
   * @param evt the evt
   */
  public void songPrefTableKeyPressedAction(KeyEvent evt) {
    String song = null;
    String artist = null;
    String album;
    String genre;
    String bpm;
    String theKey;
    String location = null;
    String image;
    if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_UP)) {
      setDownloadButtonVisibility(false);
      int row = songPrefTable.getSelectedRow();
      if (!(row < songPrefTable.getRowCount() - 1)) {
        row = songPrefTable.getSelectedRow();
      } else {
        row = songPrefTable.getSelectedRow() + 1;
      }
      if (evt.getKeyCode() == KeyEvent.VK_UP)
        if (!(row < songPrefTable.getRowCount() - 1)) {
          row = songPrefTable.getSelectedRow();
        } else {
          row = songPrefTable.getSelectedRow() - 1;
        }

      song = songPrefTable.getModel().getValueAt(row, 0).toString();
      artist = songPrefTable.getModel().getValueAt(row, 1).toString();
      album = songPrefTable.getModel().getValueAt(row, 2).toString();
      genre = songPrefTable.getModel().getValueAt(row, 3).toString();
      bpm = songPrefTable.getModel().getValueAt(row, 4).toString();
      theKey = songPrefTable.getModel().getValueAt(row, 5).toString();
      location = songPrefTable.getModel().getValueAt(row, 6).toString();
      image = "";

      try {
        image = retrieveDBImagePath(album, image);

        ResultSet result;
        if ("Directory".equals(location)) {
          result = RecordsMain.dba.getLibFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        } else {
          result = RecordsMain.dba.getFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        }

      } catch (SQLException ex) {
        ex.printStackTrace(); // FOR DEBUGGING ONLY
      }

      titleTextField.setText(song);
      artistTextField.setText(artist);
      albumTextField.setText(album);
      genreTextField.setText(genre);
      bpmTextField.setText(bpm);
      keyTextField.setText(theKey);
      locationTextField.setText(location);

      replaceAlbumCover(location, image);
    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
      location = locationTextField.getText();
      song = titleTextField.getText();
      artist = artistTextField.getText();
      try {
        ResultSet result;
        if ("Directory".equals(location)) {
          result = RecordsMain.dba.getLibFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        } else {
          result = RecordsMain.dba.getFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        }

        if (PlayerUserInterface.getmusicPlayer() != null) {
          PlayerUserInterface.getmusicPlayer().close();
        }
        beginMarquee(song + " - " + artist);
        PlayerUserInterface.PlayButton(getCurrentPath(), location, song);

      } catch (SQLException ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Chart song num plural switch.
   */
  public void chartSongNumPluralSwitch() {
    if (recommendationsTabPane.getSelectedIndex() == 1) {
      BasedOn.setVisible(false);
      DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI().rssChartsTable.getModel();
      if (model.getRowCount() == 1) {
        chartSongNum.setText(model.getRowCount() + " Song");
      } else {
        chartSongNum.setText(model.getRowCount() + " Songs");
      }
    } else {
      BasedOn.setVisible(true);
      DefaultTableModel model = (DefaultTableModel) RecordsMain.getUI().songPrefTable.getModel();
      if (model.getRowCount() == 1) {
        chartSongNum.setText(model.getRowCount() + " Song");
      } else {
        chartSongNum.setText(model.getRowCount() + " Songs");
      }
    }
  }

  /**
   * Song pref table mouse clicked action.
   *
   * @param evt the evt
   */
  public void songPrefTableMouseClickedAction(MouseEvent evt) {
    String song, artist, location, image, album, genre, bpm, theKey;
    setDownloadButtonVisibility(false);
    int row = songPrefTable.getSelectedRow();
    song = songPrefTable.getModel().getValueAt(row, 0).toString();
    String songView = songPrefTable.getValueAt(row, 0).toString();

    ArrayList<String> list = TableAttributeAdjuster.AdjustAttributes(song, songView, songPrefTable,
        row);

    song = list.get(0).toString();
    artist = list.get(1).toString();
    album = list.get(2).toString();
    genre = list.get(3).toString();
    bpm = list.get(4).toString();
    theKey = list.get(5).toString();
    location = list.get(6).toString();
    image = "";
    try {
      image = retrieveDBImagePath(album, image);

      ResultSet result;
      if ("Directory".equals(location)) {
        result = RecordsMain.dba.getLibFilePathway(song, artist);
        if (result.next()) {
          setCurrentPath(result);
        }
      } else {
        result = RecordsMain.dba.getFilePathway(song, artist);
        if (result.next()) {
          setCurrentPath(result);
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace(); // FOR DEBUGGING ONLY
    }

    titleTextField.setText(song);
    artistTextField.setText(artist);
    albumTextField.setText(album);
    genreTextField.setText(genre);
    bpmTextField.setText(bpm);
    keyTextField.setText(theKey);
    locationTextField.setText(location);
    replaceAlbumCover(location, image);

    if (evt.getClickCount() == 2) {
      try {
        ResultSet result;
        if ("Directory".equals(location)) {
          result = RecordsMain.dba.getLibFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        } else {
          result = RecordsMain.dba.getFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        }

        if (PlayerUserInterface.getmusicPlayer() != null) {
          PlayerUserInterface.getmusicPlayer().close();
        }
        beginMarquee(song + " - " + artist);
        PlayerUserInterface.PlayButton(getCurrentPath(), location, song);

      } catch (SQLException ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Rss chart table key typed action.
   *
   * @param evt the evt
   */
  public void rssChartTableKeyTypedAction(KeyEvent evt) {
    String song = null;
    String artist = null;
    String album;
    String genre;
    String bpm;
    String theKey;
    String location = null;
    String image;

    if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_UP)) {
      setDownloadButtonVisibility(false);
      int row = rssChartsTable.getSelectedRow();
      song = rssChartsTable.getModel().getValueAt(row, 0).toString();
      String songView = rssChartsTable.getValueAt(row, 0).toString();
      if (row < rssChartsTable.getRowCount() - 1) {
        row = rssChartsTable.getSelectedRow() + 1;
      }
      if (evt.getKeyCode() == KeyEvent.VK_UP) {
        if (row < rssChartsTable.getRowCount() - 1) {
          row = rssChartsTable.getSelectedRow() - 1;
        }
      }
      ArrayList<String> list = TableAttributeAdjuster.AdjustAttributes(song, songView,
          rssChartsTable, row);

      song = list.get(0).toString();
      artist = list.get(1).toString();
      album = list.get(2).toString();
      genre = list.get(3).toString();
      bpm = list.get(4).toString();
      theKey = list.get(5).toString();
      location = list.get(6).toString();
      image = "";

      try {
        image = retrieveDBImagePath(album, image);

        ResultSet result = RecordsMain.dba.getFilePathway(song, artist);
        if (result.next()) {
          setCurrentPath(result);
        }

      } catch (SQLException ex) {
        ex.printStackTrace(); // FOR DEBUGGING ONLY
      }

      titleTextField.setText(song);
      artistTextField.setText(artist);
      albumTextField.setText(album);
      genreTextField.setText(genre);
      bpmTextField.setText(bpm);
      keyTextField.setText(theKey);
      locationTextField.setText(location);
      replaceAlbumCover(location, image);

    }
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
      location = locationTextField.getText();
      song = titleTextField.getText();
      artist = artistTextField.getText();
      try {
        ResultSet result;
        if ("Directory".equals(location)) {
          result = RecordsMain.dba.getLibFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        } else {
          result = RecordsMain.dba.getFilePathway(song, artist);
          if (result.next()) {
            setCurrentPath(result);
          }
        }

        if (PlayerUserInterface.getmusicPlayer() != null) {
          PlayerUserInterface.getmusicPlayer().close();
        }
        beginMarquee(song + " - " + artist);
        PlayerUserInterface.PlayButton(getCurrentPath(), location, song);

      } catch (SQLException ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  /**
   * Rss chart table mouse clicked action.
   *
   * @param evt the evt
   */
  public void rssChartTableMouseClickedAction(MouseEvent evt) {
    String song, artist, location, image, album, genre, bpm, theKey;
    setDownloadButtonVisibility(true);
    int row = rssChartsTable.getSelectedRow();

    song = rssChartsTable.getModel().getValueAt(row, 0).toString();
    String songView = rssChartsTable.getValueAt(row, 0).toString();

    ArrayList<String> list = TableAttributeAdjuster.AdjustAttributes(song, songView,
        rssChartsTable, row);

    song = list.get(0).toString();
    artist = list.get(1).toString();
    album = list.get(2).toString();
    genre = list.get(3).toString();
    bpm = list.get(4).toString();
    theKey = list.get(5).toString();
    location = list.get(6).toString();
    image = "";

    titleTextField.setText(song);
    artistTextField.setText(artist);
    albumTextField.setText(album);
    genreTextField.setText(genre);
    bpmTextField.setText(bpm);
    keyTextField.setText(theKey);
    locationTextField.setText(location);
    replaceAlbumCover(location, image);

    if (evt.getClickCount() == 2) {
      ResultSet result;
      try {
        result = RecordsMain.dba.getFilePathway(song, artist);
        if (result.next()) {
          setCurrentPath(result);
        }
      } catch (SQLException ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }
      if (PlayerUserInterface.getmusicPlayer() != null) {
        PlayerUserInterface.getmusicPlayer().close();
      }
      beginMarquee(song + " - " + artist);
      PlayerUserInterface.PlayButton(getCurrentPath(), location, song);
    }
  }

  /**
   * Search bar key pressed action.
   *
   * @param evt the evt
   */
  public void searchBarKeyPressedAction(KeyEvent evt) {

    String SearchEntry = SearchTextField.getText().toLowerCase();
    DefaultTableModel model = (DefaultTableModel) getUserLibrary().getModel();

    if (!"".equals(SearchEntry) || SearchEntry != null) {
      // clear existing rows

      // System.out.println("all songs have been imported");
      List<String> data;

      try {
        ResultSet results = RecordsMain.dba.SearchBar(SearchEntry);
        while (model.getRowCount() > 0) {
          model.removeRow(0);
        }
        while (results.next()) {
          data = new LinkedList<>();
          // Build a Vector of Strings for the table row
          data.add(results.getString("SONG_TITLE"));
          data.add(results.getString("ARTIST"));
          data.add(results.getString("ALBUM"));
          data.add(results.getString("GENRE"));
          data.add(results.getString("ENERGY"));
          data.add(results.getString("DANCEABILITY"));
          data.add(results.getString("ACOUSTICNESS"));
          data.add(results.getString("BPM"));
          data.add(results.getString("THE_KEY"));
          data.add(results.getString("LOCATION"));

          // Add the data row to the table.
          model.addRow(data.toArray());
        }
      } catch (SQLException ex) {
        ex.printStackTrace(); // FOR DEBUGGING ONLY

      } catch (Exception ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }

    } else {
      // clear existing rows
      while (model.getRowCount() > 0) {
        model.removeRow(0);
      }

      System.out.println("all songs have been imported");
      List<String> data;

      try {
        ResultSet results = RecordsMain.dba.getAllLibrary();

        while (results.next()) {
          data = new LinkedList<>();
          // Build a Vector of Strings for the table row
          data.add(results.getString("SONG_TITLE"));
          data.add(results.getString("ARTIST"));
          data.add(results.getString("ALBUM"));
          data.add(results.getString("GENRE"));
          data.add(results.getString("ENERGY"));
          data.add(results.getString("DANCEABILITY"));
          data.add(results.getString("ACOUSTICNESS"));
          data.add(results.getString("BPM"));
          data.add(results.getString("THE_KEY"));
          data.add(results.getString("LOCATION"));

          // Add the data row to the table.
          model.addRow(data.toArray());
        }
      } catch (SQLException ex) {
        ex.printStackTrace(); // FOR DEBUGGING ONLY

      } catch (Exception ex) {
        Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
      }

    }
  }
}