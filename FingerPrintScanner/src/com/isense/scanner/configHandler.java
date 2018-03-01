/*
 * Copyright 2018 ganesh.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isense.scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;

/**
 *
 * @author ganesh
 */
public class configHandler {
    
    private String confPath;
    private String repoLocation;
    private String devMake;
    private String devSerialNumber;
    
    public static String CONFIG_FILE_NAME = "appConf.json";
    
    configHandler() throws FileNotFoundException {
        //Application Configuration.
        repoLocation = "";
        devMake = "tmpDevMake";
        devSerialNumber = "tmpDevNum";
        
        //File conf = new File(System.getProperty("user.home"));
        JsonObject  confObj = null;
        String path = System.getProperty("user.home");
        path += File.separator + ".fingerPrintScanner";
        
        confPath = path;
        
        File conf = new File(path);
        boolean success = conf.mkdir();
        if(success) {
            //TODO handle.
            //New Configuration directory created.
        }else{
            //Already avaialble.
            FileInputStream fis = new FileInputStream(path + File.separator + "appConf.json");
            // Get the JsonObject structure from JsonReader.
            try (JsonReader reader = Json.createReader(fis)) {
                // Get the JsonObject structure from JsonReader.
                confObj = reader.readObject();
                repoLocation = confObj.getString("RepoPath");
                
                JsonObject devObj = confObj.getJsonObject("Device");
                devMake = devObj.getString("make");
                devSerialNumber = devObj.getString("serial");
                fis.close();
            }
            catch(Exception e) {
                System.out.println("Exception while Parse Applicatin Config JSON");
            }
        }
    }
    
    public String getConfigRepoLocation(){
        
        return repoLocation;
    }
    
    public String getDevSerialNumber() {
        
        return devSerialNumber;
    }
    
    public String getDevMake() {
        
        return devMake;
    }
    
    private void updateConfig()throws IOException, FileNotFoundException {
        //Write to AppConfig JSON.
        //File confFile = new File(repoLocation + File.separator + CONFIG_FILE_NAME);
        JsonObject configObj = Json.createObjectBuilder()
                    .add("Device", Json.createObjectBuilder()
                            .add("make",this.devMake)
                            .add("serial", this.devSerialNumber)
                            .build()
                        )
                    .add("RepoPath", this.repoLocation)
                    .build();

        FileOutputStream fos = new FileOutputStream(confPath + File.separator + CONFIG_FILE_NAME);
        JsonWriter jsonWriter = Json.createWriter(fos);
        jsonWriter.writeObject(configObj);
        jsonWriter.close();
        fos.close();
        
    }
    
    public void updateDeviceMakeConfig(String devMake) { 
        if( !devMake.equals(this.devMake)){
            this.devMake = devMake;
        }
        try {    
            updateConfig();
        }
        catch(Exception e){
        }
    }
        
    public void updateDeviceSerialNumberConfig(String devMake) { 
        if( !devMake.equals(this.devMake)){
            this.devMake = devMake;
        }
        try {    
            updateConfig();
        }
        catch(Exception e){
        }
    }
        
    public void updateRepoLocationConfig(String repoLocation) { 

        if( !repoLocation.equals(this.repoLocation)){
            this.repoLocation = repoLocation;
        }
        try {    
            updateConfig();
        }
        catch(Exception e){
        }
    }
}
