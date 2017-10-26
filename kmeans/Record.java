package kmeans;

import java.util.List;

public class Record {
	int recordId;
	int groundCluster;
	List<Double> attributeValues;
	int cluster;
	public Record()
	{
		recordId = 0;
		groundCluster = 0;
		attributeValues = null;
		cluster = 0;
	}
	public Record(int rId, int gc, List<Double> values, int c)
	{
		recordId = rId;
		groundCluster = gc;
		attributeValues = values;
		cluster = c;
	}
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public int getGroundCluster() {
		return groundCluster;
	}
	public void setGroundCluster(int groundCluster) {
		this.groundCluster = groundCluster;
	}
	public List<Double> getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(List<Double> attributeValues) {
		this.attributeValues = attributeValues;
	}
	public int getCluster() {
		return cluster;
	}
	public void setCluster(int cluster) {
		this.cluster = cluster;
	}
}
