/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.EXTRACT_BUTTON;
import static codecheck.CodeCheckProps.PROG1_LABEL;
import static codecheck.CodeCheckProps.REFRESH_BUTTON;
import static codecheck.CodeCheckProps.REMOVE_BUTTON;
import static codecheck.CodeCheckProps.STEP1_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP1_LABEL;
import static codecheck.CodeCheckProps.VIEW_BUTTON;
import codecheck.data.CodeCheckData;
import static codecheck.style.CodeCheckStyle.CLASS_BOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX_BUTTONS;
import static codecheck.style.CodeCheckStyle.CLASS_PROMPT_LABEL;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
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
   //Step1Controller controller;
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
   
   
   
   
   
   public Step1Workspace(CodeCheckApp initApp){
       app = initApp;
       
       
       initLayout();
       
       initControllers();
   
       initStyle();
   
   }
   
    private void initLayout(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    Step1Label = new Label(props.getProperty(STEP1_LABEL));
    Step1Desc = new Label(props.getProperty(STEP1_DESC_LABEL));
    ProgLabel = new Label(props.getProperty(PROG1_LABEL));
    
    Extract = new Button(props.getProperty(EXTRACT_BUTTON));
    Remove = new Button(props.getProperty(REMOVE_BUTTON));
    Refresh = new Button(props.getProperty(REFRESH_BUTTON));
    View = new Button(props.getProperty(VIEW_BUTTON));
    Extract = new Button(props.getProperty(EXTRACT_BUTTON));
    buttons = new HBox();
    progBox = new HBox();
    BBSubs = new ListView();
    OutputWindow = new TextArea();
    MainBox = new HBox();
    LeftBox = new VBox();
    RightBox = new VBox();
    extProg = new ProgressBar();
    progInd = new ProgressIndicator();
    progInd.setProgress(50);
    
    extProg.setMinSize(450, 15);
    extProg.setPadding(new Insets(25, 0, 0, 0));
    
    
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    BBSubs.setMinSize(900, 600);
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(10);
    
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, extProg);
    LeftBox.getChildren().addAll(Step1Label, Step1Desc, BBSubs, buttons);
    RightBox.getChildren().addAll(progBox, Extract, OutputWindow);
    MainBox.getChildren().addAll(LeftBox, RightBox);
    MainBox.setSpacing(50);
    }
    private void initControllers(){
        //controller = new Step1Controller(app);
    }
    private void initStyle(){
        Refresh.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        View.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Remove.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Extract.getStyleClass().add(CLASS_FILE_BUTTON);
        ProgLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step1Label.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step1Desc.getStyleClass().add(CLASS_PROMPT_LABEL);
        MainBox.getStyleClass().add(CLASS_BOX);
        buttons.getStyleClass().add(CLASS_BUTTONBOX);
        
    }
    public HBox getStep1(){
        return MainBox;
    }
}

    