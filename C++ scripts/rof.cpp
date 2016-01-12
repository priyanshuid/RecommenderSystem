#include <iostream>
#include <stdio.h>
#include <fstream>
#include <algorithm>
#include <string>
#include <math.h>
#define nor 2000
using namespace std;
ifstream in;
ofstream out;
double dist(double ar1[],double ar2[],int n);
int main()
{
	in.open("piyansu.dat");
	out.open("output.txt");
	cout<<"enter the no. of dimensions in the dataset"<<endl;
		int a;
		cin>>a;
	cout<<"enter the no. of points"<<endl;
		int n;
		cin>>n;
	double ar[n][a];
	int ii=0,jj=-1;
	while(ii!=n-1)
	{
		in>>ar[ii][++jj];
		if(jj==a-1)
		{jj=-1;ii++;}
	}
	in.close();
	//now having read the data into a 2-d array ,we have to calculate the distance between each point in order to calculate the max
	//and min distance
	double ar1[a],ar2[a];
	for(int j=0;j<a;j++)
	{
		ar1[j]=ar[0][j];
		ar2[j]=ar[1][j];
	}
	double max_dist=dist(ar1,ar2,a);
	double min_dist=dist(ar1,ar2,a);
	for(int i=0;i<n;i++)
	{
		for(int k=i+1;k<n;k++)
		{
			for(int j=0;j<a;j++)
			{
				ar1[j]=ar[i][j];
				ar2[j]=ar[k][j];
			}
			
			double distance=dist(ar1,ar2,a);
			if(distance>max_dist)
				max_dist=distance;
			if(distance<min_dist)
				min_dist=distance;
		
		}
	}
	double r_min=min_dist;
	double r_max=max_dist;
	double delta=(r_max-r_min)/nor;
	
	double rof[n];
	for(int i=0;i<n;i++)
	rof[i]=0.0;
	for(int i=0;i<n;i++)
	{
		int flag=0;
		double res_dist;
		while(flag!=nor)
		{
		
			int count_r_res=1,count_next_r_res=1;
			res_dist=r_min+delta*flag;
			for(int k=0;k<n;k++)				//this loop checks for the no. of points in perticular resolution//
			{
				if(i!=k)
				{
					for(int j=0;j<a;j++)
						{
							ar1[j]=ar[i][j];
							ar2[j]=ar[k][j];
						}
					double distance=dist(ar1,ar2,a);
					if(distance<=res_dist)
					count_r_res++;
				}	
			}
			res_dist=r_min+delta*(flag+1);
			for(int k=0;k<n;k++)				//this loop checks for the no. of points in perticular resolution//
			{
				if(i!=k)
				{
					for(int j=0;j<a;j++)
						{
							ar1[j]=ar[i][j];
							ar2[j]=ar[k][j];
						}
					double distance=dist(ar1,ar2,a);
					if(distance<=res_dist)
					count_next_r_res++;
				}	
			}
			double val1=count_r_res-1;
			double val2=count_next_r_res;
			double val=val1/val2; 
			rof[i]+=val;			
			flag++;
		}
	}
	for(int i=1;i<n;i++)
	out<<i<<" ::"<<rof[i]<<endl;
	return 0;
}
double dist(double ar1[],double ar2[],int n)
{
	double sqr_sum=0;
	for(int i=0;i<n;i++)
	{
		sqr_sum+=((ar1[i]-ar2[i])*(ar1[i]-ar2[i]));
	}
	return sqrt(sqr_sum);
}
