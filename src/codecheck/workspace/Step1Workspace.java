/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.EXTRACT_BUTTON;
import static codecheck.CodeCheckProps.PROG1_LABEL;
import static codecheck.CodeCheckProps.PROGP_LABEL;
import static codecheck.CodeCheckProps.REFRESH_BUTTON;
import static codecheck.CodeCheckProps.REMOVE_BUTTON;
import static codecheck.CodeCheckProps.STEP1_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP1_LABEL;
import static codecheck.CodeCheckProps.STEP1_TABLE;
import static codecheck.CodeCheckProps.VIEW_BUTTON;
import codecheck.data.CodeCheckData;
import static codecheck.style.CodeCheckStyle.CLASS_BOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX_BUTTONS;
import static codecheck.style.CodeCheckStyle.CLASS_PROMPT_LABEL;
import static djf.settings.AppStartupConstants.PATH_WORK;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.progress.ProgressMonitor;
import org.apache.commons.io.filefilter.IOFileFilter;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step1Workspace {
   CodeCheckApp app;
   Step1Controller controller;
   Step2Controller controller2;
   Step3Controller controller3;
   CodeCheckData data;
   
   
   Label Step1Label;
   Label Step1Desc;
   Label ProgLabel;
   Label ProgPercentLabel;
   Button Extract;
   Button Remove;
   Button Refresh;
   Button View;
   HBox buttons;
   HBox progBox;
   ListView BBSubs;
   TextArea OutputWindow;
   HBox MainBox;
   VBox LeftBox;
   VBox RightBox;
   ProgressBar extProg;
   ProgressIndicator progInd;
   Label tableLabel;
   //ObservableList<String> blacks;
   File workFile;
   ReentrantLock progressLock;
   ObservableList<String> names;
   ObservableList<String> bbs;
    int numTasks = 0;
   public Step1Workspace(CodeCheckApp initApp){
       app = initApp;
       controller = new Step1Controller(app);
       controller2 = new Step2Controller(app);
       controller3 = new Step3Controller(app);
       initLayout();
       
       initControllers();
   
       initStyle();
   
   }
   
    private void initLayout(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    CodeCheckData data = (CodeCheckData) app.getDataComponent();
    Step1Label = new Label(props.getProperty(STEP1_LABEL));
    Step1Desc = new Label(props.getProperty(STEP1_DESC_LABEL));
    ProgLabel = new Label(props.getProperty(PROG1_LABEL));
    tableLabel = new Label(props.getProperty(STEP1_TABLE));
    Extract = new Button(props.getProperty(EXTRACT_BUTTON));
    Remove = new Button(props.getProperty(REMOVE_BUTTON));
    Refresh = new Button(props.getProperty(REFRESH_BUTTON));
    View = new Button(props.getProperty(VIEW_BUTTON));
    Extract = new Button(props.getProperty(EXTRACT_BUTTON));
    buttons = new HBox();
    progBox = new HBox();
    BBSubs = new ListView();
    BBSubs.setMinSize(900, 600);
    OutputWindow = new TextArea();
    MainBox = new HBox();
    LeftBox = new VBox();
    RightBox = new VBox();
    extProg = new ProgressBar(0);
    progInd = new ProgressIndicator();
    //extProg.setProgress(progInd.getProgress());
    ProgPercentLabel = new Label();
    extProg.setMinSize(450, 15);
    extProg.setPadding(new Insets(25, 0, 0, 0));
    progressLock = new ReentrantLock();
    OutputWindow = new TextArea();
    
    bbs = FXCollections.observableArrayList();
    
    
    //File newa = new File(PATH_WORK + "\\blackboard\\");
    //bbs.setAll(data.getBBS(newa));//
    //System.out.print(bbs.toString());
    //System.out.println(data.getCodeChecks().toString());
    //BBSubs.setItems(bbs);
    //bbs = data.getBlacks();
    //BBSubs.setItems(data.getBlacks());
    //bbs = data.getBlacks();
    //BBSubs.setItems(data.getBlacks());
    
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.setDisable(true);
    View.setDisable(true);
    Extract.setDisable(true);
    OutputWindow.setEditable(false);
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(45);
    Extract.setDisable(true);
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, extProg, ProgPercentLabel);
    LeftBox.getChildren().addAll(Step1Label, Step1Desc, tableLabel, BBSubs, buttons);
    RightBox.getChildren().addAll(progBox, Extract, OutputWindow);
    MainBox.getChildren().addAll(LeftBox, RightBox);
    MainBox.setSpacing(50);
    }
    private void initControllers(){
        Refresh.setOnAction(e -> {
            try {
                controller.handleRefresh();
            } catch (IOException ex) {
                Logger.getLogger(Step1Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        BBSubs.getSelectionModel().selectedItemProperty().addListener(e->
        {
                controller.handleSelect();
                Extract.setDisable(false);
                View.setDisable(false);
                Remove.setDisable(false);
        });
//        BBSubs.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {  
//            @Override
//            public ListCell<String> call(ListView<String> param) {
//               final ListCell<String> cell = new ListCell<>();
//               cell.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                   @Override
//                   public void handle(MouseEvent event) {
//                       final int index = cell.getIndex();
//                       if (index >= 0 && index < BBSubs.getItems().size() && BBSubs.getSelectionModel().isSelected(index)  ) {
//                        BBSubs.getSelectionModel().clearSelection();
//                        event.consume();  
//                    }  
//                   }
//               });
//               return cell;
//            }
//    });
        BBSubs.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                BBSubs.requestFocus();
                if (! cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (BBSubs.getSelectionModel().getSelectedIndices().contains(index)) {
                        BBSubs.getSelectionModel().clearSelection(index);
                        BBSubs.getFocusModel().focus(-1);
                        View.setDisable(false);
                        Remove.setDisable(false);
                        Extract.setDisable(false);
                        if(BBSubs.getSelectionModel().getSelectedItems().size() > 1){
                        View.setDisable(true);
                        Remove.setDisable(true);
                        //Extract.setDisable(true);
                        }
                        if(BBSubs.getSelectionModel().getSelectedItems().size() ==0){
                            View.setDisable(true);
                            Remove.setDisable(true);
                            Extract.setDisable(true);
                        }
                        
                    }
                    else {
                        BBSubs.getSelectionModel().select(index);
                        if(BBSubs.getSelectionModel().getSelectedItems().size() > 1){
                        View.setDisable(true);
                        Remove.setDisable(true);
                        }
                        
                    }
                    event.consume();
                }
            });
            return cell ;
        });
        Remove.setOnAction(e -> {
            try {
                controller.handleRemove();
            } catch (IOException ex) {
                Logger.getLogger(Step1Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        View.setOnAction(e->{
            try {
                controller.handleView();
            } catch (ZipException ex) {
                Logger.getLogger(Step1Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Extract.setOnAction((ActionEvent e) ->{ 
            OutputWindow.clear();
            BBSubs.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            ObservableList<String> selectedItem = BBSubs.getSelectionModel().getSelectedItems();
            
            ArrayList<ZipFile> zips = new ArrayList<>();
            
            String title = app.getGUI().getWindow().getTitle().substring(13);
            ArrayList<String> success = new ArrayList<>();
            ArrayList<String> fail = new ArrayList<>();
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    
                    for (int i = 0; i < selectedItem.size(); i++) {
                        try {
                            ZipFile newZip = new ZipFile(PATH_WORK + title + "\\blackboard\\" + selectedItem.get(i));
                            if(newZip.isValidZipFile()){
                            List fileHeaderList = newZip.getFileHeaders(); 
                            zips.add(newZip);
                            
                            //OutputWindow.appendText(zips.get(i).getFile().getName());
                            
                            for(int p = 0; p < fileHeaderList.size(); p ++){
                                FileHeader fileHeader = (FileHeader) fileHeaderList.get(p);
                                if(fileHeader.getFileName().endsWith(".rar")){
                                    fail.add(fileHeader.getFileName() + " failed to extract \n");
                                    //fileHeaderList.remove(fileHeader.getFileName());
                                }
                                else{
                                    success.add(fileHeader.getFileName() + " was successfully extracted! \n");
                                }
                            }
                            }
                        } catch (ZipException ex) {
                            Logger.getLogger(Step1Workspace.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                    }
                    
                    
                    File subFolder = new File(PATH_WORK + title + "\\submissions\\");
                    for (int x = 0; x < zips.size(); x++) {
                        
                        zips.get(x).extractAll(subFolder.getAbsolutePath());    
                        
                        File[] subs = subFolder.listFiles();
                
                        updateProgress(x + 1, zips.size());
                        Thread.sleep(5);
                    }
                    for(int d = 0; d < subFolder.listFiles().length; d++){
                        if(subFolder.listFiles()[d].getName().endsWith(".rar")){
                            subFolder.listFiles()[d].delete();
                        }
                    }
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            BBSubs.getSelectionModel().clearSelection();
                            try {
                                controller2.handleRefresh();
                                controller3.handleRefresh();
                                for(String s: success){
                                    OutputWindow.appendText(s);
                                }
                                for(String i : fail){
                                    OutputWindow.appendText("\n" + i);
                                }
                                
                            } catch (IOException ex) {
                                Logger.getLogger(Step1Workspace.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        
                    });
                    
                    return null;
                }
                
            };
            Thread thread = new Thread(task);
            extProg.progressProperty().bind(task.progressProperty());
            
            thread.start();
        });
                
    }
    private void initStyle(){
        Refresh.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        View.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Remove.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Extract.getStyleClass().add(CLASS_FILE_BUTTON);
        ProgLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ProgPercentLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step1Label.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step1Desc.getStyleClass().add(CLASS_PROMPT_LABEL);
        MainBox.getStyleClass().add(CLASS_BOX);
        buttons.getStyleClass().add(CLASS_BUTTONBOX);
        tableLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        
    }
    public HBox getStep1(){
        return MainBox;
    }
    public ListView getBBSubs(){
        return BBSubs;
    }
}

    