package friends;

import java.util.*;
import java.io.*;
import java.lang.*;

//No multiples edges allowed
//No self loops allowed

public class Graph
{
	HashMap<String, Person> graph = new HashMap<String, Person>();

	///// Build Graph
	public Graph()
	{
	}
	
	public Graph(String graphFile) throws FileNotFoundException
	{
		if (graphFile == null)
			throw new FileNotFoundException();

		Scanner sc = new Scanner(new File(graphFile));
		int numberOfPeople = Integer.parseInt(sc.nextLine());

		int count = 0;
		while (count < numberOfPeople)
		{
			String line = sc.nextLine();
			Person p = createPerson(line);
			graph.put(p.name, p);
			count++;
		}

		while (sc.hasNextLine())
		{
			String line = sc.nextLine();
			addEdge(line);
		}
		sc.close();
	}

	private Person createPerson(String line)
	{
		String[] parts = line.split("\\|");

		if (parts.length == 3)
			return new Person(parts[0], parts[2]);

		else return new Person(parts[0]);
	}

	private void addEdge(String line)
	{
		String[] parts = line.split("\\|");
		ArrayList<Person> neighbors = graph.get(parts[0]).neighbors;
		neighbors.add(graph.get(parts[1]));
		neighbors = graph.get(parts[1]).neighbors;
		neighbors.add(graph.get(parts[0]));
	}

	
	///// Print Graph
	public void printGraph()
	{
		System.out.println();
		for (String key : graph.keySet())
		{
			System.out.printf( "%-15s %-25s -->     %s %n", key, graph.get(key), graph.get(key).neighbors);
		}
	}
	
	///// Shortest Chain
	public void shortestChain()
	{
		Scanner sc = new Scanner(System.in);
		String personOne = null;
		String personTwo = null;
		
		boolean goodName = false;
		while (!goodName)
		{
			System.out.print("\nEnter the first person: ");
			personOne = sc.nextLine();
			if (graph.containsKey(personOne.toLowerCase()))
				goodName = true;
			else System.out.println("'"+personOne+"' is not a valid person");
		}
		
		goodName = false;
		while (!goodName)
		{
			System.out.print("Enter the second person: ");
			personTwo = sc.nextLine();		
			if (graph.containsKey(personTwo.toLowerCase()))
				goodName = true;
			else System.out.println("\n'"+personTwo+"' is not a valid person");
		}
		
		Person origin = graph.get(personOne);
		Person target = graph.get(personTwo);
		
		HashMap<Person, Integer> height = heightMap(origin);
		
//		shortest(origin, target);
		
		
	}
	
	private HashMap<Person, Integer> heightMap(Person origin)
	{
		HashMap<Person, Integer> height = new HashMap<Person, Integer>();
		HashMap<Person, Boolean> visited = new HashMap<Person, Boolean>();


		for (String key : graph.keySet())
		{
			height.put(graph.get(key), -1);
			visited.put(graph.get(key), false);
		}
		
		System.out.println("\n");
		for (Person key : height.keySet())
			System.out.println(key+" "+height.get(key)+" "+visited.get(key));
		
		height.put(origin, 0);
		visited.put(origin, true);
		
		System.out.println("\n");
		for (Person key : height.keySet())
			System.out.println(key+" "+height.get(key)+" "+visited.get(key));
		
		
		
		
		Person current = origin;
		boolean cont = true;
		while (cont)
		{
			for (Person p : current.neighbors)
			{
				if (visited.get(p) == false)
				{
					height.put(p, height.get(current)+1);
					visited.put(p, true);
				}
			}
			
			cont = false;
			for (Person p : current.neighbors)
			{
				if (visited.get(p) == false)
				{
					cont = true;
				}
			}
			
			int min = 99999999;
			
			for (Person p : height.keySet())
			{
				if ((height.get(p) >= 0) && (height.get(p) <= min))
				{
					current = p;
				}
			}
		}
		
		
		System.out.println("\n");
		for (Person key : height.keySet())
			System.out.println(key+" "+height.get(key)+" "+visited.get(key));
		
		
		
		
		
		
		
		
		
		return null;	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void shortest(Person origin, Person target)
	{
		System.out.println("test");
	}
}




















