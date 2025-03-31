package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.awt.*;
import java.io.IOException;

/**
 * Implements an algorithm to convert images to ASCII art. This class utilizes the SubImgCharMatcher
 * to match image brightness levels to corresponding characters, facilitating the generation of ASCII art
 * from an image. The algorithm divides the image into squares, calculates the brightness of each square,
 * and matches it with a character that best represents the square's brightness level.
 */
public class AsciiArtAlgorithm {
    private final SubImgCharMatcher subImgCharMatcher;
    private final Image image;
    private final int numOfLetterInRow;

    /**
     * Constructs an AsciiArtAlgorithm with the specified matcher, image, and layout parameters.
     *
     * @param subImgCharMatcher The matcher used to associate image brightness levels with characters.
     * @param image             The image to convert to ASCII art.
     * @param numOfLetterInRow  The number of characters per row in the generated ASCII art.
     */
    public AsciiArtAlgorithm(SubImgCharMatcher subImgCharMatcher, Image image, int numOfLetterInRow) {
        this.subImgCharMatcher = subImgCharMatcher;
        this.image = image;
        this.numOfLetterInRow = numOfLetterInRow;
    }


    /**
     * Executes the ASCII art generation algorithm. This method divides the image into squares,
     * calculates the brightness for each square, and uses the SubImgCharMatcher to find the best matching
     * character for each square's brightness. The result is a 2D char array representing the ASCII art.
     *
     * @return A 2D char array where each element represents a character in the ASCII art.
     */
    public char[][] run() {
        this.image.divideIntoSquaresAndCalculationBrightness(numOfLetterInRow);
        double[][] a = image.getBrightnessArray();
        char[][] b = new char[a.length][a[0].length];
        for (int row = 0; row < a.length; ++row) {
            for (int col = 0; col < a[row].length; ++col) {
                b[row][col] = subImgCharMatcher.getCharByImageBrightness(a[row][col]);
            }
        }
        return b;
    }
}
