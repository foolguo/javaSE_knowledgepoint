/**
 * @Author: yd
 * @Date: 2019/3/16 9:14
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        int[] array=new int[]{3,2,5,4,7,1,8};

//        bubbleSort(SortHelper.generateRandomArray(100000,0,100000));
//        insertSort(SortHelper.generateRandomArray(100000,0,100000));
//        binaryInsertSort(SortHelper.generateRandomArray(100000,0,100000));
//        shellSort(SortHelper.generateRandomArray(100000,0,100000));
            //shellSort(array);
       // choiceSort(array);
        //mergeSort(array);
        quickSort(array);
       SortHelper.print(array);
    }
    public static void bubbleSort(int[] array){
        long start=System.currentTimeMillis();
        int n=array.length;
        if(n<=1){
            return ;
        }else{
            for(int i=0;i<n;i++){
                int flag=0;
                for(int j=0;j<n-i-1;j++){
                    if(array[j]>array[j+1]){
                        int temp=array[j];
                        array[j]=array[j+1];
                        array[j+1]=temp;
                        flag=1;
                    }
                }
                if(flag==0){
                    break;
                }

            }

        }
        long end=System.currentTimeMillis();
        System.out.println("冒泡排序耗时"+(end-start)+"毫秒");
    }
    public static void insertSort(int[] array){
        long s=System.currentTimeMillis();
        int n=array.length;
        if(n<=1){
            return;
        }else{
            for(int i=1;i<n;i++){
                int j=i-1,value=array[i];
                for(;j>=0;j--){
                    if(array[j]>value){
                        array[j+1]=array[j];
                    }else{
                        break;
                    }
                }
                array[j+1]=value;
            }

        }
        long end=System.currentTimeMillis();
        System.out.println("直接插入排序耗时"+(end-s)+"毫秒");
    }
    public static void binaryInsertSort(int[] array){
        long s=System.currentTimeMillis();
        int n=array.length;
        if(n<=1){
            return ;
        }else{
            for(int i=1;i<n;i++){
                int left=0,right=i-1,value=array[i];
                while(left<=right){
                    int mid=left+(right-left)/2;
                    if(value>array[mid]){
                        left=mid+1;
                    }else{
                        right=mid-1;
                    }
                }
                int j=i-1;
                for(;j>=right+1;j--){
                    array[j+1]=array[j];
                }
                array[right+1]=value;
            }
        }
        long e=System.currentTimeMillis();
        System.out.println("折半排序耗时"+(e-s)+"毫秒");

    }
    public static void shellSort(int[] array){
        long s=System.currentTimeMillis();
        int n=array.length;
        if(n<=1){
            return ;
        }else{
            for(int step=n/2;step>0;step/=2){
                for(int i=step;i<n;i++){
                    int j=i-step,value=array[i];
//                    for (;j>=0;j-=step){
//                        if(value<array[j]){
//                            array[j+step]=array[j];
//                        }else{
//                            break;
//                        }
//                    }
                    while(j>=0&&value<array[j]){
                        array[j+step]=array[j];
                        j-=step;
                    }
                    array[j+step]=value;
                }
            }
        }
        long e=System.currentTimeMillis();
        System.out.print("希尔排序耗时"+(e-s)+"毫秒");
    }
    public static void choiceSort(int[] array){
        int n=array.length;
        if(n<=1){
            return ;
        }else{
            for(int i=0;i<n-1;i++){
                int min=i;
                for(int j=i+1;j<n;j++){
                    if(array[min]>array[j]){
                        min=j;
                    }
                }
                if(min!=i){
                    int temp=array[min];
                    array[min]=array[i];
                    array[i]=temp;
                }

            }
        }

    }
    public static void mergeSort(int[] array){
        int n=array.length;
        if (n<=1){
            return ;
        }else{
            mergeSortInternal(array,0,n-1);
        }
    }
    private static void mergeSortInternal(int[] array,int l,int h){
        if(l>=h){
            return ;
        }
        int mid=l+(h-l)/2;
        mergeSortInternal(array,l,mid);
        mergeSortInternal(array,mid+1,h);
        merge(array,l,mid,h);
    }
    private static void merge(int[] array,int l,int mid,int h){
        int[] temp=new int[h-l+1];
        int i=l,j=mid+1;
        int k=0;
        while(i<=mid&&j<=h){
            if(array[i]<=array[j]){
                temp[k++]=array[i++];
            }else{
                temp[k++]=array[j++];
            }
        }
        int start=i,end=mid;
        if(j<=h){
            start=j;
            end=h;
        }
        while(start<=end){
            temp[k++]=array[start++];
        }
        for(i=0;i<temp.length;i++){
            array[i+l]=temp[i];
        }


    }
    public static void quickSort(int[] array){
        int n=array.length;
        if(n<=1){
            return ;
        }else{
        quickSortInternal(array,0,n-1);
        }
    }
    private static void quickSortInternal(int[] array,int l,int h){
        if(l>=h){
            return ;
        }
        int q=partition3(array,l,h);
        quickSortInternal(array,l,q-1);
        quickSortInternal(array,q+1,h);

    }
    private static int partition1(int[] array,int l,int h){
        int randomIndex=(int) (Math.random()*(h-l+1)+l);
        swap(array,l,randomIndex);
        int j=l;
        int i=l+1;
        int value=array[l];
        for(;i<array.length;i++){
            if(value>array[i]){
                swap(array,j+1,i);
                j++;
            }
        }
        swap(array,j,l);
        return  j;
    }
    private static int partition2(int[] array,int l,int h){
        int randomIndex=((int) (Math.random()*(h-l+1)+l));
        swap(array,randomIndex,l);
        int value=array[l];
        int i=l+1,j=h;
        while(true){
            while(array[i]<value&&i<=h) i++;
            while(array[j]>value&&j>=l+1)j--;
            if(i>j){
                break;
            }
            swap(array,i,j);
            i++;
            j--;
        }
        swap(array,j,l);
        return j;

//        int randomIndex=(int) (Math.random()*(h-l+1)+l);
//        swap(array,randomIndex,l);
//        //array[l+1……i]<value     array[j……r]>value
//        int i=l+1,j=h,value=array[l];
//        while(true){
//            while(array[i]<value&&i<=h)i++;
//            while(array[j]>value&&j>=l)j--;
//            if(i>j){
//                break;
//            }
//            swap(array,i,j);
//            i++;
//            j--;
//        }
//        swap(array,j,l);
//        return j;

    }
    private static int partition3(int[] array,int l,int h){
        int randomIndex=((int) (Math.random()*(h-l+1)+l));
        swap(array,randomIndex,l);
        int value=array[l];
        int lt=l,gt=h+1,i=l+1;
        while(i<gt){
            if(array[i]<value){
                swap(array,lt+1,i);
                i++;
                lt++;
            }else if(array[i]>value){
                swap(array,i,gt-1);
                gt--;
            }else {
                i++;
            }
        }
        swap(array,l,lt);
        return lt;

    }
    private static void swap(int[] array,int i,int j){
        int temp=array[i];
        array[i]=array[j];
        array[j]=temp;
    }
}
