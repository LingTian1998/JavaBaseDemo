import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.Scanner;

public class SortAlgorithms {
    public static void main(String[] args){
        //int[] a={2,4,1,7,4,5,324,23,4325};
        int[] a={7,15,3,6,10,9};
        //insertSort_Array(a);
        quickSort(a,0,a.length-1);
        //bubbleSort(a);
        for (Integer integer:a)
            System.out.println(integer);


        System.out.println(binarySearch(a,15));
    }
    public static void insertSort_Array(int[] a){
        for (int i = 1;i<a.length;i++){
            int j=0;
            for (;j<i;j++){
                if (a[i]<a[j])
                    break;
            }
            if (j==i)
                continue;


            int temp = a[i];
            int temp_index = j;
            for (j=i;j>=temp_index+1;j--)
                a[j]=a[j-1];

            a[temp_index]=temp;
        }
    }

    public static void quickSort(int[] a,int start ,int end){
        int base  = a[start];
        int i=start;
        int j=end;

        while (i<=j){
            while (a[i]<base&&i<end)
                i++;
            while (a[j]>base&&j>start)
                j--;

            if (i<=j){    //如果没有等号 i==j时的那个值会被同时放入两次递归中
                int temp = a[i];
                a[i]=a[j];
                a[j]=temp;
                i++;
                j--;
            }
        }
        if (i<end){
            quickSort(a,i,end);
        }
        if (start<j){
            quickSort(a,start,j);
        }

    }

    public static void bubbleSort(int[] a){
        int last = a.length-1;
        while (last!=1){
            for (int i=0;i<last;i++){
                if (a[i]>a[i+1]){
                    int temp =a[i];
                    a[i]=a[i+1];
                    a[i+1]=temp;
                }
            }
            last--;
        }
    }

    public static int binarySearch(int[] array,int target){
        int start =0;
        int end   = array.length-1;
        int index = 0;
        if ((start+end)%2!=0){
             index = (start+end+1)/2;
        }
        else
             index = (start+end)/2;
        int cur   = array[index];
        while (cur!=target){
            if (cur<target)
                start= index;
            if (cur>target)
                end  =index;

            if ((start+end)%2!=0){
                index = (start+end+1)/2;
            }
            else
                index = (start+end)/2;
            cur   = array[index];
        }
        return index;
    }
}
