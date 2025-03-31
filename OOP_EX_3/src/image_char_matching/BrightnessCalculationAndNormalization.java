package image_char_matching;

import java.util.*;

/**
 * Manages the calculation and normalization of brightness values for characters based on their pixel
 * representation.
 * This class supports determining characters with minimum, medium, and maximum brightness and normalizes
 * these values to facilitate consistent representation.
 * It employs a hashmap to track the original and normalized brightness values of characters and array
 * lists to manage characters at brightness extremes.
 *
 * @author Yoav Barak
 */
public class BrightnessCalculationAndNormalization {
    private static final int NUMBER_OF_PIXEL_IN_ROW = 16;
    private static final String MID_CHAR = "Is mid char";
    private static final String MIN_CHAR = "Is min char!";
    private static final String MAX_CHAR = "Is max char!";

    private final HashMap<Character, Double> allLetterAndBrightness = new HashMap<>();
    private HashMap<Character, Double> allLetterAndBrightnessNormal;

    /**
     * Retrieves the mapping of all characters to their respective brightness values.
     * This method provides access to the raw brightness calculations for each character,
     * allowing for further analysis or processing outside of the class.
     *
     * @return A HashMap<Character, Double> where each key is a character and each value is its calculated
     * brightness.
     */
    public HashMap<Character, Double> getAllLetterAndBrightness() {
        return allLetterAndBrightness;
    }

    private final ArrayList<Pair<Character, Double>> maxBrightnessLetter = new ArrayList<>();
    private final ArrayList<Pair<Character, Double>> minBrightnessLetter = new ArrayList<>();

    /**
     * Retrieves the mapping of all characters to their respective normalized brightness values.
     * This method offers access to the normalized brightness values of characters, where normalization
     * adjusts the brightness values to a common scale, facilitating comparisons and further processing.
     *
     * @return A HashMap<Character, Double> containing the characters and their normalized brightness values.
     * Each key in the HashMap represents a character, and the value is its normalized brightness.
     */
    public HashMap<Character, Double> getAllLetterAndBrightnessNormal() {
        return allLetterAndBrightnessNormal;
    }


    /**
     * Initializes a new instance of the BrightnessCalculationAndNormalization class.
     * Sets up the initial conditions for the class by populating the minBrightnessLetter and
     * maxBrightnessLetter lists
     * with a default character and its artificially high and low brightness values, respectively.
     * This constructor ensures that the class is ready to calculate and normalize the brightness of
     * additional characters
     * by providing baseline maximum and minimum brightness characters for comparison.
     */
    public BrightnessCalculationAndNormalization() {
        this.minBrightnessLetter.add(new Pair<Character,
                Double>('#', 999.0));
        this.maxBrightnessLetter.add(new Pair<Character,
                Double>('#', (double) -999));
    }

    /**
     * Calculates and stores the brightness for each character in the given character array.
     * Iterates through the provided array of characters, calculating the brightness for each character
     * using the AddLetterAndBrightness method. This process involves determining the character's pixel
     * density and updating internal data structures to reflect each character's brightness.
     *
     * @param charset An array of characters for which to calculate brightness values.
     */
    public void ArrayBrightnessCalculation(char[] charset) {
        for (char letter : charset) {
            AddLetterAndBrightness(letter);

        }
    }

    /**
     * Adds a character to the internal mapping with its calculated brightness value and updates the
     * minimum and maximum
     * brightness character lists if necessary. This method calculates the brightness of the provided
     * character,
     * stores the result in a mapping of characters to their brightness, and checks if this new entry should
     * update the lists of characters with the minimum or maximum brightness values.
     *
     * @param letter The character to add and calculate brightness for.
     * @return A boolean indicating whether the minimum or maximum brightness character list was updated.
     * Returns true if either the minimum or maximum brightness list was updated, false otherwise.
     */
    public boolean AddLetterAndBrightness(char letter) {
        Character character = letter;
        Double brightness = CalculationBrightness(letter);
        this.allLetterAndBrightness.put(character, brightness);
        return UpdateMinMaxAddCase(character, brightness);
    }

    /**
     * Calculates the brightness of a given character based on its pixel representation.
     * This method converts the character into a 2D boolean array representing its pixel matrix,
     * where each 'true' value indicates a pixel that contributes to the character's visual representation.
     * The brightness is then calculated as the ratio of 'true' pixels to the total number of pixels in a row,
     * effectively measuring the density of the character's representation.
     *
     * @param letter The character whose brightness is to be calculated.
     * @return The calculated brightness value of the character, as a Double. This value represents the
     * density of the character's visual representation and is normalized by the number of pixels
     * in a row.
     */

    public Double CalculationBrightness(char letter) {
        boolean[][] boolArray = CharConverter.convertToBoolArray(letter);
        int numberOfTrue = CountNumberOfTrue(boolArray);
        return (double) numberOfTrue / NUMBER_OF_PIXEL_IN_ROW;
    }


    /**
     * Updates the lists of characters with the minimum and maximum brightness based on the given
     * character's brightness.
     * If the character's brightness matches or sets a new minimum or maximum, the respective list is updated.
     * This method ensures that the minimum and maximum brightness values are accurately reflected in the
     * class state.
     *
     * @param character  The character being added or updated.
     * @param brightness The brightness value of the character.
     * @return A boolean indicating whether an update was made to the minimum or maximum brightness lists.
     * Returns true if the minimum or maximum brightness was updated, false otherwise.
     */
    private boolean UpdateMinMaxAddCase(Character character, Double brightness) {
        if (Objects.equals(minBrightnessLetter.getFirst().getBrightness(), brightness)) {
            minBrightnessLetter.add(new Pair<>(character, brightness));
        }
        if (minBrightnessLetter.getFirst().getBrightness() > brightness) {
            minBrightnessLetter.clear();
            minBrightnessLetter.add(new Pair<>(character, brightness));
            return true;
        }

        if (Objects.equals(maxBrightnessLetter.getFirst().getBrightness(), brightness)) {
            maxBrightnessLetter.add(new Pair<>(character, brightness));
        }
        if (maxBrightnessLetter.getFirst().getBrightness() < brightness) {
            maxBrightnessLetter.clear();
            maxBrightnessLetter.add(new Pair<>(character, brightness));
            return true;
        }
        return false;
    }

    /**
     * Updates the minimum and maximum brightness lists by removing entries with the specified brightness
     * value.
     * This method iterates through the lists of characters with the minimum and maximum brightness,
     * removing any that match the given brightness value. If removing an entry results in an empty list,
     * the output is adjusted to indicate whether the minimum or maximum brightness character has been
     * removed.
     *
     * @param brightness The brightness value for which matching characters should be removed from the
     *                   min/max lists.
     * @return A String indicating the result of the operation:
     * - Returns {@link #MID_CHAR} if neither the minimum nor maximum brightness list is emptied.
     * - Returns {@link #MIN_CHAR} if the removal empties the minimum brightness list.
     * - Returns {@link #MAX_CHAR} if the removal empties the maximum brightness list.
     */
    public String UpdateMinMaxRemoveCase(Double brightness) {
        String output = MID_CHAR;
        for (Pair<Character, Double> currPair : minBrightnessLetter) {
            if (Objects.equals(currPair.getBrightness(), brightness)) {
                minBrightnessLetter.remove(currPair);
                if (minBrightnessLetter.isEmpty()) {
                    output = MIN_CHAR;
                }
            }
        }
        for (Pair<Character, Double> currPair : maxBrightnessLetter) {
            if (Objects.equals(currPair.getBrightness(), brightness)) {
                maxBrightnessLetter.remove(currPair);
                if (maxBrightnessLetter.isEmpty()) {
                    output = MAX_CHAR;
                }
            }
        }
        return output;
    }

    /**
     * Normalizes the brightness values of all characters stored in the class.
     * This method recalculates the brightness values to ensure they are scaled relative to the characters
     * with the minimum
     * and maximum brightness. The normalization process adjusts each character's brightness so that the
     * range spans uniformly
     * from the character with the lowest brightness to the character with the highest brightness.
     * The result is stored in a separate hashmap, preserving the original brightness values for reference.
     */
    public void NormalizeAllLetterAndBrightness() {
        this.allLetterAndBrightnessNormal = new HashMap<>();
        for (Map.Entry<Character, Double> currPair : this.allLetterAndBrightness.entrySet()) {
            NormalizeLetterAndBrightness(currPair);
        }
    }

    /**
     * Normalizes the brightness of a specific character based on the minimum and maximum brightness values
     * recorded.
     * This method takes a character and its current brightness value, recalculates the brightness to fit
     * within the normalized
     * scale established by the minimum and maximum brightness characters, and updates the normalized
     * brightness mapping.
     *
     * @param currPair A Map.Entry<Character, Double> representing the character and its original
     *                 brightness value.
     * @return A Pair<Character, Double> containing the character and its new, normalized brightness value.
     * This pair reflects the adjusted brightness value that ensures a uniform distribution across
     * the set range from the minimum to the maximum brightness values observed.
     */
    public Pair<Character, Double> NormalizeLetterAndBrightness(Map.Entry<Character, Double> currPair) {
        Double newCharBrightness =
                (currPair.getValue() - this.minBrightnessLetter.getFirst().getBrightness()) /
                        (this.maxBrightnessLetter.getFirst().getBrightness() -
                                this.minBrightnessLetter.getFirst().getBrightness());
        Pair<Character, Double> characterDoublePair = new Pair<>(currPair.getKey(), newCharBrightness);
        this.allLetterAndBrightnessNormal.put(characterDoublePair.getLetter(),
                characterDoublePair.getBrightness());
        return characterDoublePair;
    }

    /**
     * Counts the number of true values in a 2D boolean array representing a character's pixel matrix.
     * This method iterates over each element in the boolean array, counting how many times a true value
     * occurs.
     * The count of true values is indicative of the character's visual density or brightness, as each true
     * value
     * represents an 'active' or 'lit' pixel in the character's representation.
     *
     * @param boolArray A 2D boolean array representing the pixel matrix of a character.
     * @return An int representing the number of true values found in the array, which correlates to the
     * character's brightness.
     */
    private static int CountNumberOfTrue(boolean[][] boolArray) {
        short numOfTrue = 0;
        for (short row = 0; row < NUMBER_OF_PIXEL_IN_ROW; ++row) {
            for (short col = 0; col < NUMBER_OF_PIXEL_IN_ROW; ++col) {
                if (boolArray[row][col]) {
                    ++numOfTrue;
                }
            }
        }
        return numOfTrue;
    }


}



