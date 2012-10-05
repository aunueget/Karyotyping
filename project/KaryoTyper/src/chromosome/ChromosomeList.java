package chromosome;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import MedialAxis.MedialAxisGraph;

public class ChromosomeList {
	private LinkedList<ChromosomeCluster> chromosomeList;
	private GeneticSlideImage img;
	
	public ChromosomeList(LinkedList<ChromosomeCluster> newList,GeneticSlideImage newImg){
		this.chromosomeList=newList;
		img=newImg;
	}

    public void printChromosomes(){
		for(int i=0;i<this.chromosomeList.size();i++){
			ChromosomeCluster tempCluster=this.chromosomeList.get(i);
			MedialAxisGraph tempGraph2=new MedialAxisGraph(tempCluster.getMedialAxis().getSkeltonPoints());
			tempCluster.getMedialAxis().fillInSkeleton(tempCluster,tempGraph2);
//			tempGraph2.removeSegments((int)Math.round((img.getAverage())), -1);
//			tempShape.setMedialAxis(tempGraph2.getMedialAxis());
//			tempShape.fillInSkeleton(tempGraph2);
			//TODO(aamcknig):remove segments only if they don't have interesctions at both ends
				if(tempCluster.checkKeepThisCluster()){
					writeTargetImage(tempCluster,tempCluster.getMedialAxis().getSkeltonPoints(),new Color(255,0,0));
				}
				else{
					writeRemovedImage(tempCluster);
				}
		}
		this.chromosomeList=new LinkedList<ChromosomeCluster>();
}
public void writeTargetImage(ChromosomeCluster tempCluster,LinkedList<Point> colorPoints,Color paintColor){
	try {
		File curDir=new File(".");
		String imageName=new File(tempCluster.getTitle()).getName();
		File outputfile = new File(curDir.getCanonicalPath()+"/shapeData/Keep/"+imageName.substring(0,imageName.indexOf('.'))+"_"+(tempCluster.getClusterNimageID())+".png");
		BufferedImage tempImg=img.getSubImage(tempCluster,colorPoints,paintColor);//,targetImgBorderSize);//30pixel border
	    ImageIO.write(tempImg, "png", outputfile);
	} catch (IOException e) {
	    System.out.println(e);
	}
}

public void writeISOClineImage(ChromosomeCluster tempCluster){
	try {
		File curDir=new File(".");
		String imageName=new File(tempCluster.getTitle()).getName();
		File outputfile = new File(curDir.getCanonicalPath()+"/shapeData/Keep/"+imageName.substring(0,imageName.indexOf('.'))+"_"+(tempCluster.getClusterNimageID())+"ISO"+".png");//,tempShape.getTitle().indexOf(".jpg"))+"_"+(inImageTargetCount)+".png"
		BufferedImage tempImg=img.getISOcline(tempCluster.getMedialAxis().getDistanceMap());//,targetImgBorderSize);//30pixel border
	    ImageIO.write(tempImg, "png", outputfile);
	} catch (IOException e) {
	    System.out.println(e);
	}

}

public void writeRemovedImage(ChromosomeCluster tempCluster){
	try {
		File curDir=new File(".");
		String imageName=new File(tempCluster.getTitle()).getName();
		File outputfile = new File(curDir.getCanonicalPath()+"/shapeData/Removed/"+imageName.substring(0,imageName.indexOf('.'))+"_"+(tempCluster.getClusterNimageID())+".png");//,tempShape.getTitle().indexOf(".jpg"))+"_"+(inImageTargetCount)+".png"
		BufferedImage tempImg=img.getSubImage(tempCluster,null,null);//,targetImgBorderSize);//30pixel border
	    ImageIO.write(tempImg, "png", outputfile);
	} catch (IOException e) {
		System.out.println(e);
	}

}
}
