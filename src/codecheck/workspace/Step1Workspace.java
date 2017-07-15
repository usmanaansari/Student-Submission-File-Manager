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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step1Workspace {
   CodeCheckApp app;
   Step1Controller controller;
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
   
   ObservableList<String> names;
   ObservableList<String> bbs;
   public Step1Workspace(CodeCheckApp initApp){
       app = initApp;
       controller = new Step1Controller(app);
       
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
    extProg = new ProgressBar();
    progInd = new ProgressIndicator(.47);
    extProg.setProgress(progInd.getProgress());
    ProgPercentLabel = new Label(props.getProperty(PROGP_LABEL));
    extProg.setMinSize(450, 15);
    extProg.setPadding(new Insets(25, 0, 0, 0));
    
    
    
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
    
    
    
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(45);
    
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

    