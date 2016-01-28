package com.research.priyanshuid.recSys;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MetricScoreCalculator {
	
	public class pair{
		public int r1, r2;
		
		public pair(){
			r1=0;
			r2=0;
		}
		
		public pair(int a, int b){
			r1=a;
			r2=b;
		}
	}
	
	public pair countMatchingPoints(String actualDataPath, String generatedResultPath, int flag){
		boolean markedWith10[]= new boolean[5001];
		boolean markedWithout10[]= new boolean[5001];
		boolean actual[][]= new boolean[5002][5002];
		for(int r=0;r<=5001;r++)
			for(int c=0;c<=5001;c++)
				actual[r][c]=false;
		BufferedReader br= null;
		String line="";
		
		try{
			
			br= new BufferedReader(new FileReader(actualDataPath));
			while((line= br.readLine())!=null){
				String []values= line.split(",");
				int f, s;
				f= Integer.parseInt(values[0].trim());
				s= Integer.parseInt(values[1].trim());
				actual[f][s]=true;
				
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
		Integer counterWithout10= new Integer(0);
		Integer counterWith10= new Integer(0);
		try{
			br= new BufferedReader(new FileReader(generatedResultPath));
			while((line= br.readLine())!=null){
				String []values= line.split(",");
				int f, s;
				double rating;
				f= Integer.parseInt(values[0].trim());
				s= Integer.parseInt(values[1].trim());
				rating = Double.parseDouble(values[2].trim());
				
				if(flag==0){
					int temp=f;
					f= s;
					s= temp;
				}
				if(Double.compare(rating, 10.0)==0 && actual[f][s]){
					markedWith10[f]=true;
				}
				if(actual[f][s])
					markedWithout10[f]=true;
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for(int i=1;i<=5000;i++){
			if(markedWithout10[i])
				counterWithout10++;
			if(markedWith10[i])
				counterWith10++;
		}
		pair p= new pair(counterWith10, counterWithout10);
		return p;
	}
		
		public static void main(String args[]){
			MetricScoreCalculator newCalculator= new MetricScoreCalculator();
			//String mfMatchingFilePath= "output/maximumMatchingMaleToFemale.csv";
			//String fmMatchingFilePath= "output/maximumMatchingFemaleToMale.csv";
			
			String testMFFilePath= "data/testMtoF18Jan.csv";
			String testFMFilePath= "data/testFtoM18Jan.csv";
			
			//pair p1= newCalculator.countMatchingPoints(testMFFilePath, mfMatchingFilePath, 0);
			//pair p2= newCalculator.countMatchingPoints(testFMFilePath, fmMatchingFilePath, 0);
			
			//System.out.println("Male to female correct predictions::with10Rating "+p1.r1+"  without10Rating "+p1.r2);
			//System.out.println("Female to male correct predictions::with10Rating "+p2.r1+"  without10Rating "+p2.r2);
			
			
			String matchFilePath= "output/maximum_matching_with_ratings.csv";
			
			pair p3= newCalculator.countMatchingPoints(testMFFilePath, matchFilePath, 0);
			pair p4= newCalculator.countMatchingPoints(testFMFilePath, matchFilePath, 1);
			
			String UBCFMFFilePath= "output/UBCFTopMtoF.csv";
			String UBCFFMFilePath= "output/UBCFTopFtoM.csv";
			pair p5= newCalculator.countMatchingPoints(testMFFilePath, UBCFMFFilePath, 0);
			pair p6= newCalculator.countMatchingPoints(testFMFilePath, UBCFFMFilePath, 0);
			
			System.out.println("UBCF Male to female correct predictions::"+p5.r1);
			System.out.println("UBCF Female to male correct predictions::"+p6.r1);
			
			System.out.println("UBCF Algorithm total number of correct predictions::"+(p5.r1+p6.r1));
			System.out.println("Matching Algorithm total number of correct predictions::"+(p3.r2+p4.r2));
	}
}
