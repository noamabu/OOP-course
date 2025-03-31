package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
public class Image {

    private final Color[][] pixelArray;
    private Color[][] pixelArrayNormal; // Normalized pixel array for resized images
    private double[][] brightnessArray; // Brightness values for image squares
    private final int width; // Original image width
    private int widthNormal; // Width of the normalized (resized) image
    private final int height; // Original image height
    private int heightNormal; // Height of the normalized (resized) image

    /**
     * Loads an image from a file and initializes the pixel array based on the image content.
     *
     * @param filename The path to the image file.
     * @throws IOException If an error occurs during reading the image file.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Initializes the Image object with a predefined pixel array, width, and height.
     * This constructor is useful for manual image creation or modification.
     *
     * @param pixelArray The array of Colors representing the image.
     * @param width      The width of the image.
     * @param height     The height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }


    public double[][] getBrightnessArray() {
        return brightnessArray;
    }


    /**
     * Saves the current state of the image to a file. The saved image format is JPEG.
     *
     * @param fileName The name of the file to save the image to, without the extension.
     */
    public void saveImage(String fileName) {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getWidthNormal() {
        return widthNormal;
    }

    public int getHeightNormal() {
        return heightNormal;
    }


    /**
     * Resizes the image to dimensions that are powers of two. This method is often used in texture mapping
     * where such dimensions are required. The resizing strategy centers the original image within the new
     * dimensions,
     * filling the surrounding areas with white.
     */
    public void resizeImage() {
        this.widthNormal = nextPowerOfTwo(width);
        this.heightNormal = nextPowerOfTwo(height);
        this.pixelArrayNormal = new Color[this.heightNormal][this.widthNormal];


        // Fill the new array with white pixels
        Color white = new Color(255, 255, 255); // Assuming RGB color model
        for (int i = 0; i < this.heightNormal; i++) {
            for (int j = 0; j < this.widthNormal; j++) {
                this.pixelArrayNormal[i][j] = white;
            }
        }

        // Copy the original image pixels into the center of the new array
        int xOffset = (this.widthNormal - width) / 2;
        int yOffset = (this.heightNormal - height) / 2;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.pixelArrayNormal[yOffset + i][xOffset + j] = this.pixelArray[i][j];
            }
        }
    }

    /**
     * Calculates the next power of two greater than or equal to a given number.
     * This utility method is used to determine the next power of two, which is a common operation
     * when resizing images or performing operations that require dimensions to be powers of two,
     * such as in certain graphics processing algorithms.
     *
     * @param number The number for which to find the next power of two.
     * @return The smallest power of two that is greater than or equal to the given number.
     */
    private int nextPowerOfTwo(int number) {
        int power = 1;
        while (power < number) {
            power *= 2;
        }
        return power;
    }

    /**
     * Divides the image into equal-sized squares and calculates the average brightness for each square.
     * The brightness calculation converts each pixel to grayscale first, then averages these values for
     * the square.
     *
     * @param squaresPerRow The number of squares per row, which determines the total number of squares
     *                      (assuming a square image).
     */
    public void divideIntoSquaresAndCalculationBrightness(int squaresPerRow) {
        int squareSize = widthNormal / squaresPerRow;
        this.brightnessArray = new double[heightNormal / squareSize][widthNormal / squareSize];
        // Initialize the four-dimensional array to hold all sub-images
        // First two dimensions are for rows and columns of the grid of sub-images
        for (int y = 0, row = 0; y < heightNormal; y += squareSize, row++) {
            for (int x = 0, col = 0; x < widthNormal; x += squareSize, col++) {
                // Extract each square and store it in the corresponding position in the subImages array
                extractSquarePixels(x, y, squareSize, row, col);
            }
        }
    }

    /**
     * Extracts a square of pixels from the normalized image and calculates its average brightness.
     * This method iterates through each pixel in the specified square, converts it to grayscale,
     * and accumulates the total grayscale value. The average brightness of the square is then calculated
     * by dividing the total grayscale value by the number of pixels in the square and normalizing it to a
     * 0-1 scale.
     *
     * @param startX     The starting x-coordinate (column) of the square within the image.
     * @param startY     The starting y-coordinate (row) of the square within the image.
     * @param squareSize The size of the square (both width and height).
     * @param row        The row index in the brightnessArray to store the calculated average brightness.
     * @param col        The column index in the brightnessArray to store the calculated average brightness.
     */
    private void extractSquarePixels(int startX, int startY, int squareSize, int row, int col) {
        double totalGrayScale = 0;
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {
                totalGrayScale += getAverageGrayScaleFormPixel(this.pixelArrayNormal[startY + i][startX + j]);
            }
        }
        this.brightnessArray[row][col] = totalGrayScale / (squareSize * squareSize * 255);
    }


    /**
     * Calculates the average grayscale value of a pixel. This is a utility method used in brightness
     * calculations.
     * The grayscale value is determined using a weighted sum of the red, green, and blue components of the
     * color.
     *
     * @param pixel The Color object representing the pixel.
     * @return The average grayscale value of the pixel.
     */
    private static double getAverageGrayScaleFormPixel(Color pixel) {

        double red = pixel.getRed() * 0.2126;
        double green = pixel.getGreen() * 0.7152;
        double blue = pixel.getBlue() * 0.0722;
        double grayScale = (red + green + blue);
        return grayScale;
    }

}
