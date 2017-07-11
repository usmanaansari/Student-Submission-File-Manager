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
public class Step3Workspace {
   CodeCheckApp app;
   //Step1Controller controller;
   CodeCheckData data;
   
   
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
    unzipProg = new ProgressBar();
    progInd = new ProgressIndicator(.47);
    unzipProg.setProgress(progInd.getProgress());
    progInd.setProgress(50);
    ProgPercentLabel = new Label(props.getProperty(PROGP_LABEL));
    unzipProg.setMinSize(450, 15);
    unzipProg.setPadding(new Insets(25, 0, 0, 0));
    
    
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    SZips.setMinSize(900, 600);
    OutputWindow.setMinSize(900, 600);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(45);
    
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, unzipProg,ProgPercentLabel);
    LeftBox.getChildren().addAll(Step3Label, Step3Desc,tableLabel, SZips, buttons);
    RightBox.getChildren().addAll(progBox, Unzip, OutputWindow);
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
}
