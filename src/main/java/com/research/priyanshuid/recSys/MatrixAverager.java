package com.research.priyanshuid.recSys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class MatrixAverager {
	
	public static double weightedRatingMatrix[][]= new double[5002][5002];	
	public static void generateNewMatrix(int k) throws IOException, TasteException {
	
		/**
		 * uncomment these lines if you want to run one way recommendation separately from 
		 * two way recommendation. Else it might not work.
		 */
		for(int i=1;i<=5001;i++){
			for(int j=1;j<=5001;j++){
				weightedRatingMatrix[i][j]=(UserRecommender.mfRatingMatrix[i][j]+UserRecommender.fmRatingMatrix[j][i])/2;
			}
		}
		BufferedWriter bw= new BufferedWriter(new FileWriter("output/weightedRatingMatrix.csv"));
		System.out.println("finished printing matrix");
		bw.close();
	}
}
