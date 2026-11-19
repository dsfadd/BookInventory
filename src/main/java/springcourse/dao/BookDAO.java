package springcourse.dao;

import springcourse.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<Book> getBookById(int id) {
        return
                jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new BeanPropertyRowMapper<>(Book.class), id)
                        .stream()
                        .findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year, person_id) VALUES (?, ?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getPerson_id());
    }

    public List<Book> findAll() {
        return
                jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void update(Book bookUpdateData) {
        jdbcTemplate.update("UPDATE Book SET  title=?, author=?, year=?, person_id=? WHERE id=?",
                bookUpdateData.getTitle(),
                bookUpdateData.getAuthor(),
                bookUpdateData.getYear(),
                bookUpdateData.getPerson_id(),
                bookUpdateData.getId());
    }

    public void removeById(int id) {

        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);

    }

    public List<Book> getBooksByPersonId(int id) {

        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?",
                new BeanPropertyRowMapper<>(Book.class),
                id);
    }

    public void dischargeByBookId(int id) {

        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE id=?", id);
    }

    public void chargeByBookId(int id, int personId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", personId, id);

    }

    public boolean existsByTitleAndAuthor(String title, String author) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Book WHERE title = ? AND author = ?",
                Integer.class,
                title, author
        );
        return count != null && count > 0;
    }
}
