package fileSplitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * The Main FileSplitter class from which all others descend (meaning they
 * extend this class).
 */
public class FileSplitterCore {

	/** The output path for the split parts. */
	String outPath;

	/** The input param (in this case, it's the number of requested parts). */
	int inputParam;

	/**
	 * Instantiates a new basic file splitter.
	 *
	 * @param outPath        the output path in which file parts should be placed
	 * @param requestedParts the requested amount of parts. Note that this <b>can be
	 *                       different from the actual amount of parts</b> (because
	 *                       there could be some leftover bytes)
	 */
	FileSplitterCore(String outPath, int requestedParts) {
		this.outPath = outPath;
		this.inputParam = requestedParts;
	}

	/**
	 * Split the files.
	 *
	 * @param files the files to be split
	 * @throws Exception when the files array is empty.
	 */
	void splitFiles(File[] files) throws Exception {
		if (files == null || files.length == 0)
			throw new Exception("Lista Vuota!");

		for (File file : files) {
			splitFile(file, inputParam);
		}
	}

	/**
	 * Split a single file.
	 *
	 * @param file         the file to be split.
	 * @param howManyParts the desired amount of parts resulting from the split.
	 * @throws Exception when the file is empty or something goes wrong during the
	 *                   IO processes
	 */
	void splitFile(File file, int howManyParts) throws Exception {
		System.out.printf("Splitting file %s\n", file.getPath());

		if (file.length() == 0) {
			throw new Exception("Il file " + file.getPath() + " è vuoto!");

		}

		long bytesPerPart = Math.floorDiv(file.length(), howManyParts);
		int leftOverBytes = (int) (file.length() - (bytesPerPart * howManyParts));

		FileInputStream fileInputStream = new FileInputStream(file);
		FileWriter bufferedPartitionLog = new FileWriter(this.outPath + "/" + file.getName() + ".partitioninfo");

		bufferedPartitionLog.write(SplitModalityEnum.getSplitModalityFromSplitterClass(this.getClass()).name());
		bufferedPartitionLog.write(System.lineSeparator());

		for (int part = 1; part <= howManyParts; part++) {

			String outputPath = getOutputPath(file) + "." + part + ".split";
			System.out.printf("\t Writing partition %s\n", outputPath);

			bufferedPartitionLog.write(file.getName() + "." + part + ".split");
			bufferedPartitionLog.write(System.lineSeparator());

			FileOutputStream fileOutputStream = new FileOutputStream(outputPath);

			byte readByte;
			for (int numberOfReadBytes = 0; numberOfReadBytes < bytesPerPart; numberOfReadBytes++) {

				readByte = (byte) fileInputStream.read();
				fileOutputStream.write(readByte);

			}

			fileOutputStream.close();
		}

		if (leftOverBytes > 0) {

			String outputPath = getOutputPath(file) + "." + (howManyParts + 1) + ".split";

			FileOutputStream fileOutputStream = new FileOutputStream(outputPath);

			byte[] leftovers = new byte[leftOverBytes];

			fileInputStream.read(leftovers);
			fileOutputStream.write(leftovers);
			fileOutputStream.close();

			bufferedPartitionLog.write(file.getName() + "." + (howManyParts + 1) + ".split");

		}
		System.out.printf("Completed File %s\n", file.getPath());

		fileInputStream.close();
		bufferedPartitionLog.close();
	}

	/**
	 * Gets the output path for a splitting process.
	 *
	 * @param file the file being split.
	 * @return the output path where the split parts should end up.
	 */
	protected String getOutputPath(File file) {
		return outPath + '/' + file.getName();
	}
}
