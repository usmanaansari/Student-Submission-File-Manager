/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Usman
 */
public class Step1Controller {
    
    CodeCheckApp app;
    
    public Step1Controller(CodeCheckApp initApp){
        app = initApp;
    }
    
    
    public void handleRefresh() throws IOException{
        //System.out.println("here: " + PATH_WORK + (app.getGUI().getWindow().getTitle().substring(13)));
        File theFile = new File(PATH_WORK + (app.getGUI().getWindow().getTitle().substring(13)));
        if( theFile.isFile()){
        app.getFileComponent().loadData(app.getDataComponent(), theFile.getAbsolutePath());
        }
        
    }
}
