package com.kynsof.treatments.domain.service;

/**
 * Interfaz para el servicio de carga de archivos de resultados
 */
public interface IResultFileUploadService {
    
    /**
     * Carga un archivo al servicio de archivos y devuelve la URL del archivo cargado
     *
     * @param base64Content Contenido del archivo en formato base64
     * @param fileName Nombre del archivo
     * @param objectId Identificador del objeto asociado al archivo
     * @param folderPath Ruta de la carpeta donde se guardará el archivo
     * @param uploadedById ID del usuario que carga el archivo
     * @param uploadedByUsername Nombre del usuario que carga el archivo
     * @return URL del archivo cargado
     */
    String uploadFile(String base64Content, String fileName, String objectId, 
                     String folderPath, String uploadedById, String uploadedByUsername);
    
    /**
     * Elimina un archivo por su ID
     *
     * @param fileId ID del archivo a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean deleteFile(String fileId);
}