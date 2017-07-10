/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck;

//import codecheck.data.CodeCheckData;
//import codecheck.file.CodeCheckFiles;
//import codecheck.workspace.CodeCheckWorkspace;
import codecheck.data.CodeCheckData;
import codecheck.file.CodeCheckFiles;
import codecheck.workspace.CodeCheckWorkspace;
import djf.AppTemplate;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import properties_manager.InvalidXMLFileFormatException;

/**
 *
 * @author Usman
 */
public class CodeCheckApp extends AppTemplate{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
	try{
        launch(args);}
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void buildAppComponentsHook() {
        fileComponent = new CodeCheckFiles(this);
        dataComponent = new CodeCheckData(this);
        workspaceComponent = new CodeCheckWorkspace(this);
    }

    
    
}
