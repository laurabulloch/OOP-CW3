
import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {

    /** Instance field for title. */
    private String title;

    /** Instance field for authors. */
    private String[] authors;

    /** Immutable instance field for rating. */
    private float rating;

    /** Immutable instance field for ISBN. */
    private String ISBN;

    /** Immutable instance field for page number. */
    private int pages;

    /**
     * Create the book entry constructor which initialises parameters while
     * making sure arguments are valid.
     *
     * @param title stores title of book.
     * @param authors stores books authors.
     * @param rating stores rating of book.
     * @param ISBN stores ISBN of book.
     * @param pages stores number of pages in book..
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException     if any of the given parameters are null.
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        Objects.requireNonNull(title, "Given title must not be null.");
        Objects.requireNonNull(authors, "Given authors must not be null.");
        Objects.requireNonNull(ISBN, "Given ISBN must not be null.");

        //checks title is valid
        if (title.equals(" ")) {
            throw new IllegalArgumentException("Invalid title.");
        } else{
            this.title = title;
        }

        this.authors = authors;

        //checks rating is valid
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Invalid rating. Needs to be between 0 and 5.");
        } else {
            this.rating = rating;
        }

        //checks ISBN is valid
       if (ISBN.equals(" ")) {
            throw new IllegalArgumentException("Invalid ISBN.");
        } else{
            this.ISBN = ISBN;
        }

        //checks pages is valid
        if (pages < 1) {
            throw new IllegalArgumentException("Invalid number of pages. Must be a positive integer.");
        } else {
            this.pages = pages;
        }

    }

    /**
     * getter for title
     *
     * @return title the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for authors
     *
     * @return authors the authors of the book
     */
    public String[] getAuthors() {
        return authors.clone();
    }

    /**
     * getter for rating
     *
     * @return rating the rating of the book
     */
    public float getRating() {
        return rating;
    }

    /**
     * getter for ISBN
     *
     * @return ISBN the ISBN of the book
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * getter for pages
     *
     * @return pages the number of pages in the book
     */
    public int getPages() {
        return pages;
    }

    /**
     * Override the toString method to output in the required format.
     */
@Override
    public String toString() {
        final String padding = "\n";
        StringBuilder bld = new StringBuilder();

        bld.append(title);
        bld.append(padding).append("by ").append(Arrays.toString(authors).replace("[","").replace("]",""));
        bld.append(padding).append("Rating: ").append(String.format("%.2f",rating));
        bld.append(padding).append("ISBN: ").append(ISBN);
        bld.append(padding).append(pages).append(" pages");

        return bld.toString();

    }

    /**
     * Override the equals method to compare 2 book entries.
     *
     * @param o an object e.g. a book entry
     */
@Override
    public boolean equals (Object o) {
        // check against itself
        if (o == this) {
            return true;
        }
        // check against null
        if (o == null) {
            return false;
        }
        // check type
        if (!(o instanceof BookEntry)) {
            return false;
        }
        BookEntry bookEntry = (BookEntry) o;

        return Objects.equals(title, bookEntry.title) && Objects.equals(authors, bookEntry.authors) && rating == bookEntry.rating && Objects.equals(ISBN, bookEntry.ISBN) && pages == bookEntry.pages;

    }

    /**
     * Override hashcode method to hash the fields of the object.
     *
     * @return hashed object
     */
@Override
    public int hashCode(){
        return Objects.hash(title, authors, rating, ISBN, pages);
}



}
