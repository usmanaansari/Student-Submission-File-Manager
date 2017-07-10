/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.CREATE_NEW_LABEL;
import static codecheck.CodeCheckProps.RECENT_WORK_LABEL;
import static codecheck.CodeCheckProps.WELC_LABEL;
import static codecheck.CodeCheckProps.XBUTTON;
import codecheck.data.CodeCheckData;
import static codecheck.style.CodeCheckStyle.CLASS_BOX;
import static codecheck.style.CodeCheckStyle.CLASS_NEWCC_LABEL;
import static codecheck.style.CodeCheckStyle.CLASS_PROMPT_LABEL;
import static codecheck.style.CodeCheckStyle.CLASS_WLEFT_BOX;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class WelcomeWorkspace {
    CodeCheckApp app;
    CodeCheckWorkspace work;
    CodeCheckData data;
    WelcomeController controller;
    Label welcTitle;
    Label recentWork;
    HBox newWorkBox;
    Hyperlink rec1;
    Hyperlink rec2;
    Hyperlink rec3;
    Hyperlink rec4;
    Hyperlink rec5;
    Hyperlink newCodeCheck;
    ImageView ccImage;
    BorderPane headPane;
    Button xButton;
    VBox leftBox;
    VBox rightBox;
    SplitPane MainBox;
    Pane whole;
    Image image;
    
    public WelcomeWorkspace(CodeCheckApp initApp){
        app = initApp;
        controller = new WelcomeController(app);
        initLayout();
        initControllers();
        initStyle();
    }
    
    private void initLayout(){
      PropertiesManager props = PropertiesManager.getPropertiesManager();
      image = new Image(FILE_PROTOCOL + PATH_IMAGES + "codecheck.jpg"); 
      ccImage = new ImageView(image);
      ccImage.resize(1000, 1000);
      ccImage.setX(1500);
      ccImage.setY(1500);
      
    
    leftBox = new VBox();
    rightBox = new VBox();
    MainBox = new SplitPane();
    whole = new Pane();
    recentWork = new Label(props.getProperty(RECENT_WORK_LABEL));
    welcTitle = new Label(props.getProperty(WELC_LABEL));

    newCodeCheck = new Hyperlink();
    newWorkBox = new HBox();
    newCodeCheck.setText(props.getProperty(CREATE_NEW_LABEL));
    newCodeCheck.setLayoutX(500);
    newCodeCheck.setPadding(new Insets(0, 150, 0, 160));
    newWorkBox.getChildren().add(newCodeCheck);
    
    //newCodeCheck.setLayoutX(50);
    
    leftBox.minHeight(1000);
    leftBox.minWidth(1500);
    rightBox.minWidth(1000);
    rightBox.setSpacing(500);
    rightBox.setPadding(new Insets(50, 50, 50, 300));
    whole.setMinWidth(2500);
    whole.setMinHeight(1000);
    MainBox.setMinWidth(2500);
    MainBox.setMinHeight(1000);
    
    headPane = new BorderPane();
    xButton = new Button(props.getProperty(XBUTTON));
    
    headPane.setLeft(welcTitle);
    headPane.setRight(xButton);
    
    //app.getGUI().getAppPane().setTop(headPane);
    
    rightBox.getChildren().addAll(ccImage, newWorkBox);
    leftBox.getChildren().addAll(recentWork);
    MainBox.getItems().addAll(leftBox,rightBox);
    MainBox.setDividerPositions(0.3);
    whole.getChildren().add(MainBox);
    
    
    }
    private void initControllers(){
        newCodeCheck.setOnAction( e -> {
            controller.handleNew();
        });
    }
    private void initStyle(){
        recentWork.getStyleClass().add(CLASS_PROMPT_LABEL);
        //welcTitle.getStyleClass().add(CLASS_PROMPT_LABEL);
        leftBox.getStyleClass().add(CLASS_WLEFT_BOX);
        //rightBox.getStyleClass().add(CLASS_BOX);
        newCodeCheck.getStyleClass().add(CLASS_NEWCC_LABEL);
        headPane.getStyleClass().add(CLASS_BORDERED_PANE);
    }
    public Pane getWelc(){
        return whole;
    }
}

