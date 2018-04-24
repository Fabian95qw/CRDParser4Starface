package nucom.module.parser.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log 
{
	private String Name = "";
	private SimpleDateFormat SDF = null;
	public Log(Class<?> C)
	{
		Name = C.getSimpleName();
		SDF = new SimpleDateFormat("HH:mm:ss");
	}
	
	public void debug(String S)
	{
		System.out.println("["+SDF.format(new Date())+"]"+"["+Name+"]"+S);
	}
	
	public void EtoStringLog(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		debug(sw.toString()); //
	}
}
