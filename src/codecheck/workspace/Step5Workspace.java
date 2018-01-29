/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.CODECHECK_BUTTON;
import static codecheck.CodeCheckProps.PROG3_LABEL;
import static codecheck.CodeCheckProps.PROG5_LABEL;
import static codecheck.CodeCheckProps.PROGP_LABEL;
import static codecheck.CodeCheckProps.REFRESH_BUTTON;
import static codecheck.CodeCheckProps.REMOVE_BUTTON;
import static codecheck.CodeCheckProps.STEP3_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP3_LABEL;
import static codecheck.CodeCheckProps.STEP5_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP5_LABEL;
import static codecheck.CodeCheckProps.STEP5_TABLE;
import static codecheck.CodeCheckProps.UNZIP_BUTTON;
import static codecheck.CodeCheckProps.VIEWRESULTS_BUTTON;
import static codecheck.CodeCheckProps.VIEW_BUTTON;
import codecheck.data.CodeCheckData;
import static codecheck.style.CodeCheckStyle.CLASS_BOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX_BUTTONS;
import static codecheck.style.CodeCheckStyle.CLASS_PROMPT_LABEL;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.lingala.zip4j.exception.ZipException;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step5Workspace {
    CodeCheckApp app;
   //Step1Controller controller;
   CodeCheckData data;
   Step5Controller controller;
   
   Label Step5Label;
   Label Step5Desc;
   Label ProgLabel;
   Label ProgPercentLabel;
   Button CodeCheckB;
   Button ViewResults;
   Button Remove;
   Button Refresh;
   Button View;
   HBox buttons;
   HBox progBox;
   ListView SWork;
   TextArea OutputWindow;
   HBox MainBox;
   VBox LeftBox;
   VBox RightBox;
   ProgressBar checkProg;
   ProgressIndicator progInd;
   HBox rightBBox;
   Label tableLabel;
   WebView web;
   WebEngine brow;
   Hyperlink googleLink;
   int numTasks = 0;
   ReentrantLock progressLock;
   public Step5Workspace(CodeCheckApp initApp){
       app = initApp;
       controller = new Step5Controller(app);
       
       initLayout();
       
       initControllers();
   
       initStyle();
   
   }
   
    private void initLayout(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    Step5Label = new Label(props.getProperty(STEP5_LABEL));
    Step5Desc = new Label(props.getProperty(STEP5_DESC_LABEL));
    ProgLabel = new Label(props.getProperty(PROG5_LABEL));
    tableLabel = new Label(props.getProperty(STEP5_TABLE));
    ProgPercentLabel = new Label(props.getProperty(PROGP_LABEL));
    CodeCheckB = new Button(props.getProperty(CODECHECK_BUTTON));
    ViewResults = new Button(props.getProperty(VIEWRESULTS_BUTTON));
    Remove = new Button(props.getProperty(REMOVE_BUTTON));
    Refresh = new Button(props.getProperty(REFRESH_BUTTON));
    View = new Button(props.getProperty(VIEW_BUTTON));
    buttons = new HBox();
    progBox = new HBox();
    SWork = new ListView();
    OutputWindow = new TextArea();
    MainBox = new HBox();
    LeftBox = new VBox();
    RightBox = new VBox();
    checkProg = new ProgressBar(0);
    progInd = new ProgressIndicator();
    //checkProg.setProgress(progInd.getProgress());
    rightBBox = new HBox();
    checkProg.setMinSize(450, 15);
    checkProg.setPadding(new Insets(25, 0, 0, 0));
    googleLink = new Hyperlink("https://bing.com");
    CodeCheckB.setDisable(true);
    ViewResults.setDisable(true);
    Remove.setDisable(true);
    View.setDisable(true);
    OutputWindow.setEditable(false);
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    SWork.setMinSize(900, 600);
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(45);
    rightBBox.getChildren().addAll(CodeCheckB, ViewResults);
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, checkProg);
    LeftBox.getChildren().addAll(Step5Label, Step5Desc,tableLabel, SWork, buttons);
    RightBox.getChildren().addAll(progBox, rightBBox, OutputWindow);
    MainBox.getChildren().addAll(LeftBox, RightBox);
    MainBox.setSpacing(50);
     progressLock = new ReentrantLock();
    }
    private void initControllers() {
       CodeCheckB.setOnAction(e ->{
           
           
           Task<Void> task = new Task<Void>() {
               int task = numTasks++;
                    double max = 1;
                    double perc;
               @Override
               protected Void call() throws Exception {
                   try {
                            progressLock.lock();
                        for (int i = 0; i < 2; i++) {
                            System.out.println(i);
                            perc = i/max;
                   Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                           OutputWindow.setText("Student Plagarism Results can be found at: \n" + googleLink.getText());
                           progInd.setProgress(perc);
                           checkProg.setProgress(perc);
                       }
                   });
                   
                   Thread.sleep(5);
                }}
                   finally {
			    // WHAT DO WE NEED TO DO HERE?
                            progressLock.unlock();
                            
                                }
                        return null;
               }
           };
           Thread thread = new Thread(task);
           thread.start();
           
       });
       Remove.setOnAction(e->{
           try {
               controller.handleRemove();
           } catch (IOException ex) {
               Logger.getLogger(Step5Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
       });
       View.setOnAction(e->{
           controller.handleView();
       });
       Refresh.setOnAction(e->{
           try {
               controller.handleRefresh();
           } catch (IOException ex) {
               Logger.getLogger(Step5Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
       });
       SWork.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                SWork.requestFocus();
                if (! cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (SWork.getSelectionModel().getSelectedIndices().contains(index)) {
                        SWork.getSelectionModel().clearSelection(index);
                        SWork.getFocusModel().focus(-1);
                        View.setDisable(false);
                        Remove.setDisable(false);
                        if(SWork.getSelectionModel().getSelectedItems().size() > 1){
                        View.setDisable(true);
                        Remove.setDisable(true);
                        CodeCheckB.setDisable(false);
                        ViewResults.setDisable(false);
                        }
                        if(SWork.getSelectionModel().getSelectedItems().size() == 0){
                            CodeCheckB.setDisable(true);
                            ViewResults.setDisable(true);
                        }
                        
                    }
                    else {
                        SWork.getSelectionModel().select(index);
                        if(SWork.getSelectionModel().getSelectedItems().size() > 1){
                        View.setDisable(true);
                        Remove.setDisable(true);
                        }
                        
                    }
                    event.consume();
                }
            });
            return cell ;
        });
       SWork.getSelectionModel().selectedItemProperty().addListener(e->
        {
                controller.handleSelect();
        });
       ViewResults.setOnAction(e->{
           controller.handleBrowser();
       });
    }
    private void initStyle(){
        Refresh.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        View.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Remove.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        CodeCheckB.getStyleClass().add(CLASS_FILE_BUTTON);
        ViewResults.getStyleClass().add(CLASS_FILE_BUTTON);
        ProgLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step5Label.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step5Desc.getStyleClass().add(CLASS_PROMPT_LABEL);
        MainBox.getStyleClass().add(CLASS_BOX);
        buttons.getStyleClass().add(CLASS_BUTTONBOX);
        tableLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ProgPercentLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        
    }
    public HBox getStep5(){
        return MainBox;
    }
    public ListView getSWork(){
        return SWork;
    }
    
}

