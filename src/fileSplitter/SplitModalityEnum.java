package fileSplitter;

import java.util.Arrays;

public enum SplitModalityEnum {
	EQUAL_DIM("Dimensione Uguale Per Ogni Parte", "Dimensione:", FileSplitterByDimension.class, FileStitcherCore.class),
	ENCRYPTED_EQUAL_DIM("Dimensione Uguale Per Ogni Parte + Crittografia", "Dimensione:", FileSplitterCrypto.class,
			FileStitcherCrypto.class),
	COMPRESSED_EQUAL_DIM("Dimensione Uguale Per Ogni Parte + Compressione", "Dimensione:", FileSplitterCompressed.class,
			FileStitcherCompressed.class),
	NUMBER_OF_PARTS("Specifico Numero Di Parti", "       N° Parti:", FileSplitterCore.class, FileStitcherCore.class);

	protected String hint;
	protected String description;
	protected Class<? extends FileSplitterCore> splitterClass;
	protected Class<? extends FileStitcherCore> stitcherClass;

	SplitModalityEnum(String d, String h, Class<? extends FileSplitterCore> splitterC,
			Class<? extends FileStitcherCore> stitcherC) {
		description = d;
		hint = h;
		splitterClass = splitterC;
		stitcherClass = stitcherC;
	}

	public static SplitModalityEnum getFromClass(Class<? extends FileSplitterCore> c) {
		return Arrays.stream(values()).filter(e -> e.splitterClass.equals(c)).findFirst().orElse(null);
	}

}
