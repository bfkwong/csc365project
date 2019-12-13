import java.sql.*;
import java.util.*;

public class DBSmoothTalker {

   static final String driver = "com.mysql.cj.jdbc.Driver";
   static final String dburl = "jdbc:mysql://csc365.toshikuboi.net:3306/group08"; 

   static final String user = "group08";
   static final String pass = "Group08@csc365";

   static private String first = "";
   static private String last = "";
   
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
   
}
