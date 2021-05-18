package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singlenton.DBConnection;
public class DaoDueno {

	/*
	 * PROPIEDADES Y M�TODOS SINGLETON
	 */

	private Connection con = null;

	private static DaoDueno instance = null;// obj misma clase

	private DaoDueno() throws SQLException {
		con = DBConnection.getConnection();// conexion
	}

	public static DaoDueno getInstance() throws SQLException {
		if (instance == null)
			instance = new DaoDueno();// se crea cuando lo llamo por 1 vez

		return instance;
	}

	public void cerrarSesion() throws SQLException {
		con.close();

	}

	/**
	 * Metodo que se utiliza para añadir un dueño a la base de datos
	 * 
	 * @param dueno
	 * @throws SQLException
	 */
	public void insertarDueno(Dueno dueno) throws SQLException {
		try (PreparedStatement ps = con
				.prepareStatement("INSERT INTO vet_dueno (dni,nombre,apellidos,telefono,ciudad) VALUES (?,?,?,?,?)");) {

			ps.setString(1, dueno.getDni());
			ps.setString(2, dueno.getNombre());
			ps.setString(3, dueno.getApellidos());
			ps.setString(4, dueno.getTelefono());
			ps.setString(5, dueno.getCiudad());

			// Actualizo informacion
			ps.executeUpdate();
		}
	}
	/**
	 * Metodo que se utilizada para mostrar todos los dueños que estan registrados
	 * en la base de datos
	 * 
	 * @return result
	 * @throws SQLException
	 */
	public List<Dueno> listarTodosLosDuenos() throws SQLException {
		List<Dueno> result = new ArrayList<Dueno>();
		boolean hayDatos = false;

		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM vet_dueno");) {
			// Ejecuto consulta para obtener informacion
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;

				result.add(new Dueno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getString("ciudad")));
			}
			rs.close();
		}
		if (!hayDatos) {
			result = null;
		}
		return result;
	}

	/**
	 * Metodo que se utiliza para buscar a un dueno por su nombre en la base de datos
	 * 
	 * @param nombre
	 * @return result
	 * @throws SQLException
	 */
	public Dueno listarDuenoPorNombre(String nombre) throws SQLException {
		Dueno result = null;

		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM vet_dueno WHERE nombre = ?")) {
			// Recojo valor
			ps.setString(1, nombre);

			// Ejecuto sentencia
			ResultSet rs = ps.executeQuery();

			// Recorro el resultado
			if (rs.next()) {

				result = (new Dueno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getString("ciudad")));
			}
			rs.close();
		}
		return result;
	}

	/**
	 * Metodo que se utiliza para buscar a un dueño de la mascota por su dni
	 * @param dni del dueño
	 * @return result resultado del dueño encontrado por ese dni
	 * @throws SQLException
	 */
	public Dueno listarDuenoPorDni(String dni) throws SQLException {
		Dueno result = null;

		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM vet_dueno WHERE dni = ?")) {
			// Recojo valor
			ps.setString(1, dni);

			// Ejecuto sentencia
			ResultSet rs = ps.executeQuery();

			// Recorro el resultado
			if (rs.next()) {

				result = (new Dueno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getString("ciudad")));
			}
			rs.close();
		}
		return result;
	}
}
