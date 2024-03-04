package programaRAF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import clasesRAF.Producto;

public class ProgramaSupermercado {

	public static Scanner sc = new Scanner(System.in);
	private static final int TR = 40;

	public static void main(String[] args) {

		File f = new File("ProductosRAF.txt");
		try {
			f.createNewFile();
			//System.out.println("Fichero creado correctamente");
		} catch (IOException e) {
			System.out.println("Error al crear el fichero");
		}

		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(f, "rw");
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
		}

		int opcion = 0;
		System.out.println("==================================");
		System.out.println("Bienvenido al menu principal");

		do {
			mostrarMenu();
			System.out.println("-----------------");
			System.out.println("Elige una opcion");
			opcion = sc.nextInt();
			sc.nextLine();
			switch (opcion) {
			case 1:
				String nombre;
				int id, precio;
				System.out.println("¿Cuantos productos quieres añadir?");
				int num = sc.nextInt();
				ArrayList<Producto> productos = new ArrayList<Producto>(num);
				for (int i = 0; i < num; i++) {
					System.out.println("Introduce el ID:");
					id = sc.nextInt();
					System.out.println("Introduce el nombre: ");
					nombre = sc.next();
					System.out.println("Introduce el precio");
					precio = sc.nextInt();
					productos.add(new Producto(id, nombre, precio));
				}
				for (Producto p : productos) {
					escribirProducto(raf, p, p.getId());
				}
				break;
			case 2:
				String nombre2;
				int id2, precio2;
				System.out.println("Introduce el ID:");
				id2 = sc.nextInt();
				System.out.println("Introduce el nombre: ");
				nombre2 = sc.next();
				System.out.println("Introduce el precio");
				precio2 = sc.nextInt();
				Producto p = new Producto(id2, nombre2, precio2);
				escribirProducto(raf, p, p.getId());
				break;
			case 3:
				leerFichero(raf);
				break;
			case 4:
				System.out.println("¿Que producto quieres leer?");
				int np4 = sc.nextInt();
				leerProducto(raf, np4);
				break;
			case 5:
				System.out.println("¿Que producto quieres modificar?");
				int np5 = sc.nextInt();
				leerProducto(raf, np5);
				Producto dp5 = devolverProducto(raf, np5);
				System.out.println("Introduce los nuevos datos");
				String nombre5;
				int precio5;
				System.out.println("Nuevo nombre: ");
				nombre5 = sc.next();
				System.out.println("Nuevo precio: ");
				precio5 = sc.nextInt();
				Producto p5 = new Producto(dp5.getId(), nombre5, precio5);
				escribirProducto(raf, p5, p5.getId());
				break;
			case 6:
				System.out.println("¿Que producto quieres eliminar?");
				int np6 = sc.nextInt();
				borrarProducto(raf, np6);
				break;
			case 7:
				System.out.println("Saliendo");
				break;
			case 69:
				try {
					test(raf);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				System.out.println("Opcion no valida");
				break;
			}
		} while (opcion != 7);

		System.out.println("\n");
		System.out.println("Hasta pronto");

	}

	private static void test(RandomAccessFile raf) throws IOException {
		raf.seek(raf.length());
		escribirProducto(raf, new Producto(1, "Manzana", 3), 1);
		escribirProducto(raf, new Producto(2, "Pera", 4), 2);
		escribirProducto(raf, new Producto(3, "Platano", 5), 3);
		escribirProducto(raf, new Producto(4, "Naranja", 8), 4);
		escribirProducto(raf, new Producto(5, "Aceituna", 3), 5);
		escribirProducto(raf, new Producto(6, "Mandarina", 2), 6);
		escribirProducto(raf, new Producto(7, "Albaricoque", 1), 7);
	}

	private static void mostrarMenu() {
		System.out.println("1-Añadir varios productos");
		System.out.println("2-Añadir un producto");
		System.out.println("3-Mostrar todos los prodcutos");
		System.out.println("4-Mostrar un producto");
		System.out.println("5-Modificar un producto");
		System.out.println("6-Borrar productos");
		System.out.println("7-Salir");
	}

	public static void escribirProducto(RandomAccessFile raf, Producto p, int nr) {
		try {
			raf.seek((nr - 1) * TR);
			raf.writeInt(p.getId());
			raf.writeUTF(p.getNombre());
			raf.writeInt(p.getPrecio());
		} catch (IOException e1) {
			System.out.println("Error al escribir");
		}
	}

	public static void borrarProducto(RandomAccessFile raf, int nr) {
		try {

			ArrayList<Producto> productos = new ArrayList<Producto>();
			int numP = (int) (raf.length() / TR);
			raf.seek(0);
			for (int i = 0; i <= numP; i++) {

				Producto p = devolverProducto(raf, i+1);
				if (p.getId() != nr) {
					productos.add(p);
				}

			}

			int cont = 1;
			for (Producto producto : productos) {
				System.out.println(producto.toString());
				if (producto.getId() > nr) {
					producto.setId(producto.getId() - 1);
					escribirProducto(raf, producto, cont);
				}
				escribirProducto(raf, producto, cont);
				cont++;
			}

			raf.setLength(raf.length() - TR);

		} catch (IOException e) {
			System.out.println("Error al borrar el producto");
		}
	}

	public static void borrarProducto2(RandomAccessFile raf, int nr) {
		try {
			int numP = (int) (raf.length() / TR);
			int nuevaPos = 0;
			raf.seek(nr * TR);
			for (int i = nr; i <= numP; i++) {
				
				Producto p = devolverProducto(raf, i + 1);
				p.setId(p.getId() - 1);
				escribirProducto(raf, p, i);
				
				nuevaPos = i * TR;
				raf.seek(nuevaPos);

			}
			raf.setLength(raf.length() - TR);
		} catch (IOException e) {
			System.out.println("Error al borrar el archivo");
		}
	}

	public static Producto devolverProducto(RandomAccessFile raf, int nr) {
		Producto p = null;
		try {
			raf.seek((nr - 1) * TR);
			int id = raf.readInt();
			String nombre = raf.readUTF();
			int precio = raf.readInt();

			p = new Producto(id, nombre, precio);
			return p;

		} catch (IOException e) {
			System.out.println("Error al devolver el producto");
			return null;
		}
	}

	public static void leerProducto(RandomAccessFile raf, int nr) {
		try {
			raf.seek((nr - 1) * TR);
			int id = raf.readInt();
			String nombre = raf.readUTF();
			int precio = raf.readInt();

			System.out.println("-----------------------------");
			System.out.printf("ID: " + id + "\n");
			System.out.printf("Nombre: " + nombre + "\n");
			System.out.printf("Precio: " + precio + "\n");
			System.out.println("-----------------------------");
		} catch (IOException e) {
			System.out.println("Error al leer el producto");
		}
	}

	public static void leerFichero(RandomAccessFile raf) {
		try {
			int numP = (int) (raf.length() / TR);
			raf.seek(0);
			for (int i = 0; i <= numP; i++) {

				int id = raf.readInt();
				String nombre = raf.readUTF();
				int precio = raf.readInt();

				System.out.println("-----------------------------");
				System.out.printf("ID: " + id + "\n");
				System.out.printf("Nombre: " + nombre + "\n");
				System.out.printf("Precio: " + precio + "\n");
				System.out.println("-----------------------------");

				int nuevaPos = TR + (TR * i);
				raf.seek(nuevaPos);

			}
		} catch (IOException e) {
			System.out.println("Error al leer el archivo");
		}
	}

}
