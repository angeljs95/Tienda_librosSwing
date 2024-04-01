package angel.Shop_Books.Views;

import angel.Shop_Books.Entity.Book;
import angel.Shop_Books.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class BookForm extends JFrame {
    BookService bookService;
    private JPanel panel;
    private JTextField idTxt;
    private JTable table_books;
    private JLabel jLabel_shopBook;
    private JTextField txt_libro_name;
    private JTextField txt_libro_autor;
    private JTextField txt_libro_price;
    private JTextField txt_libro_stock;
    private JButton btn_add;
    private JButton btn_update;
    private JButton btn_remove;

    private DefaultTableModel tableModel;

    @Autowired
    public BookForm(BookService bookService) {
        this.bookService = bookService;
        startForm();
        btn_add.addActionListener(e -> addBook());

        table_books.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectedBook();
                btn_add.setEnabled(false);
            }
        });
        btn_update.addActionListener(e -> updateBook());
        btn_remove.addActionListener(e -> deleteBook());
    }

    private void startForm() {
        setContentPane(panel);
        // Para cerrar la app con exito
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // asi se visualiza la app
        setVisible(true);
        //tamaño max de la app
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - getWidth() / 2);
        int y = (screenSize.height - getHeight() / 2);
        setLocation(x, y);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        idTxt = new JTextField("");
        idTxt.setVisible(false);


        this.tableModel = new DefaultTableModel(0, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headBoards = {"Id", "Libro", "Autor", "Precio", "Stock"};
        this.tableModel.setColumnIdentifiers(headBoards);
        // instaciamos el objeto jTable
        this.table_books = new JTable(tableModel);
        table_books.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listBooks();
    }

    private void listBooks() {
        // Limpiamos la tabla
        tableModel.setRowCount(0);
        List<Book> listBook = bookService.listBook();
        //Agregamos a la tabla los valores de la lista
        listBook.forEach(book -> {
            Object[] lineTable = {
                    book.getIdBook(),
                    book.getName(),
                    book.getAutor(),
                    book.getPrice(),
                    book.getStock(),
            };
            this.tableModel.addRow(lineTable);
        });
    }

    private void addBook() {
        // Leemos valores del formulario
        if (txt_libro_name.getText().equals("") || txt_libro_autor.getText().equals("")
                || txt_libro_price.getText().equals("") || txt_libro_stock.getText().equals("")) {
            showMesagge("All fields are required");
            txt_libro_name.requestFocusInWindow();
            return;
        }
        var bookName = txt_libro_name.getText();
        var bookAutor = txt_libro_autor.getText();
        var bookPrice = Double.parseDouble(txt_libro_price.getText());
        var stock = Integer.parseInt(txt_libro_stock.getText());
        Book newBook = new Book(null, bookName, bookAutor, bookPrice, stock);
        this.bookService.saveBook(newBook);
        showMesagge(" Successfully registered Book");
        cleanForm();
        listBooks();
    }

    private void updateBook() {
        if (idTxt.getText().equals("")) {
            showMesagge("you must select a book to modify");
        } else if (txt_libro_name.getText().equals("") || txt_libro_autor.getText().equals("")
                || txt_libro_price.getText().equals("") || txt_libro_stock.getText().equals("")) {
            showMesagge("All fields are required");
            txt_libro_name.requestFocusInWindow();
            return;
        } else if (idTxt.getText() != null) {
            Book book = bookService.findBookById(Integer.parseInt(idTxt.getText()));
            if (book != null) {
                book.setName(txt_libro_name.getText());
                book.setAutor(txt_libro_autor.getText());
                book.setPrice(Double.parseDouble(txt_libro_price.getText()));
                book.setStock(Integer.parseInt(txt_libro_stock.getText()));
                bookService.saveBook(book);
                showMesagge("the book has been updated");
                cleanForm();
                listBooks();
                btn_add.setEnabled(true);
            }
        }

    }

    private void deleteBook() {
        if (idTxt.getText().equals("")) {
            showMesagge(" Select the book to remove");
        } else {
            Book book = bookService.findBookById(Integer.parseInt(idTxt.getText()));
            bookService.removeBook(book);
            showMesagge(" The book has been removed");
            cleanForm();
            listBooks();
            btn_add.setEnabled(true);
        }


    }

    private void loadSelectedBook() {
        // los indices de la tabla inician en 0
        var index = table_books.getSelectedRow();
        if (index != -1) { //regresa -1 si no se selecciona ningun registro
            String idBook = table_books.getModel().getValueAt(index, 0).toString();
            idTxt.setText(idBook);
            txt_libro_name.setText(table_books.getValueAt(index, 1).toString());
            txt_libro_autor.setText(table_books.getValueAt(index, 2).toString());
            txt_libro_price.setText(table_books.getValueAt(index, 3).toString());
            txt_libro_stock.setText(table_books.getValueAt(index, 4).toString());
        }

    }

    private void showMesagge(String message) {
        JOptionPane.showMessageDialog(this, message);

    }

    private void cleanForm() {
        idTxt.setText("");
        txt_libro_name.setText("");
        txt_libro_autor.setText("");
        txt_libro_price.setText("");
        txt_libro_stock.setText("");
    }
}
