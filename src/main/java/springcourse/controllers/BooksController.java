package springcourse.controllers;

import springcourse.dao.BookDAO;
import springcourse.dao.PersonDAO;
import jakarta.validation.Valid;
import springcourse.models.Book;
import springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.utils.BookValidator;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String indexPage(Model model) {
        model.addAttribute("books", bookDAO.findAll());
        return "books/index";
    }

    @GetMapping("/new")
    public String createPage(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id,
                           Model model) {

        Optional<Book> book = bookDAO.getBookById(id);

        if (!book.isPresent()) {
            return "redirect:/books";
        }

        model.addAttribute("book", book.get());

        return "books/edit";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.getBookById(id);

        if (!book.isPresent()) {
            return "redirect:/books";
        }

        Book book1 = book.get();

        String patronName = null;

        if (book1.getPerson_id() != null) {
            Optional<Person> person = personDAO.getPersonById(book1.getPerson_id());
            if (person.isPresent()) {
                patronName = person.get().getName();
            }
        }

        model.addAttribute("persons", personDAO.findAll());
        model.addAttribute("book", book1);
        model.addAttribute("patronName", patronName);

        return "books/book";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book") @Valid Book bookUpdateData,
                         BindingResult bindingResult) {

        bookUpdateData.setId(id);

        bookValidator.validate(bookUpdateData, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(bookUpdateData);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/discharge")
    public String discharge(@PathVariable("id") int id) {
        bookDAO.dischargeByBookId(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/charge")
    public String charge(@PathVariable("id") int id,
                         @RequestParam("personId") int personId) {
        bookDAO.chargeByBookId(id, personId);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}/edit")
    public String delete(@PathVariable("id") int id) {
        bookDAO.removeById(id);
        return "redirect:/books";
    }

}
