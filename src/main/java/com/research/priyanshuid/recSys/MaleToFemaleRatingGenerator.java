package com.research.priyanshuid.recSys;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class MaleToFemaleRatingGenerator {
	
	public static double mfRatingMatrix[][]= new double[5002][5002];
	
	public static void generateNewMatrix(int k) throws IOException, TasteException {
		UserRecommender.computeMaleRecommendations(k);
		for(int i=1;i<=5001;i++){
			for(int j=1;j<=5001;j++){
				mfRatingMatrix[i][j]=UserRecommender.mfRatingMatrix[i][j];
			}
		}
	}
}
