package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singlenton.DBConnection;

public class DaoCita {
	/*
	 * PROPIEDADES Y METODOS SINGLETON
	 */

	private Connection con = null;

	private static DaoCita instance = null;

	private DaoCita() throws SQLException {
		con = DBConnection.getConnection();
	}

	public static DaoCita getInstance() throws SQLException {
		if (instance == null)
			instance = new DaoCita();

		return instance;
	}

	public void cerrarSesion() throws SQLException {
		con.close();

	}

	/**
	 * Metodo que se utiliza para crear una cita
	 * 
	 * @param cita
	 * @throws SQLException
	 */
	public void insertarCita(Cita cita) throws SQLException {
		try (PreparedStatement ps = con
				.prepareStatement("INSERT INTO vet_cita (fecha,motivo,dni_veterinario,chip) VALUES (?,?,?,?)");) {

			ps.setDate(1, Date.valueOf(cita.getFecha()));
			ps.setString(2, cita.getMotivo());
			ps.setString(3, cita.getVeterinario().getDni());
			ps.setInt(4, cita.getMascota().getChip());

			// Actualizo informacion
			ps.executeUpdate();
		}
	}

	/**
	 * Metodo que se utiliza para mostrar todas las citas que existen en la base de
	 * datos
	 * 
	 * @return result lista de citas
	 * @throws SQLException
	 */
	public List<Cita> listarTodasLasCitas() throws SQLException {
		List<Cita> result = new ArrayList<Cita>();
		boolean hayDatos = false;
		Veterinario.especialidad especialidad = null;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_cita vc INNER JOIN vet_mascota vm ON vc.chip= vm.chip INNER JOIN vet_veterinario vv ON vc.dni_veterinario = vv.dni INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni;")) {
			Dueno d;
			Mascota m;
			Veterinario v;

			// Ejecuto sentencia
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;
				d = new Dueno(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("telefono"), rs.getString("ciudad"));
				m = new Mascota(rs.getInt("chip"), rs.getString("nombre"), rs.getString("raza"), rs.getString("sexo"),
						d);
				v = new Veterinario(rs.getString("dni"), rs.getString("nombre"), rs.getString("apellidos"),
						especialidad.values()[rs.getInt("especialidad")]);
				result.add(new Cita(rs.getInt("id"), rs.getDate("fecha").toLocalDate(), rs.getString("motivo"), m, v));

			}
			rs.close();
		}
		if (!hayDatos) {
			result = null;
		}

		return result;
	}

	/**
	 * Metodo que se utiliza para mostrar las citas de un veterinario en concreto
	 * 
	 * @param dni veterinario
	 * @return resultadoCitas
	 * @throws SQLException
	 */
	public List<Cita> consultarCitasVeterinario(String dni) throws SQLException {
		List<Cita> resultadoCitas = new ArrayList<Cita>();
		boolean hayDatos = false;
		Veterinario.especialidad especialidad = null;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_cita vc INNER JOIN vet_mascota vm ON vc.chip= vm.chip INNER JOIN vet_veterinario vv ON vc.dni_veterinario = vv.dni INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni WHERE dni_veterinario= ?;")) {
			Dueno d;
			Mascota m;
			Veterinario v;

			// Recojo el valor
			ps.setString(1, dni);

			// Ejecuto consulta
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;

				d = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				m = new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), d);
				v = new Veterinario(rs.getString("vv.dni"), rs.getString("vv.nombre"), rs.getString("vv.apellidos"),
						especialidad.values()[rs.getInt("vv.especialidad")]);
				resultadoCitas.add(new Cita(rs.getInt("vc.id"), rs.getDate("vc.fecha").toLocalDate(),
						rs.getString("vc.motivo"), m, v));

			}
			rs.close();
			if (!hayDatos) {
				resultadoCitas = null;
			}
		}

		return resultadoCitas;

	}

	/**
	 * Metodo que se utiliza para buscar una cita por su id
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Cita buscarCitaPorId(int id) throws SQLException {
		Cita resultado = null;
		Veterinario.especialidad especialidad = null;
		Mascota m;
		Veterinario v;
		Dueno d;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_cita vc INNER JOIN vet_mascota vm ON vc.chip= vm.chip INNER JOIN vet_veterinario vv ON vc.dni_veterinario = vv.dni INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni WHERE id=?;")) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				d = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				m = new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), d);
				v = new Veterinario(rs.getString("vv.dni"), rs.getString("vv.nombre"), rs.getString("vv.apellidos"),
						especialidad.values()[rs.getInt("vv.especialidad")]);
				resultado = new Cita(rs.getInt("id"), rs.getDate("fecha").toLocalDate(), rs.getString("motivo"), m, v);
			}
			rs.close();
		}

		return resultado;
	}

	/**
	 * Metodo que se utiliza para actualizar las citas en la base de datos
	 * 
	 * @param cita
	 * @throws SQLException
	 */
	public void actualizarCita(Cita cita) throws SQLException {

		if (cita.getId() == 0) {
			return;
		}
		// Actualizo la tabla citas
		try (PreparedStatement ps = con
				.prepareStatement("UPDATE vet_cita SET fecha = ?, motivo= ?, chip=?, dni_veterinario=? WHERE id=?;")) {

			ps.setDate(1, Date.valueOf(cita.getFecha()));
			ps.setString(2, cita.getMotivo());
			ps.setInt(3, cita.getMascota().getChip());
			ps.setString(4, cita.getVeterinario().getDni());
			ps.setInt(5, cita.getId());

			ps.executeUpdate();
		}
	}

}
