package com.kittydaddy.search;

public class HighLightSearch {
	 public static void main(String args[]) {
	        Indexer newIndex = new Indexer();
	        newIndex.index();
	        
	        Search newSearch = new Search();
	        newSearch.search("UIMA");
	}
}
