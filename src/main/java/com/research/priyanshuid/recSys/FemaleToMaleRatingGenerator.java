package com.research.priyanshuid.recSys;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class FemaleToMaleRatingGenerator {

	public double fmRatingMatrix[][]= new double[5002][5002];

	public void generateNewMatrix(int k, UserRecommender ob) throws IOException, TasteException {
		ob.computeFemaleRecommendations(k);
		for(int i=1;i<=5001;i++){
			for(int j=1;j<=5001;j++){
				fmRatingMatrix[i][j]=ob.fmRatingMatrix[i][j];
			}
		}
	}

}
