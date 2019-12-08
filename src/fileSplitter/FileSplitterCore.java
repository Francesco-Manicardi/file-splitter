package fileSplitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class FileSplitterCore {

	String outPath;
	int inputParam;

	FileSplitterCore(String outPath, int requestedParts) {
		this.outPath = outPath;
		this.inputParam = requestedParts;
	}

	void splitFiles(File[] files) throws Exception {
		if (files == null || files.length == 0)
			throw new Exception("Lista Vuota!");

		for (File file : files) {
			splitFile(file, inputParam);
		}
	}

	void splitFile(File file, int howManyParts) throws Exception {
		System.out.printf("Splitting file %s\n", file.getPath());

		if (file.length() == 0) {
			throw new Exception("Il file " + file.getPath() + " è vuoto!");

		}

		long bytesPerPart = Math.floorDiv(file.length(), howManyParts);
		int leftOverBytes = (int) (file.length() - (bytesPerPart * howManyParts));

		FileInputStream fileInputStream = new FileInputStream(file);
		FileWriter bufferedPartitionLog = new FileWriter(this.outPath + "/" + file.getName() + ".partitioninfo");

		bufferedPartitionLog.write(SplitModalityEnum.getFromClass(this.getClass()).name());
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

	protected String getOutputPath(File file) {
		return outPath + '/' + file.getName();
	}
}
