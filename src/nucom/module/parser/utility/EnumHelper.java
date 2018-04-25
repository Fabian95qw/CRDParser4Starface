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
		EQUALSNOT
	}
	
}
