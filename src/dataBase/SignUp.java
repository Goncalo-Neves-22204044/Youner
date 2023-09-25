package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUp extends DataBase{


	public int CreateAccount(String email, String name, String birthday, String country, String password){
		int resultado = 0;
		String query = "INSERT INTO User(email, name, birthday, country, password) VALUES ('" + email + "', '" + name +"', '" + birthday + "', '" + country + "', '" + password + "');";
		
		TurnOn();

		try{
			this.getConnection().createStatement().executeUpdate(query);
		}
		catch(SQLException ex){
			resultado = -1;
			System.out.println("SignUp - CreateAccount(String email, String name, String birthday, String country, String password) - " + ex.getMessage());
		}
		TurnOff();
		
		return resultado;
	}
	
	public boolean consultCreate(String email){
		String query = "SELECT * FROM user WHERE email = '"+ email +"';";

		TurnOn();

		try{
			ResultSet rs = this.getConnection().createStatement().executeQuery(query);
			return rs.isBeforeFirst();
		}catch(Exception ex) {
			System.out.println("User - consultCreate(String email) - " + ex.getMessage());
			return false;
		}
	}
}
