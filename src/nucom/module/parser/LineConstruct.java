package nucom.module.parser;

import java.lang.reflect.Method;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.Log;

public class LineConstruct 
{
	private String id="";
	private String callid="";
	private String callstepid="";
	private String callleguuid="";
	private String cdraccountid="";
	private String calleraccountid="";
	private String callercallerid="";
	private String calledaccountid="";
	private String calledcallerid="";
	private String serviceid="";
	private String starttime="";
	private String ringingtime="";
	private String linktime="";
	private String callresulttime="";
	private String callresult="";
	private String callresultcausedby="";
	private String lineid="";
	private String linename="";
	private String callbacknumber="";
	private String answeredelswhere="";
	private String incoming="";
	private String answered="";
	private String hasvoicemail="";
	private String hasmonitor="";
	private String hasfax="";
	private String deleted="";
	private String privatecall="";
	private String callbacknumberextern="";
	private String summarystep="";
	private String duration="";
	private String login="";

	private Log log = null;
	
	public LineConstruct()
	{
		log = new Log(this.getClass());
		id="";
		callid="";
		callstepid="";
		callleguuid="";
		cdraccountid="";
		calleraccountid="";
		callercallerid="";
		calledaccountid="";
		calledcallerid="";
		serviceid="";
		starttime="";
		ringingtime="";
		linktime="";
		callresulttime="";
		callresult="";
		callresultcausedby="";
		lineid="";
		linename="";
		callbacknumber="";
		answeredelswhere="";
		incoming="";
		answered="";
		hasvoicemail="";
		hasmonitor="";
		hasfax="";
		deleted="";
		privatecall="";
		callbacknumberextern="";
		summarystep="";
		duration="";
		login="";
	}	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCallid() {
		return callid;
	}


	public void setCallid(String callid) {
		this.callid = callid;
	}


	public String getCallstepid() {
		return callstepid;
	}


	public void setCallstepid(String callstepid) {
		this.callstepid = callstepid;
	}


	public String getCallleguuid() {
		return callleguuid;
	}


	public void setCallleguuid(String callleguuid) {
		this.callleguuid = callleguuid;
	}


	public String getCdraccountid() {
		return cdraccountid;
	}


	public void setCdraccountid(String cdraccountid) {
		this.cdraccountid = cdraccountid;
	}


	public String getCalleraccountid() {
		return calleraccountid;
	}


	public void setCalleraccountid(String calleraccountid) {
		this.calleraccountid = calleraccountid;
	}


	public String getCallercallerid() {
		return callercallerid;
	}


	public void setCallercallerid(String callercallerid) {
		this.callercallerid = callercallerid;
	}


	public String getCalledaccountid() {
		return calledaccountid;
	}


	public void setCalledaccountid(String calledaccountid) {
		this.calledaccountid = calledaccountid;
	}


	public String getCalledcallerid() {
		return calledcallerid;
	}


	public void setCalledcallerid(String calledcallerid) {
		this.calledcallerid = calledcallerid;
	}


	public String getServiceid() {
		return serviceid;
	}


	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}


	public String getStarttime() {
		return starttime;
	}


	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}


	public String getRingingtime() {
		return ringingtime;
	}


	public void setRingingtime(String ringingtime) {
		this.ringingtime = ringingtime;
	}


	public String getLinktime() {
		return linktime;
	}


	public void setLinktime(String linktime) {
		this.linktime = linktime;
	}


	public String getCallresulttime() {
		return callresulttime;
	}


	public void setCallresulttime(String callresulttime) {
		this.callresulttime = callresulttime;
	}


	public String getCallresult() {
		return callresult;
	}


	public void setCallresult(String callresult) {
		this.callresult = callresult;
	}


	public String getCallresultcausedby() {
		return callresultcausedby;
	}


	public void setCallresultcausedby(String callresultcausedby) {
		this.callresultcausedby = callresultcausedby;
	}


	public String getLineid() {
		return lineid;
	}


	public void setLineid(String lineid) {
		this.lineid = lineid;
	}


	public String getLinename() {
		return linename;
	}


	public void setLinename(String linename) {
		this.linename = linename;
	}


	public String getCallbacknumber() {
		return callbacknumber;
	}


	public void setCallbacknumber(String callbacknumber) {
		this.callbacknumber = callbacknumber;
	}


	public String getAnsweredelswhere() {
		return answeredelswhere;
	}


	public void setAnsweredelswhere(String answeredelswhere) {
		this.answeredelswhere = answeredelswhere;
	}


	public String getIncoming() {
		return incoming;
	}


	public void setIncoming(String incoming) {
		this.incoming = incoming;
	}


	public String getAnswered() {
		return answered;
	}


	public void setAnswered(String answered) {
		this.answered = answered;
	}


	public String getHasvoicemail() {
		return hasvoicemail;
	}


	public void setHasvoicemail(String hasvoicemail) {
		this.hasvoicemail = hasvoicemail;
	}


	public String getHasmonitor() {
		return hasmonitor;
	}


	public void setHasmonitor(String hasmonitor) {
		this.hasmonitor = hasmonitor;
	}


	public String getHasfax() {
		return hasfax;
	}


	public void setHasfax(String hasfax) {
		this.hasfax = hasfax;
	}


	public String getDeleted() {
		return deleted;
	}


	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


	public String getPrivatecall() {
		return privatecall;
	}


	public void setPrivatecall(String privatecall) {
		this.privatecall = privatecall;
	}


	public String getCallbacknumberextern() {
		return callbacknumberextern;
	}


	public void setCallbacknumberextern(String callbacknumberextern) {
		this.callbacknumberextern = callbacknumberextern;
	}


	public String getSummarystep() {
		return summarystep;
	}


	public void setSummarystep(String summarystep) {
		this.summarystep = summarystep;
	}


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}

	public ObservableValue<String> get(Data D) 
	{
		String S = D.toString();
		String output = S.substring(0, 1).toUpperCase() + S.substring(1);
		//log.debug("set"+output);
		try
		{
			Method M = this.getClass().getMethod("get"+output);
			String Result =(String) M.invoke(this);
			return new SimpleStringProperty(Result);
		}
		catch(Exception e)
		{
			log.EtoStringLog(e);
			return new SimpleStringProperty("");
		}
	}


	
}
