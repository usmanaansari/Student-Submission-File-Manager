/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.CCHECK_LABEL;
import static codecheck.CodeCheckProps.CSCHECK_LABEL;
import static codecheck.CodeCheckProps.EXTRACTCODE_BUTTON;
import static codecheck.CodeCheckProps.JCHECK_LABEL;
import static codecheck.CodeCheckProps.JSCHECK_LABEL;
import static codecheck.CodeCheckProps.PROG3_LABEL;
import static codecheck.CodeCheckProps.PROG4_LABEL;
import static codecheck.CodeCheckProps.PROGP_LABEL;
import static codecheck.CodeCheckProps.REFRESH_BUTTON;
import static codecheck.CodeCheckProps.REMOVE_BUTTON;
import static codecheck.CodeCheckProps.SFT_LABEL;
import static codecheck.CodeCheckProps.STEP3_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP3_LABEL;
import static codecheck.CodeCheckProps.STEP4_DESC_LABEL;
import static codecheck.CodeCheckProps.STEP4_LABEL;
import static codecheck.CodeCheckProps.STEP4_TABLE;
import static codecheck.CodeCheckProps.UNZIP_BUTTON;
import static codecheck.CodeCheckProps.VIEW_BUTTON;
import codecheck.data.CodeCheckData;
import static codecheck.style.CodeCheckStyle.CLASS_BOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX;
import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX_BUTTONS;
import static codecheck.style.CodeCheckStyle.CLASS_CHECKBOX;
import static codecheck.style.CodeCheckStyle.CLASS_FTYPE_BOX;
import static codecheck.style.CodeCheckStyle.CLASS_FTYPE_LABEL;
import static codecheck.style.CodeCheckStyle.CLASS_PROMPT_LABEL;
import static djf.settings.AppStartupConstants.PATH_WORK;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class Step4Workspace {
    CodeCheckApp app;
   //Step1Controller controller;
   CodeCheckData data;
   Step4Controller controller;
   
   Label Step4Label;
   Label Step4Desc;
   Label ProgLabel;
   Label ProgPercentLabel;
   Button ExtractCode;
   Button Remove;
   Button Refresh;
   Button View;
   HBox buttons;
   HBox progBox;
   ListView zipFiles;
   TextArea OutputWindow;
   HBox MainBox;
   VBox LeftBox;
   VBox RightBox;
   GridPane FileTypes;
   ProgressBar extCodeProg;
   ProgressIndicator progInd;
   CheckBox JCheck;
   CheckBox CCheck;
   CheckBox JSCheck;
   CheckBox CSCheck;
   CheckBox CustomCheck;
   Label JCheckL;
   Label CCheckL;
   Label JSCheckL;
   Label CSCheckL;
   Label CustomCheckL;
   Label FileTL;
   TextField CustomType;
   HBox TypesBox1;
   HBox TypesBox2;
   HBox TypesBox3;
   VBox TypesBox;
   Label tableLabel;
   
   
   
   public Step4Workspace(CodeCheckApp initApp){
       app = initApp;
       controller = new Step4Controller(app);
       
       initLayout();
       
       initControllers();
   
       initStyle();
   
   }
   
    private void initLayout(){
    PropertiesManager props = PropertiesManager.getPropertiesManager();
    Step4Label = new Label(props.getProperty(STEP4_LABEL));
    Step4Desc = new Label(props.getProperty(STEP4_DESC_LABEL));
    ProgLabel = new Label(props.getProperty(PROG4_LABEL));
    tableLabel = new Label(props.getProperty(STEP4_TABLE));
    ExtractCode = new Button(props.getProperty(EXTRACTCODE_BUTTON));
    Remove = new Button(props.getProperty(REMOVE_BUTTON));
    Refresh = new Button(props.getProperty(REFRESH_BUTTON));
    View = new Button(props.getProperty(VIEW_BUTTON));
    buttons = new HBox();
    progBox = new HBox();
    zipFiles = new ListView();
    OutputWindow = new TextArea();
    MainBox = new HBox();
    LeftBox = new VBox();
    RightBox = new VBox();
    extCodeProg = new ProgressBar(.47);
    progInd = new ProgressIndicator();
    FileTypes = new GridPane();
    progInd.setProgress(50);
    JCheck = new CheckBox();
    CCheck = new CheckBox();
    JSCheck = new CheckBox();
    CSCheck = new CheckBox();
    CustomCheck = new CheckBox();
    FileTL = new Label(props.getProperty(SFT_LABEL));
    JCheckL = new Label(props.getProperty(JCHECK_LABEL));
    CCheckL = new Label(props.getProperty(CCHECK_LABEL));
    JSCheckL = new Label(props.getProperty(JSCHECK_LABEL));
    CSCheckL = new Label(props.getProperty(CSCHECK_LABEL));
    CustomType = new TextField();
    extCodeProg.setMinSize(450, 15);
    extCodeProg.setPadding(new Insets(25, 0, 0, 0));
    TypesBox = new VBox();
    TypesBox1 = new HBox(123);
    TypesBox2 = new HBox(1234);
    TypesBox3 = new HBox();
    ProgPercentLabel = new Label(props.getProperty(PROGP_LABEL));
    View.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Remove.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    Refresh.prefWidthProperty().bind(buttons.widthProperty().multiply(.2));
    zipFiles.setMinSize(900, 300);
    OutputWindow.setMinSize(900, 400);
    LeftBox.setPadding(new Insets(0, 0, 0, 10));
    RightBox.setSpacing(45);
    JSCheck.setAlignment(Pos.CENTER_LEFT);
    
    CSCheck.setAlignment(Pos.CENTER_LEFT);
    TypesBox1.getChildren().addAll(JCheck, JCheckL, JSCheck, JSCheckL);
    TypesBox2.getChildren().addAll(CCheck, CCheckL, CSCheck, CSCheckL);
    TypesBox3.getChildren().addAll(CustomCheck, CustomType);
    TypesBox.getChildren().addAll(TypesBox1, TypesBox2, TypesBox3);
    TypesBox1.setSpacing(10);
    TypesBox2.setSpacing(10);
    TypesBox3.setSpacing(10);
    
    buttons.getChildren().addAll(Remove, Refresh, View);
    progBox.getChildren().addAll(ProgLabel, extCodeProg,ProgPercentLabel);
    LeftBox.getChildren().addAll(Step4Label, Step4Desc, tableLabel, zipFiles, buttons, FileTL, TypesBox);
    RightBox.getChildren().addAll(progBox, ExtractCode, OutputWindow);
    MainBox.getChildren().addAll(LeftBox, RightBox);
    MainBox.setSpacing(50);
    }
    private void initControllers(){
       Remove.setOnAction(e -> {
           try {
               controller.handleRemove();
           } catch (IOException ex) {
               Logger.getLogger(Step4Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
       });
       zipFiles.getSelectionModel().selectedItemProperty().addListener(e->
        {
                controller.handleSelect();
        });
       View.setOnAction(e -> {
           try {
               controller.handleView();
           } catch (ZipException ex) {
               Logger.getLogger(Step4Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
       });
       Refresh.setOnAction(e -> {
           try {
               controller.handleRefresh();
           } catch (IOException ex) {
               Logger.getLogger(Step4Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
       });
       ExtractCode.setOnAction(e ->{
           //ObservableList<String>
           ArrayList<File> projFolderList = new ArrayList<>();
           ArrayList<File> list = new ArrayList<>();
           FileFilter fileFilter = new WildcardFileFilter("*.java");
           ArrayList<File> fileNames = new ArrayList<>();
           String[] names = new String[1];
           names[0] = ".java";
           
           Task<Void> task = new Task<Void>() {
               @Override
               protected Void call() throws Exception {
                   ObservableList<String> tableData = zipFiles.getItems();
                   String projPath = PATH_WORK + app.getGUI().getWindow().getTitle().substring(13) + "\\projects\\";
                    for(String s : tableData){
                       File folder = new File(projPath);
                        File proj = new File(projPath + s);
                        projFolderList.addAll(Arrays.asList(proj.listFiles()));
                    }
                        for (int i = 0; i < projFolderList.size(); i++) {
                            File ok = projFolderList.get(i);
                            list.add(ok);
                            System.out.println(list.get(i));
                            //fileNames.addAll(Arrays.asList(files));

//                            Iterator it = FileUtils.iterateFiles(ok, names, true);
//                            while (it.hasNext()) {
//                                //System.out.println(((File) it.next()).getName());
//                            }
                        }
                        //System.out.print(fileNames.get(0).getName() + fileNames.get(1).getName() + fileNames.get(2).getName() );
//                       if(proj.isDirectory()){
//                       File[] files = proj.listFiles();
//                      
//                       list.addAll(Arrays.asList(files));
//                       System.out.print(list.get(2).getName());
                       
                       
                   
                   
                   
                   return null;
               }
           };
           Thread thread = new Thread(task);
           thread.start();
       });
    }
    private void initStyle(){
        Refresh.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        View.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        Remove.getStyleClass().add(CLASS_BUTTONBOX_BUTTONS);
        ExtractCode.getStyleClass().add(CLASS_FILE_BUTTON);
        ProgLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step4Label.getStyleClass().add(CLASS_PROMPT_LABEL);
        Step4Desc.getStyleClass().add(CLASS_PROMPT_LABEL);
        FileTL.getStyleClass().add(CLASS_PROMPT_LABEL);
        MainBox.getStyleClass().add(CLASS_BOX);
        buttons.getStyleClass().add(CLASS_BUTTONBOX);
        JCheckL.getStyleClass().add(CLASS_FTYPE_LABEL);
        JSCheckL.getStyleClass().add(CLASS_FTYPE_LABEL);
        CCheckL.getStyleClass().add(CLASS_FTYPE_LABEL);
        CSCheckL.getStyleClass().add(CLASS_FTYPE_LABEL);
        JCheck.getStyleClass().add(CLASS_CHECKBOX);
        JSCheck.getStyleClass().add(CLASS_CHECKBOX);
        CCheck.getStyleClass().add(CLASS_CHECKBOX);
        CSCheck.getStyleClass().add(CLASS_CHECKBOX);
        CustomCheck.getStyleClass().add(CLASS_CHECKBOX);
        TypesBox.getStyleClass().add(CLASS_FTYPE_BOX);
        tableLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ProgPercentLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
    }
    public HBox getStep4(){
        return MainBox;
    }
    public ListView getzipfiles(){
        return zipFiles;
    }
}

