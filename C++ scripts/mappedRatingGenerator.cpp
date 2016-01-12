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

void generateMappedRatings(char *iPath, char *oPath){



}

int main(){


    string iMPath= "../data/maleMapping.csv";
    string iFPath= "../data/femalMapping.csv";

    memset(mMap, 0, sizeof(mMap));
    memset(fMap, 0, sizeof(fMap));

    createMap(iMPath, &mMap[0u]);
    createMap(iFPath, &fMap[0u]);


    return 0;
}
