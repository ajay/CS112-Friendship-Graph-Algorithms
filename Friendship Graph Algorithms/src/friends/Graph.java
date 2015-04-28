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
		
//		sc.close();
		
		Person origin = graph.get(personOne);
		Person target = graph.get(personTwo);
		
		shortest(origin, target);
		
		
//		HashMap<Person, Integer> distances = new HashMap<Person, Integer>();
//		
//		for (String key : graph.keySet())
//			distances.put(graph.get(key), -1);
//		

//		
//		distances.put(origin, 0);
//		
//		ArrayList<Person> path = new ArrayList<Person>();
//		path.add(origin);
//		
//		while (path.get(path.size()-1) != target)
//		{
//			System.out.println("\n");
//			for (Person key : distances.keySet())
//				System.out.println(key+" "+distances.get(key));
//			
//			
//			Person min = null;
//			int minimum = 999999999;
//			
//			for (Person key : distances.keySet())
//			{
//				if ((distances.get(key) >= 0) && (distances.get(key) <= minimum))
//				{
//					min = key;
//					minimum = distances.get(key);
//				}
//			}
//			
////			System.out.println("min is "+min);
//
//			for (Person n : min.neighbors)
//			{
//				if (distances.containsKey(n))
//					distances.put(n, minimum + 1);
//			}
//			
//			if (path.get(path.size()-1).neighbors.contains(min))
//				path.add(min);
//			distances.remove(min);
//			
////			System.out.println(distances.size());
////			for (Person p : path)
////				System.out.print(p+" --> ");
//			
//		}
//		
//		
//		
////		for (Person key : distances.keySet())
////			System.out.println(key+" "+distances.get(key));
//
//		
//		System.out.println("\n");
//		
//		for (Person p : path)
//			System.out.print(p+" --> ");
	}
	
	public void shortest(Person origin, Person target)
	{
		System.out.println("test");
	}
}