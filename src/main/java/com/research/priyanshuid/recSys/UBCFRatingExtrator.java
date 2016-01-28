package com.research.priyanshuid.recSys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UBCFRatingExtrator {
	
	public static void extractTopRatings(String actualDataPath, String outPath, double matrix[][]){
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
					for(int i=1;i<values.length;i++){
						s= Integer.parseInt(values[i]);
						
						if(matrix[f][s]==10.0){
							wr.write(f+","+s+","+"10.0\n");
							counter++;
						}
					}
					wr.flush();
					
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
		
	}

}
