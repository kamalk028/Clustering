package agglomerative;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kmeans.Record;

public class RunAgglomerativeClustering {
	
	private static BufferedReader br;
	
	public static List<Record> readObjectsFromFile(String fileName) throws IOException
	{
		List<Record> objects = new ArrayList<Record>();
		br = new BufferedReader(new FileReader(fileName));
		String curLine;
		while((curLine = br.readLine()) != null)
		{
			String arr[] = curLine.split("	");
			int rId = Integer.parseInt(arr[0]);
			int gc = Integer.parseInt(arr[1]);
			List<Double> attValues = new ArrayList<Double>(arr.length - 2);
			for(int i=2; i<arr.length; i++)
			{
				double value = Double.parseDouble(arr[i]);
				attValues.add(value);
			}
			Record r = new Record(rId, gc, attValues, 0);
			objects.add(r);
		}
		return objects;
	}
	public static void main(String[] args) throws IOException {
		String fileName = "E:\\001 COURSE WORK\\Fall 2017\\CSE 601 - Data Mining\\Project 2\\iyer.txt";
		List<Record> objects = readObjectsFromFile(fileName);
		AgglomerativeClustering aC = new AgglomerativeClustering(objects, 10);
		List<String> result = aC.performClustering();
		System.out.println("printing the Strings ==> ");
		for(String s : result)
		{
			System.out.println(s);
		}
	}

}
