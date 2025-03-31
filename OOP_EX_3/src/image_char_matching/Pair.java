package image_char_matching;

/**
 * A generic class representing a pair of objects. This class is designed to hold two related objects together
 * in a single entity. It is particularly useful for cases where two pieces of data are closely related
 * and need to be passed around together within an application. The generic type parameters <K, V> allow
 * for flexible usage with any object types.
 *
 * @param <K> The type of the first object in the pair (key).
 * @param <V> The type of the second object in the pair (value).
 */
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Constructs a new Pair with specified key and value objects.
     *
     * @param letter     The object to be used as the key.
     * @param brightness The object to be used as the value.
     */
    public Pair(K letter, V brightness) {
        this.key = letter;
        this.value = brightness;
    }

    /**
     * Retrieves the key object of this pair.
     *
     * @return The key object of the pair.
     */
    public K getLetter() {
        return key;
    }

    /**
     * Retrieves the value object of this pair.
     *
     * @return The value object of the pair.
     */
    public V getBrightness() {
        return value;
    }

}
