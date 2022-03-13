import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * List command that allows books to be displayed in a short or long format.
 */
public class ListCmd extends LibraryCommand {

    /** Instance field for list type. */
    private String listType;

    /** Indicator of where to split. */
    private static final String PATTERN = ",";

    /** Indicator for short list. */
    private static final String SHORT = "short";

    /** Indicator for long list. */
    private static final String LONG = "long";


    /**
     * Create the list command and initialise it with
     * the given command argument.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);

    }

    /**
     * Parses the argument input. Checks the input is one of the two valid options.
     *
     * @param argumentInput input from user.
     * @return true if argument is valid.
     * @return false if argument is invalid.
     * @throws NullPointerException     if any of the given parameters are null.
     */
    @Override
    protected boolean parseArguments(String argumentInput){
        Objects.requireNonNull(argumentInput, "Given remove value must not be null.");

        if (argumentInput.equals(SHORT) || argumentInput.equals(LONG) || argumentInput.equals("")) {
            listType = argumentInput;
            return true;

        } else {
            return false;
        }

    }

    /**
     * Execute the list command. This sets the required output depending on type of command.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException when data is null.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null.");
        LibraryData libraryData = data;
        List <BookEntry> bookData = libraryData.getBookData();

        int listSize = bookData.size();

        if (listSize == 0) {
            System.out.println("The library has no book entries.");

        }else {
            System.out.println(listSize + " books in library:");

            if (listType.equals(SHORT) || listType.equals("")) {
                printShort(bookData);
            }

            if(listType.equals(LONG)){
                printLong(bookData);
            }
        }

    }

    /**
     * Prints the long version of the list command.
     *
     * @param bookData book data to be considered for command execution.
     */
    private void printLong(List <BookEntry> bookData) {
        final String padding = "\n";

        for (int i = 0; i < bookData.size(); i++) {
            BookEntry book = bookData.get(i);
            String title = book.getTitle();
            String[] authors = book.getAuthors();
            float rating = book.getRating();
            String ISBN = book.getISBN();
            int pages = book.getPages();

            //string builder to print long list
            StringBuilder bld = new StringBuilder();
            bld.append(title);
            bld.append(padding).append("by ").append(Arrays.toString(authors).replace("[", "").replace("]", ""));
            bld.append(padding).append("Rating: ").append(String.format("%.2f",rating));
            bld.append(padding).append("ISBN: ").append(ISBN);
            bld.append(padding).append(pages).append(" pages");

            System.out.println(bld.toString());
            System.out.println("");
        }

    }

    /**
     * Prints the short version of the list command.
     *
     * @param bookData book data to be considered for command execution.
     */
    private void printShort (List <BookEntry> bookData) {
        for (int i = 0; i < bookData.size(); i++) {
            BookEntry book = bookData.get(i);
            String title = book.getTitle();

            StringBuilder bld = new StringBuilder();
            bld.append(title);

            System.out.println(bld.toString());

        }
    }

}
