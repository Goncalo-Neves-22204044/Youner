package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends DataBase{


	public boolean consultLogin(String email, String password){
		String query = "SELECT * FROM user WHERE email = '"+ email +"' AND password = '"+ password +"';";

		TurnOn();

		try{
			ResultSet rs = this.getConnection().createStatement().executeQuery(query);
			return rs.next();
		}catch(Exception ex) {
			System.out.println("User - consultLogin(String name, String password) - " + ex.getMessage());
			return false;
		}
	}
	
	public boolean consultEmail(String email){
		String query = "SELECT * FROM user WHERE email = '"+ email +"';";

		TurnOn();

		try{
			ResultSet rs = this.getConnection().createStatement().executeQuery(query);
			return rs.next();
		}catch(Exception ex) {
			System.out.println("User - consultEmail(String email) - " + ex.getMessage());
			return false;
		}
	}
	
	public int update(String email, String password){
		int resultado = 0;
		String query = "UPDATE User SET password = '" + password + "' WHERE email = '" + email + "';";
		
		System.out.println(query);
		
		TurnOn();
		
		try{
			this.getConnection().createStatement().executeUpdate(query);
		}
		catch(SQLException ex){
			resultado = -1;
			
			System.out.println("User - update(String email, String password) - " + ex.getMessage());
		}
		
		TurnOff();
		
		return resultado;
	}
	
	
}
