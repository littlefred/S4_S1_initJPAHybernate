package dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public abstract class DaoImplements<T> implements Dao<T> {
	private EntityManager em;
	private final Class<T> classType;

	public DaoImplements(EntityManager em, Class<T> classType) {
		this.em = em;
		this.classType = classType;
	}

	public T create(T t) {
		// em.getTransaction().begin();
		em.persist(t);
		// em.getTransaction().commit();
		return t;
	}

	public T getById(Long id) {
		T t = em.find(classType, id);
		return t;
	}

	public T update(T t) {
		// em.getTransaction().begin();
		T t2 = em.merge(t);
		// em.getTransaction().commit();
		return t2;
	}

	public void deleteMonumentById(Long id) {
		T t = this.getById(id);
		// em.getTransaction().begin();
		t = em.merge(t);
		em.remove(t);
		// em.getTransaction().commit();
	}
	
	public String display(TypedQuery<T> list) {
		String str = "";
		for (T c : list.getResultList()) {
			System.out.println(c);
		}
		return str;
	}

}
