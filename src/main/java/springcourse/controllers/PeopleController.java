package springcourse.controllers;

import springcourse.dao.BookDAO;
import springcourse.dao.PersonDAO;
import jakarta.validation.Valid;
import springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @DeleteMapping("/{id}/edit")
    public String delete(@PathVariable("id") int id) {
        personDAO.removeById(id);

        return "redirect:/people";
    }

    @PatchMapping("/{id}/edit")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute @Valid Person person,
                         BindingResult bindingResult) {
        person.setId(id);

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDAO.update(person);

        return "redirect:/people";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String getCreatePage(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @GetMapping("/{id}/edit")
    public String getUpdatePage(@PathVariable("id") int id,
                                Model model) {
        Optional<Person> personToUpdate = personDAO.getPersonById(id);

        if (!personToUpdate.isPresent()) {
            return "redirect:/people";
        }

        model.addAttribute("person", personToUpdate.get());
        return "people/edit";
    }

    @GetMapping
    public String getIndexPage(Model model) {
        model.addAttribute("people", personDAO.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model) {

        Optional<Person> person=personDAO.getPersonById(id);

        if (!person.isPresent()) {
            return "redirect:/people";
        }

        model.addAttribute("person", person.get());
        model.addAttribute("personBooks", bookDAO.getBooksByPersonId(id));

        return "people/person";
    }
}
