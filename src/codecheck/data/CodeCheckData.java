/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.data;

import codecheck.CodeCheckApp;
import djf.components.AppDataComponent;
import java.io.File;
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
    ObservableList<String> names;
    ObservableList<String> bbs;
    File workFile;
    File bbFile;
    public CodeCheckData(CodeCheckApp initApp) {
    CodeCheckTitle = "";
    codechecks = FXCollections.observableArrayList();
    workPath = "C:\\Users\\Usman\\Desktop\\219\\CodeCheckProj\\CodeCheck\\work\\"; 
    workFile = new File(workPath); 
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
    public void addCodeCheck1(CodeCheck code){
        codechecks.add(code);
    }
    public ObservableList<String> getCodeCheckNames(){
        names = FXCollections.observableArrayList();
    for(File file : workFile.listFiles()){
       if(file.isDirectory()){
            addCodeCheck(file.getName(),file.getPath());
            names.add(file.getName());
            
           }
        }
        return names;
    }
    public ObservableList<CodeCheck> getCodeChecks(){
        return codechecks;
    }
    public ObservableList<String> getBlackBoardSubs(){
        bbs = FXCollections.observableArrayList();
        for(File file : workFile.listFiles()){
            if(file.isDirectory()){
            File bbFile = new File(file.getPath() + "blackboard\\");
            for(File file1 : bbFile.listFiles()){
                bbs.add(file1.getName());
            }
            }
        }
        System.out.println("Yo" + bbs.size());
        return bbs;
    }
    public String getWorkPath(){
        return workPath;
    }
    public void setRecents(){
        
    }
    public File getWorkFile(){
        return workFile;
    }
}
