package Records.UserInterface;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Records.Attributes.FeedDataSaver;
import Records.Attributes.FeedDataSender;
import Records.Connector.ConnectionChecker;
import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class SplashClass.
 */
public class SplashClass extends Thread {

	/** The j label2. */
	private JLabel jLabel2;

	/** The Send. */
	private static FeedDataSender Send;

	/** The dialog. */
	private JDialog dialog;

	/** The progress. */
	private JProgressBar progress;

	/** The load. */
	private int load;

	/** The pre rss extract count. */
public int preRssExtractCount;

	/**
	 * Instantiates a new splash class.
	 */
	public SplashClass() {
		initSplashComponents();
	}

	/**
	 * Adds the to pre rss extract count.
	 *
	 * @param preRssExtractCount
	 *            the pre rss extract count
	 */
	public void addToPreRssExtractCount(int preRssExtractCount) {
		this.preRssExtractCount = this.preRssExtractCount + preRssExtractCount;
	}

	/**
	 * Hide splash screen.
	 */
	private void hideSplashScreen() {
		System.out.println("SplashClass.hideSplashScreen()");
		dialog.setVisible(false);
		dialog.dispose();
	}

	/**
	 * Inits the splash components.
	 */
	private void initSplashComponents() {
		dialog = new JDialog();
		dialog.getContentPane().setLayout(null);
		dialog.setModal(false);
		dialog.setUndecorated(true);
		jLabel2 = new JLabel("");
		jLabel2.setBounds(10, 233, 424, 14);
		dialog.getContentPane().add(jLabel2);

		progress = new JProgressBar();
		progress.setBounds(3, 259, 445, 14);
		progress.setStringPainted(true);
		dialog.getContentPane().add(progress);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SplashClass.class
				.getResource("/Records/Images/Records_Program_Splash.png")));
		lblNewLabel.setBounds(0, 0, 451, 274);
		dialog.getContentPane().add(lblNewLabel);
		dialog.setPreferredSize(new Dimension(452, 274));

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(SplashClass.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		System.out.println("SplashClass.run()");
		boolean proceed = true;
		ConnectionChecker CC = new ConnectionChecker("http://www.google.com");
		if (!(CC.isConnected())) {
			JOptionPane
					.showMessageDialog(
							null,
							"There are problems connecting to the internet. Connect to the internet and restart.",
							"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} else {
			try {
				RecordsMain.dba.truncateChartTables();
			} catch (SQLException ex) {
				proceed = false;
				ex.printStackTrace();
			}

			if (!(proceed)) {
				RecordsMain.getUI().setVisible(true);
				hideSplashScreen();
			} else {
				try {
					showSplashScreen();
				} catch (MalformedURLException ex) {
					Logger.getLogger(SplashClass.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				Send = new FeedDataSender();
				Send.start();
			}
		}
		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {

			@Override
			protected Void doInBackground() throws Exception {
				System.out.println("preRssExtractCount= " + preRssExtractCount);
				System.out.println("SaveFeedData.getRssSongCounter()= "
						+ FeedDataSaver.getRssSongCounter());
				while (FeedDataSaver.getRssSongCounter() < preRssExtractCount) {
					load = ((FeedDataSaver.getRssSongCounter() * 100) / preRssExtractCount);
					publish(load);// Notify progress
					jLabel2.setText(FeedDataSaver.getCurSavedSong());
				}
				return null;
			}

			@Override
			protected void done() {
				System.out.println("Done Splash");
				RecordsMain.getUI().setVisible(true);
				hideSplashScreen();
			}

			@Override
			protected void process(List<Integer> chunks) {
				// System.out.println("Setting Progress value= " +load);
				progress.setValue(load);
				// System.out.println("Progress value Set");
			}
		};
		worker.execute();
	}

	/**
	 * Show splash screen.
	 *
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	private void showSplashScreen() throws MalformedURLException {
		System.out.println("SplashClass.ShowSplashScreen()");
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
}
