package nucom.module.parser.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log 
{
	private String Name = "";
	private SimpleDateFormat SDF = null;
	
	/**
	 * 
	 * @param The Log uses this Classname when printing out logs
	 */
	
	public Log(Class<?> C)
	{
		Name = C.getSimpleName();
		SDF = new SimpleDateFormat("HH:mm:ss");
	}
	
	/**
	 * 
	 * @param S String to Print out
	 */
	
	public void debug(String S)
	{
		System.out.println("["+SDF.format(new Date())+"]"+"["+Name+"]"+S);
	}
	
	/**
	 * 
	 * @param e Exception to turn into a String, and print out
	 */
	
	public void EtoStringLog(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		debug(sw.toString()); //
	}
}
