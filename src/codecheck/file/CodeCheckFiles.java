/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck.file;

import codecheck.CodeCheckApp;
import codecheck.data.CodeCheck;
import codecheck.data.CodeCheckData;
import codecheck.workspace.CodeCheckWorkspace;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author Usman
 */
public class CodeCheckFiles implements AppFileComponent {
    CodeCheckApp app;
    CodeCheckWorkspace work;
    static final String JSON_CODECHECK_TITLE = "title";
    
    public CodeCheckFiles(CodeCheckApp initApp){
        app = initApp;
        work = (CodeCheckWorkspace) app.getWorkspaceComponent();
    }

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
       CodeCheckData dataManager = (CodeCheckData) app.getDataComponent();
       
       JsonArrayBuilder ccArrayBuilder = Json.createArrayBuilder();
       JsonObjectBuilder ccTitleBuilder = Json.createObjectBuilder();
       ObservableList<CodeCheck> codechecks = dataManager.getCodeChecks();
       
       //for(CodeCheck codecheck : codechecks){
           JsonObject codecheckJson = (JsonObject) Json.createObjectBuilder()
                   .add(JSON_CODECHECK_TITLE, dataManager.getTitle()).build();
           
       //}
       JsonArray codecheckArray = ccArrayBuilder.build();
       JsonObject codecheckTitle = ccTitleBuilder.build();
       
       JsonObject dataManagerJSO = Json.createObjectBuilder()
               .add(JSON_CODECHECK_TITLE, dataManager.getTitle()).build();
       
         Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath  + ".json");
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();

    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        JsonObject json = loadJSONFile(filePath);
        CodeCheckData dataManager = (CodeCheckData) app.getDataComponent();
        dataManager.resetData();
        
        //JsonObject jsonCCObj = json.getJsonObject(filePath);
        
        String ccTitle = json.getString(JSON_CODECHECK_TITLE);
        
        
        // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
        //String codecheckTitle = dataManager.getTitle();
        
        
        
        String codecheckTitle = dataManager.getTitle();
        dataManager.setTitle(ccTitle);
        app.getGUI().getPrimaryStage().setTitle(ccTitle);
    }
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
