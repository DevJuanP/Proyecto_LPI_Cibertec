package service;

import java.sql.SQLException;
import java.util.ArrayList;

import model.BookStatus;
import repository.IBookStatusRepository;

public class BookStatusService implements IBookStatusService {
    private final IBookStatusRepository bookStatusRepository;

    public BookStatusService(IBookStatusRepository bookStatusRepository) {
        this.bookStatusRepository = bookStatusRepository;
    }

    public String getActiveStatusId() throws SQLException, ClassNotFoundException {
        return bookStatusRepository.findByName("Activo").getBookStatusId();
    }

    public String getInactiveStatusId() throws SQLException, ClassNotFoundException {
        return bookStatusRepository.findByName("Inactivo").getBookStatusId();
    }

    public ArrayList<BookStatus> findAll() throws SQLException, ClassNotFoundException {
        return bookStatusRepository.findAll();
    }
}
