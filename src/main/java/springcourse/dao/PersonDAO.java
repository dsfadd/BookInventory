package springcourse.dao;

import springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<Person> getPersonById(int id) {
        return
                jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id)
                        .stream().findFirst();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birthday) VALUES (?,?)",
                person.getName(),
                person.getBirthday());
    }

    public List<Person> findAll() {
        return
                jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void update(Person personUpdateData) {

        jdbcTemplate.update("UPDATE Person SET name=?, birthday=? WHERE id=?",
                personUpdateData.getName(),
                personUpdateData.getBirthday(),
                personUpdateData.getId());

    }

    public void removeById(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
