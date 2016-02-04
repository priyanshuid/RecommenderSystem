package com.research.priyanshuid.recSys;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

public class SimulationDriver {

	private static final Double PARTITION= 0.3; 

	public String fmRawRatingsFilePath= "data/fmFinalRatings12Jan.csv";
	public String mfRawRatingsFilePath= "data/mfFinalRatings12Jan.csv";
	private Integer partInt= (int) (PARTITION*10);

	public String testMFRatingPath= "data/testMF"+ partInt.toString() +".csv";
	public String trainMFRatingPath= "data/trainMF"+partInt.toString()+".csv";

	public String testFMRatingPath= "data/testFM"+ partInt.toString() +".csv";
	public String trainFMRatingPath= "data/trainFM"+partInt.toString()+".csv";

	private static final int SZ=5001;
	public int[] 	 result;
	public ArrayList<matchingEntry> entries;

	public double maleUserRecommenderScore=0;
	public double femaleUserRecommenderScore=0;

	public double avgUserRecommenderScore=0;

	public double matchingRecommenderScore=0;
	public double avgMatchingRecommenderScore=0;

	private static final int TOTALUSERS=5000;

	public void scoreTwoWayRatings(int k, UserRecommender ob, MatrixAverager avgob) throws IOException, TasteException{
		entries= new ArrayList<matchingEntry>();
		for(int index=0;index<SZ-1;index++){
			matchingEntry newEntry= new matchingEntry(index+1, result[index], avgob.weightedRatingMatrix[index+1][result[index]]);
			entries.add(newEntry);
		}
		for(int i=1;i<=5000;i++)
		{
			maleUserRecommenderScore+= ob.mfRatingMatrix[i][ob.mTopRec[i]];
			femaleUserRecommenderScore+= ob.fmRatingMatrix[i][ob.fTopRec[i]];
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

	//	public static void scoreOneWayRatingsMaleToFemale(int k) throws IOException, TasteException{
	//		matchingRecommenderScore=0;
	//		
	//		entries= new ArrayList<matchingEntry>();
	//		for(int index=0;index<SZ-1;index++){
	//			matchingEntry newEntry= new matchingEntry(index+1, HungarianBipartiteMatching.result[index], MaleToFemaleRatingGenerator.mfRatingMatrix[index+1][HungarianBipartiteMatching.result[index]]);
	//			entries.add(newEntry);
	//		}
	//		for(int i=1;i<=5000;i++)
	//		{
	//			maleUserRecommenderScore+= UserRecommender.mfRatingMatrix[i][UserRecommender.mTopRec[i]];
	//		}
	//		avgUserRecommenderScore= (maleUserRecommenderScore)/TOTALUSERS;
	//		for(matchingEntry entry:entries){
	//			matchingRecommenderScore+=entry.rating;
	//		}
	//		avgMatchingRecommenderScore= matchingRecommenderScore/TOTALUSERS;
	//		
	//		System.out.println("Average Male to Female User Recommender System Score:"+ avgUserRecommenderScore);
	//		System.out.println("Average Male to Female Matching Recommender System Score:"+ avgMatchingRecommenderScore);
	//		
	//	}
	//	public static void scoreOneWayRatingsFemaleToMale(int k) throws IOException, TasteException{
	//		matchingRecommenderScore=0;
	//		
	//		entries= new ArrayList<matchingEntry>();
	//		for(int index=0;index<SZ-1;index++){
	//			matchingEntry newEntry= new matchingEntry(index+1, HungarianBipartiteMatching.result[index], FemaleToMaleRatingGenerator.fmRatingMatrix[index+1][HungarianBipartiteMatching.result[index]]);
	//			entries.add(newEntry);
	//		}
	//		for(int i=1;i<=5000;i++)
	//		{
	//			femaleUserRecommenderScore+= UserRecommender.fmRatingMatrix[i][UserRecommender.fTopRec[i]];
	//		}
	//		avgUserRecommenderScore= (femaleUserRecommenderScore)/TOTALUSERS;
	//		for(matchingEntry entry:entries){
	//			matchingRecommenderScore+=entry.rating;
	//		}
	//		avgMatchingRecommenderScore= matchingRecommenderScore/TOTALUSERS;
	//		
	//		System.out.println("Average Female to Male User Recommender System Score:"+ avgUserRecommenderScore);
	//		System.out.println("Average FemaleTo Male Matching Recommender System Score:"+ avgMatchingRecommenderScore);
	//	}

	public void calculateMatchingForTwoWayRating(int k, MatrixAverager ob) throws IOException, TasteException
	{
		BufferedWriter bw= new BufferedWriter(new FileWriter("output/maximum_matching_with_ratings.csv"));
		int r= 5001, c=5001;
		double[][] cost = new double[5001][5001];
		for (int i = 0; i < r; i++)
		{
			for (int j = 0; j < c; j++)
			{
				cost[i][j] =10-ob.weightedRatingMatrix[i+1][j+1] ;
			}
		}
		HungarianBipartiteMatching hbm = new HungarianBipartiteMatching(cost);
		result = hbm.execute();
		for(int i=0;i<result.length;i++)
			result[i]+=1;
		System.out.println("Bipartite Matching"+result.toString());
		System.out.println("calculated matched matrix");
		for(int i=0;i<result.length-1;i++){
			bw.write(i+1+","+result[i]+","+ob.weightedRatingMatrix[i+1][result[i]]);
			bw.write("\n");
		}
		bw.close();
	}
	public void generatePartition() throws FileNotFoundException{
		DataPartitioner dp= new DataPartitioner();
		dp.partitionDataRandomly(mfRawRatingsFilePath, testMFRatingPath, trainMFRatingPath, PARTITION);
		dp.partitionDataRandomly(fmRawRatingsFilePath, testFMRatingPath, trainFMRatingPath, PARTITION);
	}

	public static void main(String[] args) throws IOException, TasteException {

		int ar[]= {5, 10, 20, 40, 60};

		for(int i=0;i<=4;i++){
			int k=ar[i];
			System.out.println(k);
			/**
			 * Hungarian bipartite matching for one way ratings
			 * uncomment this code to calculate one way ratings for male/female users.
			 */
			//HungarianBipartiteMatching.calculateMatchingForOneWayRatingMale(k);
			//HungarianBipartiteMatching.calculateMatchingForOneWayRatingFemale(k);
			//scoreOneWayRatingsFemaleToMale(k);
			//scoreOneWayRatingsMaleToFemale(k);

			SimulationDriver scob= new SimulationDriver();
			scob.generatePartition();

			UserRecommender urob= new UserRecommender();
			urob.computeFemaleRecommendations(scob.trainFMRatingPath, k);
			urob.computeMaleRecommendations(scob.trainMFRatingPath, k);

			MatrixAverager avgob= new MatrixAverager();
			avgob.generateNewMatrix(k, urob);

			scob.calculateMatchingForTwoWayRating(k, avgob);

			scob.scoreTwoWayRatings(k, urob, avgob);
			System.out.println("Done scoring");

			String input= "output/femaleToMaleRec.csv";
			String output= "output/UBCFTopFtoM.csv";
			UBCFRatingExtrator.extractTopRatings(input, output, urob.fmRatingMatrix);
			System.out.println("Done for FM");
			input = "output/maleToFemaleRec.csv";
			output= "output/UBCFTopMtoF.csv";
			UBCFRatingExtrator.extractTopRatings(input, output, urob.mfRatingMatrix);
			System.out.println("k="+k+"done");
			MetricScoreCalculator mscob= new MetricScoreCalculator();
			mscob.calculate();
		}
	}
}