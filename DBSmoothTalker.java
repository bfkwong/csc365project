import java.sql.*;
import java.util.*;

public class DBSmoothTalker {

   static final String driver = "com.mysql.cj.jdbc.Driver";
   static final String dburl = "jdbc:mysql://csc365.toshikuboi.net:3306/group08"; 

   static final String user = "group08";
   static final String pass = "Group08@csc365";

   static private String first = "";
   static private String last = "";
   static private int userId = 0;    

   static private String[] supportedCommands = {"search", "book", "cancel"};  

   public static void main(String[] args) {
      try {
         Class.forName(driver);
         Connection connection = DriverManager.getConnection(dburl, user, pass);

         ultimateUIExperience(connection);

         connection.close();
      } catch (Exception sqlException) {
         sqlException.printStackTrace();
      }

   }
   
   private static void ultimateUIExperience(Connection connection) {
      Scanner scan = new Scanner(System.in); 
      String input;
      
      System.out.println("SignIn: <Firstname> <Lastname>"); 
      String[] si = scan.nextLine().split(" ");
      first = si[0]; 
      last = si[1]; 

      signInUser(first, last, connection); 
      //TODO: user signin into database
      
      do {
    	  	System.out.println("Commands: Search, Book, Cancel"); 
    	  	input = scan.nextLine(); 
    	  	executeCommand(connection, input); 
      } while (!input.equals("exit")); 
   }
   
   private static void executeCommand(Connection connection, String command) {
      ArrayList<String> sc = new ArrayList<String>(Arrays.asList(supportedCommands)); 
	  if (!sc.contains(command)) {
    	     return; 
      }
   }

   private static int signInUser(String first, String last, Connection connObj) {
      PreparedStatement ps = connObj.prepareStatement("SELECT * FROM Users WHERE firstName = ? and lastName = ?"); 
      ps.setString(1, first); 
      ps.setString(2, last); 

      ResultSet res = ps.executeQuery(); 

      if (res.next() == null) {
         PreparedStatement ps_ins = connObj.prepareStatement("INSERT INTO Users (firstName, lastName) VALUE (?, ?)");
         ps_ins.setString(1, first); 
         ps_ins.setString(2, first); 
         
         ps_ins.executeQuery(); 
      } else {
         res.beforeFirst(); 
         res.next(); 

         userId = res.getInt("id");
         System.out.println(first + " " + last + " (User ID: " + userId + ") is now signed in.");      
      }
   }

   
}
