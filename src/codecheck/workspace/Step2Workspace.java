/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.EXTRACT_BUTTON;
import static codecheck.CodeCheckProps.PROG1_LABEL;
import static codecheck.CodeCheckProps.PROG2_LABEL;
import static codecheck.CodeCheckProps.PROGP_LABEL;
import static codecheck.CodeCheckProps.REFRESH_BUTTON;
import static codecheck.CodeCheckProps.REMOVE_BUTTON;
import static codecheck.CodeCheckProps.RENAME_BUTTON;
import static codecheck.CodeCheckProps.STEP1_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP1_LABEL;
import static codecheck.CodeCheckProps.STEP1_TABLE;
import static codecheck.CodeCheckProps.STEP2_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP2_LABEL;
import static codecheck.CodeCheckProps.STEP2_TABLE;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step2Workspace {
   CodeCheckApp app;
   //Step1Controller controller;
   CodeCheckData data;
   Step2Controller controller;
   
   Label Step2Label;
   Label Step2Desc;
   Label ProgLabel;
   Label ProgPercentLabel;
   Button Rename;
   Button Remove;
   Button Refresh;
   Button View;
   HBox buttons;
   HBox progBox;
   ListView SSubs;
   TextArea OutputWindow;
   HBox MainBox;
   VBox LeftBox;
   VBox RightBox;
   ProgressBar extProg;
   ProgressIndicator progInd;
   Label tableLabel;
   
   
   
   
   public Step2Workspace(CodeCheckApp initApp){
       app = initApp;
       controller = new Step2Controller(app);
       
       initLayout();
       
       initControllers();
   
       initStyle();
   
   }
   
    private void initLayout(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    Step2Label = new Label(props.getProperty(STEP2_LABEL));
    Step2Desc = new Label(props.getProperty(STEP2_DESC_LABEL));
    ProgLabel = new Label(props.getProperty(PROG2_LABEL));
    tableLabel = new Label(props.getProperty(STEP2_TABLE));
    Rename = new Button(props.getProperty(RENAME_BUTTON));
    Remove = new Button(props.getProperty(REMOVE_BUTTON));
    Refresh = new Button(props.getProperty(REFRESH_BUTTON));
    View = new Button(props.getProperty(VIEW_BUTTON));
    buttons = new HBox();
    progBox = new HBox();
    SSubs = new ListView();
    OutputWindow = new TextArea();
    MainBox = new HBox();
    LeftBox = new VBox();
    RightBox = new VBox();
    extProg = new ProgressBar(0);
    progInd = new ProgressIndicator();
    //extProg.setProgress(progInd.getProgress());
    ProgPercentLabel = new Label(props.getProperty(PROGP_LABEL));
    extProg.setMinSize(450, 15);
    extProg.setPadding(new Insets(25, 0, 0, 0));
    
    
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    SSubs.setMinSize(900, 600);
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(45);
    
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, extProg, ProgPercentLabel);
    LeftBox.getChildren().addAll(Step2Label, Step2Desc, tableLabel, SSubs, buttons);
    RightBox.getChildren().addAll(progBox, Rename, OutputWindow);
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
        SSubs.getSelectionModel().selectedItemProperty().addListener(e->
        {
                controller.handleSelect();
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
        Rename.setOnAction(e -> {
//           try {
//               controller.handleRename();
//           } catch (IOException ex) {
//               Logger.getLogger(Step2Workspace.class.getName()).log(Level.SEVERE, null, ex);
//           }
            
            ObservableList<String> netIDS = FXCollections.observableArrayList();
            String subPath = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\submissions\\";
            ObservableList<File> files = FXCollections.observableArrayList();
            String title = app.getGUI().getWindow().getTitle().substring(13);
            ObservableList<String> zips = FXCollections.observableArrayList();
            ObservableList<String> texts = FXCollections.observableArrayList();
           try {
               controller.handleRename();
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    File directory = new File(subPath);
//                    for (File listFile : directory.listFiles()) {
//                        files.add(listFile);
//                    }
                    ObservableList<String> tableData = SSubs.getItems();
                    for(String s: tableData){
                        if(s.endsWith(".zip")){
                            if(s.contains("_")){
                                String net = s.split("_")[1];
                                netIDS.add(net + ".zip");
                            }
                            else{
                                netIDS.add(s);
                            }
                        }
                        updateProgress(tableData.indexOf(s)+1, directory.listFiles().length);
                    }
//                    for(int i = 0; i< files.size(); i++){
//                        if(files.get(i).getName().endsWith(".zip")){
//                            if(files.get(i).getName().contains("_")){
//                                String name = files.get(i).getName().split("_")[1];
//                                File netID = new File(subPath + name + ".zip");
//                                if(netID.exists()){
//                                    
//                                }
//                                else{
//                                files.get(i).renameTo(netID);
//                                zips.add(netID.getName());
//                                }
//                            }
//                            else{
//                                String name = files.get(i).getName();
//                                File netID = new File(subPath + name);
//                                
//                                zips.add(netID.getName());
//                            }
//                        }
//                        else{
//                            
//                            texts.add(files.get(i).getName());
//                        }
//                    
//                        
//                    }



                     
                     Platform.runLater(new Runnable() {
                     @Override public void run() {
                         try {
                             controller.handleRefresh();
                         } catch (IOException ex) {
                             Logger.getLogger(Step2Workspace.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     }
                 });
                    Thread.sleep(5);
                    return null;
                }
                
            };
            Thread thread = new Thread(task);
            extProg.progressProperty().bind(task.progressProperty());
            thread.start();
           } catch (IOException ex) {
               Logger.getLogger(Step2Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           
            
            

        });
    }
    private void initStyle(){
        Refresh.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        View.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Remove.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Rename.getStyleClass().add(CLASS_FILE_BUTTON);
        ProgLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step2Label.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step2Desc.getStyleClass().add(CLASS_PROMPT_LABEL);
        MainBox.getStyleClass().add(CLASS_BOX);
        buttons.getStyleClass().add(CLASS_BUTTONBOX);
        tableLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ProgPercentLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        
    }
    public HBox getStep2(){
        return MainBox;
    }
    public ListView getSSubs(){
        return SSubs;
    }
}
