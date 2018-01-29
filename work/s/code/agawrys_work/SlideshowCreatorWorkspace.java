package sc.workspace;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import sc.SlideshowCreatorApp;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import properties_manager.PropertiesManager;
import static sc.SlideshowCreatorProp.ADD_ALL_IMAGES_BUTTON_TEXT;
import static sc.SlideshowCreatorProp.ADD_IMAGE_BUTTON_TEXT;
import static sc.SlideshowCreatorProp.CAPTION_PROMPT_TEXT;
import static sc.SlideshowCreatorProp.CURRENT_HEIGHT_COLUMN_TEXT;
import static sc.SlideshowCreatorProp.CURRENT_HEIGHT_PROMPT_TEXT;
import static sc.SlideshowCreatorProp.CURRENT_WIDTH_COLUMN_TEXT;
import static sc.SlideshowCreatorProp.CURRENT_WIDTH_PROMPT_TEXT;
import static sc.SlideshowCreatorProp.FILE_NAME_COLUMN_TEXT;
import static sc.SlideshowCreatorProp.FILE_NAME_PROMPT_TEXT;
import static sc.SlideshowCreatorProp.ORIGINAL_HEIGHT_PROMPT_TEXT;
import static sc.SlideshowCreatorProp.ORIGINAL_WIDTH_PROMPT_TEXT;
import static sc.SlideshowCreatorProp.PATH_PROMPT_TEXT;
import static sc.SlideshowCreatorProp.REMOVE_IMAGE_BUTTON_TEXT;
import static sc.SlideshowCreatorProp.UPDATE_BUTTON_TEXT;
import sc.data.Slide;
import sc.data.SlideshowCreatorData;
import static sc.style.SlideshowCreatorStyle.CLASS_EDIT_BUTTON;
import static sc.style.SlideshowCreatorStyle.CLASS_EDIT_SLIDER;
import static sc.style.SlideshowCreatorStyle.CLASS_EDIT_TEXT_FIELD;
import static sc.style.SlideshowCreatorStyle.CLASS_PROMPT_LABEL;
import static sc.style.SlideshowCreatorStyle.CLASS_SLIDES_TABLE;
import static sc.style.SlideshowCreatorStyle.CLASS_UPDATE_BUTTON;

/**
 * This class serves as the workspace component for the TA Manager application.
 * It provides all the user interface controls in the workspace area.
 *
 * @author Richard McKenna
 * @author Agnieszka Gawrys
 */
public class SlideshowCreatorWorkspace extends AppWorkspaceComponent {

    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    SlideshowCreatorApp app;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    SlideshowCreatorController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    HBox editImagesToolbar;
    Button addAllImagesInDirectoryButton;
    Button addImageButton;
    Button removeImageButton;

    // FOR THE SLIDES TABLE
    ScrollPane slidesTableScrollPane;
    TableView<Slide> slidesTableView;
    TableColumn<Slide, StringProperty> fileNameColumn;
    TableColumn<Slide, IntegerProperty> currentWidthColumn;
    TableColumn<Slide, IntegerProperty> currentHeightColumn;

    // THE EDIT PANE
    GridPane editPane;
    Label fileNamePromptLabel;
    TextField fileNameTextField;
    Label pathPromptLabel;
    TextField pathTextField;
    Label captionPromptLabel;
    TextField captionTextField;
    Label originalWidthPromptLabel;
    TextField originalWidthTextField;
    Label originalHeightPromptLabel;
    TextField originalHeightTextField;
    Label currentWidthPromptLabel;
    Slider currentWidthSlider;
    Label currentHeightPromptLabel;
    Slider currentHeightSlider;
    Button updateButton;

    /**
     * The constructor initializes the user interface for the workspace area of
     * the application.
     */
    public SlideshowCreatorWorkspace(SlideshowCreatorApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // LAYOUT THE APP
        initLayout();

        // HOOK UP THE CONTROLLERS
        //edit slider values here
        initSliders();
        initControllers();
        textFieldControllers();

        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();
        initDisableButtons();
        
    }

    private void initLayout() {
        // WE'LL USE THIS TO GET UI TEXT
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // FIRST MAKE ALL THE COMPONENTS
        editImagesToolbar = new HBox();
        addAllImagesInDirectoryButton = new Button(props.getProperty(ADD_ALL_IMAGES_BUTTON_TEXT));
        addImageButton = new Button(props.getProperty(ADD_IMAGE_BUTTON_TEXT));
        removeImageButton = new Button(props.getProperty(REMOVE_IMAGE_BUTTON_TEXT));
        slidesTableScrollPane = new ScrollPane();
        slidesTableView = new TableView();
        fileNameColumn = new TableColumn(props.getProperty(FILE_NAME_COLUMN_TEXT));
        currentWidthColumn = new TableColumn(props.getProperty(CURRENT_WIDTH_COLUMN_TEXT));
        currentHeightColumn = new TableColumn(props.getProperty(CURRENT_HEIGHT_COLUMN_TEXT));
        editPane = new GridPane();
        fileNamePromptLabel = new Label(props.getProperty(FILE_NAME_PROMPT_TEXT));
        fileNameTextField = new TextField();
        pathPromptLabel = new Label(props.getProperty(PATH_PROMPT_TEXT));
        pathTextField = new TextField();
        captionPromptLabel = new Label(props.getProperty(CAPTION_PROMPT_TEXT));
        captionTextField = new TextField();
        originalWidthPromptLabel = new Label(props.getProperty(ORIGINAL_WIDTH_PROMPT_TEXT));
        originalWidthTextField = new TextField();
        originalHeightPromptLabel = new Label(props.getProperty(ORIGINAL_HEIGHT_PROMPT_TEXT));
        originalHeightTextField = new TextField();
        currentWidthPromptLabel = new Label(props.getProperty(CURRENT_WIDTH_PROMPT_TEXT));
        currentWidthSlider = new Slider();
        currentHeightPromptLabel = new Label(props.getProperty(CURRENT_HEIGHT_PROMPT_TEXT));
        currentHeightSlider = new Slider();
        updateButton = new Button(props.getProperty(UPDATE_BUTTON_TEXT));

        // ARRANGE THE TABLE
        fileNameColumn = new TableColumn(props.getProperty(FILE_NAME_COLUMN_TEXT));
        currentWidthColumn = new TableColumn(props.getProperty(CURRENT_WIDTH_COLUMN_TEXT));
        currentHeightColumn = new TableColumn(props.getProperty(CURRENT_HEIGHT_COLUMN_TEXT));
        slidesTableView.getColumns().add(fileNameColumn);
        slidesTableView.getColumns().add(currentWidthColumn);
        slidesTableView.getColumns().add(currentHeightColumn);
        fileNameColumn.prefWidthProperty().bind(slidesTableView.widthProperty().divide(2));
        currentWidthColumn.prefWidthProperty().bind(slidesTableView.widthProperty().divide(4));
        currentHeightColumn.prefWidthProperty().bind(slidesTableView.widthProperty().divide(4));
        fileNameColumn.setCellValueFactory(
                new PropertyValueFactory<Slide, StringProperty>("fileName")
        );
        currentWidthColumn.setCellValueFactory(
                new PropertyValueFactory<Slide, IntegerProperty>("currentWidth")
        );
        currentHeightColumn.setCellValueFactory(
                new PropertyValueFactory<Slide, IntegerProperty>("CurrentHeight")
        );
        // HOOK UP THE TABLE TO THE DATA
        SlideshowCreatorData data = (SlideshowCreatorData) app.getDataComponent();
        ObservableList<Slide> model = data.getSlides();
        slidesTableView.setItems(model);

        // THEM ORGANIZE THEM
        editImagesToolbar.getChildren().add(addAllImagesInDirectoryButton);
        editImagesToolbar.getChildren().add(addImageButton);
        editImagesToolbar.getChildren().add(removeImageButton);
        slidesTableScrollPane.setContent(slidesTableView);
        editPane.add(fileNamePromptLabel, 0, 0);
        editPane.add(fileNameTextField, 1, 0);
        editPane.add(pathPromptLabel, 0, 1);
        editPane.add(pathTextField, 1, 1);
        editPane.add(captionPromptLabel, 0, 2);
        editPane.add(captionTextField, 1, 2);
        editPane.add(originalWidthPromptLabel, 0, 3);
        editPane.add(originalWidthTextField, 1, 3);
        editPane.add(originalHeightPromptLabel, 0, 4);
        editPane.add(originalHeightTextField, 1, 4);
        editPane.add(currentWidthPromptLabel, 0, 5);
        editPane.add(currentWidthSlider, 1, 5);
        editPane.add(currentHeightPromptLabel, 0, 6);
        editPane.add(currentHeightSlider, 1, 6);
        editPane.add(updateButton, 0, 7);

        // DISABLE THE DISPLAY TEXT FIELDS
        fileNameTextField.setDisable(true);
        pathTextField.setDisable(true);
        originalWidthTextField.setDisable(true);
        originalHeightTextField.setDisable(true);
        updateButton.setDisable(true);
        captionTextField.setDisable(true);
        // AND THEN PUT EVERYTHING INSIDE THE WORKSPACE
        app.getGUI().getTopToolbarPane().getChildren().add(editImagesToolbar);
        BorderPane workspaceBorderPane = new BorderPane();
        workspaceBorderPane.setCenter(slidesTableScrollPane);
        slidesTableScrollPane.setFitToWidth(true);
        slidesTableScrollPane.setFitToHeight(true);
        workspaceBorderPane.setRight(editPane);

        // AND SET THIS AS THE WORKSPACE PANE
        workspace = workspaceBorderPane;
    }

    private void initControllers() {
        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new SlideshowCreatorController(app);

        addImageButton.setOnAction(e -> {
            controller.handleAnImageInDirectory();
            slidesTableView.requestFocus();
            slidesTableView.getSelectionModel().clearAndSelect(slidesTableView.getItems().size() - 1);
            slidesTableView.getFocusModel().focus(slidesTableView.getItems().size() - 1);
            app.getGUI().enableSaveButton();

        });
        addAllImagesInDirectoryButton.setOnAction(e -> {
            controller.handleAddAllImagesInDirectory();
            app.getGUI().enableSaveButton();

        });
        removeImageButton.setOnMouseClicked(e -> {
            slidesTableView.getItems().remove(slidesTableView.getSelectionModel().getSelectedItem());
            removeButtonEnable();
            app.getGUI().enableSaveButton();
        });

    }

    private void textFieldControllers() {
        //cant be on enter. only on update

        captionTextField.setOnKeyReleased(e -> {
            Slide selectedItem = slidesTableView.getSelectionModel().getSelectedItem();
            selectedItem.setNewCaption(captionTextField.getText());
            if (!selectedItem.getCaption().equals(selectedItem.getNewCaption())) {
                updateButton.setDisable(false);
                app.getGUI().enableSaveButton();
            }

        });

        slidesTableView.getSelectionModel().selectedItemProperty().addListener((slides, old, newSlide) -> {
            captionTextField.setDisable(false);
            captionTextField.setText(newSlide.getCaption());
            pathTextField.setText(newSlide.getPath());
            fileNameTextField.setText(newSlide.getFileName());
            originalWidthTextField.setText(newSlide.getOriginalWidth().toString());
            originalHeightTextField.setText(newSlide.getOriginalHeight().toString());
            currentWidthSlider.setValue(newSlide.getCurrentWidth());
            currentHeightSlider.setValue(newSlide.getCurrentHeight());

        });
        currentWidthSlider.valueProperty().addListener((value, old, newValue) -> {
            if (currentWidthSlider.getValue() != slidesTableView.getSelectionModel().getSelectedItem().getCurrentWidth()) {
                updateButton.setDisable(false);
                app.getGUI().enableSaveButton();
            }
        });
        currentHeightSlider.valueProperty().addListener((value, old, newValue) -> {
            if (currentHeightSlider.getValue() != slidesTableView.getSelectionModel().getSelectedItem().getCurrentHeight()) {
                updateButton.setDisable(false);
                app.getGUI().enableSaveButton();
            }
        });

        updateButton.setOnMouseClicked(e -> {
            slidesTableView.getSelectionModel().getSelectedItem().setCaption(slidesTableView.getSelectionModel().getSelectedItem().getNewCaption());
            slidesTableView.getSelectionModel().getSelectedItem().setCurrentWidth((int) currentWidthSlider.getValue());
            slidesTableView.getSelectionModel().getSelectedItem().setCurrentHeight((int) currentHeightSlider.getValue());
            updateButton.setDisable(true);
        });

    }

    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    private void initStyle() {
        editImagesToolbar.getStyleClass().add(CLASS_BORDERED_PANE);
        addAllImagesInDirectoryButton.getStyleClass().add(CLASS_EDIT_BUTTON);
        addImageButton.getStyleClass().add(CLASS_EDIT_BUTTON);
        removeImageButton.getStyleClass().add(CLASS_EDIT_BUTTON);

        // THE SLIDES TABLE
        slidesTableView.getStyleClass().add(CLASS_SLIDES_TABLE);
        for (TableColumn tc : slidesTableView.getColumns()) {
            tc.getStyleClass().add(CLASS_SLIDES_TABLE);
        }

        editPane.getStyleClass().add(CLASS_BORDERED_PANE);
        fileNamePromptLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        fileNameTextField.getStyleClass().add(CLASS_EDIT_TEXT_FIELD);
        pathPromptLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        pathTextField.getStyleClass().add(CLASS_EDIT_TEXT_FIELD);
        captionPromptLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        captionTextField.getStyleClass().add(CLASS_EDIT_TEXT_FIELD);
        originalWidthPromptLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        originalWidthTextField.getStyleClass().add(CLASS_EDIT_TEXT_FIELD);
        originalHeightPromptLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        originalHeightTextField.getStyleClass().add(CLASS_EDIT_TEXT_FIELD);
        currentWidthPromptLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        currentWidthSlider.getStyleClass().add(CLASS_EDIT_SLIDER);
        currentHeightPromptLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        currentHeightSlider.getStyleClass().add(CLASS_EDIT_SLIDER);
        updateButton.getStyleClass().add(CLASS_UPDATE_BUTTON);
    }

    @Override
    public void resetWorkspace() {

    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {

    }

    private void initSliders() {
        currentWidthSlider.setMin(0);
        currentWidthSlider.setMax(1000);
        currentWidthSlider.setShowTickLabels(true);
        currentWidthSlider.setShowTickMarks(true);
        currentWidthSlider.setMajorTickUnit(200);
        currentWidthSlider.setBlockIncrement(200);

        currentHeightSlider.setMin(0);
        currentHeightSlider.setMax(1000);
        currentHeightSlider.setShowTickLabels(true);
        currentHeightSlider.setShowTickMarks(true);
        currentHeightSlider.setMajorTickUnit(200);
        currentHeightSlider.setBlockIncrement(200);
    }
    @Override
    public void initDisableButtons(){
        addImageButton.setDisable(true);
        removeImageButton.setDisable(true);
        addAllImagesInDirectoryButton.setDisable(true);
    }
    @Override
    public void enableAddButtons(){
        addImageButton.setDisable(false);
        //removeImageButton.setDisable(false);
        addAllImagesInDirectoryButton.setDisable(false);
    }
    @Override
    public void removeButtonEnable(){
        if(slidesTableView.getItems().size() > 0){
            removeImageButton.setDisable(false);
            currentWidthSlider.setDisable(false);
            currentHeightSlider.setDisable(false);
        }
        if(slidesTableView.getItems().isEmpty()==true){
            removeImageButton.setDisable(true);
            captionTextField.setDisable(true);
            currentWidthSlider.setDisable(true);
            currentHeightSlider.setDisable(true);
        }
    }

}
