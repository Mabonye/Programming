/*
 * This java program performs the following functions:
 * 1. Compresses log files from the local folder to tresorit folder 
 * 2. Deletes the source files and folders after compression at the 
 *    local directory (given that the buslogger is not running).
 * 3. Checking if the buslogger is running, if not then compress the 
 *    last file in the dource directory then delete the folder.
 *    If the buslogger is still running, then do nothing.
 */

package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.Properties;

public class Compression {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){ 
    
        long start = System.currentTimeMillis();
        long end = start + 600000;//run the program for 10 minutes

        //while(true) {
        while(System.currentTimeMillis() < end){
            try{
                
                //Loading java properties file
                Properties prop = new Properties();
                FileReader reader = new FileReader("C:\\PropertiesFiles\\Customer.properties");
                prop.load(reader);
               
                byte[] buffer = new byte[1024]; 
       
                //Setting the destination directory 
                String destDirectory = prop.getProperty("destinationpath");
   
                //Getting the source directory
                String dirPath = prop.getProperty("sourcepath");
                File dir = new File(dirPath);
       
                //Get a list of subdirectories in that directory
                File listDir[] = dir.listFiles();
       
       
                /* Checking the first modified subdirectory 
                 * If the list of subdirectories is more than one
                 */
                if (listDir.length > 1){
                    File lastModifiedFolder = listDir[0];
                    for (int j = 0; j < listDir.length; j++){
                        if (lastModifiedFolder.lastModified() > listDir[j].lastModified()){
                            lastModifiedFolder = listDir[j];
                        }
                    }

                    //Creating a new subdirectory path and assign it to SubdirPath
                    String SubdirPath = dirPath + lastModifiedFolder.getName() + "\\";
                    File Subdir = new File(SubdirPath);
                    String[] NumberofFiles = lastModifiedFolder.list();
              
                    /* For loop for iterating the number of files to be compressed 
                     * In the first modified folder
                     */
                    for (int k = 0; k < NumberofFiles.length; k++){
                
                        //Getting array of files in the subdirectory
                        String[] empty = lastModifiedFolder.list();
                
                        /* Checking if the number of files in the last modified folder 
                         * is greater than or equal to 1 
                         */
                        if (empty.length > 0){
                            //Get all the first modified file from the subdirectory
                            File[] files = Subdir.listFiles();
                            File lastModifiedFile = files[0];
                    
                            //Getting the name of the last modified file without the path
                            String filename = lastModifiedFile.getName();
                    
                            //Removing the extension from filename
                            String name = filename.substring(0, filename.lastIndexOf('.'));
                    
                            //The Zip extension
                            String FILE_EXTENSION = ".Zip";
                    
                            /* Creating a folder at tresorit that has the same name as the source 
                             * folder at local folder and move its compressed files in it 
                             */
                            String newdestDirectory1 = destDirectory + lastModifiedFolder.getName() + "\\";
                            File newDirectory1 = new File(newdestDirectory1); 
                            newDirectory1.mkdirs();
                    
                            //Create a filepath for the zip file
                            String filepath = newDirectory1 + "\\" + name + FILE_EXTENSION;
                    
                            //Getting the last modified file from the lastModiiedFolder
                            for (int i = 0; i < files.length; i++){
                                if (lastModifiedFile.lastModified() > files[i].lastModified()){
                                    lastModifiedFile = files[i];
                                } 
                            }
                  
                            FileOutputStream fos = new FileOutputStream(filepath);
                            ZipOutputStream zos = new ZipOutputStream(fos);
                  
                            //Adding a file entry to zip (lastModifiedFile.getName() = the name of the file)
                            ZipEntry ze = new ZipEntry(filename);
                            zos.putNextEntry(ze);
                  
                            //Read the file from the given path
                            FileInputStream in = new FileInputStream(lastModifiedFile);
                  
                            int len;
                            while ((len = in.read(buffer)) > 0){
                                zos.write(buffer, 0, len);
                            }
                  
                            in.close();
                            zos.closeEntry();
                            zos.close();
                  
                            //Delete the compressed file from source
                            lastModifiedFile.delete();
                  
                        }
                    }
            
                    //Checking if the folder is empty then delete it
                    String[] empty = lastModifiedFolder.list();
                    if (empty.length == 0){
                        lastModifiedFolder.delete();
              
                    }
                }

                else if (listDir.length == 1){
                    File lastModifiedFolder = listDir[0];
                
                    //Create a new subdirectory path 
                    String SubdirPath1 = dirPath + lastModifiedFolder.getName() + "\\";
                    File Subdir1 = new File(SubdirPath1);
                    String[] NumberofFiles1 = lastModifiedFolder.list();
                
                
                
                    /* Creating a folder at tresorit that has the same name as the source 
                     * folder at local folder and move its compressed files in it 
                     */
                    String newdestDirectory2 = destDirectory + lastModifiedFolder.getName() + "\\";
                    File newDirectory2 = new File(newdestDirectory2); 
                    newDirectory2.mkdirs();  
                
                
                
                    if (NumberofFiles1.length > 1){
                                     
                        for (int h = 1; h < NumberofFiles1.length; h++){
                            //Get all the first modified file from the subdirectory
                            File[] files = Subdir1.listFiles();
                  
                            File lastModifiedFile1 = files[0];
                    
                            //Getting the name of the last modified file without the path
                            String filename1 = lastModifiedFile1.getName();
                    
                            //Removing the extension from filename
                            String name1 = filename1.substring(0, filename1.lastIndexOf('.'));
                    
                            //The Zip extension
                            String FILE_EXTENSION1 = ".Zip";
                    
                            //Create a filepath for the zip file
                            String filepath = newDirectory2 + "\\" + name1 + FILE_EXTENSION1;
                    
                            //For loop for getting the last modified file
                            for (int g = 1; g < files.length; g++){
                                if (lastModifiedFile1.lastModified() > files[g].lastModified()){
                                    lastModifiedFile1 = files[g];
                                }  
                            }
                    
                            FileOutputStream fos = new FileOutputStream(filepath);
                            ZipOutputStream zos = new ZipOutputStream(fos);
                  
                            //Adding a file entry to zip (lastModifiedFile.getName() = the name of the file)
                            ZipEntry ze = new ZipEntry(filename1);
                            zos.putNextEntry(ze);
                  
                            //Read the file from the given path
                            FileInputStream in = new FileInputStream(lastModifiedFile1);
                  
                            int len;
                            while ((len = in.read(buffer)) > 0){
                                zos.write(buffer, 0, len);
                            }
                  
                            in.close();
                            zos.closeEntry();
                            zos.close();
                  
                            //Delete the compressed file from source
                            lastModifiedFile1.delete();
                    
                        }    
                    }
                
                    else if (NumberofFiles1.length == 1){
                        String line;
                        String pidInfo ="";

                        Process p =Runtime.getRuntime().exec("C:\\Windows\\system32\\tasklist.exe");

                        BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));

                        while ((line = input.readLine()) != null) {
                            pidInfo+=line; 
                        }

                        input.close();
                    
                        //Checking if buslogger is running on windows
                        if(!pidInfo.contains("BusLogger.exe")){

                            //Create a new subdirectory path 
                            String SubdirPath2 = dirPath + lastModifiedFolder.getName() + "\\";
                            File Subdir2 = new File(SubdirPath1);
                            String[] NumberofFiles2 = lastModifiedFolder.list();
                            File[] files = Subdir2.listFiles();
                  
                            File lastModifiedFile2 = files[0];
                    
                            //Getting the name of the last modified file without the path
                            String filename2 = lastModifiedFile2.getName();
                    
                            //Removing the extension from filename
                            String name1 = filename2.substring(0, filename2.lastIndexOf('.'));
                    
                            //The Zip extension
                            String FILE_EXTENSION2 = ".Zip";
                        
                            //Create a filepath for the zip file
                            String filepath = newDirectory2 + "\\" + name1 + FILE_EXTENSION2;
                        
                            FileOutputStream fos = new FileOutputStream(filepath);
                            ZipOutputStream zos = new ZipOutputStream(fos);
                  
                            //Adding a file entry to zip (lastModifiedFile.getName() = the name of the file)
                            ZipEntry ze = new ZipEntry(filename2);
                            zos.putNextEntry(ze);
                  
                            //Read the file from the given path
                            FileInputStream in = new FileInputStream(lastModifiedFile2);
                  
                            int len;
                            while ((len = in.read(buffer)) > 0){
                                zos.write(buffer, 0, len);
                            }
                  
                            in.close();
                            zos.closeEntry();
                            zos.close();
                  
                            //Delete the compressed file from source
                            lastModifiedFile2.delete();
                            lastModifiedFolder.delete();
                    
                        }     
                    }
                }      
            }
            catch(Exception e){
            }
        }
       
    }
    
}
