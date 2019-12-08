package fileSplitter;

import java.util.Arrays;

/**
 * An enum containing the split modalities and the appropriate splitter and
 * stitcher for each one of them. Also allows to search one of them by the
 * splitter class.
 */
public enum SplitModalityEnum {

	/**
	 * The mode where the user has to input the part size, which is equal for all
	 * files.
	 */
	EQUAL_DIM("Dimensione Uguale Per Ogni Parte", "Dimensione:", FileSplitterByDimension.class, FileStitcherCore.class),

	/**
	 * The mode where the user has to input the part size, which is equal for all
	 * files, and they get crypted.
	 */
	ENCRYPTED_EQUAL_DIM("Dimensione Uguale Per Ogni Parte + Crittografia", "Dimensione:", FileSplitterCrypto.class,
			FileStitcherCrypto.class),

	/**
	 * The mode where the user has to input the part size, which is equal for all
	 * files, and they get compressed in a <i>.zip</i> file .
	 */
	COMPRESSED_EQUAL_DIM("Dimensione Uguale Per Ogni Parte + Compressione", "Dimensione:", FileSplitterCompressed.class,
			FileStitcherCompressed.class),

	/**
	 * The mode where the user has to input the number of parts.
	 */

	NUMBER_OF_PARTS("Specifico Numero Di Parti", "       N° Parti:", FileSplitterCore.class, FileStitcherCore.class);

	/**
	 * A string that is shown to the user when he selects a modality so that he
	 * knows what he has to input.
	 */
	protected String hint;

	/** The description of the split modality. */
	protected String description;

	/** The appropriate splitter class for the split modality. */
	protected Class<? extends FileSplitterCore> splitterClass;

	/** The appropriate stitcher class for stitching files back together. */
	protected Class<? extends FileStitcherCore> stitcherClass;

	/**
	 * Instantiates a new split modality enum.
	 *
	 * @param d         the description
	 * @param h         the hint
	 * @param splitterC the splitter Class
	 * @param stitcherC the stitcher Class
	 */
	SplitModalityEnum(String d, String h, Class<? extends FileSplitterCore> splitterC,
			Class<? extends FileStitcherCore> stitcherC) {
		description = d;
		hint = h;
		splitterClass = splitterC;
		stitcherClass = stitcherC;
	}

	/**
	 * Gets the enum value from the splitter class.
	 *
	 * @param c the splitter class
	 * @return the first enum value which splitter class is equal to the input
	 *         splitter class.
	 */
	public static SplitModalityEnum getSplitModalityFromSplitterClass(Class<? extends FileSplitterCore> c) {
		return Arrays.stream(values()).filter(e -> e.splitterClass.equals(c)).findFirst().orElse(null);
	}

}
