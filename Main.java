package com.gmail.s12348.evgen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter the URL:");
		String urlLink = sc.nextLine();
		try {
			URL url = new URL(urlLink);
			File file = new File("url.txt");
			URLParser urlpars = new URLParser(urlLink, file);
			urlpars.startingProcessing();
		} catch (IOException e) {
			System.out.println("URL not register");
		}
		sc.close();
	}
}
