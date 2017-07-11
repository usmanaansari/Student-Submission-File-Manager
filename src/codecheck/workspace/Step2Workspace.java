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
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step2Workspace {
   CodeCheckApp app;
   //Step1Controller controller;
   CodeCheckData data;
   
   
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
    extProg = new ProgressBar();
    progInd = new ProgressIndicator(.47);
    extProg.setProgress(progInd.getProgress());
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
       // controller = new Step1Controller(app);
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
}
