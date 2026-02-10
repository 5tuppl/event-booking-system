package repository;

import domain.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IRepository<User> {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
        //  admin for system access
        users.add(new User("admin", "admin123", "ADMIN", "admin@event.com"));
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public List<User> getAll()
    {
        return users;
    }

    @Override
    public User findById(int id) 
    {
        return null; 
    }

    @Override
    public boolean delete(int id)
    {
        return false;
    }

    public User findByUsername(String username)
    {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
}
