#include <bits/stdc++.h>
using namespace std;
int mMap[135340];
int fMap[135358];

void createMap(char *iPath, int mapAr[]){

    ifstream in;
    in.open(iPath);

    while(in){

        string str;
        if(!getline(in, str))break;

        istringstream ss(str);
        string s1, s2;

        if(!getline(ss, s1, ','))break;
        if(!getline(ss, s2, ','))break;

        mapAr[atoi(s2.c_str())]= atoi(s1.c_str());

    }
    in.close();
    return;
}

void generateMappedRatings(char *iPath, char *oPath, int id1[], int id2[]){

    ifstream in;
    ofstream out;
    in.open(iPath);
    out.open(oPath);


    while(in){
        string str;
        if(!getline(in, str))break;

        istringstream ss(str);
        string s1, s2, s3;

        if(!getline(ss, s1, ','))break;
        if(!getline(ss, s2, ','))break;
        if(!getline(ss, s3, ','))break;

        int x= atoi(s1.c_str());
        int y= atoi(s2.c_str());

        out<<id1[x]<<","<<id2[y]<<","<<s3<<endl;
    }
    in.close();
    out.close();
    return;

}

int main(){


    string iMPath= "../data/maleMapping.csv";
    string iFPath= "../data/femaleMapping.csv";

    string rMData= "../data/mfTop5000Ratings.csv";
    string rFData= "../data/fmTop5000Ratings.csv";

    string oMPath= "../data/mfFinalRatings12Jan.csv";
    string oFPath= "../data/fmFinalRatings12Jan.csv";

    memset(mMap, 0, sizeof(mMap));
    memset(fMap, 0, sizeof(fMap));

    createMap(&iMPath[0u], mMap);
    createMap(&iFPath[0u], fMap);

    generateMappedRatings(&rMData[0u], &oMPath[0u], mMap, fMap);
    generateMappedRatings(&rFData[0u], &oFPath[0u], fMap, mMap);


    return 0;
}
