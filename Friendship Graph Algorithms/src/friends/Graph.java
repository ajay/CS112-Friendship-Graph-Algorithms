package friends;

import java.util.*;
import java.io.*;
import java.lang.*;

//No multiples edges allowed
//No self loops allowed

public class Graph
{
	HashMap<String, ArrayList<Person>> graph = new HashMap<String, ArrayList<Person>>();

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
			ArrayList<Person> edges = new ArrayList<Person>();
			graph.put(p.name, edges);
			edges.add(p);
			count++;
		}
		
		System.out.println("Graph Before Adding Edges:");
		printGraph();
		
		while (sc.hasNextLine())
		{
			String line = sc.nextLine();
			addEdge(line);
		}
		
		System.out.println("\nGraph After Adding Edges:");
		printGraph();
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
		ArrayList<Person> edges = graph.get(parts[0]);
		edges.add(graph.get(parts[1]).get(0));
		edges = graph.get(parts[1]);
		edges.add(graph.get(parts[0]).get(0));
	}
	
	private void printGraph()
	{
		for (String key : graph.keySet())
		{
			System.out.printf( "%-12s %s %n", key, graph.get(key));
		}
	}
}