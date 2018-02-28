/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isense.scanner;

import java.io.File;
import MFS100.FingerData;
import java.io.FileOutputStream;
/**
 *
 * @author ganesh
 */
public class repositoryHandler {

    
    private String repoPath; 
    
    private File repoCreateDirectory() {
        File dir = new File(repoPath);
        
        
    }
     
    public repositoryHandler() {
        
    }
    
    public int repoStoreContent(FingerData fInfo, infoPerson pInfo){
        
        //Create Directory.
        String dirName = pInfo.getName() + Integer.toString(pInfo.getAge()) + pInfo.getFingerNumber();
        
        File dir = new File(dirName);
        
        try{
            dir.mkdir();
        }
        catch(Exception e){
            //TODO exception.
            return -1;
        }
        
        //Create File and write into it.
        try {
            //String FilePath = new File("").getAbsolutePath();//System.getProperty("user.dir");

            dirName += File.separator +  FileName;
            FileOutputStream fos = new FileOutputStream(FilePath);
            fos.write(Bytes);
            fos.close();
        } catch (Exception ex) {
        }

        
        return 0;
    }
    
    public void setRepoPath(String path) {
        repoPath = path;
    }

    
    public infoPerson getContent(FingerData fInfo) {
        
        return new infoPerson();
    } 
    
    
    
    
}
