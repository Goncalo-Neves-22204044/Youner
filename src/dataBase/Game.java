package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Game extends DataBase{
	public int addScore(String date, int score, String email, int idGame){
		int resultado = 0;
		String query = "INSERT INTO Game_has_User(date, gameScore, User_email, Game_idGame) VALUES ('" + date + "', '" + score + "', '" + email + "', " + idGame + ");";
		
		TurnOn();

		try{
			this.getConnection().createStatement().executeUpdate(query);
		}
		catch(SQLException ex){
			resultado = -1;
			System.out.println("Game - addScore(String date, int score, String email, int idGame) - " + ex.getMessage());
		}
		TurnOff();
		
		return resultado;
	}
	
	public Object[][] getScore() {
        Object[][] resultado = null;
        String query = "select name, gameScore, country, date from user inner join game_has_user on game_has_user.User_email = user.email ORDER BY gameScore DESC;";

        TurnOn();

        try{
            ResultSet rs = this.getConnection().createStatement().executeQuery(query);

            int numColunas = rs.getMetaData().getColumnCount();
            int numLinhas = 0;

            rs.last();
            numLinhas = rs.getRow();
            rs.beforeFirst();

            resultado = new Object[numLinhas][numColunas];

            for(int l = 0; l< numLinhas; l++) {
                rs.next();
                for(int c = 0; c < numColunas; c++) {
                    resultado[l][c] = rs.getObject(c+1);
                }
            }
        }catch(SQLException ex) {
            System.out.println("GetScore" + ex.getMessage());
        }
        return resultado;
    }
	
	public Object[][] getPlayerScore(String email) {
        Object[][] resultado = null;
        String query = "select name, gameScore, country, date from user inner join game_has_user on game_has_user.User_email = user.email where email = '" + email +"'ORDER BY gameScore DESC;";

        TurnOn();

        try{
            ResultSet rs = this.getConnection().createStatement().executeQuery(query);

            int numColunas = rs.getMetaData().getColumnCount();
            int numLinhas = 0;

            rs.last();
            numLinhas = rs.getRow();
            rs.beforeFirst();

            resultado = new Object[numLinhas][numColunas];

            for(int l = 0; l< numLinhas; l++) {
                rs.next();
                for(int c = 0; c < numColunas; c++) {
                    resultado[l][c] = rs.getObject(c+1);
                }
            }
        }catch(SQLException ex) {
            System.out.println("GetGlobalScore" + ex.getMessage());
        }
        return resultado;
    }
}
