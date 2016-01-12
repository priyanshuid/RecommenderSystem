package com.research.priyanshuid.recSys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class MatrixRecalculator {
	
	public static double weightedRatingMatrix[][]= new double[5002][5002];
	
	public static int newListMale[][]= new int[5002][100];
	public static int newListFemale[][]= new int[5002][100];
	
	public static void generateNewMatrix(int k) throws IOException, TasteException {
		UserRecommender.computeRecommendations(k);
		
		for(int i=1;i<=5001;i++)
			for(int j=1;j<=5001;j++){
				weightedRatingMatrix[i][j]=0.0;
			}
		for(int i=1;i<=5001;i++){
			for(int j=1;j<=5001;j++){
				weightedRatingMatrix[i][j]=(UserRecommender.mfRatingMatrix[i][j]+UserRecommender.fmRatingMatrix[j][i])/2;
			}
		}
		BufferedWriter bw= new BufferedWriter(new FileWriter("output/weightedRatingMatrix.csv"));
		/*for(int i=1;i<=5001;i++){
			for(int j=1;j<=5001;j++)
				bw.write(weightedRatingMatrix[i][j]+",");
			bw.write("\n");
		}*/
		System.out.println("finished printing matrix");
		bw.close();
	}
}
