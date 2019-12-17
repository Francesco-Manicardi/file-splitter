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
import javax.crypto.spec.IvParameterSpec;

/**
 * This class splits files and crypts them using AES algorithm with padding and
 * CBC (instead of EBC). EBC encrypts the blocks indipendently from each other,
 * while CBC uses XOR to make the encription more secure.
 */
public class FileSplitterCrypto extends FileSplitterByDimension {

	/**
	 * Instantiates a new file splitter with cryptography functionality.
	 *
	 * @param outPath             the output path in which split and crypted parts
	 *                            will end up
	 * @param dimensionOfEachPart the dimension of each split part.
	 */
	public FileSplitterCrypto(String outPath, int dimensionOfEachPart) {
		super(outPath, dimensionOfEachPart);

	}

	/**
	 * Split and crypt a file. Stores secret key and IV in base64 encoding in the
	 * .partitioninfo file.
	 *
	 * @param file                the file to split and crypt.
	 * @param dimensionOfEachPart the dimension of each encrypted part
	 * @throws Exception when file is empty or something goes wrong during IO.
	 */
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

		bufferedPartitionLog.write(SplitModalityEnum.getSplitModalityFromSplitterClass(this.getClass()).name());
		bufferedPartitionLog.write(System.lineSeparator());

		// Generate the key
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256); // Key size
		SecretKey key = keyGen.generateKey();
		String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
		System.out.println("Decoded Key: " + key);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		bufferedPartitionLog.write(encodedKey);
		bufferedPartitionLog.write(System.lineSeparator());
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// Get initialization vector for later usage in decription process.
		IvParameterSpec spec = cipher.getParameters().getParameterSpec(IvParameterSpec.class);
		byte[] iv = spec.getIV();

		String base64iv = Base64.getEncoder().encodeToString(iv);
		bufferedPartitionLog.write(base64iv);
		bufferedPartitionLog.write(System.lineSeparator());

		for (int part = 1; part <= howManyParts; part++) {

			String outputPath = getOutputPath(file) + "." + part + ".split";
			System.out.printf("\t Writing partition %s\n", outputPath);

			BufferedOutputStream bufferedOutput = new BufferedOutputStream(
					new FileOutputStream(this.outPath + File.separator + file.getName() + "." + part + ".split"));
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
			BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(
					this.outPath + File.separator + file.getName() + "." + (howManyParts + 1) + ".split"));
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
