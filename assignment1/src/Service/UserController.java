package Service;

import Bean.*;
import Dao.UserProfileDAO;

import java.util.ArrayList;

public class UserController {

    public void createNewUser(User newUser)
    {

        UserProfileDAO userDao=new UserProfileDAO();
        userDao.createNewUser(newUser);

    }

    public boolean checkUserId(String user_id) {
        UserProfileDAO userDao=new UserProfileDAO();

        return userDao.checkUserId(user_id);
    }

    public boolean checkParentFrDependent(String userParent1, String userParent2) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.checkParentFrDependent(userParent1,userParent2);
    }

    public User searchUser(String name) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.searchUser(name);

    }
    public ArrayList<String> findFriends(String name) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.findFriends(name);

    }
    public boolean checkInDependents(String userParent1) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.checkInDependents(userParent1);
    }

    public void deleteUser(String uName) {
        UserProfileDAO userDao=new UserProfileDAO();
        userDao.deleteUser(uName);
    }

    public boolean checkFriends(String user1, String user2) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.checkFriends(user1,user2);
    }

    public void createTable() {
        UserProfileDAO userDao=new UserProfileDAO();
        userDao.createTable();
    }
}
