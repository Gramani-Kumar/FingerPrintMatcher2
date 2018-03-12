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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 *
 * @author ganesh
 */
public class scanFingerImage implements Icon{

    int height;
    int width;
    Image image;
    
    
    scanFingerImage(int height, int width) {
        
        this.height = height;
        this.width  = width;
        image = null;
    }
    
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        if(image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
        else{
            //g.fillRect(x, y, getIconWidth(), getIconHeight());
            g.drawImage(null, x, y, Color.WHITE, null);
            //g.drawString("_SCANNED__", x, y);
        }
    }
    
    public void loadImage(String imageLocation) {

        try {
            //Read Image
            File imageFile = new File(imageLocation);

            Image img = ImageIO.read(imageFile);
            setImage(img);
            
        }
        catch (Exception e) {
            System.out.println("Exception " + e.getStackTrace());
        }
    }
    
    public void setImage(Image image){
        if(image != null) {
            this.image = image.getScaledInstance(getIconWidth(), getIconHeight(), Image.SCALE_FAST);
        }else {
            this.image = null;
        }
    }

    @Override
    public int getIconWidth() {
        return width;
    }    

    @Override
    public int getIconHeight() {
        return height;
    }
    
}
