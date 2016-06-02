package proj1;

public class TopScores {
	private String documentID = "";
	private int rank = 0;
	private String firstSentance;
	private double rankingScore = 0;
	
	public String getDocumentID() 
	{
		return documentID;
	}
	public void setDocumentID(String documentID) 
	{
		this.documentID = documentID;
	}
	public int getRank() 
	{
		return rank;
	}
	public void setRank(int rank) 
	{
		this.rank = rank;
	}
	public String getFirstSentance() 
	{
		return firstSentance;
	}
	public void setFirstSentance(String firstSentance) 
	{
		this.firstSentance = firstSentance;
	}
	public double getRankingScore() 
	{
		return rankingScore;
	}
	public void setRankingScore(double rankingScore) 
	{
		this.rankingScore = rankingScore;
	}
	
}
