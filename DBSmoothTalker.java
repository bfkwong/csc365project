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
   
   static private String[] supportedCommands = {"search", "book", "cancel", "change"};  

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
   
   private static void ultimateUIExperience(Connection connection) throws SQLException {
      Scanner scan = new Scanner(System.in); 
      String input;
      
      System.out.println("SignIn: <Firstname> <Lastname>"); 
      String[] si = scan.nextLine().split(" ");
      first = si[0]; 
      last = si[1]; 

      signInUser(first, last, connection);
      //TODO: user signin into database
      
      do {
    	  	System.out.println("\nValid Commands: Search, Book, Cancel, Change"); 
    	  	input = scan.nextLine(); 
    	  	executeCommand(connection, input, scan); 
      } while (!input.equals("exit")); 
   }
   
   private static void executeCommand(Connection connection, String command, Scanner scan) throws SQLException {
	  command = command.toLowerCase(); 
      ArrayList<String> sc = new ArrayList<String>(Arrays.asList(supportedCommands)); 
	  if (!sc.contains(command)) {
    	     return; 
      }
	  switch (command) {
	  case "search": 
		  searchCommand(connection, scan); 
	  case "book": 
		  bookCommand(connection, scan); 
	  case "cancel": 
		  cancelCommand(connection, scan); 
	  case "change": 
		  changeCommand(connection, scan); 
	  }
   }
   
   private static void searchCommand(Connection connection, Scanner scan) throws SQLException {
	   System.out.println("Search Paramaters? (format: t:<Day Of The Month> o:<Origin> d:<Destination>)");
	   String[] inputs = scan.nextLine().split(" "); 
	   ArrayList<String> yeet = new ArrayList<String>(); 
	   String sql = "select flightNo, airline, departureTime, arrivalTime, origin, dest from Flights where 0 = 0"; 
	   for (String s : inputs) {
		   switch (s.charAt(0)) {
		   case 't': 
			   sql += (" and DAY(departureTime) = ?");   
			   break; 
		   case 'o': 
			   sql += (" and origin = ?"); 
			   break; 
		   case 'd': 
			   sql += (" and dest = ?");
			   break; 
		   default: 
			   break;
		   }	
		   yeet.add(s.substring(2)); 
	   }
	   PreparedStatement prep = connection.prepareStatement(sql); 
	   for (int i = 0; i < yeet.size(); i++) {
		   prep.setString(i+1, yeet.get(i));
	   }
	   
	   ResultSet result = prep.executeQuery(); 
	   
	   while(result.next()) {
		   System.out.println("flightNo: " +result.getInt("flightNo")+ 
				   " airline: " +result.getInt("airline")+ 
				   " departure time: " +result.getTimestamp("departureTime")+
				   " arrival time: " +result.getTimestamp("arrivalTime")+
				   " origin: " +result.getString("origin")+ 
				   " destination: " +result.getString("dest")); 
	   }
   }
   
   private static void bookCommand(Connection connection, Scanner scan) {
	   
   }
   
   private static void cancelCommand(Connection connection, Scanner scan) {
	   
   }
   
   private static void changeCommand(Connection connection, Scanner scan) {
	   
   }
   
   private static void signInUser(String first, String last, Connection connObj) throws SQLException {
	      PreparedStatement ps = connObj.prepareStatement("SELECT * FROM Users WHERE firstName = ? and lastName = ?");
	      ps.setString(1, first);
	      ps.setString(2, last);

	      ResultSet res = ps.executeQuery();

	      if (res.next()) {
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
