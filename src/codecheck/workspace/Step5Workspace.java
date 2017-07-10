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
import static codecheck.CodeCheckProps.REFRESH_BUTTON;
import static codecheck.CodeCheckProps.REMOVE_BUTTON;
import static codecheck.CodeCheckProps.STEP3_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP3_LABEL;
import static codecheck.CodeCheckProps.STEP5_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP5_LABEL;
import static codecheck.CodeCheckProps.UNZIP_BUTTON;
import static codecheck.CodeCheckProps.VIEWRESULTS_BUTTON;
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
public class Step5Workspace {
    CodeCheckApp app;
   //Step1Controller controller;
   CodeCheckData data;
   
   
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
   
   
   
   
   public Step5Workspace(CodeCheckApp initApp){
       app = initApp;
       
       
       initLayout();
       
       initControllers();
   
       initStyle();
   
   }
   
    private void initLayout(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    Step5Label = new Label(props.getProperty(STEP5_LABEL));
    Step5Desc = new Label(props.getProperty(STEP5_DESC_LABEL));
    ProgLabel = new Label(props.getProperty(PROG5_LABEL));
    
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
    checkProg = new ProgressBar();
    progInd = new ProgressIndicator();
    progInd.setProgress(50);
    rightBBox = new HBox();
    checkProg.setMinSize(450, 15);
    checkProg.setPadding(new Insets(25, 0, 0, 0));
    
    
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    SWork.setMinSize(900, 600);
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(10);
    rightBBox.getChildren().addAll(CodeCheckB, ViewResults);
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, checkProg);
    LeftBox.getChildren().addAll(Step5Label, Step5Desc, SWork, buttons);
    RightBox.getChildren().addAll(progBox, rightBBox, OutputWindow);
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
        CodeCheckB.getStyleClass().add(CLASS_FILE_BUTTON);
        ViewResults.getStyleClass().add(CLASS_FILE_BUTTON);
        ProgLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step5Label.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step5Desc.getStyleClass().add(CLASS_PROMPT_LABEL);
        MainBox.getStyleClass().add(CLASS_BOX);
        buttons.getStyleClass().add(CLASS_BUTTONBOX);
        
    }
    public HBox getStep5(){
        return MainBox;
    }
}

