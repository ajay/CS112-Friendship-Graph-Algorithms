package friends;

import java.util.*;
import java.io.*;
import java.lang.*;

public class Graph
{
	HashMap<String, Person> graph = new HashMap<String, Person>();

	///// Build Graph
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
		
		shortest(origin, target);
	}
	
	private void shortest(Person origin, Person target)
	{
		ArrayList<Person> unvisited = new ArrayList<Person>();
		ArrayList<Person> q = new ArrayList<Person>();
		HashMap<Person, Integer> distances = new HashMap<Person, Integer>();
		HashMap<Person, Person> previous = new HashMap<Person, Person>();
		ArrayList<Person> path = new ArrayList<Person>();

		for (String key : graph.keySet())
		{
			unvisited.add(graph.get(key));
			distances.put(graph.get(key), -1);
			previous.put(graph.get(key), null);
		}
		
		Person current = origin;
		unvisited.remove(current);
		distances.put(current, 0);
		
		while ((!q.isEmpty()) || (!unvisited.isEmpty()))
		{
			for(Person p : current.neighbors)
			{
				if (unvisited.contains(p))
				{
					unvisited.remove(p);
					q.add(p);
					distances.put(p, distances.get(current)+1);
					previous.put(p, current);
				}
			}

			if (!q.isEmpty())
				current = q.remove(0);
			else if (!unvisited.isEmpty())
				break;
		}

		if (distances.get(target) == -1)
		{
			System.out.println("\nThere exists no shortest path from "+origin.name+" to "+target.name+".");
			return;
		}
		
		System.out.println("\nThe shortest path from "+origin.name+" to "+target.name+" is: ");
		
		path.add(target);
		while (target != origin)
		{
			target = previous.get(target);
			path.add(0, target);
		}
		
		System.out.print(path.remove(0).name);
		
		for (Person p : path)
			System.out.print(" --> "+p.name);
		
		System.out.print("\n");
		
//		System.out.println("\n");
//		for (Person key : distances.keySet())
//			System.out.printf( "%-23s %5d %8s %n", key, distances.get(key), !unvisited.contains(key));
	}	
}





















