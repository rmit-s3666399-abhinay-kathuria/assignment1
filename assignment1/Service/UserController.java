package Service;

import Bean.*;
import Dao.UserProfileDAO;

import java.util.ArrayList;

public class UserController {

	 public void createTable() {
	        UserProfileDAO userDao=new UserProfileDAO();
	        userDao.createTable();
	    }

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

   
}
