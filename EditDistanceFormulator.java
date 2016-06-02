package spelling;

import java.util.ArrayList;

public class EditDistanceFormulator {
	public ArrayList<String> calculateEditDist(ArrayList <String> allPossibilities, String theWord)
	{
		ArrayList<String> closestPossibilities = new ArrayList<String>();
		for(int i = 0; i < allPossibilities.size() ; i++)
		{
			if(editDist(theWord, allPossibilities.get(i))<3)
			{
				closestPossibilities.add(allPossibilities.get(i));
			}
		}
		return closestPossibilities;
	}
	
	public int editDist(String word1a, String word2a)
	{
		String word1 = word1a.toLowerCase();
		String word2 = word2a.toLowerCase();
		int[][] d = new int [word1.length()+1][word2.length()+1];
		int cost = 0;
		
		for(int i = 0; i<=word1.length(); i++)
		{
			d[i][0] = i;
		}
		for(int j =0; j<=word2.length(); j++)
		{
			d[0][j] = j;
		}
		
		for(int i  = 1; i<=word1.length(); i++)
		{
			for(int j = 1; j<=word2.length(); j++)
			{
				if(word1.charAt(i-1) == word2.charAt(j-1))
				{
					cost = 0;
				}
				else
				{
					cost = 1;
				}
				int deletion = d[i-1][j] +1;
				int insertion = d[i][j-1]+1;
				int substitution = d[i-1][j-1]+cost;
				d[i][j] = Math.min(Math.min(deletion, insertion), substitution);
			}
		}
		return d[word1.length()][word2.length()];
	}
}
