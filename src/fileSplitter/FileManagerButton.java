package fileSplitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManagerButton extends JButton implements ActionListener {

	File[] files;
	JFileChooser chooser = new JFileChooser();

	private Controller controller;

	public FileManagerButton(Controller controller) {
		this.setBounds(20, 20, 100, 30);
		this.setText("Aggiungi File");
		this.controller = controller;
		this.addActionListener(this);

		FileNameExtensionFilter partitionInfoFilter = new FileNameExtensionFilter("File Per Ricomposizione",
				"partitioninfo");
		chooser.addChoosableFileFilter(partitionInfoFilter);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		chooser.setMultiSelectionEnabled(true);
		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {

			files = chooser.getSelectedFiles();
			this.controller.afterChoseFiles();
		}
	}

	public void empty() {
		this.files = new File[0];
		this.controller.fileListDisplayer.refreshModel();
		this.controller.fileListDisplayer.updateListing();
	}

}
