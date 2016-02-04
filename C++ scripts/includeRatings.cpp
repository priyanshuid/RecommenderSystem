#include <bits/stdc++.h>
using namespace std;
#define RATIO 0.2

int fMtoF[5001];
int fFtoM[5001];

void findFrequency(char *path, int ar[]){
    ifstream in;
    in.open(path);

    while(in){
        string str;

        if(!getline(in, str))break;
        istringstream ss(str);
        string s1;

        if(!getline(in, s1, ','))break;
        int pos= atoi(s1.c_str());
        ar[pos]++;
    }
}

void fillNewRatings(char *topRatingsFilePath, char *fullRatingsFilePath, char *oPathRatings, char *oPathTestFile, int ar[]){
    ifstream in;
    in.open(fullRatingsFilePath);
    ofstream out;
    out.open(oPathRatings);
    ofstream out2;
    out2.open(oPathTestFile);
    while(in){
        string str;
        if(!getline(in, str))break;
            out<<str<<endl;
        cout<<"first"<<str<<endl;
    }
    in.close();
    in.open(topRatingsFilePath);
    int index= 1;
    while(in && index<=5000){
        int count=0;
        int split= RATIO*ar[index];
        for(int i=1;i<=ar[index];i++){
            string str;
            if(!getline(in, str))break;
            if(count<split)
                out<<str<<endl;
            else out2<<str<<endl;
            count++;
            cout<<"second"<<str<<endl;
        }
        index++;
    }
    out.close();
    out2.close();
}

int main(){

    string inputPathTopRatingsM= "../data/mfTopRatings13Jan.csv";
    string inputPathTopRatingsF= "../data/fmTopRatings13Jan.csv";

    string fullRatingsFilePathM= "../data/mfWTopRatings13Jan.csv";
    string fullRatingsFilePathF= "../data/fmWTopRatings13Jan.csv";

    string outputFilePathM     = "../data/trainMtoF18Jan.csv";
    string outputFilePathF     = "../data/trainFtoM18Jan.csv";

    string testFileM           = "../data/testMtoF18Jan.csv";
    string testFileF           = "../data/testFtoM18Jan.csv";

    memset(fMtoF, 0, sizeof(fMtoF));
    memset(fFtoM, 0, sizeof(fFtoM));
    findFrequency(&inputPathTopRatingsM[0u], fMtoF);
    findFrequency(&inputPathTopRatingsF[0u], fFtoM);

    fillNewRatings(&inputPathTopRatingsM[0u], &fullRatingsFilePathM[0u], &outputFilePathM[0u], &testFileM[0u], fMtoF);
    fillNewRatings(&inputPathTopRatingsF[0u], &fullRatingsFilePathF[0u], &outputFilePathF[0u], &testFileF[0u], fFtoM);

    return 0;
}
