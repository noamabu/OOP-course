package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.TreeSet;

/**
 * Represents a shell for processing images.
 * This class initializes various components required for image processing.
 */
public class Shell {

    private static final char[] START_CHARS = {'0','1','2','3','4','5','6','7','8','9'};
    private static final String EXIT_COMMAND = "exit";
    private static final String CHARS_COMMAND = "chars";
    private final static String ADD_COMMAND = "add";
    private static final String ADD_AND_SPACE = "add ";
    private static final String ALL = "all";
    private static final int FIRST_ASCII_CHAR = 32;
    private static final int SPACE_ASCII_CHAR = 126;
    private static final char SPACE_CHAR = ' ';
    private static final char HYPHEN = '-';
    private static final int HYPHEN_POSITION = 1;
    private static final int FIRST_CHAR_POSITION = 0;
    private static final int SECOND_CHAR_POSITION = 2;
    private static final String FORMAT_ADD_ERROR = "Did not add due to incorrect format.";
    private static final String REMOVE_COMMAND = "remove";
    private static final String SPACE_WORD = "space";
    private static final String FORMAT_REMOVE_ERROR = "Did not remove due to incorrect format.";
    private static final String RES_COMMAND = "res";
    private static final String RES_AND_SPACE = "res ";
    private static final int FACTOR = 2;
    private static final int DOWN_RES = 0;
    private static final int UP_RES = 1;
    private static final String FORMAT_ERROR ="Did not change resolution due to exceeding boundaries.";
    private static final String RES_ARG_ERROR = "Did not change resolution due to incorrect format.";
    private static final String IMAGE_COMMAND = "image";
    private static final String IMAGE_COMMAND_AND_SPACE = "image ";
    private static final String IMAGE_ARG_ERROR = "Did not execute due to problem with image file.";
    private static final String SPACE_STRING = " ";
    private static final String REMOVE_AND_SPACE = "remove ";
    private static final String OUTPUT_COMMAND = "output";
    private static final String OUTPUT_AND_SPACE = "output ";
    private static final String OUTPUT_ERROR = "Did not change output method due to incorrect format.";
    private static final String OUTPUT_HTML = "html";
    private static final String OUTPUT_CONSOLE = "console";
    private static final String ASCII_ART_COMMAND = "asciiArt";
    private static final String FONT_NAME_HTML = "Courier New";
    private static final String FILE_OUTPUT_NAME = "out.html";
    private static final String ASCII_ERROR = "Did not execute. Charset is empty.";
    private static final String INCORRECT_COMMAND = "Did not execute due to incorrect command.";
    private static final String CAT_PATH = "cat.jpeg";
    private final SubImgCharMatcher charMatcher;
    private final TreeSet<Character> set;
    private AsciiOutput output;
    private Image image;
    private int minCharsInRow;
    private int resolution = 128;

    /**
     * Constructs a new Shell instance.
     *
     */
    public Shell() {
        this.output = new ConsoleAsciiOutput();
        this.set = new TreeSet<>();
        for (char c : START_CHARS)
            this.set.add(c);
        this.charMatcher = new SubImgCharMatcher(START_CHARS);
    }

    /**
     * Runs the shell command loop.
     * This method processes user commands and executes corresponding actions.
     */

    public void run(){
        if (!initialize()){
            return;
        }
        String command = "";
        while (!command.equals(EXIT_COMMAND)){
            System.out.print(">>> ");
            command = KeyboardInput.readLine();
            if (command.contains(CHARS_COMMAND)) {
                charsCommand(command);
            }
            else if (command.contains(ADD_COMMAND)) {
                addAndRemoveCommand(command, ADD_COMMAND);
            }
            else if (command.contains(REMOVE_COMMAND)) {
                addAndRemoveCommand(command, REMOVE_COMMAND);
            }
            else if (command.contains(RES_COMMAND)) {
                resCommand(command);
            }
            else if (command.contains(IMAGE_COMMAND)){
                imageCommand(command);
            }
            else if (command.contains(OUTPUT_COMMAND)){
                outputCommand(command);
            }
            else if (command.contains(ASCII_ART_COMMAND)){
                asciiArtCommand(command);
            }
            else if (!command.contains(EXIT_COMMAND) ||
                    (command.contains(EXIT_COMMAND) && command.length() > EXIT_COMMAND.length())){
                System.out.println(INCORRECT_COMMAND);
            }
        }
    }

    private boolean initialize() {
        try {
            this.image = new Image(CAT_PATH);
        }
        catch (IOException e){
            System.out.println(IMAGE_ARG_ERROR);
            return false;
        }
        this.image.resizeImage();
        this.minCharsInRow = Math.max(1, image.getWidthNormal() / image.getHeightNormal());
        return true;
    }

    private void asciiArtCommand(String command) {
        if (!command.startsWith(ASCII_ART_COMMAND)){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        if (command.length() > ASCII_ART_COMMAND.length()){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        if (this.set.isEmpty()){
            System.out.println(ASCII_ERROR);
            return;
        }
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(this.charMatcher, this.image,
                this.resolution);
        this.output.out(asciiArtAlgorithm.run());

    }

    private void outputCommand(String command) {
        if (!command.startsWith(OUTPUT_AND_SPACE)){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        if (command.length() == OUTPUT_AND_SPACE.length()){
            System.out.println(OUTPUT_ERROR);
            return;
        }
        String renderer = command.substring(OUTPUT_AND_SPACE.length());
        if (!renderer.equals(OUTPUT_HTML) && !renderer.equals(OUTPUT_CONSOLE)){
            System.out.println(OUTPUT_ERROR);
            return;
        }
        if (renderer.equals(OUTPUT_HTML)) {
            this.output = new HtmlAsciiOutput(FILE_OUTPUT_NAME, FONT_NAME_HTML);
        }
        else {
            this.output = new ConsoleAsciiOutput();
        }
    }

    private void imageCommand(String command) {
        if (!command.startsWith(IMAGE_COMMAND_AND_SPACE)){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        if (command.length() == IMAGE_COMMAND_AND_SPACE.length()){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        String pathImage = command.substring(IMAGE_COMMAND_AND_SPACE.length());
        try{
            this.image = new Image(pathImage);
            this.image.resizeImage();
        } catch (IOException e) {
            System.out.println(IMAGE_ARG_ERROR);
        }
    }

    private void resCommand(String command) {
        if (!command.startsWith(RES_AND_SPACE)){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        if (command.length() == RES_AND_SPACE.length()){
            System.out.println(RES_ARG_ERROR);
            return;
        }
        String resArgument = command.substring(RES_AND_SPACE.length());
        if (resArgument.equals("up")){
            handelUp();
        }
        else if (resArgument.equals("down")) {
            handelDown();
        }
        else {
            System.out.println(RES_ARG_ERROR);
        }

    }

    private void handelDown() {
        if (checkCorrect(DOWN_RES)){
            this.resolution /= FACTOR;
            System.out.println("Resolution set to " + this.resolution + ".");
        }
        else {
            System.out.println(FORMAT_ERROR);
        }

    }

    private void handelUp() {
        if (checkCorrect(UP_RES)){
            this.resolution *= FACTOR;
            System.out.println("Resolution set to " + this.resolution + ".");
        }
        else {
            System.out.println(FORMAT_ERROR);
        }
    }

    private boolean checkCorrect(int i) {
        if (i == UP_RES){
            return resolution*FACTOR <= this.image.getWidthNormal();
        }
        return resolution/FACTOR >= this.minCharsInRow;
    }

    private void addToList(char c){
        if (this.set.add(c)) {
            this.charMatcher.addChar(c);
        }
    }

    private void removeFromList(char c){
        if (this.set.remove(c)) {
            this.charMatcher.removeChar(c);
        }
    }

    private void addAndRemoveCommand(String command, String operator) {
        String argument;
        if (!goodStartsWith(command)){
            return;
        }
        if (command.length() == operator.length() + 1){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        if (operator.equals(ADD_COMMAND)){
            argument = command.substring(ADD_AND_SPACE.length());
        }
        else {
            argument = command.substring(REMOVE_AND_SPACE.length());
        }
        if (argument.equals(ALL)) {
            allOperation(operator);
        } else if (argument.equals(SPACE_WORD)) {
            addOrRemove(SPACE_CHAR, operator);
        } else if (argument.length() == 1) {
            char c = argument.charAt(0);
            addOrRemove(c, operator);
        } else if (argument.length() == 3 &&
                argument.charAt(HYPHEN_POSITION) == HYPHEN) {
            characterRange(argument, operator);
        } else {
            errorHandlingAddRemove(operator, argument);
        }
    }

    private void allOperation(String operator) {
        if (operator.equals(ADD_COMMAND)) {
            for (int i = FIRST_ASCII_CHAR; i <= SPACE_ASCII_CHAR; i++) {
                addOrRemove((char) i, operator);
            }
        }
        else {
            TreeSet<Character> copy = new TreeSet<>(this.set);
            for (char c: copy){
                addOrRemove(c, operator);
            }
        }
    }

    private boolean goodStartsWith(String command) {
        if (!command.startsWith(ADD_AND_SPACE) && !command.startsWith(REMOVE_AND_SPACE)){
            System.out.println(INCORRECT_COMMAND);
            return false;
        }
        return true;
    }

    private void errorHandlingAddRemove(String operator, String argument) {
        if (argument.length() != 1 && argument.length() != 3){
            if (operator.equals(ADD_COMMAND)){
                System.out.println(FORMAT_ADD_ERROR);
            }
            else {
                System.out.println(FORMAT_REMOVE_ERROR);
            }
        }
        else if (argument.length() == 3 && argument.charAt(1) != HYPHEN){
            if (operator.equals(ADD_COMMAND)){
                System.out.println(FORMAT_ADD_ERROR);
            }
            else {
                System.out.println(FORMAT_REMOVE_ERROR);
            }
        }
    }

    private void addOrRemove(char c, String operator) {
        if (operator.equals(ADD_COMMAND)){
            addToList(c);
        }
        else {
            removeFromList(c);
        }
    }


    private void characterRange(String addArgument, String operator) {
        char firstChar = addArgument.charAt(FIRST_CHAR_POSITION);
        char secondChar = addArgument.charAt(SECOND_CHAR_POSITION);
        if (firstChar < secondChar){
            for (int i = firstChar; i <= secondChar; i++){
                addOrRemove((char) i, operator);
            }
        }
        else {
            for (int i = secondChar; i <= firstChar; i++){
                addOrRemove((char) i, operator);
            }
        }
    }

    private void charsCommand(String command) {
        if (!command.startsWith(CHARS_COMMAND)){
            System.out.println(INCORRECT_COMMAND);
            return;
        }
        for (char c : this.set) {
            System.out.print(c + SPACE_STRING);
        }
        System.out.println();
    }

    /**
     * the main function that run the shell
     * @param args not used
     */
    public static void main(String[] args) {
        new Shell().run();
    }
}
