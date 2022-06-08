package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
	public static void main(String[] args) {
		String url = "jdbc:sqlserver://RAKUS089;databaseName=project_nuoc_hoa;integratedSecurity=false;trustServerCertificate=true;";
		String user ="sa";
		String password ="Admin123";
		
		try {
			Connection connection = DriverManager.getConnection(url,user,password);
			System.out.println("Connected");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			System.out.println("Not Connected !!!");
		}
	}
}
