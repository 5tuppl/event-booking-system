package service;

import domain.User;
import repository.IRepository;

public class AuthService
{
    private IRepository<User> userRepo;

    public AuthService(IRepository<User> userRepo)
    {
        this.userRepo = userRepo;
    }

    public User login(String username, String password)
    {
        // DIP: We interact with the List provided by the Interface
        for (User u : userRepo.getAll())
        {
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
            {
                return u;
            }
        }
        return null;
    }

    public boolean register(String username, String password, String email)
    {
        boolean exists = userRepo.getAll().stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
        
        if (exists) return false;

        userRepo.add(new User(username, password, "USER", email));
        return true;
    }
}
