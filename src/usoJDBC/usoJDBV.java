package usoJDBC;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class usoJDBV {
	
	private static final String URL = "jdbc:mysql://localhost:3306/usojdbc";
	private static final String USUARIO = "root";
	private static final String PASSWORD = "MySQL";
	
	
	static {
	    try {	
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se pudo cargar el driver de JDBC");
	        e.printStackTrace();
	    }
	}


	public static void main(String[] args) {
		
	System.out.println("Hola, esta aplicación te permite trabajar con una base de datos");	
	System.out.println("Te presentamos las opciones que tienes:");
	System.out.println("Opción 1 : Ver la tabla");
	System.out.println("Opción 2 : Agregar un nuevo registro");
	System.out.println("Opción 3 : Modificar algún registro por el ID");
	System.out.println("Opción 4 : Eliminar algún registro por el ID");
	System.out.println("Selecciona a continuación:");
	int opcion;
	Scanner Lector = new Scanner(System.in);
	opcion = Lector.nextInt();
	
	
	switch (opcion) {
		
	case 1:
		verTabla();
		break;
	case 2:
		String nombre = "NULL";
		int edad = 0;
		Lector.nextLine();
		System.out.println("Digita nombre:");
		nombre = Lector.nextLine();
	    
		System.out.println("Digita edad:");
	    try {
	        String entradaEdad = Lector.nextLine();
	        edad = Integer.parseInt(entradaEdad); 
	        agregarRegistro(nombre, edad);
	    } catch (NumberFormatException e) {
	        System.out.println("Edad inválida. Debes escribir un número entero.");
	    }
	    break;
	case 3:
		int ID = 0;
		String NombreNuevo = "NULL";
		Lector.nextLine();
		System.out.println("Digita el ID del usuario a modificar");
		ID = Lector.nextInt();
		System.out.println("Digita el nuevo nombre que quieres");
		Lector.nextLine();
		NombreNuevo = Lector.nextLine();
		modificarRegistro(ID, NombreNuevo);
		break;
	case 4:
		eliminarRegistro();
		break;
	default:
		System.out.println("Opción no válida");
		} 
	
	}
	
	public static void verTabla() {
		
        System.out.println("============================================================");
        System.out.println("VALORES DE LA TABLA:");
		
		String sql = "SELECT * FROM Ejemplo";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql); var rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ID")
                        + ", Usuario: " + rs.getString("Usuario")
                        + ", Edad: " + rs.getString("Edad"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
		
	}
	public static void agregarRegistro(String nombre, int edad) {
		String sql = "INSERT INTO Ejemplo (Usuario, Edad) VALUES (?, ?)";
		
		try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql))
		{
			
			pstmt.setString(1, nombre);
			pstmt.setInt(2, edad);
			pstmt.executeUpdate();
			System.out.println("Registros insertados con éxito.");
		}
		
		catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
	public static void modificarRegistro(int ID, String NombreNuevo) {
		

		    String sql = "UPDATE Ejemplo SET Usuario = ? WHERE ID = ?";

		    try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setString(1, NombreNuevo); 
		        pstmt.setInt(2, ID);            

		        int affectedRows = pstmt.executeUpdate();

		        if (affectedRows > 0) {
		            System.out.println("Nombre actualizado con éxito para el ID " + ID);
		        } else {
		            System.out.println("No se encontró una persona con el ID " + ID);
		        }

		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		

		
	}
	public static void eliminarRegistro() {
		
        String sql = "DELETE FROM Ejemplo";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int affectedRows = pstmt.executeUpdate();

            System.out.println(affectedRows + " registros eliminados con éxito.");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    
		
	}
	
	public static void ignorarYBorrar() {
        Object obj = new Object();
        System.out.println("Todo funciona correctamente.");
	}
}
