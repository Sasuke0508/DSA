/*
There are N courses 1, 2, ..., N that must be assigned to M periods 1, 2, ..., M.
Each course i has credit ci and has some courses as prerequisites. The load of a
period is defined to be the sum of credits of courses assigned to that period.
The prerequisite information is represented by a matrix ANxN in which Ai,j = 1
indicates that course i must be assigned to a period before the period to which
the course j is assigned. Compute the solution satisfying constraints
+ Satisfy the prerequisites constraints: if Ai,j = 1, then course i must be
assigned to a period before the period to which the course j is assigned
+ The maximum load for all periods is minimal

 Input
    Line 1 contains N and M (2 ≤ N ≤16, 2 ≤ M ≤ 5)
    Line 2 contains c1, c2, ..., cN
    Line i+2 (i = 1,..., N) contains the i th line of the matrix A

 Output
    Unique line contains that maximum load for all periods of the solution
   found

 Example
Input             Output
6 2               12
4 4 4 4 2 4
0 0 0 0 0 0
0 0 0 0 0 0
0 0 0 0 0 0
0 0 1 0 0 0
0 0 1 0 0 0
1 0 0 0 0 0

*/

// Author: Nguyen Van Quan
// ID: OJT_203

import java.util.Scanner;
import java.util.Vector;

public class BCAP {
    int n, m, ans;
    int credits[];
    int prerequisites[][];
    Vector<Vector<Integer>> results;

    public BCAP(){
        results = new Vector<>();
        ans = 999999;
    }

    public void input(){
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        credits = new int[n];
        prerequisites = new int[n][n];
        for (int i =0; i<n; i++){
            credits[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                prerequisites[i][j] = sc.nextInt();
            }
        }
    }

    public boolean isValid(int period, int course){
        for (int i = 0; i <= period; i++) {
            for(int j=0; j< results.get(i).size(); j++){
                int r = results.get(i).get(j).intValue();
                if(prerequisites[course][r] == 1){
                    return false;
                }
            }
        }
        for(int i = period + 1; i<m; i++){
            for(int j = 0; j<results.get(i).size(); j++){
                int r = results.get(i).get(j).intValue();
                if(prerequisites[r][course] == 1){
                    return false;
                }
            }
        }
        return true;
    }

//    Tìm tổng số tín chỉ lớn nhất trong các kỳ học
    public int maxSumPeriod(){
        int maxSum = 0;
        for(int i=0; i< results.size(); i++){
            int sum = 0;
            for (Integer integer: results.get(i)) {
                sum += integer.intValue();
            }
            maxSum = Math.max(sum, maxSum);
        }
        return maxSum;
    }

    public void BACP(int count){
        if(count == n){
            ans = Math.min(ans, maxSumPeriod());
            return;
        }
        for(int i = count; i< n; i++) {
            int tmp = 0;
            for (int j = 0; j < m; j++) {
                if (isValid(j, i)) {
                    if (j == m - 1) {
                        tmp = m;
                    }
                    results.get(j).add(i);
                    count++;
                    BACP(count);
                    results.get(j).remove(results.get(j).size() - 1);
                    tmp++;
                    count--;
                } else {
                    tmp++;
                }
            }
            if (tmp >= m) {
                break;
            }
        }
    }

    public boolean checkInput(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(prerequisites[i][j] == prerequisites[j][i] && prerequisites[i][j] == 1){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BCAP bcap = new BCAP();
        bcap.input();
        if(bcap.checkInput()){
            for(int i=0; i< bcap.m; i++){
                Vector<Integer> v = new Vector<>();
                bcap.results.add(v);
            }
            bcap.BACP(0);
        }
        System.out.println(bcap.ans);
    }
}
