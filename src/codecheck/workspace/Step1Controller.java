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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.progress.ProgressMonitor;
import properties_manager.PropertiesManager;

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
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step1Workspace work1 = work.work1;
        File theFile = new File(PATH_WORK + (app.getGUI().getWindow().getTitle().substring(13)));
        if (theFile.isDirectory()) {
            File[] files = theFile.listFiles();
            ObservableList<String> bbs = FXCollections.observableArrayList();
            for (File f : files) {
                if (f.getName().equals("blackboard")) {
                    File bbFile = new File(theFile.getAbsolutePath() + "\\blackboard\\");
                    for (File b : bbFile.listFiles()) {
                        bbs.add(b.getName());
                    }

                }
            }
            work1.getBBSubs().setItems(bbs);
        }

    }
    
    public void handleSelect() {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step1Workspace work1 = work.work1;
        ListView BBSubs = work1.BBSubs;
        BBSubs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> selectedItems = BBSubs.getSelectionModel().getSelectedItems();
        
        if (selectedItems.size() > 1) {
            work1.View.setDisable(true);
            work1.Remove.setDisable(true);

        } else {
            work1.View.setDisable(false);
            work1.Remove.setDisable(false);
        }

    }
    
    public void handleRemove() throws IOException {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step1Workspace work1 = work.work1;
        ListView BBSubs = work1.BBSubs;
        BBSubs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String selectedItem = (String) BBSubs.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\blackboard\\";
        //ystem.out.print(path);
        //File file = new File(path);

        File f = new File(path + selectedItem.substring(16));
        //System.out.println(selectedItem);
        f.delete();
        handleRefresh();
        
    }
    public void handleView() throws ZipException{
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step1Workspace work1 = work.work1;
        ListView BBSubs = work1.BBSubs;
        BBSubs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        String selectedItem = (String) BBSubs.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\blackboard\\";
        File f = new File(path + selectedItem);
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(props.getProperty(STEP1_VIEWT));
        alert.setHeaderText("Here are the contents of " + selectedItem);
        TextArea text = new TextArea();
        
        ZipFile z = new ZipFile(path + selectedItem);
        List fileHeaderList = z.getFileHeaders();
        TreeItem<String> root = new TreeItem<>(selectedItem);
        root.setExpanded(true);
        for (int i = 0; i < fileHeaderList.size(); i++) {
            FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
            TreeItem<String> item = new TreeItem<>(fileHeader.getFileName());
            if (fileHeader.getFileName().contains(".txt")) {

            } else {
                root.getChildren().add(item);
            }
        }
        TreeView<String> tree = new TreeView<>(root);

        alert.getDialogPane().setExpandableContent(tree);
        alert.showAndWait();
        
    }
        public void handleExtract() throws ZipException{
            CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
            Step1Workspace work1 = work.work1;
            ListView BBSubs = work1.BBSubs;
            ProgressBar prog = work1.extProg;
            ProgressIndicator progI = work1.progInd;
            BBSubs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            
            ObservableList<String> selectedItem = BBSubs.getSelectionModel().getSelectedItems();
            String title = app.getGUI().getWindow().getTitle().substring(13);            
            for(String s: selectedItem){
                //System.out.println(PATH_WORK +  title +"\\blackboard\\" + s);
                ZipFile z = new ZipFile(PATH_WORK +  title +"\\blackboard\\" + s);
                System.out.print(z.getFile().getName());
                z.extractAll(PATH_WORK + title + "\\submissions\\" );
                
//                ProgressMonitor y = z.getProgressMonitor();
//                progI.setProgress(y.getWorkCompleted());
//                prog.setProgress(progI.getProgress());
                
            }
  
        }
}
