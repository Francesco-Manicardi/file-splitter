package fileSplitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The File Splitter that also compresses the split parts.
 */
public class FileSplitterCompressed extends FileSplitterCore {

	/**
	 * Instantiates a new file splitter (that also compresses).
	 *
	 * @param o the output path
	 * @param r Originally this was the superclass requested amount of parts, but
	 *          here this parameter is used as the size of each split part file.
	 */
	FileSplitterCompressed(String o, int r) {
		super(o, r);
	}

	/**
	 * Split the file and compress the resulting parts.
	 *
	 * @param file         the file to split and compress.
	 * @param bytesPerPart the amount bytes for each split part (not taking into
	 *                     account the compression)
	 * @throws Exception exceptions such as empty file, or failure to parse the file
	 *                   name / extension
	 */
	@Override
	void splitFile(File file, int bytesPerPart) throws Exception {
		System.out.printf("Splitting file %s\n", file.getPath());

		if (file.length() == 0) {
			throw new Exception("Il file " + file.getPath() + " è vuoto!");

		}

		int howManyParts = (int) (file.length() / bytesPerPart);
		int leftOverBytes = (int) (file.length() - (bytesPerPart * howManyParts));

		FileInputStream fileInputStream = new FileInputStream(file);
		FileWriter bufferedPartitionLog = new FileWriter(this.outPath + "/" + file.getName() + ".partitioninfo");

		bufferedPartitionLog.write(SplitModalityEnum.getSplitModalityFromSplitterClass(this.getClass()).name());
		bufferedPartitionLog.write(System.lineSeparator());
		bufferedPartitionLog.write(file.getName() + ".zip");
		bufferedPartitionLog.write(System.lineSeparator());

		FileOutputStream fileOutputStream = new FileOutputStream(this.outPath + "/" + file.getName() + ".zip");

		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

		for (int part = 1; part <= howManyParts; part++) {

			String outputPath = getOutputPath(file) + "." + part + ".split";
			System.out.printf("\t Writing partition %s\n", outputPath);

			zipOutputStream.putNextEntry(new ZipEntry(file.getName() + "." + part + ".split"));
			byte readByte;
			for (int numberOfReadBytes = 0; numberOfReadBytes < bytesPerPart; numberOfReadBytes++) {

				readByte = (byte) fileInputStream.read();
				zipOutputStream.write(readByte);

			}

			zipOutputStream.closeEntry();
		}

		if (leftOverBytes > 0) {

			zipOutputStream.putNextEntry(new ZipEntry(file.getName() + "." + (howManyParts + 1) + ".split"));

			byte[] leftovers = new byte[leftOverBytes];

			fileInputStream.read(leftovers);
			zipOutputStream.write(leftovers);
			zipOutputStream.closeEntry();

		}
		System.out.printf("Completed File %s\n", file.getPath());

		zipOutputStream.close();
		fileOutputStream.close();

		fileInputStream.close();
		bufferedPartitionLog.close();
	}

}
