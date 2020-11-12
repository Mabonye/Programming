/*
 * This java program performs the following functions:
 * 1. Check the first modified folder if there is more than one folder,
 *    if yes then decompresses all the log files from tresorit first 
 *    modified folder to Ascom local folder.
 * 2. Then deletes the decompressed files from tresorit folder and the 
 *    source folder . 
 * 3. If there is only one folder in the tresorit directory, decompress
 *    all the zip files but do not delete the source folder.
 */
package decompression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.FileReader;
import java.util.Properties;

public class Decompression {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        long start = System.currentTimeMillis();
        long end = start + 600000;//run the program for 2 minutes
        
        //while (true) {
        while(System.currentTimeMillis() < end){
            try {
                
                //Loading java properties file
                Properties prop = new Properties();
                FileReader reader = new FileReader("C:\\PropertiesFiles\\Ascom.properties");
                prop.load(reader);

                byte[] buffer = new byte[1024];

                //Setting the source directory
                String sourceDirectory = prop.getProperty("Sourcepath");
                File sourcedir = new File(sourceDirectory);

                //Get a list of subdirectories in that source directory
                File listDir[] = sourcedir.listFiles();

                //Getting the destination directory
                String destDirectory = prop.getProperty("Destinationpath");
                
                /* Checking if there is unwanted file called "desktop.ini" 
                 * then delete it if present 
                 */
                String Unwantedpath = prop.getProperty("UnwantedPath");
                if (new File(Unwantedpath).exists()) {
                    new File(Unwantedpath).delete();
                }

                /* Checking the first modified subdirectory 
                 * If the list of subdirectories is more than one 
                 */
                if (listDir.length > 1) {
                    File lastModifiedFolder = listDir[0];
                    //System.out.println(lastModifiedFolder);
                    for (int j = 0; j < listDir.length; j++) {
                        if ((lastModifiedFolder.lastModified() > listDir[j].lastModified())) {
                            lastModifiedFolder = listDir[j];
                        }
                    }

                    /* Creating a new subdirectory path for the selected first modified 
                     * folder at the source and assign it to Subdir 
                     */
                    String SubdirPath = sourceDirectory + lastModifiedFolder.getName() + "\\";
                    File Subdir = new File(SubdirPath);

                    //Getting an array of files in that subdirectory
                    String[] NumberofFiles = lastModifiedFolder.list();
                    //System.out.println(NumberofFiles);
                    
                    String Unwantedpath1 = "C:\\Users\\SEprjJMa\\Documents\\NetBeansProjects\\Application\\SharedFolder\\desktop.ini";
                    if (new File(Unwantedpath1).exists()) {
                        new File(Unwantedpath1).delete();
                    }
                    /* For loop for iterating the number of files to be compressed 
                     * In the first modified folder
                     */
                    for (int k = 0; k < NumberofFiles.length; k++) {
                        String[] empty = lastModifiedFolder.list();
                        /* Checking if the number of files in the last modified folder 
                         * is greater than 0 
                         */
                        if (empty.length > 0) {
                            //Get all the first modified file from the subdirectory
                            File[] files = Subdir.listFiles();
                            File lastModifiedFile = files[0];

                            //Getting the name of the last modified file without the path
                            String filename = lastModifiedFile.getName();

                            /* Creating a folder at tresorit that has the same name as the source 
                             * folder at local folder and move its compressed files in it 
                             */
                            String newdestDirectory1 = destDirectory + lastModifiedFolder.getName() + "\\";
                            File newDirectory1 = new File(newdestDirectory1);
                            newDirectory1.mkdirs();

                            //Getting the last modified file from the lastModiiedFolder
                            for (int i = 0; i < files.length; i++) {
                                if (lastModifiedFile.lastModified() > files[i].lastModified()) {
                                    lastModifiedFile = files[i];
                                }
                            }

                            //Read the zipfile from the given path
                            ZipInputStream zis = new ZipInputStream(new FileInputStream(lastModifiedFile));

                            //Adding a file entry to zipEntry 
                            ZipEntry ze = zis.getNextEntry();

                            //When the zipEntry is not empty (has a zip file)
                            while (ze != null) {
                                /* Get the name of the selected file before compression
                                 * e.g. 141202 094318.log 
                                 */
                                String fileName = ze.getName();

                                //Create a destination path for the file
                                String filePath = newDirectory1 + "//" + fileName;
                                File newFile = new File(filePath);

                                /* create all non exists folders
                                 * else you will hit FileNotFoundException for compressed folder
                                 */
                                new File(newFile.getParent()).mkdirs();

                                FileOutputStream fos = new FileOutputStream(newFile);

                                int len;
                                while ((len = zis.read(buffer)) > 0) {
                                    fos.write(buffer, 0, len);
                                }

                                fos.close();
                                ze = zis.getNextEntry();
                            }

                            zis.closeEntry();
                            zis.close();

                            //Delete the compressed file from source
                            lastModifiedFile.delete();
                        }
                    }
                    //Checking if the folder is empty then delete it
                    String[] empty = lastModifiedFolder.list();
                    if (empty.length == 0) {
                        lastModifiedFolder.delete();
                    }
                } else if (listDir.length == 1) {
                    //Getting the last folder
                    File lastModifiedFolder = listDir[0];

                    //Create a new subdirectory path 
                    String SubdirPath1 = sourceDirectory + lastModifiedFolder.getName() + "\\";
                    File Subdir1 = new File(SubdirPath1);
                    String[] NumberofFiles1 = lastModifiedFolder.list();

                    /* Creating a folder at tresorit that has the same name as the source 
                     * folder at local folder and move its compressed files in it 
                     */
                    String newdestDirectory2 = destDirectory + lastModifiedFolder.getName() + "\\";
                    File newDirectory2 = new File(newdestDirectory2);
                    newDirectory2.mkdirs();

                    //Checking if the number of files in the last folder is >=1 
                    if (NumberofFiles1.length >= 1) {

                        //For all the number of files in the last folder
                        for (int h = 0; h < NumberofFiles1.length; h++) {
                            File[] files = Subdir1.listFiles();

                            File lastModifiedFile1 = files[0];

                            //Getting the name of the last modified file without the path
                            String filename1 = lastModifiedFile1.getName();

                            //For loop for getting the last modified file
                            for (int g = 1; g < files.length; g++) {
                                if (lastModifiedFile1.lastModified() > files[g].lastModified()) {
                                    lastModifiedFile1 = files[g];
                                }
                            }

                            //Read the zipfile from the given path
                            ZipInputStream zis = new ZipInputStream(new FileInputStream(lastModifiedFile1));

                            //Adding a file entry to zipEntry 
                            ZipEntry ze = zis.getNextEntry();

                            //When the zipEntry is not empty (has a zip file)
                            while (ze != null) {
                                /* Get the name of the selected file before compression
                                 * e.g. 141202 094318.log 
                                 */
                                String fileName1 = ze.getName();

                                //Create a destination path for the file
                                String filePath1 = newDirectory2 + "//" + fileName1;
                                File newFile1 = new File(filePath1);

                                /* create all non exists folders
                                 * else you will hit FileNotFoundException for compressed folder
                                 */
                                new File(newFile1.getParent()).mkdirs();

                                //System.out.println("file unzip : "+ newFile.getAbsoluteFile());
                                FileOutputStream fos = new FileOutputStream(newFile1);

                                int len;
                                while ((len = zis.read(buffer)) > 0) {
                                    fos.write(buffer, 0, len);
                                }

                                fos.close();
                                ze = zis.getNextEntry();
                            }

                            zis.closeEntry();
                            zis.close();

                            //Delete the compressed file from source
                            lastModifiedFile1.delete();
                        }
                    }
                }
            } catch (IOException e) {
            }
        }
    }

}

