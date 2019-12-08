package fileSplitter;

import java.io.File;

public class FileSplitterByDimension extends FileSplitterCore {

	public FileSplitterByDimension(String outPath, int requestedParts) {
		super(outPath, requestedParts);
	}

	@Override
	void splitFile(File file, int dimensionOfEachPart) throws Exception {
		super.splitFile(file, (int) Math.ceil(file.length() / (dimensionOfEachPart * 1000)));
	}
}
