/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_final;

import java.nio.file.*;
import java.util.*;
import java.io.IOException;

/**
 *
 * @author franciscomurillo
 */
public class Proyecto_Final {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner read = new Scanner(System.in);
	Path rootFile = Paths.get("archivos"); 
  	Archivo[] archivos = new Archivo[20]; 
     GestorFiles gestorFiles = new GestorFiles();
     gestorFiles.loadFiles(archivos,rootFile);
int opcion;
do {
    System.out.println("\n=== MENÚ GESTOR DE ARCHIVOS ===");
    System.out.println("1. Mostrar archivos");
    System.out.println("2 Agregar archivos");
    System.out.println("3. Modificar archivo");
    System.out.println("4. Eliminar archivo");
    System.out.println("5. Buscar archivo"); 
    System.out.println("6. Descargar archivo"); 
    System.out.println("7. Generar reporte de archivo"); 
    System.out.println("8. Estadistica de archivo"); 
    System.out.println("9. Salir");

    System.out.print("Seleccione una opción: ");
    opcion = read.nextInt();
    read.nextLine(); // limpiar buffer

    switch (opcion) {
        case 1 ->  {for (Archivo archivo: archivos){
            if(archivo != null)
                  {
                         archivo.mostrarInformacion();
                          }
                       }}
        case 2 -> gestorFiles.addFile(archivos);
        case 3 -> gestorFiles.ModifyFile(archivos);
        case 4 -> gestorFiles.DeleteFile(archivos);
        case 5 -> gestorFiles.FilterFile(archivos);
        case 6-> gestorFiles.DownloadFile(archivos);
        case 7-> gestorFiles.GenerateReport(archivos);
        case 8 -> gestorFiles.GenerateEstadisticas(archivos);
        case 9 -> {System.out.println("👋 Saliendo del programa...");
                    opcion=0;
        }
        default -> System.out.println("❌ Opción inválida");
    }
} while (opcion > 0 && opcion <= 9);

   }
    }
    

