package fileSplitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileSplitterCompressed extends FileSplitterCore {

	int dimensionPerPart;

	FileSplitterCompressed(String o, int r) {
		super(o, 0);
		this.dimensionPerPart = r;
	}

	@Override
	void splitFiles(File[] files) throws Exception {
		if (files == null || files.length == 0)
			throw new Exception("Lista Vuota!");

		for (File file : files) {
			splitFile(file, dimensionPerPart);
		}
	}

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

		bufferedPartitionLog.write(SplitModalityEnum.getFromClass(this.getClass()).name());
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

	@Override
	protected String getOutputPath(File file) {
		return outPath + '/' + file.getName();
	}
}
