package util;

import model.User;
import model.UserSession;
import service.SessionServiceImpl;
import service.UsersServiceImpl;


public class UUIDGenerator {
    public static final void main(String[] args) {
        UsersServiceImpl usersServiceImpl = UsersServiceImpl.getInstance();
        SessionServiceImpl sessionService = SessionServiceImpl.getInstanse();
        User user = usersServiceImpl.getUserByLogin("den","1");
        sessionService.createSession(user);
        UserSession userSession = sessionService.get(user.getId());
        System.out.println(userSession.getUuid());
        sessionService.delete(userSession);
        //usersServiceImpl.deleteUser(user);
    }
}
