package service;

import java.sql.SQLException;
import java.util.List;

import model.Country;

public interface ICountryService {
    List<Country> findAll() throws SQLException, ClassNotFoundException;
}
