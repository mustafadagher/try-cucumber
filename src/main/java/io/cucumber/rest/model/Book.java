package io.cucumber.rest.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by mustafadagher on 14/06/2017.
 */
@Data
public class Book {
    private final String title;
    private final String author;
    private final Date published;
}
