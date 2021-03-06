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
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_MESSAGE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_TITLE;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class WelcomeController {
     CodeCheckApp app;
     BorderPane appPane;
    public WelcomeController(CodeCheckApp initApp){
        app = initApp;
        appPane = app.getGUI().getAppPane();
    }
    
    public void handleNew() throws IOException{
        CodeCheckWorkspace work1 = (CodeCheckWorkspace) app.getWorkspaceComponent();
        WelcomeWorkspace work = work1.welcwork;
        CodeCheckData data = (CodeCheckData) app.getDataComponent();
        
        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        VBox leftBox = work.leftBox;
        VBox rightBox = work.rightBox;
        BorderPane headPane = work.headPane;
        
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
                
                String path= PATH_WORK + title;
                
                CodeCheck codeC = new CodeCheck(title, path);
                File CC = new File(codeC.getPath());
                
                if(CC.mkdir()){
                    System.out.println("Code Check successfully created");
                    directories.add(new File(CC.getPath() + "\\blackboard"));
                    directories.add(new File(CC.getPath() + "\\submissions"));
                    directories.add(new File(CC.getPath() + "\\projects"));
                    directories.add(new File(CC.getPath() + "\\code"));
                    data.addCodeCheck(title, path);
                    
                    for(File f : directories) {
                        if(f.mkdir()) {
                           System.out.println(f.getPath() + " made");
                        }
                        else{
                          System.out.println(f.getPath() + " not made");
                        }
                    }
                    
                    
                    data.setTitle(title);
                
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
                    
                    appPane.getChildren().remove(headPane);
                    appPane.getChildren().remove(leftBox);
                    appPane.getChildren().remove(rightBox);
                    
                    appPane.setTop(app.getGUI().getTopToolbarPane());
                    
                    app.getGUI().getPrimaryStage().setTitle("Code Check - " + title);
                    appPane.setCenter(work1.Step1);
                    
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
    public void handleX(){
        CodeCheckWorkspace work1 = (CodeCheckWorkspace) app.getWorkspaceComponent();
        WelcomeWorkspace work = work1.welcwork;
        VBox leftBox = work.leftBox;
        VBox rightBox = work.rightBox;
        BorderPane headPane = work.headPane;
        
        appPane.getChildren().remove(headPane);
        appPane.getChildren().remove(leftBox);
        appPane.getChildren().remove(rightBox);

        appPane.setTop(app.getGUI().getTopToolbarPane());

    }
    public void handleLoadRecent(String name){
     
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step1Workspace work1 = work.work1;
        WelcomeWorkspace wwork = work.welcwork;
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        VBox leftBox = wwork.leftBox;
        VBox rightBox = wwork.rightBox;
        BorderPane headPane = wwork.headPane;
        File selectedFile = new File(app.getGUI().getP() + name);
        //System.out.print(selectedFile.toPath());
        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                
                BorderPane appPane = app.getGUI().getAppPane();
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                //app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
//                String title = selectedFile.getName();
//                app.getGUI().getWindow().setTitle("Code Check - " + title);
//                File[] files = selectedFile.listFiles();
//                ObservableList<String> bbs = FXCollections.observableArrayList();
//                for(File f :files){
//                    if(f.getName().equals("blackboard")){
//                        File bbFile = new File(selectedFile.getAbsolutePath() + "\\blackboard\\");
//                        for(File b : bbFile.listFiles()){             
//                            bbs.add(b.getName());
//                        }
//                       
//                    }
//                }
                updateStep1Table(selectedFile);
                updateStep2Table(selectedFile);
                updateStep3Table(selectedFile);
                updateStep4Table(selectedFile);
                updateStep5Table(selectedFile);
                
                //work1.getBBSubs().setItems(bbs);
                appPane.getChildren().remove(headPane);
                appPane.getChildren().remove(leftBox);
                appPane.getChildren().remove(rightBox);
        
                appPane.setTop(app.getGUI().getTopToolbarPane());
                
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
    public void updateStep1Table(File selectedFile) {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step1Workspace work1 = work.work1;
        String title = selectedFile.getName();
        app.getGUI().getWindow().setTitle("Code Check - " + title);
        File[] files = selectedFile.listFiles();
        ObservableList<String> bbs = FXCollections.observableArrayList();
        for (File f : files) {
            if (f.getName().equals("blackboard")) {
                File bbFile = new File(selectedFile.getAbsolutePath() + "\\blackboard\\");
                for (File b : bbFile.listFiles()) {
                    bbs.add(b.getName());
                }

            }
        }
        work1.getBBSubs().setItems(bbs);
    }

    public void updateStep2Table(File selectedFile) {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step2Workspace work2 = work.work2;
        String title = selectedFile.getName();
        app.getGUI().getWindow().setTitle("Code Check - " + title);
        File[] files = selectedFile.listFiles();
        ObservableList<File> Sbs = FXCollections.observableArrayList();
        ObservableList<String> studs = FXCollections.observableArrayList();
        for (File f : files) {
            if (f.getName().equals("submissions")) {
                File SbFile = new File(selectedFile.getAbsolutePath() + "\\submissions\\");
                for (File s : SbFile.listFiles()) {
                    Sbs.add(s);
                    studs.add(s.getName());
                }

            }
        }
        if(studs.isEmpty()){
            work2.Rename.setDisable(true);
        }
        else{
            work2.Rename.setDisable(false);
        }
        work2.getSSubs().setItems(studs);
    }
    public void updateStep3Table(File selectedFile){
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step3Workspace work3 = work.work3;
        String title = selectedFile.getName();
        app.getGUI().getWindow().setTitle("Code Check - " + title);
        File[] files = selectedFile.listFiles();
        ObservableList<File> Sbs = FXCollections.observableArrayList();
        ObservableList<String> studs = FXCollections.observableArrayList();
        for (File f : files) {
            if (f.getName().equals("submissions")) {
                File SbFile = new File(selectedFile.getAbsolutePath() + "\\submissions\\");
                for (File s : SbFile.listFiles()) {
                    Sbs.add(s);
                    if(s.getAbsolutePath().endsWith(".zip")){
                    studs.add(s.getName());
                    }
                }

            }
        }
        if(studs.isEmpty()){
            work3.Unzip.setDisable(true);
        }
        else{
            work3.Unzip.setDisable(true);
        }
        work3.getSZips().setItems(studs);
    }
    public void updateStep4Table(File selectedFile){
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step4Workspace work4 = work.work4;
        String title = selectedFile.getName();
        app.getGUI().getWindow().setTitle("Code Check - " + title);
        File[] files = selectedFile.listFiles();
        ObservableList<File> Sbs = FXCollections.observableArrayList();
        ObservableList<String> studs = FXCollections.observableArrayList();
        for (File f : files) {
            if (f.getName().equals("projects")) {
                File SbFile = new File(selectedFile.getAbsolutePath() + "\\projects\\");
                for (File s : SbFile.listFiles()) {
                    Sbs.add(s);
                    //if(s.getAbsolutePath().contains(".zip")){
                    studs.add(s.getName());
                    //}
                }

            }
        }
        if(studs.isEmpty()){
            work4.ExtractCode.setDisable(true);
        }
        else{
            work4.ExtractCode.setDisable(true);
        }
        work4.getzipfiles().setItems(studs);
    }
    public void updateStep5Table(File selectedFile){
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        String title = selectedFile.getName();
        app.getGUI().getWindow().setTitle("Code Check - " + title);
        File[] files = selectedFile.listFiles();
        ObservableList<File> Sbs = FXCollections.observableArrayList();
        ObservableList<String> studs = FXCollections.observableArrayList();
        for (File f : files) {
            if (f.getName().equals("code")) {
                File SbFile = new File(selectedFile.getAbsolutePath() + "\\code\\");
                for (File s : SbFile.listFiles()) {
                    Sbs.add(s);
                    //if(s.getAbsolutePath().contains(".zip")){
                    studs.add(s.getName());
                    //}
                }

            }
        }
        if(studs.isEmpty()){
            work5.CodeCheckB.setDisable(true);
            work5.ViewResults.setDisable(true);
        }
        else{
            work5.CodeCheckB.setDisable(true);
            work5.ViewResults.setDisable(true);
        }
        work5.getSWork().setItems(studs);
    }
}

