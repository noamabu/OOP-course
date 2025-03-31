package image_char_matching;

import java.util.ArrayList;
import java.util.AbstractMap;

import java.util.Map;
import java.util.Objects;

/**
 * A class designed for matching characters to sub-images based on brightness.
 * Utilizes BrightnessCalculationAndNormalization for calculating and normalizing brightness values of
 * characters,
 * and TakeCareOnTreeMap for organizing characters by their brightness levels. This allows for efficient
 * matching
 * of characters to specific brightness levels within images.
 *
 * @author Yoav Barak
 */
public class SubImgCharMatcher {

    private static final String MID_CHAR = "Is mid char";
    private final BrightnessCalculationAndNormalization brightnessCalculationAndNormalization;
    private final TakeCareOnTreeMap takeCareOnTreeMap;


    /**
     * Constructs a SubImgCharMatcher with a given charset. It calculates and normalizes the brightness for
     * each character
     * in the charset, and prepares a data structure for matching characters based on brightness.
     *
     * @param charset An array of characters to be used for matching against image brightness.
     */
    public SubImgCharMatcher(char[] charset) {
        this.brightnessCalculationAndNormalization = new BrightnessCalculationAndNormalization();
        this.takeCareOnTreeMap = new TakeCareOnTreeMap();
        this.brightnessCalculationAndNormalization.ArrayBrightnessCalculation(charset);
        this.brightnessCalculationAndNormalization.NormalizeAllLetterAndBrightness();
        this.takeCareOnTreeMap.resetDoubleSetsTreeMap(this.brightnessCalculationAndNormalization.
                getAllLetterAndBrightnessNormal());

    }

    /**
     * Matches a character to a given image brightness. Finds the character with the closest matching
     * brightness
     * and returns it.
     *
     * @param brightness The brightness value to match against.
     * @return The character with the closest brightness to the given value.
     */
    public char getCharByImageBrightness(double brightness) {
        ArrayList<Character> arrayCharByBrightness =
                this.takeCareOnTreeMap.FindArrayCharByBrightness(brightness);

        // Assume the first character has the smallest ASCII value initially
        char smallestChar = arrayCharByBrightness.getFirst();

        // Iterate through the ArrayList to find the character with the smallest ASCII value
        for (char ch : arrayCharByBrightness) {
            if (ch < smallestChar) {
                smallestChar = ch;
            }
        }
        return smallestChar;
    }

    /**
     * Adds a character to the matching system. Calculates its brightness, normalizes it, and updates the data
     * structure used for matching.
     *
     * @param c The character to add.
     */
    public void addChar(char c) {
        if (this.brightnessCalculationAndNormalization.getAllLetterAndBrightnessNormal().containsKey(c)) {
            return;
        }
        if (this.brightnessCalculationAndNormalization.AddLetterAndBrightness(c)) {
            // The min or max change!
            this.brightnessCalculationAndNormalization.NormalizeAllLetterAndBrightness();
            this.takeCareOnTreeMap.resetDoubleSetsTreeMap(
                    this.brightnessCalculationAndNormalization.getAllLetterAndBrightnessNormal());
        } else {
            Double brightness = this.brightnessCalculationAndNormalization.CalculationBrightness(c);
            Pair<Character, Double> characterDoublePairNormalization =
                    this.brightnessCalculationAndNormalization.NormalizeLetterAndBrightness(
                            new AbstractMap.SimpleEntry<>(c, brightness));
            Map.Entry<Character, Double> mapEntry =
                    new AbstractMap.SimpleEntry<>(characterDoublePairNormalization.getLetter(),
                            characterDoublePairNormalization.getBrightness());
            this.takeCareOnTreeMap.addPairToTree(mapEntry);
        }
    }

    /**
     * Removes a character from the matching system. If the character is present, it is removed, and the
     * matching
     * structures are updated accordingly.
     *
     * @param c The character to remove.
     */
    public void removeChar(char c) {
        if (!this.brightnessCalculationAndNormalization.getAllLetterAndBrightnessNormal().containsKey(c)) {
            return;
        }
        Double currBrightnessNormal =
                this.brightnessCalculationAndNormalization.getAllLetterAndBrightnessNormal().get(c);
        String ifMinOrMax =
                this.brightnessCalculationAndNormalization.UpdateMinMaxRemoveCase(currBrightnessNormal);
        this.brightnessCalculationAndNormalization.getAllLetterAndBrightness().remove(c);

        if (Objects.equals(ifMinOrMax, MID_CHAR)) {
            this.brightnessCalculationAndNormalization.getAllLetterAndBrightnessNormal().remove(c);
            this.takeCareOnTreeMap.removePairToTree(c, currBrightnessNormal);
        } else {
            // The min or max change!
            this.brightnessCalculationAndNormalization.NormalizeAllLetterAndBrightness();
            this.takeCareOnTreeMap.resetDoubleSetsTreeMap(
                    this.brightnessCalculationAndNormalization.getAllLetterAndBrightnessNormal());
        }

    }
}



