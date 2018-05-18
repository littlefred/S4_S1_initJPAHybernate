package dao;

import javax.persistence.EntityManager;

import co.simplon.patrimoine.model.City;

public class CityDao extends DaoImplements<City> {
	public CityDao(EntityManager em) {
		super(em, City.class);
	}
}
