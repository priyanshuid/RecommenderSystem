#include <bits/stdc++.h>
using namespace std;
int maleAr[11276];
int femaleAr[11100];
int main(){

    ifstream in;
    in.open("maleShort.txt");

    ofstream out;
    out.open("mappedMale.txt");

    memset(maleAr, 0, sizeof(maleAr));
    memset(femaleAr, 0, sizeof(femaleAr));

    int counter=1;
    while(in){
        string str;
        if(!getline(in, str))break;
        out<< str<<","<<counter<<endl;
        maleAr[atoi(str.c_str())]= counter;
        counter++;
    }
    in.close();
    out.close();

    in.open("femaleShort.txt");
    out.open("mappedFemale.txt");
    counter= 1;
    while(in){
        string str;
        if(!getline(in, str))break;
        out<<str<<","<<counter<<endl;
        femaleAr[atoi(str.c_str())]= counter;
        counter++;
    }

    in.close();
    out.close();

    ifstream minp, finp;
    ofstream mout, fout;

    minp.open("maleToFemaleSmall.txt");
    finp.open("femaleToMaleSmall.txt");

    mout.open("maleToFemaleSmallMapped.txt");
    fout.open("femaleToMaleSmallMapped.txt");

    while(minp){

        string str;
        if(!getline(minp, str))break;

        istringstream ss(str);

        string str1, str2, str3;
        if(!getline(ss, str1, ','))break;
        if(!getline(ss, str2, ','))break;
        if(!getline(ss, str3, ','))break;

        int m= atoi(str1.c_str());
        int f= atoi(str2.c_str());
        int r= atoi(str3.c_str());

        int mmapped= maleAr[m];
        int fmapped= femaleAr[f];

        mout<<mmapped<<","<<fmapped<<","<<r<<endl;

    }
    minp.close();
    mout.close();

    while(finp){
        string str;
        if(!getline(finp, str))break;

        istringstream ss(str);

        string str1, str2, str3;
        if(!getline(ss, str1, ','))break;
        if(!getline(ss, str2, ','))break;
        if(!getline(ss, str3, ','))break;

        int f= atoi(str1.c_str());
        int m= atoi(str2.c_str());
        int r= atoi(str3.c_str());

        int mmapped= maleAr[m];
        int fmapped= femaleAr[f];

        fout<<fmapped<<","<<mmapped<<","<<r<<endl;
    }
    finp.close();
    fout.close();
    return 0;
}
