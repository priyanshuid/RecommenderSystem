#include <bits/stdc++.h>
using namespace std;
int main(){
    ifstream in;
    ofstream out;
    ofstream out2;


    in.open("../data/fmFinalRatings12Jan.csv");

    out.open("../data/fmTopRatings13Jan.csv");
    out2.open("../data/fmWTopRatings13Jan.csv");
    int counter=0;
    while(in){

        string s;
        if(!getline(in, s))  break;

        istringstream ss(s);
        string s1, s2, s3;
        if(!getline(ss, s1, ',')) break;
        if(!getline(ss, s2, ',')) break;
        if(!getline(ss, s3, ',')) break;

        int ratingValue= atoi(s3.c_str());

        if(ratingValue ==10)
            out<< s1<<","<<s2<<","<<s3<<endl;
        else out2<<s1<<","<<s2<<","<<s3<<endl;

    }
    in.close();
    out.close();
    out2.close();
    return 0;
}
