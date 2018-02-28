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

/**
 *
 * @author ganesh
 */
public class infoPerson {
    
    private String  name;
    private String  sex;
    private int     age;
    private String  fingerNumber;

    public infoPerson() {
        age = 0;
        sex = "";
        name = "";
        fingerNumber = "";
    }
    
    public infoPerson(String name, String sex, String fNumber, int age) {

        this.age = age;
        this.sex = sex;
        this.name = name;
        this.fingerNumber = fNumber;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setFingerNumber(String fNumber){
        this.fingerNumber = fNumber;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public String getFingerNumber() {
        return this.fingerNumber;
    }
    
    public String getSex() {
        return this.sex;
    }
}
