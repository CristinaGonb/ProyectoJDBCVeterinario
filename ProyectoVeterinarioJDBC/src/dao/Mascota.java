package dao;

public class Mascota {

	private int chip;
	private String nombreMascota, raza;
	private String sexo;
	private Dueno dueno;

	public Mascota(int chip, String nombre, String raza, String sexo, Dueno dueno) {

		this.chip = chip;
		this.nombreMascota = nombre;
		this.raza = raza;
		this.sexo = sexo;
		this.dueno = dueno;
	}

	public int getChip() {
		return chip;
	}

	public void setChip(int chip) {
		this.chip = chip;
	}

	public String getNombre() {
		return nombreMascota;
	}

	public void setNombre(String nombre) {
		this.nombreMascota = nombre;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Dueno getDueno() {
		return dueno;
	}

	public void setDueno(Dueno dueno) {
		this.dueno = dueno;
	}
	
//	public String mascotaPorCiudad() {
//		return "Mascotas registradas en la ciudad "+dueno.getCiudad()+ " :Mascota "+nombreMascota;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + chip;
		result = prime * result + ((dueno == null) ? 0 : dueno.hashCode());
		result = prime * result + ((nombreMascota == null) ? 0 : nombreMascota.hashCode());
		result = prime * result + ((raza == null) ? 0 : raza.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
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
		Mascota other = (Mascota) obj;
		if (chip != other.chip)
			return false;
		if (dueno == null) {
			if (other.dueno != null)
				return false;
		} else if (!dueno.equals(other.dueno))
			return false;
		if (nombreMascota == null) {
			if (other.nombreMascota != null)
				return false;
		} else if (!nombreMascota.equals(other.nombreMascota))
			return false;
		if (raza == null) {
			if (other.raza != null)
				return false;
		} else if (!raza.equals(other.raza))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mascota [chip=" + chip + ", nombre=" + nombreMascota + ", raza=" + raza + ", sexo=" + sexo + ", dueno=" + dueno.getNombre()+ " " +dueno.getApellidos()
				+ "]";
	}
	

}
