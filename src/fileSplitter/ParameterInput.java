package fileSplitter;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class ParameterInput extends JPanel {

	private Controller controller;
	private JTextField input;
	private JLabel label;

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

	public void updateLabel() {
		SplitModalityEnum selectedSplitModality = Enum.valueOf(SplitModalityEnum.class,
				this.controller.splitModalityChooser.selected);

		label.setText(selectedSplitModality.hint);

	}

	public int getInputValue() {
		try {
			return Integer.parseInt(this.input.getText());
		} catch (NumberFormatException e) {
			// Poichè l'input è validato, l'unico caso in cui si verifica questa eccezione è
			// se l'input è vuoto. Quindi ritorno 1 come "default".
			// Non ritorno 0 perchè sarebbe equivalente a eliminare il file.
			return 1;
		}
	}

}
