import database.User;
import managers.UsersManager;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UsersManager um = UsersManager.getInstance();
        List<User> users = um.getUsers();
        System.out.println(Arrays.toString(users.toArray()));
    }
}