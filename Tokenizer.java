package proj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import spelling.StopWords;

public class Tokenizer {
	public HashMap theMap = new HashMap();
	public HashMap maxFreq = new HashMap();
	public ArrayList<String> namesOfFiles = new ArrayList<String>();
	public HashMap parseFiles()
	{

		//Open all files, this goes through each of your files
		File allFiles = new File("docs/");
		File[] listOfFiles = allFiles.listFiles();
		int tester = 0;
		for(File curFile: listOfFiles)
		{
			tester++;
			//System.out.println(curFile.getName());
			//Scan through that file
			try {
				//System.out.println(curFile.getName());
				//System.out.println("!!!!!!!!!!!!!!!");
				Scanner scans = new Scanner(curFile);
				namesOfFiles.add(curFile.getName());
				while(scans.hasNextLine())
				{
					String curLine = scans.nextLine();
					StringTokenizer token = new StringTokenizer(curLine," ,\n\t\r.,?!;:\")(*&^%$#@-_'][");
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
									//System.out.println(finalWord);
									if(theMap.containsKey(finalWord))
									{
										HashMap updateMap = (HashMap) theMap.get(finalWord);
										if(updateMap.containsKey(curFile.getName()))
										{
											double freqCount;
											if(updateMap.get(curFile.getName()) instanceof Integer)
											{
												int freqCount2 = (int) updateMap.get(curFile.getName());
												freqCount = (double) freqCount2;
											}
											else
											{
												freqCount = (double) updateMap.get(curFile.getName());
											}
											
											freqCount = freqCount +1;
											updateMap.put(curFile.getName(),freqCount);
											theMap.put(finalWord, updateMap);
											if(maxFreq.containsKey(curFile.getName()))
											{
												if((double)maxFreq.get(curFile.getName())< freqCount)//finalWord)<freqCount)
												{
													maxFreq.put(curFile.getName(), freqCount);//finalWord, freqCount);
												}
											}
											else
											{
												maxFreq.put(curFile.getName(), freqCount);
											}
												
										}
										else
										{
											updateMap.put(curFile.getName(), 1);
											theMap.put(finalWord, updateMap);
											double freqCount = 1;
											if(maxFreq.containsKey(curFile.getName()))
											{
												if((double)maxFreq.get(curFile.getName())< freqCount)//finalWord)<freqCount)
												{
													maxFreq.put(curFile.getName(), freqCount);//finalWord, freqCount);
												}
											}
											else
											{
												maxFreq.put(curFile.getName(), freqCount);
											}
										}
									}
									else
									{
										HashMap<String, Double> wordMap = new HashMap<String, Double>();
										double freqCount = 1;
										wordMap.put(curFile.getName(), freqCount);
										theMap.put(finalWord, wordMap);
										//
										if(maxFreq.containsKey(curFile.getName()))
										{
											if((double)maxFreq.get(curFile.getName())< freqCount)//finalWord)<freqCount)
											{
												maxFreq.put(curFile.getName(), freqCount);//finalWord, freqCount);
											}
										}
										else
										{
											maxFreq.put(curFile.getName(), freqCount);
										}
										//
										////maxFreq.put(curFile.getName(), freqCount);//finalWord, freqCount);
										//System.out.println("added word");
									}
								}
							}
						}
						//System.out.println(curWordLower);
					}
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(theMap.containsKey("appear"));
			//System.out.println("does appear in the")
			//HashMap testMap = (HashMap) theMap.get("appear");
			//System.out.println(testMap.containsKey(curFile.getName()));
			if(tester == 322)
			{
				break;
			}
		}
		/*HashMap testMap = (HashMap) theMap.get("appear");
		for(int i = 0; i<namesOfFiles.size(); i++)
		{
			if(testMap.containsKey(namesOfFiles.get(i)))
			{
				System.out.println(namesOfFiles.get(i));
				System.out.println(testMap.get(namesOfFiles.get(i)));
			}
		}*/
		//System.out.println("I'm done.");
		return theMap;
		
		
	}
	
}
