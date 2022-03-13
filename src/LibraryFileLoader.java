import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.lang.Float;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     * @throws UnsupportedOperationException Not implemented yet!
     */
    public List<BookEntry> parseFileContent() {
        List<BookEntry> bookEntries = new ArrayList<BookEntry>();
        String pattern = ",";
        int index = 0;

        if (!contentLoaded()) {
            System.err.println("ERROR: No content loaded before parsing.");
            return Collections.emptyList();

        } else {

            fileContent.remove(index);

            for (String str : fileContent) {
                String[] attributes = str.split(pattern);
                String title = attributes[0];
                String authorString = attributes[1];
                float rating = Float.parseFloat(attributes[2]);
                String ISBN = attributes[3];
                int pages = Integer.parseInt(attributes[4]);
                String[] authorArray = splitAuthors(authorString);

                BookEntry book = new BookEntry(title, authorArray, rating, ISBN, pages);

                //adds book object to book entries list
                bookEntries.add(book);

            }
            return bookEntries;
        }


    }

    /**
     * Splits the author string into separate values and creates an array of them.
     *
     * @return array filled with book authors.
     */
    private String[] splitAuthors(String authorString) {
        String[] array = authorString.split("-");
        return array;
    }

}

