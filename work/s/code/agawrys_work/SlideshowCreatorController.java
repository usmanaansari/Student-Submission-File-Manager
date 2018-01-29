package sc.workspace;

import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import sc.SlideshowCreatorApp;
import static sc.SlideshowCreatorProp.APP_PATH_WORK;
import static sc.SlideshowCreatorProp.INVALID_IMAGE_PATH_MESSAGE;
import static sc.SlideshowCreatorProp.INVALID_IMAGE_PATH_TITLE;
import sc.data.SlideshowCreatorData;
import java.util.ArrayList;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @author Agnieszka Gawrys
 * @version 1.0
 */
public class SlideshowCreatorController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    SlideshowCreatorApp app;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public SlideshowCreatorController(SlideshowCreatorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }
    
    // CONTROLLER METHOD THAT HANDLES ADDING A DIRECTORY OF IMAGES
    public void handleAnImageInDirectory(){
        try{
            
            FileChooser fileChooser = new FileChooser();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            fileChooser.setTitle("Chooser for Image File");
            fileChooser.setInitialDirectory(new File(props.getProperty(APP_PATH_WORK)));
            File image = fileChooser.showOpenDialog(app.getGUI().getWindow());
            String path = image.getPath();
            String fileName = image.getName();
            
            
            //adding to display file
            if (fileName.toLowerCase().endsWith(".png") ||
                        fileName.toLowerCase().endsWith(".jpg") ||
                        fileName.toLowerCase().endsWith(".gif")) {
                        String caption = "";
                        Image slideShowImage = loadImage(path);
                        int originalWidth = (int)slideShowImage.getWidth();
                        int originalHeight = (int)slideShowImage.getHeight();
                        SlideshowCreatorData data = (SlideshowCreatorData)app.getDataComponent();
                        data.addSlide(fileName, path, caption, originalWidth, originalHeight);
                        
            }
            app.getWorkspaceComponent().removeButtonEnable();
        }
        catch(Exception e){
            
        }
    }
    public void handleAddAllImagesInDirectory() {
        try {
            // ASK THE USER TO SELECT A DIRECTORY
            DirectoryChooser dirChooser = new DirectoryChooser();
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            dirChooser.setInitialDirectory(new File(props.getProperty(APP_PATH_WORK)));
            File dir = dirChooser.showDialog(app.getGUI().getWindow());
            if (dir != null) {
                File[] files = dir.listFiles();
                for (File f : files) {
                    String fileName = f.getName();
                    if (fileName.toLowerCase().endsWith(".png") ||
                            fileName.toLowerCase().endsWith(".jpg") ||
                            fileName.toLowerCase().endsWith(".gif")) {
                        String path = f.getPath();
                        String caption = "";
                        Image slideShowImage = loadImage(path);
                        int originalWidth = (int)slideShowImage.getWidth();
                        int originalHeight = (int)slideShowImage.getHeight();
                        SlideshowCreatorData data = (SlideshowCreatorData)app.getDataComponent();
                        data.addSlide(fileName, path, caption, originalWidth, originalHeight);
                    }
                }
            }
        }
        catch(MalformedURLException murle) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String title = props.getProperty(INVALID_IMAGE_PATH_TITLE);
            String message = props.getProperty(INVALID_IMAGE_PATH_MESSAGE);
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(title, message);
        }
    }
    
    // THIS HELPER METHOD LOADS AN IMAGE SO WE CAN SEE IT'S SIZE
    private Image loadImage(String imagePath) throws MalformedURLException {
	File file = new File(imagePath);
	URL fileURL = file.toURI().toURL();
	Image image = new Image(fileURL.toExternalForm());
	return image;
    }
    
}