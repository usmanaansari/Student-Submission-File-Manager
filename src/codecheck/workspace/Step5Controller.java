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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.TextFlow;
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
                if (f.getName().equals("blackboard")) {
                    File bbFile = new File(theFile.getAbsolutePath() + "\\blackboard\\");
                    for (File b : bbFile.listFiles()) {
                        bbs.add(b.getName());
                    }

                }
            }
            work5.getSWork().setItems(bbs);
        }

    }
    
    public void handleSelect() {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        ListView SWork = work5.SWork;
        SWork.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> selectedItems = SWork.getSelectionModel().getSelectedItems();
        
//        if (selectedItems.size() > 1) {
//            work5.View.setDisable(true);
//            work5.Remove.setDisable(true);
//
//        } else {
//            work5.View.setDisable(false);
//            work5.Remove.setDisable(false);
//        }

    }
    
    public void handleRemove() throws IOException {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        ListView SWork = work5.SWork;
        SWork.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String selectedItem = (String) SWork.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\blackboard\\";

        File f = new File(path + selectedItem);
     
        f.delete();
        handleRefresh();
        
    }
    public void handleView() throws ZipException{
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step5Workspace work5 = work.work5;
        ListView SWork = work5.SWork;
        SWork.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        String selectedItem = (String) SWork.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\blackboard\\";
        File f = new File(path + selectedItem);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
