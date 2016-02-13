package com.research.priyanshuid.recSys;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class DataSetMapper {
	public void mapDataSet(int maleSelectedIds[], int femaleSelectedIds[], Map<Integer, Integer> maleMap, Map<Integer, Integer> femaleMap){
		int count=1;
		for(int val: maleSelectedIds){
			maleMap.put(val, count);
			count++;
		}
		count=1;
		for(int val: femaleSelectedIds){
			femaleMap.put(val, count);
			count++;
		}
	}
	public void generateNewRatings(Map<Integer, Integer> maleMap, Map<Integer, Integer> femaleMap, String inFile, String opFile, boolean flag){
		BufferedReader br;
		BufferedWriter bw;
		try{
			br= new BufferedReader(new FileReader(inFile));
			bw= new BufferedWriter(new FileWriter(opFile));
			String line="";
			while((line=br.readLine())!=null){
				String values[]= line.split(",");
				Integer f= Integer.parseInt(values[0].trim());
				Integer s= Integer.parseInt(values[1].trim());
				Double rating= Double.parseDouble(values[2].trim());
				if(flag==true){
					Integer mSubstitute= maleMap.get(s);
					Integer fSubstitute= femaleMap.get(f);
					bw.write(fSubstitute+","+mSubstitute+","+rating+"\n");
				}else{
					Integer mSubstitute= maleMap.get(f);
					Integer fSubstitute= femaleMap.get(s);
					bw.write(mSubstitute+","+fSubstitute+","+rating+"\n");
				}
			}
			bw.close();
		}catch(Exception Ex){
			Ex.getStackTrace();
		}
	}
	public void getMappedRatings(int selectedMIds[], int selectedFIds[],String mInFile, String fInFile, String mOutFile, String fOutFile){
		Map<Integer, Integer> mMap= new HashMap<Integer, Integer>();
		Map<Integer, Integer> fMap= new HashMap<Integer, Integer>();
		mapDataSet(selectedMIds, selectedFIds, mMap, fMap);
		generateNewRatings(mMap, fMap, fInFile, fOutFile, true);
		generateNewRatings(mMap, fMap, mInFile, mOutFile, false);
		for(Integer val: fMap.keySet()){
			System.out.println(val+","+fMap.get(val));
		}
	}
}
