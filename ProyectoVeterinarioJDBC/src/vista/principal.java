package vista;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import dao.Cita;
import dao.DaoCita;
import dao.DaoDueno;
import dao.DaoMascota;
import dao.DaoVeterinario;
import dao.Dueno;
import dao.DuenoException;
import dao.Mascota;
import dao.MascotaException;
import dao.Veterinario;
import dao.Veterinario.especialidad;

public class principal {
	/**
	 * Aplicación para uso veterinario donde se gestiona la información de las
	 * mascotas
	 * 
	 * @author Cristina González Baizán
	 */
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {

		int opcion;

		do {
			opcion = menuPrincipal();
			opcionesMenuPrincipal(opcion);
		} while (opcion != 0);
	}

	// --------------------------------------------------------------------------
	/*
	 * Método menú principal de la aplicación
	 */
	public static int menuPrincipal() {
		int opcionMenu;

		do {
			System.out.println("============SISTEMA GESTION VETERINARIO============");
			System.out.println("Bienvenidos al sistema. Introduzca un numero: ");
			System.out.println("0. Salir");
			System.out.println("1. Veterinario");
			System.out.println("2. Dueño de la mascota");
			System.out.println("3. Mascota");
			System.out.println("4. Cita");
			System.out.println("===================================================");

			// Introduzco opción del menú
			opcionMenu = Integer.parseInt(teclado.nextLine());

		} while (opcionMenu < 0 || opcionMenu > 4);

		return opcionMenu;
	}

	/*
	 * Metodo del submenu del menu de Veterinario
	 */
	public static int menuVeterinario() {
		int opcionVeterinario;

		do {
			System.out.println("======Menú Veterinario======");
			System.out.println("1. Listar todos los veterinarios");
			System.out.println("2. Consultar veterinario por dni");
			System.out.println("3. Consultar veterinarios por especialidad");
			System.out.println("4. Insertar un nuevo veterinario");
			System.out.println("5. Volver al menú principal");
			System.out.println("============================");
			System.out.println("Seleccione una opción: ");

			// Introduzco opción del menú
			opcionVeterinario = Integer.parseInt(teclado.nextLine());

		} while (opcionVeterinario < 1 || opcionVeterinario > 5);

		return opcionVeterinario;
	}

	/*
	 * Metodo del submenu del menu de Dueño de la mascota
	 */
	public static int menuDueno() {
		int opcionDueno;

		do {
			System.out.println("====== Menú Dueño mascota=======");
			System.out.println("1. Listar todos los dueños");
			System.out.println("2. Consultar dueño por nombre");
			System.out.println("3. Insertar un nuevo dueño");
			System.out.println("4. Eliminar dueño");
			System.out.println("5. Volver al menú principal");
			System.out.println("================================");
			System.out.println("Seleccione una opción: ");

			// Introduzco opción del menú
			opcionDueno = Integer.parseInt(teclado.nextLine());

		} while (opcionDueno < 1 || opcionDueno > 5);

		return opcionDueno;
	}

	/*
	 * Metodo del submenu del menu de Mascota
	 */
	public static int menuMascota() {
		int opcionMascota;

		do {
			System.out.println("====== Menú Mascota======");
			System.out.println("1. Listar todas las mascotas");
			System.out.println("2. Consultar mascotas por ciudad");// dueño
			System.out.println("3. Consultar mascotas de un dueño");
			System.out.println("4. Insertar nueva mascota");
			System.out.println("5. Eliminar Mascota por chip");
			System.out.println("6. Volver al menú principal");
			System.out.println("=========================");
			System.out.println("Seleccione una opción:");

			// Introduzco opción del menú
			opcionMascota = Integer.parseInt(teclado.nextLine());

		} while (opcionMascota < 1 || opcionMascota > 6);

		return opcionMascota;
	}

	/*
	 * Metodo del submenu del menu de Cita
	 */
	public static int menuCita() {
		int opcionCita;

		do {
			System.out.println("====== Menú Cita======");
			System.out.println("1. Listar todas las citas");
			System.out.println("2. Consultar todas las citas de un veterinario");
			System.out.println("3. Consultar citas de una mascota");
			System.out.println("4. Consultar citas de un dueño");// 3 tablas
			System.out.println("5. Consultar citas de un día");
			System.out.println("6. Insertar nueva cita ");
			System.out.println("7. Modificar fecha de la cita");
			System.out.println("8. Volver al menú principal");
			System.out.println("=============================");
			System.out.println("Seleccione una opción: ");

			// Introduzco opción del menú
			opcionCita = Integer.parseInt(teclado.nextLine());

		} while (opcionCita < 1 || opcionCita > 8);

		return opcionCita;
	}
	// -------------OPCIONES MENU----------------------------

	public static void opcionesMenuPrincipal(int opcionElegida) {
		int opcion;

		switch (opcionElegida) {
		case 0:
			try {
				System.out.println("Cerrando aplicación. ¡Hasta la próxima!");
				DaoVeterinario.getInstance().cerrarSesion();
				DaoDueno.getInstance().cerrarSesion();
				DaoMascota.getInstance().cerrarSesion();
				DaoCita.getInstance().cerrarSesion();
			} catch (SQLException e) {
				System.out.println("Error " + e.getMessage());
			}
			break;
		case 1:
			do {
				opcion = menuVeterinario();
				tratarMenuVeterinario(opcion);
			} while (opcion != 5);
			break;
		case 2:
			do {
				opcion = menuDueno();
				tratarMenuDueno(opcion);
			} while (opcion != 5);
			break;
		case 3:
			do {
				opcion = menuMascota();
				tratarMenuMascota(opcion);
			} while (opcion != 6);
			break;
		case 4:
			do {
				opcion = menuCita();
				tratarMenuCita(opcion);
			} while (opcion != 8);
			break;
		}
	}

	// ------------ TRATAR SUBMENU VETERINARIO-----------------------------------
	public static void tratarMenuVeterinario(int opcionElegida) {
		try {
			switch (opcionElegida) {
			case 1:
				listarTodosLosVeterinarios();
				break;
			case 2:
				listarUnVeterinario();
				break;
			case 3:
				listarVeterinarioPorEspecialidad();
				break;
			case 4:
				anadirVeterinario();
				break;
			}
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}
	}

	// --------------TRATAR SUBMENU DUENO----------------------------------
	public static void tratarMenuDueno(int opcionElegida) {

		try {
			switch (opcionElegida) {
			case 1:
				listarTodosLosDuenos();
				break;
			case 2:
				listarUnDuenoPorNombre();
				break;
			case 3:
				anadirDueno();
				break;
			case 4:
				eliminarDueno();
				break;
			}
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}
	}

	// -------TRATAR SUBMENU MASCOTA------------------
	public static void tratarMenuMascota(int opcionElegida) {
		try {
			switch (opcionElegida) {
			case 1:
				listarTodasLasMascotas();
				break;
			case 2:
				consultarMascotaPorCiudad();
				break;
			case 3:
				listarMascotasDeUnDueno();
				break;
			case 4:
				anadirMascota();
				break;
			case 5:
				eliminarMascota();
				break;
			}
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}
	}

	// -------TRATAR SUBMENU CITA------------------
	public static void tratarMenuCita(int opcionElegida) {
		try {
			switch (opcionElegida) {
			case 1:
				listarTodasLasCitas();
				break;
			case 2:
				consultarCitasPorVeterinario();
				break;
			case 3:
				consultarCitasMascota();
				break;
			case 4:
				consultarCitasDueno();
				break;
			case 5:
				consultarCitaPorFecha();
				break;
			case 6:
				anadirCita();
				break;
			case 7:
				actualizarCita();
				break;
			}
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}
	}

	// ----------METODOS MENU VETERINARIO
	/*
	 * Metodo que muestra los veterinarios que están registrados en la aplicación.
	 * En caso de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void listarTodosLosVeterinarios() throws SQLException {
		DaoVeterinario daoVete = DaoVeterinario.getInstance();
		List<Veterinario> listaVeterinario = null;

		try {
			listaVeterinario = daoVete.listarVeterinarios();
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

		if (listaVeterinario != null) {
			for (Veterinario veterinario : listaVeterinario) {
				System.out.println(veterinario);
			}
		} else {
			System.out.println("No se encuentran veterinarios registrados en la base de datos");
		}
	}

	/*
	 * Metodo que se utiliza para insertar un nuevo veterinario En caso de no tener
	 * ninguno registrado, aparece mensaje de error.
	 */
	public static void anadirVeterinario() throws SQLException {
		DaoVeterinario daoVete = DaoVeterinario.getInstance();
		String nombre, apellidos, dni;
		Veterinario.especialidad especialidadVete;

		System.out.println("*Añadir nuevo Veterinario*");
		System.out.println("Introduzca el dni del veterinario: ");
		dni = teclado.nextLine();

		if (validarDni(dni)) {
			Veterinario dniCorrecto = daoVete.buscarPorDni(dni);
			// Compruebo si el dni insertado existe ya
			if (dniCorrecto != null) {
				System.out.println("Error, ya existe un veterinario con ese dni");
			} else {

				System.out.println("Introduzca el nombre del veterinario: ");
				nombre = teclado.nextLine();
				System.out.println("Introduzca los apellidos del veterinario: ");
				apellidos = teclado.nextLine();

				int numeroEspecialidad;
				do {
					System.out.println("Introduzca la especialidad del veterinario: ");
					System.out.println("\t0-DERMATOLOGIA \n\t1-PELUQUERIA \n\t2-CIRUJIA \n\t3-GENERAL");
					numeroEspecialidad = Integer.parseInt(teclado.nextLine());
					especialidadVete = especialidad.values()[numeroEspecialidad];
				} while (numeroEspecialidad < 0 || numeroEspecialidad > 3);

				try {
					daoVete.insertarVeterinario(new Veterinario(dni, nombre, apellidos, especialidadVete));
					System.out.println("Nuevo veterinario creado correctamente");
				} catch (SQLException e) {
					System.out.println("Error " + e.getMessage());
				}
			}
		} else {
			System.out.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
		}
	}

	/*
	 * Metodo que muestra a un veterinario por su dni. En el caso de estar
	 * registrado con ese dni, muestra mensaje de error.
	 */
	public static void listarUnVeterinario() throws SQLException {
		DaoVeterinario daoVete = DaoVeterinario.getInstance();
		String dni;

		System.out.println("Busqueda de un veterinario");
		System.out.println("Introduzca el dni del veterinario: ");
		dni = teclado.nextLine();

		if (validarDni(dni)) {
			Veterinario vete = null;

			try {
				vete = daoVete.buscarPorDni(dni);
			} catch (SQLException e) {
				System.out.println("Error " + e.getMessage());
			}

			if (vete != null) {
				System.out.printf(vete.toString());
				System.out.println("");
			} else {
				System.out.println("No existe ningun registro con ese dni");
			}
		} else {
			System.out.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
		}
	}

	/*
	 * Metodo que muestra una lista de veterinarios de una especialidad concreta En
	 * caso de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void listarVeterinarioPorEspecialidad() throws SQLException {
		DaoVeterinario daoVeterinario = DaoVeterinario.getInstance();
		Veterinario.especialidad especialidadABuscar;
		int numeroEspecialidad;

		System.out.println("Introduzca la especialidad del veterinario que deseas buscar: ");
		System.out.println("\t0-DERMATOLOGIA \n\t1-PELUQUERIA \n\t2-CIRUJIA \n\t3-GENERAL");
		numeroEspecialidad = Integer.parseInt(teclado.nextLine());

		// Si la especialidad introducida no existe, mensaje de error para volver a
		// insertar las opciones que hay
		if (numeroEspecialidad >= 0 && numeroEspecialidad <= 3) {
			especialidadABuscar = especialidad.values()[numeroEspecialidad];

			List<Veterinario> listaVeterinariosConEspecialidad = daoVeterinario
					.buscarPorEspecialidad(especialidadABuscar);

			if (listaVeterinariosConEspecialidad == null) {
				System.out
						.println("Error, no existe ningun veterinario registrado con esa especialidad en este momento");
			} else {
				for (Veterinario veterinario : listaVeterinariosConEspecialidad) {
					System.out.println(veterinario.toString());
				}
			}
		} else {
			System.out.println("Elige una especialidad entre 0 y 3");
		}
	}

	// ---------------------METODOS MENU DUEÑO---------------
	/*
	 * Metodo que se utiliza para insertar un nuevo dueño En caso de no tener
	 * ninguno registrado, aparece mensaje de error.
	 */
	public static void anadirDueno() throws SQLException {
		DaoDueno daoDueno = DaoDueno.getInstance();
		String nombre, apellidos, dni, telefono, ciudad;
		Dueno dniEncontrado;

		System.out.println("*Añadir nuevo Dueño*");

		System.out.println("Introduzca el dni del dueño de la mascota: ");
		dni = teclado.nextLine();

		if (validarDni(dni)) {
			dniEncontrado = daoDueno.listarDuenoPorDni(dni);

			// Compruebo si el dni del dueño ya existe
			if (dniEncontrado != null) {
				System.out.println("Error, este dni ya existe");
			} else {
				System.out.println("Introduzca el nombre del dueño: ");
				nombre = teclado.nextLine();
				System.out.println("Introduzca los apellidos del dueño: ");
				apellidos = teclado.nextLine();
				System.out.println("Introduzca el telefono: ");
				telefono = teclado.nextLine();
				System.out.println("Introduce la ciudad: ");
				ciudad = teclado.nextLine();

				try {
					daoDueno.insertarDueno(new Dueno(dni, nombre, apellidos, telefono, ciudad));
					System.out.println("Nuevo dueño creado correctamente");
				} catch (SQLException e) {
					System.out.println("Error " + e.getMessage());
				}
			}
		} else {
			System.out.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
		}
	}

	/*
	 * Metodo que se utiliza para mostrar todos los dueños registrados en la
	 * aplicación En caso de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void listarTodosLosDuenos() throws SQLException {
		DaoDueno daoDueno = DaoDueno.getInstance();
		List<Dueno> listaDueno = null;

		try {
			listaDueno = daoDueno.listarTodosLosDuenos();
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

		if (listaDueno != null) {
			for (Dueno dueno : listaDueno) {
				System.out.println(dueno);
			}
		} else {
			System.out.println("No se encuentran dueños registrados en la base de datos");
		}
	}

	/*
	 * Metodo que se utiliza para mostrar un dueño por su dni En caso de no tener
	 * ninguno registrado, aparece mensaje de error.
	 */
	public static void listarUnDuenoPorNombre() throws SQLException {
		DaoDueno daoDueno = DaoDueno.getInstance();
		String nombre;

		System.out.println("Búsqueda de un dueño ");
		System.out.println("Introduce el nombre que deseas buscar: ");
		nombre = teclado.nextLine();

		Dueno dueno = null;

		try {
			dueno = daoDueno.listarDuenoPorNombre(nombre);
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

		if (dueno != null) {
			System.out.printf(dueno.toString());
		} else {
			System.out.println("No existe ningún registro con ese nombre");
		}
	}

	/*
	 * Metodo que se utiliza para eliminar un dueño introduciendo su dni En caso de
	 * no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void eliminarDueno() throws SQLException {
		DaoDueno daoDueno = DaoDueno.getInstance();
		System.out.println("Borrado del dueño");
		System.out.println("Introduce su dni: ");
		String dni = teclado.nextLine();

		if (validarDni(dni)) {
			System.out.println("¿Está usted seguro de que desea eliminar este registro? (S/N)");
			String respuesta = teclado.nextLine();

			if (respuesta.equalsIgnoreCase("S")) {
				try {
					daoDueno.eliminarDueno(dni);
					System.out.println("Registro eliminado correctamente");
				} catch (SQLException | DuenoException e) {
					System.out.println("Error " + e.getMessage());
				}
			}
		} else {
			System.out.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
		}
	}
	// -------------------METODOS MENU MASCOTA----------------

	/*
	 * Metodo que se utiliza para mostrar todas las mascotas registradas en la
	 * aplicación En caso de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void listarTodasLasMascotas() throws SQLException {
		DaoMascota daoMascota = DaoMascota.getInstance();
		List<Mascota> listaMascota = null;

		try {
			listaMascota = daoMascota.listarTodas();
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

		if (listaMascota != null) {
			for (Mascota m : listaMascota) {
				System.out.println(m.toString());
			}
		} else {
			System.out.println("No se encuentran mascotas registradas en la base de datos");
		}
	}

	/*
	 * Metodo que se utiliza para insertar una nueva mascota En caso de no tener
	 * ninguno registrado, aparece mensaje de error.
	 */

	public static void anadirMascota() throws SQLException {
		DaoMascota daoMascota = DaoMascota.getInstance();
		try {
			String nombre, raza, sexo, dniDueno;
			int chip;
			Mascota chipABuscar;

			System.out.println("*Añadir nueva Mascota*");
			System.out.println("Introduzca el chip de la mascota: ");
			chip = Integer.parseInt(teclado.nextLine());

			chipABuscar = daoMascota.buscarPorChip(chip);

			if (chipABuscar != null) {
				System.out.println("Error, ya existe una mascota con ese chip");
			} else {
				System.out.println("Introduzca el nombre de la mascota: ");
				nombre = teclado.nextLine();
				System.out.println("Introduzca la raza: ");
				raza = teclado.nextLine();
				System.out.println("Introduzca el sexo(M/H): ");
				sexo = teclado.nextLine();

				// Datos dueño
				System.out.println("Introduce el dni del dueño de la mascota: ");
				dniDueno = teclado.nextLine();

				try {
					DaoDueno daoDueno = DaoDueno.getInstance();

					Dueno duenoMascota = daoDueno.listarDuenoPorDni(dniDueno);

					if (duenoMascota == null) {
						System.out.println("No existe ningun dueño registrado con ese dni " + dniDueno);
					} else {
						daoMascota.insertarMascota(new Mascota(chip, nombre, raza, sexo, duenoMascota));
					}
					System.out.println("Nuevo registro insertado correctamente");
				} catch (SQLException e) {
					System.out.println("Error " + e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("Los datos no son correctos, vuelve a intentarlo " + e.getMessage());
		}
	}

	/*
	 * Metodo que se utiliza para consultar una mascota por la ciudad en la que esta
	 * registrada su dueño En caso de no tener ninguno registrado, aparece mensaje
	 * de error.
	 */
	public static void consultarMascotaPorCiudad() throws SQLException {
		DaoMascota daoMascota = DaoMascota.getInstance();
		String ciudad;

		System.out.println("Busqueda de una mascota por ciudad");
		System.out.println("Introduzca la ciudad que deseas buscar: ");
		ciudad = teclado.nextLine();

		List<Mascota> m = null;
		List<String> nombreMascotas = new ArrayList<String>();// Para guardar el nombre de las mascotas encontradas en
																// esa ciudad
		try {
			m = daoMascota.consultarMascotasPorCiudadDueno(ciudad);
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

		if (m != null) {
			for (Mascota mascota : m) {
				nombreMascotas.add(mascota.getNombre());
			}
			System.out.println("Mascotas registradas en la ciudad de " + ciudad + ": " + nombreMascotas.toString());
		} else {
			System.out.println("No existe ninguna mascota registrada en esa localidad");
		}
	}

	/*
	 * Metodo que se utiliza para buscar las mascotas de un dueño en concreto En
	 * caso de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void listarMascotasDeUnDueno() throws SQLException {
		DaoMascota daoMascota = DaoMascota.getInstance();
		DaoDueno daoDueno = DaoDueno.getInstance();

		System.out.println("Introduzca el dni del dueño: ");
		String dni = teclado.nextLine();

		if (validarDni(dni)) {
			Dueno buscarDniDueno = daoDueno.listarDuenoPorDni(dni);
			List<Mascota> mascotasDueno = daoMascota.consultarMascotas(dni);

			if (buscarDniDueno == null) {
				System.out.println("No existe ningun registro con ese dni");
			} else {
				if (mascotasDueno != null) {
					for (Mascota buscarMascotasDueno : mascotasDueno) {
						System.out.println(buscarMascotasDueno.toString());
					}
				} else {
					System.out.println("Dueño con dni " + buscarDniDueno.getDni() + " no tiene mascota asociada.");
				}
			}
		} else {
			System.out.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
		}
	}

	/*
	 * Metodo que se utiliza para eliminar una mascota a través del chip
	 */
	public static void eliminarMascota() throws SQLException {
		DaoMascota daoMascota = DaoMascota.getInstance();

		System.out.println("Borrado de la mascota");
		System.out.println("Introduce su chip: ");
		int chip = Integer.parseInt(teclado.nextLine());

		System.out.println("¿Está usted seguro de que desea eliminar este registro? (S/N)");
		String respuesta = teclado.nextLine();

		if (respuesta.equalsIgnoreCase("S")) {
			try {
				daoMascota.eliminarMascota(chip);
				System.out.println("Registro eliminado correctamente");
			} catch (SQLException | MascotaException e) {
				System.out.println("Error " + e.getMessage());
			}
		}
	}

	// -------------------------METODOS MENU CITA--------------
	/*
	 * Metodo que se utiliza para listar todas las citas En caso de no tener ninguno
	 * registrado, aparece mensaje de error.
	 */
	public static void listarTodasLasCitas() throws SQLException {
		DaoCita daoCita = DaoCita.getInstance();
		List<Cita> listaCita = null;

		try {
			listaCita = daoCita.listarTodasLasCitas();
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

		if (listaCita != null) {
			for (Cita citaV : listaCita) {
				System.out.println(citaV);
			}
		} else {
			System.out.println("No se encuentran citas registrados en la base de datos");
		}
	}

	/*
	 * Metodo que se utiliza para insertar una nueva cita En caso de no tener
	 * ninguno registrado, aparece mensaje de error.
	 */
	public static void anadirCita() throws SQLException {
		DaoCita daoCita = DaoCita.getInstance();
		try {
			String motivo, srtFecha, dniVeterinario;
			LocalDate fecha = null;
			int chipMascota;

			try {
				System.out.println("*Añadir nueva Cita*");
				System.out.println("Introduzca la fecha: ");
				srtFecha = teclado.nextLine();
				fecha = LocalDate.parse(srtFecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			} catch (DateTimeParseException e) {
				System.out.println("La fecha introducida no es correcta, debe contener el formato(dd-MM-yyyy)");
			}
			System.out.println("Introduzca el motivo de la cita: ");
			motivo = teclado.nextLine();
			// Dato Mascota
			System.out.println("Introduzca el chip de la mascota: ");
			chipMascota = Integer.parseInt(teclado.nextLine());
			// Dato veterinario
			System.out.println("Introduzca el dni del veterinario que va a tratar a la mascota: ");
			dniVeterinario = teclado.nextLine();

			if (validarDni(dniVeterinario)) {
				DaoVeterinario daoVete = DaoVeterinario.getInstance();
				DaoMascota daoMascota = DaoMascota.getInstance();

				Veterinario vete = daoVete.buscarPorDni(dniVeterinario);
				Mascota mascota = daoMascota.buscarPorChip(chipMascota);

				if (vete == null) {
					System.out.println("No existe ningun veterinario registrado con ese dni " + dniVeterinario);
				} else {
					if (mascota == null) {
						System.out.println("Cita no creada. No existe ninguna mascota con ese chip " + chipMascota);
					} else {
						daoCita.insertarCita(new Cita(fecha, motivo, mascota, vete));
						System.out.println("Nuevo registro insertado correctamente");
					}
				}
			} else {
				System.out
						.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
			}
		} catch (Exception e) {
			System.out.println("Los datos no son correctos, vuelve a intentarlo " + e.getMessage());
		}
	}

	/*
	 * Metodos que se utiliza para mostrar las citas que tiene una mascota En caso
	 * de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void consultarCitasMascota() throws SQLException {
		DaoCita daoCita = DaoCita.getInstance();
		DaoMascota daoMascota = DaoMascota.getInstance();

		System.out.println("Busqueda cita Mascota");
		System.out.println("Introduce el chip de la mascota que deseas buscar: ");
		int chipMascota = Integer.parseInt(teclado.nextLine());

		Mascota buscarChip = daoMascota.buscarPorChip(chipMascota);
		List<Cita> mascotaConCita = daoCita.consultarCitasMascota(chipMascota);

		if (buscarChip == null) {
			System.out.println("No existe ninguna mascota con ese chip " + chipMascota);
		} else {
			if (mascotaConCita == null) {
				System.out
						.println("La mascota con el chip " + chipMascota + " no tiene citas asociadas en este momento");
			} else {
				for (Cita cita : mascotaConCita) {
					System.out.println(cita.mostrarCitasConMascotas());
				}
			}
		}
	}

	/*
	 * Metodo que se utiliza para consultar las citas de un veterinario en concreto
	 * En caso de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void consultarCitasPorVeterinario() throws SQLException {
		DaoCita daoCita = DaoCita.getInstance();

		System.out.println("Búsqueda de la cita de un veterinario");
		System.out.println("Introduce el dni del veterinario que deseas buscar: ");
		String dniV = teclado.nextLine();

		if (validarDni(dniV)) {
			DaoVeterinario daoVete = DaoVeterinario.getInstance();
			Veterinario veterinarioABuscar = daoVete.buscarPorDni(dniV);
			List<Cita> lista = daoCita.consultarCitasVeterinario(dniV);

			if (veterinarioABuscar == null) {
				System.out.println("No existe ningun veterinario con ese dni");
				System.out.println("");
			} else {
				if (lista == null) {
					System.out
							.println("El veterinario con el dni" + dniV + " no tiene citas asociadas en este momento.");
				} else {
					for (Cita citaV : lista) {
						System.out.println(citaV.mostrarCitasConVeterinario());
					}
				}
			}
		} else {
			System.out.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
		}
	}

	/*
	 * Metodo que se utiliza para mostrar las citas del dueño de la mascota
	 */
	public static void consultarCitasDueno() throws SQLException {
		DaoCita daoCita = DaoCita.getInstance();
		DaoDueno daoDueno = DaoDueno.getInstance();

		System.out.println("Introduce el dni el dueño que deseas buscar: ");
		String dniDueno = teclado.nextLine();

		if (validarDni(dniDueno)) {
			Dueno buscarDniDueno = daoDueno.listarDuenoPorDni(dniDueno);
			List<Cita> citasDueno = daoCita.consultarCitasDueno(dniDueno);

			if (buscarDniDueno == null) {
				System.out.println("Error, no existe ningun registro con ese dni " + dniDueno);
			} else {
				if (citasDueno == null) {
					System.out.println("El dueño con el dni " + dniDueno + " no tiene citas asociadas en este momento");
				} else {
					for (Cita cita : citasDueno) {
						System.out.println(cita.mostrarCitasDueno());
					}
				}
			}
		} else {
			System.out.println("Error, dni no válido.Vuelve a introducir el dni con el siguiente formato(12345678A)");
		}
	}

	/*
	 * Metodo que se utiliza para mostrar una cita por una fecha en concreto En caso
	 * de no tener ninguno registrado, aparece mensaje de error.
	 */
	public static void consultarCitaPorFecha() throws SQLException {
		DaoCita daoCita = DaoCita.getInstance();
		LocalDate fecha = null;

		try {
			System.out.println("Introduce la fecha de la cita que deseas consultar: ");
			String fechaAConsultar = teclado.nextLine();
			// Compruebo que inserto bien la fecha
			fecha = LocalDate.parse(fechaAConsultar, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

			List<Cita> listaFecha = daoCita.consultarPorFecha(fecha);
			if (listaFecha == null) {
				System.out.println("Error, no existe un registro con la fecha " + fechaAConsultar+ " actualmente");
			} else {
				for (Cita cita : listaFecha) {
					System.out.println(cita.mostrarFechaCita());
				}
			}
		} catch (DateTimeParseException e) {
			System.out.println("La fecha introducida no es correcta, debe contener el formato(dd-MM-yyyy)");
		}
	}

	/*
	 * Metodo que se utiliza para actualizar una cita En caso de no tener ninguno
	 * registrado, aparece mensaje de error.
	 */
	public static void actualizarCita() throws SQLException {
		DaoCita daoCita = DaoCita.getInstance();
		String motivo;

		// Solicito id de cita que deseo actualizar
		System.out.println("Actualizacion de una cita");
		System.out.println("Introduzca el ID de la cita que deseas modificar: ");
		int id = Integer.parseInt(teclado.nextLine());

		Cita citaABuscar = null;

		// Busco la cita y la almaceno en cita
		citaABuscar = daoCita.buscarCitaPorId(id);

		// Si la cita que buscamos no existe
		if (citaABuscar == null) {
			System.out.println("La cita que desea modificar no esta registada en la base de datos");
		} else {
			try {
				// Si existe, introducimos los datos a modificar
				System.out.println("Introduzca la nueva fecha de la cita: ");
				String fechaAModificar = teclado.nextLine();
				LocalDate fecha = LocalDate.parse(fechaAModificar, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

				// Actualizo los valores
				citaABuscar.setFecha(fecha);

				// Actualizo la cita
				daoCita.actualizarCita(citaABuscar);

				System.out.println("Cita actualizada correctamente.");
			} catch (DateTimeParseException e) {
				System.out.println("La fecha introducida no es correcta, debe contener el formato(dd-MM-yyyy)");
			}
		}
	}

	/*
	 * Metodo para comprobar que el dni tenga una longitud de 9
	 */
	public static boolean validarDni(String dni) {
		return dni.matches("^[0-9]{7,8}[A-Z]$");
	}
}