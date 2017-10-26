package density;

import java.util.List;

public class Record {
	int recordId;
	int groundCluster;
	List<Double> attributeValues;
	int cluster;
	boolean visited = false;
	public Record()
	{
		recordId = 0;
		groundCluster = 0;
		attributeValues = null;
		cluster = 0;
	}
	public Record(int rId, int gC, List<Double> values)
	{
		recordId = rId;
		groundCluster = gC;
		attributeValues = values;
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
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
}
