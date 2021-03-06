import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArrayListBasics {

	public static void main(String[] args) {

		List<Double> numbers = new ArrayList<>();

		Scanner input = new Scanner(System.in);

		try {
			System.out.print("Enter a file name (without .txt): ");
			String filename = input.nextLine();
			input = new Scanner(new File("src/"+filename + ".txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
		}

		while (input.hasNextLine()) {
			numbers.add(input.nextDouble());
		}

		input.close();

		System.out.println("Number of elements: " + numbers.size());
		System.out.println("Sum of the list: " + getSum(numbers));
		System.out.println("Average of the list: " + findAverage(numbers));
		System.out.println("Maximum value: " + getMax(numbers));
		System.out.println("Index of Minimum: " + getMinLocation(numbers));
		System.out.println("Minimum value: " + numbers.get(getMinLocation(numbers)));

		removeElements(numbers);
		System.out.println("Number of elements remaining: " + numbers.size());
		System.out.println(numbers);
	}

	private static double getSum(List<Double> numbers) {
		double sum = 0;
		for (double num : numbers)
			sum += num;
		return sum;
	}

	private static double findAverage(List<Double> numbers) {
		double sum = 0;
		for (double num : numbers)
			sum += num;
		return sum / numbers.size();
	}

	private static double getMax(List<Double> numbers) {
		double max = numbers.get(0);
		
		for(double num: numbers)
			if(num>max)max = num;
		return max;
	}

	private static int getMinLocation(List<Double> numbers) {
		int minIndex = 0;
		double min = numbers.get(0);
		for(int i = 0; i < numbers.size(); i++) {
			if(numbers.get(i)<min) {
				min = numbers.get(i);
				minIndex = i;
			}
		}
		return minIndex;
	}

	private static void removeElements(List<Double> numbers) {
		ArrayList<Double> temp = new ArrayList<Double>();
		for(int i = 0; i < numbers.size(); i++) {
			if(numbers.get(i) >= 5 && numbers.get(i) <= 10) {
				temp.add(numbers.get(i));
			}
		}
		numbers.clear();
		for(int i = 0; i<temp.size(); i++) {
			numbers.add(temp.get(i));
		}
	}

}
