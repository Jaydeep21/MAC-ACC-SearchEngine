package com.searchengine.WebSearchEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import org.apache.commons.io.FileUtils;
public class WebSearchEngine {

	static ArrayList<String> key = new ArrayList<String>();
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static int n = 1;
	static Scanner sc = new Scanner(System.in);
	static int R;
	static int[] right;

	public WebSearchEngine() {

		System.out.println("***************************************************");
		System.out.println("****************WEB SEARCH ENGINE******************");
		System.out.println("***************************************************");
		System.out.println("**************TEAM MEMBERS***************");
		System.out.println("\n		Abhishek Patel Mullur Chandrashekar					   ");
		System.out.println("		Jaydeep Pritesh Dharamsey 				   ");
		System.out.println("		Raj Manoj Dedhia		    		   ");
		System.out.println("\n***************************************************");
	}

	// Finding pattern using Boyer Moore method.
	public static int search1(String pat, String txt) {
		BoyerMoore b = new BoyerMoore(pat);
		int offset = b.search(txt);
		return offset;
	}

	public static void searchEngine() {
		File dir = new File(System.getProperty("user.dir") + "\\textFiles\\");

		try {
			FileUtils.cleanDirectory(dir);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scanner scan = new Scanner(System.in);
		WebSearchEngine w = new WebSearchEngine();
		System.out.println("\n*****************CRAWLING STARTED******************");
//		String urlToCrawl = "http://geeksforgeeks.org/";
		System.out.println("\n\n Enter the URL you want to crawl\n");
		String urlToCrawl = scan.nextLine();
		Crawler.spider(urlToCrawl);
		System.out.println("\n*****************CRAWLING STOPPED******************");
		
		/** occurs to store the frequency of search word in each file. Each entry container <String filename, int frequency>	 */
		Hashtable<String, Integer> occurrs = new Hashtable<String, Integer>();
		String choice = "y";

		do {
			System.out.println("\n***************************************************");
			System.out.println("\nENTER THE SEARCH WORD: ");
			String p = scan.nextLine();
			System.out.println("***************************************************");
			long fileNumber = 0;
			int occur = 0;
			int pg = 0;

			try {
				File[] fileArray = dir.listFiles();
				for (int i = 0; i < fileArray.length; i++) {
					// Searching the word given as an input.
					occur = SearchWord.wordSearch(fileArray[i], p);
					occurrs.put(fileArray[i].getName(), occur);
					if (occur != 0)
						pg++;
					fileNumber++;
				}

				if (pg == 0) {
					System.out.println("\n\n\n\n\n\n---------------------------------------------------");
					System.out.println("Given word not found!!");
					System.out.println("Searching for similar words.....");
					/* using regex to find similar strings to pattern */
					SearchWord.altWord(p);
				} 
				else {
					//Ranking of Web Pages using merge sort 
					//Collections.sort by default uses merge sort
					WebSearchEngine.hashing(occurrs, pg);
					Sorting.pageSort(occurrs,pg);
				}	
				System.out.println("\n\n Do you want to continue(y/n)??");
				choice = scan.nextLine();
			} catch(Exception e) {
				e.printStackTrace();
			}
		} while(choice.equals("y"));

		System.out.println("\n***************************************************\n");
		System.out.println("	:) THANK YOU FOR USING OUR SEARCH ENGINE :)        ");
		System.out.println("\n***************************************************\n");
			
	}

	// MAIN METHOD.........
	public static void main(String[] args) {
			WebSearchEngine.searchEngine();		
	}

	static void hashing(Hashtable<String, Integer> hashtable, Integer page){
		System.out.println("-----------------------------------------------------------------------------");
		System.out.printf("| %10s | %20s", "VALUE", "KEY");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------");
		hashtable.forEach(
				(k, v) -> {
					System.out.format("| %10s | %20s ",  v , k);
					System.out.println();
				});
		System.out.println("-----------------------------------------------------------------------------");
	}

}
