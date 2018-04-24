package nucom.module.parser.dommanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import nucom.module.parser.EntryPoint;
import nucom.module.parser.GUIController;
import nucom.module.parser.LineConstruct;
import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.Log;

public class DocumentManager 
{
	private File CSVFile = null;
	private TableView<LineConstruct> TABLE_DATA = null; 
	private GUIController GC = null;
	
	private List<String> Document = null;
	private Log log = null;
	
	public DocumentManager(GUIController GC)
	{
		log = new Log(this.getClass());
		this.TABLE_DATA = GC.getData();
		this.GC=GC;
	}

	public boolean LoadCSV(File CSVFile)
	{
		try
		{
			this.CSVFile = CSVFile;
			
			GC.ClearGUI();

			BufferedReader BR = new BufferedReader(new FileReader(CSVFile));
			
			Document = new ArrayList<String>();
			
			String LineRead = null;
			String[] Split =null;

			LineConstruct LC = null;
			
			Integer LineNo =0;

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
				if(First)
				{
					First = false;
					Integer Pos = 0;
					for(String Piece : Split)
					{
						try
						{
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
					LC = new LineConstruct();
					for(Entry<Data, Integer> Entry: Template.entrySet())
					{
						String S = Entry.getKey().toString();
						String output = S.substring(0, 1).toUpperCase() + S.substring(1);
						//log.debug("set"+output);
						Method M = LC.getClass().getMethod("set"+output, String.class);
						M.invoke(LC, Split[Entry.getValue()]);
					}
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
		try
		{			

			log.debug("Saving File");
			
			FileChooser Save_To = new FileChooser();
			
			Save_To.setTitle("CSV wählen");
			
			Save_To.setInitialFileName(CSVFile.getName());
			
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV-File", "*.csv");
			Save_To.setSelectedExtensionFilter(extFilter);
			
			
			File F = Save_To.showSaveDialog(EntryPoint.ROOT_STAGE);				
			
			if(F == null)
			{
				return;
			}
			
			if(!F.getName().contains(".")) {
				  F = new File(F.getAbsolutePath() + ".csv");
				}
			
			log.debug("Saving to File: " + F.getAbsolutePath());
			
			FileWriter FW = new FileWriter(F);
			for(String DocLine : Document)
			{
				FW.write(DocLine);
				FW.write(System.lineSeparator());
			}
			
			FW.close();
				
			}
		catch(Exception e)
		{
			log.EtoStringLog(e);
		}
	}
	

}
