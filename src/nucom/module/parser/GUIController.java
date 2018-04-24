package nucom.module.parser;

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
import nucom.module.parser.dommanager.DocumentManager;
import nucom.module.parser.filter.Filter;
import nucom.module.parser.listener.TableFocusListener;
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
	private TableFocusListener TFL = null;
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
		CHOICEBOX_VALUE = new CheckComboBox<String>();
		ROOT_TOOLBAR.getItems().add(CHOICEBOX_VALUE);
		for(Data D : Data.values())
		{
			CHOICEBOX_FIELD.getItems().add(D.toString());
		}
		CHOICEBOX_FIELD.getSelectionModel().select(0);
		
		for(Logic L : Logic.values())
		{
			CHOICEBOX_LOGIC.getItems().add(L.toString());
		}
		CHOICEBOX_LOGIC.getSelectionModel().select(0);
		
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
					if(!CHOICEBOX_VALUE.getItems().contains(S))
					{
						CHOICEBOX_VALUE.getItems().add(S);
					}
					
				}
				
			}
		});
		
	}
	
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
	
	private void InitTable()
	{
		for(Data D : Data.values())
		{
			TableColumn<LineConstruct, String> TC = new TableColumn<>(D.toString());
			TC.setCellValueFactory(new Callback<CellDataFeatures<LineConstruct,String>, ObservableValue<String>>() 
			{
			    @Override
			    public ObservableValue<String> call(CellDataFeatures<LineConstruct, String> data) 
			    {
			        return data.getValue().get(D);     
			    }
			});
			TABLE_DATA.getColumns().add(TC);
		}
	}
	
	
	private void InitLogo()
	{
		try
		{
			InputStream IS = null;
			IS = EntryPoint.class.getResourceAsStream("/nucom/module/parser/logo.png");
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
	
	public TableFocusListener GetTFL()
	{
		return TFL;
	}	

	@FXML
	public void SAVE_CSV_ACTION(ActionEvent AE)
	{
		log.debug("Saving CSV-File");
		
	}

	@FXML
	public void LOAD_CSV_ACTION(ActionEvent AE)
	{
		SAVE_BUTTON(false);
		log.debug("Loading CSV File");
		FC = new FileChooser();
		
		FC.setTitle("CSV wählen");
		File CSVFile = FC.showOpenDialog(EntryPoint.ROOT_STAGE);
		
		if(CSVFile != null && CSVFile.exists())
		{
			log.debug("File: " + CSVFile.getAbsolutePath());
			if (DM.LoadCSV(CSVFile))
			{
				log.debug("Loading Successful");
				SAVE_BUTTON(true);
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

	private void AppendFilter()
	{
		List<LineConstruct> FilteredList = new ArrayList<LineConstruct>();
		ClearGUI();
		if(Filters.size() == 0)
		{
			
			TABLE_DATA.getItems().addAll(FullList);
			return;
		}
		
		log.debug("Filter Size: " +Filters.size());
		log.debug("Full List Size: " + FullList.size());
		
		Boolean Found = true;
		
		for(LineConstruct LC: FullList)
		{
			Found = true;
			for(Filter F : Filters)
			{
				log.debug(F.getValues().toString() +" <==> " + LC.get(F.getD()).getValue());
				if(F.accepts(LC.get(F.getD()).getValue()))
				{
					log.debug("Filter Matched");
				}
				else
				{
					log.debug("Filter Not Matched");
					Found = false;
				}
			}
			if(Found)
			{
				log.debug("Adding new Item to Filtered List");
				FilteredList.add(LC);
			}
			else
			{
				
			}
		}
		
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

	public void ClearGUI()
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
