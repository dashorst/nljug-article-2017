package nl.topicus.nljug.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.topicus.nljug.domain.Country;

public class CountryDaoImpl implements CountryDao {
	private static List<Country> countries = new ArrayList<Country>();

	static {
		countries.add(new Country("BE", "Belgium"));
		countries.add(new Country("D", "Germany"));
		countries.add(new Country("E", "Spain"));
		countries.add(new Country("F", "France"));
		countries.add(new Country("I", "Italy"));
		countries.add(new Country("L", "Luxemburg"));
		countries.add(new Country("NL", "Netherlands"));
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.training.cheesr.services.StateDao#getStates()
	 */
	public List<Country> getCountries() {
		return Collections.unmodifiableList(countries);
	}
}
