package fileSplitter;

import java.io.File;

/**
 * The Class FileSplitterByDimension. Simply provides an interface to the "core"
 * file splitter. The core file splitter uses the number of parts as a
 * parameter, here we calculate the number of parts by dividing file lenght by
 * part dimension.
 */
public class FileSplitterByDimension extends FileSplitterCore {

	/**
	 * Instantiates a new file splitter by dimension. we still use an integer
	 * parameter called requested parts even if we don't need it. This is so that we
	 * can instantiate all splitters with an uniform parameter scheme.
	 *
	 * @param outPath        the output path
	 * @param requestedParts the requested parts
	 */
	public FileSplitterByDimension(String outPath, int requestedParts) {
		super(outPath, requestedParts);
	}

	/**
	 * Split the file.
	 *
	 * @param file                the file to split
	 * @param dimensionOfEachPart the dimension of each split part
	 * @throws Exception Any exception thrown by the process of splitting the file
	 *                   (i.e. empty file, ...)
	 */
	@Override
	void splitFile(File file, int dimensionOfEachPart) throws Exception {
		super.splitFile(file, (int) Math.ceil(file.length() / (dimensionOfEachPart)));
	}
}
