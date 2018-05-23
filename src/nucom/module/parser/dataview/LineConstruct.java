package nucom.module.parser.dataview;

import java.text.SimpleDateFormat;
import java.util.Date;
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
		if(D.equals(Data.starttime))
		{
			try
			{
				SimpleDateFormat SDFNow = new SimpleDateFormat("\"dd.MM.yyyy HH:mm:ss\"");
				SimpleDateFormat SDFNew = new SimpleDateFormat("\"dd.MM.yyyy\"");
				
				Date SD = SDFNow.parse(Value);
				
				P.put(Data.starttimeformatted, SDFNew.format(SD));
				
				
			}
			catch(Exception e)
			{
				P.put(Data.starttimeformatted, "");
			}
		}
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
