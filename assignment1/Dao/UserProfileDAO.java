package Dao;

import Bean.*;
import Util.SQLiteJDBCDriverConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserProfileDAO {

    public void createNewUser(User newUser) {

        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="insert into User_Profile values(?,?,?,?)";

            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1, newUser.getName());
            pst.setString(2, newUser.getStatus());
            pst.setInt(3, newUser.getAge());
            pst.setString(4, newUser.getDisplay_picture());


            int rs=pst.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if(newUser instanceof Dependent)
        {
            try {
                String query = "insert into dependents values(?,?,?)";
                PreparedStatement pst=conn.prepareStatement(query);
                pst.setString(1, newUser.getName());
                pst.setString(2, ((Dependent) newUser).getParent_id1());
                pst.setString(3, ((Dependent) newUser).getParent_id2());
                int rs=pst.executeUpdate();
            }
            catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkUserId(String user_id) {

        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="select count(*) from User_Profile where trim(lower(name))= ?";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,user_id.toLowerCase());
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                if(rs.getInt(1)>0)
                    return false;
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean checkParentFrDependent(String userParent1, String userParent2) {

        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="select count(*) from dependents where trim(lower(parent_name1))=? and trim(lower(parent_name2)) <> ? ";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,userParent1.toLowerCase());
            pst.setString(2,userParent2.toLowerCase());
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                if(rs.getInt(1)>0) {
                    return false;
                }
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public void createTable() {
        Connection conn= SQLiteJDBCDriverConnection.connect();
       if(conn==null)
           return;
        try {
            String query="Create table if not exists User_Profile\n" +
                    "(\n" +
                    "Name varchar2(200),\n" +
                    "status varchar2(200),\n" +
                    "age INT,\n" +
                    "display_image path,\n" +
                    "primary key (Name))";

            PreparedStatement pst=conn.prepareStatement(query);
            pst.execute();

           String query1=" create table if not exists connections\n" +
                    "(\n" +
                    "Name varchar2(200),\n" +
                    "friend_name varchar2(200),\n" +
                    "foreign key (Name) references User_Profile(Name),\n" +
                    "foreign key (friend_name) references User_Profile(Name),\n" +
                    "primary key(Name,friend_name)\n" +
                    ")";

            PreparedStatement pst1=conn.prepareStatement(query1);
            pst1.execute();

           String query2=" create table if not exists dependents\n" +
                    "(\n" +
                    "Name varchar2(200),\n" +
                    "parent_name1 varchar2(200),\n" +
                    "parent_name2 varchar2(200),\n" +
                    "foreign key (Name) references User_Profile(Name),\n" +
                    "foreign key (parent_name1) references User_Profile(Name),\n" +
                    "foreign key (parent_name2) references User_Profile(Name),\n" +
                    "primary key (Name,parent_name1,parent_name2)\n" +
                    ")";

            PreparedStatement pst2=conn.prepareStatement(query2);
            pst2.execute();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

