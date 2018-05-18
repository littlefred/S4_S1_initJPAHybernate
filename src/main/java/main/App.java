package main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import co.simplon.patrimoine.model.City;
import co.simplon.patrimoine.model.Monument;
import co.simplon.patrimoine.model.User;

public class App implements AutoCloseable {
	private static final String PERSISTENCE_UNIT_NAME = "demo-jpa-1"; // defined in persistence.xml
	public EntityManagerFactory factory;

	public App() {
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
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrides);
	}

	public void close() {
		factory.close();
	}

	public City createCity(City city) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(city);
		em.getTransaction().commit();
		em.close();
		return city;
	}

	public Monument createMonument(Monument monument) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(monument);
		em.getTransaction().commit();
		em.close();
		return monument;
	}

	public City createCityAndUpdate(City city) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(city);
		city.setLatitude(1000.);
		em.getTransaction().commit();// MAGIC HAPPENS HERE !
		em.close();
		return city;
	}

	public City readCity(Long id) {
		EntityManager em = factory.createEntityManager();
		City city = em.find(City.class, id);
		// System.out.println(city);
		// em.close();
		return city;
	}

	public Monument readMonument(Long id) {
		EntityManager em = factory.createEntityManager();
		Monument monument = em.find(Monument.class, id);
		// em.close();
		return monument;
	}

	public City update(City city) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		city = em.merge(city);
		em.getTransaction().commit();
		em.close();
		return city;
	}

	public Monument update(Monument monument) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		monument = em.merge(monument);
		em.getTransaction().commit();
		em.close();
		return monument;
	}

	public void deleteCity(Long id) {
		EntityManager em = factory.createEntityManager();
		City city = readCity(id);
		em.getTransaction().begin();
		city = em.merge(city);
		em.remove(city);
		em.getTransaction().commit();
		em.close();
	}

	public void deleteMonument(Long id) {
		Monument monument = readMonument(id);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		monument = em.merge(monument);
		em.remove(monument);
		em.getTransaction().commit();
		em.close();
	}

	public User createUser(User user) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		// em.close();
		return user;
	}

	public static void main(String[] args) {
		try (App app = new App()) {
			// app.createCity(new City("Paris", 100, 550));
			// app.createCity(new City("Londres", 200, 650));
			// app.createCity(new City("Madrid", 300, 750));
			// app.createMonument(new Monument("Tour Eiffel"));
			// app.createMonument(new Monument("Arc de Triomphe"));
			// app.createMonument(new Monument("London Eye"));
			// app.update(new City(1L,"PaRiS",1000., 2000.));
			// app.update(new Monument(2L,"Arc De Triomphe"));
			// System.out.println(app.readCity(7L));
			// System.out.println(app.readMonument(7L));
			// app.createCity(new City("Delete",0,0));
			// app.createMonument(new Monument("Delete"));
			// app.deleteCity(2L);
			// app.deleteMonument(2L);
			// app.createCity(new City("Paris",100,550));
			// app.createMonument(new Monument("Le Louvres", 1L));
			// app.createCity(new City("Londres",150,650));
			// app.createMonument(new Monument("London Eye", 2L));
			// app.createMonument(new Monument("Arc De Triomphe", 1L));
			// app.readCity(1L);
			// app.createMonument(new Monument("Tower Bridge", 2L));
			// app.createMonument(new Monument("La Tour Eiffel",app.readCity(1L)));
			// app.readCity(2L);
			// app.createUser(new User("Toto"));
			/*
			 * Set<Monument> monuments = new HashSet<>();
			 * monuments.add(app.readMonument(2L)); monuments.add(app.readMonument(4L));
			 * User user = new User("Bob"); user.setMonuments(monuments);
			 * app.createUser(user);
			 */
			// app.createUser(new User("Sinclar"));
			/*
			 * User user = new User("Lea"); user.addMonument(app.readMonument(4L));
			 * user.addMonument(app.readMonument(5L)); app.createUser(user);
			 */
			// User user = app.createUser(new User("Louis"));
			// user.addMonument(app.readMonument(5L));

			// System.out.println(app.readCity(2L));
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
