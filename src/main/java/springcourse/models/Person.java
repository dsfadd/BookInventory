package springcourse.models;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Person {
    private int id;

    @NotBlank(message = "Поле должно быть заполнено")
    @Pattern(regexp = "^[А-Я][а-я]+(?:-[А-Я][а-я]+)? [А-Я][а-я]+ [А-Я][а-я]+$", message = "Некорректный формат")
    private String name;

    @NotNull(message = "Поле должно быть заполнено")
    @Past(message = "Некорректная дата рождения")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public Person() {
    }

    public Person(String name, LocalDate birthday, int id) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
