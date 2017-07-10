/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.data;

import codecheck.CodeCheckApp;
import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Usman
 */
public class CodeCheckData implements AppDataComponent {
    CodeCheckApp app;
    String CodeCheckTitle;
    String workPath;
    ObservableList<CodeCheck> codechecks;
    
    
    public CodeCheckData(CodeCheckApp initApp) {
    CodeCheckTitle = "";
    codechecks = FXCollections.observableArrayList();
    workPath = "C:\\Users\\Usman\\Desktop\\219\\CodeCheckProj\\CodeCheck\\work\\"; 
    }
   
    @Override
    public void resetData() {
        
    }
    
    public void setTitle(String title){
        CodeCheckTitle = title;
    }
    public String getTitle(){
        return CodeCheckTitle;
    }
    public void addCodeCheck(String title, String path){
        CodeCheck code = new CodeCheck(title, path);
        codechecks.add(code);
    }
   
    public ObservableList getCodeChecks(){
        return codechecks;
    }
    public String getWorkPath(){
        return workPath;
    }
}
