package friends;

import java.util.*;
import java.io.*;
import java.lang.*;

public class Friends
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		Graph g = new Graph();

		boolean goodFile = false;
		while (!goodFile)
		{
			goodFile = true;
//			System.out.print("Enter the friends graph file: ");
//			String graphFile = sc.next();
			String graphFile = "graph.txt";

			try
			{
				g = new Graph(graphFile);
			}
			catch (FileNotFoundException e)
			{
				System.out.println("You have entered an incorrect graph file.\n");
				goodFile = false;
			}
		}

		boolean cont = true;
		while (cont)
		{
			System.out.println("\n(1) Shortest Intro Chain");
			System.out.println(  "(2) Cliques at School");
			System.out.println(  "(3) Connectors");
			System.out.println(  "(4) Quit");
			System.out.println(  "(5) Print Out Graph");
			System.out.print(    "Enter Option --> ");
			int option = sc.nextInt();
			
			if (option == 1)
				g.shortestChain();
			
			if (option == 4)
				cont = false;
			
			if (option == 5)
				g.printGraph();
		}
		sc.close();
		System.exit(0);
	}
}