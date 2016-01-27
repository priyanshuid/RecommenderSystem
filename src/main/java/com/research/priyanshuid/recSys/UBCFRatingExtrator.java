package com.research.priyanshuid.recSys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UBCFRatingExtrator {
	
	public static void extractTopRatings(String actualDataPath, String outPath){
		BufferedReader br= null;
		BufferedWriter wr= null;
		String line="";
		try{
			int counter=0;
			br= new BufferedReader(new FileReader(actualDataPath));
			wr= new BufferedWriter(new FileWriter(outPath));
			while((line= br.readLine())!=null){
				String []values= line.split(",");
				int f, s;
				try{
					f= Integer.parseInt(values[0]);
					s= Integer.parseInt(values[1]);
					wr.write(f+","+s+",10\n");
					wr.flush();
					counter++;
				}catch(Exception e) {
					wr.write(values[0]+",0,10\n");
					wr.flush();
				}
				
			}
			System.out.println(counter);
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
	public static void main(String args[]){
		String input= "output/femaleToMaleRec.csv";
		String output= "output/UBCFTopFtoM.csv";
		extractTopRatings(input, output);
		System.out.println("Done for FM");
		input = "output/maleToFemaleRec.csv";
		output= "output/UBCFTopMtoF.csv";
		extractTopRatings(input, output);
		System.out.println("Done for MF");
	}

}
