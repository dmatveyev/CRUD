package util;

import model.User;
import model.UserSession;
import service.SessionService;
import service.UsersService;




public class UUIDGenerator {
    public static final void main(String[] args) {
        UsersService usersService = UsersService.getInstance();
        SessionService sessionService = SessionService.getInstanse();
        User user = usersService.getUserByLogin("den","1");
        sessionService.createSession(user);
        UserSession userSession = sessionService.get(user.getId());
        System.out.println(userSession.getUuid());
        sessionService.delete(userSession);
        //usersService.deleteUser(user);
    }
}
