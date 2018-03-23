package Controller;

import Bean.*;
import Service.UserController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MiniNet {

    public static void main(String args[])
    {
        UserController userController=new UserController();
        userController.createTable();
        while(true)
        {

            Scanner sc = new Scanner(System.in);
            Scanner scWhole= new Scanner(System.in);
            System.out.println("MiniNet Menu");
            System.out.println("==============================================");
            System.out.println("1. Add New User");
            System.out.println("2. Search a User");
            System.out.println("3. Delete a User");
            System.out.println("4. Are these two direct friends?");
            System.out.println("5. Connect two users as friends");
            System.out.println("6. Find out Parents of a dependent");
            System.out.println("7. Find out names of children for a user");
            System.out.println("8. List All Users");
            System.out.println("9. Update Details of a user");
            System.out.println("10. Exit");
            int choice=0;
            try {
                 choice = sc.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Incorrect Input");
            }
            if(choice ==1 )
            {
                User newUser = null;
                System.out.println("Enter Name");
                String name=null;
                sc.nextLine();
                 name=scWhole.nextLine();

                System.out.println("Enter the Age");
                int age=sc.nextInt();
                if(age<16)
                {
                    String userParent1=null;
                    do {
                        System.out.println("Enter the Name of first Parent");
                        userParent1=scWhole.nextLine();
                        if(userController.checkUserId(userParent1))
                        {
                            System.out.println("Invalid Parent Name. Please enter a valid Name");
                        }
                        else
                        {
                            break;
                        }
                    }while(true);
                    String userParent2=null;
                    do {
                        System.out.println("Enter the Name of second Parent");
                        userParent2=scWhole.nextLine();
                        if(userController.checkUserId(userParent2))
                        {
                            System.out.println("Invalid Parent Name. Please enter a valid Name");
                        }
                        else
                        {
                            break;
                        }
                    }while(true);
                    if(!userController.checkParentFrDependent(userParent1,userParent2)||(!userController.checkParentFrDependent(userParent2,userParent1)))
                    {
                        System.out.println("Parents are not mutually exclusive. Please check");
                        continue;
                    }
                    newUser=new Dependent(name,age,userParent1,userParent2);
                }
                else
                {
                    newUser=new Adults(name,age);
                }
                System.out.println("Enter the Status:");
                newUser.setStatus(scWhole.nextLine());
                System.out.println("Enter the Display Image Path");
                newUser.setDisplay_picture(scWhole.nextLine());
                if(userController.checkUserId(newUser.getName()))
                {
                    userController.createNewUser(newUser);
                    System.out.println("User Successfully Created ");
                }
                else
                {
                    System.out.println("User Name Already Exists . Please Use a unique User Name");
                }

            }
            else if(choice==2)
            {
                System.out.println("Enter the name to be searched");
                String name= scWhole.nextLine();
                User searchUser;
                searchUser = userController.searchUser(name);
                if(searchUser!=null) {
                }
                else
                {
                    System.out.println("No User found!");
                    continue;
                }
                System.out.println("Display Profile");
                System.out.println("Name:" + searchUser.getName());
                System.out.println("Age:" + searchUser.getAge());
                System.out.println("Status:" + searchUser.getStatus());
                ArrayList<String> friends = userController.findFriends(searchUser.getName());
                System.out.println("Friend List");
                System.out.println(friends);
                }
            else if(choice==3)
            {
                String uName=null;
                do {
                    System.out.println("Enter the user id for the user you want to delete");
                    uName=scWhole.nextLine();
                    if(userController.checkUserId(uName))
                    {
                        System.out.println("Invalid User Id!");

                    }
                    else
                        break;
                }while (true);
                if(!userController.checkInDependents(uName))
                {
                    System.out.println("Dependents exist for the connection. Can't Delete");
                }
                else
                {
                    userController.deleteUser(uName);
                    System.out.println("Deleted Successfully");
                }
            }
           else if(choice==4)
            {

                String user1=null;
                do {
                    System.out.println("Enter the Username of first Person");
                    user1=scWhole.nextLine();
                    if(userController.checkUserId(user1))
                    {
                        System.out.println("Invalid Id. Please enter a valid ID");
                    }
                    else
                    {
                        break;
                    }
                }while(true);
                String user2=null;
                do {
                    System.out.println("Enter the Username of second Parent");
                    user2=scWhole.nextLine();
                    if(userController.checkUserId(user2))
                    {
                        System.out.println("Invalid Id. Please enter a valid ID");

                    }
                    else
                    {
                        break;
                    }
                }while(true);
                if(userController.checkFriends(user1,user2))
                {
                    System.out.println("The users are connected");
                }
                else
                {
                    System.out.println("The users are not connected");

                }


            }
             else if(choice==5)
            {
                String user1=null;
                do {
                    System.out.println("Enter the Username of first Person");
                    user1=scWhole.nextLine();
                    if(userController.checkUserId(user1))
                    {
                        System.out.println("Invalid Id. Please enter a valid ID");
                    }
                    else
                    {
                        break;
                    }
                }while(true);
                String user2=null;
                do {
                    System.out.println("Enter the Username of second Person");
                    user2=scWhole.nextLine();
                    if(userController.checkUserId(user2))
                    {
                        System.out.println("Invalid Id. Please enter a valid ID");

                    }
                    else
                    {
                        break;
                    }
                }while(true);
                if(userController.isDependent(user1)&&userController.isDependent(user2))
                {
                    ArrayList<String> parents = new ArrayList<>();
                    parents= userController.findParents(user1);

                    Collections.sort(parents);
                    ArrayList<String> parents2 = new ArrayList<>();
                    parents2= userController.findParents(user2);
                    Collections.sort(parents2);
                    if(parents.equals(parents2))
                    {
                        System.out.println("Can't Connect! Does Not meet requirements for connecting dependents!");

                    }
                    else {
                        int ageUser1 = userController.userAge(user1);
                        int ageUser2 = userController.userAge(user2);
                        if (ageUser1 <= 2 || ageUser2 <= 2) {
                            System.out.println("Can't Connect! Does Not meet requirements for connecting dependents!");

                        } else if (Math.abs(ageUser1 - ageUser2) < 3) {
                            userController.createConnection(user1, user2);
                            System.out.println("Connected!");
                        }
                    }
                }
                else if(!userController.isDependent(user1)&&!userController.isDependent(user2)) {
                    userController.createConnection(user1, user2);
                    System.out.println("Connected!");
                }
                else
                {
                    System.out.println("Can't Connect! Does Not meet requirements for connecting dependents!");
                }
            }
          else if(choice==6)
            {
                String user1=null;
                do {
                    System.out.println("Enter the Username of the dependent");
                    user1=scWhole.nextLine();
                    if(userController.checkUserId(user1))
                    {
                        System.out.println("Invalid Id. Please enter a valid ID");
                    }
                    else
                    {
                        break;
                    }
                }while(true);
                if(!userController.isDependent(user1))
                {
                    System.out.println("Sorry! The user is not a dependent");
                }
                else
                {
                    ArrayList<String> parents = new ArrayList<>();
                    parents= userController.findParents(user1);
                    System.out.println(parents.toString());
                }

            }
           
            
            else
            {
                return;
            }

        }
    }
}
