#include <bits/stdc++.h>
using namespace std;
bool male[220962];
bool female[220961];
int main(){

    ifstream minp;
    minp.open("male.txt");
    ifstream finp;
    finp.open("female.txt");
    ofstream mout;
    mout.open("maleShort.txt");
    ofstream fout;
    fout.open("femaleShort.txt");
    memset(male, false, sizeof(male));
    memset(female, false, sizeof(female));
    for(int i=0;i<=5000;i++){
        int m;
        minp>>m;
        male[m]=true;
        mout<<m<<endl;
    }
    for(int i=0;i<=5000;i++){
        int f;
        finp>>f;
        female[f]=true;
        fout<<f<<endl;
    }
    minp.close();
    finp.close();
    mout.close();
    fout.close();

    ifstream inmf;
    inmf.open("maleToFemale.txt");

    ofstream outmf;
    outmf.open("maleToFemaleSmall.txt");

    while(inmf)
    {

        string s;
        if(!getline(inmf, s))break;

        istringstream ss(s);

        string str1, str2, str3;

        if(!getline(ss, str1, ','))break;
        if(!getline(ss, str2, ','))break;
        if(!getline(ss, str3, ','))break;

        int m= atoi(str1.c_str());
        int f= atoi(str2.c_str());

        int r= atoi(str3.c_str());
        if(male[m]==true && female[f]==true)
            outmf<<m<<","<<f<<","<<r<<endl;
    }

    outmf.close();
    inmf.close();

    ifstream infm;
    infm.open("femaleToMale.txt");

    ofstream outfm;
    outfm.open("femaleToMaleSmall.txt");

    while(infm){
        string str;
        if(!getline(infm, str))break;

        istringstream ss(str);

        string str1, str2, str3;

        if(!getline(ss, str1, ','))break;
        if(!getline(ss, str2, ','))break;
        if(!getline(ss, str3, ','))break;

        int f= atoi(str1.c_str());
        int m= atoi(str2.c_str());

        int r= atoi(str3.c_str());
        if(female[f]==true && male[m]==true)
            outfm<<f<<","<<m<<","<<r<<endl;
    }
    outfm.close();
    infm.close();

    return 0;
}
