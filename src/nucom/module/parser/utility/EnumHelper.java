package nucom.module.parser.utility;

public class EnumHelper 
{
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
