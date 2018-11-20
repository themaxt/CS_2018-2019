import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class MilkMeasurement {

	public static void main(String[] args) {
		Scanner file;
		try
		{
			String filename = "src/2.in";
			file = new Scanner (new File(filename));
			int logNum = file.nextInt();
			int G = file.nextInt();
			
			ArrayList<Log> logs = new ArrayList<Log>(logNum);
			for(int i = 0; i < logNum; i++) {
				int day = file.nextInt();
				int cow = file.nextInt();
				String changeString = file.next();
				int change = 0;
				if(changeString.charAt(0) == '+') {
					change = Integer.parseInt(changeString.substring(1));
				}
				else {
					change = -1 * Integer.parseInt(changeString.substring(1));
				}
//				System.out.println(day+" "+cow+ " "+change);
				logs.add(new Log(day, cow, change));
			}
			Collections.sort(logs, new Comparator<Log>() {
			    @Override
			    public int compare(Log o1, Log o2) {
					if(o1.getDay() < o2.getDay()) return -1;
					else if(o1.getDay() > o2.getDay()) return 1;
					else return 0;
			    }
			});
			for(int i = 0; i< logs.size(); i++) {
				System.out.println(i+" "+logs.get(i));
			}
			System.out.println();
			
			ArrayList<Cow> below = new ArrayList<Cow>(logNum);	
			ArrayList<Cow> above = new ArrayList<Cow>(logNum);
			
			int max = G;
			int maxCowNum = -1;
			int count = 0;
			
			for(int i = 0; i < logNum; i++) {
				boolean topChanged = false;
				
				int cow = logs.get(i).getCow();
				int change = logs.get(i).getChange();
				
				if(containsCow(below, cow)) {
					int index = indexOfCow(below, cow);
					int newVal = below.get(index).getValue() + change;
					if(newVal < G) {
						below.get(index).setValue(newVal);
						System.out.println(i+" below to below");
					}
					else if(newVal > G) {
						below.remove(index);
						
						above.add(new Cow(cow, newVal));
						if(newVal >= max) {
							max = newVal;
							if(cow != maxCowNum) {
								maxCowNum = cow;
								topChanged = true;
							}
						}
						System.out.println(i+" below to above");
					}
					else {
						if(change > 0) {
							above.remove(index);
							if(newVal >= max) {
								max = newVal;
								if(cow != maxCowNum) {
									maxCowNum = cow;
									topChanged = true;
								}
							}
							System.out.println(i+" below to G");
						}
						else {
							above.remove(index);
							if(above.size() == 0) {
								topChanged = true;
							}
							System.out.println(i+" below to G");
						}
					}
				}
				else if(containsCow(above, cow)) {
					int index = indexOfCow(above, cow);
					int oldVal = above.get(index).getValue();
					int newVal = above.get(index).getValue() + change;
					if(newVal < G) {
						above.remove(index);
						
						below.add(new Cow(cow, newVal));
						//change max
						int tempMax = G;
						int tempMaxCowNum = 0;
						for(int k = 0; k< above.size(); k++) {
							if(above.get(k).getValue() > tempMax) {
								tempMax = above.get(k).getValue();
								tempMaxCowNum = k;
							}
						}
						if(tempMax > max && tempMaxCowNum != cow) {
							max = tempMax;
							maxCowNum = cow;
							topChanged = true;
						}
						System.out.println(i+" above to below");
					}
					else if(newVal > G) {
						above.get(index).setValue(newVal);
						if(newVal > max) {
							max = newVal;
							if(cow != maxCowNum) {
								maxCowNum = cow;
								topChanged = true;
							}
						}
						else if(oldVal == max && change <0) {
							int tempMax = G;
							for(int k = 0; k< above.size(); k++) {
								if(above.get(k).getValue() > tempMax && k != index) {
									tempMax = above.get(k).getValue();
								}
							}
							if(tempMax == newVal) {
								max = tempMax;
								maxCowNum = cow;
								topChanged = true;
							}
						}
						System.out.println(i+" above to above");
					}
					else {
						if(change > 0) {
							above.remove(index);
							if(newVal >= max) {
								max = newVal;
								if(cow != maxCowNum) {
									maxCowNum = cow;
									topChanged = true;
								}
							}
							System.out.println(i+" above to G");
						}
						else {
							above.remove(index);
							if(above.size() == 0) {
								topChanged = true;
							}
							System.out.println(i+" above to G");
						}
					}
				}
				else {
					int newVal = G + change;
					if(change > 0) {
						above.add(new Cow(cow, newVal));
						if(newVal >= max) {
							max = newVal;
							if(cow != maxCowNum) {
								maxCowNum = cow;
								topChanged = true;
							}
						}
						System.out.println(i+" G to above");
					}
					else {
						below.add(new Cow(cow, newVal));
						if(above.size() == 0) {
							topChanged = true;
						}
						System.out.println(i+" G to below");
					}
				}
				if(topChanged) {
					System.out.println(i+" *****TOP CHANGED*****");
					count++;
				}
			}
			System.out.println(count);
			
//			for(Cow cow: above) {
//				System.out.println(cow);
//			}

		} 
		catch (FileNotFoundException e)
		{
			System.out.println("fileNotFound");
		}

	}
	
	public static boolean containsCow(ArrayList<Cow> cows, int num) {
		for(Cow cow: cows) {
			if(cow.getNum() == num) return true;
		}
		return false;
	}
	
	public static int indexOfCow(ArrayList<Cow> cows, int num) {
		for(int i = 0; i < cows.size(); i++) {
			if(cows.get(i).getNum() == num) return i;
		}
		return -1;
	}

}
