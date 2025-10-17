/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_final;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author franciscomurillo
 */
public class GestorFiles {
  public GestorFiles(){
  }
   Scanner read = new Scanner(System.in);
	int contador = 0;
//  --------------------------------------Cargar Archivos-----------------------------
    public void loadFiles(Archivo[] archivos, Path rootFile){
    if (Files.exists(rootFile) && Files.isDirectory(rootFile)) {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootFile)) {
				for (Path path : stream) {
					if (Files.isRegularFile(path) && contador < 20) {

						Archivo file = new Archivo(path.toString());

						if (!file.getName().equals("")) {
							archivos[contador] = file;
							contador++;
						}
					}
				}
           
			} catch (IOException e) {
				System.out.println("Error al recorrer la carpeta: " + e.getMessage());
			}
		} else {
			System.out.println("La carpeta 'archivos' no existe en la raíz del proyecto.");
			return;
		}
}
//    ----------------------------------Agregar Archivos---------------------------------
    	public static int searchFreeIndex(Archivo[] files){
		for (int i = 0; i < files.length; i++) {
			// Código vacio
			if (files[i] == null) {
				return i;
			}
		}
		return -1;
	}
    public static void addFile(Archivo[] files){
//      Un string vacio porque no hay ruta que se le pueda pasar como parametro, porque el archivo no existe, se va a crear, por ende, no hay un lugar donde ir a buscarlo.
        Scanner read = new Scanner(System.in);
        System.out.println("\n------Aregregar Archivo------");
        
        int index = searchFreeIndex(files);
        Archivo[] file = files;
        	if (index  > 0 && index <= files.length)   {
              
			System.out.print("Ingrese el Nombre: ");
			String name = read.nextLine();
			
			System.out.print("Ingrese la extension (.pdf, .doc, .docs): ");
			String extension =  read.nextLine();
                
//              ingrese un numero grande, sera convertido en KB,MB,GB
               System.out.println("Ingrese el tamaño del archivo: ");
                     try {
                          long size = read.nextLong();
                     
                  if((size instanceof Long )){
//               Solucion al error null
               Archivo newArchivo = new Archivo("");
               newArchivo.setName(name);
               newArchivo.setExtension(extension);
               newArchivo.setSize(size);
               newArchivo.generateId();
               files[index]= newArchivo;

               System.out.println("Archivo Creado Exitosamente");
               System.out.println("=".repeat(30));
		} else {
			System.out.println("¡No hay espacio disponible!");
		}
          } catch (Exception e) {
        System.err.println("Valor no valido, El archivo no se Agrego");
      }
       }
    }
    //    ----------------------------------Modificar Archivos---------------------------------
    public static void ModifyFile(Archivo[] files){
      Scanner read = new Scanner(System.in);
     String id;
     String newName;
     String newExtension;
     long  newSize =0;
     
      System.out.println("Inserte el id del elemento a modificar:  ");
      id = read.nextLine();
      
      Archivo archivoEncontrado = null;
      
      for (Archivo file : files) {
            if (file != null && file.getId().equals(id)) {
                archivoEncontrado = file;
                break;
            }
        }
      if (archivoEncontrado == null) {
            System.out.println("No se encontró un archivo con ese ID.");
            return;
        }
      
//      Como nombre se aceptara cualquier valor que el usuario elija
      System.out.println("Inserte el nuevo nombre: ");
      newName = read.next();
      
      //      Como extension se aceptara cualquier valor que el usuario elija, si no existe dentro de las esperada, se muestra el mensaje de extension desconocida
      System.out.println("Inserte la nueva extension: ");
      newExtension = read.next();
//      
      System.out.println("Inserte el nuevo tamaño del archivo ");
//      newSize = read.nextLong();
      try {
         if((newSize instanceof Long )){
           newSize = read.nextLong();
           archivoEncontrado.setName(newName);
      archivoEncontrado.setExtension(newExtension);
      archivoEncontrado.setSize(newSize);
      System.out.println("Archivo modificado con exito");
      }
      } catch (Exception e) {
        System.err.println("Valor no valido, El archivo no se modifico");
         read.close(); 
      }
    }
        //    ----------------------------------Eliminar Archivos---------------------------------
        public static void DeleteFile(Archivo[] files){
           Scanner read = new Scanner(System.in);
            String id;
     
          System.out.println("Inserte el id del elemento a Eliminar:  ");
         id = read.nextLine();
          try {
        boolean eliminado = false;
          for (int i = 0; i < files.length; i++) {
            if (files[i] != null && files[i].getId().equals(id)) {
                String opcion;
                System.out.println("Seguro que quiere eliminar el archivo: ?");
                opcion = read.nextLine().toLowerCase();
                switch (opcion) {
                    case "s": files[i] = null;
                                eliminado = true;
                        break;
                        case "n":System.out.println("El Archivo no se borro");
                                eliminado = false;
                         break;
                    default:
                        throw new AssertionError();
                }
                
                
            }
          }
        if (eliminado) {
            System.out.println("Archivo eliminado correctamente.");
        } else {
          System.err.println("El archivo no se elimino correctamente");
        }
        }catch (Exception e) {
                   System.err.println("Valor no valido, El archivo no se elimino");
         read.close(); 
          }
    }
           //    ----------------------------------Buscar Archivos---------------------------------
        public static void FilterFile(Archivo[] files){
          Scanner read = new Scanner(System.in);
           System.out.println("\n=== BUSCADOR DE ARCHIVOS ===");
            System.out.println("1. Buscar por ID");
           System.out.println("2. Buscar por nombre");
             System.out.print("Seleccione una opción: ");
     int opcion = read.nextInt();
    read.nextLine(); // limpiar buffer
          try {
            if(opcion instanceof Integer){
               switch (opcion) {
//                 Busca por medio del id
              case 1 -> {
                    System.out.print("Ingrese el ID a buscar: ");
                    String id = read.next();
                    read.nextLine();

                     Archivo encontrado = null;
                    for (Archivo a : files) {
                    if (a.getId().equals(id)) {
                    encontrado = a;
                    break;
                }
            }

            if (encontrado != null) {
                System.out.println("Archivo encontrado:");
                encontrado.mostrarInformacion();
                break;
            } else {
                System.out.println(" No se encontró un archivo con ese ID.");
            }
        }
//        busca por medio del nombre
          case 2 -> {
                String input = "";
                boolean terminar =false ;
                while (true && !terminar) {
               System.out.print("\nEscriba parte del nombre (ENTER vacío para salir): ");
                 input = read.nextLine().toLowerCase();

               if (input.isEmpty()) break;

               boolean encontrado = false;
                for (Archivo a : files) {
                if (a.getName().toLowerCase().startsWith(input)) {
                        a.mostrarInformacion();
                        encontrado = true;
                        terminar= true;
                        break;
                      }
                     }

                if (!encontrado) {
                   System.out.println(" No hay coincidencias con \"" + input + "\"");
                  }
                  }
              }
        default -> System.out.println(" Opción inválida.");
               }}else{
              System.err.println("Opcion no validad para buscar");
            }
          } catch (Exception e) {
            System.err.println("El archivo no existe o no fue encontrado");
            read.close();
          }
        }
           //    ----------------------------------Descargar Archivos---------------------------------
  
        public static void DownloadFile(Archivo[] files){
          Scanner read = new Scanner(System.in);
           System.out.print("\nInserte el ID del archivo a descargar: ");
            String id = read.next();
            
          Archivo archivoDescarga = null;
           if((id instanceof String)) {
        
          for (Archivo file : files) {
           if (file != null && file.getId().equals(id)) {
              archivoDescarga = file;
            break;
            }
            }

            if (archivoDescarga == null) {
              System.out.println("❌ No se encontró un archivo con ese ID.");
              return;
              }

    System.out.println("⬇️ Iniciando descarga: " );
      archivoDescarga.setDowloads();
    archivoDescarga.mostrarInformacion();
 
    
    // Simulación de barra de progreso
    for (int i = 0; i <= 100; i += 10) {
        try {
            Thread.sleep(300); // pausa de 300ms para simular tiempo de descarga
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\rDescargando... " + i + "%");
    }

    System.out.println("\n✅ Descarga completada: " + archivoDescarga.getName()+ "." + archivoDescarga.getExtension());
} else{
             System.err.println("Tipo de valor no permitido");
}}
              //    ----------------------------------Descargar Archivos---------------------------------
  
        public static void GenerateReport(Archivo[] files){
          Scanner read = new Scanner(System.in);
          int contador = 0;
          
           System.out.println("\n=== REPORTES DE ARCHIVOS ===");
    System.out.println("1. Ordenado por nombre");
    System.out.println("2. Ordenado por extensión");
    System.out.println("3. Ordenado por fecha de ingreso");
    System.out.println("4. Reporte general con número de descargas");
    System.out.print("Seleccione una opción: ");
    int opcion = read.nextInt();
    
    
//Crea una copia del array principal
       Archivo[] copia = Arrays.stream(files)
                       .filter(Objects::nonNull)
                       .toArray(Archivo[]::new);

    switch (opcion) {
        case 1 -> { 
            if(copia != null){Arrays.sort(copia, 0, contador, Comparator.comparing(Archivo::getName                              ));
        }}
        case 2 -> {if(copia != null){
            Arrays.sort(copia, 0, contador, Comparator.comparing(Archivo::getExtension));}}
        
        case 3 ->{if(copia != null){ Arrays.sort(copia,0, contador, Comparator.comparing(Archivo::              getDateTime));}}
        
       
        default -> {
            System.out.println("❌ Opción inválida.");
            return;
        }
    }        
    // Mostrar cabecera
    System.out.println("---------------------------------------------------------------");

    // Mostrar archivos
    for (Archivo a : files) {
          if(a != null){
        a.mostrarInformacion();
    }}
        }
      //    ----------------------------------Estadistica de Archivos---------------------------------
          public static void GenerateEstadisticas(Archivo[] files){
               System.out.println("\n=== ESTADÍSTICAS DE ARCHIVOS ===");

        Archivo[] topDescargas = files;
             
              Archivo[] copia = Arrays.stream(files)
                       .filter(Objects::nonNull)
                       .toArray(Archivo[]::new);
              if(copia != null){
                    Arrays.sort(copia, Comparator.comparing(Archivo::getDownloads).reversed());
              }
//        Arrays.sort(copia, Comparator.comparing(Archivo::getDownloads).reversed());
        
        System.out.println("\n📌 Top 5 archivos más descargados:");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < Math.min(5, topDescargas.length); i++) {
             if(topDescargas[i] != null){
            topDescargas[i].mostrarInformacion();
        }}

        Map<String, Integer> descargasPorExtension = new HashMap<>();
        for (Archivo a : files) {
            if (a != null) {
            descargasPorExtension.put(a.getExtension(),
                    descargasPorExtension.getOrDefault(a.getExtension(), 0) + a.getDownloads());
        }}

        // Ordenar por descargas
        List<Map.Entry<String, Integer>> extensionesOrdenadas = descargasPorExtension.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        System.out.println("\n? Extensiones más descargadas:");
        System.out.printf("%-10s | %-10s%n", "Extensión", "Descargas");
        System.out.println("--------------------------");
        for (Map.Entry<String, Integer> entry : extensionesOrdenadas) {
            System.out.printf("%-10s | %-10d%n", entry.getKey(), entry.getValue());
        }

        // 3️⃣ Las extensiones más almacenadas en el sistema
        Map<String, Integer> almacenadasPorExtension = new HashMap<>();
        for (Archivo a : files) {
            if (a != null) {
            almacenadasPorExtension.put(a.getExtension(),
                    almacenadasPorExtension.getOrDefault(a.getExtension(), 0) + 1);
            }}

        // Ordenar por cantidad de archivos
        List<Map.Entry<String, Integer>> almacenadasOrdenadas = almacenadasPorExtension.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        System.out.println("\n? Extensiones más almacenadas en el sistema:");
        System.out.printf("%-10s | %-10s%n", "Extensión", "Cantidad");
        System.out.println("--------------------------");
        for (Map.Entry<String, Integer> entry : almacenadasOrdenadas) {
            System.out.printf("%-10s | %-10d%n", entry.getKey(), entry.getValue());
        }
    }
          }

