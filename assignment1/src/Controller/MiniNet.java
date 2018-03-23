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
            else
            {
                return;
            }

        }
    }
}
