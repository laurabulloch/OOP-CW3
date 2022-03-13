import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Add command that allows books to be added to the database with the input of a file path.
 */
public class AddCmd extends LibraryCommand {

    /** Immutable instance field for path name. */
    private Path pathName;

    /** Indicator for a csv file. */
    private static final String REQUIRED_CHARACTERS = ".csv";

    /** Required minimum length to indicate csv file. */
    private static final int REQUIRED_CHARACTERS_LENGTH = 4;

    /**
     * Create the add command and initialise it with
     * the given command argument.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public AddCmd(String argumentInput) {
        super(CommandType.ADD, argumentInput);

    }

    /**
     * Parses the argument input. Checks the input has the required ending to
     * successfully read a csv file.
     *
     * @param argumentInput input from user.
     * @return true if argument is valid.
     * @return false if argument is invalid.
     * @throws NullPointerException if argument input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Path name cannot be null");
        pathName = convertToPath(argumentInput);
        String finalCharacters = "";

        if (argumentInput.length() > REQUIRED_CHARACTERS_LENGTH) {
            finalCharacters = argumentInput.substring(argumentInput.length()-REQUIRED_CHARACTERS_LENGTH);
        } else {
            finalCharacters = argumentInput;
        }

        if (finalCharacters.equals(REQUIRED_CHARACTERS)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Converts the argumentInput into a Path data type.
     *
     * @param argumentInput input from user.
     * @return path the path of the csv file.
     */
    private Path convertToPath(String argumentInput) {
        Path path = Paths.get(argumentInput);

        return path;
    }

    /**
     * Execute the add command. This adds data from the inputted csv
     * pathname into the library.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException when data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null.");
        data.loadData(pathName);
   }

}
