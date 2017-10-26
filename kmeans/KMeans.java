package kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KMeans {
	List<Record> objects;
	int k; //No of clusters
	HashMap<Integer, Cluster> clusters;
	boolean hasChangeOccurred = false;
	
	public KMeans()
	{
		objects = new ArrayList<Record>();
		k = 0;
		clusters = null;
	}
	public KMeans(List<Record> records, int k)
	{
		this.objects = records;
		this.k = k;
		clusters = getInitialClusters(records, k);
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
	public List<Record> performClustering()
	{
		try
		{
			hasChangeOccurred = true;
			//int iter = 0;
			while(hasChangeOccurred)
			{
				//iter++;
				hasChangeOccurred = false;
				for(Record r : this.objects)
				{
					int rId = r.getRecordId();
					int currentCId = r.getCluster();
					//System.out.println("Record Id, Current Cluster ==> "+rId+ "," + currentCId);
					Cluster curC = this.clusters.get(currentCId);
					double minDis = euclideanDist(curC.getCentroidValues(), r.getAttributeValues());
					//System.out.println("Initial distance ===> "+minDis);
					for(int i=1; i<=this.k; i++)
					{
						if (i==currentCId)
						{
							continue;
						}
						//System.out.println("i ==> "+i);
						Cluster c = this.clusters.get(i);
						//System.out.println("Cluster ==> "+c);
						//System.out.println("Cluster ==> "+c.toString());
						double dist = euclideanDist(c.getCentroidValues(), r.getAttributeValues());
						//System.out.println("Working with cluster "+i);
						//System.out.println("Distance ==> "+dist);
						//System.out.println("Min distance ==> "+minDis);
						if(dist < minDis)
						{
							minDis = dist;
							curC.removeRecord(rId);
							c.addRecord(r);
							r.setCluster(c.getClusterId());
							curC = c;
							this.hasChangeOccurred = true;
						}	
					}
					System.out.println("New cluster value ==> "+r.getCluster());
				}
				if (this.hasChangeOccurred)
				{
					recalculateCentroids();
				}
				System.out.println(this.hasChangeOccurred);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred while clustering");
			e.printStackTrace();
			return null;
		}
		return this.objects;
	}
	public void recalculateCentroids()
	{
		for(Map.Entry<Integer, Cluster> e : clusters.entrySet())
		{
			Cluster c = e.getValue();
			boolean test = c.recalculateCentroidValues();
			if (!test)
			{
				System.out.println("Error while calculating centroid value for cluster : "+c.clusterId);
			}			
		}
	}
	public HashMap<Integer, Cluster> getInitialClusters(List<Record> records, int k)
	{
		HashMap<Integer, Cluster> result = new HashMap<Integer, Cluster>(k);
		int recordNum = records.size();
		Record rec = records.get(0);
		int valueLen = rec.getAttributeValues().size();
		int q = recordNum/k;
		int r = recordNum % k;
		int stIdx = 0;
		for(int i=0; i<k; i++)
		{
			int endIdx = stIdx + q-1;
			if (r>0)
			{
				endIdx++;
				r--;
			}
			HashMap<Integer, Record> rsMap = new HashMap<Integer, Record>();
			for(int j=stIdx; j<=endIdx; j++)
			{
				rec = records.get(j);
				rec.setCluster(i+1);
				rsMap.put(rec.getRecordId(), rec);
			}
			Cluster c = new Cluster(i+1, valueLen, rsMap);
			result.put(c.getClusterId(),c);
			stIdx = endIdx+1;
		}
		System.out.println("Number of records ==> "+recordNum);
		System.out.println("StartIdx ==> "+stIdx);
		return result;
	}
}