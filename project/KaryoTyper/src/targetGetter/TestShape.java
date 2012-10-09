package targetGetter;

import Target.TargetShape;


public class TestShape {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TargetGetter test2=new TargetGetter(args[0]);
		String filename;
		filename=test2.getNewFiles(args[0]);
		if(filename!=null){
			int shapeNum=test2.findBackground(filename);
			shapeNum=test2.findChromosomes(filename, shapeNum);
			TargetShape testShape=test2.shapeList.get(0);
			test2.shapeList.get(0).shapeOut();
			
		}
	}

	public static TargetShape getImageObject(String filename) {
		TargetGetter test2=new TargetGetter("C:\\Users\\Robert\\Desktop\\SchoolWork\\CSC492\\repo\\project\\Karyotyper\\");
		TargetShape testShape = null;
		if(filename!=null){
			int shapeNum=test2.findBackground(filename);
			shapeNum=test2.findChromosomes(filename, shapeNum);
			testShape=test2.shapeList.get(0);
			test2.shapeList.get(0).shapeOut();
			
		}
		return testShape;
	}

}
