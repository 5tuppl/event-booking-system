package observer;

import domain.User;

public class EmailService implements NotificationObserver
{
    @Override
    public void sendNotification(User user, String message)
    {
        System.out.println("[Email to " + user.getEmail() + "]: " + message);
    }
}
