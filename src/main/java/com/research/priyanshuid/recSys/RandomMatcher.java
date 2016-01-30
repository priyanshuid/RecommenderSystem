package com.research.priyanshuid.recSys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomMatcher {
	
	public class pair{
		int i, j;
		pair(){
			i=0;
			j=0;
		}
		pair(int i, int j){
			this.i= i;
			this.j= j;
		}
	}
	
	public void matchRandomProfiles() throws IOException{
		ArrayList<Integer> list= new ArrayList<Integer>();
		for(int i=1;i<=5000;i++)
			list.add(i);
		long seed= System.nanoTime();
		Collections.shuffle(list, new Random(seed));
		Collections.shuffle(list, new Random(seed));
		ArrayList<pair> matching= new ArrayList<pair>();
		for(int i=0;i<5000;i++){
			pair p= new pair(i+1, list.get(i));
			matching.add(p);
		}
		
		BufferedWriter br= new BufferedWriter(new FileWriter("output/randomMatching.csv"));
		for(int i=0;i<5000;i++){
			br.write(i+1+","+list.get(i)+",10.0\n");
			br.flush();
		}
		br.close();
	}
	public static void main(String args[])throws IOException{
		RandomMatcher rm= new RandomMatcher();
		rm.matchRandomProfiles();
		System.out.println("done matching random profiles");
	}
}
