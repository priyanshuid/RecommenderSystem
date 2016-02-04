package com.research.priyanshuid.recSys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DataPartitioner {
	ArrayList<String> fetchRatings(String filePath) throws FileNotFoundException{

		BufferedReader br;
		ArrayList<String> list= new ArrayList<String>();
		try {
			br= new BufferedReader(new FileReader(filePath));
			String str="";
			while((str=br.readLine())!=null){
				list.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.shuffle(list, new Random(System.nanoTime()));
		return list;
	}

	void printRatings(String trainPath,String testPath, Double partition, ArrayList<String> ratings){
		BufferedWriter bw;
		BufferedWriter bwtest;
		try{
			bw= new BufferedWriter(new FileWriter(trainPath));
			bwtest= new BufferedWriter(new FileWriter(testPath));
			int split= (int)(ratings.size()*partition);
			for(int i=0;i<ratings.size();i++){
				if(i<split){
					bwtest.write(ratings.get(i)+"\n");
					bwtest.flush();
				}else{
					bw.write(ratings.get(i)+"\n");
					bw.flush();
				}
			}
			bw.close();
			bwtest.close();
		}catch(Exception E){
			System.out.println(E.getStackTrace());
		}
	}
	public void partitionDataRandomly(String inputFilePath, String testFilePath, String trainFilePath, Double Partition) throws FileNotFoundException{
		ArrayList<String> shuffledRatings= fetchRatings(inputFilePath);
		printRatings(trainFilePath, testFilePath, Partition, shuffledRatings);
	}
}
