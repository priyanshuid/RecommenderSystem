#include <bits/stdc++.h>
char gender[220971];
using namespace std;
int main(){

    ifstream inp;
    ofstream outp;
    ofstream outp2;
    for(int i=0;i<=220970;i++)  gender[i]='U';
    inp.open("../data/gender.dat");
    outp.open("../data/male.txt");
    outp2.open("../data/female.txt");
    while(inp){
        string s;
        if(!getline(inp, s))break;
        istringstream ss(s);
        string str1, str2;
        if(!(getline(ss, str1, ',')))break;
        if(!(getline(ss, str2, ',')))break;
        int pos=atoi(str1.c_str());
        gender[pos]=str2.at(0);
        if(str2.at(0)=='M')
            outp<<pos<<endl;
        else if(str2.at(0)=='F')outp2<<pos<<endl;
    }
    outp.close();
    outp2.close();
    inp.close();
    ifstream in;
    in.open("../data/inputFromDatingData.txt");
    ofstream out;
    ofstream out2;
    out.open("../data/mfRatings12012016.csv");
    out2.open("../data/fmRatings12012016.csv");
    while(in){

        string s;
        if(!getline(in, s))break;

        istringstream ss(s);
        string str1, str2, str3;
        if(!getline(ss, str1, ','))break;
        if(!getline(ss, str2, ','))break;
        if(!getline(ss, str3, ','))break;
        if(gender[atoi(str1.c_str())]=='M' && gender[atoi(str2.c_str())]=='F')
            out<<str1<<","<<str2<<","<<str3<<endl;
        else if(gender[atoi(str1.c_str())]=='F' && gender[atoi(str2.c_str())]=='M')
            out2<<str1<<","<<str2<<","<<str3<<endl;
    }
    in.close();
    out.close();
    out2.close();
    return 0;
}
