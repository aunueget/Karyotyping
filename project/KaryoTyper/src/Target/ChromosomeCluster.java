package Target;

import java.awt.Color;
import java.awt.Point;

import MedialAxis.DistanceMap;
import MedialAxis.MedialAxis;
import basicObjects.Cluster;

public class ChromosomeCluster extends Cluster {

	private double imgHeading;
    private int clusterNimageID;
    private String metaData;
    private Color colorOShape;
    private int colorCount;
    private double aboveGroundLevel;
    private ChromosomeCluster next;
    private double sizex;
    private double sizey;
    private MedialAxis medialAxis;
    
 	public ChromosomeCluster(){
    	super();
    	initChromosomeCluster();
    }
	public ChromosomeCluster(int ChromosomeNum){
		super();
		initChromosomeCluster();
		this.clusterNimageID=ChromosomeNum;
	}
	public ChromosomeCluster(Point size){
		super(size);
		initChromosomeCluster();
	}
    public ChromosomeCluster(short[][] map,int xPoint,int yPoint,int shapeColorID){
    	super(map,xPoint,yPoint,shapeColorID);
    	initChromosomeCluster();
        
    }
    public ChromosomeCluster(ChromosomeCluster makeNew){
    	super((Cluster)makeNew);
    	initChromosomeCluster();
        copyChromosome(makeNew);
    }
	public int getWidths(int pos){
		return this.medialAxis.getObjectWidth()[pos];
	}

    private void initChromosomeCluster(){
    	this.metaData="";
    	colorOShape=new Color(0,0,0);
    	this.colorCount=0;
    	this.aboveGroundLevel=0;
    	this.medialAxis=new MedialAxis();
    	next=null;
    }


    public MedialAxis getMedialAxis() {
		return medialAxis;
	}
	public void setMedialAxis(MedialAxis medialAxis) {
		this.medialAxis = medialAxis;
	}
    public void setImgHeading(double newHeading){
    	this.imgHeading=newHeading;
    }
    public double getImgHeading(){
    	return this.imgHeading;
    }
    public int getClusterNimageID(){
    	return this.clusterNimageID;
    }
    public void setClusterNimageID(int ID){
    	this.clusterNimageID=ID;
    }
    public String getMetaData(){
    	return this.metaData;
    }
    public void setMetaData(String data){
    	this.metaData=data;
    }
    public int getColorCount(){
    	return this.colorCount;
    }
    public void setColorCount(int count){
    	this.colorCount=count;
    }
    public void setColor(Color colorX){
    	this.colorOShape=colorX;
    }
    public Color getColor(){
    	return this.colorOShape;
    }
    public double getAGL(){
    	return this.aboveGroundLevel;
    }
    public void setAGL(double agl){
    	this.aboveGroundLevel=agl;
    }
    public ChromosomeCluster getNext(){
        return next;
    }
    public void setNext(ChromosomeCluster clusterN){
        next=clusterN;
    }
    public double getSizex(){
        return this.sizex;
    }
    public double getSizey(){
        return this.sizey;
    }
    public void setSizex(double size){
        this.sizex = size;
    }
    public void setSizey(double size){
        this.sizey = size;
    }
    public void copyChromosome(ChromosomeCluster copyChromosome){
    	this.metaData=copyChromosome.getMetaData();
    	this.aboveGroundLevel=copyChromosome.getAGL();
    	this.colorCount=copyChromosome.getColorCount();
    	this.clusterNimageID=copyChromosome.getClusterNimageID();
    	colorOShape=copyChromosome.getColor();
    	this.imgHeading=copyChromosome.imgHeading;
        this.medialAxis=new MedialAxis(copyChromosome.medialAxis);

    }
}
