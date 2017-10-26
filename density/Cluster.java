package density;

import java.util.HashSet;

public class Cluster {
	int clusterId;
	HashSet<Integer> points;
	public Cluster()
	{
		clusterId = 0;
		points = null;
	}
	public Cluster(int cId)
	{
		clusterId = cId;
		points = new HashSet<Integer>();
	}
	public void addPoint(Record point)
	{
		points.add(point.getRecordId());
	}
	public int getClusterId() {
		return clusterId;
	}
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	public HashSet<Integer> getPoints() {
		return points;
	}
	public void setPoints(HashSet<Integer> points) {
		this.points = points;
	}
}
