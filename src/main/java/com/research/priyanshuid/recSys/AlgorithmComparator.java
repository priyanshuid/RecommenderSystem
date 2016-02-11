package com.research.priyanshuid.recSys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AlgorithmComparator {
	
	
	class Pair{
		
		Integer uID, pID;
		Pair(){
			uID=0;pID=0;
		}
		Pair(Integer a, Integer b){
			uID=a; pID=b;
		}
	}
	
	class Tuple{
		Integer uID,pID;
		Double rating;
		Tuple(){
			uID=0;pID=0;rating=0.0;
		}
		Tuple(Integer a, Integer b, Double r){
			uID= a; pID= b; rating= r;
		}
	}
	public ArrayList<Pair> getRandomMatchings(){
		ArrayList<Integer> list= new ArrayList<Integer>();
		for(int i=5000;i>=1;i--)
			list.add(i);
		long seed= System.nanoTime();
		Collections.shuffle(list, new Random(seed));
		Collections.shuffle(list, new Random(seed));
		ArrayList<Pair> matching= new ArrayList<Pair>();
		for(int i=0;i<5000;i++){
			Pair p= new Pair(i+1, list.get(i));
			matching.add(p);
		}
		return matching;
	}
	
	public void compare(String matchingOutputFilePath, String randomOutputFilePath, String mfTestFilePath, String fmTestFilePath, int flag){
		ArrayList<Tuple> mfTestList= new ArrayList<Tuple>();
		ArrayList<Tuple> fmTestList;
	}

}
