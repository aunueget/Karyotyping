package basicObjects;
 /*
 * cluster.java
 *
 * Created on December 14, 2004, 7:09 PM
 */
import java.awt.Point;
import java.util.LinkedList;

import MedialAxis.MedialAxisGraph;
import Target.TargetImage;

/**
 *
 * @author  Andrew
 */
public class Cluster {
	private AroundPixel aroundPixel;
    private boolean[][] clusterX;
    private Point clusterSize;
    private Point screenCordinate;
    private Point firstPixel;
    private String title;
    private Cluster next;
    private int pixelCount;
    private int sides;


    private boolean removeThisCluster;
    //double latLongs[];
    /** Creates a new instance of Cluster */
    public Cluster(int imageNum) {
    	clusterX=new boolean[50][50];
    	clusterSize=new Point(50,50);
        initCluster();

    }
    public Cluster() {
    	clusterX=new boolean[50][50];
    	clusterSize=new Point(50,50);
        initCluster();
    }

    public Cluster(Cluster makeNew){
    	//System.out.println("ClusterSize: "+makeNew.ClusterSize.x+","+makeNew.ClusterSize.y);
    	clusterX=new boolean[makeNew.clusterSize.x][makeNew.clusterSize.y];
        initCluster();
        copyCluster(makeNew);
    }
    public Cluster(Point size){
    	clusterX=new boolean[size.x][size.y];
        initCluster();
    }
    public Cluster(short[][] map,int xPoint,int yPoint,int clusterColorID){
    	clusterX=new boolean[map.length][map[0].length];
        initCluster();
       setCluster(map,xPoint,yPoint,clusterColorID); 
    }
    public Cluster(int x,int y){
    	clusterX=new boolean[50][50];
    	clusterSize=new Point(50,50);
      initCluster();
      screenCordinate=new Point(x,y);
    }
    private void initCluster(){
        screenCordinate=new Point(-1,-1);
        firstPixel=new Point(-1,-1);
        title="";
        next=null;
        sides=0;
    	clusterSize=new Point(0,0);
    	pixelCount=0;
    	aroundPixel= new AroundPixel();
        removeThisCluster=true;

    	initArray();

    }
    private void initArray(){
        for(int j=0;j<clusterX[0].length;j++)
        	for(int i=0;i<clusterX.length;i++)
        		clusterX[i][j]=false;

    }
    public void setCluster(short[][] map,int xPoint,int yPoint,int clusterColorID){
        int firstCol=-1;
        int firstRow=-1;
        int bottomRow=-1;
        int farthestRight=-1;
        initCluster();
        int yRefPoint=yPoint;
        int xRefPoint=xPoint;
        if(clusterColorID==0){
        	yRefPoint-=(int)(map[0].length/2);
        	xRefPoint-=(int)(map.length/2);
        }
       for(int i=0;i<map[0].length;i++){            //find the first colom and row that contain anything
            for(int k=0;k<map.length;k++){
                if(map[k][i]==clusterColorID){
                	pixelCount++;
                   if(bottomRow<i){
                       //setScreenCordinate(screenCordinate.x,yRefPoint+i);//put y cordinate at bottom row
                        bottomRow=i;
                    }
                   if(farthestRight<k){
                   	//setScreenCordinate(xRefPoint+k,screenCordinate.y);//put x cordinate farthest rightcolumn
                   	farthestRight=k;
                   }

                    if(firstCol>k){
                        firstCol=k;
                       	setScreenCordinate(xRefPoint+k,screenCordinate.y);//put x cordinate farthest rightcolumn
                    }
                    if(firstRow==-1){
                        firstCol=k;
                        firstPixel.setLocation(k,i);
                        firstRow=i;
                        setScreenCordinate(screenCordinate.x,yRefPoint+i);//put y cordinate at bottom row
                    }
                }

            }
        }
      
        for(int i=firstRow;i<map[0].length;i++){                   //put map into Cluster
            for(int k=firstCol;k<map.length;k++){
                if(i-firstRow<clusterX.length&&k-firstCol<clusterX[0].length){
                	if(i-firstRow>=0&&k-firstCol>=0){
	                    if(map[k][i]==clusterColorID){
	                    	clusterX[k-firstCol][i-firstRow]=true;
	                    }
                	}
                }
                
            }
        }
        //firstPixel.setLocation(firstPixel.x-firstCol,firstPixel.y-firstRow);
        this.clusterSize=new Point(calcSize());

    }
    public boolean isSame(Cluster isCluster){
    	for(int j=0;j<clusterX[0].length;j++)
    		for(int i=0;i<clusterX.length;i++)
	          if(clusterX[i][j]!=isCluster.getPos(i, j))
	            return false;
      return true;
    }
	public int getPixelCount(){
		return pixelCount;
	}
	public boolean getPos(int x,int y){
        return clusterX[x][y];        
    }
    public boolean getPos(Point posX){
    	return clusterX[posX.x][posX.y];
    }
	public boolean getValue(int x,int y){
        return clusterX[x][y];        
    }
    public boolean getValue(Point posX){
    	return clusterX[posX.x][posX.y];
    }
    public void setPos(int x, int y){
        if(!clusterX[x][y])
        	pixelCount++;
    	clusterX[x][y]=true;
    }
    public void setPixel(Point xy,boolean newValue){
    	if(!newValue&&clusterX[xy.x][xy.y]){
    		this.pixelCount--;
    	}
    	else if(newValue&&!clusterX[xy.x][xy.y]){
    		this.pixelCount++;
    	}
    	clusterX[xy.x][xy.y]=newValue;

    }
    public void setPosNot(int x, int y){
        if(clusterX[x][y])
        	pixelCount--;
    	clusterX[x][y]=false;
    }
    public void setPoint(int x, int y){
        setScreenCordinate(x,y);
    }
    public Point getScreenCordinate(){
        return screenCordinate;
    }
    public Point getSCcenter(){
    	int x=screenCordinate.x+((int)Math.floor(this.clusterSize.x/2));
       	int y=screenCordinate.y+((int)Math.floor(this.clusterSize.y/2));
    	return new Point(x,y);
    }
    public void setScreenCordinate(int x, int y){
    	if(x<0)
    		x=0;
    	if(y<0)
    		y=0;
    	this.screenCordinate.setLocation(x,y);
    }
    public void setScreenCordinate(Point xy){
    	if(xy.x<0)
    		xy.x=0;
    	if(xy.y<0)
    		xy.y=0;
    	this.screenCordinate.setLocation(xy.x,xy.y);
    }
    public Point getSize(){
		return clusterSize;
	}
    public void setSize(int x, int y){
    	clusterSize.x=x;
    	
    	clusterSize.y=y;
    }
    public Point calcSize(){
    	Point size=new Point(0,0);
      for(int j=0;j<clusterX[0].length;j++){
    	  for(int i=0; i<clusterX.length;i++){
          if(clusterX[i][j]==true){
            if(i>size.x){
              size.x=i;
            }
            if(j>size.y){
              size.y=j;
            } 
          }
        }
      }
      size.y++;
      size.x++;
      return size;
    }
    
    public Cluster getNext(){
      return next;
    }
    public void setNext(Cluster clusterN){
      next=clusterN;
    }
    public String getTitle(){
      return title;
    }
    public void setTitle(String wordTitle){
      title=wordTitle;
    }
    public boolean[][] getCluster(){
    	return clusterX;
    }
    public void setSides(int sidesX){
    	sides=sidesX;
    }
    public int getSides(){
    	return sides;
    }
    public void setkeepThisCluster(){
    	this.removeThisCluster=false;
    }
    public boolean checkKeepThisCluster(){
    	return (!this.removeThisCluster);
    }
    public void copyCluster(Cluster copyCluster){
    	this.removeThisCluster=copyCluster.removeThisCluster;
    	boolean foundFirstPix=false;
    	this.next=copyCluster.next;
    	//this.myBuckets.copyBuckets(copyCluster.myBuckets.getBucketArray());
    	setScreenCordinate(copyCluster.getScreenCordinate());
        title=copyCluster.getTitle();
        this.clusterSize=new Point(copyCluster.clusterSize);
        for(int j=0;j<clusterX[0].length;j++){
        	for(int i=0; i<clusterX.length;i++){
              if(copyCluster.getPos(i,j)){
            	  this.pixelCount++;
            	  if(!foundFirstPix){
            		  firstPixel.setLocation(i,j);
            		  foundFirstPix=true;
            	  }
            	  clusterX[i][j]=true; 
              }
            }
          }
    }
    public void clusterOut(){
    	System.out.println("ScreenLocation:"+this.screenCordinate.x+","+this.screenCordinate.y);
    	System.out.println(this.getSize().x+","+this.getSize().y+" : "+this.getTitle());
    	System.out.println("PixelCount: "+pixelCount);
    	for(int i=0;i<this.getSize().y;i++){
    		for(int j=0;j<this.getSize().x;j++){
    			if(this.getPos(j, i)){
    				System.out.print('M');
    			}
    			else{
    				System.out.print('_');
    			}
    		}
    		System.out.println("");
    	}
    }
    public void matrixOut(int[][] matrix){
    	System.out.println(this.getSize().x+","+this.getSize().y+" : "+this.getTitle());
    	System.out.println("PixelCount: "+pixelCount);
    	for(int i=0;i<matrix[0].length;i++){
    		for(int j=0;j<matrix.length;j++){
    			if(matrix[j][i]==-5){
    				System.out.print('_');
    			}
    			else{
    				System.out.print(matrix[j][i]);
    			}
    		}
    		System.out.println("");
    	}
    }
    public int calcSides(){
    	int numSides=0;
    	
    	return numSides;
    }
    public Point getFirstPixel(){
    	return firstPixel;
    }

    private int getClockWiseOutline(int direction,Point currPnt,int count){
		if(count++>7){
			return -1;
		}
    	if(direction<0){
			direction=7;
		}
		Point tempPnt=new Point(currPnt.x+aroundPixel.getPos(direction).x,currPnt.y+aroundPixel.getPos(direction).y);
		if(clusterX.length>tempPnt.x&&tempPnt.x>=0
				&&clusterX[0].length>tempPnt.y&&tempPnt.y>=0){
			if(this.getPos(tempPnt)){//find the next spot thats part of the cluster
				//System.out.println("good pnt@"+(currPnt.x+aroundPixel.getPos(direction).x)+","+(currPnt.y+aroundPixel.getPos(direction).y));//error checking
				return direction;
			}
		}
		//rotate around till next spot found
		return getClockWiseOutline(--direction,currPnt,count);
    		//tempPnt.setLocation(currPnt.x+aroundPixel.getPos(direction).x,currPnt.y+aroundPixel.getPos(direction).y);
    		//System.out.println("try pnt: "+direction+"&Point: "+tempPnt);

    }
	private int tryClockWiseDirection(int direction){
   		switch(direction){
			case 0:
				direction=1;
				break;
			case 1:
			case 2:
				direction=3;
				break;
			case 3:
			case 4:
				direction=5;
				break;
			case 5:
			case 6:
				direction=7;
				break;
			case 7:
				direction=1;
				break;
   		}
   		return direction;
	}
	public int getCWOutline(int direction,Point currPnt){
		return  getClockWiseOutline(tryClockWiseDirection(direction), currPnt,0);
	}

	public Point projectToCenter(Point start,int xdir,int ydir){
//		Point end=new Point((int)(Math.round(this.clusterSize.x/2.0)),(int)Math.round(this.clusterSize.y/2.0));
//		double slope=getSlope(start,end);
//		if(slope==0){
//			slope=.0001;
//		}
//		double intercept=(double)start.y-(slope*(double)start.x);
		int partX=start.x;
		int partY=start.y;
		while(partX<this.clusterSize.x&&partY<this.clusterSize.y&&partX>=0&&partY>=0){
			//if(slope>1){
				//partX=(int)Math.round(((double)partY-intercept)/slope);
				if(partX>=0&&partX<this.clusterSize.x&&partY>=0&&partY<this.clusterSize.y){
					if(clusterX[partX][partY]){
						return new Point(partX,partY);
					}
					partY+=ydir;
					partX+=xdir;
				}
			//}
//			else{
//				partY=(int)Math.round(((double)partX*slope)+intercept);
//				if(partX>=0&&partX<this.clusterSize.x&&partY>=0&&partY<this.clusterSize.y){
//					if(clusterX[partX][partY]){
//						return new Point(partX,partY);
//					}
//				}
//				partX+=xdir;
//			}
		}
		return new Point(-1,-1);
	}
	public double getSlope(Point start, Point end){
		if(end.x-start.x==0){
			return 1000.01;
		}
		return (((double)end.y-(double)start.y)/((double)end.x-(double)start.x));
	}
	public void trimCluster(int depth){
		LinkedList<Point> pointList=new LinkedList<Point>();
//		int xdir=1;
//		int ydir=1;
		for(int k=0;k<depth;k++){
			for(int i=0;i<this.clusterSize.x;i++){
//				if(i>=this.clusterSize.x/2.0){
//					xdir=-1;
//				}
//				else{
//					xdir=1;
//				}
				pointList.push(projectToCenter(new Point(i,0),0,1));
				pointList.push(projectToCenter(new Point(i,this.clusterSize.y-1),0,-1));
			}
			for(int j=0;j<this.clusterSize.y;j++){
//				if(j>=this.clusterSize.y/2.0){
//					ydir=-1;
//				}
//				else{
//					ydir=1;
//				}
				pointList.push(projectToCenter(new Point(0,j),1,0));
				pointList.push(projectToCenter(new Point(this.clusterSize.x-1,j),-1,0));	
			}
			while(!pointList.isEmpty()){
				Point temp=pointList.pop();
				if(temp.x!=-1&&temp.y!=-1){
					if(clusterX[temp.x][temp.y]){
						this.pixelCount--;
					}
					this.clusterX[temp.x][temp.y]=false;
				}
			}
		}
	}



}
