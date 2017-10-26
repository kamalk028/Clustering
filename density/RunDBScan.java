package density;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class RunDBScan {
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
			Record r = new Record(rId, gc, attValues);
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
		double rand = (double)(m11+m00)/(double)(m11+m10+m01+m00);
		System.out.println("Rand Coefficient ==> "+rand);
		return jC;
	}
	public static void writeToFile(String output)
	{
		
	}
	public static void findOptValues(String inputFile, String outputFile) throws IOException
	{
		double stEps = 0.2;
		double edEps = 3;
		int stMinPts = 2;
		int edMinPts = 20;
		double epsInc = 0.1;
		int minPtsInc = 1;
		//StringBuffer sb;
		double maxJc = 0.0;
		double optEps = 0.0;
		int optMinPts = 0;
		for (double eps=stEps; eps <= edEps; eps+=epsInc)
		{
			for(int minPts=stMinPts; minPts <= edMinPts; minPts+=minPtsInc)
			{
				System.out.println("Trying for : ");
				System.out.println("Eps ==> "+eps);
				System.out.println("MinPts ==> "+minPts);
				//sb = new StringBuffer();
				List<Record> objects = readObjectsFromFile(inputFile);
				DBScan db = new DBScan(objects, eps, minPts);
				List<Cluster> clusters = db.performClustering();
				System.out.println("Number of clusters "+clusters.size());
				List<Record> clusteredObjects = db.getPoints();
				double jC = jaccardCoefficient(clusteredObjects);
				System.out.println("Jaccard Coefficient ==> "+jC);
				if(jC > maxJc)
				{
					maxJc = jC;
					optEps = eps;
					optMinPts = minPts;
				}
			}
		}
		System.out.println("Max JC is "+maxJc);
		System.out.println("Optimum eps is "+optEps);
		System.out.println("Optimum eps is "+optMinPts);
	}
	public static void main(String[] args) throws IOException {
		double eps = 1.03;
		int minPts = 4;
		String fileName = "E:\\001 COURSE WORK\\Fall 2017\\CSE 601 - Data Mining\\Project 2\\cho.txt";
		//findOptValues(fileName, null);
		List<Record> objects = readObjectsFromFile(fileName);
		DBScan db = new DBScan(objects, eps, minPts);
		List<Cluster> clusters = db.performClustering();
		System.out.println("Number of clusters " + clusters.size());
		List<Record> clusteredObjects = db.getPoints();
		for(Cluster c : clusters)
		{
			HashSet<Integer> rIds = c.getPoints();
			System.out.println("Record ids for cluster "+c.getClusterId());
			for(int p : rIds)
			{
				System.out.print(p+",");
			}
			System.out.println();
		}
		double jC = jaccardCoefficient(clusteredObjects);
		System.out.println("Jaccard Coefficient = > "+jC);
	}

}
