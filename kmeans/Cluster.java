package kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cluster {
	int clusterId;
	List<Double> centroidValues = new ArrayList<Double>();
	HashMap<Integer, Record> records;
	//boolean hasChanged = false; %Implementation choice:
	//Lets check this before recalculating centroids
	public int getClusterId() {
		return clusterId;
	}
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	public List<Double> getCentroidValues() {
		return centroidValues;
	}
	public void setCentroidValues(List<Double> centroidValues) {
		this.centroidValues = centroidValues;
	}
	public HashMap<Integer, Record> getRecords() {
		return records;
	}
	public void setRecords(HashMap<Integer, Record> records) {
		this.records = records;
	}
	public void removeRecord(int rId)
	{
		records.remove(rId);
	}
	public void addRecord(Record r)
	{
		records.put(r.getRecordId(), r);
	}
	@Override
	public String toString() {
		return "Cluster [clusterId=" + clusterId + ", centroidValues="
				+ centroidValues + ", records=" + records + "]";
	}
	public Cluster()
	{
		clusterId = 0;
		centroidValues = new ArrayList<Double>();
		records = new HashMap<Integer, Record>();
	}
	public Cluster(int cId, int valueLength, HashMap<Integer, Record> records)
	{
		clusterId = cId;
		this.records = records;
		centroidValues = new ArrayList<Double>(valueLength);
		for(int i=0; i<valueLength; i++)
		{
			centroidValues.add(0.0);
		}
		recalculateCentroidValues();
	}
	public boolean recalculateCentroidValues()
	{
		try
		{
			List<Double> aggValues = null;
			int recordsNum = records.size();
			int vSize = 0;
			for(Map.Entry<Integer, Record> e : this.records.entrySet())
			{
				Record r = e.getValue();
				List<Double> values = r.getAttributeValues();
				vSize = values.size();
				if (aggValues == null)
				{
					aggValues = new ArrayList<Double>(vSize);
					for(int j=0; j<vSize; j++)
					{
						aggValues.add((double) 0);
					}
				}
				for(int i=0; i<vSize; i++)
				{
					double newVal = aggValues.get(i) + values.get(i);
					aggValues.set(i, newVal);
				}
			}
			for(int k=0; k<vSize; k++)
			{
				double newVal = aggValues.get(k)/recordsNum;
				this.centroidValues.set(k, newVal);
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
}
