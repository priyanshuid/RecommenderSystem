#include <bits/stdc++.h>
using namespace std;
int main(){
	
	ifstream in("input.txt");
	ofstream out("output.txt");
	vector <vector<int> > data;

	in.open();
	out.open();

	while(in)
	{
		string s;
		if(!getline(in, s))break;
		istringstream ss(s);
		vector <int> record;

		while(ss)
		{
			string str;
			if(!getline(ss, str, ','))break;
			record.push_back(atoi(str.c_str()););
		}
		data.push_back(record);
	}


	out.close();
	in.close();
	return 0;
}