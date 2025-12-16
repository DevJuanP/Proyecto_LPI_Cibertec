package repository;

import java.sql.SQLException;
import java.util.List;

import model.Country;

public interface ICountryRepository {
    Country findById(String countryId) throws SQLException, ClassNotFoundException;
    Country findByName(String countryName) throws SQLException, ClassNotFoundException;
    List<Country> findAll() throws SQLException, ClassNotFoundException;
}