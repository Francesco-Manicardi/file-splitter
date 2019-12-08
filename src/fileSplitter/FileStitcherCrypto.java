package fileSplitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class FileStitcherCrypto extends FileStitcherCore {

	public FileStitcherCrypto(String outPath) {
		super(outPath);
	}

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

		FileOutputStream fileOutputStream = new FileOutputStream(getOutputPath(file));

		BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));

		String partitionType = reader.readLine();
		System.out.printf("Tipo Partizione:%s \n", partitionType);

		String encodedKey = reader.readLine();

		// decode the base64 encoded string
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		// rebuild key using SecretKeySpec
		SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		System.out.println("Decoded Key: " + key);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // Transformation of the algorithm

		// initialization vector
		String ivStringBase64 = reader.readLine();
		byte[] iv = Base64.getDecoder().decode(ivStringBase64);

		IvParameterSpec ivspec = new IvParameterSpec(iv);

		cipher.init(Cipher.DECRYPT_MODE, key, ivspec);

		System.out.printf("Tipo Partizione:%s \n", partitionType);

		String cryptedFileName;

		while ((cryptedFileName = reader.readLine()) != null) {
			System.out.println("Decrypting " + cryptedFileName);

			FileInputStream cryptedFileReader = new FileInputStream(
					file.getAbsoluteFile().getParent() + "\\" + cryptedFileName);
			byte[] byteBuffer = new byte[160];
			while (cryptedFileReader.read(byteBuffer) > 0) {
				fileOutputStream.write(cipher.update(byteBuffer));
			}
			cryptedFileReader.close();
		}

		fileOutputStream.close();
		reader.close();

	}

}
