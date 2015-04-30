package friends;

import java.util.*;
import java.io.*;

public class Graph
{
	HashMap<String, Person> graph = new HashMap<String, Person>();

	///// Build Graph
	public Graph(ArrayList<Person> clique)
	{
		for (Person p : clique)
		{
			Person newP = new Person(p.name, p.college);
			graph.put(newP.name, newP);
		}
				
		for (Person p: clique)
			for (Person n : p.neighbors)
				if (clique.contains(n))
					graph.get(p.name).neighbors.add(graph.get(n.name));
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
		System.out.println("Name (key)      Person Object                     Neighbors");
		System.out.println("----------      -------------                     ---------");
		for (String key : graph.keySet())
		{
			System.out.printf( "%-15s %-25s -->     %s %n", key, graph.get(key), graph.get(key).neighbors);
		}
	}

	public void printGraphFile()
	{
		ArrayList<Person> unvisited = new ArrayList<Person>();
		
		System.out.println();
		System.out.println(graph.size());
		for (String key : graph.keySet())
		{
			
			if (graph.get(key).college != null)
				System.out.println(graph.get(key).name + "|y|" + graph.get(key).college);
			else System.out.println(graph.get(key).name + "|n");
			unvisited.add(graph.get(key));
		}		
		
		for (String key : graph.keySet())
		{
			Person current = graph.get(key);
			for (Person p : current.neighbors)
			{
				if (unvisited.contains(p))
				{
					System.out.println(current.name + "|" + p.name);
				}
			}
			unvisited.remove(current);
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
			else System.out.println("'" + personOne + "' is not a valid person");
		}
		
		goodName = false;
		while (!goodName)
		{
			System.out.print("Enter the second person: ");
			personTwo = sc.nextLine();		
			if (graph.containsKey(personTwo.toLowerCase()))
				goodName = true;
			else System.out.println("\n'" + personTwo + "' is not a valid person");
		}

		Person origin = graph.get(personOne.toLowerCase());
		Person target = graph.get(personTwo.toLowerCase());
		
		shortest(origin, target);
	}
	
	private void shortest(Person origin, Person target)
	{
		ArrayList<Person> unvisited = new ArrayList<Person>();
		ArrayList<Person> q = new ArrayList<Person>();
		ArrayList<Person> path = new ArrayList<Person>();
		HashMap<Person, Integer> distances = new HashMap<Person, Integer>();
		HashMap<Person, Person> previous = new HashMap<Person, Person>();

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
			System.out.println("\nThere exists no path from " + origin.name + " to " + target.name + ".");
			return;
		}

		else if (distances.get(target) == 0)
		{
			System.out.println("\nYou are already friends with yourself, don't be silly.");
			return;
		}

		else
			System.out.println("\nThe shortest path from " + origin.name + " to " + target.name + " is: ");
			
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
	
	///// Cliques 
	public void cliques()
	{
		Scanner sc = new Scanner(System.in);
		String school = null;
		
		boolean goodSchool = false;
		while (!goodSchool)
		{
			System.out.print("\nEnter the name of the school to find cliques: ");
			school = sc.nextLine();
			for (String key : graph.keySet())
			{
				if (school.equals(""))
				{
					school = null;
					goodSchool = true;
					break;
				}
				
				if (school.equals(graph.get(key).college))
					goodSchool = true;
			}
			if (!goodSchool)
				System.out.println("There exist no cliques at this school: " + school);
		}

		cliquesHelper(school);
	}
	
	private void cliquesHelper(String school)
	{
		ArrayList<Person> unvisited = new ArrayList<Person>();
		ArrayList<Person> clique = new ArrayList<Person>();
		ArrayList<Person> q = new ArrayList<Person>();
		
		if (school != null)
		{
			for (String key : graph.keySet())
				if (school.equals(graph.get(key).college))
					unvisited.add(graph.get(key));
		}
		
		else
		{
			for (String key : graph.keySet())
				if (graph.get(key).college == null)
					unvisited.add(graph.get(key));
		}
		
		int count = 1;
		Person current = null;

		if (school == null)
			System.out.println("These are the cliques for people who do not attend any schools: ");
		
		while (!unvisited.isEmpty())
		{
			q.add(unvisited.remove(0));
			clique.add(q.get(0));

			while (!q.isEmpty())
			{
				if (!q.isEmpty())
					current = q.remove(0);

				for (Person p : current.neighbors)
				{
					if (unvisited.contains(p))
					{
						unvisited.remove(p);
						clique.add(p);
						q.add(p);
					}
				}
			}

			Graph cliqueGraph = new Graph(clique);
			
			System.out.print("\nClique " + count + ": ");
			cliqueGraph.printGraphFile();
			System.out.println();
			clique.clear();
			count++;
		}	
	}
	
	///// Connectors
	public void connectors()
	{
		ArrayList<Person> connectors = new ArrayList<Person>();
		
		for (String poop : graph.keySet())
		{
			ArrayList<Person> unvisited = new ArrayList<Person>();
			ArrayList<Person> q = new ArrayList<Person>();
			
			for (String key : graph.keySet())
				unvisited.add(graph.get(key));
			
			Person origin = graph.get(poop);
			Person current = origin;
			
			unvisited.remove(current);
	
			while ((!q.isEmpty()) || (!unvisited.isEmpty()))
			{
				for(Person p : current.neighbors)
				{
					if (unvisited.contains(p))
					{
						unvisited.remove(p);
						q.add(p);
					}
				}
	
				if (!q.isEmpty())
					current = q.remove(0);
				else if (!unvisited.isEmpty())
					break;
			}

			int personCount = unvisited.size();

			for (String no : graph.keySet())
			{
				unvisited.clear();
				q.clear();

				for (String key : graph.keySet())
					unvisited.add(graph.get(key));
				
				current = origin;
				Person notAllowed = graph.get(no);

				unvisited.remove(current);
				unvisited.remove(notAllowed);

				while ((!q.isEmpty()) || (!unvisited.isEmpty()))
				{
					for(Person p : current.neighbors)
					{
						if (unvisited.contains(p))
						{
							unvisited.remove(p);
							q.add(p);
						}
					}
		
					if (!q.isEmpty())
						current = q.remove(0);
					else if (!unvisited.isEmpty())
						break;
				}

				if (unvisited.size() > personCount)
					if (!connectors.contains(notAllowed))
						connectors.add(notAllowed);

//				System.out.println("\nOrigin: " + origin);
//				System.out.println(  "Person Not Allowed: " + notAllowed);
//				for (String key : graph.keySet())
//					System.out.printf( "%-15s %8s %n", key, !unvisited.contains(graph.get(key)));
			}
		}
		
		System.out.println("\nThe Connectors in this graph are: ");
		System.out.print(connectors.remove(0).name);
		for (Person p : connectors)
			System.out.print(", " + p.name);
		System.out.println();
	}
}