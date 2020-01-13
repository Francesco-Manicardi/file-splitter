package view;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import fileSplitter.Controller;
import fileSplitter.SplitModalityEnum;

/**
 * A dynamic chooser of split modes, using radio buttons.
 */
public class SplitModalityChooser extends JPanel {

	/** The originating controller. */
	private Controller controller;

	/** The split modalities button group (for radio buttons). */
	private ButtonGroup splitModalities;

	/** The selected split mode description. */
	private String selected;

	/**
	 * Instantiates a new split modality chooser by creating a radio button for each
	 * split modality in the SplitModalityEnum. This method also fakes a click on
	 * the first button. It doesn't simply set the selected property, because then
	 * the click handler wouldn't get called.
	 * 
	 * @param controller the originating controller
	 */
	public SplitModalityChooser(Controller controller) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.controller = controller;
		splitModalities = new ButtonGroup();

		boolean first = true;

		for (SplitModalityEnum splitModality : SplitModalityEnum.values()) {

			JRadioButton tmp = new JRadioButton(splitModality.getDescription());

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

	/**
	 * Sets the selected split modality description.
	 *
	 * @param e the event containing the new selected split modality in the action
	 *          command.
	 */
	private void setSelected(ActionEvent e) {
		this.selected = e.getActionCommand();
		if (this.controller.getParameterInput() != null)
			this.controller.getParameterInput().updateLabel();

	}

	/**
	 * A simple getter
	 * 
	 * @return the selected split mode description
	 */
	public String getSelected() {
		return selected;
	}

}
