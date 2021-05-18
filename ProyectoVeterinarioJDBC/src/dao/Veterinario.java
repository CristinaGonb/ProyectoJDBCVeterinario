package dao;

public class Veterinario {

	public enum especialidad{
		DERMATOLOGIA,PELUQUERIA,CIRUJIA,GENERAL;
	}
	
	private String dni,nombre,apellidos;
	private Veterinario.especialidad especialidad;

	//Especialidad dermatologia,cirujia,oftalmologia,peluqueria,traumatologia...
	public Veterinario(String dni, String nombre, String apellidos, Veterinario.especialidad especialidad) {

		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.especialidad=especialidad;
	}
	

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Veterinario.especialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(Veterinario.especialidad especialidad) {
		this.especialidad = especialidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellidos == null) ? 0 : apellidos.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + ((especialidad == null) ? 0 : especialidad.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veterinario other = (Veterinario) obj;
		if (apellidos == null) {
			if (other.apellidos != null)
				return false;
		} else if (!apellidos.equals(other.apellidos))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (especialidad != other.especialidad)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Veterinario [dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos + ", especialidad="
				+ especialidad + "]";
	}

	
	
	
}
