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

    public User searchUser(String name) {

        Connection conn= SQLiteJDBCDriverConnection.connect();
        User user=null;
        try {
            String query="select * from User_Profile where trim(lower(name))= ?";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,   name.toLowerCase());
            ResultSet rs=pst.executeQuery();

            while(rs.next())
            {


                if( rs.getInt(3)>16)
                {
                    user=new Adults();
                    user.setName(rs.getString(1));
                    user.setStatus(rs.getString(2));
                    user.setAge(rs.getInt(3));
                    user.setDisplay_picture(rs.getString(4));

                }
                else
                {
                    user=new Dependent();
                    user.setName(rs.getString(1));
                    user.setStatus(rs.getString(2));
                    user.setAge(rs.getInt(3));
                    user.setDisplay_picture(rs.getString(4));
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
        return user;
    }

    public ArrayList<String> findFriends(String name) {

        Connection conn= SQLiteJDBCDriverConnection.connect();
        ArrayList<String> friendList=new ArrayList<>();
        try {
            String query="select u.name from connections c , User_Profile u where c.name=? and c.friend_name=u.name\n" +
                    "Union\n" +
                    "select u.name from connections c , User_Profile u where c.friend_name=? and c.name=u.name ";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, name);
            ResultSet rs=pst.executeQuery();
            while(rs.next())
            {
               friendList.add( rs.getString(1));
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
        return friendList;
    }

    public boolean checkInDependents(String userParent1) {

        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="select count(*) from dependents where lower(parent_name1)=? or lower(parent_name2) = ? ";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,userParent1.toLowerCase());
            pst.setString(2,userParent1.toLowerCase());
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

    public void deleteUser(String uName) {

        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="delete from connections where lower(name)=? or lower(friend_name) = ? ";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,uName.toLowerCase());
            pst.setString(2,uName.toLowerCase());
            pst.executeUpdate();

            query="delete from dependents where lower(name)=? ";
            pst=conn.prepareStatement(query);
            pst.setString(1,uName.toLowerCase());
            pst.executeUpdate();


             query="delete from User_Profile where lower(name)=? ";
             pst=conn.prepareStatement(query);
            pst.setString(1,uName.toLowerCase());
            pst.executeUpdate();

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

    }

    public boolean checkFriends(String user1, String user2) {

        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="select count(*) from connections where (lower(name)=? and lower(friend_name)=?) or (lower(name)=? and lower(friend_name)=?)";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,user1.toLowerCase());
            pst.setString(2,user2.toLowerCase());
            pst.setString(3,user2.toLowerCase());
            pst.setString(4,user1.toLowerCase());
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                if(rs.getInt(1)>0) {

                    return true;
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
        return false;
    }

    public void createConnection(String user1, String user2) {

        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="insert into connections values (?,?) ";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,user1);
            pst.setString(2,user2);
            pst.executeUpdate();

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

    }

    public boolean isDependent(String user1) {

        Connection conn=SQLiteJDBCDriverConnection.connect();

        try {
            String query="select age from User_Profile where lower(name)= ?";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,user1.toLowerCase());
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
                if(rs.getInt(1)>=16)
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

    public ArrayList<String> findParents(String user1) {

        Connection conn= SQLiteJDBCDriverConnection.connect();
        ArrayList<String> parentList =new ArrayList<>();
        try {
            String query="select parent_name1 from dependents d where lower(d.name)=?\n" +
                    "UNION\n" +
                    "select parent_name2 from dependents d where lower(d.name)=?\n";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,user1.toLowerCase());
            pst.setString(2,user1.toLowerCase());
            ResultSet rs=pst.executeQuery();
           while (rs.next())
            {
                parentList.add(rs.getString(1));
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
        return parentList;
    }

   
    public int userAge(String user1) {
        Connection conn= SQLiteJDBCDriverConnection.connect();

        try {
            String query="select age from User_Profile where lower(name)= ?";
            PreparedStatement pst=conn.prepareStatement(query);
            pst.setString(1,user1.toLowerCase());
            ResultSet rs=pst.executeQuery();
            if(rs.next())
            {
               return rs.getInt(1);
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
        return 0;

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

