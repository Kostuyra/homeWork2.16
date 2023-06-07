package main.example;
import main.example.exception.ElementNotFoundException;
import main.example.exception.InvalidIndexExeption;
import main.example.exception.NullItemException;

import java.util.Arrays;


public class NewArrayList implements IntegerList {

	private  Integer[] integers;
	private int size;


	public NewArrayList() {
		integers = new Integer[10];
	}

	public NewArrayList(int sizeArray) {
		integers = new Integer[sizeArray];
	}

	@Override
	public Integer add(Integer item) {
		isNull(item);
		validateSize();
		integers[size++] = item;
		return item;
	}

	@Override
	public Integer add(int index, Integer item) {
		validateSize();
		isNull(item);
		validateIndex(index);
		if (index == size) {
			integers[size++] = item;
			return item;
		}
		System.arraycopy(integers, index, integers, index + 1, size - index);
		integers[index] = item;
		size++;
		return item;
	}

	@Override
	public Integer set(int index, Integer item) {
		isNull(item);
		validateIndex(index);
		integers[index] = item;
		return item;
	}

	@Override
	public Integer remove(Integer item) {
		isNull(item);
		int index = indexOf(item);
		if (index == -1){
			throw new ElementNotFoundException();
		}
		remove(index);
		return item;
	}

	@Override
	public Integer remove(int index) {
		validateIndex(index);
		Integer item = integers[index];
		if (index != size) {
			System.arraycopy(integers, index+1, integers, index, integers.length - index - 1);
		}
		size--;
		return item;
	}

	@Override
	public boolean contains(Integer item) {
		Integer[] listCopy = toArray();
		sort(listCopy);
		return binarySearch(listCopy, item);
	}

	@Override
	public int indexOf(Integer item) {
		for (int i = 0; i < size; i++) {
			if (integers[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Integer item) {
		for (int i = size - 1; i >= 0; i--) {
			if (integers[i].equals(item)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Integer get(int index) {
		validateIndex(index);
		return integers[index];
	}

	@Override
	public boolean equals(IntegerList otherList) {
		if (otherList == null) {
			throw new NullItemException();
		}
		return Arrays.equals(this.toArray(), otherList.toArray());
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void clear() {
		size = 0;
	}

	@Override
	public Integer[] toArray() {
		return Arrays.copyOf(integers, size);
	}

	private void sort(Integer arr[]){
		if (arr.length < 2) {
			return;
		}
		int mid = arr.length / 2;
		Integer[] left = new Integer[mid];
		Integer[] right = new Integer[arr.length - mid];

		for (int i = 0; i < left.length; i++) {
			left[i] = arr[i];
		}

		for (int i = 0; i < right.length; i++) {
			right[i] = arr[mid + i];
		}

		sort(left);
		sort(right);

		merge(arr, left, right);
	}

	private boolean binarySearch(Integer[] arr, Integer item){
		int min = 0;
		int max = arr.length - 1;

		while (min <= max) {
			int mid = (min + max) / 2;

			if (item.equals(arr[mid])) {
				return true;
			}

			if (item < arr[mid]) {
				max = mid - 1;
			} else {
				min = mid + 1;
			}
		}
		return false;
	}
	private void isNull(Integer item) {
		if (item == null) {
			throw new NullItemException();
		}
	}

	private void validateSize() {
		if (size == integers.length) {
			grow();
		}
	}

	private void validateIndex(int index) {
		if (index < 0 || index > size) {
			throw new InvalidIndexExeption();
		}
	}

	private void grow(){
		int newSize = (int)(integers.length * 1.5);
		integers = Arrays.copyOf(integers, newSize);
	}
	private static void merge(Integer[] arr, Integer[] left, Integer[] right) {

		int mainP = 0;
		int leftP = 0;
		int rightP = 0;
		while (leftP < left.length && rightP < right.length) {
			if (left[leftP] <= right[rightP]) {
				arr[mainP++] = left[leftP++];
			} else {
				arr[mainP++] = right[rightP++];
			}
		}
		while (leftP < left.length) {
			arr[mainP++] = left[leftP++];
		}
		while (rightP < right.length) {
			arr[mainP++] = right[rightP++];
		}
	}

}
