package service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dto.shared.PagedResult;
import model.BookCopy;
import repository.IBookCopyRepository;
import repository.IBookCopyStatusRepository;

/**
 * Implementación del servicio de ejemplares de libros.
 */
public class BookCopyService implements IBookCopyService {
    
    private final IBookCopyRepository bookCopyRepository;
    private final IBookCopyStatusRepository bookCopyStatusRepository;
    
    public BookCopyService(IBookCopyRepository bookCopyRepository, 
                          IBookCopyStatusRepository bookCopyStatusRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookCopyStatusRepository = bookCopyStatusRepository;
    }
    
    @Override
    public BookCopy findById(String bookCopyId) throws SQLException, ClassNotFoundException {
        return bookCopyRepository.findById(bookCopyId);
    }

    @Override
    public PagedResult<BookCopy> getRegisteredBookCopies(int page, int pageSize, String search, 
            String bookId, String bookStatusId) throws SQLException, ClassNotFoundException {
        
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        
        int offset = (page - 1) * pageSize;
        
        int totalItems = bookCopyRepository.count(search, bookId, bookStatusId);
        
        LinkedList<BookCopy> bookCopies = bookCopyRepository.findAllPaginated(offset, pageSize, search, bookId, bookStatusId);
        
        return new PagedResult<>(bookCopies, page, pageSize, totalItems);
    }
    
    @Override
    public List<BookCopy> findByBook(String bookId) throws SQLException, ClassNotFoundException {
        return bookCopyRepository.findByBook(bookId);
    }
    
    @Override
    public List<BookCopy> findByStatus(String bookCopyStatusId) throws SQLException, ClassNotFoundException {
        return bookCopyRepository.findByStatus(bookCopyStatusId);
    }
    
    @Override
    public List<BookCopy> findAvailableByBook(String bookId) throws SQLException, ClassNotFoundException {
        return bookCopyRepository.findAvailableByBook(bookId);
    }
    
    @Override
    public void save(BookCopy bookCopy) throws SQLException, ClassNotFoundException {
        bookCopyRepository.save(bookCopy);
    }
    
    @Override
    public void saveBatch(String bookId, int quantity, String notes) throws SQLException, ClassNotFoundException {
        if (quantity < 1 || quantity > 100) {
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 100");
        }

        String statusDisponibleId = bookCopyStatusRepository.findByName("Disponible").getBookCopyStatusId();
        
        for (int i = 0; i < quantity; i++) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBookId(bookId);
            bookCopy.setBookCopyStatusId(statusDisponibleId);
            bookCopy.setNotes(notes);
            bookCopyRepository.save(bookCopy);
        }
    }
    
    @Override
    public void update(BookCopy bookCopy) throws SQLException, ClassNotFoundException {
        String statusAlquiladoId = bookCopyStatusRepository.findByName("Alquilado").getBookCopyStatusId();

        if (statusAlquiladoId.equals(bookCopy.getBookCopyStatusId())) {
            throw new IllegalArgumentException("No se puede cambiar el estado a 'Alquilado' desde este mantenimiento");
        }
        
        bookCopyRepository.update(bookCopy);
    }
    
    @Override
    public void updateStatusBatch(List<String> bookCopyIds, String newStatusId) 
            throws SQLException, ClassNotFoundException {
        
        String statusAlquiladoId = bookCopyStatusRepository.findByName("Alquilado").getBookCopyStatusId();
        if (statusAlquiladoId.equals(newStatusId)) {
            throw new IllegalArgumentException("No se puede cambiar el estado a 'Alquilado' desde este mantenimiento");
        }
        
        if (bookCopyIds == null || bookCopyIds.isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar al menos un ejemplar");
        }
        
        for (String bookCopyId : bookCopyIds) {
            BookCopy bookCopy = bookCopyRepository.findById(bookCopyId);
            if (bookCopy != null) {
                bookCopy.setBookCopyStatusId(newStatusId);
                bookCopyRepository.update(bookCopy);
            }
        }
    }
    
    @Override
    public void delete(String bookCopyId) throws SQLException, ClassNotFoundException {
        String statusAlquiladoId = bookCopyStatusRepository.findByName("Alquilado").getBookCopyStatusId();
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId);
        if (bookCopy != null && statusAlquiladoId.equals(bookCopy.getBookCopyStatusId())) {
            throw new IllegalArgumentException("No se puede eliminar un ejemplar que está alquilado");
        }
        
        bookCopyRepository.delete(bookCopyId);
    }
    
    @Override
    public int countByBook(String bookId) throws SQLException, ClassNotFoundException {
        return bookCopyRepository.countByBook(bookId);
    }
    
    @Override
    public int countAvailableByBook(String bookId) throws SQLException, ClassNotFoundException {
        return bookCopyRepository.countAvailableByBook(bookId);
    }
    
    @Override
    public String getAvailableStatusId() throws SQLException, ClassNotFoundException {
        return bookCopyStatusRepository.findByName("Disponible").getBookCopyStatusId();
    }
    
    @Override
    public String getRentedStatusId() throws SQLException, ClassNotFoundException {
        return bookCopyStatusRepository.findByName("Alquilado").getBookCopyStatusId();
    }
    
    @Override
    public String getMaintenanceStatusId() throws SQLException, ClassNotFoundException {
        return bookCopyStatusRepository.findByName("Mantenimiento").getBookCopyStatusId();
    }
    
    @Override
    public String getDiscontinuedStatusId() throws SQLException, ClassNotFoundException {
        return bookCopyStatusRepository.findByName("Descontinuado").getBookCopyStatusId();
    }
}