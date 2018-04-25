package nucom.module.parser.entrypoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import nucom.module.parser.controller.GUIController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class EntryPoint extends Application 
{
	BorderPane BP_ROOT = null;
	FXMLLoader GUILOADER = null;
	GUIController GC = null;
	
	/*
	 * EntryPoint of the whole application 
	 * 
	 */
	
	public static Stage ROOT_STAGE = null;
	
	@Override
		public void start(Stage Root_Stage) {
		try {
						
			//Loading the GUI, and the GUIController
			FXMLLoader GUILOADER = new FXMLLoader();
			
			BP_ROOT = GUILOADER.load(EntryPoint.class.getResourceAsStream("/nucom/module/parser/controller/GUI.fxml"));
			GUILOADER.getController();
			
			GC = GUILOADER.<GUIController>getController();

			//Set the Scene and Maximized the Program
			Root_Stage.setScene(new Scene(BP_ROOT));
			Root_Stage.show();
			Root_Stage.setMaximized(true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		ROOT_STAGE = Root_Stage;
		
		//Exit any sub-threads if the Main Application Closes
		ROOT_STAGE.setOnCloseRequest( event -> 
		{
			System.exit(0);
		});
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
