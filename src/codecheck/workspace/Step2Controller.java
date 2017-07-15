/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;

import static codecheck.CodeCheckProps.STEP1_VIEWT;
import static djf.settings.AppStartupConstants.PATH_WORK;
import java.nio.file.Files;
import java.io.IOException;
import java.io.File;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import static org.apache.commons.io.FilenameUtils.getBaseName;
import static org.apache.commons.io.FilenameUtils.isExtension;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step2Controller {

    CodeCheckApp app;

    public Step2Controller(CodeCheckApp initApp) {
        app = initApp;
    }

    public void handleRefresh() throws IOException {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step2Workspace work2 = work.work2;
        File theFile = new File(PATH_WORK + (app.getGUI().getWindow().getTitle().substring(13)));
        if (theFile.isDirectory()) {
            File[] files = theFile.listFiles();
            ObservableList<String> sbs = FXCollections.observableArrayList();
            for (File f : files) {
                if (f.getName().equals("submissions")) {
                    File sbFile = new File(theFile.getAbsolutePath() + "\\submissions\\");
                    for (File s : sbFile.listFiles()) {
                        if(s.getAbsolutePath().endsWith(".txt")){
                            
                        }
                        else{
                        sbs.add(s.getName());
                        }
                    }

                }
            }
            work2.getSSubs().setItems(sbs);
        }

    }

    public void handleSelect() {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step2Workspace work2 = work.work2;
        ListView SSubs = work2.SSubs;
        SSubs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> selectedItems = SSubs.getSelectionModel().getSelectedItems();

        if (selectedItems.size() > 1) {
            work2.View.setDisable(true);
            work2.Remove.setDisable(true);

        } else {
            work2.View.setDisable(false);
            work2.Remove.setDisable(false);
        }

    }

    public void handleRemove() throws IOException {
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step2Workspace work2 = work.work2;
        ListView SSubs = work2.SSubs;
        SSubs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String selectedItem = (String) SSubs.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\submissions\\";
        //ystem.out.print(path);
        //File file = new File(path);

        File f = new File(path + selectedItem);

        delete(f);
        handleRefresh();
    }

    public void handleView() throws ZipException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step2Workspace work2 = work.work2;
        ListView SSubs = work2.SSubs;
        SSubs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        String selectedItem = (String) SSubs.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\submissions\\";
        File f = new File(path + selectedItem);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(props.getProperty(STEP1_VIEWT));
        alert.setHeaderText("Here are the contents of " + selectedItem);
        TextArea text = new TextArea();

        File directory = new File(path + selectedItem);
        String[] names = directory.list();
        TreeItem<String> root = new TreeItem<>(selectedItem);
        root.setExpanded(true);
        for (int i = 0; i < names.length; i++) {
            File file = new File(names[i]);
            TreeItem<String> item = new TreeItem<>(file.getName());
            if (file.getName().contains(".txt")) {

            } else {
                root.getChildren().add(item);
            }
        }
        TreeView<String> tree = new TreeView<>(root);

        alert.getDialogPane().setExpandableContent(tree);
        alert.showAndWait();

    }

    public void handleRename() throws ZipException {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        Step2Workspace work2 = work.work2;
        ListView SSubs = work2.SSubs;
        SSubs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        String selectedItem = (String) SSubs.getSelectionModel().getSelectedItem();
        String path = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\submissions\\";
        File directory = new File(path + selectedItem);
        File[] names = directory.listFiles();
        ObservableList<String> netIDS = FXCollections.observableArrayList();
        
        for (File f : names) {
            if(f.getName().contains(".txt")){}
            else{
                netIDS.add(getBaseName(f.getName()).split("_")[1] + ".zip");
                ZipFile newFile = new ZipFile(path  +selectedItem + "\\" + f.getName().split("_")[1] + "\\");
                
                //f.renameTo(newFile);
                //System.out.println(newFile.getAbsolutePath());
                //System.out.println(f.getName().split("_")[1]);
                //System.out.println(getBaseName(f.getName()).split("_")[1]);
                //.renameTo(newFile);

            }
        }
        //System.out.print(netIDS.toString());
        for(String s : netIDS){
            File yo = new File(path + selectedItem + s);
                  //f.renameTo(yo);
                  System.out.print(yo.getPath());
                
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
