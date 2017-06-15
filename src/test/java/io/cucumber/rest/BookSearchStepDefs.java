package io.cucumber.rest;

import cucumber.api.Format;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.rest.model.Book;
import io.cucumber.rest.model.Library;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mustafadagher on 14/06/2017.
 */
public class BookSearchStepDefs {

    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @Given("^.+book with the title '(.+)', written by '(.+)', published in (.+)$")
    public void aBookWithTheTitleOneGoodBookWrittenByAnonymousPublishedInMarch(final String title, final String author, @Format("dd MMMMM yyyy") final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("^the customer searches for books published between (\\d+) and (\\d+)$")
    public void theCustomerSearchesForBooksPublishedBetweenAnd(@Format("yyyy") final Date from, @Format("yyyy") final Date to) throws Throwable {
        result = library.findBooks(from, to);
    }

    @Then("^(\\d+) books should have been found$")
    public void booksShouldHaveBeenFound(final int booksFound) throws Throwable {
        assertThat(result.size(), equalTo(booksFound));
    }

    @And("^Book (\\d+) should have the title '(.+)'$")
    public void bookShouldHaveTheTitleSomeOtherBook(final int position, final String title) throws Throwable {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
}
