package repository;
import java.util.List;

public interface IRepository<T>
{
    void add(T item);
    List<T> getAll();
    T findById(int id);
    boolean delete(int id);
}
