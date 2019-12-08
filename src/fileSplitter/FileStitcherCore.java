package fileSplitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class FileStitcherCore {

	String outPath;

	FileStitcherCore(String outPath) {
		this.outPath = outPath;
	}

	void stitchFile(File file) throws Exception {
		System.out.printf("Stitching with control file %s\n", file.getPath());

		if (file.length() == 0) {
			throw new Exception("Il file " + file.getPath() + " è vuoto!");

		}
		System.out.printf("estensione file: %s\n", getExtension(file));
		if (!getExtension(file).equals("partitioninfo")) {
			throw new Exception("Il file " + file.getPath()
					+ " non è un file partitioninfo! Seleziona solo file partitioninfo per ricomporre.");

		}

		FileOutputStream fileOutputStream = new FileOutputStream(getOutputPath(file));

		BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

		String partitionType = reader.readLine();
		System.out.printf("Tipo Partizione:%s \n", partitionType);

		String line = null;
		byte[] result = new byte[1000];
		while ((line = reader.readLine()) != null) {
			FileInputStream partitionedFileReader = new FileInputStream(
					file.getAbsoluteFile().getParent() + "\\" + line);
			while (partitionedFileReader.read(result) != -1) {
				fileOutputStream.write(result);
			}
			partitionedFileReader.close();
		}
		fileOutputStream.close();
		reader.close();
	}

	/**
	 * @param file - Il File di cui vogliamo estrarre l'estensione. Gestisce anche
	 *             path con '.' nel nome.
	 * @throws Exception Se non riusciamo a estrarre l'estensione.
	 */
	protected String getExtension(File file) throws Exception {
		String fileName = file.getPath();

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
			return fileName.substring(i + 1);
		} else {
			throw new Exception("Failed Parsing Extension");
		}
	}

	protected String getOutputPath(File file) throws Exception {
		String str = file.getName();

		if (str != null && str.contains(".")) {
			return outPath + '/' + str.substring(0, str.lastIndexOf('.'));
		} else
			throw new Exception("Failed Parsing File Extensions");

	}
}
