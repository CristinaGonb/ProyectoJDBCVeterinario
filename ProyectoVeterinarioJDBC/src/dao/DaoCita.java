package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
	 * Metodo que se utiliza para crear una cita en la base de datos
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
	 * @return resultadoCitas
	 * @throws SQLException
	 */
	public List<Cita> listarTodasLasCitas() throws SQLException {
		List<Cita> resultadoCitas = new ArrayList<Cita>();
		boolean hayDatos = false;
		Veterinario.especialidad especialidad = null;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_cita vc INNER JOIN vet_mascota vm ON vc.chip= vm.chip INNER JOIN vet_veterinario vv ON vc.dni_veterinario = vv.dni INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni;")) {
			Dueno dueno;
			Mascota mascota;
			Veterinario veterinario;

			// Ejecuto sentencia
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;
				dueno = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				mascota = new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), dueno);
				veterinario = new Veterinario(rs.getString("vv.dni"), rs.getString("vv.nombre"), rs.getString("vv.apellidos"),
						especialidad.values()[rs.getInt("vv.especialidad")]);
				resultadoCitas.add(new Cita(rs.getInt("vc.id"), rs.getDate("vc.fecha").toLocalDate(), rs.getString("vc.motivo"),
						mascota, veterinario));

			}
			rs.close();
		}
		if (!hayDatos) {
			resultadoCitas = null;
		}

		return resultadoCitas;
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
				"SELECT * FROM vet_cita vc INNER JOIN vet_veterinario vv ON vc.dni_veterinario = vv.dni WHERE dni_veterinario= ?")) {

			Veterinario veterinario;

			// Recojo el valor
			ps.setString(1, dni);

			// Ejecuto consulta
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;

				veterinario = new Veterinario(rs.getString("vv.dni"), rs.getString("vv.nombre"), rs.getString("vv.apellidos"),
						especialidad.values()[rs.getInt("vv.especialidad")]);
				resultadoCitas.add(new Cita(rs.getInt("vc.id"), rs.getDate("vc.fecha").toLocalDate(),
						rs.getString("vc.motivo"), veterinario));
			}

			rs.close();
			if (!hayDatos) {
				resultadoCitas = null;
			}
		}

		return resultadoCitas;

	}
	/**
	 * Metodo que muestra las citas que tiene una mascota en concreto
	 * @param chip
	 * @return resultadoCitasMascota
	 * @throws SQLException
	 */
	public List<Cita> consultarCitasMascota(int chip) throws SQLException {
		List<Cita> resultadoCitasMascota = new ArrayList<Cita>();
		boolean hayDatos = false;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_cita vc INNER JOIN vet_mascota vm ON vc.chip= vm.chip INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni WHERE vm.chip= ?;")) {
			Dueno dueno;
			Mascota mascota;
			Veterinario veterinario;

			// Recojo el valor
			ps.setInt(1, chip);

			// Ejecuto consulta
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;

				dueno = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				mascota = new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), dueno);
				resultadoCitasMascota.add(new Cita(rs.getInt("vc.id"), rs.getDate("vc.fecha").toLocalDate(),
						rs.getString("vc.motivo"), mascota));

			}
			rs.close();
			if (!hayDatos) {
				resultadoCitasMascota = null;
			}
		}

		return resultadoCitasMascota;

	}
	/**
	 * Metodo que muestra las citas que tiene un dueño en concreto
	 * @param dniDueno
	 * @return resultadoCitasDueno
	 * @throws SQLException
	 */
	public List<Cita> consultarCitasDueno(String dniDueno) throws SQLException {
		List<Cita> resultadoCitasDueno = new ArrayList<Cita>();
		boolean hayDatos = false;
		Dueno dueno;
		Mascota mascota;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * from vet_cita vc INNER JOIN vet_mascota vm on vc.chip=vm.chip INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni WHERE vd.dni=?;")) {

			Veterinario veterinario;

			// Recojo el valor
			ps.setString(1, dniDueno);

			// Ejecuto consulta
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;

				dueno = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				mascota = new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), dueno);
				resultadoCitasDueno.add(new Cita(rs.getInt("vc.id"), rs.getDate("vc.fecha").toLocalDate(),
						rs.getString("vc.motivo"), mascota));
			}

			rs.close();
			if (!hayDatos) {
				resultadoCitasDueno = null;
			}
		}

		return resultadoCitasDueno;

	}

	/**
	 * Metodo que se utiliza para buscar una cita por su id
	 * @param id
	 * @return resultadoCitaId
	 * @throws SQLException
	 */
	public Cita buscarCitaPorId(int id) throws SQLException {
		Cita resultadoCitaId = null;
		Veterinario.especialidad especialidad = null;
		Mascota mascota;
		Veterinario veterinario;
		Dueno dueno;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_cita vc INNER JOIN vet_mascota vm ON vc.chip= vm.chip INNER JOIN vet_veterinario vv ON vc.dni_veterinario = vv.dni INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni WHERE id=?;")) {
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				dueno = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				mascota = new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), dueno);
				veterinario = new Veterinario(rs.getString("vv.dni"), rs.getString("vv.nombre"), rs.getString("vv.apellidos"),
						especialidad.values()[rs.getInt("vv.especialidad")]);
				resultadoCitaId = new Cita(rs.getInt("id"), rs.getDate("fecha").toLocalDate(), rs.getString("motivo"), mascota, veterinario);
			}
			rs.close();
		}

		return resultadoCitaId;
	}
	/**
	 * Metodo que muestra las citas por una fecha determinada
	 * @param fecha
	 * @return resultadoCitasFecha
	 * @throws SQLException
	 */
	public List<Cita> consultarPorFecha(LocalDate fecha) throws SQLException {
		List<Cita> resultadoCitasFecha = new ArrayList<Cita>();
		boolean hayDatos = false;
		Veterinario.especialidad especialidad = null;
		Mascota mascota;
		Veterinario veterinario;
		Dueno dueno;

		try (PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM vet_cita vc INNER JOIN vet_mascota vm ON vc.chip= vm.chip INNER JOIN vet_veterinario vv ON vc.dni_veterinario = vv.dni INNER JOIN vet_dueno vd ON vm.dni_dueno = vd.dni WHERE vc.fecha = ?;")) {
			ps.setDate(1, Date.valueOf(fecha));

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				hayDatos = true;

				dueno = new Dueno(rs.getString("vd.dni"), rs.getString("vd.nombre"), rs.getString("vd.apellidos"),
						rs.getString("vd.telefono"), rs.getString("vd.ciudad"));
				mascota = new Mascota(rs.getInt("vm.chip"), rs.getString("vm.nombre"), rs.getString("vm.raza"),
						rs.getString("vm.sexo"), dueno);
				veterinario = new Veterinario(rs.getString("vv.dni"), rs.getString("vv.nombre"), rs.getString("vv.apellidos"),
						especialidad.values()[rs.getInt("vv.especialidad")]);
				resultadoCitasFecha.add(
						new Cita(rs.getInt("id"), rs.getDate("fecha").toLocalDate(), rs.getString("motivo"), mascota, veterinario));

			}
			rs.close();

			if (!hayDatos) {
				resultadoCitasFecha = null;
			}
		}
		return resultadoCitasFecha;
	}

	/**
	 * Metodo que se utiliza para actualizar la fecha de una cita en la base de datos
	 * @param cita
	 * @throws SQLException
	 */
	public void actualizarCita(Cita cita) throws SQLException {

		if (cita.getId() == 0) {
			return;
		}
		// Actualizo la tabla citas
		try (PreparedStatement ps = con.prepareStatement("UPDATE vet_cita SET fecha= ? WHERE id=?;")) {

			ps.setDate(1, Date.valueOf(cita.getFecha()));
			ps.setInt(2, cita.getId());

			ps.executeUpdate();
		}
	}

}
