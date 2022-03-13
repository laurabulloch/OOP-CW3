import java.util.ArrayList;
import java.util.Objects;

/**
 * Search command that allows books to be searched for by title.
 */
public class SearchCmd extends LibraryCommand {

    /** Instance field for search value. */
    private String searchValue;

    /**
     * Create the search command and initialise it with
     * the given command argument.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);

    }

    /**
     * Parses the argument input. Checks the input is not empty and contains no spaces.
     *
     * @param argumentInput input from user.
     * @return true if argument is valid.
     * @return false if argument is invalid.
     * @throws NullPointerException     if any of the given parameters are null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Search value cannot be null");

        if (argumentInput.length() == 0 || argumentInput.contains(" ")) {
            return false;

        } else {
            searchValue = argumentInput;
            return true;
        }

    }

    /**
     * Execute the search command. This searches for books where the title contains an inputted value.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException when data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null.");
        LibraryData libraryData = data;
        ArrayList<BookEntry> bookData = (ArrayList<BookEntry>) libraryData.getBookData();
        String lowerSearchValue = searchValue.toLowerCase();
        int matches = 0;

        //gets the title of a book and checks to see if this contains the search value
        for (int i = 0; i < bookData.size(); i++) {
            BookEntry book = bookData.get(i);
            String title = book.getTitle();
            String lowerTitle = title.toLowerCase();

            if (lowerTitle.contains(lowerSearchValue)) {
                matches = matches + 1;
                System.out.println(title);
            }

        }

        //if no matches are found display message
        if (matches == 0) {
            System.out.println("No hits found for search term: " + searchValue);

        }
    }
}
