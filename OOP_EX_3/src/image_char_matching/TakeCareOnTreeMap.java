package image_char_matching;

import java.util.*;

/**
 * Manages a TreeMap specifically designed to associate double values, typically brightness levels, with
 * lists of characters.
 * This class provides functionality to add, remove, and find characters based on their associated double
 * values,
 * facilitating operations such as matching characters by brightness in image processing tasks.
 */
public class TakeCareOnTreeMap {
    private DoubleArrayOfCharacterTreeMap doubleArrayOfCharacterTreeMap;

    /**
     * Constructs a new instance of TakeCareOnTreeMap, initializing the underlying TreeMap.
     */
    public TakeCareOnTreeMap() {
    }

    /**
     * Resets and populates the TreeMap using the given HashMap. This method is typically used to
     * initialize or reinitialize the TreeMap with a new set of character-to-double value mappings.
     *
     * @param hashMap A HashMap containing character-to-double value mappings to populate the TreeMap.
     */
    public void resetDoubleSetsTreeMap(HashMap<Character, Double> hashMap) {
        this.doubleArrayOfCharacterTreeMap = new DoubleArrayOfCharacterTreeMap();
        for (Map.Entry<Character, Double> characterDoublePair : hashMap.entrySet()) {
            addPairToTree(characterDoublePair);
        }
    }

    /**
     * Adds a character and its associated double value to the TreeMap. If the double value already exists
     * as a key,
     * the character is added to the existing list. Otherwise, a new list is created and added to the TreeMap.
     *
     * @param characterDoublePair A Map.Entry containing the character and its double value.
     */
    public void addPairToTree(Map.Entry<Character, Double> characterDoublePair) {

        doubleArrayOfCharacterTreeMap.computeIfAbsent(characterDoublePair.getValue(),
                k -> new ArrayList<>()).add(characterDoublePair.getKey());
    }


    /**
     * Finds and returns a list of characters associated with a double value that is closest to the
     * specified brightness.
     * This method is useful for finding characters with brightness values nearest to a target value.
     *
     * @param brightness The target brightness value.
     * @return An ArrayList of characters with the closest brightness value to the specified target.
     */
    public ArrayList<Character> FindArrayCharByBrightness(double brightness) {

        Double floorKey = this.doubleArrayOfCharacterTreeMap.floorKey(brightness);
        Double ceilingKey = this.doubleArrayOfCharacterTreeMap.ceilingKey(brightness);

        // Compute the differences
        double diffFloor = floorKey != null ? Math.abs(brightness - floorKey) : Double.MAX_VALUE;
        double diffCeiling = ceilingKey != null ? Math.abs(brightness - ceilingKey) : Double.MAX_VALUE;

        // Determine which key is closer in terms of absolute value
        Double closestKey;
        if (diffFloor < diffCeiling) {
            closestKey = floorKey;
        } else {
            closestKey = ceilingKey;
        }

        // Get the value of the closest key
        if (closestKey != null) {
            return this.doubleArrayOfCharacterTreeMap.get(closestKey);
        } else {
            return null;
        }
    }

    /**
     * Removes a character and its associated brightness from the TreeMap. If the character is the only one
     * associated
     * with its brightness value, the brightness key is also removed from the TreeMap.
     *
     * @param character  The character to remove.
     * @param brightness The brightness value associated with the character.
     */
    public void removePairToTree(Character character, Double brightness) {
        ArrayList<Character> values = doubleArrayOfCharacterTreeMap.get(brightness);
        values.remove(character);
        // If the list is empty after removal, remove the key from the map
        if (values.isEmpty()) {
            doubleArrayOfCharacterTreeMap.remove(brightness);
        }
    }
}

