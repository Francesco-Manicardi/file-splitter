package fileSplitter;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SplitModalityChooser extends JPanel {

	private Controller controller;
	private ButtonGroup splitModalities;
	protected String selected;

	public SplitModalityChooser(Controller controller) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.controller = controller;
		splitModalities = new ButtonGroup();

		boolean first = true;

		for (SplitModalityEnum splitModality : SplitModalityEnum.values()) {

			JRadioButton tmp = new JRadioButton(splitModality.description);

			tmp.setActionCommand(splitModality.name());
			tmp.addActionListener(e -> {
				this.setSelected(e);
			});
			splitModalities.add(tmp);
			add(tmp);

			if (first) {
				tmp.doClick();
				first = false;
			}

		}

	}

	private void setSelected(ActionEvent e) {
		this.selected = e.getActionCommand();
		if (this.controller.parameterInput != null)
			this.controller.parameterInput.updateLabel();

	}

}
