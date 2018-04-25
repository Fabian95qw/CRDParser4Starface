package nucom.module.parser.dataview;

import java.util.Properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import nucom.module.parser.utility.EnumHelper.Data;

public class LineConstruct 
{
	Properties P = new Properties();
	
	/**
	 * Object for the Table View, Each LineConstruct is a Line of the tableview
	**/
	
	public LineConstruct()
	{

	}	
	
	public void set(Data D, String Value)
	{
		P.put(D, Value);
	}
	
	//Get the String out of the Property, and wrap it into a SimpleStringProperty, for use in the Tableview
	public ObservableValue<String> get(Data D) 
	{
		if(P.get(D) == null)
		{
			return new SimpleStringProperty("");
		}
		String S = (String) P.get(D);
		return new SimpleStringProperty(S);
	}


	
}
