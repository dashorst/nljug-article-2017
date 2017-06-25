package nl.topicus.nljug.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import nl.topicus.nljug.domain.Cheese;

public class CheeseDaoImpl implements CheeseDao {
	private static List<Cheese> cheeses = new ArrayList<Cheese>();

	static {
		readCheeses();
	}

	public List<Cheese> getCheeses() {
		return Collections.unmodifiableList(cheeses);
	}

	private static void readCheeses() {
		Properties props = new Properties();
		try {
			props.load(CheeseDaoImpl.class
					.getResourceAsStream("cheeses.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object obj : props.keySet()) {
			String key = obj.toString();

			// only process a cheese once (identified by its name)
			if (!key.endsWith(".name"))
				continue;
			key = key.substring(0, key.indexOf("."));

			// retrieve each property value
			String name = props.getProperty(key + ".name");
			String description = props.getProperty(key + ".description");
			double price = Double.valueOf(props.getProperty(key + ".price"));

			cheeses.add(new Cheese(name, description, price));
		}
	}
}
