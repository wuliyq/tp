package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    // ============================
    // Empty strings
    // ============================

    @Test
    public void isNonZeroUnsignedInteger_emptyString_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger(""));
    }

    @Test
    public void isNonZeroUnsignedInteger_whitespaceOnly_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));
    }

    // ============================
    // Not a number
    // ============================

    @Test
    public void isNonZeroUnsignedInteger_singleLetter_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
    }

    @Test
    public void isNonZeroUnsignedInteger_multipleLetters_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));
    }

    // ============================
    // Zero and zero prefix
    // ============================

    @Test
    public void isNonZeroUnsignedInteger_zero_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));
    }

    @Test
    public void isNonZeroUnsignedInteger_zeroPrefixedNumber_returnsTrue() {
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));
    }

    // ============================
    // Signed numbers
    // ============================

    @Test
    public void isNonZeroUnsignedInteger_negativeNumber_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
    }

    @Test
    public void isNonZeroUnsignedInteger_positiveSignNumber_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));
    }

    // ============================
    // Numbers with whitespace
    // ============================

    @Test
    public void isNonZeroUnsignedInteger_leadingAndTrailingSpaces_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 "));
    }

    @Test
    public void isNonZeroUnsignedInteger_internalSpaces_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0"));
    }

    // ============================
    // Larger than Integer.MAX_VALUE
    // ============================

    @Test
    public void isNonZeroUnsignedInteger_largerThanIntegerMaxValue_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));
    }

    // ============================
    // Valid numbers
    // ============================

    @Test
    public void isNonZeroUnsignedInteger_one_returnsTrue() {
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1"));
    }

    @Test
    public void isNonZeroUnsignedInteger_ten_returnsTrue() {
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }

    @Test
    public void isNonZeroUnsignedInteger_leadingZeroStillValid_returnsTrue() {
        // Leading zero allowed because Integer.parseInt("01") == 1
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));
    }

    @Test
    public void isNonZeroUnsignedInteger_multipleLeadingZeros_returnsTrue() {
        assertTrue(StringUtil.isNonZeroUnsignedInteger("00042"));
    }

    @Test
    public void isNonZeroUnsignedInteger_extremeIntegerMaxValue_returnsTrue() {
        assertTrue(StringUtil.isNonZeroUnsignedInteger(String.valueOf(Integer.MAX_VALUE)));
    }

    @Test
    public void isNonZeroUnsignedInteger_overflowingNumber_returnsFalse() {
        // Long value larger than Integer.MAX_VALUE
        String tooLarge = String.valueOf((long) Integer.MAX_VALUE + 100);
        assertFalse(StringUtil.isNonZeroUnsignedInteger(tooLarge));
    }

    @Test
    public void isNonZeroUnsignedInteger_plusZero_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+0"));
    }

    @Test
    public void isNonZeroUnsignedInteger_nonNumericSymbol_returnsFalse() {
        assertFalse(StringUtil.isNonZeroUnsignedInteger("#"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("12#"));
    }

    @Test
    public void isNonZeroUnsignedInteger_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.isNonZeroUnsignedInteger(null));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "aaa BBB"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *   - query word matches part of sentence word
     *
     * Possible scenarios returning false:
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_partialSubstringAtEnd_returnsTrue() {
        // Checks partial match at the end of a word
        assertTrue(StringUtil.containsWordIgnoreCase("java programming", "ming"));
    }

    @Test
    public void containsWordIgnoreCase_partialSubstringAtStart_returnsTrue() {
        // Checks partial match at the beginning of a word
        assertTrue(StringUtil.containsWordIgnoreCase("java programming", "jav"));
    }

    @Test
    public void containsWordIgnoreCase_sentenceWithSymbols_returnsTrue() {
        // Sentence contains punctuation and symbols
        assertTrue(StringUtil.containsWordIgnoreCase("error#404 not_found!", "404"));
        assertTrue(StringUtil.containsWordIgnoreCase("email@domain.com is valid", "domain"));
    }

    @Test
    public void containsWordIgnoreCase_wordWithSymbols_returnsTrue() {
        // Word itself includes a symbol
        assertTrue(StringUtil.containsWordIgnoreCase("abc@123 xyz", "@123"));
    }

    @Test
    public void containsWordIgnoreCase_sentenceWithNewlines_returnsTrue() {
        // Newline-separated words are valid because split("\\s+") handles them
        assertTrue(StringUtil.containsWordIgnoreCase("abc\nxyz", "xyz"));
        assertTrue(StringUtil.containsWordIgnoreCase("abc\nxyz", "x"));
    }

    @Test
    public void containsWordIgnoreCase_sentenceWithTabs_returnsTrue() {
        // Tab-separated words should still match
        assertTrue(StringUtil.containsWordIgnoreCase("first\tsecond\tthird", "second"));
    }

    @Test
    public void containsWordIgnoreCase_wordLongerThanSentenceWord_returnsFalse() {
        // The word is longer than any token in the sentence
        assertFalse(StringUtil.containsWordIgnoreCase("abc def", "abcdef"));
    }

    @Test
    public void containsWordIgnoreCase_sentenceWithNumbers_returnsTrue() {
        assertTrue(StringUtil.containsWordIgnoreCase("123 456", "45"));
        assertTrue(StringUtil.containsWordIgnoreCase("A1B2C3", "b2"));
    }

    @Test
    public void containsWordIgnoreCase_sentenceWithMixedDelimiters_returnsTrue() {
        // Mixed whitespace types
        assertTrue(StringUtil.containsWordIgnoreCase("apple\t banana  \ncherry", "ban"));
    }

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Does not match any word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bc")); // No match
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "aab")); // No match
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches a partial word, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "aa")); // First word
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Second word
        assertTrue(StringUtil.containsWordIgnoreCase("aaa aa ccc", "aa")); // First and second word
        assertTrue(StringUtil.containsWordIgnoreCase("AaA aa ccc", "aAa")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("AaA aa ccc12@", "CCc12@")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "bb")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aA")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  cc  ")); // Leading/trailing spaces

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bB"));
        assertTrue(StringUtil.containsWordIgnoreCase("Acc bc cac", "ac"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

    @Test
    public void getDetails_runtimeException_containsClassNameAndMessage() {
        RuntimeException e = new RuntimeException("runtime failed");
        String result = StringUtil.getDetails(e);
        assertTrue(result.contains("RuntimeException"));
        assertTrue(result.contains("runtime failed"));
    }

    @Test
    public void getDetails_exceptionWithoutMessage_stillContainsClassName() {
        Exception e = new Exception();
        String result = StringUtil.getDetails(e);
        assertTrue(result.contains("Exception"));
    }

}
