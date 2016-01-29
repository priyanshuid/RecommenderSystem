package com.research.priyanshuid.recSys;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class MaleToFemaleRatingGenerator {
	
	public double mfRatingMatrix[][]= new double[5002][5002];
	
	public void generateNewMatrix(int k, UserRecommender ob) throws IOException, TasteException {
		ob.computeMaleRecommendations(k);
		for(int i=1;i<=5001;i++){
			for(int j=1;j<=5001;j++){
				mfRatingMatrix[i][j]=ob.mfRatingMatrix[i][j];
			}
		}
	}
}
