package nl.topicus.nljug.services;

import java.util.List;

import nl.topicus.nljug.domain.Country;

public interface CountryDao {

	public List<Country> getCountries();
}