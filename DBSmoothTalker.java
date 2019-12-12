import java.sql.*; 

public class DBSmoothTalker {
	
	static final String driver = "com.mysql.cj.jdbc.Driver"; 
	static final String dburl = "jdbc:mysql://csc365.toshikuboi.net:3306/group08"; 
	
	static final String user = "group08"; 
	static final String pass = "Group08@csc365"; 
	
	public static void main(String[] args) {
		try {
			Class.forName(driver); 
			Connection connection = DriverManager.getConnection(dburl, user, pass); 
			
			// TODO: code shit here lol
			
			connection.close();
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
		}
		
	}
	
}
