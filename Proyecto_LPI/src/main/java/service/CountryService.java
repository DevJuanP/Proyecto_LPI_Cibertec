package service;

import java.sql.SQLException;
import java.util.List;

import model.Country;
import repository.ICountryRepository;

public class CountryService implements ICountryService {
    private final ICountryRepository countryRepository;

    public CountryService(ICountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll() throws SQLException, ClassNotFoundException {
        return countryRepository.findAll();
    }
}
