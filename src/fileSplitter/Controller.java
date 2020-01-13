package fileSplitter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import view.ClearListButton;
import view.FileListDisplayer;
import view.FileManagerButton;
import view.FileSplitterButton;
import view.FileStitcherButton;
import view.OutputDirChooser;
import view.ParameterInput;
import view.SplitModalityChooser;
import view.Window;

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
	public FileManagerButton fileManagerButton;

	/** The button that clears the chosen files list. */
	ClearListButton clearListButton;

	/** The file splitter button. Splits the chosen files on click. */
	FileSplitterButton fileSplitterButton;

	/** The file stitcher button. Stitches the files together on click. */
	FileStitcherButton fileStitcherButton;

	/** The file list displayer. Displays the chosen files in a scrollable list. */
	public FileListDisplayer fileListDisplayer;

	/** The split modality chooser. You can choose between 4 different modes. */
	private SplitModalityChooser splitModalityChooser;

	/** The parameter input. It is used when splitting files as a parameter. */
	private ParameterInput parameterInput;

	/**
	 * The output dir chooser. With this component you can choose where the output
	 * of the stitching and splitting will end up.
	 */
	private OutputDirChooser outputDirChooser;

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
		setSplitModalityChooser(new SplitModalityChooser(this));
		setParameterInput(new ParameterInput(this));

		/* And add them to the left view. */
		leftView.add(fileManagerButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(clearListButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(fileSplitterButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(fileStitcherButton);
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(getSplitModalityChooser());
		leftView.add(Box.createVerticalStrut(20));
		leftView.add(getParameterInput());
		leftView.add(Box.createVerticalStrut(20));

		splitPane.setLeftComponent(leftView);
		/* Create the right view and its components and add them to the view. */
		rightView = new JPanel();

		fileListDisplayer = new FileListDisplayer(this);
		rightView.add(fileListDisplayer);

		setOutputDirChooser(new OutputDirChooser());
		rightView.add(getOutputDirChooser());

		splitPane.setRightComponent(rightView);

		splitPane.setResizeWeight(0.5);
		/* Add the split pane to the main window. */
		window.getFrame().add(splitPane);

	}

	/**
	 * @return the outputDirChooser
	 */
	public OutputDirChooser getOutputDirChooser() {
		return outputDirChooser;
	}

	/**
	 * @param outputDirChooser the outputDirChooser to set
	 */
	public void setOutputDirChooser(OutputDirChooser outputDirChooser) {
		this.outputDirChooser = outputDirChooser;
	}

	/**
	 * @return the parameterInput
	 */
	public ParameterInput getParameterInput() {
		return parameterInput;
	}

	/**
	 * @param parameterInput the parameterInput to set
	 */
	public void setParameterInput(ParameterInput parameterInput) {
		this.parameterInput = parameterInput;
	}

	/**
	 * @return the splitModalityChooser
	 */
	public SplitModalityChooser getSplitModalityChooser() {
		return splitModalityChooser;
	}

	/**
	 * @param splitModalityChooser the splitModalityChooser to set
	 */
	public void setSplitModalityChooser(SplitModalityChooser splitModalityChooser) {
		this.splitModalityChooser = splitModalityChooser;
	}

}
