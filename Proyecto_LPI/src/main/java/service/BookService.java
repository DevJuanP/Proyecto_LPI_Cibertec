package service;

import java.sql.SQLException;
import java.util.LinkedList;

import dto.shared.PagedResult;
import model.Book;
import model.BookStatus;
import repository.IBookRepository;
import repository.IBookStatusRepository;

public class BookService implements IBookService {
    private final IBookRepository bookRepository;
    private final IBookStatusRepository bookStatusRepository;

    public BookService(IBookRepository bookRepository, IBookStatusRepository bookStatusRepository) {
        this.bookRepository = bookRepository;
        this.bookStatusRepository = bookStatusRepository;
    }

    @Override
    public PagedResult<Book> getRegisteredBooks(int page, int pageSize, String search, 
            String authorId, String categoryId, String bookStatusId) throws SQLException, ClassNotFoundException {
        
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        
        int offset = (page - 1) * pageSize;
        
        int totalItems = bookRepository.count(search, authorId, categoryId, bookStatusId);
        
        LinkedList<Book> books = bookRepository.findAllPaginated(offset, pageSize, search, authorId, categoryId, bookStatusId);
        
        return new PagedResult<>(books, page, pageSize, totalItems);
    }

    @Override
    public int getTotalBooksCount() throws SQLException, ClassNotFoundException {
        return bookRepository.count();
    }

    @Override
    public int getActiveBooksCount() throws SQLException, ClassNotFoundException {
        BookStatus activeStatus = bookStatusRepository.findByName("Activo");

        return bookRepository.count(null, null, null, activeStatus.getBookStatusId());
    }

    @Override
    public Book findById(String bookId) throws SQLException, ClassNotFoundException {
        return bookRepository.findById(bookId);
    }

    @Override
    public void save(Book book) throws SQLException, ClassNotFoundException {
        bookRepository.save(book);
    }

    @Override
    public void update(Book book) throws SQLException, ClassNotFoundException {
        bookRepository.update(book);
    }

    @Override
    public void delete(String bookId) throws SQLException, ClassNotFoundException {
        bookRepository.delete(bookId);
    }
}