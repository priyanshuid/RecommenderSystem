package com.research.priyanshuid.recSys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class RawDataGenerator {
	
	public static final int numberOfIds= 5000;
	public static final int numberOfUsers= 100;
	
	public double mfRatingMatrix[][]= new double[numberOfUsers][numberOfIds];
	public double fmRatingMatrix[][]= new double[numberOfUsers][numberOfIds];
	
	public class tuple{
		int id;
		int frequency;
		
		tuple(){
			id= 0;
			frequency=0;
			
		}
		tuple(int id, int frequency){
			this.id= id;
			this.frequency= frequency;
		}
	}
	protected ArrayList<tuple> freqMList= new ArrayList<tuple>();
	protected ArrayList<tuple> freqFList= new ArrayList<tuple>();
	
	public void countMostFrequentData(String filePath, ArrayList <tuple> mList, ArrayList<tuple> fList){
		ArrayList<tuple> list= new ArrayList<tuple>();
		BufferedReader br;
		try{
			br= new BufferedReader(new FileReader(filePath));
			String line;
			
			while((line=br.readLine())!=null){
				String values[]= line.split(",");
				
				int first, second;
				double rating;
				first= Integer.parseInt(values[0].trim());
				second= Integer.parseInt(values[1].trim());
				rating= Double.parseDouble(values[2]);
			}
			
		}catch(Exception Ex){
			
		}
	}
}
