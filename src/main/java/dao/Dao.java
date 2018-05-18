package dao;

public interface Dao<T> {

	public T create(T t);

	public T getById(Long id);

	public T update(T t);

	void deleteMonumentById(Long id);
}
