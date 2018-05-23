package nucom.module.parser.utility;

public class EnumHelper 
{
	/**
	 * 
	 * @author FZU
	 * Data Enum, for the known .csv Fields
	 */
	
	public enum Data
	{
		id,
		callid,
		callstepid,
		callleguuid,
		cdraccountid,
		calleraccountid,
		callercallerid,
		calledaccountid,
		calledcallerid,
		serviceid,
		starttime,
		starttimeformatted,
		ringingtime,
		linktime,
		callresulttime,
		callresult,
		callresultcausedby,
		lineid,
		linename,
		callbacknumber,
		answeredelswhere,
		incoming,
		answered,
		hasvoicemail,
		hasmonitor,
		hasfax,
		deleted,
		privatecall,
		callbacknumberextern,
		summarystep,
		duration,
		login
	}
	
	public enum Logic
	{
		EQUALS,
		EQUALSNOT,
		LIKE,
		LIKENOT,
		GREATER_THAN,
		LESS_THAN,
	}
	
	public enum StatisticTypes
	{
		NONE,
		Line,
		Pie, 
		Stacked
	}
	
	public enum StatisticLogic
	{
		MERGE_WHERE
	}
	
}
