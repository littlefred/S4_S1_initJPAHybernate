package dao;

import javax.persistence.EntityManager;

import co.simplon.patrimoine.model.Monument;

public class MonumentDao extends DaoImplements<Monument> {
	public MonumentDao(EntityManager em) {
		super(em, Monument.class);
	}
}
