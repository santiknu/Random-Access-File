package clasesRAF;

public class Producto {
	
	int id;
	String nombre;
	int precio;
	
	public Producto(int id, String nombre, int precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
	}
	
	public int medirCaracteresProducto() {
		int caracteres = 0;
		caracteres = String.valueOf(this.id).length() + this.nombre.length() + String.valueOf(this.precio).length() + 2;
		return caracteres;
	}
	

}
