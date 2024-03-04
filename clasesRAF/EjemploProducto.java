package clasesRAF;

import java.io.IOException;
import java.io.RandomAccessFile;

public class EjemploProducto {
	
	 	private int codigo;
	    private String nombre;
	    private double precio;

	    public EjemploProducto(int codigo, String nombre, double precio) {
	        this.codigo = codigo;
	        this.nombre = nombre;
	        this.precio = precio;
	    }

	    public int getCodigo() {
	        return codigo;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public double getPrecio() {
	        return precio;
	    }

	    // Método para escribir el producto en el archivo de acceso aleatorio
	    public void escribirEnArchivo(RandomAccessFile archivo) throws IOException {
	        archivo.writeInt(codigo);
	        archivo.writeUTF(String.format("%-20s", nombre));
	        archivo.writeDouble(precio);
	    }

	    // Método para leer el producto desde el archivo de acceso aleatorio
	    public static EjemploProducto leerDesdeArchivo(RandomAccessFile archivo) throws IOException {
	        int codigo = archivo.readInt();
	        String nombre = archivo.readUTF().trim();
	        double precio = archivo.readDouble();
	        return new EjemploProducto(codigo, nombre, precio);
	    }
	
}
