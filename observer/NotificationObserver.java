package observer;

import domain.User;

public interface NotificationObserver
{
    void sendNotification(User user, String message);
}
