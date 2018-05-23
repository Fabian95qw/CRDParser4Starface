package nucom.module.parser.filter;

import java.util.List;

import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.EnumHelper.Logic;
import nucom.module.parser.utility.EnumHelper.StatisticLogic;

public class Filter 
{
	private Data D = null;
	private List<String> Values=null;
	private Logic L = null;
	private StatisticLogic SL = null;
	
	/**
	 * 
	 * @param D ==> Which Datafield to apply the filter to
	 * @param Values ==> Which Values to use
	 * @param L ==> if it has to be equals or equalsnot the values
	 * @param sL 
	 */
	
	public Filter(Data D, List<String> Values, Logic L)
	{
		this.D=D;
		this.Values=Values;
		this.L=L;
	}
	
	public Filter(Data D, List<String> Values, Logic L, StatisticLogic SL)
	{
		this.D=D;
		this.Values=Values;
		this.L=L;
		this.SL=SL;
	}

	public Data getD()
	{
		return D;
	}
	
	public StatisticLogic getSL()
	{
		return SL;
	}

	public List<String> getValues()
	{
		return Values;
	}
	
	@Override
	public String toString()
	{
		if(SL == null)
		{
			String Result = D.toString()+" "+ L.toString() +" " + Values.toString();
			return Result;
		}
		else
		{
			String Result = SL.toString() +" " + D.toString()+" "+ L.toString() +" " + Values.toString();
			return Result;
		}
	}
	
	
	/**
	 * 
	 * @param Value ==> Value to check against this filter. 
	 * @return true ==> The value is valid for this filter. false ==> the value does not match the filter.
	 */

	public boolean accepts(String Value) 
	{
		switch(L)
		{
		case EQUALS:
			for(String S: Values)
			{
				if(S.equals(Value))
				{
					return true;
				}
			}
			return false;
		case EQUALSNOT:
			for(String S: Values)
			{
				if(S.equals(Value))
				{
					return false;
				}
			}
			return true;
		case LIKE:
			for(String S : Values)
			{
				if(Value.contains(S))
				{
					return true;
				}
			}
			return false;
		case LIKENOT:
			for(String S : Values)
			{
				if(Value.contains(S))
				{
					return false;
				}
			}
			return true;
			case GREATER_THAN:
			{
				Integer IValue = toInt(Value);
				if(IValue == null) {return false;};
				if(Values.size() == 0) {return false;}	
				Integer FilterValue = toInt(Values.get(0));
				if(FilterValue == null) {return false;}
				
				return IValue > FilterValue;
			}
		case LESS_THAN:
			{
				Integer IValue = toInt(Value);
				if(IValue == null) {return false;};
				if(Values.size() == 0) {return false;}	
				Integer FilterValue = toInt(Values.get(0));
				if(FilterValue == null) {return false;}
				
				return IValue < FilterValue;
			}
		default:
			return false;
		}
	}
	

	private Integer toInt(String S)
	{
		S=S.replace("\"", "");
		try
		{
			return Integer.parseInt(S);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public String toChartString() 
	{
		String S ="";
		
		switch(L)
		{
		case EQUALS:
			S="==";
			break;
		case EQUALSNOT:
			S="!=";
			break;
		case GREATER_THAN:
			S=">";
			break;
		case LESS_THAN:
			S="<";
			break;
		case LIKE:
			S="=%";
			break;
		case LIKENOT:
			S="!=%";
			break;
		default:
			break;
		}
		S = "\""+ S + Values.toString()+"\"";
		return S;
	}
}
