package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MergeSort {
	
	/**
	 * receive an array as input and
	 * sort it in ascending order
	 * with mergesort algorithm
	 * 
	 * 
	 * @param a Array of Comparables
	 */
	@SuppressWarnings("rawtypes")
	public static void mergeSort(Comparable[] a)
	{
		Comparable[] tmp = new Comparable[a.length];
		mergeSort(a,tmp,0,a.length - 1);
		
	}
	
	/**
	 * receive an array and a boolean as input
	 * if boolean is true sort array in descending order
	 * with mergesort algorithm
	 * 
	 * @param a
	 * @param b
	 */
	@SuppressWarnings("rawtypes")
	public static void mergeSort(Comparable[] a, boolean b)
	{

		mergeSort(a);
		if(b)
		{
			List<Comparable> list = Arrays.asList(a);
			Collections.reverse(list);
			a = list.toArray(a);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void mergeSort(Comparable[] a, Comparable[] tmp, int left, int right)
	{
		if(left < right)
		{
			int center = (left + right) / 2;
			mergeSort(a, tmp, left, center);
			mergeSort(a, tmp, center + 1, right);
			merge(a, tmp, left, center+1, right);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void merge(Comparable[] a, Comparable[] tmp, int left, int right, int rightEnd)
	{
		int leftEnd = right - 1;
		int k = left;
		int num = rightEnd - left + 1;
		
		while(left <= leftEnd && right <= rightEnd)
		{
			if(a[left].compareTo(a[right]) <= 0)
			{
				tmp[k++] = a[left++];
			}else{
				tmp[k++] = a[right++];
			}
		}
		
		while(left <= leftEnd)
		{
			tmp[k++] = a[left++];
		}
		
		while(right <= rightEnd)
		{
			tmp[k++] = a[right++];
		}
		
		for(int i = 0; i < num; i++, rightEnd--)
		{
			a[rightEnd] = tmp[rightEnd];
		}
	}
	

}
