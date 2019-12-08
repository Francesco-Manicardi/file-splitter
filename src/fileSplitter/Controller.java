package fileSplitter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

public class Controller {

	Window window;
	JSplitPane splitPane;
	FileManagerButton fileManagerButton;
	ClearListButton clearListButton;
	FileSplitterButton fileSplitterButton;
	FileStitcherButton fileStitcherButton;

	FileListDisplayer fileListDisplayer;
	SplitModalityChooser splitModalityChooser;
	ParameterInput parameterInput;
	OutputDirChooser outputDirChooser;

	JPanel leftView, rightView;

	public Controller(Window window) {
		this.window = window;

		splitPane = new JSplitPane();

		leftView = new JPanel();
		leftView.setLayout(new BoxLayout(leftView, BoxLayout.Y_AXIS));

		leftView.setBorder(new EmptyBorder(10, 10, 10, 10));

		fileManagerButton = new FileManagerButton(this);
		clearListButton = new ClearListButton(this);
		fileSplitterButton = new FileSplitterButton(this);
		fileStitcherButton = new FileStitcherButton(this);
		splitModalityChooser = new SplitModalityChooser(this);
		parameterInput = new ParameterInput(this);

		leftView.add(fileManagerButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(clearListButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(fileSplitterButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(fileStitcherButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(splitModalityChooser);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(parameterInput);
		leftView.add(Box.createVerticalStrut(20));

		splitPane.setLeftComponent(leftView);

		rightView = new JPanel();

		fileListDisplayer = new FileListDisplayer(this);
		rightView.add(fileListDisplayer);

		outputDirChooser = new OutputDirChooser();
		rightView.add(outputDirChooser);

		splitPane.setRightComponent(rightView);

		splitPane.setResizeWeight(0.5);

		window.frame.add(splitPane);

	}

	public void afterChoseFiles() {

		this.fileListDisplayer.updateListing();

	}

}
