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
    workPath = "C:\\Users\\Usman\\Desktop\\219\\Final\\codecheckproject\\CodeCheck\\work\\"; 
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
    public void addCodeCheck(CodeCheck cc){
        String p = cc.getPath();
        codechecks.add(cc);
    }
    public ObservableList getCodeChecks(){
        return codechecks;
    }
    public String getWorkPath(){
        return workPath;
    }
}
