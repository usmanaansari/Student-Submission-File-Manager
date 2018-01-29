/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.STEP1_VIEWT;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.progress.ProgressMonitor;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step5Controller {
    
    CodeCheckApp app;
    
    public Step5Controller(CodeCheckApp initApp){
        app = initApp;
    }
    
    
    public void handleRefresh() throws IOException{
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        File theFile = new File(PATH_WORK + (app.getGUI().getWindow().getTitle().substring(13)));
        if (theFile.isDirectory()) {
            File[] files = theFile.listFiles();
            ObservableList<String> bbs = FXCollections.observableArrayList();
            for (File f : files) {
                if (f.getName().equals("code")) {
                    File bbFile = new File(theFile.getAbsolutePath() + "\\code\\");
                    for (File b : bbFile.listFiles()) {
                        bbs.add(b.getName());
                    }

                }
            }
            work5.getSWork().setItems(bbs);
        }

    }
    public void handleBrowser(){
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        
        ScrollPane scroll =  new ScrollPane();
        scroll.setContent(browser);
        
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>(){
            @Override
            public void changed(ObservableValue observable, State oldValue, State newValue) {
               if(newValue == Worker.State.SUCCEEDED){
                   stage.setTitle(webEngine.getLocation());
                   
               }
            }
            
        });
        webEngine.load("https://bing.com");
        scene.setRoot(scroll);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
    }
    public void handleSelect() {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        ListView SWork = work5.SWork;
        SWork.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> selectedItems = SWork.getSelectionModel().getSelectedItems();
        
        if (selectedItems.size() > 1) {
            work5.View.setDisable(true);
            work5.Remove.setDisable(true);
            work5.CodeCheckB.setDisable(false);
            work5.ViewResults.setDisable(false);

        } else {
            work5.View.setDisable(false);
            work5.Remove.setDisable(false);
        }
        if (selectedItems.size() == 1) {
            work5.CodeCheckB.setDisable(false);
            work5.ViewResults.setDisable(false);

        }

    }
    
    public void handleRemove() throws IOException {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        ListView SWork = work5.SWork;
        SWork.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String selectedItem = (String) SWork.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\code\\";

        File f = new File(path + selectedItem);
     
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Source folder contents");
        alert.setHeaderText("Are you sure you want to delete this item? " );
        alert.setContentText(selectedItem);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            delete(f);
            handleRefresh();
        }
        
    }
    public void handleView(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        ListView SWork = work5.SWork;
        
        SWork.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String selectedItem = (String) SWork.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\code\\";
       
        File f = new File(path + selectedItem);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(props.getProperty(STEP1_VIEWT));
        alert.setHeaderText("Here are the contents of " + selectedItem);
        TextArea text = new TextArea();
        File directory = new File(path + selectedItem);
        File[] fileHeaderList = directory.listFiles();
       
        TreeItem<String> root = new TreeItem<>(selectedItem);
        root.setExpanded(true);
        for (int i = 0 ; i< fileHeaderList.length; i++) {
            File fileHeader = fileHeaderList[i];
            TreeItem<String> item = new TreeItem<>(fileHeader.getName());
            if (fileHeader.getName().contains(".txt")) {
                
            } else {
                root.getChildren().add(item);
            }
        }
        
        TreeView<String> tree = new TreeView<>(root);
        
        alert.getDialogPane().setExpandableContent(tree);
        alert.showAndWait();
        
       

    }
    public static void delete(File file)
            throws IOException {

        if (file.isDirectory()) {

            //directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : "
                        + file.getAbsolutePath());

            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    delete(fileDelete);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : "
                            + file.getAbsolutePath());
                }
            }

        } else {
            //if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }
//        public void handleRunCode() {
//            CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
//            Step5Workspace work5 = work.work5;
//            Step2Workspace work2 = work.work2;
//            ListView SWork = work5.SWork;
//            ProgressBar prog = work5.checkProg;
//            ProgressIndicator progI = work5.progInd;
//            TextFlow text = work5.OutputWindow;
//        
//            
//            
//            Hyperlink google = new Hyperlink();
//            text.setUserData( google);
//        
//            
//        }
        
}
