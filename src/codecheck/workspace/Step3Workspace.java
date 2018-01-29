/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.PROG2_LABEL;
import static codecheck.CodeCheckProps.PROG3_LABEL;
import static codecheck.CodeCheckProps.PROGP_LABEL;
import static codecheck.CodeCheckProps.REFRESH_BUTTON;
import static codecheck.CodeCheckProps.REMOVE_BUTTON;
import static codecheck.CodeCheckProps.RENAME_BUTTON;
import static codecheck.CodeCheckProps.STEP2_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP2_LABEL;
import static codecheck.CodeCheckProps.STEP3_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP3_LABEL;
import static codecheck.CodeCheckProps.STEP3_TABLE;
import static codecheck.CodeCheckProps.UNZIP_BUTTON;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step3Workspace {
   CodeCheckApp app;
   //Step1Controller controller;
   CodeCheckData data;
   Step3Controller controller;
   Step4Controller controller4;
   Label Step3Label;
   Label Step3Desc;
   Label ProgLabel;
   Label ProgPercentLabel;
   Button Unzip;
   Button Remove;
   Button Refresh;
   Button View;
   HBox buttons;
   HBox progBox;
   ListView SZips;
   TextArea OutputWindow;
   HBox MainBox;
   VBox LeftBox;
   VBox RightBox;
   ProgressBar unzipProg;
   ProgressIndicator progInd;
   Label tableLabel;
   
   
   
   
   public Step3Workspace(CodeCheckApp initApp){
       app = initApp;
       controller = new Step3Controller(app);
       controller4 = new Step4Controller(app);
       initLayout();
       
       initControllers();
   
       initStyle();
   
   }
   
    private void initLayout(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    Step3Label = new Label(props.getProperty(STEP3_LABEL));
    Step3Desc = new Label(props.getProperty(STEP3_DESC_LABEL));
    ProgLabel = new Label(props.getProperty(PROG3_LABEL));
    tableLabel = new Label(props.getProperty(STEP3_TABLE));
    Unzip = new Button(props.getProperty(UNZIP_BUTTON));
    Remove = new Button(props.getProperty(REMOVE_BUTTON));
    Refresh = new Button(props.getProperty(REFRESH_BUTTON));
    View = new Button(props.getProperty(VIEW_BUTTON));
    buttons = new HBox();
    progBox = new HBox();
    SZips = new ListView();
    OutputWindow = new TextArea();
    MainBox = new HBox();
    LeftBox = new VBox();
    RightBox = new VBox();
    unzipProg = new ProgressBar(0);
    progInd = new ProgressIndicator();
    //unzipProg.setProgress(progInd.getProgress());
    progInd.setProgress(50);
    ProgPercentLabel = new Label(props.getProperty(PROGP_LABEL));
    unzipProg.setMinSize(450, 15);
    unzipProg.setPadding(new Insets(25, 0, 0, 0));
    Remove.setDisable(true);
    View.setDisable(true);
    Unzip.setDisable(true);
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    SZips.setMinSize(900, 600);
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(45);
    OutputWindow.setEditable(false);
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, unzipProg);
    LeftBox.getChildren().addAll(Step3Label, Step3Desc,tableLabel, SZips, buttons);
    RightBox.getChildren().addAll(progBox, Unzip, OutputWindow);
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
        SZips.getSelectionModel().selectedItemProperty().addListener(e->
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
        SZips.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            cell.textProperty().bind(cell.itemProperty());
            cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                SZips.requestFocus();
                if (! cell.isEmpty()) {
                    int index = cell.getIndex();
                    if (SZips.getSelectionModel().getSelectedIndices().contains(index)) {
                        SZips.getSelectionModel().clearSelection(index);
                        SZips.getFocusModel().focus(-1);
                        View.setDisable(false);
                        Remove.setDisable(false);
                        if(SZips.getSelectionModel().getSelectedItems().size() > 1){
                        View.setDisable(true);
                        Remove.setDisable(true);
                        }
                        if(SZips.getSelectionModel().getSelectedItems().size() == 0){
                            View.setDisable(true);
                            Remove.setDisable(true);
                            Unzip.setDisable(true);
                        } 
                        if(SZips.getSelectionModel().getSelectedItems().size() == 1){
                            Unzip.setDisable(false);
                        }
                    }
                    else {
                        SZips.getSelectionModel().select(index);
                        if(SZips.getSelectionModel().getSelectedItems().size() > 1){
                        View.setDisable(true);
                        Remove.setDisable(true);
                        }
                        
                    }
                    event.consume();
                }
            });
            return cell ;
        });
        Unzip.setOnAction(e ->{
//           try {
//               controller.handleUnzip();
//           } catch (ZipException ex) {
//               Logger.getLogger(Step3Workspace.class.getName()).log(Level.SEVERE, null, ex);
//           }
            SZips.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            ObservableList<String> selectedItems = SZips.getSelectionModel().getSelectedItems();
            String title = app.getGUI().getWindow().getTitle().substring(13);
            File subDirectory = new File(PATH_WORK + title + "\\submissions\\");
            //SZips.getSelectionModel().clearSelection();
            ArrayList<String> success = new ArrayList<>();
            ArrayList<String> fail = new ArrayList<>();
            Task<Void> task = new Task<Void>() {
           
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i < selectedItems.size(); i++) {
                        ZipFile z;
                        try {
                            
                            String s = selectedItems.get(i);
                            z = new ZipFile(subDirectory.getAbsolutePath() + "\\" + s);
                            File directory = new File(PATH_WORK + title + "\\projects\\" + s.split("\\.")[0] + "_work");
                            if(z.isValidZipFile()){
                            z.extractAll(directory.getAbsolutePath());
                            success.add(z.getFile().getName() + " was successfully extracted");
                            }
                        } catch (ZipException ex) {
                            Logger.getLogger(Step3Workspace.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        updateProgress(i+1, selectedItems.size());
                        Thread.sleep(5);
                        
                    }
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                           SZips.getSelectionModel().clearSelection();
                            try {
                                controller4.handleRefresh();
                                for(String s : success){
                                    OutputWindow.appendText(s + "\n");
                                }
                                
                            } catch (IOException ex) {
                                Logger.getLogger(Step3Workspace.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                    });
                    return null;
                    
                }
                
            };
            Thread thread = new Thread(task);
            unzipProg.progressProperty().bind(task.progressProperty());
            thread.start();
            
        });
    }
    private void initStyle(){
        Refresh.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        View.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Remove.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Unzip.getStyleClass().add(CLASS_FILE_BUTTON);
        ProgLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step3Label.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step3Desc.getStyleClass().add(CLASS_PROMPT_LABEL);
        MainBox.getStyleClass().add(CLASS_BOX);
        buttons.getStyleClass().add(CLASS_BUTTONBOX);
        tableLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ProgPercentLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
    }
    
   
    public HBox getStep3(){
        return MainBox;
    }
    public ListView getSZips(){
        return SZips;
    }
}
