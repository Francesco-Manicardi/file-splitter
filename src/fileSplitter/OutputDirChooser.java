package fileSplitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OutputDirChooser extends JPanel implements ActionListener {
	protected JLabel titolo;
	protected JButton chooseDirButton;
	protected JLabel output;

	protected JFileChooser chooser;

	protected File chosenDir;

	OutputDirChooser() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		output = new JLabel();
		titolo = new JLabel("Scegli La Cartella Di Output");
		chooseDirButton = new JButton("Scegli");
		chooseDirButton.addActionListener(this);
		add(titolo);
		add(chooseDirButton);
		add(output);

		chooser = new JFileChooser();
		this.chosenDir = this.chooser.getCurrentDirectory();
		output.setText(this.chosenDir.getPath());

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			this.chosenDir = chooser.getSelectedFile();
			output.setText(this.chosenDir.getPath());
		} else {
			System.out.println("No Selection ");
		}
	}

}