import Target.ChromosomeCluster;


public class TestShape {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TargetGetter test2=new TargetGetter(args[0]);
		String filename;
		filename=test2.getNewFiles(args[0]);
		if(filename!=null){
			int shapeNum=test2.findBackground(filename);
			shapeNum=test2.findChromosomes(filename, shapeNum);
			ChromosomeCluster testCluster=test2.clusterList.get(0);
			test2.clusterList.get(0).clusterOut();
		}
	}

}
