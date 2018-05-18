package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import co.simplon.patrimoine.model.City;
import co.simplon.patrimoine.model.Monument;
import co.simplon.patrimoine.model.User;
import dao.CityDao;
import dao.MonumentDao;
import dao.UserDao;

public class App2 implements AutoCloseable {
	private static final String PERSISTENCE_UNIT_NAME = "demo-jpa-1"; // defined in persistence.xml
	private EntityManagerFactory factory2;

	public App2() {
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		for (String envName : env.keySet()) {
			if (envName.contains("DB_USER")) {
				configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
			}
			if (envName.contains("DB_PASS")) {
				configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
			}
			if (envName.contains("DB_URL")) {
				configOverrides.put("javax.persistence.jdbc.url", env.get(envName));
			}
		}
		factory2 = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrides);
	}

	public void close() {
		factory2.close();
	}

	public static void main(String[] args) {
		try (App2 ap = new App2()) {
			EntityManager em = ap.factory2.createEntityManager();
			em.getTransaction().begin();

			// DAO
			/*
			 * UserDao daoUser = new UserDao(em); MonumentDao daoMonument = new
			 * MonumentDao(em); CityDao daoCity = new CityDao(em); User albert =
			 * daoUser.create(new User("Albert"));
			 * albert.addMonument(daoMonument.getById(4L));
			 * albert.addMonument(daoMonument.getById(5L)); daoUser.update(albert);
			 * albert.addMonument(daoMonument.create(new Monument("Rialto",
			 * daoCity.getById(3L)))); daoCity.create(new City("New York", 1300, 2500));
			 */
			
			// SCRIPT DB 
			/*UserDao userDao = new UserDao(em);
			MonumentDao monumentDao = new MonumentDao(em);
			CityDao cityDao = new CityDao(em);
			
			City city1 = cityDao.create(new City("Paris",1,2));
			City city2 = cityDao.create(new City("Londres",1,4));
			City city3 = cityDao.create(new City("Barcelone",1,1));
			City city4 = cityDao.create(new City("Venise",3,1));
			City city5 = cityDao.create(new City("New York",8,3));
			cityDao.create(new City("Tokyo",5,2));
			cityDao.create(new City("Pekin",4,3));
			Monument m1 = monumentDao.create(new Monument("Arc De Triomphe", city1));
			Monument m2 = monumentDao.create(new Monument("Le Louvres", city1));
			Monument m3 = monumentDao.create(new Monument("Tower Bridge", city2));
			Monument m4 = monumentDao.create(new Monument("London Eye", city2));
			Monument m5 = monumentDao.create(new Monument("Sagrada Familia", city3));
			Monument m6 = monumentDao.create(new Monument("Rialto", city4));
			Monument m7 = monumentDao.create(new Monument("Empire State Building", city5));
			User user1 = userDao.create(new User("Albert"));
			user1.addMonument(m1);
			user1.addMonument(m2);
			user1.addMonument(m5);
			User user2 = userDao.create(new User("Fred"));
			user2.addMonument(m6);
			User user3 = userDao.create(new User("Fabien"));
			user3.addMonument(m1);
			user3.addMonument(m4);
			User user4 = userDao.create(new User("David"));
			user4.addMonument(m3);
			user4.addMonument(m7);
			User user5 = userDao.create(new User("Léa"));
			user5.addMonument(m5);
			userDao.create(new User("Bob"));
			userDao.create(new User("Louis"));*/
			
			/*MonumentDao monumentDao = new MonumentDao(em);
			CityDao cityDao = new CityDao(em);
			cityDao.create(new City("Test",1,1));
			monumentDao.create(new Monument("Monument sans attache User",cityDao.getById(8L)));*/

			// JPQL
			/*
			 * String jpql = "SELECT c FROM City c ORDER BY c.name"; TypedQuery<City> query
			 * = em.createQuery(jpql, City.class); for (City c : query.getResultList()) {
			 * System.out.println(c); }
			 */

			/*
			 * String jpql2 = "SELECT m FROM Monument m WHERE id=?1"; TypedQuery<Monument>
			 * query2 = em.createQuery(jpql2, Monument.class); query2.setParameter(1, 1L);
			 * // System.out.println(query2); System.out.println(query2.getSingleResult());
			 * // Monument m = query2.getSingleResult(); // System.out.println(m);
			 */
			
			/*
			 * String jpql3 = "SELECT u FROM User u WHERE name=:nameParam"; TypedQuery<User>
			 * query3 = em.createQuery(jpql3, User.class); query3.setParameter("nameParam",
			 * "Albert"); // System.out.println(query3);
			 * System.out.println(query3.getSingleResult()); // User u =
			 * query3.getSingleResult(); // System.out.println(u);
			 */			
			
			/*
			 * String jpql4 = "DELETE FROM User u WHERE u.id=:idParam"; Query q =
			 * em.createQuery(jpql4); q.setParameter("idParam", 3L); q.executeUpdate();
			 */
			
			/*CityDao daoCity = new CityDao(em);
			System.out.println(daoCity.display(em.createNamedQuery("City.findAll", City.class)));*/

			/*UserDao daoUser = new UserDao(em);
			System.out.println(daoUser.display(em.createNamedQuery("User.findAll", User.class)));*/
			/*Query query4 = em.createNamedQuery("User.deleteById");
			query4.setParameter("id", 1L);
			query4.executeUpdate();*/
			
			// Delete User is OK with the monument dependency
			/*
			 * Query query5 = em.createNamedQuery("User.deleteById");
			 * query5.setParameter("id", 3L); query5.executeUpdate();
			 */
			
			// Delete Monument is OK with the user dependency
			/*
			 * Query query6 = em.createNamedQuery("Monument.deleteById");
			 * query6.setParameter("id", 3L); query6.executeUpdate();
			 */
			
			// Delete City OK without monument dependency
			// Delete City NOK with monument dependency, do delete monument associated at the city before delete city
			CityDao daoCity = new CityDao(em);
			Query q = em.createQuery("DELETE FROM Monument m WHERE city = :city");
			q.setParameter("city", daoCity.getById(8L));
			q.executeUpdate();
			Query query7 = em.createNamedQuery("City.deleteById");
			query7.setParameter("id", 8L);
			query7.executeUpdate();
			
			em.getTransaction().commit();

			/*
			 * MonumentDao daoMonument = new MonumentDao(em);
			 * System.out.println(daoMonument.display(em.createNamedQuery(
			 * "Monument.findAll", Monument.class)));
			 */

			em.close();
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}
}
