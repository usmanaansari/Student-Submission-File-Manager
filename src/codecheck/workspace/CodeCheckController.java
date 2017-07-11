/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.ABOUT_MESSAGE;
import static codecheck.CodeCheckProps.ABOUT_TITLE;
import static codecheck.CodeCheckProps.APP_PATH_WORK;
import static codecheck.CodeCheckProps.DUP_CODECHECK_MESSAGE;
import static codecheck.CodeCheckProps.DUP_CODECHECK_TITLE;
import static codecheck.CodeCheckProps.NEW_CONFIRMATION;
import static codecheck.CodeCheckProps.NEW_CONF_CONTENT;
import static codecheck.CodeCheckProps.NEW_CONTENT;
import static codecheck.CodeCheckProps.NEW_TITLE;
import static codecheck.CodeCheckProps.RENAME_MESSAGE;
import static codecheck.CodeCheckProps.RENAME_TITLE;
import codecheck.data.CodeCheck;
import codecheck.data.CodeCheckData;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.LOAD_WORK_TITLE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_MESSAGE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_TITLE;
import static djf.settings.AppPropertyType.NEW_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.NEW_ERROR_TITLE;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
//import org.apache.commons.io.FileUtils;
//import static org.apache.commons.io.FileUtils.directoryContains;
//import static org.apache.commons.io.FileUtils.forceMkdir;
/**
 *
 * @author Usman
 */
public class CodeCheckController {
    CodeCheckApp app;
    
    public CodeCheckController(CodeCheckApp initApp){
        app = initApp;
    }
    
    public void handleNew() throws IOException{
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        CodeCheckData data = (CodeCheckData) app.getDataComponent();
        
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
                
                String path= "C:\\Users\\Usman\\Desktop\\219\\CodeCheckProj\\CodeCheck\\work\\" + title;
                
                CodeCheck codeC = new CodeCheck(title, path);
                File CC = new File(codeC.getPath());
                
                
                if(CC.mkdir()){
                    System.out.println("Code Check successfully created");
                    directories.add(new File(CC.getPath() + "\\blackboard"));
                    directories.add(new File(CC.getPath() + "\\submissions"));
                    directories.add(new File(CC.getPath() + "\\projects"));
                    directories.add(new File(CC.getPath() + "\\code"));
                    data.addCodeCheck(title,path);
                    
                    for(File f : directories) {
                        if(f.mkdir()) {
                           System.out.println(f.getPath() + " made");
                        }
                        else{
                          System.out.println(f.getPath() + " not made");
                        }
                    }
                    app.getGUI().getPrimaryStage().setTitle("Code Check - "+ title);
                    
                    data.setTitle(title);
                    //System.out.println(title);
                    
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
                    
                    app.getFileComponent().saveData(data, path+"File");
                    //app.getWorkspaceComponent().getWorkspace().
                    // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                    // THE APPROPRIATE CONTROLS
                    // TELL THE USER NEW WORK IS UNDERWAY
                    dialog.show(props.getProperty(NEW_COMPLETED_TITLE), props.getProperty(NEW_COMPLETED_MESSAGE));
                    }
                }
                else{
                    Alert rename = new Alert(AlertType.ERROR);
                    rename.setTitle(props.getProperty(DUP_CODECHECK_TITLE));
                    rename.setContentText(props.getProperty(DUP_CODECHECK_MESSAGE));
                    rename.showAndWait();
                    System.out.println("Code Check with this name already exists");
                }
            }
    }
}
    public void handleLoad(){
        
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToOpen = true;
        
        // THE USER CAN OPT OUT HERE WITH A CANCEL
        //continueToOpen = promptToSave();
        // IF THE USER REALLY WANTS TO OPEN A Course
        if (continueToOpen) {
            // GO AHEAD AND PROCEED LOADING A Course
            promptToOpen();
            
        }
    }
    public void handleNext(){
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step1Workspace work1 = new Step1Workspace(app);
        Step2Workspace work2 = new Step2Workspace(app);
        //Button nextButton = work.nextButton;
        BorderPane appPane = work.appPane;
        
        HBox Main1 = work1.getStep1();
        HBox Main2 = work2.getStep2();
       
       
       Pane workspace = app.getWorkspaceComponent().getWorkspace();
     
        if(appPane.getCenter().equals(Main1)){
            appPane.setCenter(Main2);
        }
        if(appPane.getCenter().equals(Main2)){
            appPane.setCenter(Main1);
            workspace = Main1;
        }
        
    }
    
    public void handleRename() throws IOException{
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        CodeCheckData data = (CodeCheckData) app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(props.getProperty(RENAME_TITLE));
        dialog.setContentText(props.getProperty(RENAME_MESSAGE));
        ObservableList<CodeCheck> codeChecks = data.getCodeChecks();
        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        String path = data.getWorkPath();
        if (result.isPresent()){
            String p = path + result.get();
            File file = new File(p);
            if(file.exists()){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("A Code Check with that name already exists");
                alert.showAndWait();
            }
            //}
            else{
                
                 app.getGUI().getPrimaryStage().setTitle("Code Check - " + result.get());
                 //app.getFileComponent().saveData(data, path+"yo");
                 File f = new File(path + data.getTitle());
                 data.setTitle(result.get());
                 f.renameTo(file);
                 
                
            }
            
        }
    }
        
        
    public void handleAbout(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(props.getProperty(ABOUT_TITLE));
        alert.setContentText(props.getProperty(ABOUT_MESSAGE));

        alert.showAndWait();        
    }
    
    private void promptToOpen() {
	// WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
                
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
        
}
    

