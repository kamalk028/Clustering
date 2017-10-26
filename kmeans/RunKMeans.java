package kmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunKMeans {

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
	public static double jaccardCoefficient(List<Record> objects)
	{
		int num = objects.size();
		boolean P[][] = new boolean[num][num];
		boolean C[][] = new boolean[num][num];
		int m11 = 0;
		int m10 = 0;
		int m01 = 0;
		int m00 = 0;
		for(int i=0; i<num; i++)
		{
			for(int j=0; j<num; j++)
			{
				boolean pVal = false;
				boolean cVal = false;
				Record objI = objects.get(i);
				Record objJ = objects.get(j);
				if(objI.getCluster() == objJ.getCluster())
				{
					cVal = true;
				}
				if(objI.getGroundCluster() == objJ.getGroundCluster())
				{
					pVal = true;
				}
				if (cVal&&pVal)
				{
					m11++;
				}
				else if(cVal && !pVal)
				{
					m10++;
				}
				else if(!cVal && pVal)
				{
					m01++;
				}
				else
				{
					m00++;
				}
				C[i][j] = cVal;
				P[i][j] = pVal;
				
			}
		}
		System.out.println("m11 ==> "+m11);
		System.out.println("m10 ==> "+m10);
		System.out.println("m01 ==> "+m01);
		System.out.println("m00 ==> "+m00);
		double jC = (double)m11/(double)(m11+m10+m01);
		return jC;
	}
	public static void main(String[] args) throws IOException {
		String fileName = "E:\\001 COURSE WORK\\Fall 2017\\CSE 601 - Data Mining\\Project 2\\iyer.txt";
		List<Record> objects = readObjectsFromFile(fileName);
		System.out.println(objects.size());
		/*for(Record r : objects)
		{
			System.out.println(r.getRecordId());
			System.out.println(r.getGroundCluster());
			System.out.println(r.getAttributeValues().size());
			System.out.println(r.getAttributeValues());
		}*/
		KMeans kmeans = new KMeans(objects, 10);
		HashMap<Integer, Cluster> cMap = kmeans.clusters;
		int aggKeys = 0;
		for(Map.Entry<Integer, Cluster> e : cMap.entrySet())
		{
			Cluster c = e.getValue();
			System.out.println("Key ===> "+e.getKey());
			System.out.println(c.getClusterId());
			System.out.println(c.centroidValues);
			HashMap<Integer, Record> rMap = c.getRecords();
			int rNum = rMap.size();
			System.out.println(rNum);
			aggKeys+=rNum;
		}
		System.out.println("Total records held across all clusters ==> "+aggKeys);
		System.out.println("Number of clusters ===> "+cMap.size());
		System.out.println("Printing rId vs cluster id");
		for(Record r: objects)
		{
			System.out.println(r.getRecordId() + " === " + r.getCluster());
		}
		System.out.println("size of records ==> "+objects.size());
		List<Record> clusteredObjects = kmeans.performClustering();
		double jC = jaccardCoefficient(clusteredObjects);
		System.out.println("Jaccard Coefficient ===> "+jC);
		/*System.out.println("Number of records ==> "+clusteredObjects.size());
		for(Record r : clusteredObjects)
		{
			System.out.println(r.getRecordId() + " === "+r.getGroundCluster()+" === "+r.getCluster());
		}*/
		
	}

}
