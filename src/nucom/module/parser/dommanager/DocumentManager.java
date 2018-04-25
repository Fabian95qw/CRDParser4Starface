package nucom.module.parser.dommanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.control.TableView;
import nucom.module.parser.controller.GUIController;
import nucom.module.parser.dataview.LineConstruct;
import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.Log;

public class DocumentManager 
{
	private TableView<LineConstruct> TABLE_DATA = null; 
	private GUIController GC = null;
	
	private List<String> Document = null;
	private Log log = null;
	
	/**
	 * 
	 * @param GC ==> The active GUIController Instance, is required to write the Table_Data.
	 */
	
	public DocumentManager(GUIController GC)
	{
		log = new Log(this.getClass());
		this.TABLE_DATA = GC.getData();
		this.GC=GC;
	}

	
	/**
	 * 
	 * @param CSVFile ==> CSV-File to load
	 * @return ==> true, if loading was successful, false otherwise
	 */
	public boolean LoadCSV(File CSVFile)
	{
		try
		{			
			GC.Clear_DATA_TABLE();

			BufferedReader BR = new BufferedReader(new FileReader(CSVFile));
			
			Document = new ArrayList<String>();
			
			String LineRead = null;
			String[] Split =null;

			LineConstruct LC = null;
			
			Integer LineNo =0;

			//Read in the whole document first.
			while((LineRead = BR.readLine()) != null)
			{
				Document.add(LineRead);
			}
			
			BR.close();
			
			Boolean First = true;
			
			Map<Data, Integer> Template = new HashMap<Data, Integer>();
			
			for(String Line : Document)
			{
				log.debug("Working on: " +Line);
				Split = Line.split(";");
				//IF it's the First line, Convert Line Into a Teplate, of Data <--> Position
				if(First)
				{
					First = false;
					Integer Pos = 0;
					for(String Piece : Split)
					{
						try
						{
							//Remove " from the Input, in Order to convert it to the data enum
							Piece= Piece.replace("\"", "");
							Data D = Data.valueOf(Piece);
							Template.put(D, Pos);	
						}
						catch(Exception e)
						{
							log.debug("Invalid Data recieved!");
							log.EtoStringLog(e);
						}
						Pos++;
					}
				}
				else
				{
					//Try to Translate the Line into
					LC = new LineConstruct();
					for(Entry<Data, Integer> Entry: Template.entrySet())
					{
						LC.set(Entry.getKey(), Split[Entry.getValue()]);
					}
					//Add the Converted Data to the Table
					log.debug("Adding new Line to Table");
					TABLE_DATA.getItems().add(LC);
					LineNo++;
				}				
			}
		}
		catch(Exception e)
		{
			log.EtoStringLog(e);
			return false;
		}
		return true;
	}

	
	public void SaveCSV()
	{
		//TODO SAVING
	}
	

}
