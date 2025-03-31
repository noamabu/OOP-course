# ASCII Image Converter

## Overview
The ASCII Image Converter is a Java-based program that takes an image and converts it into an ASCII representation. The program processes the image by analyzing brightness values and mapping sections of the image to ASCII characters based on their intensity.

## Features
- **Load Image**: Reads an image file and extracts pixel data.
- **Brightness Calculation**: Converts image colors to grayscale values.
- **Resizing**: Adjusts the image to dimensions that are powers of two for uniform processing.
- **ASCII Conversion**: Maps brightness values to ASCII characters for visual representation.
- **Save Processed Image**: Allows saving the processed image in a specified format.

## Installation & Setup
### Prerequisites
- Java Development Kit (JDK) 8 or later
- A terminal or command prompt for execution

### Running the Program
1. Clone or download the repository.
2. Navigate to the project directory.
3. Compile the Java files:
   ```sh
   javac image/Image.java
   ```
4. Run the program with an image file as input:
   ```sh
   java image.Image <path-to-image>
   ```

## How It Works
1. **Load Image**: Reads an image file and stores pixel data.
2. **Normalize Size**: Resizes the image to a power-of-two dimension for easier processing.
3. **Brightness Calculation**: Converts color information to grayscale.
4. **Divide into Sections**: Splits the image into square regions and calculates brightness.
5. **ASCII Mapping**: Maps brightness values to a set of ASCII characters (darker shades use denser characters like `#`, `@`, while lighter ones use `.` or ` `).
6. **Output ASCII Art**: Prints or saves the ASCII version of the image.

## Example
If the input image has high contrast, the ASCII output might look like this:
```
@@@@@@##***++==--..
@##**++==--....   
##**++==--..      
**++==--         
```

## Future Enhancements
- Implement different ASCII character sets for finer detail.
- Add support for colored ASCII output.
- Optimize brightness calculation for better accuracy.

## License
This project is open-source and can be freely modified and distributed.

---
Enjoy converting images to ASCII art! 🎨

