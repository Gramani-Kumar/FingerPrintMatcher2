/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isense.scanner;

import MFS100.*;
/**
 *
 * @author ganesh
 */
public class mfsDeviceHandler implements MFS100Event  {
    
    @Override
    public void OnPreview(final FingerData fingerData) {
        System.out.println("OnPrivew Quality  : " + String.valueOf(fingerData.Nfiq()));
        //System.out.println("The Nfig    Level : " + String.valueOf(fingerData.Quality()));

    }
    
    @Override
    public void OnCaptureCompleted(boolean status, int errorCode, String errorMsg, final FingerData fingerData) {

        System.out.println("OnCaputreCompleted  ");
        System.out.println("OnCaputre Quality : " + String.valueOf(fingerData.Nfiq()));
        System.out.println("The Nfig    Level : " + String.valueOf(fingerData.Quality()));
    }
    
    mfsDeviceHandler() {
        
    }
    
    
    public void storeData(final FingerData fData) {
        
        //Create Directory on the name.
        
        System.out.println("data Ready to store ");
        
        System.out.println("The Nfig    Level : " + String.valueOf(fData.Nfiq()));

    }
    
    
}
