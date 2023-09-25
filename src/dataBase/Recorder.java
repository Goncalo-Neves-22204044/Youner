package dataBase;

import java.sql.SQLException;

public class Recorder extends DataBase{
	
	public int addRecord(String email, String name, String duration, String size, String date, String dir, String instrument){
		int resultado = 0;
		String query = "INSERT INTO Record(User_email, sampleName, sampleDuration, sampleSize, sampleDate, sampleLoc, instrument) VALUES ('" + email + "', '" + name +"', '" + duration + "', '" + size + "', '" + date + "', '" + dir + "', '" + instrument + "');";
		
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

}
