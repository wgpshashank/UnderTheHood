/* 
Stable Marriage Problem is the problem of finding a stable matching between two sets of elements given a set of preferences
for each element. Formally, given a set M = {m1, m2, …, mn} of n men, a set W = {w1, w2, …, wn} of n women, a preference 
list (i.e., an ordering of n men) for each woman and a preference list (i.e., an ordering of n women) for each man, the 
problem is to find a set S of pairs (m,w) for some m ∈ M and w ∈ W such that Each m ∈ M and w ∈ W appears in exactly one 
pair in S If (m, w) ∈ S and (m’, w’) ∈ S, then it cannot be the case that m prefers w’ to w and w’ prefers m to m’
This problem comes up in several real-world scenarios from a self-enforcing college recruitment procedure to assignment of 
patients to hospitals. Mathematicians David Gale and Lloyd Shapley proved that, for any equal number of men and women, it 
is always possible to solve the problem and make all marriages stable. The Gale-Shapley algorithm, as described in 
Algorithm Design by Kleinberg and Tardos, is as follows:

Initially all m ∈ M and w ∈ W are free
while ∃ m who is free and hasn't proposed to every woman:
    Choose m
    Let w be the highest-ranked woman in m's preference list 
	to whom m has not yet proposed
    if w is free:
	(m, w) become engaged
    else w is currently engaged to m':
	if w prefers m' to m:
	    m remains free
	else w prefers m to m':
	    (m, w) become engaged
	    m' becomes free
Return the set S of engaged pairs

*/


#include <cstdio>
#include <cstring>
#include <queue>
using namespace std;

// Men and women are represented by integers 1...N

// ManPref is the preference list of all men for all women.
// ManPref[m][i] = w iff w is at ith position in the preference list of m

// WomanPref is the preference list of all women for all men.
// WomanPref[w][i] = m iff m is at ith position in the preference list of w

// Ranking gives the ranking of each man in the preference list of each woman
// Ranking[w][m] = i iff WomanPref[w][i] = m

// Current gives the current engagement of each women
// Current[w] = m iff w is currently engaged to m

// Next gives the index of next woman to propose to in the preference list of each man
// Next[m] = i iff m has proposed to all w s.t. ManPref[m][j] = w for j = 1...i-1 but not ManPref[m][i]
int Ranking[505][505], ManPref[505][505], WomanPref[505][505], Next[505], Current[505];

int main()  {
    int T, N, i, j, m, w;

    // list of men who are not currently engaged
    queue <int> freeMen;

    scanf("%d \n", &T);    // No. of test cases
    while (T--) {
        scanf("%d \n", &N);    // No. of men and women
        for (i = 1; i <= N; i++) {
            scanf("%d", &w);    // Woman number (b/w 1 and N) followed by her preference list
            for (j = 1; j <= N; j++)
                scanf("%d", &WomanPref[w][j]);
        }
        for (i = 1; i <= N; i++) {
            scanf("%d", &m);    // Man number (b/w 1 and N) followed by his preference list
            for (j = 1; j <= N; j++)
                scanf("%d", &ManPref[m][j]);
        }

        for (i = 1; i <= N; i++)
            for (j = 1; j <= N; j++)
                Ranking[i][WomanPref[i][j]] = j;

        memset(Current, 0, (N + 1) * sizeof(int));

        for (i = 1; i <= N; i++) {
            freeMen.push(i);
            Next[i] = 1;
        }

        while (!freeMen.empty())    {
            m = freeMen.front();
            w = ManPref[m][Next[m]++];
            if (Current[w] == 0)   {
                Current[w] = m;
             //printf("*******************IF*************************");
             printf("%d %d\n", Current[w], w);	
             //printf("*******************IF*************************");
                freeMen.pop();
            } else if (Ranking[w][m] < Ranking[w][Current[w]])  {
            	
            // printf("******************Else If**************************");
             //printf("%d %d\n", Current[w], w);	
                freeMen.pop();
                freeMen.push(Current[w]);
                Current[w] = m;
               
             //printf("\n %d %d\n", Current[w], w);
             //printf("********************************************");
            }
        }

        // (m, w) pairs in the stable matching
        for (w = 1; w <= N; w++)
            printf("%d %d\n", Current[w], w);
    }

    return 0;
}

/****************************/
Time Complexity O(N^2)
Space Complexity O(N^2)
Run Here http://ideone.com/YizaSm
Read this for details http://www.iiia.csic.es/~ismel/articles/chapter11.pdf
/****************************/
