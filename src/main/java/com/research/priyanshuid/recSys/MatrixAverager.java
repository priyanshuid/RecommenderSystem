package com.research.priyanshuid.recSys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class MatrixAverager {
	
	public int uSize=100;
	public Double weightedRatingMatrix[][]= new Double[uSize+2][uSize+2];	
	public void generateNewMatrix(int k, UserRecommender ob) throws IOException, TasteException {
		/**
		 * uncomment these lines if you want to run one way recommendation separately from 
		 * two way recommendation. Else it might not work.
		 */
		int count=0;
		for(int i=1;i<=uSize+1;i++){
			for(int j=1;j<=uSize+1;j++){
				System.out.println(count++);
				weightedRatingMatrix[i][j]=(ob.mfRatingMatrix[i][j]+ob.fmRatingMatrix[j][i])/2;
				//weightedRatingMatrix[i][j]=Math.max(ob.mfRatingMatrix[i][j],ob.fmRatingMatrix[j][i]);
			}
		}
		BufferedWriter bw= new BufferedWriter(new FileWriter("output/weightedRatingMatrix.csv"));
		System.out.println("finished printing matrix");
		bw.close();
	}
}
