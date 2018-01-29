package djf.ui;

//import codecheck.CodeCheckApp;
//import static codecheck.CodeCheckProps.CREATE_NEW_LABEL;
//import static codecheck.CodeCheckProps.RECENT_WORK_LABEL;
//import static codecheck.CodeCheckProps.WELC_LABEL;
//import static codecheck.CodeCheckProps.XBUTTON;
//import codecheck.data.CodeCheck;
//import codecheck.data.CodeCheckData;
//import static codecheck.style.CodeCheckStyle.CLASS_BUTTONBOX_BUTTONS;
//import static codecheck.style.CodeCheckStyle.CLASS_CC_IMAGEV;
//import static codecheck.style.CodeCheckStyle.CLASS_CREATENEW;
//import static codecheck.style.CodeCheckStyle.CLASS_HEAD_PANE;
//import static codecheck.style.CodeCheckStyle.CLASS_LEFT_WPANE;
//import static codecheck.style.CodeCheckStyle.CLASS_PROMPT_LABEL;
//import static codecheck.style.CodeCheckStyle.CLASS_RIGHT_WPANE;
//import static codecheck.style.CodeCheckStyle.CLASS_XB;
//import codecheck.workspace.CodeCheckWorkspace;
//import codecheck.workspace.WelcomeController;
//import codecheck.workspace.WelcomeWorkspace;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import djf.controller.AppFileController;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.*;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * This class provides the basic user interface for this application,
 * including all the file controls, but not including the workspace,
 * which would be customly provided for each app.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class AppGUI {
    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    protected AppFileController fileController;

    // THIS IS THE APPLICATION WINDOW
    protected Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    protected Scene primaryScene;
    Scene welcScene;
    Stage welcome;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION AppGUI. NOTE THAT THE WORKSPACE WILL GO
    // IN THE CENTER REGION OF THE appPane
    protected BorderPane appPane;
    
    // THIS IS THE TOP PANE WHERE WE CAN PUT TOOLBAR
    protected FlowPane topToolbarPane;
    
    // THIS IS THE FILE TOOLBAR AND ITS CONTROLS
    protected FlowPane fileToolbar;

    // FILE TOOLBAR BUTTONS
    protected Button newButton;
    protected Button loadButton;
    protected Button saveButton;
    protected Button saveAsButton;
    protected Button exitButton;
    
    // THIS DIALOG IS USED FOR GIVING FEEDBACK TO THE USER
    protected AppYesNoCancelDialogSingleton yesNoCancelDialog;
    
    // THIS TITLE WILL GO IN THE TITLE BAR
    protected String appTitle;
    
//    CodeCheckApp app;
//    CodeCheckWorkspace work;
//    CodeCheckData data;
//    WelcomeController controller;
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
    
    String p = "C:\\Users\\Usman\\Desktop\\219\\CodeCheckProj\\CodeCheck\\work\\";
    /**
     * This constructor initializes the file toolbar for use.
     * 
     * @param initPrimaryStage The window for this application.
     * 
     * @param initAppTitle The title of this application, which
     * will appear in the window bar.
     * 
     * @param app The app within this gui is used.
     */
    public AppGUI(  Stage initPrimaryStage, 
		    String initAppTitle, 
		    AppTemplate app){
	// SAVE THESE FOR LATER
	primaryStage = initPrimaryStage;
	appTitle = initAppTitle;
	
        
        //CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
        
        //Stage welc = work.getWelcome();
            
         initTopToolbar(app);
            
        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow();
        //buildWelcome((CodeCheckApp) app);
        //buildWelcome();
        //buildWelcome(app);
        
        // INIT THE STYLESHEET AND THE STYLE FOR THE FILE TOOLBAR
        initStylesheet(app);
        initFileToolbarStyle();      
      // }
        //}
        // INIT THE TOOLBAR
          
    
    }
    
    /**
     * Accessor method for getting the file toolbar controller.
     */
    public AppFileController getFileController() { return fileController; }
    
    /**
     * Accessor method for getting the application pane, within which all
     * user interface controls are ultimately placed.
     * 
     * @return This application GUI's app pane.
     */
    public BorderPane getAppPane() { return appPane; }
    
    /**
     * Accessor method for getting the toolbar pane in the top, within which
     * other toolbars are placed.
     * 
     * @return This application GUI's app pane.
     */    
    
    public FlowPane getTopToolbarPane() {
        return topToolbarPane;
    }
    
    /**
     * Accessor method for getting the file toolbar pane, within which all
     * file controls are ultimately placed.
     * 
     * @return This application GUI's app pane.
     */    
    public FlowPane getFileToolbar() {
        return fileToolbar;
    }
    
    /**
     * Accessor method for getting this application's primary stage's,
     * scene.
     * 
     * @return This application's window's scene.
     */
    public Scene getPrimaryScene() { return primaryScene; }
    
    /**
     * Accessor method for getting this application's window,
     * which is the primary stage within which the full GUI will be placed.
     * 
     * @return This application's primary stage (i.e. window).
     */    
    public Stage getWindow() { return primaryStage; }

    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
     * 
     * @param saved Describes whether the loaded Page has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        saveButton.setDisable(saved);
        

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
        saveAsButton.setDisable(false);
	newButton.setDisable(false);
        loadButton.setDisable(false);
	exitButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }

    /****************************************************************************/
    /* BELOW ARE ALL THE PRIVATE HELPER METHODS WE USE FOR INITIALIZING OUR AppGUI */
    /****************************************************************************/
    
    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    public void initTopToolbar(AppTemplate app) {
        fileToolbar = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newButton = initChildButton(fileToolbar,	NEW_ICON.toString(),	    NEW_TOOLTIP.toString(),	false);
        loadButton = initChildButton(fileToolbar,	LOAD_ICON.toString(),	    LOAD_TOOLTIP.toString(),	false);
        saveButton = initChildButton(fileToolbar,	SAVE_ICON.toString(),	    SAVE_TOOLTIP.toString(),	true);
        saveAsButton = initChildButton(fileToolbar,     SAVE_AS_ICON.toString(),     SAVE_AS_TOOLTIP.toString(),  true);
        exitButton = initChildButton(fileToolbar,	EXIT_ICON.toString(),	    EXIT_TOOLTIP.toString(),	false);
        
	// AND NOW SETUP THEIR EVENT HANDLERS
        fileController = new AppFileController(app);
        newButton.setOnAction(e -> {
            fileController.handleNewRequest();
        });
        loadButton.setOnAction(e -> {
            fileController.handleLoadRequest();
        });
        saveButton.setOnAction(e -> {
            fileController.handleSaveRequest();
        });
        saveAsButton.setOnAction(e -> {
            fileController.handleSaveAsRequest();
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest();
        });	
        
        // NOW PUT THE FILE TOOLBAR IN THE TOP TOOLBAR, WHICH COULD
        // ALSO STORE OTHER TOOLBARS
        topToolbarPane = new FlowPane();
        topToolbarPane.getChildren().add(fileToolbar);
    }

    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Page IS CREATED OR LOADED
    private void initWindow() {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(appTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        appPane = new BorderPane();
        appPane.setTop(topToolbarPane);
        primaryScene = new Scene(appPane);
        
        // SET THE APP ICON
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        String appIcon = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(APP_LOGO);
        primaryStage.getIcons().add(new Image(appIcon));

        // NOW TIE THE SCENE TO THE WINDOW
        primaryStage.setScene(primaryScene);
    }
//    private void buildWelcome(AppTemplate app){
//      PropertiesManager props = PropertiesManager.getPropertiesManager();
//      CodeCheckData data1 = (CodeCheckData) app.getDataComponent();
//       
//      image = new Image(FILE_PROTOCOL + PATH_IMAGES + "codecheck.jpg"); 
//      ccImage = new ImageView(image);
//      //ccImage.resize(2000, 1000);
//      //ccImage.setX(1500);
//      //ccImage.setY(1500);
//     ccImage.getStyleClass().add(CLASS_CC_IMAGEV);
//    
//    leftBox = new VBox();
//    rightBox = new VBox();
//    MainBox = new SplitPane();
//    whole = new Pane();
//    recentWork = new Label(props.getProperty(RECENT_WORK_LABEL));
//    welcTitle = new Label(props.getProperty(WELC_LABEL));
//
//    newCodeCheck = new Hyperlink();
//    newWorkBox = new HBox();
//    newCodeCheck.setText(props.getProperty(CREATE_NEW_LABEL));
//    newCodeCheck.getStyleClass().add(CLASS_CREATENEW);
//    newCodeCheck.setLayoutX(500);
//    newCodeCheck.setPadding(new Insets(0, 150, 0, 160));
//    newWorkBox.getChildren().add(newCodeCheck);
//    //ObservableList<CodeCheck> codes= data.getCodeChecks();
//    String path = "C:\\Users\\Usman\\Desktop\\219\\CodeCheckProj\\CodeCheck\\work\\";
//    
//    //
//    
//    
//    
//    headPane = new BorderPane();
//    
//    recentWork.setPadding(new Insets(0,0,0,200));
//    xButton = new Button(props.getProperty(XBUTTON));
//   
//    rightBox.setPadding(new Insets(50, 50, 50, 300));
//    whole.setMinWidth(2500);
//    whole.setMinHeight(1000);
//    MainBox.setMinWidth(2500);
//    MainBox.setMinHeight(1000);
//    
//    headPane.getStyleClass().add(CLASS_HEAD_PANE);
//    rightBox.getStyleClass().add(CLASS_RIGHT_WPANE);
//    leftBox.getStyleClass().add(CLASS_LEFT_WPANE);
//    recentWork.getStyleClass().add(CLASS_PROMPT_LABEL);
//    welcTitle.getStyleClass().add(CLASS_PROMPT_LABEL);
//    xButton.getStyleClass().add(CLASS_XB);
//    rightBox.getChildren().addAll(ccImage, newWorkBox);
//    leftBox.getChildren().addAll(recentWork, rec5, rec4, rec3, rec2, rec1);
//    headPane.setLeft(welcTitle);
//    headPane.setRight(xButton);
//    appPane.setTop(headPane);
//    leftBox.prefWidth(1000);
//    appPane.setLeft(leftBox);
//    //appPane.getLeft().prefWidth(1000);
//    appPane.setCenter(rightBox);
//    
//    initStylesheet(app);
//    
//    xButton.setOnAction(e-> {
//        appPane.getChildren().remove(headPane);
//        appPane.getChildren().remove(leftBox);
//        appPane.getChildren().remove(rightBox);
//        initTopToolbar(app);
//        initFileToolbarStyle();
//        appPane.setTop(topToolbarPane);
//        app.buildAppComponentsHook();
//    });
//    
//    newCodeCheck.setOnAction(e -> {
//          try {
//              fileController.handleNew();
//          } catch (IOException ex) {
//              Logger.getLogger(AppGUI.class.getName()).log(Level.SEVERE, null, ex);
//          }
//    });
//    rec2.setOnAction(e -> {
//        fileController.handleLoad2();
//        appPane.getChildren().remove(headPane);
//        appPane.getChildren().remove(leftBox);
//        appPane.getChildren().remove(rightBox);
//        initTopToolbar(app);
//        initFileToolbarStyle();
//        appPane.setTop(topToolbarPane);
//        app.buildAppComponentsHook();
//    });
//    rec1.setOnAction(e -> {
//        fileController.handleLoad();
//        appPane.getChildren().remove(headPane);
//        appPane.getChildren().remove(leftBox);
//        appPane.getChildren().remove(rightBox);
//        initTopToolbar(app);
//        initFileToolbarStyle();
//        appPane.setTop(topToolbarPane);
//        app.buildAppComponentsHook();
//    });
//    rec3.setOnAction(e -> { 
//        fileController.handleLoad3();
//        appPane.getChildren().remove(headPane);
//        appPane.getChildren().remove(leftBox);
//        appPane.getChildren().remove(rightBox);
//        initTopToolbar(app);
//        initFileToolbarStyle();
//        appPane.setTop(topToolbarPane);
//        app.buildAppComponentsHook();
//    });
//    rec4.setOnAction(e -> {
//        fileController.handleLoad4();
//        appPane.getChildren().remove(headPane);
//        appPane.getChildren().remove(leftBox);
//        appPane.getChildren().remove(rightBox);
//        initTopToolbar(app);
//        initFileToolbarStyle();
//        appPane.setTop(topToolbarPane);
//        app.buildAppComponentsHook();
//    });
//    rec5.setOnAction(e -> {
//        fileController.handleLoad5();
//        appPane.getChildren().remove(headPane);
//        appPane.getChildren().remove(leftBox);
//        appPane.getChildren().remove(rightBox);
//        initTopToolbar(app);
//        initFileToolbarStyle();
//        appPane.setTop(topToolbarPane);
//        app.buildAppComponentsHook();
//    });
//    //MainBox.getItems().addAll(leftBox,rightBox);
//    //MainBox.setDividerPositions(0.3);
//    //whole.getChildren().add(MainBox);
//    
//    
//    }
    public Hyperlink get1(){
        return rec1;
    }
    public Hyperlink get3(){
        return rec3;
    }
    public Hyperlink get4(){
        return rec4;
    }
    public Hyperlink get5(){
        return rec5;
    }
    
    public Hyperlink get2(){
        return rec2;
    }
    public String getP(){
        return p;
    }
    /**
     * This is a public helper method for initializing a simple button with
     * an icon and tooltip and placing it into a toolbar.
     * 
     * @param toolbar Toolbar pane into which to place this button.
     * 
     * @param icon Icon image file name for the button.
     * 
     * @param tooltip Tooltip to appear when the user mouses over the button.
     * 
     * @param disabled true if the button is to start off disabled, false otherwise.
     * 
     * @return A constructed, fully initialized button placed into its appropriate
     * pane container.
     */
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
    
   /**
     *  Note that this is the default style class for the top file toolbar
     * and that style characteristics for this type of component should be 
     * put inside app_properties.xml.
     */
    public static final String CLASS_BORDERED_PANE = "bordered_pane";

   /**
     *  Note that this is the default style class for the file buttons in
     * the top file toolbar and that style characteristics for this type
     * of component should be put inside app_properties.xml.
     */
    public static final String CLASS_FILE_BUTTON = "file_button";
    
    /**
     * This function sets up the stylesheet to be used for specifying all
     * style for this application. Note that it does not attach CSS style
     * classes to controls, that must be done separately.
     */
    public void initStylesheet(AppTemplate app) {
	// SELECT THE STYLESHEET
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String stylesheet = props.getProperty(APP_PATH_CSS);
	stylesheet += props.getProperty(APP_CSS);
        Class appClass = app.getClass();
	URL stylesheetURL = appClass.getResource(stylesheet);
	String stylesheetPath = stylesheetURL.toExternalForm();
	primaryScene.getStylesheets().add(stylesheetPath);
        //welcScene.getStylesheets().add(stylesheetPath);
    }
    
    /**
     * This function specifies the CSS style classes for the controls managed
     * by this framework.
     */
    public void initFileToolbarStyle() {
	topToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
        fileToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
	newButton.getStyleClass().add(CLASS_FILE_BUTTON);
	loadButton.getStyleClass().add(CLASS_FILE_BUTTON);
	saveButton.getStyleClass().add(CLASS_FILE_BUTTON);
	exitButton.getStyleClass().add(CLASS_FILE_BUTTON);
    }
    
    public Button getNewButton(){
        return newButton;
    }
    public Stage getPrimaryStage(){
        return primaryStage;
    }
    public BorderPane getToppane(){
        return headPane;
    }
    public VBox getLeftBox(){
        return leftBox;
    }
    public VBox getRightBox(){
        return rightBox;
    }
}

