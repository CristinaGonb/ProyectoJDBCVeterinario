package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singlenton.DBConnection;

public class DaoMascota {
	/*
	 * PROPIEDADES Y M�TODOS SINGLETON
	 */

	private Connection con = null;

	private static DaoMascota instance = null;// obj misma clase

	private DaoMascota() throws SQLException {
		con = DBConnection.getConnection();// conexion
	}

	public static DaoMascota getInstance() throws SQLException {
		if (instance == null)
			instance = new DaoMascota();// se crea cuando lo llamo por 1 vez

		return instance;
	}

	public void cerrarSesion() throws SQLException {
		con.close();

	}

	/**
	 * Metodo que se utiliza para añadir una nueva mascota a la base de datos
	 * 
	 * @param mascota
	 * @throws SQLException
	 */
	public void insertarMascota(Mascota mascota) throws SQLException {
		try (PreparedStatement ps = con
				.prepareStatement("INSERT INTO vet_mascota (chip,nombre,raza,sexo,dni_dueno) VALUES (?,?,?,?,?)");) {

			ps.setInt(1, mascota.getChip());
			ps.setString(2, mascota.getNombre());
			ps.setString(3, mascota.getRaza());
			ps.setString(4, mascota.getSexo());
			ps.setString(5, mascota.getDueno().getDni());

			// Actualizo informacion
			ps.executeUpdate();

		}
	}

	public List<Mascota> listarTodas() throws SQLException {
		List<Mascota> result = new ArrayList<Mascota>();
		boolean hayDatos = false;

		try (PreparedStatement ps = con
				.prepareStatement("SELECT * FROM vet_mascota vm INNER JOIN vet_dueno vd ON vm.dni_dueno=vd.dni;")) {
			Dueno d;

			// Ejecuto sentencia
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;

				d = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				result.add(new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), d));
			}
			rs.close();
		}
		if (!hayDatos) {
			result = null;
		}

		return result;
	}

	public List<Mascota> consultarMascotasPorCiudadDueno(String ciudad) throws SQLException {

		List<Mascota> result = new ArrayList<Mascota>();
		boolean hayDatos = false;
		Dueno d;

		try (PreparedStatement ps = con
				.prepareStatement("SELECT * FROM vet_mascota LEFT JOIN vet_dueno on dni_dueno= dni WHERE ciudad=?;")) {
			// Recojo valor
			ps.setString(1, ciudad);

			// Ejecuto consulta
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;
				d = new Dueno(rs.getString("ciudad"));
				result.add(new Mascota(rs.getInt("chip"), rs.getString("nombre"), rs.getString("raza"),
						rs.getString("sexo"), d));
			}
			rs.close();

			if (!hayDatos) {
				result = null;
			}
		}

		return result;
	}

	public Mascota buscarPorChip(int chip) throws SQLException {
		Mascota resultadoMascota = null;
		Dueno dueno;
		// Creo consulta
		PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_mascota vm JOIN vet_dueno vd ON vm.dni_dueno=vd.dni WHERE chip= ?;");

		// Recojo el valor que quiero buscar
		ps.setInt(1, chip);
		// Ejecuto consulta
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			dueno = new Dueno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
					rs.getString("telefono"), rs.getString("ciudad"));
			resultadoMascota = new Mascota(rs.getInt("chip"), rs.getString("nombre"), rs.getString("raza"),
					rs.getString("sexo"), dueno);
		}
		rs.close();

		return resultadoMascota;
	}

	public void eliminarMascota(int chip) throws SQLException, MascotaException {
		Mascota mascotaAEncontrar = buscarPorChip(chip);

		if (mascotaAEncontrar == null) {
			throw new MascotaException("No existe una mascota con ese chip " + chip);
		}

		try (PreparedStatement ps = con.prepareStatement("DELETE FROM vet_mascota WHERE chip= ?")) {
			ps.setInt(1, chip);
			ps.executeUpdate();
		}
	}
}