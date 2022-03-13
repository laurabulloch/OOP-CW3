import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Group command that allows books in the database to be grouped by author or title.
 */
public class GroupCmd extends LibraryCommand {

    /** Instance field for group type */
    private String groupType;

    /** Indicator for a title group. */
    private static final String TITLE = "TITLE";

    /** Indicator for a author group. */
    private static final String AUTHOR = "AUTHOR";

    /**
     * Create the group command and initialise it with
     * the given command argument.
     *
     * @param argumentInput argument input as expected by the extending subclass.
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);

    }

    /**
     * Parses the argument input. Checks that the input is one of the only 2
     * accepted inputs.
     *
     * @param argumentInput input from user.
     * @return true if argument is valid.
     * @return false if argument is invalid.
     * @throws NullPointerException if argument input is null.
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "Given Group value must not be null.");

        if (argumentInput.equals(TITLE) || argumentInput.equals(AUTHOR)) {
            groupType = argumentInput;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Execute the group command. Checks that group type is valid and sets the
     * output depending on command selected.
     *
     * @param data book data to be considered for command execution.
     * @throws NullPointerException is data is null
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "Given library data must not be null.");

        if(data.getBookData().size() == 0) {
            emptyCase();
        } else {

            LibraryData libraryData = data;
            ArrayList<BookEntry> bookData = (ArrayList<BookEntry>) libraryData.getBookData();

            if (bookData.isEmpty()) {
                emptyCase();

            } else {

                if (groupType.equals(TITLE)) {
                    System.out.println("Grouped data by TITLE");
                    titleOutput(bookData);

                } else if (groupType.equals(AUTHOR)) {
                    System.out.println("Grouped data by AUTHOR");
                    authorOutput(bookData);

                } else {
                    throw new IllegalArgumentException("Invalid Group input");

                }
            }
        }

    }

    /**
     * Prints a message to console when a case is empty.
     */
    private void emptyCase() {
        System.out.println("The library has no book entries.");
    }

    /**
     * Constructs the output given when grouped by title and outputs it to console.
     *
     * @param bookData array list holding book data.
     */
    private void titleOutput(ArrayList<BookEntry> bookData) {
        String[] titleArray = getSortedTitleArray(bookData);
        List <String> numberValues = new ArrayList<String>();
        Boolean numberValuesPresent = false;

        //sets the current char to the first letter of the alphabet.
        char currentChar = 'A';

        System.out.println("## " + currentChar);

        for (int i = 0; i < titleArray.length; i++) {
            char firstCharTitle = titleArray[i].charAt(0);

            if (Character.isDigit(firstCharTitle)) {
                numberValues.add(titleArray[i]);
                numberValuesPresent = true;

            } else if (firstCharTitle != currentChar) {
                System.out.println("## " + firstCharTitle);
                currentChar = firstCharTitle;
                System.out.println("   " + titleArray[i]);

            } else {

                System.out.println("   " + titleArray[i]);
            }

        }

        if (numberValuesPresent) {
            digitCase(numberValues);
        }

    }

    /**
     * Constructs the output given when grouped by author and outputs it to console.
     *
     * @param bookData array list holding book data.
     */
    private void authorOutput(ArrayList<BookEntry> bookData) {
        String[] authorArray = getSortedAuthorArray(bookData);

        HashMap<String, ArrayList<String>> titleAndAuthors = new HashMap<String, ArrayList<String>>();

        //gets all the data for the book and sets to specific variables.
        for (int i = 0; i < bookData.size(); i++) {
            ArrayList<String> authors = new ArrayList<>(bookData.size());
            BookEntry book = bookData.get(i);
            String title = book.getTitle();
            String[] authorsArray = book.getAuthors();
            authors.addAll(Arrays.asList(authorsArray));
            titleAndAuthors.put(title, authors);
        }

        // prints the output in the correct format.
        for (int j = 0; j < authorArray.length; j++) {
            String newAuthor = authorArray[j];
            System.out.println("## " + newAuthor);

            for (Map.Entry<String, ArrayList<String>> entry : titleAndAuthors.entrySet()) {
                if (entry.getValue().contains(newAuthor)) {

                    System.out.println("   " + entry.getKey());

                    }
            }
        }
    }


    /**
     * Displays the cases where the first character of the title is a number after all
     * cases where first character is a letter have been displayed.
     *
     * @param numberValues list containing the values of titles that start with numbers.
     */
    private void digitCase(List <String> numberValues ) {
        String[] arrayNumberValues = new String[numberValues.size()];
        numberValues.toArray(arrayNumberValues);
        System.out.println("## [0-9]");

        for (int i = 0; i < arrayNumberValues.length; i++) {
            System.out.println("   " + arrayNumberValues[i]);
        }
    }

    /**
     * Sorts the titleArray into lexicographical order.
     *
     * @param bookData array list holding book data.
     * @return titleArray sorted titleArray.
     */
    private String[] getSortedTitleArray(ArrayList<BookEntry> bookData) {
        String[] titleArray = getTitleArray(bookData);
        Arrays.sort(titleArray);

        return titleArray;

    }

    /**
     * Sorts the authorArray into lexicographical order.
     *
     * @param bookData array list holding book data.
     * @return authorArray sorted authorArray.
     */
    private String[] getSortedAuthorArray(ArrayList<BookEntry> bookData) {
        String[] authorArray = getAuthorArray(bookData);
        Arrays.sort(authorArray);

        return authorArray;

    }

    /**
     * Creates an array filled with all the book titles in the database.
     *
     * @param bookData array list holding book data.
     * @return titleArray.
     */
    private String[] getTitleArray(ArrayList<BookEntry> bookData) {
        String[] titleArray = new String[bookData.size()];

        for (int i = 0; i < bookData.size(); i++) {
            BookEntry book = bookData.get(i);
            String title = book.getTitle();
            titleArray[i] = title;

        }
        return titleArray;

    }

    /**
     * Creates an array filled with all the authors in the database.
     *
     * @param bookData array list holding book data.
     * @return authorArray without duplicates.
     */
    private String[] getAuthorArray(ArrayList<BookEntry> bookData) {
        List <String> authorList = new ArrayList<String>();

        for (int i = 0; i < bookData.size(); i++) {
            BookEntry book = bookData.get(i);
            String[] authorsPerBook = book.getAuthors();
            authorList.addAll(Arrays.asList(authorsPerBook));
        }

        List <String> authorListNoDuplicates = removeDuplicates(authorList);
        String[] authorArray = new String[authorListNoDuplicates.size()];
        authorListNoDuplicates.toArray(authorArray);

        return authorArray;

    }
    /**
     * Creates an list of all authors in the database without duplicate values.
     *
     * @param authorList list holding author data.
     * @return an list of all the authors in the database with duplicates removed.
     */
    private List<String> removeDuplicates(List<String> authorList) {
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(authorList);
        List<String> authorListNoDuplicates = new ArrayList<>(hashSet);

        return authorListNoDuplicates;
    }

}
