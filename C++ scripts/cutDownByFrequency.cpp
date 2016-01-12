#include <bits/stdc++.h>
using namespace std;
int fid[135360];
int mid[135359];
bool mflags[535360];
bool fflags[535360];
bool compare(pair<int, int> a, pair<int, int> b){
    return a.second>b.second;
}

void cutDown(char *inputPath, int idar[], bool flagValues[],int size){

    ifstream in;
    cout<<inputPath<<endl;
    in.open(inputPath);

    while(in){

        string str;
        if(!getline(in, str))break;

        istringstream ss(str);
        string s1, s2, s3;
        if(!getline(ss, s1, ','))break;
        if(!getline(ss, s2, ','))break;
        if(!getline(ss, s3, ','))break;

        int id= atoi(s1.c_str());

        idar[id]++;
    }
    cout<<"finished reading and calculating frequency"<<endl;
    vector <pair<int, int> > frequencySet;
    vector <pair<int, int> > ::const_iterator itr;
    int count=0;
    for(int i=1;i<size;i++)
    {
        frequencySet.push_back(pair<int, int>(i, idar[i]));
    }

    cout<<"finished creating vector"<<endl;
    sort(frequencySet.begin(), frequencySet.end(), compare);
    cout<<"sorted vector"<<endl;
    for(int i=0;i<5000;i++){
        flagValues[frequencySet[i].first]= true;
        cout<<flagValues[frequencySet[i].first]<<endl;
        cout<<i<<endl;
    }
    in.close();
}


void writeMaleRatings(char *inputPath, char *outputPath){
    ifstream inm;
    inm.open(inputPath);

    ofstream outm;
    outm.open(outputPath);

    while(inm){
        string str;
        if(!getline(inm, str))break;

        istringstream ss(str);
        string s1, s2, s3;
        if(!getline(ss, s1, ','))break;
        if(!getline(ss, s2, ','))break;
        if(!getline(ss, s3, ','))break;

        int id1= atoi(s1.c_str());
        int id2= atoi(s2.c_str());

        if(mflags[id1]==true && fflags[id2]==true){
            outm<<id1<<","<<id2<<","<<s3<<endl;
            cout<<id1<<","<<id2<<","<<s3<<endl;
        }
    }
    inm.close();
    outm.close();
}

void writeFemaleRatings(char *inputPath, char *outputPath){

    ifstream in2;
    in2.open(inputPath);

    ofstream out2;
    out2.open(outputPath);

    while(in2){
        string str;
        if(!getline(in2, str))break;

        istringstream ss(str);
        string s1, s2, s3;
        if(!getline(ss, s1, ','))break;
        if(!getline(ss, s2, ','))break;
        if(!getline(ss, s3, ','))break;

        int id1= atoi(s1.c_str());
        int id2= atoi(s2.c_str());

        if(fflags[id1]==true && mflags[id2]==true){
            out2<<id1<<","<<id2<<","<<s3<<endl;
            cout<<id1<<","<<id2<<","<<s3<<endl;
        }
    }
    in2.close();
    out2.close();
}

int main(){

    for(int i=0;i<135360;i++)
        fid[i]=0;
    for(int i=0;i<135359;i++)
        mid[i]=0;

    string inputPath1="../data/fmRatings12012016.csv";
    string inputPath2="../data/mfRatings12012016.csv";

    string outputPath1="../data/fmTop5000Ratings.csv";
    string outputPath2="../data/mfTop5000Ratings.csv";

    memset(mflags, 0, sizeof(mflags));
    memset(fflags, 0, sizeof(fflags));

    cutDown(&inputPath1[0u], fid, fflags,sizeof(fid)/4);
    cutDown(&inputPath2[0u], mid, mflags, sizeof(mid)/4);

    writeMaleRatings(&inputPath2[0u], &outputPath2[0u]);
    writeFemaleRatings(&inputPath1[0u], &outputPath1[0u]);

    return 0;
}
