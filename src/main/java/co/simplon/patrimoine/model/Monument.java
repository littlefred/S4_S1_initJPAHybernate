package co.simplon.patrimoine.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MONUMENTS")
@NamedQueries({
	@NamedQuery(name = "Monument.findAll", query = " SELECT m FROM Monument m ORDER BY m.name "),
	@NamedQuery(name = "Monument.deleteById", query = " DELETE FROM Monument m WHERE m.id = :id") })
public class Monument {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monument")
	@SequenceGenerator(name = "monument", sequenceName = "monument_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;
	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_city", foreignKey = @ForeignKey(name = "fk_city"))
	private City city;
	
	@ManyToMany(mappedBy="monuments")
	private Set<User> users = new HashSet<User>();

	public Monument(String name, City city) {
		super();
		this.name = name;
		this.city = city;
	}

	public Monument() {
	}

	public Monument(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Monument [id=" + id + ", name=" + name + ", city=" + city + "]";
	}
}
