package friends;

public class Person
{
	String name;
	String college;
	
	public Person(String name)
	{
		this.name = name;
	}
	
	public Person(String name, String college)
	{
		this.name = name;
		this.college = college;
	}
	
	public String toString() 
	{
		return "(" + name + ", " + college + ")";
	}
}