#include <bits/stdc++.h>
using namespace std;
int mfRating[5002][5002];
int fmRating[5002][5002];
int main(){

    memset(mfRating, 0, sizeof(mfRating));
    memset(fmRating, 0, sizeof(fmRating));

    ifstream in;
    ofstream out;

    in.open("maleToFemaleSmallMapped.txt");
    out.open("mfRating.txt");

    while(in){
        string str;
        if(!getline(in, str))break;

        istringstream ss(str);
        string str1, str2, str3;
        if(!getline(ss, str1, ','))break;
        if(!getline(ss, str2, ','))break;
        if(!getline(ss, str3, ','))break;

        int m, f, r;
        m= atoi(str1.c_str());
        f= atoi(str2.c_str());
        r= atoi(str3.c_str());

        mfRating[m][f]=r;
    }

    for(int i=1;i<=5001;i++){
        for(int j=1;j<= 5001;j++)
            out<<mfRating[i][j]<<",";
        out<<endl;
    }
    in.close();
    out.close();

    in.open("femaleToMaleSmallMapped.txt");
    out.open("fmRating.txt");

    while(in){
        string str;
        if(!getline(in, str))break;

        istringstream ss(str);
        string str1, str2, str3;
        if(!getline(ss, str1, ','))break;
        if(!getline(ss, str2, ','))break;
        if(!getline(ss, str3, ','))break;

        int f, m, r;
        f= atoi(str1.c_str());
        m= atoi(str2.c_str());
        r= atoi(str3.c_str());

        fmRating[f][m]=r;
    }
    for(int i=1;i<=5001;i++)
            for(int j=1;j<=5001;j++)
                out<<fmRating[i][j]<<",";
            out<<endl;

    in.close();
    out.close();
    return 0;
}
