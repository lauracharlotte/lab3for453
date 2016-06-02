package spelling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import proj1.PorterStemmer;
import proj1.TopScores;

public class SnippetGenerator {
	double snip1 = 0;
	double snip2 = 0;
	double snip3 = 0;
	double snip4 = 0;
	double snip5 = 0;
	
	int placeInDoc = 0;
	ArrayList<String> theQuery = new ArrayList<String>();
	ArrayList<String> theQueryNoStops =new ArrayList<String>();
	
	String topSentance = "";
	String secondSentance = "";
	String docName = "";
	
	double topSentanceScore = 0.0;
	double secondSentanceScore = 0.0;
	
	public void createSnippet(ArrayList<TopScores> topDocList, String query)
	{

		theQuery = breakIntoArray(query);
		for(int i = 0; i<topDocList.size(); i++)
		{
			docName = topDocList.get(i).getDocumentID();
			File curFile = new File("docs/"+topDocList.get(i).getDocumentID());
			try {
				Scanner scanDoc = new Scanner(curFile);
					
				String aSentance = "";
				while(scanDoc.hasNext())
				{
					String curWord1= scanDoc.next();
					
					if(curWord1.contains(".")||curWord1.contains("?")||curWord1.contains("!"))
					{
						String curWord2 = curWord1.replace(".", "");
						curWord2 = curWord2.replace("?", "");
						curWord2 = curWord2.replace("!", "");
						aSentance += curWord2 + " ";
						theSnipSnip(aSentance);
						aSentance = "";
					}
					else
					{
						aSentance += curWord1 + " ";
					}
				}

				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//System.out.println("here1");
			Scanner scanLast = new Scanner(topSentance);
			ArrayList<String> topArray = new ArrayList<String>();
			PorterStemmer stemstem = new PorterStemmer();
			while(scanLast.hasNext())
			{
				//System.out.println("here2");
				topArray.add(scanLast.next());
			}
			//System.out.println("here2: " + topArray);
			for(int y = 0; y<theQuery.size(); y++)
			{
				for(int z = 0; z<topArray.size(); z++)
				{
					String orig = topArray.get(z);
					String stemmed = stemstem.stem(topArray.get(z).toLowerCase());
					if(theQuery.get(y).equals(stemmed))
					{
						//System.out.println("here3");
						//topArray.add(z, topArray.get(z).toUpperCase());
						topArray.remove(z);
						topArray.add(z, orig.toUpperCase());
						
					}
				}
			}
			//System.out.println("here3: " + topArray);
			String topSentance1 = "";
			for(int z = 0; z<topArray.size(); z++)
			{
				//System.out.println("her4");
				if(z!=topArray.size()-1){
					topSentance1 += topArray.get(z) + " ";
				}
				else
				{
					topSentance1 += topArray.get(z);
				}
				
			}
			//System.out.println("here4: " + topSentance1);
			Scanner scanLast2 = new Scanner(secondSentance);
			ArrayList<String> secondArray = new ArrayList<String>();
			PorterStemmer stemstem2 = new PorterStemmer();
			while(scanLast2.hasNext())
			{
				//System.out.println("here6");
				secondArray.add(scanLast2.next());
			}
			
			for(int y = 0; y<theQuery.size(); y++)
			{
				for(int z = 0; z<secondArray.size(); z++)
				{
					//System.out.println("here7");
					String orig = secondArray.get(z);
					String stemmed = stemstem2.stem(secondArray.get(z).toLowerCase());
					if(theQuery.get(y).equals(stemmed))
					{
						secondArray.remove(z);
						secondArray.add(z, orig.toUpperCase());
						
					}
				}
			}
			String secondSentance1 = "";
			for(int z = 0; z<secondArray.size(); z++)
			{
				if(z!=secondArray.size()-1){
					//System.out.println("here8");
					secondSentance1 += secondArray.get(z) + " ";
				}
				else
				{
					//System.out.println("here9");
					secondSentance1 += secondArray.get(z);
				}
				
			}
			//System.out.println("here10");
			topSentance = topSentance1;
			secondSentance = secondSentance1;
			System.out.println(docName);
			System.out.println("TopSentance: "+topSentance);
			//System.out.println("TopScore: "+topSentanceScore);
			System.out.println("SecondSentance: "+secondSentance);
			//System.out.println("SecondScore: "+secondSentanceScore);
			
			topSentanceScore = 0;
			secondSentanceScore = 0;
		}

	}
	
	public double theSnipSnip(String sentance)
	{
		snip1 = 0;
		snip2 = 0;
		snip3 = 0;
		snip4 = 0;
		snip5 = 0;
		
		String curString = sentance;
		//System.out.println(curString);
		snipCheck1(curString);
		snipCheck2(curString);
		snipCheck3(curString);
		snipCheck4(curString);
		snipCheck5(curString);
		placeInDoc++;
		
		double totalScore = snip1+snip2+snip3+snip4+snip5;
		if(totalScore>topSentanceScore)
		{
			secondSentanceScore = topSentanceScore;
			secondSentance = topSentance;
			topSentanceScore = totalScore;
			topSentance = sentance;
		}
		else if(totalScore >secondSentanceScore)
		{
			secondSentanceScore = totalScore;
			secondSentance = sentance;
		}
		//System.out.println("Snip Total Score: "+ totalScore);
		return 0.0;
	}
	
	public double snipCheck1(String sentance) //Check for if first or second sentance
	{
		ArrayList<String> sentanceQuery = breakIntoArray(sentance);
		if(placeInDoc<2)
		{
			for(int i = 0; i<theQuery.size(); i++)
			{
				for(int p = 0; p<sentanceQuery.size(); p++)
				{
					if(theQuery.get(i).equals(sentanceQuery.get(p)))
					{
						if(placeInDoc == 0)
						{
							snip1 = 2;
						}
						if(placeInDoc == 1)
						{
							snip1 = 1;
						}
					}
				}
			}
		}
		else
		{
			snip1 = 0;
		}
		//System.out.println("Snip1: "+ snip1);
		return snip1;
	}
	public double snipCheck2(String sentance) //check how many significant words are in the sentance
	{
		ArrayList<String> sentanceQuery = breakIntoArray(sentance);
		//System.out.println(sentanceQuery);
		HashSet queryHash = new HashSet();
		queryHash.addAll(theQuery);
		theQueryNoStops.addAll(queryHash);
		/*for(int j = 0; j<theQuery.size(); j++)
		{
			StopWords stopIt = new StopWords();
			if(stopIt.contains(theQuery.get(j)))
			{
				theQuery.remove(j);
				j--;
			}
		}*/
		for(int i = 0; i<theQuery.size(); i++)
		{
			for(int p = 0; p<sentanceQuery.size(); p++)
			{
				if(theQuery.get(i).equals(sentanceQuery.get(p)))
				{
					snip2++;
				}
			}
		}
		//System.out.println("Snip2: "+ snip2);
		return snip2++;
	}
	public double snipCheck3(String sentance)
	{
		ArrayList<String> sentanceQuery = breakIntoArray(sentance);
		for(int i = 0; i<theQueryNoStops.size(); i++)
		{
			for(int p = 0; p<sentanceQuery.size(); p++)
			{
				if(theQueryNoStops.get(i).equals(sentanceQuery.get(p)))
				{
					snip3++;
					break;
				}
			}
		}
		//System.out.println("Snip3: "+ snip3);
		return snip3;
	}
	public double snipCheck4(String sentance)
	{
		ArrayList<String> sentanceQuery = breakIntoArray(sentance);
		double value = 0.0;
		for(int i = 0; i<sentanceQuery.size(); i++)
		{
			for(int j = 0; j<theQuery.size(); j++)
			{
				if(sentanceQuery.get(i).equals(theQuery.get(j)))
				{
					value++;
					int iPlus = i+1;
					int jPlus = j+1;
					while(iPlus<sentanceQuery.size() &&jPlus<theQuery.size() && sentanceQuery.get(iPlus).equals(theQuery.get(jPlus)))
					{
						value++;
						iPlus++;
						jPlus++;
					}
				}
			}
		}
		snip4 = value;
		//System.out.println("Snip4: "+ snip4);
		return snip4;
	}
	public double snipCheck5(String sentance)
	{
		ArrayList<String> sentanceQuery = breakIntoArray(sentance);
		double sigWordCount = 0;
		double totalWordCount = 0;
		for(int j = 0; j<sentanceQuery.size(); j++)
		{
			for(int i = 0; i<theQuery.size(); i++)
			{
				if(sentanceQuery.get(j).equals(theQuery.get(i)))
				{
					sigWordCount++;
				}
				totalWordCount++;
			}
		}
		snip5 = (sigWordCount*sigWordCount)/totalWordCount;
		//System.out.println("Snip5: "+ snip5);
		return snip5;
	}
	public ArrayList<String> breakIntoArray(String sentance)
	{
		/*Scanner seperateSentance = new Scanner(sentance);
		ArrayList<String> sentanceArray = new ArrayList<String>();
		while(seperateSentance.hasNext())
		{
			sentanceArray.add(seperateSentance.next());
		}
		return sentanceArray;*/
		ArrayList<String> sentanceArray = new ArrayList<String>();
		StopWords snipStops = new StopWords();
		PorterStemmer stemSnips = new PorterStemmer();
		Scanner scanQ = new Scanner(sentance);
		while(scanQ.hasNext())
		{
			String curQ = scanQ.next();
			curQ = curQ.toLowerCase();
			if(!snipStops.contains(curQ))
			{
				String newQ = stemSnips.stem(curQ);
				sentanceArray.add(newQ);
			}
		}
		return sentanceArray;
	}
}
