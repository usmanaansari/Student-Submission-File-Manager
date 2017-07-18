/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.workspace;

import codecheck.CodeCheckApp;
import static codecheck.CodeCheckProps.ABOUT_BUTTON;
import static codecheck.CodeCheckProps.ABOUT_PIC;
import static codecheck.CodeCheckProps.ABOUT_TOOLTIP;
import static codecheck.CodeCheckProps.CLOSE_CONFIRMATION;
import static codecheck.CodeCheckProps.CLOSE_MESSAGE;
import static codecheck.CodeCheckProps.HOME_BUTTON;
import static codecheck.CodeCheckProps.NEXT_BUTTON;
import static codecheck.CodeCheckProps.PREV_BUTTON;
import static codecheck.CodeCheckProps.RENAME_BUTTON;
import static codecheck.CodeCheckProps.RENAME_PIC;
import static codecheck.CodeCheckProps.RENAME_TOOLTIP;
import static codecheck.CodeCheckProps.WELC_LABEL;
import codecheck.data.CodeCheckData;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.APP_CSS;
import static djf.settings.AppPropertyType.APP_PATH_CSS;
import static djf.settings.AppPropertyType.LOAD_ICON;
import static djf.settings.AppPropertyType.LOAD_TOOLTIP;
import static djf.settings.AppPropertyType.NEW_ICON;
import static djf.settings.AppPropertyType.NEW_TOOLTIP;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Usman
 */
public class CodeCheckWorkspace extends AppWorkspaceComponent {
    CodeCheckApp app;
    CodeCheckData data;
    CodeCheckController controller;
    
    codecheck.workspace.WelcomeWorkspace welcwork;
    codecheck.workspace.Step1Workspace work1;
    codecheck.workspace.Step2Workspace work2;
    codecheck.workspace.Step3Workspace work3;
    codecheck.workspace.Step4Workspace work4;
    codecheck.workspace.Step5Workspace work5;
    
    Pane Step0;
    HBox Step1;
    HBox Step2;
    HBox Step3;
    HBox Step4;
    HBox Step5;
    
    FlowPane StepTraverseBar;
    FlowPane top;
    Button nextButton;
    Button prevButton;
    Button homeButton;
    Button aboutButton;
    Button renameButton;
    
    Button newButton;
    Button loadButton;
    int workspaceCounter;
    FlowPane fileTool;
    BorderPane appPane;
    
    Label Title;
    Stage welcome;
    Scene welcscene;
    public CodeCheckWorkspace(CodeCheckApp initApp){
        app = initApp;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        workspace = new BorderPane();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
//        ((BorderPane) workspace).setCenter(tabs);
//       
//        
//       taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));
//        // NOW LET'S SETUP THE EVENT HANDLING
////        controller = new CodeCheckController(app);
        
        initLayout();
        
        initControllers();
        
        initStyle();
    }
    private void initLayout(){
        controller = new CodeCheckController(app);
        data = new CodeCheckData(app);
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        //welcwork = new WelcomeWorkspace(app);
        work1 = new Step1Workspace(app);
        work2 = new Step2Workspace(app);
        work3 = new Step3Workspace(app);
        work4 = new Step4Workspace(app);
        work5 = new Step5Workspace(app);
        welcwork = new WelcomeWorkspace(app);
        fileTool = app.getGUI().getFileToolbar();
        top = app.getGUI().getTopToolbarPane();
        StepTraverseBar = new FlowPane();
        welcome = new Stage();
        
        //work4 = new Step4Workspace(app);
        nextButton = new Button(props.getProperty(NEXT_BUTTON));
        prevButton = new Button(props.getProperty(PREV_BUTTON));
        homeButton = new Button(props.getProperty(HOME_BUTTON));
        
        renameButton = new Button(props.getProperty(RENAME_BUTTON));
        aboutButton = new Button(props.getProperty(ABOUT_BUTTON));
        //newButton = new Button(props.getProperty(NEW_ICON));
        loadButton = new Button(props.getProperty(LOAD_ICON));
        
        fileTool.getChildren().remove(0, 5);
        newButton = initChildButton(fileTool, NEW_ICON.toString(), NEW_TOOLTIP.toString(), false);
        loadButton = initChildButton(fileTool, LOAD_ICON.toString(), LOAD_TOOLTIP.toString(), false);
        renameButton = initChildButton(fileTool, RENAME_PIC.toString(), RENAME_TOOLTIP.toString(), false);
        aboutButton = initChildButton(fileTool, ABOUT_PIC.toString(), ABOUT_TOOLTIP.toString(), false);
        
        
        Title = new Label();
        Title.setText(data.getTitle());

        homeButton.setDisable(true);
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        
        StepTraverseBar.getChildren().addAll(homeButton, prevButton, nextButton);
        
        
        
        appPane = app.getGUI().getAppPane();
        fileTool.setHgap(50);
        fileTool.setMinWidth(1000);
        
        StepTraverseBar.setMinWidth(700);
        //StepTraverseBar.setMaxWidth(1000);
        StepTraverseBar.setHgap(50);
        
        renameButton.setMinWidth(150);
        newButton.setMinWidth(150);
        loadButton.setMinWidth(150);
        
//        renameButton.prefWidthProperty().bind(fileTool.widthProperty().multiply(.2));

        
        app.getGUI().getTopToolbarPane().getChildren().add(StepTraverseBar);
        //StepTraverseBar.setPadding(new Insets(20, 10, 20, 0));
        Step0 = welcwork.getWelc();
        Step1 = work1.getStep1();
        Step2 = work2.getStep2();
        Step3 = work3.getStep3();
        Step4 = work4.getStep4();
        Step5 = work5.getStep5();
        renameButton.setDisable(true);
        //app.getGUI().getWindow().setTitle(Title.getText());
        
        //Set StyleSheet for WelcomeWindow
        String stylesheet = props.getProperty(APP_PATH_CSS);
	stylesheet += props.getProperty(APP_CSS);
        Class appClass = app.getClass();
	URL stylesheetURL = appClass.getResource(stylesheet);
	String stylesheetPath = stylesheetURL.toExternalForm();
	welcscene = new Scene(Step0);
        welcscene.getStylesheets().add(stylesheetPath);
        welcome.setTitle(props.getProperty(WELC_LABEL));
        welcome.setScene(welcscene);
        //welcome.show();
        
        //app.getGUI().getPrimaryStage().close();
        workspace = Step0;
        
        //fileTool.setPadding(new Insets(0, 20, 10, 20));
        
        //workspace = workspaceBorderPane;
    }
    
    private void initControllers(){
        app.getGUI().getPrimaryStage().setOnCloseRequest(e ->{
//           PropertiesManager props = PropertiesManager.getPropertiesManager();
//        
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle(props.getProperty(CLOSE_CONFIRMATION));
//            alert.setContentText(props.getProperty(CLOSE_CONFIRMATION));
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                app.getGUI().getPrimaryStage().close();
//            } else {
//                e.consume();
//            }
        });
        newButton.setOnAction(e -> {
            try {
                controller.handleNew();
                //app.getGUI().getFileController().handleNewRequest();
            } catch (IOException ex) {
                Logger.getLogger(CodeCheckWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        nextButton.setOnAction(e -> {
            if(appPane.getCenter().equals(Step1)){
                homeButton.setDisable(false);
                nextButton.setDisable(false);
                prevButton.setDisable(false);
                app.getGUI().getAppPane().setCenter(Step2);
                return;
            }
            if(appPane.getCenter().equals(Step2)){
                
                app.getGUI().getAppPane().setCenter(Step3);
                return;
            }
            if(appPane.getCenter().equals(Step3)){
                
                app.getGUI().getAppPane().setCenter(Step4);
                return;
            }
            if(appPane.getCenter().equals(Step4)){
                nextButton.setDisable(true);
                app.getGUI().getAppPane().setCenter(Step5);
                //nextButton.setDisable(true);
                return;
            }
            if(appPane.getCenter().equals(Step5)){
                
            }
        });
        prevButton.setOnAction(e -> {
            if(appPane.getCenter().equals(Step1)){
                nextButton.setDisable(false);
                
            }
            if(appPane.getCenter().equals(Step2)){
                homeButton.setDisable(true);
                nextButton.setDisable(false);
                prevButton.setDisable(true);
                app.getGUI().getAppPane().setCenter(Step1);
                
                return;
            }
            if(appPane.getCenter().equals(Step3)){
                nextButton.setDisable(false);
                app.getGUI().getAppPane().setCenter(Step2);
                
                return;
            }
            if(appPane.getCenter().equals(Step4)){
                 nextButton.setDisable(false);
                app.getGUI().getAppPane().setCenter(Step3);
                
                return;
            }
            if(appPane.getCenter().equals(Step5)){
                nextButton.setDisable(false);
                app.getGUI().getAppPane().setCenter(Step4);
                
                return;
            }
        });
        homeButton.setOnAction(e -> {
            if(appPane.getCenter().equals(Step1)){
                
            }
            else{
            app.getGUI().getAppPane().setCenter(Step1);
            homeButton.setDisable(true);
            prevButton.setDisable(true);
            nextButton.setDisable(false);
            }
        });
        renameButton.setOnAction( e -> {
            try {
                controller.handleRename();
            } catch (IOException ex) {
                Logger.getLogger(CodeCheckWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        aboutButton.setOnAction(e -> {
            controller.handleAbout();
        });
        loadButton.setOnAction(e -> {
            controller.handleLoad1();
        });
    }
    
    private void initStyle(){
        StepTraverseBar.getStyleClass().add(CLASS_BORDERED_PANE);
        //app.getGUI().getFileToolbar().getStyleClass().add(CLASS_BORDERED_PANE);
        newButton.getStyleClass().add(CLASS_FILE_BUTTON);
        loadButton.getStyleClass().add(CLASS_FILE_BUTTON);
        renameButton.getStyleClass().add(CLASS_FILE_BUTTON);
        aboutButton.getStyleClass().add(CLASS_FILE_BUTTON);
        homeButton.getStyleClass().add(CLASS_FILE_BUTTON);
        nextButton.getStyleClass().add(CLASS_FILE_BUTTON);
        prevButton.getStyleClass().add(CLASS_FILE_BUTTON);
        //app.getGUI().getFileToolbar().getStyleClass().add(CLASS_BORDERED_PANE);
    }
    
    @Override
    public void resetWorkspace() {
        workspace = Step1;
        nextButton.setDisable(false);
        homeButton.setDisable(true);
        prevButton.setDisable(true);
        renameButton.setDisable(false);
        appPane.setCenter(Step1);
        work1.BBSubs.getItems().clear();
        work2.SSubs.getItems().clear();
        work3.SZips.getItems().clear();
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        
    }
    public Stage getWelcome(){
        return welcome;
    }
    
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
}
