package spelling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SoundexCreator {
	HashMap<String, ArrayList<String>> soundexHash = new HashMap();
	public HashMap<String, ArrayList<String>> createHashMap()
	{
		
		File theDictionary = new File("dictionary.txt");
		Scanner scans;
		try {
			scans = new Scanner(theDictionary);
			while(scans.hasNext())
			{
				String curWord = scans.next();
				
				String theSoundexCode = soundExThis(curWord);
			
				if(soundexHash.containsKey(theSoundexCode))
				{
					ArrayList<String> curList = (ArrayList)soundexHash.get(theSoundexCode);
					curList.add(curWord);
					soundexHash.put(theSoundexCode, curList);
				}
				else
				{
					ArrayList<String> curList = new ArrayList<String>();
					curList.add(curWord);
					soundexHash.put(theSoundexCode, curList);
				}
						
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return soundexHash;
	}
	
	public String soundExThis(String curWord)
	{
		String makeUpperCase = curWord.substring(0, 1).toUpperCase();
		String takeOutVowels = curWord.substring(1);
		
		//To replace vowels
		takeOutVowels = takeOutVowels.replace('a','-');
		takeOutVowels = takeOutVowels.replace('e','-');
		takeOutVowels = takeOutVowels.replace('i','-');
		takeOutVowels = takeOutVowels.replace('o','-');
		takeOutVowels = takeOutVowels.replace('u','-');
		takeOutVowels = takeOutVowels.replace('y','-');
		takeOutVowels = takeOutVowels.replace('h','-');
		takeOutVowels = takeOutVowels.replace('w','-');
		
		String replaceLettersWithNumbers = takeOutVowels;
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('b', '1');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('f', '1');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('p', '1');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('v', '1');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('c', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('g', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('j', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('k', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('q', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('s', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('x', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('z', '2');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('d', '3');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('t', '3');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('l', '4');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('m', '5');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('n', '5');
		replaceLettersWithNumbers = replaceLettersWithNumbers.replace('r', '6');
		
		String startDeletions = replaceLettersWithNumbers;
		ArrayList<String> theWord = new ArrayList<String>();
		for(int i  = 0; i < startDeletions.length(); i++)
		{
			theWord.add(String.valueOf(startDeletions.charAt(i)));
		}
		
		String prevLetter = "";
		for(int i = 0; i <theWord.size(); i++)
		{
			if(theWord.get(i).equals(prevLetter) && !prevLetter.equals("-"))
			{
				theWord.remove(i);
				i--;
			}
			prevLetter = theWord.get(i);
		}
		
		for(int i = 0; i <theWord.size(); i++)
		{
			if(theWord.get(i).equals("-"))
			{
				theWord.remove(i);
				i--;
			}
		}
		
		while(theWord.size()<4)
		{
			theWord.add("0");
		}
		String theSoundexCode = makeUpperCase + theWord.get(0) + theWord.get(1) + theWord.get(2);
		return theSoundexCode;
	}
}
