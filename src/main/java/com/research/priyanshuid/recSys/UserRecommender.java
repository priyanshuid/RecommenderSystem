package com.research.priyanshuid.recSys;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserRecommender 
{
	
	public int uSize=100;
	public double mfRatingMatrix[][]= new double[uSize+2][uSize+2];
	public double fmRatingMatrix[][]= new double[uSize+2][uSize+2];
	
	public int mTopRec[]= new int[uSize+2];
	public int fTopRec[]= new int[uSize+2];
	
	public void computeMaleRecommendations(String mfTrainFilePath, int k) throws IOException, TasteException
	{
		String str= "data/trainMtoF18Jan.csv";
		DataModel model = new FileDataModel(new File(mfTrainFilePath));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(k, similarity, model);
		UserBasedRecommender recommender = 
				new GenericUserBasedRecommender(model, neighborhood, similarity);
//		List<RecommendedItem> recommendations = recommender.recommend(2, 5);
//		System.out.println(recommendations.size());
//		for (RecommendedItem recommendation : recommendations) {
//		 System.out.println(recommendation);
//		}
		BufferedWriter wr= new BufferedWriter(new FileWriter("output/maleToFemaleRec.csv"));
		for(int i=1;i<=uSize;i++){
			try{
				List<RecommendedItem> recommendations= recommender.recommend(i, uSize);
				if(!recommendations.isEmpty())
				{
					mTopRec[i]=(int) recommendations.get(0).getItemID();
				}else mTopRec[i]=0;
				wr.write(i+ ",");
				for(RecommendedItem recommendation:recommendations){
					wr.write(recommendation.getItemID()+",");
					mfRatingMatrix[i][(int) (recommendation.getItemID())]=recommendation.getValue();
				}
			}catch(Exception E){
				wr.write(i+"");
				System.out.println(i);
			}
			wr.write("\n");

		}
		wr.close();
		System.out.println("finished male to female recommendation process");
	}
	public void computeFemaleRecommendations(String FMTrainFilePath, int k) throws IOException, TasteException{
		String str= "data/trainFtoM18Jan.csv";
		DataModel model2 = new FileDataModel(new File(FMTrainFilePath));
		UserSimilarity similarity2 = new PearsonCorrelationSimilarity(model2);
		UserNeighborhood neighborhood2 = new NearestNUserNeighborhood(k, similarity2, model2);
		UserBasedRecommender recommender2 = 
				new GenericUserBasedRecommender(model2, neighborhood2, similarity2);
		BufferedWriter wr2= new BufferedWriter(new FileWriter("output/femaleToMaleRec.csv"));
		for(int i=1;i<=uSize;i++){
			try{
				List<RecommendedItem> recommendations= recommender2.recommend(i, uSize);
				wr2.write(i+ ",");
				if(!recommendations.isEmpty())
				{
					fTopRec[i]=(int) recommendations.get(0).getItemID();
				}else fTopRec[i]=0;
				for(RecommendedItem recommendation:recommendations){
					wr2.write(recommendation.getItemID()+",");
					fmRatingMatrix[i][(int) (recommendation.getItemID())]=recommendation.getValue();

				}
			}catch(Exception E){
				wr2.write(i+"");
				System.out.println(i);
			}
			wr2.write("\n");
		}
		wr2.close();
		System.out.println("finished female to male recommendation process");
	}
//	public static void main(String args[]) throws IOException, TasteException{
//		int k=3;
//		UserRecommender ur= new UserRecommender();
//		ur.computeMaleRecommendations(k);
//	}
}
