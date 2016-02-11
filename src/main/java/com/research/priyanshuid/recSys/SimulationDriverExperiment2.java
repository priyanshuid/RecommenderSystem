package com.research.priyanshuid.recSys;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

public class SimulationDriverExperiment2 {

	public static Double THRESHOLD= 7.0;
	public static Double PARTITION= 0.3;
	public int[] 	 result;

	public Boolean partitionAlreadyGenerated= false;
	public Integer numberOfTestHitsMatching=0;
	public Integer numberOfTestHitsRandom= 0;

	public Double[][] testMF= new Double[5001][5001];
	public Double[][] testFM= new Double[5001][5001];

	public void partitionDataSet(String inputFilePath,String testFilePath,String trainFilePath) throws FileNotFoundException{
		DataPartitioner dp = new DataPartitioner();
		dp.partitionDataRandomly(inputFilePath, testFilePath, trainFilePath, PARTITION);
	}
	public void calculateMatchingForTwoWayRating(int k, MatrixAverager ob, String outputFilePath) throws IOException, TasteException
	{
		BufferedWriter bw= new BufferedWriter(new FileWriter(outputFilePath));
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
	public void generateTestRatingMatrix(String testFilePath, Double matrix[][]){
		BufferedReader br= null;
		String line="";
		try{
			br= new BufferedReader(new FileReader(testFilePath));
			while((line= br.readLine())!=null){
				String []values= line.split(",");
				int f, s;
				f= Integer.parseInt(values[0].trim());
				s= Integer.parseInt(values[1].trim());
				Double rating= Double.parseDouble(values[2].trim());
				matrix[f][s]= rating;
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(br!=null){

				try{
					br.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	public Double calculateDifferenceMetric(String matchingFilePath, Integer count) throws FileNotFoundException{
		Double result=0.0;
		BufferedReader br; 
		String line="";
		try{
			br= new BufferedReader(new FileReader(matchingFilePath));
			while((line= br.readLine())!=null){
				String []values= line.split(",");
				int f, s;
				f= Integer.parseInt(values[0].trim());
				s= Integer.parseInt(values[1].trim());
				if(testMF[f][s]!=0.0 && testFM[s][f]!=0.0){
					if(testMF[f][s]>=THRESHOLD || testFM[f][s]>=THRESHOLD){
						result=result+ Math.abs(testMF[f][s]-testFM[s][f]);
						count++;
					}
				}
			}	
		}catch(Exception E){
			System.out.println(E.toString());
		}
		return result;
	}

	public void simulate(String mfFilePath, String fmFilePath, String matchingResultPath, String randomResultPath, int k) throws IOException, TasteException{

		String trnMF= "";
		String tstMF= "";
		String trnFM= "";
		String tstFM= "";

		if(!partitionAlreadyGenerated){
			partitionDataSet(mfFilePath, tstMF, trnMF);
			partitionDataSet(fmFilePath, tstFM, trnFM);
			generateTestRatingMatrix(tstFM, testFM);
			generateTestRatingMatrix(tstMF, testMF);
			partitionAlreadyGenerated=true;
		}
		UserRecommender urob=new UserRecommender();
		urob.computeMaleRecommendations(trnMF, k);
		urob.computeFemaleRecommendations(trnFM, k);

		MatrixAverager avgob= new MatrixAverager();
		avgob.generateNewMatrix(k, urob);

		calculateMatchingForTwoWayRating(k, avgob, matchingResultPath);

		RandomMatcher rmob= new RandomMatcher();
		rmob.matchRandomProfiles(randomResultPath, avgob.weightedRatingMatrix);
		
		Double matchingDifferenceScore= calculateDifferenceMetric(matchingResultPath, numberOfTestHitsMatching);
		Double randomDifferenceScore= calculateDifferenceMetric(randomResultPath, numberOfTestHitsRandom);
		System.out.println("MatchingDifferenceScore=>"+ matchingDifferenceScore);
		System.out.println("RandomMatchingDifferenceScore=>"+randomDifferenceScore);
	}
	public static void main(String args[]) throws IOException, TasteException{
		SimulationDriverExperiment2 sde= new SimulationDriverExperiment2();
		int k=50;
		String mfFilePath= "";
		String fmFilePath= "";
		String matchingResultPath="";
		String randomResultPath= "";
		sde.simulate(mfFilePath, fmFilePath, matchingResultPath, randomResultPath, k);
	}

}
