package Records.Email;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailWindow.
 */
public class EmailWindow extends JDialog {

	/**
	 * Launch the application.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		try {
			EmailWindow dialog = new EmailWindow();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** The content panel. */
	private final JPanel contentPanel = new JPanel();

	/** The subject line. */
	JTextField subjectLine;

	/** The ex description. */
	JTextArea exDescription;

	/**
	 * Create the dialog.
	 */
	public EmailWindow() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblYourFeedback = new JLabel("Your Feedback");
			lblYourFeedback.setBounds(10, 10, 132, 22);
			lblYourFeedback.setFont(new Font("Arial", Font.BOLD, 18));
			lblYourFeedback.setHorizontalAlignment(SwingConstants.LEFT);
			contentPanel.add(lblYourFeedback);
		}
		{
			JLabel Subject = new JLabel("Subject:");
			Subject.setBounds(10, 43, 56, 22);
			contentPanel.add(Subject);
		}
		{
			exDescription = new JTextArea();
			exDescription.setBorder(new LineBorder(new Color(0, 0, 0)));
			exDescription.setLineWrap(true);
			exDescription.setBounds(10, 100, 414, 117);
			contentPanel.add(exDescription);
		}
		{
			subjectLine = new JTextField();
			subjectLine.setBounds(56, 43, 368, 20);
			contentPanel.add(subjectLine);
			subjectLine.setColumns(10);
		}
		{
			JLabel Describe = new JLabel("Describe your experience:");
			Describe.setBounds(10, 72, 249, 22);
			contentPanel.add(Describe);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton SubmitButton = new JButton("Submit");
				SubmitButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						EmailSender sender = new EmailSender();
						sender.Send();
						dispose();
					}
				});
				SubmitButton.setActionCommand("Submit");
				buttonPane.add(SubmitButton);
				getRootPane().setDefaultButton(SubmitButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
	}

}
