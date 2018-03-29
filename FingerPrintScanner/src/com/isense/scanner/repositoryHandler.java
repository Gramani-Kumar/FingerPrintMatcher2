/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isense.scanner;

import java.io.File;
import MFS100.FingerData;
import MFS100.MFS100;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.json.*;

/**
 *
 * @author ganesh
 */
public class repositoryHandler {

    
    private String repoPath; 
    private final ArrayList templateList = new ArrayList(); 
    
    private void addTemplates(String dirPath, File[] fList) throws FileNotFoundException, IOException{

        for(File file : fList ){
            if(file.isDirectory()) {
                addTemplates(file.getAbsolutePath(), file.listFiles());
            }
            
            repoData rd = null;
            
            if(file.getName().equals("ISOTemplate.iso")){
                rd = new repoData();
                FileInputStream fis = new FileInputStream(file);
                fis.read(rd.getIsoTemplate());
                fis.close();
                System.out.println("The ISO Tempalted added ");
            }
            if(file.getName().equals("AnsiTemplate.ansi")){
                rd = new repoData();
                FileInputStream fis = new FileInputStream(file);
                fis.read(rd.getAnsiTemplate());
                fis.close();
                System.out.println("The Ansi Tempalted added ");
            }
            
            if(rd != null) {
                rd.fileLocation(dirPath);
                System.out.println("The AbsolutePath " + dirPath);

                //Update.
                templateList.add(rd);
            }
        }
    }
    
    private void scanRepoForTemplates() {
        
        //Scan RepoPath.
        File fList[] = new File(repoPath).listFiles();
        
        try {
            addTemplates(repoPath, fList);
        }catch(Exception e){
            System.out.println("Exception: on ScanRepoForTemplates");
        }
    }
    
    private void dumpToFile(String fileName, byte[] data){

        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            fout.write(data);
            fout.close();
            
        }catch(Exception e) {
            //TODO exception on write.
            System.out.println("Exception in storing files " + e.getMessage());
        }
    }

    private void dumpToJson(String fileName, infoPerson pInfo, int quality, int Nfiq){

        JsonObject person = Json.createObjectBuilder()
                            .add("Name", pInfo.getName())
                            .add("Age", pInfo.getAge())
                            .add("Sex", pInfo.getSex())
                            .add("FingerName",pInfo.getFingerNumber())
                            .add("Quality",
                                    Json.createObjectBuilder().add("Quality",quality)
                                    .add("NITS FIQ", Nfiq)
                                    .build()
                                )
                            .build();
        //write to file
        try {
            FileOutputStream os = new FileOutputStream(fileName);
            //FileWriter
            JsonWriter jsonWriter = Json.createWriter(os);
            jsonWriter.writeObject(person);
            jsonWriter.close();
            System.out.println("Data Successfully Saved");
        }
        catch(Exception e) {
            System.out.println("Exception on creating data Json" + e.getMessage());
        }
    }
     
    public repositoryHandler() {
        repoPath = "";
    }
    
    public int repoStoreContent(FingerData fInfo, infoPerson pInfo){
        
        //Create Directory.
        String dirName = pInfo.getName() + Integer.toString(pInfo.getAge()) + pInfo.getFingerNumber();

        System.out.println("The directory to create " + dirName);
        System.out.println("The Repo path           " + repoPath);
        
        File dir = new File(repoPath + File.separator + dirName);
        
        try{
            if(dir.mkdir()) {
                System.out.println("The directory created Successfully ");
            }
            else {
                System.out.println("Issue in creating directory ");
                return -1;
            }
        }
        catch(Exception e){
            //TODO exception.
            System.out.println("Exception on Directory creating " + e.getMessage());
            return -1;
        }
        
        //JSON Data.
        dumpToJson(repoPath + File.separator + dirName + File.separator + "data.json",
                        pInfo, fInfo.Quality(), fInfo.Nfiq());

        //TODO ::Span thread to dump data to files.
        //dumpToFile(repoPath + File.separator + dirName + File.separator + "FingerImage.bmp", fInfo.FingerImage());
        dumpToFile(repoPath + File.separator + dirName + File.separator + "ISOTemplate.iso", fInfo.ISOTemplate());
        //dumpToFile(repoPath + File.separator + dirName + File.separator + "ISOImage.iso", fInfo.ISOImage());
        dumpToFile(repoPath + File.separator + dirName + File.separator + "AnsiTemplate.ansi", fInfo.ANSITemplate());
        //dumpToFile(repoPath + File.separator + dirName + File.separator + "RawData.raw", fInfo.RawData());
        //dumpToFile(repoPath + File.separator + dirName + File.separator + "WSQImage.wsq", fInfo.WSQImage());
        
        //Add to List
        repoData rd = new repoData();
        
        rd.fileLocation(repoPath+ File.separator + dirName);
        rd.storeAnsiTemplate(fInfo.ANSITemplate());
        rd.storeIsoTemplate(fInfo.ISOTemplate());
        
        templateList.add(rd);
        
        return 0;
    }
    
    public void setRepoPath(String path) {
        repoPath = path;
        
        scanRepoForTemplates();
        //TODO: Span thread to populate to list 
        //Runnable runnable = new Runnable() {
        
        //public void run() {
            
        //}
        //};
        
        //Thread populate = new Thread(runnable);
        //populate.start();
    }


    private infoPerson getInfoPerson(String fileLocation) {

        infoPerson iPerson = null; 
        
        try {
            FileInputStream fis = new FileInputStream(fileLocation + File.separator + "data.json");
            // Get the JsonObject structure from JsonReader.

            JsonReader reader = Json.createReader(fis);
            // Get the JsonObject structure from JsonReader.
            JsonObject confObj = reader.readObject();
            iPerson = new infoPerson();
            iPerson.setName(confObj.getString("Name"));
            iPerson.setAge(confObj.getInt("Age"));
            iPerson.setSex(confObj.getString("Sex"));
            iPerson.setFingerNumber(confObj.getString("FingerName"));
            //System.out.println("Name  : " + iPerson.getName() + "" + iPerson.getFingerNumber() +" " + iPerson.getSex());

            fis.close();
            return iPerson;
        }
        catch(Exception e) {
            System.out.println("Exception " + e.getStackTrace());
            
        }
        return iPerson;
    }
    
    public infoPerson checkIsoTemplate(MFS100 devLib, byte[] isoTemplate){

        long startTime;
        long endTime;
        
        repoData rd;

        startTime = System.nanoTime();
        
        for(int i = 0; i < templateList.size(); i ++) {
            rd = (repoData) templateList.get(i);
            int score = devLib.MatchISO(isoTemplate, rd.getIsoTemplate());
            if(score > 14000){
                
                endTime = System.nanoTime();

                System.out.println("******** Search Result in ISO **********");
                System.out.println("Elapsed Time in Search " + String.valueOf((endTime - startTime) / 1000 ) + "us" );
                System.out.println("Total Record Searched  " + String.valueOf(i + 1) );
                System.out.println("Total Record in System " + String.valueOf(templateList.size() + 1) );
                System.out.println("*****************************************");
                
                return getInfoPerson(rd.getFileLocation());
            }
        }
        return null;
    }
    
    public infoPerson checkAnsiTemplate(MFS100 devLib, byte[] ansiTemplate){
        repoData rd;

        for(int i = 0; i < templateList.size(); i ++) {
            rd = (repoData) templateList.get(i);
            int score = devLib.MatchANSI(ansiTemplate, rd.getAnsiTemplate());
            System.out.println("Ansi Match Score :" + String.valueOf(i) + ":: " + String.valueOf(score));
             if(score > 14000){
              return getInfoPerson(rd.getFileLocation());
            }
        }
        return null;
    }
}

 class repoData {

    private byte [] isoTemplate;
    private byte [] ansiTemplate;
    
    private String fileLocation;
    
    public repoData() {
        //TODO intialialize
        isoTemplate = new byte[512];
        ansiTemplate = new byte[512];
        fileLocation = "";
    }
    
    public void storeIsoTemplate(byte [] isoTemplate){
       this.isoTemplate = Arrays.copyOf(isoTemplate, isoTemplate.length);
    }
    
    public void storeAnsiTemplate(byte [] ansiTemplate){
       this.ansiTemplate = Arrays.copyOf(ansiTemplate, ansiTemplate.length);
    }

    public byte[] getIsoTemplate(){
        
         return isoTemplate;
    }
    
    public byte [] getAnsiTemplate() {
        
        return ansiTemplate;
    }
    
    public void fileLocation(String location) {
        fileLocation = location;
    }
    
    public String getFileLocation() {
        return fileLocation;
    }
    
}