/**
 * The UserController is the the service layer class which acts as an interface bwtween the mininet and the database.
 * The class talks with the front end and also with the database to fetch and store data.
 * @author  Abhinay Kathuria Achal Vaish
 * @version 1.0
 * @since   2018-03-23
 */


package Service;

import Bean.*;
import Dao.UserProfileDAO;

import java.util.ArrayList;

public class UserController {

    /**
     * This method is create a new user.
     * In case of dependent it adds the parents detail into the table storing those values.
     * @param newUser The user to be created is passed.
     */

    public void createNewUser(User newUser)
    {
        UserProfileDAO userDao=new UserProfileDAO();
        userDao.createNewUser(newUser);
    }

    /**
     * This method is check if the user name is unique.
     * @param user_id The Name to be checked is passed.
     * @return boolean This returns false if it is not unique otherwise returns true.
     */
    public boolean checkUserId(String user_id) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.checkUserId(user_id);
    }

    /**
     * This method is check if the combination of parent id's is mutually exclusive to create a dependent.
     * @param userParent1 The Name of first parent is passed.
     * @param userParent2 The Name of secondd parent is passed.
     * @return boolean This returns false if they are not mutually exclusive otherwise returns true.
     */

    public boolean checkParentFrDependent(String userParent1, String userParent2) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.checkParentFrDependent(userParent1,userParent2);
    }

    /**
     * This method is to search a particular user in the database.
     * @param name The Name of user is passed.
     * @return User This returns the details of user if found otherwise returns null.
     */

    public User searchUser(String name) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.searchUser(name);
    }

    /**
     * This method is to find friends of a particular user.
     * @param name The Name of user is passed.
     * @return ArrayList<String> This returns the the list of friends of the user.
     */
    public ArrayList<String> findFriends(String name) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.findFriends(name);
    }

    /**
     * This method is to find if a parent has dependents.
     * @param userParent1 The Name of parent is passed.
     * @return boolean This returns false if there are dependents otherwise true.
     */
    public boolean checkInDependents(String userParent1) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.checkInDependents(userParent1);
    }

    /**
     * This method is to delete the user from the network.
     * @param uName The Name of user is passed.
     */
    public void deleteUser(String uName) {
        UserProfileDAO userDao=new UserProfileDAO();
        userDao.deleteUser(uName);
    }

    /**
     * This method is to find if two users are friends.
     * @param user1 The Name of user1 is passed.
     * @param user2 The Name of user2 is passed.
     * @return boolean This returns true if they are friends otherwise false.
     */

    public boolean checkFriends(String user1, String user2) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.checkFriends(user1,user2);
    }

    /**
     * This method is to connect two users as friends.
     * @param user1 The Name of user1 is passed.
     * @param user2 The Name of user2 is passed.
     */
    public void createConnection(String user1, String user2) {
        UserProfileDAO userDao=new UserProfileDAO();
        userDao.createConnection(user1,user2);
    }

    /**
     * This method is to check if a user is a Dependent.
     * @param user1 The Name of user is passed.
     * @return boolean This returns true if it is a Dependent otherwise false.
     */

    public boolean isDependent(String user1) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.isDependent(user1);
    }

    /**
     * This method is to find the name of parents for a dependent.
     * @param user1 The Name of user is passed.
     * @return ArrayList<String> This returns the parents.
     */
    public ArrayList<String> findParents(String user1) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.findParents(user1);
    }

    /**
     * This method is to find the name of children of a parent.
     * @param user1 The Name of user is passed.
     * @return ArrayList<String> This returns the list of children if they exist.
     */
    public ArrayList<String> findChildren(String user1) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.findChildren(user1);
    }

    /**
     * This method is to find the age of a user.
     * @param user1 The Name of user is passed.
     * @return int This returns the age of the user.
     */
    public int userAge(String user1) {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.userAge(user1);

    }

    /**
     * This method is used to create the tables in the database.
     */
    public void createTable() {
        UserProfileDAO userDao=new UserProfileDAO();
        userDao.createTable();
    }

    /**
     * This method is to find the list of all users.
     * @return ArrayList<User> This returns the list of all users in the network.
     */
    public ArrayList<User> listAllUsers() {
        UserProfileDAO userDao=new UserProfileDAO();
        return userDao.listAllUsers();
    }

    /**
     * This method is to find the update the user.
     * @param updateUser The user to be updated is passed.
     */
    public void updateUser(User updateUser) {
        UserProfileDAO userDao=new UserProfileDAO();
        userDao.updateUser(updateUser);
    }
}
