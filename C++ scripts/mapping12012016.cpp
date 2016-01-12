#include <bits/stdc++.h>
using namespace std;
int main(){

    ifstream inm;
    ifstream inf;

    inm.open("../data/mfTop5000Ratings.csv");
    inf.open("../data/fmTop5000Ratings.csv");

    ofstream outm;
    ofstream outf;

    outm.open("../data/maleMapping.csv");
    outf.open("../data/femaleMapping.csv");

    int k=0;
    int counter=1;
    int prev;
    while(inm){

        string str;
        if(!getline(inm, str))break;

        istringstream ss(str);

        string s1, s2, s3;
        if(!getline(ss, s1, ','))break;
        if(!getline(ss, s2, ','))break;
        if(!getline(ss, s3, ','))break;
        if(k==0){
            outm<<counter<<","<<s1<<endl;
            prev= atoi(s1.c_str());
        }else{
            if(prev!= atoi(s1.c_str())){
                prev= atoi(s1.c_str());
                counter++;
                outm<<counter<<","<<s1<<endl;
            }
        }
        k++;
    }
    counter=1;
    k=0;
    while(inf){

        string str;
        if(!getline(inf, str))break;

        istringstream ss(str);

        string s1, s2, s3;
        if(!getline(ss, s1, ','))break;
        if(!getline(ss, s2, ','))break;
        if(!getline(ss, s3, ','))break;
        if(k==0){
            outf<<counter<<","<<s1<<endl;
            prev= atoi(s1.c_str());
        }else{
            if(prev!= atoi(s1.c_str())){
                prev= atoi(s1.c_str());
                counter++;
                outf<<counter<<","<<s1<<endl;
            }
        }
        k++;
    }

    return 0;
}
