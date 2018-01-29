package djf.controller;

//import static codecheck.CodeCheckProps.DUP_CODECHECK_MESSAGE;
//import static codecheck.CodeCheckProps.DUP_CODECHECK_TITLE;
//import static codecheck.CodeCheckProps.NEW_CONFIRMATION;
//import static codecheck.CodeCheckProps.NEW_CONF_CONTENT;
//import static codecheck.CodeCheckProps.NEW_CONTENT;
//import static codecheck.CodeCheckProps.NEW_TITLE;
//import codecheck.data.CodeCheck;
//import codecheck.data.CodeCheckData;
//import codecheck.workspace.CodeCheckWorkspace;
import djf.ui.AppYesNoCancelDialogSingleton;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppGUI;
import djf.components.AppDataComponent;
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import properties_manager.PropertiesManager;
import djf.AppTemplate;
import static djf.settings.AppPropertyType.LOAD_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.LOAD_ERROR_TITLE;
import static djf.settings.AppPropertyType.LOAD_WORK_TITLE;
import static djf.settings.AppPropertyType.NEW_COMPLETED_MESSAGE;
import static djf.settings.AppPropertyType.WORK_FILE_EXT;
import static djf.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static djf.settings.AppPropertyType.NEW_COMPLETED_TITLE;
import static djf.settings.AppPropertyType.NEW_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.NEW_ERROR_TITLE;
import static djf.settings.AppPropertyType.SAVE_COMPLETED_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_COMPLETED_TITLE;
import static djf.settings.AppPropertyType.SAVE_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_ERROR_TITLE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import static djf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static djf.settings.AppStartupConstants.PATH_WORK;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;



/**
 * This class provides the event programmed responses for the file controls
 * that are provided by this framework.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public class AppFileController {
    // HERE'S THE APP
    AppTemplate app;
    
    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    boolean saved;
    
    // THIS IS THE FILE FOR THE WORK CURRENTLY BEING WORKED ON
    File currentWorkFile;

    /**
     * This constructor just keeps the app for later.
     * 
     * @param initApp The application within which this controller
     * will provide file toolbar responses.
     */
    public AppFileController(AppTemplate initApp) {
        // NOTHING YET
        saved = true;
        app = initApp;
    }
    
    /**
     * This method marks the appropriate variable such that we know
     * that the current Work has been edited since it's been saved.
     * The UI is then updated to reflect this.
     * 
     * @param gui The user interface editing the Work.
     */
    public void markAsEdited(AppGUI gui) {
        // THE WORK IS NOW DIRTY
        saved = false;
        
        // LET THE UI KNOW
        gui.updateToolbarControls(saved);
    }

    /**
     * This method starts the process of editing new Work. If work is
     * already being edited, it will prompt the user to save it first.
     * 
     */
    public void handleNewRequest() {
	AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
       
        
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave();
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // NOW RELOAD THE WORKSPACE WITH THE RESET DATA
                app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
		
		// WORK IS NOT SAVED
                saved = false;
		currentWorkFile = null;
                
                //app.getWorkspaceComponent().getWorkspace().
                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                app.getGUI().updateToolbarControls(saved);
               
                
                // TELL THE USER NEW WORK IS UNDERWAY
		dialog.show(props.getProperty(NEW_COMPLETED_TITLE), props.getProperty(NEW_COMPLETED_MESSAGE));
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG, PROVIDE FEEDBACK
	    dialog.show(props.getProperty(NEW_ERROR_TITLE), props.getProperty(NEW_ERROR_MESSAGE));
        }
    }
    
//    public void handleNew() throws IOException{
//        CodeCheckWorkspace work = (CodeCheckWorkspace) app.getWorkspaceComponent();
//        CodeCheckData data = (CodeCheckData) app.getDataComponent();
//        
//        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
//	PropertiesManager props = PropertiesManager.getPropertiesManager();
//        
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle(props.getProperty(NEW_CONFIRMATION));
//        alert.setContentText(props.getProperty(NEW_CONF_CONTENT));
//        
//        TextInputDialog dialog1 = new TextInputDialog();
//        dialog1.setTitle(props.getProperty(NEW_TITLE));
//        dialog1.setContentText(props.getProperty(NEW_CONTENT));
//        
//        BorderPane appPane = app.getGUI().getAppPane();
//        FlowPane topToolbarPane = app.getGUI().getTopToolbarPane();
//        FlowPane fileToolbar = app.getGUI().getFileToolbar();
//        //topToolbarPane.getStyleClass().add(CLASS_BORDERED_PANE);
//        //fileToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
//        VBox leftBox = app.getGUI().getLeftBox();
//        VBox rightBox = app.getGUI().getRightBox();
//        BorderPane headPane = app.getGUI().getToppane();
//        
//       
//        List<File> directories = new ArrayList<>();
//        // String title;
//        Optional<ButtonType> result = alert.showAndWait();
//        if(result.get() == ButtonType.OK){
//            Optional<String> result1 = dialog1.showAndWait();
//            if(result1.isPresent()){
//                String title = result1.get();
//                
//                String path= "C:\\Users\\Usman\\Desktop\\219\\CodeCheckProj\\CodeCheck\\work\\" + title;
//                
//                CodeCheck codeC = new CodeCheck(title, path);
//                File CC = new File(codeC.getPath());
//                
//                if(CC.mkdir()){
//                    System.out.println("Code Check successfully created");
//                    directories.add(new File(CC.getPath() + "\\blackboard"));
//                    directories.add(new File(CC.getPath() + "\\submissions"));
//                    directories.add(new File(CC.getPath() + "\\projects"));
//                    directories.add(new File(CC.getPath() + "\\code"));
//                    data.addCodeCheck(title, path);
//                    
//                    for(File f : directories) {
//                        if(f.mkdir()) {
//                           System.out.println(f.getPath() + " made");
//                        }
//                        else{
//                          System.out.println(f.getPath() + " not made");
//                        }
//                    }
//                    // fix this app.getGUI().getPrimaryStage().setTitle("Code Check - "+ title);
//                    
//                    data.setTitle(title);
//                    
//                    // WE MAY HAVE TO SAVE CURRENT WORK
//                    boolean continueToMakeNew = true;
//                    // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
//                    if (continueToMakeNew) {
//                    // RESET THE WORKSPACE
//                    app.getWorkspaceComponent().resetWorkspace();
//
//                    // RESET THE DATA
//                    app.getDataComponent().resetData();
//                
//                    // NOW RELOAD THE WORKSPACE WITH THE RESET DATA
//                    app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
//
//                    // MAKE SURE THE WORKSPACE IS ACTIVATED
//                    app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
//                    app.getFileComponent().saveData(data, path+"File");
//                    appPane.getChildren().remove(headPane);
//                    appPane.getChildren().remove(leftBox);
//                    appPane.getChildren().remove(rightBox);
//                   
//                    appPane.setTop(app.getGUI().getTopToolbarPane());
//                    
//                    app.getGUI().getPrimaryStage().setTitle("Code Check - " + title);
//                    //app.getWorkspaceComponent().getWorkspace().
//                    // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
//                    // THE APPROPRIATE CONTROLS
//                    // TELL THE USER NEW WORK IS UNDERWAY
//                    dialog.show(props.getProperty(NEW_COMPLETED_TITLE), props.getProperty(NEW_COMPLETED_MESSAGE));
//                    }
//                }
//                else{
//                    Alert rename = new Alert(Alert.AlertType.ERROR);
//                    rename.setTitle(props.getProperty(DUP_CODECHECK_TITLE));
//                    rename.setContentText(props.getProperty(DUP_CODECHECK_MESSAGE));
//                    rename.showAndWait();
//                    System.out.println("Code Check with this name already exists");
//                }
//            }
//    }
//    }
public void handleLoad2(){
        
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToOpen = true;
        
        // THE USER CAN OPT OUT HERE WITH A CANCEL
        //continueToOpen = promptToSave();
        // IF THE USER REALLY WANTS TO OPEN A Course
        if (continueToOpen) {
            // GO AHEAD AND PROCEED LOADING A Course
            open2();
            
        }
    }
public void handleLoad3(){
        
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToOpen = true;
        
        // THE USER CAN OPT OUT HERE WITH A CANCEL
        //continueToOpen = promptToSave();
        // IF THE USER REALLY WANTS TO OPEN A Course
        if (continueToOpen) {
            // GO AHEAD AND PROCEED LOADING A Course
            open3();
            
        }
    }
public void handleLoad(){
        
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToOpen = true;
        
        // THE USER CAN OPT OUT HERE WITH A CANCEL
        //continueToOpen = promptToSave();
        // IF THE USER REALLY WANTS TO OPEN A Course
        if (continueToOpen) {
            // GO AHEAD AND PROCEED LOADING A Course
            open();
            
        }
    }


public void handleLoad5(){
        
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToOpen = true;
        
        // THE USER CAN OPT OUT HERE WITH A CANCEL
        //continueToOpen = promptToSave();
        // IF THE USER REALLY WANTS TO OPEN A Course
        if (continueToOpen) {
            // GO AHEAD AND PROCEED LOADING A Course
            open5();
            
        }
    }
public void handleLoad4(){
        
        // WE MAY HAVE TO SAVE CURRENT WORK
        boolean continueToOpen = true;
        
        // THE USER CAN OPT OUT HERE WITH A CANCEL
        //continueToOpen = promptToSave();
        // IF THE USER REALLY WANTS TO OPEN A Course
        if (continueToOpen) {
            // GO AHEAD AND PROCEED LOADING A Course
            open4();
            
        }
    }
    /**
     * This method lets the user open a Course saved to a file. It will also
     * make sure data for the current Course is not lost.
     * 
     * @param gui The user interface editing the course.
     */
    public void handleLoadRequest() {
       

        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }

            // IF THE USER REALLY WANTS TO OPEN A Course
            if (continueToOpen) {
                // GO AHEAD AND PROCEED LOADING A Course
                promptToOpen();
                
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    PropertiesManager props = PropertiesManager.getPropertiesManager();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }

    /**
     * This method will save the current course to a file. Note that we already
     * know the name of the file, so we won't need to prompt the user.
     * 
     * 
     * @param courseToSave The course being edited that is to be saved to a file.
     */
    public void handleSaveRequest() {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
	    // MAYBE WE ALREADY KNOW THE FILE
	    if (currentWorkFile != null) {
		saveWork(currentWorkFile);
	    }
	    // OTHERWISE WE NEED TO PROMPT THE USER
	    else {
		// PROMPT THE USER FOR A FILE NAME
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

		File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
		if (selectedFile != null) {
		    saveWork(selectedFile);
		}
	    }
        } catch (IOException ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }

    }
    
    public void handleSaveAsRequest(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try {
	    // MAYBE WE ALREADY KNOW THE FILE
	    if (currentWorkFile != null) {
		saveWork(currentWorkFile);
	    }
	    // OTHERWISE WE NEED TO PROMPT THE USER
	    else {
		// PROMPT THE USER FOR A FILE NAME
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

		File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
		if (selectedFile != null) {
		    saveWork(selectedFile);
		}
	    }
        } catch (IOException ioe) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
        }
    }
    
    // HELPER METHOD FOR SAVING WORK
    private void saveWork(File selectedFile) throws IOException {
	// SAVE IT TO A FILE
	app.getFileComponent().saveData(app.getDataComponent(), selectedFile.getPath());
	
	// MARK IT AS SAVED
	currentWorkFile = selectedFile;
	saved = true;
	
	// TELL THE USER THE FILE HAS BEEN SAVED
	AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        dialog.show(props.getProperty(SAVE_COMPLETED_TITLE),props.getProperty(SAVE_COMPLETED_MESSAGE));
		    
	// AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
	// THE APPROPRIATE CONTROLS
	app.getGUI().updateToolbarControls(saved);	
    }
    
    /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     * 
     */
    public void handleExitRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
		PropertiesManager props = PropertiesManager.getPropertiesManager();
                dialog.show(props.getProperty(SAVE_ERROR_TITLE), props.getProperty(SAVE_ERROR_MESSAGE));
        }
    }

    /**
     * This helper method verifies that the user really wants to save their
     * unsaved work, which they might not want to do. Note that it could be used
     * in multiple contexts before doing other actions, like creating new
     * work, or opening another file. Note that the user will be
     * presented with 3 options: YES, NO, and CANCEL. YES means the user wants
     * to save their work and continue the other action (we return true to
     * denote this), NO means don't save the work but continue with the other
     * action (true is returned), CANCEL means don't save the work and don't
     * continue with the other action (false is returned).
     *
     * @return true if the user presses the YES option to save, true if the user
     * presses the NO option to not save, false if the user presses the CANCEL
     * option to not continue.
     */
    private boolean promptToSave() throws IOException {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// CHECK TO SEE IF THE CURRENT WORK HAS
	// BEEN SAVED AT LEAST ONCE
	
        // PROMPT THE USER TO SAVE UNSAVED WORK
	AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.show(props.getProperty(SAVE_UNSAVED_WORK_TITLE), props.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            // SAVE THE DATA FILE
            AppDataComponent dataManager = app.getDataComponent();
	    
	    if (currentWorkFile == null) {
		// PROMPT THE USER FOR A FILE NAME
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new ExtensionFilter(props.getProperty(WORK_FILE_EXT_DESC), props.getProperty(WORK_FILE_EXT)));

		File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
		if (selectedFile != null) {
		    saveWork(selectedFile);
		    saved = true;
		}
	    }
	    else {
		saveWork(currentWorkFile);
		saved = true;
	    }
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(AppYesNoCancelDialogSingleton.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }

    /**
     * This helper method asks the user for a file to open. The user-selected
     * file is then loaded and the GUI updated. Note that if the user cancels
     * the open process, nothing is done. If an error occurs loading the file, a
     * message is displayed, but nothing changes.
     */
    private void promptToOpen() {
	// WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());
                
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
    public void open2(){
        // WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
//        FileChooser fc = new FileChooser();
//        fc.setInitialDirectory(new File(PATH_WORK));
//	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        String yo = app.getGUI().get2().getText();
        File selectedFile = new File(app.getGUI().getP() + yo + "File");
        System.out.print(selectedFile.toPath());
        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                
                BorderPane appPane = app.getGUI().getAppPane();
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
    public void open(){
        // WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
//        FileChooser fc = new FileChooser();
//        fc.setInitialDirectory(new File(PATH_WORK));
//	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        String yo = app.getGUI().get1().getText();
        File selectedFile = new File(app.getGUI().getP() + yo + "File");
        System.out.print(selectedFile.toPath());
        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                
                
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
   
    public void open3(){
        // WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
//        FileChooser fc = new FileChooser();
//        fc.setInitialDirectory(new File(PATH_WORK));
//	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        String yo = app.getGUI().get3().getText();
        File selectedFile = new File(app.getGUI().getP() + yo + "File");
        System.out.print(selectedFile.toPath());
        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                
                BorderPane appPane = app.getGUI().getAppPane();
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
    public void open4(){
        // WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
//        FileChooser fc = new FileChooser();
//        fc.setInitialDirectory(new File(PATH_WORK));
//	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        String yo = app.getGUI().get4().getText();
        File selectedFile = new File(app.getGUI().getP() + yo + "File");
        System.out.print(selectedFile.toPath());
        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                
                BorderPane appPane = app.getGUI().getAppPane();
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
    public void open5(){
        // WE'LL NEED TO GET CUSTOMIZED STUFF WITH THIS
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
//        FileChooser fc = new FileChooser();
//        fc.setInitialDirectory(new File(PATH_WORK));
//	fc.setTitle(props.getProperty(LOAD_WORK_TITLE));
        String yo = app.getGUI().get5().getText();
        File selectedFile = new File(app.getGUI().getP() + yo + "File");
        System.out.print(selectedFile.toPath());
        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                // RESET THE WORKSPACE
		app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                
                // LOAD THE FILE INTO THE DATA
                
                BorderPane appPane = app.getGUI().getAppPane();
		// MAKE SURE THE WORKSPACE IS ACTIVATED
		app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                app.getFileComponent().loadData(app.getDataComponent(), selectedFile.getAbsolutePath());
                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                saved = true;
                app.getGUI().updateToolbarControls(saved);
            } catch (Exception e) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(LOAD_ERROR_TITLE), props.getProperty(LOAD_ERROR_MESSAGE));
            }
        }
    }
    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the course is changed in some way.
     */
    public void markFileAsNotSaved() {
        saved = false;
    }

    /**
     * Accessor method for checking to see if the current work has been saved
     * since it was last edited.
     *
     * @return true if the current work is saved to the file, false otherwise.
     */
    public boolean isSaved() {
        return saved;
    }
    public File getCurrentFile(){
        return currentWorkFile;
    }
}
