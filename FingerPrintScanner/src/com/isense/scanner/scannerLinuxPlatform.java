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

import MFS100.FingerData;
import MFS100.MFS100;
import MFS100.MFS100Event;

/**
 *
 * @author ganesh
 */
public class scannerLinuxPlatform implements scannerPlatform, MFS100Event  {

    private MFS100      scannerDevice;    
    private int         timeOut;
    private int         expectScore;
    private int         expectNFiq;
    private int         numOfReTry;
    private FingerData  scannedInfo;

    public scannerLinuxPlatform() {
        
         scannerDevice  = new MFS100(this, "");
         timeOut        = 1500;
         expectScore    = 65;
         expectNFiq     = 3;  
         numOfReTry     = 4;
        
    }
    
    @Override
    public int initializeDevice() {
        
        scannerDevice.Init();
        return scannerPlatform.Success;
    }    

    @Override
    public FingerData startScanning(int timeOut, int quality) {
    
        int  retValue = 0;
        scannedInfo  = new FingerData();
 
        retValue = scannerDevice.AutoCapture(scannedInfo, quality, timeOut, true);
        if(retValue != 0) 
            return null;
        
        return scannedInfo;
    }

    @Override
    public int stopScanning() {
        //Abort scanning.
        return scannerPlatform.Success;
    }

    @Override
    public int deInit() {
        return scannerDevice.Uninit();
    }

    @Override
    public int compareData(byte[] data1, byte[] data2) {
        
        int score = 0;
        
        score = scannerDevice.MatchISO(data1, data2);
        if(score > 14000) {
            return scannerPlatform.Success;
        }else{
            return scannerPlatform.Error;
        }
    }

    @Override
    public void OnPreview(FingerData fd) {
        // do Nothing.
    }

    @Override
    public void OnCaptureCompleted(boolean bln, int i, String string, FingerData fd) {
        // do Nothing.
    }

    @Override
    public boolean isConnected() {
        return scannerDevice.IsConnected();
    }
    
    
    
    
}
