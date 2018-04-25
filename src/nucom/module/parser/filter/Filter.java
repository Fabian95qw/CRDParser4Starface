package nucom.module.parser.filter;

import java.util.List;

import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.EnumHelper.Logic;

public class Filter 
{
	private Data D = null;
	private List<String> Values=null;
	private Logic L = null;
	
	/**
	 * 
	 * @param D ==> Which Datafield to apply the filter to
	 * @param Values ==> Which Values to use
	 * @param L ==> if it has to be equals or equalsnot the values
	 */
	
	public Filter(Data D, List<String> Values, Logic L)
	{
		this.D=D;
		this.Values=Values;
		this.L=L;
	}

	public Data getD() {
		return D;
	}

	@Override
	public String toString()
	{
		String Result = D.toString()+" "+ L.toString() +" " + Values.toString();
		return Result;
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
				return false;
			}
		case EQUALSNOT:
			for(String S: Values)
			{
				if(S.equals(Value))
				{
					return false;
				}
				return true;
			}
		default:
			return false;
		}
	}
	
}
