package com.research.priyanshuid.recSys;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.mahout.cf.taste.common.TasteException;

class matchingEntry{

	public int mID, fID;
	public double rating;
	matchingEntry(int m, int f, double r){
		this.mID = m;
		this.fID = f;
		this.rating= r;
	}
	matchingEntry(){
		mID=0;
		fID=0;
		rating=0.0;
	}
}

public class ScoreCalculator {
	private static final int SZ=5001;
	public static ArrayList<matchingEntry> entries;
	
	public static double maleUserRecommenderScore=0;
	public static double femaleUserRecommenderScore=0;
	
	public static double avgUserRecommenderScore=0;
	
	public static double matchingRecommenderScore=0;
	public static double avgMatchingRecommenderScore=0;
	
	private static final int TOTALUSERS=5000;
	
	public static void scoreTwoWayRatings(int k) throws IOException, TasteException{
		HungarianBipartiteMatching.calculateMatchingForTwoWayRating(k);
		entries= new ArrayList<matchingEntry>();
		for(int index=0;index<SZ-1;index++){
			matchingEntry newEntry= new matchingEntry(index+1, HungarianBipartiteMatching.result[index], MatrixAverager.weightedRatingMatrix[index+1][HungarianBipartiteMatching.result[index]]);
			entries.add(newEntry);
		}
		for(int i=1;i<=5000;i++)
		{
			maleUserRecommenderScore+= UserRecommender.mfRatingMatrix[i][UserRecommender.mTopRec[i]];
			femaleUserRecommenderScore+= UserRecommender.fmRatingMatrix[i][UserRecommender.fTopRec[i]];
		}
		avgUserRecommenderScore= (maleUserRecommenderScore+femaleUserRecommenderScore)/TOTALUSERS;
		avgUserRecommenderScore= avgUserRecommenderScore/2;
		for(matchingEntry entry:entries){
			matchingRecommenderScore+=entry.rating;
		}
		avgMatchingRecommenderScore= matchingRecommenderScore/TOTALUSERS;
		
		System.out.println("Average User Recommender System Score:"+ avgUserRecommenderScore);
		System.out.println("Average Matching Recommender System Score:"+ avgMatchingRecommenderScore);
	}
	
	public static void scoreOneWayRatingsMaleToFemale(int k) throws IOException, TasteException{
		matchingRecommenderScore=0;
		
		entries= new ArrayList<matchingEntry>();
		for(int index=0;index<SZ-1;index++){
			matchingEntry newEntry= new matchingEntry(index+1, HungarianBipartiteMatching.result[index], MaleToFemaleRatingGenerator.mfRatingMatrix[index+1][HungarianBipartiteMatching.result[index]]);
			entries.add(newEntry);
		}
		for(int i=1;i<=5000;i++)
		{
			maleUserRecommenderScore+= UserRecommender.mfRatingMatrix[i][UserRecommender.mTopRec[i]];
		}
		avgUserRecommenderScore= (maleUserRecommenderScore)/TOTALUSERS;
		for(matchingEntry entry:entries){
			matchingRecommenderScore+=entry.rating;
		}
		avgMatchingRecommenderScore= matchingRecommenderScore/TOTALUSERS;
		
		System.out.println("Average Male to Female User Recommender System Score:"+ avgUserRecommenderScore);
		System.out.println("Average Male to Female Matching Recommender System Score:"+ avgMatchingRecommenderScore);
		
	}
	public static void scoreOneWayRatingsFemaleToMale(int k) throws IOException, TasteException{
		matchingRecommenderScore=0;
		
		entries= new ArrayList<matchingEntry>();
		for(int index=0;index<SZ-1;index++){
			matchingEntry newEntry= new matchingEntry(index+1, HungarianBipartiteMatching.result[index], FemaleToMaleRatingGenerator.fmRatingMatrix[index+1][HungarianBipartiteMatching.result[index]]);
			entries.add(newEntry);
		}
		for(int i=1;i<=5000;i++)
		{
			femaleUserRecommenderScore+= UserRecommender.fmRatingMatrix[i][UserRecommender.fTopRec[i]];
		}
		avgUserRecommenderScore= (femaleUserRecommenderScore)/TOTALUSERS;
		for(matchingEntry entry:entries){
			matchingRecommenderScore+=entry.rating;
		}
		avgMatchingRecommenderScore= matchingRecommenderScore/TOTALUSERS;
		
		System.out.println("Average Female to Male User Recommender System Score:"+ avgUserRecommenderScore);
		System.out.println("Average FemaleTo Male Matching Recommender System Score:"+ avgMatchingRecommenderScore);
	}
	public static void main(String[] args) throws IOException, TasteException {
		int k=500;
		/**
		 * Hungarian bipartite matching for one way ratings
		 * uncomment this code to calculate one way ratings for male/female users.
		 */
		//HungarianBipartiteMatching.calculateMatchingForOneWayRatingMale(k);
		//HungarianBipartiteMatching.calculateMatchingForOneWayRatingFemale(k);
		//scoreOneWayRatingsFemaleToMale(k);
		//scoreOneWayRatingsMaleToFemale(k);
		UserRecommender.computeMaleRecommendations(k);
		UserRecommender.computeFemaleRecommendations(k);
		System.out.println("Scoring two way ratings");
		
		scoreTwoWayRatings(k);
		System.out.println("Done scoring");
		System.out.println("Extracting ratings");
		
		String input= "output/femaleToMaleRec.csv";
		String output= "output/UBCFTopFtoM.csv";
		UBCFRatingExtrator.extractTopRatings(input, output, UserRecommender.fmRatingMatrix);
		System.out.println("Done for FM");
		input = "output/maleToFemaleRec.csv";
		output= "output/UBCFTopMtoF.csv";
		UBCFRatingExtrator.extractTopRatings(input, output, UserRecommender.mfRatingMatrix);
		System.out.println("Done for MF");
		System.out.println("done");
	}
}
