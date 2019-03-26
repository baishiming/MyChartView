package com.xietong.mychartview.Test;

/**
 * Author : baishiming
 * Time : 2019/3/25
 * e_mail :
 * function:
 */
public class BubbleSort {


    public static void main(String[] arg){
        int[] M = {1,2,4,2,100};
        int[] N = {2,6,9};


        print(reverseMergeSortArray(M,N));

        int a = FindRepeat(M);
        System.out.println(a);
    }

    public static void print(int[] a){
        for (int i : a) {
            System.out.println(i);
        }
    }


    public static void bubbleSort(int[] numbers){
        int temp = 0;
        int n = numbers.length;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-i-1;j++){
                if(numbers[j]>numbers[j+1]){
                    temp = numbers[j+1];
                    numbers[j+1] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
    }



    public static int getMiddle(int[] numbers,int low,int high){
        int temp = numbers[low];
        while(low<high){
            while (low<high && numbers[high] >= temp){
                high--;
            }
            numbers[low] = numbers[high];

            while (low<high && numbers[low] < temp){
                low++;
            }
            numbers[high] = numbers[low];
        }
        numbers[low] = temp;
        return low;
    }


    public static void quickSort(int[] numbers,int low,int high){
        if(low<high){
            int middle = getMiddle(numbers,low,high);
            quickSort(numbers,low,middle-1);
            quickSort(numbers,middle+1,high);

        }

    }

    public static void quick(int[] numbers){
        if(numbers!=null && numbers.length>0){
            quickSort(numbers,0,numbers.length-1);
        }
    }


    /**
     * 二分查找
     * @param numbers
     * @param searchValue
     * @return
     */
    public static int binarySearch(int[] numbers,int searchValue){
        int low = 0;
        int high = numbers.length;
        while (low<=high){
            int middle = (low + high) /2;
            if(searchValue == numbers[middle]){
                return middle;
            }else if(searchValue > numbers[middle]){
                high = middle -1;
            }else {
                low = middle +1;
            }
        }
        return -1;
    }


    public static int[]  reverseMergeSortArray(int A[],int B[]){
        int temp_A = A.length-1;
        int temp_B = B.length-1;
        int Combine_len = A.length+ B.length;
        int[] combine = new int[Combine_len];
        for(int i = Combine_len-1;i>=0;i--)
        {
            //while((temp_A>=0)&&(temp_B>=0))

            if(temp_A>=0 && temp_B>=0){
                if(A[temp_A]>= B[temp_B])
                {
                    combine[i] = A[temp_A];
                    temp_A--;
                }
                else if(A[temp_A] < B[temp_B])
                {
                    combine[i] = B[temp_B];
                    temp_B--;
                }
            }else {
                if((temp_A<0)&&(temp_B>=0))
                {
                    combine[i] = B[temp_B];
                    temp_B--;
                }

                if((temp_B<0)&&(temp_A>=0))
                {
                    combine[i] = A[temp_A];
                    temp_A--;
                }
            }
        }
        return combine;
    }



    public static int FindRepeat(int[] arr)
    {
        for (int i = 0; i < arr.length; i++) {
            for (int i1 = 0; i1 < arr.length; i1++) {
                if(i!=i1){
                    if(arr[i] == arr[i1]){
                        return arr[i];
                    }
                }
            }

        }
        return 0;
    }

}
