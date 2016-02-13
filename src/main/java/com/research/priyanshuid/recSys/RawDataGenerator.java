package com.research.priyanshuid.recSys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class RawDataGenerator {

	public static final int numberOfIds= 5000;
	public static final int numberOfUsers= 100;

	public class Tuple implements Comparable<Tuple>{
		int id;
		int frequency;

		Tuple(){
			id= 0;
			frequency=0;

		}
		Tuple(int id, int frequency){
			this.id= id;
			this.frequency= frequency;
		}
		public int compareTo(Tuple t1) {			
			int compareFrequency = ((Tuple) t1).frequency; 
			return compareFrequency- this.frequency;
			
		}
	}
	public Tuple mFreq[]= new Tuple[numberOfIds+1];
	public Tuple fFreq[]= new Tuple[numberOfIds+1];
	int selectedMaleIDs[]= new int[numberOfUsers+1];
	int selectedFemaleIDs[]= new int[numberOfUsers+1];
	public void countMostFrequentData(String filePath, Tuple mFreq[], Tuple fFreq[], boolean flag){
		BufferedReader br;
		for(int set=0;set<=5000;set++){
			mFreq[set]= new Tuple();
			fFreq[set]= new Tuple();
		}
		try{
			br= new BufferedReader(new FileReader(filePath));
			String line;

			while((line=br.readLine())!=null){
				String values[]= line.split(",");

				int first, second;
				first= Integer.parseInt(values[0].trim());
				second= Integer.parseInt(values[1].trim());
				if(flag==true)
				{
					int temp =first;
					first= second;
					second= temp;
				}
				mFreq[first].id= first;
				mFreq[first].frequency++;
				fFreq[second].id= second;
				fFreq[second].frequency++;
			}
			br.close();

		}catch(Exception Ex){
			Ex.printStackTrace();
		}
	}
	public void selectFrequentIds(Tuple list[], int selectedList[], int size){
		Arrays.sort(list);
		for(int i=0;i<size-1;i++){
			selectedList[i]=list[i].id;
			System.out.println(i);
		}
	}
	public void generateMostFrequentData(int selectedList1[], int selectedList2[], String inputFilePath, String outputFilePath){
		BufferedReader br;
		BufferedWriter bw;
		int firstHash[]= new int[numberOfIds+1];
		int secondHash[]= new int[numberOfIds+1];
		for(int val: selectedList1){
			firstHash[val]=1;
		}
		for(int val: selectedList2)
			secondHash[val]=1;
		try{
			br= new BufferedReader(new FileReader(inputFilePath));
			bw= new BufferedWriter(new FileWriter(outputFilePath));
			String line="";
			while((line=br.readLine())!=null){
				String values[]= line.split(",");
				int f= Integer.parseInt(values[0].trim());
				int s= Integer.parseInt(values[1].trim());
				if(firstHash[f]==1 && secondHash[s]==1)
				{
					bw.write(line+"\n");
				}
				bw.flush();
			}
		}catch(Exception E){
			E.printStackTrace();
		}
	}
	public void generateTopNRatingsData(String MFFilePath, String FMFilePath, String oMFPath, String oFMPath){
		countMostFrequentData(MFFilePath, mFreq, fFreq, false);
		countMostFrequentData(MFFilePath, mFreq, fFreq, true);

		selectFrequentIds(mFreq, selectedMaleIDs, numberOfUsers+1);
	//	for(int val : selectedMaleIDs) System.out.println(val);
		selectFrequentIds(fFreq, selectedFemaleIDs, numberOfUsers+1);
		generateMostFrequentData(selectedMaleIDs, selectedFemaleIDs, MFFilePath, oMFPath);
		generateMostFrequentData(selectedFemaleIDs, selectedMaleIDs, FMFilePath, oFMPath);
	}
	public void generateMappedRatings(String inMFFile, String inFMFile, String mapMFFile, String mapFMFile){
		DataSetMapper dsmOb= new DataSetMapper();
		dsmOb.getMappedRatings(selectedMaleIDs, selectedFemaleIDs, inMFFile, inFMFile, mapMFFile, mapFMFile);
		
	}
	
	public static void main(String args[]){
		String inMFFilePath= "data/mfFinalRatings12Jan.csv";
		String inFMFilePath= "data/fmFinalRatings12Jan.csv";
		String oMFPath= "data/top100MF.csv";
		String oFMPath= "data/top100FM.csv";
		String mapMFFile= "data/mappedTop100MF.csv";
		String mapFMFile= "data/mappedTop100FM.csv";
		RawDataGenerator rdgob= new RawDataGenerator();
		rdgob.generateTopNRatingsData(inMFFilePath, inFMFilePath, oMFPath, oFMPath);
		rdgob.generateMappedRatings(oMFPath, oFMPath, mapMFFile, mapFMFile);
	}
}
