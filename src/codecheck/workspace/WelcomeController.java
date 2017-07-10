/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.DUP_CODECHECK_MESSAGE;
import static codecheck.CodeCheckProps.DUP_CODECHECK_TITLE;
import static codecheck.CodeCheckProps.NEW_CONFIRMATION;
import static codecheck.CodeCheckProps.NEW_CONF_CONTENT;
import static codecheck.CodeCheckProps.NEW_CONTENT;
import static codecheck.CodeCheckProps.NEW_TITLE;
import codecheck.data.CodeCheck;
import codecheck.data.CodeCheckData;
import static djf.settings.AppPropertyType.NEW_COMPLETED_MESSAGE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_TITLE;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class WelcomeController {
     CodeCheckApp app;
    
    public WelcomeController(CodeCheckApp initApp){
        app = initApp;
    }
    
    public void handleNew(){
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        CodeCheckData data = (CodeCheckData) app.getDataComponent();
        
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(props.getProperty(NEW_CONFIRMATION));
        alert.setContentText(props.getProperty(NEW_CONF_CONTENT));
        
        TextInputDialog dialog1 = new TextInputDialog();
        dialog1.setTitle(props.getProperty(NEW_TITLE));
        dialog1.setContentText(props.getProperty(NEW_CONTENT));
        
        
        List<File> directories = new ArrayList<>();
        // String title;
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Optional<String> result1 = dialog1.showAndWait();
            if(result1.isPresent()){
                String title = result1.get();
                
                String path= "C:\\Users\\Usman\\Desktop\\219\\Final\\codecheckproject\\CodeCheck\\work\\" + title;
                
                CodeCheck codeC = new CodeCheck(title, path);
                File CC = new File(codeC.getPath());
                
                if(CC.mkdir()){
                    System.out.println("Code Check successfully created");
                    directories.add(new File(CC.getPath() + "\\blackboard"));
                    directories.add(new File(CC.getPath() + "\\submissions"));
                    directories.add(new File(CC.getPath() + "\\projects"));
                    directories.add(new File(CC.getPath() + "\\code"));
                    data.addCodeCheck(codeC);
                    
                    for(File f : directories) {
                        if(f.mkdir()) {
                           System.out.println(f.getPath() + " made");
                        }
                        else{
                          System.out.println(f.getPath() + " not made");
                        }
                    }
                    //fix this app.getGUI().getPrimaryStage().setTitle("Code Check - "+ title);
                    
                    //data.setTitle(title);
                
                    // WE MAY HAVE TO SAVE CURRENT WORK
                    boolean continueToMakeNew = true;
                    // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
                    if (continueToMakeNew) {
                    // RESET THE WORKSPACE
                    app.getWorkspaceComponent().resetWorkspace();

                    // RESET THE DATA
                    app.getDataComponent().resetData();
                
                    // NOW RELOAD THE WORKSPACE WITH THE RESET DATA
                    app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

                    // MAKE SURE THE WORKSPACE IS ACTIVATED
                    app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
		
                    //app.getWorkspaceComponent().getWorkspace().
                    // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                    // THE APPROPRIATE CONTROLS
                    // TELL THE USER NEW WORK IS UNDERWAY
                    dialog.show(props.getProperty(NEW_COMPLETED_TITLE), props.getProperty(NEW_COMPLETED_MESSAGE));
                    }
                }
                else{
                    Alert rename = new Alert(Alert.AlertType.ERROR);
                    rename.setTitle(props.getProperty(DUP_CODECHECK_TITLE));
                    rename.setContentText(props.getProperty(DUP_CODECHECK_MESSAGE));
                    rename.showAndWait();
                    System.out.println("Code Check with this name already exists");
                }
            }
    }
    }
}

