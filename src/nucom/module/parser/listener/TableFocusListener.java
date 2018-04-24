package nucom.module.parser.listener;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import nucom.module.parser.EntryPoint;
import nucom.module.parser.LineConstruct;

public class TableFocusListener implements Runnable
{
	private TableView<LineConstruct> TABLE_DATA = null;
	private TablePosition<LineConstruct, ?> LastFocus = null;
	
	public TableFocusListener(TableView<LineConstruct> TABLE_DATA)
	{
		this.TABLE_DATA = TABLE_DATA;
	}

	@Override
	public void run() 
	{
		while(!EntryPoint.Shutdown)
		{
			@SuppressWarnings("unchecked")
			TablePosition<LineConstruct, ?> Entry = TABLE_DATA.getFocusModel().getFocusedCell();
			if(LastFocus != Entry && Entry.getRow() != -1 && Entry.getColumn() != -1)
			{
				log("Focus Changed!");
				log(""+Entry.getColumn());
				log(""+Entry.getRow());
				LastFocus = Entry;	
			}
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				log(e);
			}
		}
		
	}
	
	public TablePosition<LineConstruct, ?> GetFocus()
	{
		return LastFocus;
	}
	
	private void log(String S)
	{
		System.out.println("[TFL]"+S);
	}

	private void log(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log(sw.toString());
	}
	
}
