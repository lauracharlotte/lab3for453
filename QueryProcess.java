package proj1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import spelling.StopWords;

public class QueryProcess {
	public ArrayList<TopScores> queryThese(Tokenizer theTokenizer, ArrayList<String>allText1)
	{
		HashMap theMap = theTokenizer.theMap;
		
		/*String text1 = "killing incident";
		String text2 = "suspect charged with murder";
		String text3 = "court";
		String text4 = "jury sentenced murderer to prison";
		String text5 = "movie";
		String text6 = "entertainment films";
		String text7 = "court appeal";
		String text8 = "action film producer";
		String text9 = "drunk driving accusations";
		String text10 = "actor appeared in movie premiere";
		String text11 = "James Bond Actors";*/
		
		ArrayList<String> allText = allText1;
				
		/*ArrayList<String> allText = new ArrayList<String>();
		allText.add(text1);
		allText.add(text2);
		allText.add(text3);
		allText.add(text4);
		allText.add(text5);
		allText.add(text6);
		allText.add(text7);
		allText.add(text8);
		allText.add(text9);
		allText.add(text10);
		allText.add(text11);*/
		
		for(int i = 0; i<allText.size(); i++)
		{
			ArrayList<TopScores> topList = new ArrayList<TopScores>();
			ArrayList<String> groupOfWords = new ArrayList<String>();
			StringTokenizer token = new StringTokenizer(allText.get(i)," ,\n\t\r.,?!;:\")(*&^%$#@-_'][");
			while(token.hasMoreTokens())
			{
				String curWord = token.nextToken();
				String curWordLower = curWord.toLowerCase();
				StopWords checkStopWords = new StopWords();
				if(!checkStopWords.contains(curWordLower))
				{
					PorterStemmer createStem = new PorterStemmer();
					String finalWord = createStem.stem(curWordLower);
					if(finalWord != "Invalid term")
					{
						if(finalWord!="No term entered")
						{
							groupOfWords.add(finalWord);
						}
					}
				}
			}
			for(int j = 0; j<theTokenizer.namesOfFiles.size(); j++)
			{
				//System.out.println("here1");
				double score = 0;
				
				for(int k = 0; k < groupOfWords.size(); k++)
				{
					if(theMap.containsKey(groupOfWords.get(k)))
					{
						//System.out.println("here1");
						HashMap wordMap = (HashMap) theMap.get(groupOfWords.get(k));
						
						if(wordMap.containsKey(theTokenizer.namesOfFiles.get(j)))
						{
							//System.out.println("here1");
							double part1;
							double part2;
							if(wordMap.get(theTokenizer.namesOfFiles.get(j)) instanceof Integer)
							{
								int part1int = (int) wordMap.get(theTokenizer.namesOfFiles.get(j));
								part1 = (double) part1int;
							}
							else
							{
								part1 = (double)wordMap.get(theTokenizer.namesOfFiles.get(j));
							}
							if(theTokenizer.maxFreq.get(theTokenizer.namesOfFiles.get(j)) instanceof Integer)//groupOfWords.get(k)) instanceof Integer)
							{
								int part2int = (int) theTokenizer.maxFreq.get(theTokenizer.namesOfFiles.get(j));//groupOfWords.get(k));
								part2 = (double) part2int;
							}
							else
							{
								//System.out.println(j);
								//System.out.println(theTokenizer.namesOfFiles.get(j));
								//System.out.println(theTokenizer.maxFreq.get(theTokenizer.namesOfFiles.get(j)));
								part2 = (double) theTokenizer.maxFreq.get(theTokenizer.namesOfFiles.get(j));//groupOfWords.get(k));
							}
							
							double tf =  part1/part2;
							double idf = Math.log(322.0/(double)wordMap.size())/Math.log(2);
							double tfAndIdf = tf*idf;
							score = score + tfAndIdf;			
						}	
					}
				}
				if(topList.size() == 0)
				{
					TopScores curTopScore = new TopScores();
					curTopScore.setDocumentID(theTokenizer.namesOfFiles.get(j));
					curTopScore.setRankingScore(score);
					topList.add(curTopScore);
				}
				for(int z = 0; z<topList.size(); z++)
				{//System.out.println("here1");
					if(score>topList.get(z).getRankingScore())
					{
						TopScores curTopScore = new TopScores();
						curTopScore.setDocumentID(theTokenizer.namesOfFiles.get(j));
						curTopScore.setRankingScore(score);
						curTopScore.setFirstSentance(allText.get(i));
						topList.add(z, curTopScore);
						break;
					}
				}
				if(topList.size()>5)
				{
					topList.remove(5);
				}
			
			}
			//System.out.println("Word: "+ allText.get(i));
			for(int y = 0; y<topList.size(); y++)
			{
				//System.out.println(y);
				//System.out.println(topList.get(y).getDocumentID());
				//System.out.println(topList.get(y).getRankingScore());
				//System.out.println("SaidWord: " + topList.get(y).getFirstSentance());
			}
			for(int h =0; h<groupOfWords.size(); h++)
			{
				//System.out.println(groupOfWords.get(h));
			}
			return topList;
		}
		return null;

	}
}
