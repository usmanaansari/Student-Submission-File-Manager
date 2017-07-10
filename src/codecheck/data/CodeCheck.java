/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Usman
 */
public class CodeCheck {
    StringProperty title;
    StringProperty path;
    
    public CodeCheck(String t, String p){
        title = new SimpleStringProperty(t);
        path = new SimpleStringProperty(p);
    }

    
    public String getTitle(){
        return title.getValue();
    }
    public String getPath(){
        return path.getValue();
    }
    public void setTitle(String value){
        title.set(value);
    }
    public void setPath(String value){
        path.setValue(value);
    }
}
