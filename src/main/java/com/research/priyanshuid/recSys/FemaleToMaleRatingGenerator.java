package com.research.priyanshuid.recSys;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class FemaleToMaleRatingGenerator {

	public static double fmRatingMatrix[][]= new double[5002][5002];

	public static void generateNewMatrix(int k) throws IOException, TasteException {
		UserRecommender.computeFemaleRecommendations(k);
		for(int i=1;i<=5001;i++){
			for(int j=1;j<=5001;j++){
				fmRatingMatrix[i][j]=UserRecommender.fmRatingMatrix[i][j];
			}
		}
	}

}
