package nucom.module.parser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class EntryPoint extends Application {
	BorderPane BP_ROOT = null;
	FXMLLoader GUILOADER = null;
	GUIController GC = null;
	
	public static boolean Shutdown = false;
	
	public static Stage ROOT_STAGE = null;
	
	@Override
		public void start(Stage Root_Stage) {
		try {
						
			FXMLLoader GUILOADER = new FXMLLoader();
			
			BP_ROOT = GUILOADER.load(EntryPoint.class.getResourceAsStream("GUI.fxml"));
			GUILOADER.getController();
			
			GC = GUILOADER.<GUIController>getController();

			Root_Stage.setScene(new Scene(BP_ROOT));;
			Root_Stage.show();
			Root_Stage.setMaximized(true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		ROOT_STAGE = Root_Stage;
		
		ROOT_STAGE.setOnCloseRequest( event -> 
		{
			Shutdown = true;
			System.exit(0);
		});
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
