package angel.Shop_Books.Repository;

import angel.Shop_Books.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
