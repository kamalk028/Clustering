package density;

import java.util.ArrayList;
import java.util.List;


public class DBScan {
	double distMatrix[][];
	public List<Cluster> getClusters() {
		return clusters;
	}
	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}
	public List<Record> getPoints() {
		return points;
	}
	public void setPoints(List<Record> points) {
		this.points = points;
	}
	public double getEps() {
		return eps;
	}
	public void setEps(double eps) {
		this.eps = eps;
	}
	public int getMinPts() {
		return minPts;
	}
	public void setMinPts(int minPts) {
		this.minPts = minPts;
	}
	List<Cluster> clusters;
	List<Record> points;
	double eps;
	int minPts;
	int recordsNum;
	public DBScan()
	{
		distMatrix = null;
		clusters = null;
		points = null;
		eps = 0.0;
		minPts = 0;
	}
	public DBScan(List<Record> points, double eps, int minPts)
	{
		recordsNum = points.size();
		this.points = points;
		this.eps = eps;
		this.minPts = minPts;
		distMatrix = new double[recordsNum][recordsNum];
		clusters = new ArrayList<Cluster>();
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
		Record rI = points.get(i);
		Record rJ = points.get(j);
		return euclideanDist(rI.getAttributeValues(), rJ.getAttributeValues());
	}
	public boolean initializeDistMatrix()
	{
		try
		{
			for(int i=0; i<recordsNum; i++)
			{
				for(int j=0; j<recordsNum; j++)
				{
					if(i == j)
					{
						distMatrix[i][j] = 0;
					}
					else if(distMatrix[i][j] <= 0)
					{
						distMatrix[i][j] = distMatrix[j][i] = calculateDistance(i, j);
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
	public List<Integer> regionQuery(int p)
	{
		List<Integer> result = new ArrayList<Integer>();
		for(int j=0; j<recordsNum; j++)
		{
			if (distMatrix[p][j] < eps)
			{
				result.add(j);
			}
		}
		return result;
	}
	public boolean expandCluster(Record p, List<Integer> neighborsIdxs , Cluster c)
	{
		try
		{
			p.setCluster(c.getClusterId());
			c.addPoint(p);
			int length = neighborsIdxs.size();
			for(int i=0; i<length; i++)
			{
				int nIdx = neighborsIdxs.get(i);
				Record n = points.get(nIdx);
				if (!n.visited)
				{
					n.setVisited(true);
					List<Integer> newNeighborsIdxs = regionQuery(nIdx);
					if(newNeighborsIdxs.size() >= minPts)
					{
						neighborsIdxs.addAll(newNeighborsIdxs);
						length = neighborsIdxs.size();
					}	
				}
				if(n.getCluster() <= 0)
				{
					n.setCluster(c.getClusterId());
					c.addPoint(n);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred while expandCluster...");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public List<Cluster> performClustering()
	{
		Cluster c = null;
		int nextCId = 0;
		for(int i=0; i<recordsNum; i++)
		{
			Record point = points.get(i);
			if(!point.isVisited())
			{
				point.setVisited(true);
				List<Integer> neighborIdxs = regionQuery(i);
				if(neighborIdxs.size() < minPts)
				{
					point.setCluster(-1);
				}
				else
				{
					nextCId++;
					c = new Cluster(nextCId);
					expandCluster(point, neighborIdxs, c);
					clusters.add(c);
				}
				
			}
		}
		
		return clusters;
	}
}
