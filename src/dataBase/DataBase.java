package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase{

	private String url;
	private String username;
	private String password;
	private Connection connection;

	public DataBase(){
		url = "jdbc:mysql://localhost:3306/Youner?useSSL=false";
		username = "root";
		password= "";
		connection = null;
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void TurnOn(){
		try{
			connection = DriverManager.getConnection(url, username, password);
		}catch (SQLException ex){
			System.out.println("dataBase - TurnOn() - " + ex.getMessage());
		}
	}
	
	public void TurnOff(){
		try{
			connection.close();
		}catch (SQLException ex){
			System.out.println("dataBase - TurnOff() - " + ex.getMessage());
		}
	}
	

}

