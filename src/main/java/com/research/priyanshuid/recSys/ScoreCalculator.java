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
	public static void main(String[] args) throws IOException, TasteException {
		int k=20;
		HungarianBipartiteMatching.calculateMatrix(k);
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

}
