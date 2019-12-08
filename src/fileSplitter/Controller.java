package fileSplitter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

/**
 * The Controller Class where all the GUI gets built.
 */
public class Controller {

	/** The main window. */
	Window window;

	/** A split pane with 1 panel for each side. */
	JSplitPane splitPane;

	/**
	 * A button that, when pressed, prompts the user to choose some files to
	 * split/stitch.
	 */
	FileManagerButton fileManagerButton;

	/** The button that clears the chosen files list. */
	ClearListButton clearListButton;

	/** The file splitter button. Splits the chosen files on click. */
	FileSplitterButton fileSplitterButton;

	/** The file stitcher button. Stitches the files together on click. */
	FileStitcherButton fileStitcherButton;

	/** The file list displayer. Displays the chosen files in a scrollable list. */
	FileListDisplayer fileListDisplayer;

	/** The split modality chooser. You can choose between 4 different modes. */
	SplitModalityChooser splitModalityChooser;

	/** The parameter input. It is used when splitting files as a parameter. */
	ParameterInput parameterInput;

	/**
	 * The output dir chooser. With this component you can choose where the output
	 * of the stitching and splitting will end up.
	 */
	OutputDirChooser outputDirChooser;

	/** The left and right views of the split panel. */
	JPanel leftView, rightView;

	/**
	 * Instantiates a new controller and attaches it to the windows.
	 *
	 * @param window the main window.
	 */
	public Controller(Window window) {
		this.window = window;

		splitPane = new JSplitPane();

		leftView = new JPanel();
		leftView.setLayout(new BoxLayout(leftView, BoxLayout.Y_AXIS));

		leftView.setBorder(new EmptyBorder(10, 10, 10, 10));

		/* Instantiate all the left panel components... */
		fileManagerButton = new FileManagerButton(this);
		clearListButton = new ClearListButton(this);
		fileSplitterButton = new FileSplitterButton(this);
		fileStitcherButton = new FileStitcherButton(this);
		splitModalityChooser = new SplitModalityChooser(this);
		parameterInput = new ParameterInput(this);

		/* And add them to the left view. */
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
		/* Create the right view and its components and add them to the view. */
		rightView = new JPanel();

		fileListDisplayer = new FileListDisplayer(this);
		rightView.add(fileListDisplayer);

		outputDirChooser = new OutputDirChooser();
		rightView.add(outputDirChooser);

		splitPane.setRightComponent(rightView);

		splitPane.setResizeWeight(0.5);
		/* Add the split pane to the main window. */
		window.frame.add(splitPane);

	}

}
