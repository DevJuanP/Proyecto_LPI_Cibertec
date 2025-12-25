package service;

import repository.IRentalRepository;
import repository.IRentalStatusRepository;
import repository.IBookCopyRepository;
import repository.IBookCopyStatusRepository;
import dto.shared.PagedResult;
import model.Rental;
import model.RentalStatus;
import model.BookCopy;
import model.BookRentalStats;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class RentalService implements IRentalService {
    private final IRentalRepository rentalRepository;
    private final IRentalStatusRepository rentalStatusRepository;
    private final IBookCopyRepository bookCopyRepository;
    private final IBookCopyStatusRepository bookCopyStatusRepository;
    
    private static final BigDecimal DEFAULT_DAILY_RATE = new BigDecimal("5.00");

    public RentalService(IRentalRepository rentalRepository, 
                          IRentalStatusRepository rentalStatusRepository,
                          IBookCopyRepository bookCopyRepository,
                          IBookCopyStatusRepository bookCopyStatusRepository) {
        this.rentalRepository = rentalRepository;
        this.rentalStatusRepository = rentalStatusRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.bookCopyStatusRepository = bookCopyStatusRepository;
    }

    @Override
    public Rental findById(String rentalId) throws SQLException, ClassNotFoundException {
        return rentalRepository.findById(rentalId);
    }

    @Override
    public List<Rental> findAll() throws SQLException, ClassNotFoundException {
        return rentalRepository.findAll();
    }

    @Override
    public PagedResult<Rental> getRegisteredRentals(int page, int pageSize, String search, 
                                                      String userId, String rentalStatusId) 
                                                      throws SQLException, ClassNotFoundException {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        
        int offset = (page - 1) * pageSize;
        
        int totalItems = rentalRepository.countByFilters(search, userId, rentalStatusId);
        
        LinkedList<Rental> rentals = rentalRepository.findPaginated(offset, pageSize, search, userId, rentalStatusId);
        
        return new PagedResult<>(rentals, page, pageSize, totalItems);
    }

    @Override
    public void createRental(String userId, String bookId, int rentalDays, String notes) 
                             throws SQLException, ClassNotFoundException {
        BookCopy bookCopy = bookCopyRepository.findFirstAvailableCopyByBookId(bookId);

        if (bookCopy == null) {
            throw new IllegalStateException("No se encontrÃ³ ejemplar disponible para ser alquilado al usuario");
        }

        if (rentalRepository.existsActiveRentalForBookCopy(bookCopy.getBookCopyId())) {
            throw new IllegalStateException("Este ejemplar ya estÃ¡ alquilado");
        }
        
        String activeStatusId = rentalStatusRepository.findByName("En Proceso").getRentalStatusId();
        
        Rental rental = new Rental();
        rental.setUserId(userId);
        rental.setBookCopyId(bookCopy.getBookCopyId());
        rental.setRentalStatusId(activeStatusId);
        rental.setRentalDate(LocalDateTime.now());
        rental.setDueDate(LocalDateTime.now().plusDays(rentalDays));
        rental.setRentalDays(rentalDays);
        rental.setDailyRate(DEFAULT_DAILY_RATE);
        rental.setTotalCost(DEFAULT_DAILY_RATE.multiply(new BigDecimal(rentalDays)));
        rental.setNotes(notes);
        
        rentalRepository.save(rental);
        
        String rentedStatusId = getRentedBookCopyStatusId();
        
        if (rentedStatusId != null) {
            bookCopy.setBookCopyStatusId(rentedStatusId);
            bookCopyRepository.update(bookCopy);
        }
    }

    @Override
    public void markAsReturned(String rentalId) throws SQLException, ClassNotFoundException {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) {
            throw new IllegalArgumentException("Alquiler no encontrado");
        }
        
        String returnedStatusId = rentalStatusRepository.findByName("Devuelto").getRentalStatusId();
        
        rental.setRentalStatusId(returnedStatusId);
        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.update(rental);
        
        BookCopy bookCopy = bookCopyRepository.findById(rental.getBookCopyId());
        if (bookCopy != null) {
            String availableStatusId = getAvailableBookCopyStatusId();
            if (availableStatusId != null) {
                bookCopy.setBookCopyStatusId(availableStatusId);
                bookCopyRepository.update(bookCopy);
            }
        }
    }

    @Override
    public void cancelRental(String rentalId) throws SQLException, ClassNotFoundException {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) {
            throw new IllegalArgumentException("Alquiler no encontrado");
        }
        
        String cancelledStatusId = rentalStatusRepository.findByName("Cancelado").getRentalStatusId();
        
        rental.setRentalStatusId(cancelledStatusId);
        rentalRepository.update(rental);
        
        BookCopy bookCopy = bookCopyRepository.findById(rental.getBookCopyId());
        if (bookCopy != null) {
            String availableStatusId = getAvailableBookCopyStatusId();
            if (availableStatusId != null) {
                bookCopy.setBookCopyStatusId(availableStatusId);
                bookCopyRepository.update(bookCopy);
            }
        }
    }

    @Override
    public int getTotalRentalsCount() throws SQLException, ClassNotFoundException {
        return rentalRepository.countAll();
    }

    @Override
    public int getActiveRentalsCount() throws SQLException, ClassNotFoundException {
        String activeStatusId = rentalStatusRepository.findByName("En Proceso").getRentalStatusId();
        if (activeStatusId == null) {
            return 0;
        }
        return rentalRepository.findByStatus(activeStatusId).size();
    }

    @Override
    public int getOverdueRentalsCount() throws SQLException, ClassNotFoundException {
        return rentalRepository.findOverdueRentals().size();
    }

    @Override
    public int getDueSoonRentalsCount(int days) throws SQLException, ClassNotFoundException {
        return rentalRepository.findDueSoon(days).size();
    }

    @Override
    public int getOnTimeRentalsCount(int dueSoonDays) throws SQLException, ClassNotFoundException {
        String activeStatusId = rentalStatusRepository.findByName("En Proceso").getRentalStatusId();
        if (activeStatusId == null) {
            return 0;
        }
        List<Rental> activeRentals = rentalRepository.findByStatus(activeStatusId);
        List<Rental> overdueRentals = rentalRepository.findOverdueRentals();
        List<Rental> dueSoonRentals = rentalRepository.findDueSoon(dueSoonDays);
        
        return activeRentals.size() - overdueRentals.size() - dueSoonRentals.size();
    }

    @Override
    public PagedResult<Rental> findActiveRentals(int page, int pageSize, String search, String state, Integer dueSoonDays, String fromDate, String toDate)
                                                      throws SQLException, ClassNotFoundException {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        
        int offset = (page - 1) * pageSize;
        
        int totalItems = rentalRepository.countByActiveRentalsFilters(search, state, dueSoonDays, fromDate, toDate);
        
        LinkedList<Rental> rentals = rentalRepository.findActiveRentals(offset, pageSize, search, state, dueSoonDays, fromDate, toDate);
        
        return new PagedResult<>(rentals, page, pageSize, totalItems);
    }

    @Override
    public List<Rental> findOverdueRentals() throws SQLException, ClassNotFoundException {
        return rentalRepository.findOverdueRentals();
    }

    @Override
    public List<Rental> findDueSoonRentals(int days) throws SQLException, ClassNotFoundException {
        return rentalRepository.findDueSoon(days);
    }

    @Override
    public List<RentalStatus> getAllRentalStatuses() throws SQLException, ClassNotFoundException {
        return rentalStatusRepository.findAll();
    }

    @Override
    public BigDecimal getDefaultDailyRate() {
        return DEFAULT_DAILY_RATE;
    }

    @Override
    public PagedResult<BookRentalStats> getMostRequestedBooks(int page, int pageSize, String categoryId) 
                                throws SQLException, ClassNotFoundException {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 20;
        
        int offset = (page - 1) * pageSize;
        
        List<BookRentalStats> items = rentalRepository.getMostRequestedBooks(offset, pageSize, categoryId);
        int totalItems = rentalRepository.countMostRequestedBooks(categoryId);
        
        return new PagedResult<>(items, page, pageSize, totalItems);
    }

    private String getRentedBookCopyStatusId() throws SQLException, ClassNotFoundException {
        return bookCopyStatusRepository.findByName("Alquilado").getBookCopyStatusId();
    }

    private String getAvailableBookCopyStatusId() throws SQLException, ClassNotFoundException {
        return bookCopyStatusRepository.findByName("Disponible").getBookCopyStatusId();
    }
}