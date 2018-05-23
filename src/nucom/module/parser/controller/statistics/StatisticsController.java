package nucom.module.parser.controller.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.controlsfx.control.CheckComboBox;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import nucom.module.parser.controller.GUIController;
import nucom.module.parser.dataview.LineConstruct;
import nucom.module.parser.filter.Filter;
import nucom.module.parser.utility.Log;
import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.EnumHelper.Logic;
import nucom.module.parser.utility.EnumHelper.StatisticLogic;
import nucom.module.parser.utility.EnumHelper.StatisticTypes;

public class StatisticsController
{	
	private Log log = null;
	private GUIController GC = null;
	
	@FXML BorderPane ROOT_PANE;
	@FXML AnchorPane CHART_ROOT;
	@FXML ChoiceBox<String> CHOICEBOX_CHART_TYPE;
	@FXML ChoiceBox<String> CHOCIEBOX_AXIS1;
	@FXML ChoiceBox<String> CHOCIEBOX_AXIS2;
	@FXML VBox VBOX_FILTERS;
	@FXML ChoiceBox<String> CHOICEBOX_FUNCTION;
	@FXML ChoiceBox<String> CHOICEBOX_LOGIC;
	@FXML ChoiceBox<String> CHOICEBOX_FIELD;
	@FXML TextField TEXTFIELD_VALUE;
	@FXML ToolBar ROOT_TOOLBAR;
	private CheckComboBox<String> CHOICEBOX_VALUE;
	
	Map<Data, List<Filter>> Filters = null;
	
	public StatisticsController()
	{}

	@FXML
	protected void initialize() 
	{		
		log = new Log(this.getClass());
		InitChoicebox();
		InitResizer();
		Filters = new HashMap<Data, List<Filter>>();
	}
	
	private void InitResizer()
	{
		ROOT_PANE.widthProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) 
			{
				log.debug("Width Changed!");
				log.debug("From: " + oldValue.doubleValue() +" to: " + newValue.doubleValue());
				if(CHART_ROOT.getChildren().size() > 0)
				{
					Chart C = (Chart) CHART_ROOT.getChildren().get(0);
					C.setPrefWidth(CHART_ROOT.getWidth());
				}
				
				
			}
		});
		
		ROOT_PANE.heightProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) 
			{			
				log.debug("Height Changed!");
				log.debug("From: " + oldValue.doubleValue() +" to: " + newValue.doubleValue());
				if(CHART_ROOT.getChildren().size() > 0)
				{
					Chart C = (Chart) CHART_ROOT.getChildren().get(0);
					C.setPrefHeight(newValue.doubleValue()-40);
				}
			}
		});
	}
	
	private void InitChoicebox()
	{
		for(StatisticTypes ST: StatisticTypes.values())
		{
			CHOICEBOX_CHART_TYPE.getItems().add(ST.toString());
		}
		
		CHOICEBOX_CHART_TYPE.getSelectionModel().select(0);
		
		CHOICEBOX_CHART_TYPE.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{

			@Override
			public void changed(ObservableValue<? extends String> value, String oldValue, String newValue) 
			{
				log.debug("Choice Changed");
				log.debug("Old value:" + oldValue);
				log.debug("New Value:" + newValue);
				
				SwitchStatistics();
			}
		});

		for(Data D : Data.values())
		{
			CHOCIEBOX_AXIS1.getItems().add(D.toString());
		}
		//Set the Selection to the First Item of the choicebox
		CHOCIEBOX_AXIS1.getSelectionModel().select(0);
		
		CHOCIEBOX_AXIS1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{

			@Override
			public void changed(ObservableValue<? extends String> value, String oldValue, String newValue) 
			{
				log.debug("Choice Changed");
				log.debug("Old value:" + oldValue);
				log.debug("New Value:" + newValue);
				
				SwitchStatistics();
			}
		});
		
		for(Data D : Data.values())
		{
			CHOCIEBOX_AXIS2.getItems().add(D.toString());
		}
		//Set the Selection to the First Item of the choicebox
		CHOCIEBOX_AXIS2.getSelectionModel().select(0);
		
		CHOCIEBOX_AXIS2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
		{

			@Override
			public void changed(ObservableValue<? extends String> value, String oldValue, String newValue) 
			{
				log.debug("Choice Changed");
				log.debug("Old value:" + oldValue);
				log.debug("New Value:" + newValue);
				
				SwitchStatistics();
			}
		});
		
		CHOICEBOX_VALUE = new CheckComboBox<String>();
		ROOT_TOOLBAR.getItems().add(CHOICEBOX_VALUE);
		ROOT_TOOLBAR.getItems().remove(TEXTFIELD_VALUE);
		
		for(Data D : Data.values())
		{
			CHOICEBOX_FIELD.getItems().add(D.toString());
		}
		CHOICEBOX_FIELD.getSelectionModel().select(0);
		
		for(StatisticLogic SL : StatisticLogic.values())
		{
			CHOICEBOX_FUNCTION.getItems().add(SL.toString());
		}
		CHOICEBOX_FUNCTION.getSelectionModel().select(0);
		
		for(Logic L : Logic.values())
		{
			CHOICEBOX_LOGIC.getItems().add(L.toString());
		}
		CHOICEBOX_LOGIC.getSelectionModel().select(0);
		
		CHOICEBOX_LOGIC.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
		{

			@Override
			public void changed(ObservableValue<? extends String> value, String oldvalue, String newvalue) 
			{
				log.debug("Choice Changed");
				log.debug("Old value:" + oldvalue);
				log.debug("New Value:" + newvalue);
				
				Logic L = Logic.valueOf(newvalue);
				
				switch(L)
				{
				case EQUALS:
				case EQUALSNOT:
					ROOT_TOOLBAR.getItems().remove(TEXTFIELD_VALUE);
					if(!ROOT_TOOLBAR.getItems().contains(CHOICEBOX_VALUE))
					{
						ROOT_TOOLBAR.getItems().add(CHOICEBOX_VALUE);
					}
					break;
				case LIKE:
				case LIKENOT:
				case GREATER_THAN:
				case LESS_THAN:
					ROOT_TOOLBAR.getItems().remove(CHOICEBOX_VALUE);
					if(!ROOT_TOOLBAR.getItems().contains(TEXTFIELD_VALUE))
					{
						ROOT_TOOLBAR.getItems().add(TEXTFIELD_VALUE);
					}
					break;
				}
				
			}
		});
		
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
				
				for(LineConstruct LC : GC.getData().getItems())
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
	
	public void SetGUIController(GUIController GC)
	{
		this.GC=GC;
	}
	
	private void SwitchStatistics()
	{
		StatisticTypes ST = StatisticTypes.valueOf(CHOICEBOX_CHART_TYPE.getSelectionModel().getSelectedItem());
		ClearStaticistics();
		switch(ST)
		{
		case Line:
		{
			LineChart<String, Number> LC = new LineChart<>(new CategoryAxis(), new NumberAxis());
			
			List<XYChart.Series<String, Number>> SeriesList = new ArrayList<XYChart.Series<String, Number>>();
			
			for(Entry<String, Integer> Entry : DataasMap().entrySet())
			{
				XYChart.Series<String, Number> S = new XYChart.Series<String, Number>();
				S.setName(Entry.getKey());
				log.debug("Adding Serie: " + Entry.getKey() +" ==> " + Entry.getValue());
				S.getData().add(new XYChart.Data<String, Number>(Entry.getKey(), Entry.getValue()));
				SeriesList.add(S);
			}
			
			LC.getData().addAll(SeriesList);
			
			CHART_ROOT.getChildren().add(LC);
			LC.setPrefWidth(CHART_ROOT.getWidth());		
			LC.setPrefHeight(CHART_ROOT.getHeight());
		}
			break;
		case NONE:
			break;
		case Pie:
		{
			PieChart PC = new PieChart();
			ObservableList<PieChart.Data> PCD = FXCollections.observableArrayList();
						
			for(Entry<String, Integer> Entry : DataasMap().entrySet())
			{
				PCD.add(new PieChart.Data(Entry.getKey(), Entry.getValue()));
			}
			
			PC.setData(PCD);
			PC.getData().forEach(data -> 
				data.nameProperty().bind
				(	
						Bindings.concat(data.getName(), " ", data.pieValueProperty().getValue().intValue())
				)
			);
			CHART_ROOT.getChildren().add(PC);
			PC.setPrefWidth(CHART_ROOT.getWidth());		
			PC.setPrefHeight(CHART_ROOT.getHeight());		
		}
			break;
		case Stacked:
			StackedBarChart<String, Number> SC = new StackedBarChart<>(new CategoryAxis(), new NumberAxis());
			
			List<XYChart.Series<String, Number>> SeriesList = new ArrayList<XYChart.Series<String, Number>>();
			
			for(Entry<String, Integer> Entry : DataasMap().entrySet())
			{
				XYChart.Series<String, Number> S = new XYChart.Series<String, Number>();
				S.setName(Entry.getKey());
				log.debug("Adding Serie: " + Entry.getKey() +" ==> " + Entry.getValue());
				S.getData().add(new XYChart.Data<String, Number>(Entry.getKey(), Entry.getValue()));
				SeriesList.add(S);
			}
			
			SC.getData().addAll(SeriesList);
			
			CHART_ROOT.getChildren().add(SC);
			SC.setPrefWidth(CHART_ROOT.getWidth());		
			SC.setPrefHeight(CHART_ROOT.getHeight());		
			break;
		default:
			break;
		}
	}
	
	private Map<String, Integer> DataasMap()
	{
		Data D = Data.valueOf(CHOCIEBOX_AXIS1.getSelectionModel().getSelectedItem());
				
		Map<String, Integer> DataChart = new HashMap<String, Integer>();
		
		Map<String, Integer> DataChartFinal = new HashMap<String, Integer>();
		
		for(LineConstruct LC : GC.getData().getItems())
		{
			if(DataChart.get(LC.get(D).getValue()) != null)
			{
				Integer I = DataChart.get(LC.get(D).getValue());
				I++;
				DataChart.put(LC.get(D).getValue(), I);
			}
			else
			{
				DataChart.put(LC.get(D).getValue(), 1);
			}
		}
		
		if(Filters.size() > 0 && Filters.get(D) != null && Filters.get(D).size() > 0)
		{	
			
			log.debug("Appending Filters");
			Outerloop : for(Entry<String, Integer> DC : DataChart.entrySet())
			{
				for(Filter SF : Filters.get(D))
				{
					switch(SF.getSL())
					{
					case MERGE_WHERE:
						if(SF.accepts(DC.getKey()))
						{
							//String S = SF.getValues().toString();
							String S = SF.toChartString();
							if(DataChartFinal.get(S) != null)
							{
								Integer I = DataChartFinal.get(S);
								I = I + DC.getValue();
								DataChartFinal.put(S , I);
							}
							else
							{
								DataChartFinal.put(S, 1);
							}
							continue Outerloop;
						}
						break;
					default:
						break;

					}
				}
				log.debug("Filter is not valid for: "+ DC.getKey());
				DataChartFinal.put(DC.getKey(), DC.getValue());
			}				
		}
		else
		{
			log.debug("No Filters Found");
			DataChartFinal = DataChart;
		}
		
		
		return DataChartFinal;
	}
	
	private void ClearStaticistics()
	{
		List<Node> Children = new ArrayList<Node>();
		
		for(Node Entry : CHART_ROOT.getChildren())
		{
			Children.add(Entry);
		}
		CHART_ROOT.getChildren().removeAll(Children);
	}
	
	@FXML
	public void ADD_FILTER_ACTION(ActionEvent AE)
	{
		log.debug("Adding Filter");
		
		StatisticLogic SL = StatisticLogic.valueOf(CHOICEBOX_FUNCTION.getSelectionModel().getSelectedItem());
		Logic L = Logic.valueOf(CHOICEBOX_LOGIC.getSelectionModel().getSelectedItem());
		Data D = Data.valueOf(CHOICEBOX_FIELD.getSelectionModel().getSelectedItem());
		
		List<String> FilterItems = new ArrayList<String>();
		
		switch(L)
		{
		case EQUALS:
		case EQUALSNOT:
			for(String S : CHOICEBOX_VALUE.getItems())
			{
				if(CHOICEBOX_VALUE.getItemBooleanProperty(S).getValue())
				{
					FilterItems.add(S);
				}
			}
			break;
		case LIKE:
		case LIKENOT:
		case GREATER_THAN:
		case LESS_THAN:
			FilterItems.add(TEXTFIELD_VALUE.getText());
			break;
		
		}
		
		log.debug("Adding Filter:" + D.toString() +" with Values: " + FilterItems.toString());
		
		Filter F = new Filter(D, FilterItems, L, SL);
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
		
		if(Filters.get(D) == null)
		{
			Filters.put(D, new ArrayList<Filter>());
		}
		Filters.get(D).add(F);
		AppendFilter();
	}
	
	private void AppendFilter()
	{
		SwitchStatistics();
	}
	
}
