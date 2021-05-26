package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singlenton.DBConnection;


public class DaoVeterinario {

	/*
	 * PROPIEDADES Y METODOS SINGLETON
	 */

	private Connection con = null;

	private static DaoVeterinario instance = null;// obj misma clase

	private DaoVeterinario() throws SQLException {
		con = DBConnection.getConnection();// conexion
	}

	public static DaoVeterinario getInstance() throws SQLException {
		if (instance == null)
			instance = new DaoVeterinario();// se crea cuando lo llamo por 1 vez

		return instance;
	}

	public void cerrarSesion() throws SQLException {
		con.close();

	}

	/**
	 * Metodo que se utiliza para insertar un veterinario en la base de datos
	 * 
	 * @param vete
	 * @throws SQLException
	 */
	public void insertarVeterinario(Veterinario vete) throws SQLException {
		// Creo consulta para añadir veterinario
		PreparedStatement ps = con
				.prepareStatement("INSERT INTO vet_veterinario (dni,nombre,apellidos,especialidad) VALUES (?,?,?,?)");

		ps.setString(1, vete.getDni());
		ps.setString(2, vete.getNombre());
		ps.setString(3, vete.getApellidos());
		
		// Recoge la especialidad que especifico en el principal al crear el veterinario
		// ordinal para sacar la posicion
		switch (vete.getEspecialidad()) {
		case CIRUJIA:
			ps.setInt(4, vete.getEspecialidad().CIRUJIA.ordinal());
			break;
		case DERMATOLOGIA:
			ps.setInt(4, vete.getEspecialidad().DERMATOLOGIA.ordinal());
			break;
		case GENERAL:
			ps.setInt(4, vete.getEspecialidad().GENERAL.ordinal());
			break;
		case PELUQUERIA:
			ps.setInt(4, vete.getEspecialidad().PELUQUERIA.ordinal());
			break;
		}

		// Actualizo sentencia
		ps.executeUpdate();

		ps.close();
	}

	/**
	 * Metodo para listar todos los veterinarios que hay en la base de datos
	 * @return result lista con el resultado de todos los veterinarios 
	 * @throws SQLException
	 */

	public List<Veterinario> listarVeterinarios() throws SQLException {

		List<Veterinario> result = new ArrayList<Veterinario>();
		boolean hayDatos = false;
		Veterinario.especialidad especialidad = null;

		// Creo consulta
		try (PreparedStatement ps = con.prepareStatement("SELECT * FROM vet_veterinario");) {
			// Ejecuto para obtener informacion
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;
				// Añado el veterinario a la lista
				result.add(new Veterinario(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						especialidad.values()[rs.getInt("especialidad")]));

			}
			// Cierro resultSet
			rs.close();
		}
		if (!hayDatos) {
			result = null;
		}
		return result;
	}

	/**
	 * Metodo para buscar un veterinario por dni
	 * 
	 * @param dni
	 * @return result veterinario encontrado con ese dni
	 * @throws SQLException
	 */

	public Veterinario buscarPorDni(String dni) throws SQLException {
		Veterinario.especialidad especialidad = null;
		Veterinario result = null;
		// Croe consulta
		PreparedStatement ps = con.prepareStatement("SELECT * FROM vet_veterinario WHERE dni= ?");

		// Recojo el valor que quiero buscar
		ps.setString(1, dni);
		// Ejecuto consulta
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			result = (new Veterinario(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
					especialidad.values()[rs.getInt("especialidad")]));
		}
		rs.close();
		ps.close();

		return result;
	}

}
