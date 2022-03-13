import java.util.*;

/**
 * remove command that allows books to be removed from the database based on title or author.
 */
public class RemoveCmd extends LibraryCommand {

    /** Instance field for remove value. */
    private String removeValue;

    /** Indicates title option. */
    private static final String TITLE_SPACE = "TITLE ";

    /** Indicates author option. */
    private static final String AUTHOR_SPACE = "AUTHOR ";

    /** Indicates title option. */
    private static final String TITLE = "TITLE";

    /** Indicates author option. */
    private static final String AUTHOR = "AUTHOR";

    /**
     * Create the remove command and initialise it with
     * the given command argument.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);

    }

    /**
     * Parses the argument input. Checks the input is one of the two valid options
     *
     * @param argumentInput input from user.
     * @return true if argument is valid.
     * @return false if argument is invalid.
     * @throws NullPointerException   if any of the given parameters are null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given remove value must not be null.");

        if (argumentInput.equals(TITLE) || argumentInput.equals(TITLE_SPACE) || argumentInput.equals(AUTHOR) || argumentInput.equals(AUTHOR_SPACE)){
            return false;

        } else if(argumentInput.contains(TITLE) || argumentInput.contains(AUTHOR)){
            removeValue = argumentInput;
            return true;

        }else {
            return false;
        }

    }

    /**
     * Execute the remove command. This removes data from the book database based on
     * either an author value or title value.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException when data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null.");
        LibraryData libraryData = data;
        ArrayList<BookEntry> bookData = (ArrayList<BookEntry>) libraryData.getBookData();

        String removeString = getRemoveValues();

        if (removeValue.contains(TITLE)) {
            removeTitleOutput(bookData, removeString);

        } else if (removeValue.contains(AUTHOR)) {
            removeAuthorOutput(bookData, removeString);
        }

    }

    /**
     * Removes data based on title value.
     *
     * @param bookData book data to be considered for command execution.
     * @param removeString string containing value to be removed.
     */
    private void removeTitleOutput(ArrayList<BookEntry> bookData, String removeString) {
        int counter = 0;

        for (int i = 0; i < bookData.size(); i++) {
            BookEntry book = bookData.get(i);
            String title = book.getTitle();

            // breaks when title to be removedis found as there should be no duplicates.
            if (title.equals(removeString)) {
                bookData.remove(i);
                counter = counter + 1;
                System.out.println(removeString + ": removed successfully.");
                break;
            }
        }

        if (counter == 0) {
            System.out.println(removeString + ": not found.");
        }
    }

    /**
     * Removes data based on author value.
     *
     * @param bookData book data to be considered for command execution.
     * @param removeString string containing value to be removed.
     */
    private void removeAuthorOutput(ArrayList<BookEntry> bookData, String removeString) {
        int counter = 0;

        for (int i = 0; i < bookData.size(); i++) {
            BookEntry book = bookData.get(i);
            String[] authors = book.getAuthors();
            if (Arrays.asList(authors).contains(removeString)) {
                bookData.remove(i);
                counter = counter + 1;
            }
        }

        if (counter == 0) {
            System.out.println("0 books removed for author: " + removeString);

        } else {
            System.out.println(counter + " books removed for author: " + removeString);
        }
    }

    /**
     * Gets the string value the command to be removed.
     */
    private String getRemoveValues() {
        String[] attributes = removeValue.split("\\s", 2);
        String removeString = attributes[1];
        return removeString;
    }
}
