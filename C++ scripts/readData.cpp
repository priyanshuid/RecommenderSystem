#include <bits/stdc++.h>
#define SIZE 5000
using namespace std;
pair<int,int> ratingMatrix[SIZE+1][SIZE+1];
int main(){

	ifstream in;
	in.open("inputFromDatingData.txt");
	ofstream out;
	out.open("output.txt");
	vector <vector<int> > data;
	cout<<"Reading data from file, please wait, this might take around few minutes"<<endl;
	cout<<"......................................................................."<<endl;
	while(in)
	{
		string s;
		if(!getline(in, s))break;
		istringstream ss(s);
		vector <int> record;

		while(ss)
		{
			string str;
			if(!getline(ss, str, ','))break;
			record.push_back(atoi(str.c_str()));
		}
		data.push_back(record);
	}
	cout<<"Finished reading data from the file."<<endl;
	cout<<"-------------Generating Matrix-----------"<<endl;
    for(int i=1;i<=SIZE;i++)
        for(int j=1;j<=SIZE;j++){
            ratingMatrix[i][j].first=0;
            ratingMatrix[i][j].second=0;
    }
    for(int i=0;i<data.size();i++)
    {
        int a= data[i][0];
        int b= data[i][1];
        int rating= data[i][2];

        if(a<=SIZE && b<=SIZE)
            ratingMatrix[a][b].first= rating;
    }
    cout<<"Finished Generating Matrix"<<endl;
    cout<<"Generating two-way rating matrix"<<endl;
    int count=0;
    for(int i=1;i<=SIZE;i++)
    {
        for(int j=1;j<=SIZE;j++)
        {
            if(ratingMatrix[i][j].first !=0 || ratingMatrix[j][i].first!=0)
            {
                ratingMatrix[i][j].second=ratingMatrix[j][i].first;
                count++;
            }

        }
    }
    cout<<"Number of non-zero pair entries are-"<<count<<endl;
    cout<<"Started printing matrix in file output.txt"<<endl;
    for(int i=1;i<=SIZE;i++)
    {
        for(int j=1;j<=SIZE;j++)
        {
            out<<ratingMatrix[i][j].first<<","<<ratingMatrix[i][j].second<< " ";
        }
        out<<endl;
    }
    cout<<"Finished printing final matrix to file: output.txt"<<endl;
	out.close();
	in.close();
	return 0;
}
