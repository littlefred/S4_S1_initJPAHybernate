package dao;

import javax.persistence.EntityManager;

import co.simplon.patrimoine.model.User;

public class UserDao extends DaoImplements<User>{
	public UserDao(EntityManager em) {
		super(em, User.class);
	}
}
