class Element
{	
	String city = "";
	String country = "";
	int population = 0;
	int year = 0;
	int month = 0;
	int day = 0;
	
	public Element()
	{
		
	}
	
	public Element(String in)
	{
		//Month Day Year
		
		//replace extra character with spaces
		in = in.replaceAll("\"","");
		//in = in.replaceAll(","," ");
		in = in.replaceAll("/",",");
		in = in.replaceAll(", ",",");
		
		String[] parse = in.split(",");
		this.city = parse[0];
		this.country = parse[1];
		this.population = Integer.parseInt(parse[2]);
		this.month = Integer.parseInt(parse[3]);
		this.day = Integer.parseInt(parse[4]);
		this.year = Integer.parseInt(parse[5]);
	}
	
	public String toString()
	{
		return this.city + ", " + this.country + ": " + population + ", " + this.month + "/" + this.day + "/" + this.year;
	}
	
	public boolean compare(Element other, String option, String operation)
	{
		boolean result = false;
		if (option.equals("city"))
		{
			if (operation.equals("least")) 
			{ 
				if (this.city.compareTo(other.city) > 0) { result = true; }
			}
			else if (operation.equals("greatest"))
			{ 
				if (this.city.compareTo(other.city) < 0) { result = true; }
			}
			else if (operation.equals("equal"))
			{
				if (this.city.equals(other.city)) { result = true; }
			}
		}
		else if (option.equals("country"))
		{
			if (operation.equals("least")) 
			{ 
				if (this.country.compareTo(other.country) > 0) { result = true; }//if this is after other in the alphabet
			}
			else if (operation.equals("greatest"))
			{ 
				if (this.country.compareTo(other.country) < 0) { result = true; }//if this is before other in the alphabet
			}
			else if (operation.equals("equal"))
			{
				if (this.country.equals(other.country)) { result = true; }
			}
		}
		else if (option.equals("population"))
		{
			if (operation.equals("least")) { result = this.population < other.population; }
			else if (operation.equals("greatest")) { result = this.population > other.population; }
		}
		else if (option.equals("date"))
		{
			if (operation.equals("least")) 
			{ 
				if (this.year == other.year)
				{
					if (this.month == other.month)
					{
						result = this.day < other.day;
					}
					else
					{
						result = this.month < other.month;
					}
				}
				else
				{
					result = this.year < other.year;
				}
			}
			else if (operation.equals("greatest"))
			{ 
				if (this.year == other.year)
				{
					if (this.month == other.month)
					{
						result = this.day > other.day;
					}
					else
					{
						result = this.month > other.month;
					}
				}
				else
				{
					result = this.year > other.year;
				}
			}
			
			else if (operation.equals("equal"))
			{
				if (this.day == other.day && this.month == other.month && this.year == other.year) { result = true; }
			}
		}
		return result;
	}
}