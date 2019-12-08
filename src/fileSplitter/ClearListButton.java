package fileSplitter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ClearListButton extends JButton implements ActionListener {

	private Controller controller;

	public ClearListButton(Controller controller) {
		this.setBounds(20, 20, 100, 30);
		this.setText("Reset Coda");
		this.controller = controller;
		this.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		this.controller.fileManagerButton.empty();
	}

}
