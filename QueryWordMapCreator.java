package spelling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class QueryWordMapCreator {
	
	HashMap<String,HashMap> queryWordMap = new HashMap<String,HashMap>();
	HashMap<String,Integer> pwHashMap = new HashMap<String,Integer>();
	double totalNumberOfWords = 0;
	int counted = 0;
	
	public void createTheMap()
	{
		File theDictionary = new File("query_log.txt");
		Scanner scans;
		
		try {
			scans = new Scanner(theDictionary);
			while(scans.hasNextLine())
			{
				String nextLine= scans.nextLine();
				
				Scanner scanWords = new Scanner(nextLine);
				String sessionID = scanWords.next();
				int pos = 1;
				while(scanWords.hasNext())
				{
					String curWord = scanWords.next();
					if(!queryWordMap.containsKey(curWord))
					{
						HashMap<String, ArrayList<Integer>> positionMap = new HashMap<String, ArrayList<Integer>>();
						ArrayList<Integer> positions = new ArrayList<Integer>();
						positions.add(pos);
						positionMap.put(sessionID, positions);
						queryWordMap.put(curWord, positionMap);
					}
					else
					{
						HashMap<String, ArrayList<Integer>> positionMap = queryWordMap.get(curWord);
						if(positionMap.containsKey(sessionID))
						{
							ArrayList<Integer> position = positionMap.get(sessionID);
							position.add(pos);
							positionMap.put(sessionID, position);
							queryWordMap.put(curWord, positionMap);
						}
						else
						{
							
							ArrayList<Integer> positions = new ArrayList<Integer>();
							positions.add(pos);
							positionMap.put(sessionID, positions);
							queryWordMap.put(curWord, positionMap);
						}
					}
					pos++;
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void createThePWHash()
	{
		File allFiles = new File("docs/");
		File[]listOfFiles = allFiles.listFiles();
		StopWords stopIt = new StopWords();
		for(File curFile: listOfFiles)
		{
			Scanner scans;
			try {
				scans = new Scanner(curFile);
				while(scans.hasNext())
				{
					String curWord = scans.next();
					//if it is not a stop word
					if(!stopIt.contains(curWord))
					{
						if(pwHashMap.containsKey(curWord))
						{
							int wordCount = pwHashMap.get(curWord);
							wordCount++;
							pwHashMap.put(curWord, wordCount);
							totalNumberOfWords++;
						}
						else
						{
							int wordCount = 1;
							pwHashMap.put(curWord, wordCount);
							totalNumberOfWords++;
						}
					}
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public double findThePEW(String correct, String incorrect)
	{
		HashMap<String, ArrayList<Integer>> correctWordQ = new HashMap<String, ArrayList<Integer>>();
		HashMap<String, ArrayList<Integer>> incorrectWordQ = new HashMap<String, ArrayList<Integer>>();
		double itReplaced = 0.0;
		double bottomTerm = 0.0;
		if(queryWordMap.containsKey(correct))
		{
			correctWordQ = queryWordMap.get(correct);
			if(queryWordMap.containsKey(incorrect))
			{
				incorrectWordQ = queryWordMap.get(incorrect);
				//Got from Online to itterate through a string
				for(Map.Entry<String,ArrayList<Integer>> entry1 : correctWordQ.entrySet())
				{
					String key = entry1.getKey();
					ArrayList<Integer> thePositions1 = entry1.getValue();
					if(incorrectWordQ.containsKey(key))
					{
						ArrayList<Integer> thePositions2 = entry1.getValue();
						for(int i = 0; i< thePositions1.size(); i++)
						{
							for(int j = 0; j<thePositions2.size(); j++)
							{
								if(thePositions1.get(i)==thePositions2.get(j))
								{
									itReplaced++;
								}
							}
						}
					}
				}
			}
			//Finding the bottom part, number of times w has been replaced for any word
			
			for(Map.Entry<String,ArrayList<Integer>> entry1 : correctWordQ.entrySet())
			{
				String key = entry1.getKey();
				ArrayList<Integer> thePositions1 = entry1.getValue();
				for(Map.Entry<String,HashMap> entry2 : queryWordMap.entrySet())
				{
					String key2 = entry2.getKey();
					HashMap<String, ArrayList<Integer>> theSessions = entry2.getValue();
					if(!key2.equals(correct))
					{
						if(theSessions.containsKey(key))
						{
							ArrayList<Integer> thePositions2 = theSessions.get(key);
							for(int i = 0; i< thePositions1.size(); i++)
							{
								for(int j = 0; j<thePositions2.size(); j++)
								{
									if(thePositions1.get(i)==thePositions2.get(j))
									{
										bottomTerm++;
									}
								}
							}
						}
					}
				}
			}
		}
		return itReplaced;
	}
	
	public double findThePW(String word)
	{
		double thePW = 0;
		if(pwHashMap.containsKey(word))
		{
			double numberAppeared = (double) pwHashMap.get(word);
			thePW = numberAppeared/totalNumberOfWords;
		}
		return thePW;
	}
	
	public String getTheSugg(ArrayList<String> sugPossibilites,String incorrect)
	{
		double highestCorrelation = 0;
		String highestCorrelate = "";
		double pe = 1;
		double pw = 1;
		for(int i = 0; i<sugPossibilites.size(); i++)
		{
			pe = findThePEW(sugPossibilites.get(i), incorrect);
			pw = findThePW(sugPossibilites.get(i));
			double correlate = pe*pw;
			if(correlate>highestCorrelation)
			{
				highestCorrelate = sugPossibilites.get(i);
				highestCorrelation = correlate;
			}
		}
		//System.out.println("The highest correlation: "+highestCorrelate + " with score: " +highestCorrelation);
		return highestCorrelate;
	}

}
