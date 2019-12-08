package fileSplitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * The File Stitcher For Compressed Files.
 */
public class FileStitcherCompressed extends FileStitcherCore {

	/**
	 * Instantiates a new file stitcher for compressed parts.
	 *
	 * @param outPath the path were the stitched file should end up.
	 */
	public FileStitcherCompressed(String outPath) {
		super(outPath);
	}

	/**
	 * Stitch a single compressed file.
	 *
	 * @param file the control file (partitioninfo).
	 * @throws Exception when file is empty or something goes wrong during IO.
	 */
	@Override
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

		BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

		String partitionType = reader.readLine();
		System.out.printf("Tipo Partizione:%s \n", partitionType);
		String zipFilePath = reader.readLine();

		FileInputStream fin = new FileInputStream(file.getAbsoluteFile().getParent() + "\\" + zipFilePath);
		ZipInputStream zin = new ZipInputStream(fin);
		FileOutputStream fout = new FileOutputStream(getOutputPath(file));

		ZipEntry ze = null;
		while ((ze = zin.getNextEntry()) != null) {
			System.out.println("Unzipping " + ze.getName());
			for (int c = zin.read(); c != -1; c = zin.read()) {
				fout.write(c);
			}
			zin.closeEntry();
		}
		fout.close();
		zin.close();
		reader.close();
	}

}
