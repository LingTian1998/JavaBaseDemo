package main.Algorithm.PartitionAlgorithm;

import java.util.ArrayList;

/**
 * 求最大子数组
 */
public class MaxSubAarryProblem {
    public static void main(String[] args){
        int[] input = new int[]{
                100,113,110,85,105,102,86,63,81,101,94,106,101,79,94,90,97,100,110,121,109,99
        };

        int[] diffArray = new int[input.length-1];
        for (int i=1;i<input.length;i++){
            diffArray[i-1] = input[i]-input[i-1];
        }
        System.out.println(find_maximum_subarray(diffArray,0,diffArray.length-1)[2]);
        System.out.println(antherWay(diffArray));
    }

    public static int[] find_max_crossing_subarray(int[] A,int low, int mid,int high){
        int left_max = Integer.MIN_VALUE;
        int sum=0;
        int marker_left = -1;
        for (int i = mid; i>=low;i--){
            sum = sum + A[i];
            if (sum>left_max)
            {
                left_max=sum;
                marker_left = i;
            }
        }

        int right_max = Integer.MIN_VALUE;
        sum = 0;
        int marker_right = -1;
        for (int i = mid+1; i<= high;i++){
            sum=sum+A[i];
            if (sum>right_max)
            {
                right_max=sum;
                marker_right=i;
            }
        }
        return new int[]{marker_left,marker_right,right_max+left_max};
    }

    /**
     * 将问题分解成三部分，左 中 右； 左右不包含mid 中间包含
     * @param A
     * @param low
     * @param high
     * @return
     */
    public static int[] find_maximum_subarray(int[] A,int low,int high){
        if (high==low){
            return new int[]{low,high,A[low]};
        }
        else {
            int mid = (low+high)/2;
            int[] result_left = find_maximum_subarray(A,low,mid);

            int[] result1_right = find_maximum_subarray(A,mid+1,high);

            int[] result2_cross = find_max_crossing_subarray(A,low,mid,high);

            if (result_left[2]>=result1_right[2] && result_left[2]>=result2_cross[2]){
                return result_left;
            }
            else if (result1_right[2]>=result_left[2]&&result1_right[2]>=result2_cross[2]){
                return result1_right;
            }
            else
                return result2_cross;
        }
    }

    public static int antherWay(int[] A){
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for (int i = A.length-1;i>=0;i--){
            if (sum>=0){
                sum=sum+A[i];
            }
            if (sum<0)
                sum=0;

            if (sum>max)
                max=sum;

        }
        return max;
    }
}
