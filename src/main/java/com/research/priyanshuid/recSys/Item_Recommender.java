package com.research.priyanshuid.recSys;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class Item_Recommender {

	public static void main(String[] args) throws IOException {
		DataModel dm = new FileDataModel(new File("data/femaleToMaleSmallMapped"));
		ItemSimilarity sim= new LogLikelihoodSimilarity(dm);
		System.out.println(sim+"");
	}

}
