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
import static codecheck.style.CodeCheckStyle.CLASS_HEAD_PANE;
import static codecheck.style.CodeCheckStyle.CLASS_LEFT_WPANE;
import static codecheck.style.CodeCheckStyle.CLASS_NEWCC_LABEL;
import static codecheck.style.CodeCheckStyle.CLASS_PROMPT_LABEL;
import static codecheck.style.CodeCheckStyle.CLASS_RIGHT_WPANE;
import static codecheck.style.CodeCheckStyle.CLASS_WLEFT_BOX;
import static codecheck.style.CodeCheckStyle.CLASS_XB;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    BorderPane appPane;
    
    public WelcomeWorkspace(CodeCheckApp initApp){
        app = initApp;
        controller = new WelcomeController(app);
        data = (CodeCheckData) app.getDataComponent();
        work = (CodeCheckWorkspace) app.getWorkspaceComponent();
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
      
      appPane = app.getGUI().getAppPane();
    
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
    rec1 = new Hyperlink();
    rec2 = new Hyperlink();
    rec3 = new Hyperlink();
    rec4 = new Hyperlink();
    rec5 = new Hyperlink();
    //app.getGUI().getAppPane().setTop(headPane);
    ArrayList<String> files = folders(data.getWorkPath());
    //System.out.print(files);
    File file = new File(data.getWorkPath());
    int yo = file.listFiles().length;
            if (yo == 0){
                System.out.print("Folder empty");
            }
            else if(yo == 3){
                rec5.setText(files.get(0));
            }
            else if(yo == 6){
                rec5.setText(files.get(1));
                rec4.setText(files.get(0));
            }
            else if(yo == 9){
                rec5.setText(files.get(2));
                rec4.setText(files.get(1));
                rec3.setText(files.get(0));
            }
            else if(yo==12){
                rec5.setText(files.get(3));
                rec4.setText(files.get(2));
                rec3.setText(files.get(1));
                rec2.setText(files.get(0));
            }
            else if(yo==15){
                rec5.setText(files.get(4));
                rec4.setText(files.get(3));
                rec3.setText(files.get(2));
                rec2.setText(files.get(1));
                rec1.setText(files.get(0));
            }
            else if( yo > 15){
                rec5.setText(files.get((yo/3)-1));
                rec4.setText(files.get((yo/3)-2));
                rec3.setText(files.get((yo/3)-3));
                rec2.setText(files.get((yo/3)-4));
                rec1.setText(files.get((yo/3)-5));
            }
    rightBox.getChildren().addAll(ccImage, newWorkBox);
    leftBox.getChildren().addAll(recentWork, rec5, rec4, rec3, rec2, rec1);
    //MainBox.getItems().addAll(leftBox,rightBox);
    MainBox.setDividerPositions(0.3);
   // whole.getChildren().add(MainBox);
    
    app.getGUI().getAppPane().setLeft(leftBox);
    app.getGUI().getAppPane().setCenter(rightBox);
    app.getGUI().getAppPane().setTop(headPane);
    
    }
    private void initControllers(){
        newCodeCheck.setOnAction( e -> {
            try {
                controller.handleNew();
            } catch (IOException ex) {
                Logger.getLogger(WelcomeWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        xButton.setOnAction(e-> {
            controller.handleX();
    });
        rec5.setOnAction(e -> {
            String name = rec5.getText();
            controller.handleLoadRecent(name);
        });
        rec4.setOnAction(e -> {
            String name = rec4.getText();
            controller.handleLoadRecent(name);
        });
        rec3.setOnAction(e -> {
            String name = rec3.getText();
            controller.handleLoadRecent(name);
        });
        rec2.setOnAction(e -> {
            String name = rec2.getText();
            controller.handleLoadRecent(name);
        });
        rec1.setOnAction(e -> {
            String name = rec1.getText();
            controller.handleLoadRecent(name);
        });
    }
    private void initStyle(){
    newCodeCheck.getStyleClass().add(CLASS_NEWCC_LABEL);
    headPane.getStyleClass().add(CLASS_HEAD_PANE);
    rightBox.getStyleClass().add(CLASS_RIGHT_WPANE);
    leftBox.getStyleClass().add(CLASS_LEFT_WPANE);
    recentWork.getStyleClass().add(CLASS_PROMPT_LABEL);
    welcTitle.getStyleClass().add(CLASS_PROMPT_LABEL);
    xButton.getStyleClass().add(CLASS_XB);
    }
    public Pane getWelc(){
        return whole;
    }
    public VBox getLeft(){
        return leftBox;
    }
    public VBox getRight(){
        return rightBox;
    }
    public BorderPane getHeadPane(){
        return headPane;
    }
    private ArrayList<String> folders(String name){
    
    
    File codeDir = new File(name);
    ArrayList<String> work = new ArrayList();
    ArrayList<Long> dateList = new ArrayList();
    File[] yo = codeDir.listFiles();
    for(File f : yo){
        if(f.isDirectory()){
            dateList.add(f.lastModified());
            
        }
    }
    Collections.sort(dateList);
//    for(int i = 0; i < dateList.size(); i ++){
//        for(int j = 0; j< yo.length; j++){
//            if(yo[i].lastModified() == dateList.get(i)){
//                work.add(yo[i].getName());
//            }
//        }
//    }
    for(Long date : dateList){
        for(File file : yo){
            if(file.lastModified() == date){
                work.add(file.getName());
            }
        }
    }
    return work;
    
    }
}

