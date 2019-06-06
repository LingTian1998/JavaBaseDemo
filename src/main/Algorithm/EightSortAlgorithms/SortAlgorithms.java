package main.Algorithm.EightSortAlgorithms;

import java.util.ArrayList;

public class SortAlgorithms {
    public static void main(String[] args){
        //int[] a={2,4,1,7,4,5,324,23,4325};
        //int[] a={4,1,16,2,9,3,10,14,8,7};
        //int[] a={4,1,3,2,16,9,10,14,8,7};
        int[] a={5,1,1,2,0,0};
        //int[] a={-4,0,7,4,9,-5,-1,0,-7,-1};
        //insertSort_Array(a);
        //quickSort(a,0,a.length-1);
        //bubbleSort(a);
        //BUILD_MAX_HEAP(a);
        //heap_sort(a);
        //MergeSort(a,0,a.length-1);
        quick_sort(a,0,a.length-1);
        for (Integer integer:a)
            System.out.println(integer);


//        System.out.println(binarySearch(a,15));
    }

    /**
     * 插入排序
     * @param a
     */
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

    /**
     * 快速排序
     * @param a
     * @param start
     * @param end
     */
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

    /**
     * 冒泡排序
     * @param a
     */
    public static void bubbleSort(int[] a){
        int last = a.length-1;
        while (last!=0){
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

    /**
     * 二分查找
     * @param array
     * @param target
     * @return
     */
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


    /**
     * 归并排序
     * @param array
     * @param left
     * @param right
     */
    public  static void MergeSort(int[] array,int left,int right){
        int currentSize = 1;
        int arraySize = right-left+1;
        while (currentSize<arraySize){
            int step = currentSize*2;
            for (int i =left;i<right;i=i+step){
                if (i+step-1<right)
                    merge(array,i,i+currentSize-1,i+step-1);
                else
                    merge(array,i,i+currentSize-1,right);
            }
            currentSize*=2;
        }
    }

    /**
     *
     * @param data
     * @param begin 游标1开始下标
     * @param mid   游标1结束下标
     * @param end   游标2结束下标
     */
    public static void merge(int[] data, int begin,int mid,int end){
        int[] tempArray = new int[end-begin+1];
        int cursor_1 = begin;
        int cursor_2 = mid+1;
        int index = 0;
        while (cursor_1<=mid&&cursor_2<=end){
            if (data[cursor_1]<=data[cursor_2]){
                tempArray[index]=data[cursor_1];
                cursor_1++;
            }else {
                tempArray[index]=data[cursor_2];
                cursor_2++;
            }
            index++;
        }
        if (cursor_1==mid+1){
            while (cursor_2<=end)
            {
                tempArray[index++]=data[cursor_2++];
            }
        }
        if (cursor_2==end+1)
        {
            while (cursor_1<=mid)
                tempArray[index++]=data[cursor_1++];
        }
        for (int i=0,j=begin;i<=index-1&&j<=end;i++,j++){
            data[j]=tempArray[i];
        }
    }
    /**
     * 堆排序
     */

    /**
     * 用于维护堆的性质
     * @param A
     * @param i
     */
    public static void MAX_HEAPIFY(int[] A, int i,int heapSize){
        int l = LEFT(i+1)-1;
        int r = RIGHT(i+1)-1;

        int larger = i;
        if (l<=heapSize-1&&A[l]>A[larger])
            larger = l;
        if (r<=heapSize-1&&A[r]>A[larger])
            larger = r;
        if (larger!=i)
        {
            swap(A,larger,i);
            MAX_HEAPIFY(A,larger,heapSize);
        }
    }
    public static void swap(int[] A, int i, int j){
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        return;
    }
    public static int LEFT(int i){
        return 2*i;
    }
    public static int RIGHT(int i){
        return 2*i+1;
    }

    /**
     * 建堆
     */
    public static void BUILD_MAX_HEAP(int[] A){
        int heap_size = A.length;
        for ( int i = (heap_size)/2-1; i>=0; i--)
            MAX_HEAPIFY(A,i,A.length);
    }

    /**
     * 堆排序
     */
    public static void heap_sort(int[] A){
        BUILD_MAX_HEAP(A);
        int heap_size = A.length;
        for (int i=A.length-1; i>=1;i--){
            swap(A,0,i);
            heap_size-=1;
            MAX_HEAPIFY(A,0,heap_size);
        }
    }


    /**
     * 算法导论上的快排  基于分治法
     */
    public static int partition(int[] A,int p ,int r){
        int x = A[r];
        int i = p-1;
        for (int j=p;j<=r-1;j++){
            if (A[j]<=x){
                i=i+1;
                swap(A,i,j);
            }
        }
        swap(A,i+1,r);
        return i+1;
    }

    public static void quick_sort(int[] A,int p,int r){
        if (p<r){
            int q = partition(A,p,r);
            quick_sort(A,p,q-1);
            quick_sort(A,q+1,r);
        }
    }
}
