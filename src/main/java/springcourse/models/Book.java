package springcourse.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.Objects;

public class Book {
    private Integer id;

    @NotBlank(message = "Поле должно быть заполнено")
    @Length(max = 100, message="Длина строки не более 100 символов")
    private String title;

    @NotBlank(message = "Поле должно быть заполнено")
    private String author;

    @NotNull(message = "Поле должно быть заполнено")
    @Range(min = 1900, max = 2026, message="Некорректный год(1900 - 2026)")
    private int year;

    private Integer person_id;

    public Book() {
    }

    public Book(Integer id, String title, String author, int year, Integer person_id) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.person_id = person_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

}
