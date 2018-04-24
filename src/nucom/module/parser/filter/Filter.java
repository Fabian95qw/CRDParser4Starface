package nucom.module.parser.filter;

import java.util.List;

import nucom.module.parser.utility.EnumHelper.Data;
import nucom.module.parser.utility.EnumHelper.Logic;

public class Filter 
{
	private Data D = null;
	private List<String> Values=null;
	private Logic L = null;
	
	public Filter(Data D, List<String> Values, Logic L)
	{
		this.D=D;
		this.Values=Values;
		this.L=L;
	}

	public Data getD() {
		return D;
	}


	public List<String> getValues() {
		return Values;
	}

	@Override
	public String toString()
	{
		String Result = D.toString()+" "+ L.toString() +" " + Values.toString();
		return Result;
	}

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
