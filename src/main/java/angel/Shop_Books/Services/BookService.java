package angel.Shop_Books.Services;

import angel.Shop_Books.Entity.Book;
import angel.Shop_Books.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book findBookById(Integer idBook) {
        Book book= bookRepository.findById(idBook).orElse(null);
        return book;
    }

    @Override
    public List<Book> listBook() {
        return bookRepository.findAll();
    }

    @Override
    public void saveBook(Book book) {
    bookRepository.save(book);

    }

    @Override
    public void removeBook(Book book) {
        bookRepository.delete(book);

    }
}
