package angel.Shop_Books.Services;

import angel.Shop_Books.Entity.Book;

import java.util.List;

public interface IBookService {

    public Book findBookById(Integer idBook);

    public List<Book> listBook();

    public void saveBook(Book book);

    public void removeBook(Book book);
}
