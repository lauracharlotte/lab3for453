package spelling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import proj1.QueryProcess;
import proj1.Tokenizer;
import proj1.TopScores;

public class StartHere {

	public static void main(String [] args){
		
		

		
		
			
		
		
		
		QueryWordMapCreator queryMap = new QueryWordMapCreator();
		queryMap.createTheMap();
		queryMap.createThePWHash();
		
		SoundexCreator soundexTHIS = new SoundexCreator();
		soundexTHIS.createHashMap();
		
		String answer = "";
		String q1 = "sentenced to prision";
		String q2 = "open cuort case";
		String q3 = "entretainment group";
		String q4 = "tv axtor";
		String q5 = "scheduled movie screning";
		
		ArrayList<String>allText = new ArrayList<String>();
		
		allText.add(q1);
		allText.add(q2);
		allText.add(q3);
		allText.add(q4);
		allText.add(q5);
		
		while (answer != "-1")
		{
			SnippetGenerator theSnipper = new SnippetGenerator();
			System.out.println("What words would you like spell checked?");
			
			Scanner userInput1 = new Scanner(System.in);
			String tester = userInput1.nextLine();
			Scanner userInput = new Scanner(tester);
			String wholeQuery = "";
			String userWord = "";
			int found = 0;
			String misSpelled = "";
			int spot = 0;
			while(userInput.hasNext())
			{
				found = 0;
				userWord = userInput.next().toLowerCase();
				misSpelled = userWord;
				
				File theDictionary = new File("dictionary.txt");
				Scanner scans;
				try {
					scans = new Scanner(theDictionary);
					
					while(scans.hasNext())
					{
						//System.out.println("here");
						String curDictionaryWord = scans.next().toLowerCase();
						
						if(curDictionaryWord.equals(userWord))
						{
							found = 1;
							//System.out.println("here1");
							wholeQuery+= userWord + " ";
							break;
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(found == 0)
				{
					break;
				}
				spot++;
			}
			
			//System.out.println("Misspelled:" + misSpelled);
			
			ArrayList<String>suggestedWords = soundexTHIS.soundexHash.get(soundexTHIS.soundExThis(misSpelled));
			EditDistanceFormulator edformulator = new EditDistanceFormulator();
			ArrayList<String> sugWordsPossibilites = edformulator.calculateEditDist(suggestedWords, misSpelled);
			//System.out.println("here13");
			String checkedWord = queryMap.getTheSugg(sugWordsPossibilites, misSpelled);
			//System.out.println("here14");
			wholeQuery += checkedWord + " ";
			//System.out.println("here19");
			while(userInput.hasNext())
			{
				//System.out.println("here20");
				wholeQuery += userInput.next() + " ";
				//System.out.println("here16");
				//System.out.println(wholeQuery);
			}
			//System.out.println(wholeQuery);
			//Put query back together w/ corrected word
			//System.out.println("here15");
			ArrayList<String> thisText = new ArrayList<String>();
			thisText.add(wholeQuery);
			
			System.out.println("Original Query: " + tester);
			System.out.println("Corrected Query: "+ wholeQuery);
			System.out.println("Soundex Code: " + soundexTHIS.soundExThis(misSpelled));
			System.out.println("Suggested Corrections: ");
			for(int u = 0; u< sugWordsPossibilites.size(); u++)
			{
				System.out.println(sugWordsPossibilites.get(u));
			}
			
			//From Project1!!!!
			Tokenizer officialTokenizer = new Tokenizer();
			QueryProcess officialQuerier = new QueryProcess();
			officialTokenizer.parseFiles();
			ArrayList<TopScores> topDocList = officialQuerier.queryThese(officialTokenizer, thisText);
			//System.out.println("here11");
			theSnipper.createSnippet(topDocList, wholeQuery);
			//System.out.println("here12");
		}
	}
}
