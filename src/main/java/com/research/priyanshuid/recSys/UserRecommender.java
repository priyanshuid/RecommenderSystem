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
	public static double mfRatingMatrix[][]= new double[5002][5002];
	public static double fmRatingMatrix[][]= new double[5002][5002];

	public static int mTopRec[]= new int[5002];
	public static int fTopRec[]= new int[5002];

	public static void computeRecommendations(int k) throws IOException, TasteException
	{

		for(int i=1;i<=5001;i++)
			for(int j=1;j<=5001;j++)
				mfRatingMatrix[i][j]= fmRatingMatrix[i][j]=0;
		DataModel model = new FileDataModel(new File("data/maleToFemaleSmallMapped.csv"));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(k, similarity, model);
		UserBasedRecommender recommender = 
			new GenericUserBasedRecommender(model, neighborhood, similarity);
		//List<RecommendedItem> recommendations = recommender.recommend(1093, 100);
		//for (RecommendedItem recommendation : recommendations) {
		// System.out.println(recommendation);
		//}
		BufferedWriter wr= new BufferedWriter(new FileWriter("output/maleToFemaleRec.csv"));
		for(int i=1;i<=5001;i++){
			try{
				List<RecommendedItem> recommendations= recommender.recommend(i, 5000);
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

		DataModel model2 = new FileDataModel(new File("data/femaleToMaleSmalleMapped.csv"));
		UserSimilarity similarity2 = new PearsonCorrelationSimilarity(model2);
		UserNeighborhood neighborhood2 = new NearestNUserNeighborhood(k, similarity2, model2);
		UserBasedRecommender recommender2 = 
			new GenericUserBasedRecommender(model2, neighborhood2, similarity2);
		//List<RecommendedItem> recommendations = recommender.recommend(1093, 100);
		//for (RecommendedItem recommendation : recommendations) {
		// System.out.println(recommendation);
		//}
		BufferedWriter wr2= new BufferedWriter(new FileWriter("output/femaleTomaleRec.csv"));
		for(int i=1;i<=5001;i++){
			try{
				List<RecommendedItem> recommendations= recommender2.recommend(i, 5000);
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
	public static void main(String args[]) throws IOException, TasteException{
		int k=20;
		UserRecommender.computeRecommendations(k);
	}
}
