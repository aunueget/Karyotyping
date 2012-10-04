package Target;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import Color.ISOClineColor;
import MedialAxis.DistanceMap;
import basicObjects.Cluster;


public class TargetImage {

    private String comments;
    private	BufferedImage img;
	private double aboveGroundLevelFeet;
    private double imgLat;
    private double imgLong;
    private double heading;
    private int hours;
    private int mins;
    private int secs;
    private double []grayScale=new double[256];
    private LinkedList<Integer> chromosomeWidth;
    private double averageWidth;
	public TargetImage(String filename){
		img = null;
		averageWidth=-1;
		chromosomeWidth=new LinkedList<Integer>();
		for(int i=0;i<grayScale.length;i++){
			grayScale[i]=0;
		}
		try {
	        	if(filename.contains("imag")){
	        		System.out.println();
	        		System.out.println();
	        		System.out.println("Starting file: "+filename.substring(filename.indexOf("imag")));
	        	}
	        img = ImageIO.read(new File(filename));
	        System.out.println("Image Height: "+img.getHeight()+" Width: "+img.getWidth());
		} catch (IOException e) {
			System.out.println(e);
		}


	}
    public BufferedImage getSubImage(Cluster targetCluster){
		BufferedImage tempImg=new BufferedImage(targetCluster.getSize().x,targetCluster.getSize().y,BufferedImage.TYPE_3BYTE_BGR);
		for(int i=targetCluster.getScreenCordinate().x;i<(targetCluster.getScreenCordinate().x+targetCluster.getSize().x);i++){
			for(int j=targetCluster.getScreenCordinate().y;j<(targetCluster.getScreenCordinate().y+targetCluster.getSize().y);j++){
				if(targetCluster.getValue(i-targetCluster.getScreenCordinate().x, j-targetCluster.getScreenCordinate().y))
				tempImg.setRGB(i-targetCluster.getScreenCordinate().x, j-targetCluster.getScreenCordinate().y, img.getRGB(i,j));
				else{
					tempImg.setRGB(i-targetCluster.getScreenCordinate().x, j-targetCluster.getScreenCordinate().y, (Color.BLACK).getRGB());
				}
			}

		}
		return tempImg;
    }
    public BufferedImage getSubImage(Cluster targetCluster,LinkedList<Point> pointList,Color draw){
		BufferedImage tempImg=new BufferedImage(targetCluster.getSize().x,targetCluster.getSize().y,BufferedImage.TYPE_3BYTE_BGR);
		for(int i=targetCluster.getScreenCordinate().x;i<(targetCluster.getScreenCordinate().x+targetCluster.getSize().x);i++){
			for(int j=targetCluster.getScreenCordinate().y;j<(targetCluster.getScreenCordinate().y+targetCluster.getSize().y);j++){
				if(targetCluster.getValue(i-targetCluster.getScreenCordinate().x, j-targetCluster.getScreenCordinate().y))
				tempImg.setRGB(i-targetCluster.getScreenCordinate().x, j-targetCluster.getScreenCordinate().y, img.getRGB(i,j));
				else{
					tempImg.setRGB(i-targetCluster.getScreenCordinate().x, j-targetCluster.getScreenCordinate().y, (Color.WHITE).getRGB());
				}
			}

		}
		if(pointList!=null&&!pointList.isEmpty()){
			for(int i=0;i<pointList.size();i++){
				tempImg.setRGB(pointList.get(i).x, pointList.get(i).y, (draw).getRGB());
			}
		}
					
		return tempImg;
    }
    public BufferedImage getISOcline(DistanceMap distanceMap){
		BufferedImage tempImg=new BufferedImage(distanceMap.getWidth(),distanceMap.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		for(int i=0;i<distanceMap.getHeight();i++){
			for(int j=0;j<distanceMap.getWidth();j++){
				if(distanceMap.getDistanceFromEdge(new Point(j, i))<0){
					tempImg.setRGB(j,i,Color.BLUE.getRGB());
				}
				else{
					Color temp=ISOClineColor.getColor(distanceMap.getDistanceFromEdge(new Point(j, i)));
					tempImg.setRGB(j,i,temp.getRGB());
				}
			}

		}
					
		return tempImg;
    }

    public BufferedImage getSubImage(Cluster targetCluster,int borderPixels){
		BufferedImage tempImg=new BufferedImage(targetCluster.getSize().x+(2*borderPixels),targetCluster.getSize().y+(2*borderPixels),BufferedImage.TYPE_3BYTE_BGR);
		for(int i=targetCluster.getScreenCordinate().x-borderPixels;i<(targetCluster.getScreenCordinate().x+targetCluster.getSize().x+borderPixels);i++){
			for(int j=targetCluster.getScreenCordinate().y-borderPixels;j<(targetCluster.getScreenCordinate().y+targetCluster.getSize().y+borderPixels);j++){
				if(i>=0&&j>=0&&i<img.getWidth()&&j<img.getHeight()){
					tempImg.setRGB(i-(targetCluster.getScreenCordinate().x-borderPixels), j-(targetCluster.getScreenCordinate().y-borderPixels), img.getRGB(i,j));
				}
				else{
					tempImg.setRGB(i-(targetCluster.getScreenCordinate().x-borderPixels), j-(targetCluster.getScreenCordinate().y-borderPixels),(new Color(0,0,0,0).getRGB()));
				}
			}

		}
		return tempImg;
    }
    public String getMetaData(){
    	return this.comments;
    }

	public Color getColorAt(int x,int y){
		return this.convertPixel(img.getRGB(x,y));
	}
	public double getAboveGroundLevelFeet() {
		return aboveGroundLevelFeet;
	}
	public double getImgLat() {
		return imgLat;
	}
	public double getImgLong() {
		return imgLong;
	}
	public double getHeading() {
		return heading;
	}
	public void addWidth(int width){
		this.calcNewAvg(width);
		this.chromosomeWidth.add(width);
	}
	public double calcFinalAverage(){
		this.recalcAvgWidth();
		return this.averageWidth;
	}
	public double getAverage(){
		return this.averageWidth;
	}

    public Color convertPixel(int pixel){
	      int red = (pixel & 0x00ff0000) >> 16;
	      int green = (pixel & 0x0000ff00) >> 8;
	      int blue = pixel & 0x000000ff;
	      return new Color(red,green,blue);

  }
    public int getImgHeight(){
    	return img.getHeight();
    }
    public int getImgWidth(){
    	return img.getWidth();
    }
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMins() {
		return mins;
	}
	public void setMins(int mins) {
		this.mins = mins;
	}
	public int getSecs() {
		return secs;
	}
	public void setSecs(int secs) {
		this.secs = secs;
	}
	public void computeScale(){
		Color tempColor=new Color(0,0,0);
		for(int i=100;i<this.getImgWidth();i++){
			for(int j=100;j<this.getImgHeight();j++){
				tempColor=this.getColorAt(i,j);
				double tempGreyPixel=(.299*tempColor.getRed())+(.587*tempColor.getGreen())+(.114*tempColor.getBlue());
				this.grayScale[(int)Math.round(tempGreyPixel)]++;
				
			}
		}
	}
	public void graphScale(){
		 try{
			 // Create file 
			 FileWriter fstream = new FileWriter("GrayScale.txt",true);
			 BufferedWriter out = new BufferedWriter(fstream);
			 String buffer="";
			 for(int i=0;i<this.grayScale.length;i++){//out.write("Hello Java");
				  buffer+=""+this.grayScale[i]+",";
			 }
			 out.write(buffer);
			 out.write("\n");
			  
			 //Close the output stream
			 out.close();
			 //Catch exception if any
		 } catch (Exception e){
			 System.err.println("Error: " + e.getMessage());
		 }
			  
			
	}
	public void writeChromosomesWidth() {
		System.out.print("Widths for this image: ");
		for(int i=0;i<this.chromosomeWidth.size();i++){
			System.out.print(this.chromosomeWidth.get(i)+",");
		}
	}
	public void calcNewAvg(int newWidth){
		if(this.chromosomeWidth.isEmpty()){
			this.averageWidth=newWidth;
		}
		else{
			this.averageWidth=(((this.averageWidth*this.chromosomeWidth.size())+newWidth)/(this.chromosomeWidth.size()+1));
		}
	}
	public void recalcAvgWidth(){
		double temp=-1;
		LinkedList<Integer> goodWidths=new LinkedList<Integer>();
		if(this.chromosomeWidth.size()>2){
			for(int i=0;i<this.chromosomeWidth.size();i++){
				if(Math.abs(this.averageWidth-((double)this.chromosomeWidth.get(i)))<3){
					goodWidths.add(this.chromosomeWidth.get(i));
				}
			}

			for(int i=0;i<goodWidths.size();i++){
				temp=temp+goodWidths.get(i);			
			}
			this.averageWidth=temp/goodWidths.size();
		}
		System.out.println("AverageWidth: "+this.averageWidth);
	}


}
