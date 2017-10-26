package agglomerative;
import java.util.ArrayList;
import java.util.List;

import kmeans.*;

public class AgglomerativeClustering {
	List<Record> objects;
	int k; //No of desired clusters
	double distMatrix[][];
	int minDistance[];
	int numRecords;
	public AgglomerativeClustering()
	{
		objects = null;
		k = 0;
		distMatrix = null;
		minDistance = null;
		numRecords = 0;
	}
	public AgglomerativeClustering(List<Record> records, int k)
	{
		this.objects = records;
		this.k = k;
		numRecords = records.size();
		this.distMatrix = new double[numRecords][numRecords];
		minDistance = new int[numRecords];
		initializeDistMatrix();
	}
	public double euclideanDist(List<Double> v1, List<Double> v2)
	{
		double result = 0.0;
		int len = v1.size();
		double ssd = 0.0;
		for(int i=0; i<len; i++)
		{
			double one = v1.get(i);
			double two = v2.get(i);
			double sqDiff = Math.pow((one-two), 2.0);
			ssd = ssd+sqDiff;
		}
		result = Math.sqrt(ssd);
		return result;
	}
	public double calculateDistance(int i, int j)
	{
		Record rI = objects.get(i);
		Record rJ = objects.get(j);
		return euclideanDist(rI.getAttributeValues(), rJ.getAttributeValues());
	}
	public boolean initializeDistMatrix()
	{
		try
		{
			for(int i=0; i<numRecords; i++)
			{
				for(int j=0; j<numRecords; j++)
				{
					if(i == j)
					{
						distMatrix[i][j] = Double.POSITIVE_INFINITY;
					}
					else if(distMatrix[i][j] <= 0)
					{
						distMatrix[i][j] = distMatrix[j][i] = calculateDistance(i, j);
					}
					if(distMatrix[i][j] < distMatrix[i][minDistance[i]])
					{
						minDistance[i] = j;
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred while initializing distance matrix. ");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public List<String> performClustering()
	{
		List<String> result = new ArrayList<String>();
		for(int x=0; x<numRecords-1; x++)
		{
			int c1 = 0;
			for(int i=0; i<numRecords; i++)
			{
				if(distMatrix[i][minDistance[i]] < distMatrix[c1][minDistance[c1]])
				{
					c1 = i;
				}
			}
			int c2 = minDistance[c1];
			String cluster = objects.get(c1).getRecordId()+","+objects.get(c2).getRecordId();
			result.add(cluster);
			System.out.println("Clusters ==> "+cluster);
			for(int j=0; j<numRecords; j++)
			{
				if(distMatrix[c2][j] < distMatrix[c1][j])
				{
					distMatrix[c1][j] = distMatrix[j][c1] = distMatrix[c2][j];
				}
			}
			distMatrix[c1][c1] = Double.POSITIVE_INFINITY;
			for(int i=0; i<numRecords; i++)
			{
				distMatrix[c2][i] = distMatrix[i][c2] = Double.POSITIVE_INFINITY;
			}
			
			for(int j=0; j<numRecords; j++)
			{
				if(minDistance[j] == c2)
				{
					minDistance[j] = c1;
				}
				if(distMatrix[c1][j] < distMatrix[c1][minDistance[c1]])
				{
					minDistance[c1] = j;
				}
			}
		}
		return result;
	}
}
