package fileSplitter;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 * The Parameter Input Text Field. We use it filtered by OnlyPositiveIntegers.
 */
public class ParameterInput extends JPanel {

	/** The originating controller. */
	private Controller controller;

	/** The input text field. */
	private JTextField input;

	/** The label for the text field. */
	private JLabel label;

	/**
	 * Instantiates a new parameter input text field.
	 *
	 * @param controller the originating controller
	 */
	public ParameterInput(Controller controller) {
		setLayout(new FlowLayout());

		this.controller = controller;
		input = new JTextField();
		input.setPreferredSize(new Dimension(200, 20));

		PlainDocument doc = (PlainDocument) input.getDocument();
		doc.setDocumentFilter(new OnlyPositiveIntegersFilter());

		label = new JLabel();
		updateLabel();
		add(label);
		add(input);

	}

	/**
	 * Update the input label based on a chosen split mode.
	 */
	public void updateLabel() {
		SplitModalityEnum selectedSplitModality = Enum.valueOf(SplitModalityEnum.class,
				this.controller.splitModalityChooser.selected);

		label.setText(selectedSplitModality.hint);

	}

	/**
	 * Get the input value.
	 *
	 * @return the input value
	 */
	public int getInputValue() {
		try {
			return Integer.parseInt(this.input.getText());
		} catch (NumberFormatException e) {
			// This exception is only thrown when the input is empty, since the field is
			// validated. as a fallback, we return 1.
			return 1;
		}
	}

}
