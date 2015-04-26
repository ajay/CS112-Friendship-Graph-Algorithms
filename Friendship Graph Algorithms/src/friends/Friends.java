package friends;

import java.io.*;
import java.util.*;
import java.lang.*;

public class Friends
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the friends graph file: ");
		String graphFile = sc.next();
		
		//build graph
		
		while (true)
		{
			System.out.println("\n(1) Shortest Intro Chain");
			System.out.println("(2) Cliques at School");
			System.out.println("(3) Connectors");
			System.out.println("(4) Quit");
			System.out.print("Enter Option --> ");
			int option = sc.nextInt();
		}

	}
}