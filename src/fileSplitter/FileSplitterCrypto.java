package fileSplitter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class FileSplitterCrypto extends FileSplitterByDimension {

	public FileSplitterCrypto(String outPath, int requestedParts) {
		super(outPath, requestedParts);

	}

	@Override
	void splitFile(File file, int dimensionOfEachPart) throws Exception {

		System.out.printf("Splitting file %s\n", file.getPath());

		if (file.length() == 0) {
			throw new Exception("Il file " + file.getPath() + " è vuoto!");

		}

		int howManyParts = (int) Math.ceil(file.length() / dimensionOfEachPart);
		int leftOverBytes = (int) (file.length() - (dimensionOfEachPart * howManyParts));

		FileInputStream fileInputStream = new FileInputStream(file);
		FileWriter bufferedPartitionLog = new FileWriter(this.outPath + "/" + file.getName() + ".partitioninfo");

		bufferedPartitionLog.write(SplitModalityEnum.getFromClass(this.getClass()).name());
		bufferedPartitionLog.write(System.lineSeparator());

		// Generate the key
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256); // Key size
		SecretKey key = keyGen.generateKey();
		String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("Decoded Key: " + key);

		bufferedPartitionLog.write(encodedKey);
		bufferedPartitionLog.write(System.lineSeparator());

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		for (int part = 1; part <= howManyParts; part++) {

			String outputPath = getOutputPath(file) + "." + part + ".split";
			System.out.printf("\t Writing partition %s\n", outputPath);

			BufferedOutputStream bufferedOutput = new BufferedOutputStream(
					new FileOutputStream(this.outPath + "\\" + file.getName() + "." + part + ".split"));
			bufferedPartitionLog.write(file.getName() + "." + part + ".split");
			bufferedPartitionLog.write(System.lineSeparator());
			byte[] readBytes = new byte[160];
			for (int numberOfReadBytes = 0; numberOfReadBytes < dimensionOfEachPart; numberOfReadBytes += 160) {

				fileInputStream.read(readBytes);
				bufferedOutput.write(cipher.update(readBytes));

			}

			bufferedOutput.close();
		}
		if (leftOverBytes > 0) {
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(
					new FileOutputStream(this.outPath + "\\" + file.getName() + "." + (howManyParts + 1) + ".split"));
			bufferedPartitionLog.write(file.getName() + "." + (howManyParts + 1) + ".split");
			bufferedPartitionLog.write(System.lineSeparator());

			byte[] readBytes = new byte[leftOverBytes];

			fileInputStream.read(readBytes);
			bufferedOutput.write(cipher.doFinal(readBytes));
			bufferedOutput.close();
		}

		fileInputStream.close();
		bufferedPartitionLog.close();
	}

}
