/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isense.scanner;

import java.io.File;
import MFS100.FingerData;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
/**
 *
 * @author ganesh
 */
public class repositoryHandler {

    
    private String repoPath; 
    
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
        
        //TODO ::Span thread to dump data to files.
        dumpToFile(repoPath + File.separator + dirName + File.separator + "FingerImage.bmp", fInfo.FingerImage());
        dumpToFile(repoPath + File.separator + dirName + File.separator + "ISOTemplate.iso", fInfo.ISOTemplate());
        dumpToFile(repoPath + File.separator + dirName + File.separator + "ISOImage.iso", fInfo.ISOImage());
        dumpToFile(repoPath + File.separator + dirName + File.separator + "AnsiTemplate.ansi", fInfo.ANSITemplate());
        dumpToFile(repoPath + File.separator + dirName + File.separator + "RawData.raw", fInfo.RawData());
        dumpToFile(repoPath + File.separator + dirName + File.separator + "WSQImage.wsq", fInfo.WSQImage());
        
        return 0;
    }
    
    public void setRepoPath(String path) {
        repoPath = path;
    }

    
    public infoPerson getContent(FingerData fInfo) {
        
        return new infoPerson();
    } 
    
    
    
    
}
