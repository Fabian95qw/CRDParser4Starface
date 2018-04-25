package nucom.module.parser.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.CheckComboBox;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import nucom.module.parser.dataview.LineConstruct;
import nucom.module.parser.dommanager.DocumentManager;
import nucom.module.parser.entrypoint.EntryPoint;
import nucom.module.parser.filter.Filter;
import nucom.module.parser.utility.Log;
import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.EnumHelper.Logic;

public class GUIController
{	
		
	@FXML Button BUTTON_CSV_LOAD;
	@FXML Button BUTTON_CSV_SAVE;
	@FXML ToolBar ROOT_TOOLBAR;
	@FXML TableView<LineConstruct> TABLE_DATA;
	@FXML ImageView IMAGE_LOGO_NUCOM;
	@FXML ChoiceBox<String> CHOICEBOX_FIELD;
	@FXML ChoiceBox<String> CHOICEBOX_LOGIC;
	CheckComboBox<String> CHOICEBOX_VALUE;
	@FXML VBox VBOX_FILTERS;
	
	
	
	private FileChooser FC = null;
	private DocumentManager DM = null;
	private Log log = null;
	private List<LineConstruct> FullList=null;
	private List<Filter> Filters = null;
	
	public GUIController()
	{}

	@FXML
	protected void initialize() 
	{		
		
		log = new Log(this.getClass());
		Filters = new ArrayList<Filter>();
		log.debug("Initialized GUIController");
		DM = new DocumentManager(this);
		log.debug("Constructing Table Data");
		InitLogo();
		InitTable();
		InitChoicebox();
	}
	
	private void InitChoicebox()
	{
		// Put the whole Data enum into the choicebox
		CHOICEBOX_VALUE = new CheckComboBox<String>();
		ROOT_TOOLBAR.getItems().add(CHOICEBOX_VALUE);
		for(Data D : Data.values())
		{
			CHOICEBOX_FIELD.getItems().add(D.toString());
		}
		//Set the Selection to the First Item of the choicebox
		CHOICEBOX_FIELD.getSelectionModel().select(0);
		
		//Put the Logic values into it's choicebox
		for(Logic L : Logic.values())
		{
			CHOICEBOX_LOGIC.getItems().add(L.toString());
		}
		//Set the Selection to the First Item of the choicebox
		CHOICEBOX_LOGIC.getSelectionModel().select(0);
		
		//When the Choicebox_Field changes it item, refresh the Value Choicebox with the new values of the column
		CHOICEBOX_FIELD.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{

			@Override
			public void changed(ObservableValue<? extends String> value, String oldvalue, String newvalue) 
			{
				log.debug("Choice Changed");
				log.debug("Old value:" + oldvalue);
				log.debug("New Value:" + newvalue);
				Data D = Data.valueOf(newvalue);
				Clear_CHOICEBOX_VALUES();
				
				for(LineConstruct LC : TABLE_DATA.getItems())
				{
					String S = LC.get(D).getValue();
					//Check if Choicebox already contains item or not, so we don't add the same value multiple times.
					if(!CHOICEBOX_VALUE.getItems().contains(S))
					{
						CHOICEBOX_VALUE.getItems().add(S);
					}
					
				}
				
			}
		});
		
	}
	/**
	 * Clear Choicebox_Values
	 */
	private void Clear_CHOICEBOX_VALUES()
	{
		List<String> Entries = new ArrayList<String>();
		for(String Entry: CHOICEBOX_VALUE.getItems())
		{
			Entries.add(Entry);
		}
		
		for(String Entry: Entries)
		{
			CHOICEBOX_VALUE.getItems().remove(Entry);
		}
	}
	/**
	 *  Configure the CellValueFactory for each Column
	 */
	private void InitTable()
	{
		for(Data D : Data.values())
		{
			//Sets the Data as the name of each column
			TableColumn<LineConstruct, String> TC = new TableColumn<>(D.toString());
			TC.setCellValueFactory(new Callback<CellDataFeatures<LineConstruct,String>, ObservableValue<String>>() 
			{
			    @Override
			    public ObservableValue<String> call(CellDataFeatures<LineConstruct, String> data) 
			    {
			    	//Get the Value, based on the Data Enum
			        return data.getValue().get(D);     
			    }
			});
			TABLE_DATA.getColumns().add(TC);
		}
	}
	
	/**
	 * Add our Logo to the GUI
	 */
	private void InitLogo()
	{
		try
		{
			InputStream IS = null;
			IS = EntryPoint.class.getResourceAsStream("/nucom/module/parser/controller/logo.png");
			IMAGE_LOGO_NUCOM.setFitWidth(160.0);
			IMAGE_LOGO_NUCOM.setFitHeight(32.0);
			IMAGE_LOGO_NUCOM.setImage(new Image(IS, 160, 32, false, false));
			
			IS.close();
		}
		catch(Exception e)
		{
			log.EtoStringLog(e);
		}
	}
	
	public TableView<LineConstruct> getData()
	{
		return TABLE_DATA;
	}

	@FXML
	public void SAVE_CSV_ACTION(ActionEvent AE)
	{
		log.debug("Saving CSV-File");
		DM.SaveCSV();
	}

	@FXML
	public void LOAD_CSV_ACTION(ActionEvent AE)
	{
		//Load a .CSV
		SAVE_BUTTON(false);
		log.debug("Loading CSV File");
		FC = new FileChooser();
		
		FC.setTitle("CSV wählen");
		File CSVFile = FC.showOpenDialog(EntryPoint.ROOT_STAGE);
		
		//IF CSV is exists, load it with the Documentmanager
		if(CSVFile != null && CSVFile.exists())
		{
			log.debug("File: " + CSVFile.getAbsolutePath());
			if (DM.LoadCSV(CSVFile))
			{
				log.debug("Loading Successful");
				SAVE_BUTTON(true);
				//Put all Lineconstructs into a list, is required to reset to an original, if filters are applied/removed
				FullList = new ArrayList<LineConstruct>();
				for(LineConstruct LC : TABLE_DATA.getItems())
				{
					FullList.add(LC);
				}
				log.debug("List Size:" + TABLE_DATA.getItems().size());
			}
		}
		else
		{
			log.debug("Invalid or no File Chosen");
		}
		
	}
	
	@FXML
	public void ADD_FILTER_ACTION(ActionEvent AE)
	{
		log.debug("Adding new Filter");
		//Get the Selected Datafield, and Logic, and the Selected items from the GUI, and convert them into a Filter Object
		Data D = Data.valueOf(CHOICEBOX_FIELD.getSelectionModel().getSelectedItem());
		Logic L = Logic.valueOf(CHOICEBOX_LOGIC.getSelectionModel().getSelectedItem());
		List<String> FilterItems = new ArrayList<String>();
		
		for(String S : CHOICEBOX_VALUE.getItems())
		{
			if(CHOICEBOX_VALUE.getItemBooleanProperty(S).getValue())
			{
				FilterItems.add(S);
			}
		}
		log.debug("Adding Filter:" + D.toString() +" with Values: " + FilterItems.toString());
		
		Filter F = new Filter(D, FilterItems, L);
		Button B = new Button(F.toString());
		
		VBOX_FILTERS.getChildren().add(B);
		
		//Put a Button into the GUI, which will remove the filter, and the button itself
		B.setOnAction(new EventHandler<ActionEvent>() 
		{

			@Override
			public void handle(ActionEvent AE) 
			{
				VBOX_FILTERS.getChildren().remove(B);
				Filters.remove(F);
				log.debug("Removing Filter:" + F.toString());
				log.debug("Total Filters:" + Filters.size());
				AppendFilter();
			}
		});
		
		Filters.add(F);
		AppendFilter();
	}

	/**
	 * Appends the Filter's which were set
	 */
	private void AppendFilter()
	{
		List<LineConstruct> FilteredList = new ArrayList<LineConstruct>();
		Clear_DATA_TABLE();
		//If no Filter is left, put the original list back into the Dataview
		if(Filters.size() == 0)
		{
			
			TABLE_DATA.getItems().addAll(FullList);
			return;
		}
		
		log.debug("Filter Size: " +Filters.size());
		log.debug("Full List Size: " + FullList.size());
		
		Boolean Matched = true;
		//Check each Lineconstruct against all filter
		for(LineConstruct LC: FullList)
		{
			Matched = true;
			for(Filter F : Filters)
			{
				
				if(F.accepts(LC.get(F.getD()).getValue()))
				{
					log.debug("Filter Matched");
				}
				else
				{
					log.debug("Filter Not Matched");
					Matched = false;
				}
			}
			//If the Line Matched all filters, put it into the filtered list.
			if(Matched)
			{
				log.debug("Adding new Item to Filtered List");
				FilteredList.add(LC);
			}
			else
			{
				
			}
		}
		//Put the FIltered List into the dataview
		TABLE_DATA.getItems().addAll(FilteredList);		
	}
	
	private void SAVE_BUTTON(boolean Enable)
	{
		Platform.runLater(new Runnable()
		{

			@Override
			public void run() 
			{
				BUTTON_CSV_SAVE.setDisable(!Enable);				
			}
		});
	}

	/**
	 * Clears the Data Table from all Data
	 */
	public void Clear_DATA_TABLE()
	{
		List<LineConstruct> Entries = new ArrayList<LineConstruct>();
		for(LineConstruct Entry: TABLE_DATA.getItems())
		{
			Entries.add(Entry);
		}
		
		for(LineConstruct Entry: Entries)
		{
			TABLE_DATA.getItems().remove(Entry);
		}
	
	}
	

}
