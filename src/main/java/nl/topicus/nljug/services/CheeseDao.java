package nl.topicus.nljug.services;

import java.util.List;

import nl.topicus.nljug.domain.Cheese;

public interface CheeseDao {
	public List<Cheese> getCheeses();
}
