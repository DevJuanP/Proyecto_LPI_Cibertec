package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dto.author.AuthorData;
import dto.shared.PagedResult;
import model.Author;
import repository.IAuthorRepository;
import util.DateUtil;

public class AuthorService implements IAuthorService {
    private final IAuthorRepository authorRepository;

    public AuthorService(IAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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
    public int getAuthorsWithBooksCount() throws SQLException, ClassNotFoundException {
        // TODO: Implementar luego
        return 0;
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

    /**
     * Mapea un Author a AuthorData DTO.
     */
    private AuthorData mapToAuthorData(Author author) {
        return new AuthorData(
            author.getAuthorId(),
            author.getFullName(),
            author.getPseudonym(),
            author.getBiography(),
            DateUtil.formatYear(author.getBirthDate()),
            DateUtil.formatYear(author.getDeathDate()),
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
