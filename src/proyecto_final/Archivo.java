/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_final;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author franciscomurillo
 */
public final class Archivo {
    public String id;
    private String name;
	private String extension;
	private Long size;
	private LocalDateTime creationDate;
    private int download;
  
  	public Archivo(String rutaArchivo) {
		try {
			
			Path path = Paths.get(rutaArchivo);
			
//      cuando se agregue un nuevo archivo, se buscara el index vacio, luego se inicializa con estos valores, para poder modificarlo en el metodo agregarArchivo();
                if("".equals(rutaArchivo)) {
                  	this.name = "example";
				this.extension = ".docs";
				this.size = 0L;
                     this.creationDate = LocalDateTime.now();
                     this.id = generateId();
				return;
                }
			if (!Files.exists(path) || !Files.isRegularFile(path) ) {
				System.out.println("El archivo no existe o no es válido: " + rutaArchivo);
				this.name = "";
				this.extension = "";
				this.size = 0L;
				return;
			}
			String nombreCompleto = path.getFileName().toString();
			this.name = getNombreArchivoSinExtension(nombreCompleto);
			this.extension = getExtensionArchivo(nombreCompleto);
			this.size = Files.size(path);
			this.creationDate = LocalDateTime.now();
               this.id = generateId();

      
		} catch (IOException e) {
			System.out.println("Error al acceder al archivo: " + rutaArchivo);
			this.name = "";
			this.extension = "";
			this.size = 0L;
		}
    }
//    setter y getter de los atributos
    public void setDowloads(){
      this.download = download + 1; 
    }
    public int getDownloads(){
      return download;
    }
    public void setId(String id){
        this.id = id;
  };
    public String getId(){
      return this.id;
    }
    public String getName(){
    return name;
  };
    public void setName(String name){
        this.name= name;
    }
	public String getExtension(){
    return extension;
  };
  public void setExtension(String extension){
    this.extension = extension;
  }
	public  Long getSize(){
    return size;
  };
    public void setSize(Long size ){
    this.size = size;
  }
public LocalDateTime getDateTime(){
  return creationDate;
}
  private String getNombreArchivoSinExtension(String fileName) {
		int punto = fileName.lastIndexOf(".");
		if (punto > 0) {
			return fileName.substring(0, punto);
		}
		return "";
	}

	private String getExtensionArchivo(String fileName) {
		int punto = fileName.lastIndexOf(".");

		if (punto > 0 && punto < fileName.length() - 1) {
			return fileName.substring(punto);
		}

		return "";
	}
    public double getArchivoKB() {
		return this.size / 1024.0;
	}
	
	public double getArchivoMB() { 
		return size / (1024.0 * 1024); 
	}
	
     public double getArchivoGB() { 
		return size / (1024.0 * 1024 * 1024); 
	}
	public String getTipoArchivo() {
		switch (this.extension) {
			case ".pdf":
					return "Documento portable";
			case ".doc": case ".docx": 		
					return "Documento de word";
			case ".ppt": case ".pptx":
				return "Documento de power point";
              case ".mp4":case ".m4a":
					return "Documento Musical";
              case ".mp3":
					return "Documento Musical";
              case ".blend":
					return "Archivos nativos de Blender";
              case ".xlsx":
					return "Archivos nativos de Excel";
              case ".png":
					return "Archivos de Imagenes";
			default:
				return "Extension desconocida";
		}

	}
	// Mostrar informació, metodo copiado
	public  void mostrarInformacion() {
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String FormatedDate = creationDate.format(formato);
          System.out.println("ID publico: " + getId());
		System.out.println("Nombre: " + name);
		System.out.println("Extensión: " + extension);
		System.out.println("Tipo de archivo: " + getTipoArchivo());
		System.out.println("Tamaño (Bytes): " + size);
		System.out.printf("Tamaño: %.2f KB | %.2f MB | %.6f GB%n",
						getArchivoKB(), getArchivoMB(), getArchivoGB());
           System.out.println("Downloads: " + download);
                      System.out.println("Creation Date: " + FormatedDate);

		System.out.println("-----------------------------------------");
	}
//metodo copiado
  private static final AtomicInteger counter = new AtomicInteger(0);

   public  String generateId() {
   long timestamp = System.currentTimeMillis(); // milisegundos desde 1970
   int count = counter.getAndIncrement();
  return timestamp + "-" + count;
  
}
}
