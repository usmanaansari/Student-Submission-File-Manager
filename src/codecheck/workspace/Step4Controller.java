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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step4Controller {
    CodeCheckApp app;

    public Step4Controller(CodeCheckApp initApp) {
        app = initApp;
    }

    public void handleRefresh() throws IOException {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step4Workspace work4 = work.work4;
        File theFile = new File(PATH_WORK + (app.getGUI().getWindow().getTitle().substring(13)));
        if (theFile.isDirectory()) {
            File[] files = theFile.listFiles();
            ObservableList<String> sbs = FXCollections.observableArrayList();
            for (File f : files) {
                if (f.getName().equals("projects")) {
                    File sbFile = new File(theFile.getAbsolutePath() + "\\projects\\");
                    for (File s : sbFile.listFiles()) {
                        if(s.getName().contains(".txt")){
                            
                        }
                        else{
                        sbs.add(s.getName());
                        }
                    }
                }
            }
            work4.getzipfiles().setItems(sbs);
        }

    }

    public void handleSelect() {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step4Workspace work4 = work.work4;
        ListView zipFiles = work4.zipFiles;
        zipFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> selectedItems = zipFiles.getSelectionModel().getSelectedItems();

        if (selectedItems.size() > 1) {
            work4.View.setDisable(true);
            work4.Remove.setDisable(true);
            work4.ExtractCode.setDisable(false);

        } else {
            work4.View.setDisable(false);
            work4.Remove.setDisable(false);
            //work4.ExtractCode.setDisable(true);
        }
        if(selectedItems.size() == 1){
             work4.ExtractCode.setDisable(false);
        }

    }

    public void handleRemove() throws IOException {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step4Workspace work4 = work.work4;
        ListView zipFiles = work4.zipFiles;
        zipFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        String selectedItem = (String) zipFiles.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\projects\\";
        //ystem.out.print(path);
        //File file = new File(path);

        File f = new File(path + selectedItem);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Folder contents");
        alert.setHeaderText("Are you sure you want to delete this item? " );
        alert.setContentText(selectedItem);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(f.isDirectory()){
            delete(f);
                }
            handleRefresh();
        }
    }

    public void handleView() throws ZipException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step4Workspace work4 = work.work4;
        ListView zipFiles = work4.zipFiles;
        zipFiles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String selectedItem = (String) zipFiles.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\projects\\";
       
        File f = new File(path + selectedItem);

        
        if(selectedItem.endsWith(".txt")){
        Alert nope = new Alert(Alert.AlertType.ERROR);
        nope.setTitle(props.getProperty(STEP1_VIEWT));
        nope.setHeaderText("Cannot View Contents of this File Type" + selectedItem );
        nope.showAndWait();
        }
        else{
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
       

    }
    public void handleUnzip() throws ZipException{
    CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
    Step4Workspace work4 = work.work4;
    ListView zipFiles = work4.zipFiles;
    
    zipFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
    ObservableList<String> selectedItems = zipFiles.getSelectionModel().getSelectedItems();
    String title = app.getGUI().getWindow().getTitle().substring(13);
    File subDirectory = new File(PATH_WORK + title + "\\submissions\\");
    for(String s : selectedItems){
        ZipFile z = new ZipFile(subDirectory.getAbsolutePath() + "\\" + s);
        File directory = new File(PATH_WORK + title + "\\projects\\" + s.split("\\.")[0]);
        z.extractAll(directory.getAbsolutePath());
    }
    
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
        
}
