package fileSplitter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * The Filter used for the text field input. We only accept positive integers.
 */
class OnlyPositiveIntegersFilter extends DocumentFilter {

	/**
	 * Add string to current text.
	 *
	 * @param fb     A direct access to the text field (otherwise it would
	 *               infinitely filter)
	 * @param offset the offset at which the string is being added
	 * @param string the string being added
	 * @param attr   the attributes associated with the inserted content. Can be
	 *               null if none.
	 * @throws BadLocationException when attempting to access a location that
	 *                              doesn't exist.
	 */
	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (test(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			// don't do anything because the action would result in the text field not
			// containing a positive integer.
		}
	}

	/**
	 * Function that tests if the text being inserted is a positive integer.
	 *
	 * @param text the text being inserted
	 * @return true only if the text can be parsed as an integer and is greater than
	 *         0.
	 */
	private boolean test(String text) {
		try {
			if (text.equals(""))
				return true;

			int value = Integer.parseInt(text);
			if (value > 0)
				return true;
		} catch (NumberFormatException e) {
			return false;
		}
		return false;
	}

	/**
	 * Replace content in current text.
	 *
	 * @param fb     A direct access to the text field (otherwise it would
	 *               infinitely filter)
	 * @param offset the offset at which the string is being added
	 * @param length the length of the content that replaces the old content.
	 * @param text   the string being added
	 * @param attrs  the attributes associated with the inserted content. Can be
	 *               null if none.
	 * @throws BadLocationException when attempting to access a location that
	 *                              doesn't exist.
	 */
	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);

		if (test(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		} else {

			// don't do anything because the action would result in the text field not
			// containing a positive integer.
		}

	}

	/**
	 * Remove content from current text.
	 *
	 * @param fb     A direct access to the text field (otherwise it would
	 *               infinitely filter)
	 * @param offset the offset at which the content is being deleted
	 * @param length the lenght of the content being deleted
	 * @throws BadLocationException when attempting to access a location that
	 *                              doesn't exist.
	 */
	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);

		if (test(sb.toString())) {
			super.remove(fb, offset, length);
		} else {

			// don't do anything because the action would result in the text field not
			// containing a positive integer.
		}

	}
}