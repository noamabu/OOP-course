package image_char_matching;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Extends the TreeMap to create a specialized data structure that maps a Double key to an ArrayList of
 * Characters.
 * This class is designed to efficiently organize and access characters based on their associated numerical
 * values,
 * such as brightness levels. The TreeMap's natural ordering of keys allows for quick retrieval of
 * character lists
 * by their numerical value, making it ideal for scenarios where characters need to be sorted or grouped
 * by a specific metric. This implementation leverages the TreeMap's capabilities to maintain sorted order
 * of keys and provides an easy-to-use structure for storing and accessing characters based on double values.
 */

public class DoubleArrayOfCharacterTreeMap extends TreeMap<Double, ArrayList<Character>> {
}
