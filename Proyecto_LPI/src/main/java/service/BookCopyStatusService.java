package service;

import java.sql.SQLException;
import java.util.List;

import model.BookCopyStatus;
import repository.IBookCopyStatusRepository;

public class BookCopyStatusService implements IBookCopyStatusService {
    private final IBookCopyStatusRepository bookCopyStatusRepository;

    public BookCopyStatusService(IBookCopyStatusRepository bookCopyStatusRepository) {
        this.bookCopyStatusRepository = bookCopyStatusRepository;
    }

    public List<BookCopyStatus> findAll() throws SQLException, ClassNotFoundException {
        return bookCopyStatusRepository.findAll();
    }
}
