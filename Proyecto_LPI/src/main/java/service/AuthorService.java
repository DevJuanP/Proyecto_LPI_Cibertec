package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dto.author.AuthorData;
import dto.shared.PagedResult;
import model.Author;
import model.Status;
import repository.IAuthorRepository;
import repository.IStatusRepository;
import util.DateUtil;

public class AuthorService implements IAuthorService {
    private final IAuthorRepository authorRepository;
    private final IStatusRepository statusRepository;

    public AuthorService(IAuthorRepository authorRepository, IStatusRepository statusRepository) {
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public PagedResult<AuthorData> getRegisteredAuthors(int page, int pageSize, String search, 
            String countryId, String statusId) throws SQLException, ClassNotFoundException {
        
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 10;
        if (pageSize > 100) pageSize = 100;
        
        int offset = (page - 1) * pageSize;
        
        int totalItems = authorRepository.count(search, countryId, statusId);
        
        LinkedList<Author> authors = authorRepository.findAllPaginated(offset, pageSize, search, countryId, statusId);
        
        List<AuthorData> authorDataList = new ArrayList<>(authors.size());
        for (Author author : authors) {
            authorDataList.add(mapToAuthorData(author));
        }
        
        return new PagedResult<>(authorDataList, page, pageSize, totalItems);
    }

    @Override
    public int getTotalAuthorsCount() throws SQLException, ClassNotFoundException {
        return authorRepository.count();
    }

    @Override
    public int getAuthorBookCount(String authorId) throws SQLException, ClassNotFoundException {
        return authorRepository.countAuthorBooks(authorId);
    }

    @Override
    public int getActiveAuthorsCount() throws SQLException, ClassNotFoundException {
        Status activeStatus = statusRepository.findByName("Active");

        return authorRepository.count(null, null, activeStatus.getStatusId());
    }

    @Override
    public Author findById(String authorId) throws SQLException, ClassNotFoundException {
        return authorRepository.findById(authorId);
    }

    @Override
    public void save(Author author) throws SQLException, ClassNotFoundException {
        authorRepository.save(author);
    }

    @Override
    public void update(Author author) throws SQLException, ClassNotFoundException {
        authorRepository.update(author);
    }

    @Override
    public void delete(String authorId) throws SQLException, ClassNotFoundException {
        authorRepository.delete(authorId);
    }

    @Override
    public ArrayList<Author> findAll() throws SQLException, ClassNotFoundException {
        return new ArrayList<>(authorRepository.findAll());
    }

    /**
     * Mapea un Author a AuthorData DTO.
     */
    private AuthorData mapToAuthorData(Author author) {
        return new AuthorData(
            author.getAuthorId(),
            author.getFullName(),
            author.getPseudonym(),
            author.getBiography(),
            DateUtil.formatYear(author.getBirthYear()),
            DateUtil.formatYear(author.getDeathYear()),
            author.getWebsite(),
            author.getEmail(),
            author.getPhotoUrl(),
            author.getCreatedAt(),
            author.getUpdatedAt(),
            author.getCountryId(),
            author.getCountry() != null ? author.getCountry().getCountryName() : null,
            author.getStatusId(),
            author.getStatus() != null ? author.getStatus().getStatusName() : null
        );
    }
}
