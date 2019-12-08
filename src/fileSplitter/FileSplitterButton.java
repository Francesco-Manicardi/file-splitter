package fileSplitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class FileSplitterButton extends JButton implements ActionListener {

	private Controller controller;
	private FileSplitterCore fileSplitterCore;

	public FileSplitterButton(Controller controller) {
		this.setBounds(20, 20, 100, 30);
		this.setText("Splitta i File");
		this.controller = controller;
		this.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			String selectedSplitModality = this.controller.splitModalityChooser.selected;

			SplitModalityEnum splitModality = SplitModalityEnum.valueOf(selectedSplitModality);

			this.fileSplitterCore = splitModality.splitterClass.getDeclaredConstructor(String.class, int.class)
					.newInstance(this.controller.outputDirChooser.chosenDir.getPath(),
							this.controller.parameterInput.getInputValue());

			this.fileSplitterCore.splitFiles(this.controller.fileManagerButton.files);
		} catch (Exception exception) {
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, exception.getMessage());
		}
	}

}
