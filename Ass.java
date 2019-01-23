import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Ass implements assignment2_27289{

	//Part 1: Image Blending:
	public BufferedImage Blending(String imagePath1, String imagePath2, double r1, double r2) {
		//New Image for the result
		BufferedImage result=null;
		
		try {
			//Read images
			BufferedImage bf1=ImageIO.read(new File(imagePath1));
			BufferedImage bf2=ImageIO.read(new File(imagePath2));
			
			//Getting the image width and height
			int width=bf1.getWidth();
			int height=bf1.getHeight();
			
			result=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
			//Accessing pixels from the two image  
			for (int i = 0; i < height; i++) {
				for(int j=0; j < width; j++ ){
					
					
					//Accessing pixel from first image
					int p1=bf1.getRGB(j,i);
					int a1 = (p1>>24)&0xff;
					int r1img = (p1>>16)&0xff;
					int g1 = (p1>>8)&0xff;
					int b1 = p1&0xff;
					
					//Accessing pixel from second image
					int p2=bf2.getRGB(j,i);
					int a2= (p2>>24)&0xff;
					int r2img = (p2>>16)&0xff;
					int g2 = (p2>>8)&0xff;
					int b2 = p2&0xff;
					
					
					//Multiplying by the ratio
					int aresult=(int)((r1*a1)+(r2*a2));
					int rresult=(int) ((r1img*r1)+(r2img*r2));
					int gresult=(int)((g1*r1)+(g2*r2));
					int bresult=(int)((b1*r1)+(b2*r2));
					
					//setting the result of the ratio with the image
					int presult=(aresult<<24) | (rresult<<16) | (gresult<<8) | bresult;
					result.setRGB(j,i, presult);
					
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	//Part 2: Video down-sampling :
	public void downsampling(String inputPath, String outputPath ,int level) {
		ArrayList<BufferedImage> video=new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> totemporial=new ArrayList<BufferedImage>();
		int levels=(int) Math.pow(2, level);
		//Loading the images into the array list from the folder
		for(int i=225;i<=256;i++){
			File input=new File(inputPath+"/RuskaUfer23-"+i+".jpg");
			try {
				BufferedImage x=ImageIO.read(input);
				video.add(x);
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		
		//Spatial down-sampling
		for(int i=0;i<video.size();i++){
				BufferedImage man1=video.get(i);
				int width=man1.getWidth();
				int height=man1.getHeight();
				//Creating a new buffered image to add the result to it
				BufferedImage man=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
			//Loop for accessing the height of the image
			for(int j=0;j<height;j+=levels){
				//Loop for accessing the width of the image
				for (int l=0;l<width;l+=levels){
					int p=man1.getRGB(l,j);
					//Accessing and setting pixel 1
						for(int k = 0; k < levels; k++){
							for(int h = 0; h < levels; h++)
							{
								if(l+levels<width){
									man.setRGB(l+k, j+h, p);
								}
							}
					}
				}
		}
		try {
				//Writing the Final image to the output path
				File f= new File(outputPath+"/output(Spatial)"+"(level"+level+")"+i+".jpg");
				totemporial.add(man);
				ImageIO.write(man, "jpg", f);
				
		} catch (IOException e) {
				e.printStackTrace();
		}
	}
		

		
	    //Loop for accessing the images from the array list
		for(int i=0;i<totemporial.size();i+=2){
			BufferedImage toman=totemporial.get(i);
				try {
					int x=i+1;
						//Writing the Final image to the output path
						for (int j = 0; j <level; j++) {
							File f= new File(outputPath+"/output(Temporal)"+"(level"+level+")"+i+".jpg");
							File f1= new File(outputPath+"/output(Temporal)"+"(level"+level+")"+x+".jpg");
							ImageIO.write(toman, "jpg", f);
							ImageIO.write(toman, "jpg", f1);
						}
				} catch (IOException e) {
						e.printStackTrace();
				}
					
		}
	}
	

	//Part 3: Image Enhancement:
	public void imageEnhancement(String inputPath, String outputPath, int key) {
		try {
			BufferedImage imageIn=ImageIO.read(new File(inputPath));
			int width=imageIn.getWidth();
			int height=imageIn.getHeight();
			BufferedImage imageOut=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
			switch (key) {
			case 1:
				//Accessing the pixel in the input image
				for(int i=0;i<width;i++){
					for(int j=0;j<height;j++){
						int p=imageIn.getRGB(i,j);
						int a = (p>>24)&0xff;
						int r = (p>>16)&0xff;
						int g = (p>>8)&0xff;
						int b = p&0xff;
						
						//Checking range after adding 50
						int af;
						if(a+50>255){
							af=255;
						}
						else{
							af=a+50;
						}
						int rf;
						if(r+50>255){
							rf=255;
						}
						else{
							rf=r+50;
						}
						int gf;
						if(g+50>255){
							gf=255;
						}
						else{
							gf=a+50;
						}
						int bf;
						if(b+50>255){
							bf=255;
						}
						else{
							bf=a+50;
						}
						
						//setting the value of Image after adding 50
						int presult=(af<<24) | (rf<<16) | (gf<<8) | bf;
						imageOut.setRGB(i,j, presult);
					}
				}
				
				//write the resulting image
				File f=new File(outputPath+"fifty.jpg");
				ImageIO.write(imageOut, "jpg", f);
				break;
				
			case 2:
				for(int i=0;i<width;i++){
					for(int j=0;j<height;j++){
						//Accessing the pixel in the input image
						int p=imageIn.getRGB(i,j);
						int a = (p>>24)&0xff;
						int r = (p>>16)&0xff;
						int g = (p>>8)&0xff;
						int b = p&0xff;
						
						//Checking the range after squaring
						int aa;
						if(a*a>255){
							aa=255;
						}
						else{
							aa=a*a;
						}
						int rr;
						if(r*r>255){
							rr=255;
						}
						else{
							rr=r+50;
						}
						int gg;
						if(g*g>255){
							gg=255;
						}
						else{
							gg=a+50;
						}
						int bb;
						if(b*b>255){
							bb=255;
						}
						else{
							bb=a+50;
						}
						
						//setting the value of Image after after Squaring
						int presult=(aa<<24) | (rr<<16) | (gg<<8) | bb;
						imageOut.setRGB(i,j, presult);
					}
				}
				
				//write the resulting image
				File fsquare =new File(outputPath+"square.jpg");
				ImageIO.write(imageOut, "jpg", fsquare);
				break;
			case 3:		
				
			for(int i=0;i<width;i++){
				for(int j=0;j<height;j++){
					//Accessing the pixel in the input image
					int p=imageIn.getRGB(i,j);
					int a = (p>>24)&0xff;
					int r = (p>>16)&0xff;
					int g = (p>>8)&0xff;
					int b = p&0xff;
					
					//Checking the range after squaring
					int aa;
					if(Math.sqrt(a)>255){
						aa=255;
					}
					else{
						aa=(int) Math.sqrt(a);
					}
					int rr;
					if(Math.sqrt(r)>255){
						rr=255;
					}
					else{
						rr=(int) Math.sqrt(r);
					}
					int gg;
					if(Math.sqrt(g)>255){
						gg=255;
					}
					else{
						gg=(int) Math.sqrt(g);
					}
					int bb;
					if(Math.sqrt(b)>255){
						bb=255;
					}
					else{
						bb=(int) Math.sqrt(b);
					}
					
					//setting the value of Image after adding 50
					int presult=(aa<<24) | (rr<<16) | (gg<<8) | bb;
					imageOut.setRGB(i,j, presult);
				}
			}
			
			//write the resulting image
			File fsquareroot =new File(outputPath+"squareroot.jpg");
			ImageIO.write(imageOut, "jpg", fsquareroot);

				break;
			default:
				System.out.println("Please enter any number from 1 to 3(1:for adding fifty , 2:for squaring each pixel and 3:for squarrooting each pixel)");
				break;
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
			}
	
	
	public static void main(String[] args) {
		Ass testing=new Ass();
		//Part 1.1(call):
		BufferedImage buff=testing.Blending("/Users/israragheb/Desktop/Assignment 2/input.png", "/Users/israragheb/Desktop/Assignment 2/text.png", 0.5, 0.5);
		File f= new File("/Users/israragheb/Desktop/Assignment 2/sample.jpg");
		try {
			ImageIO.write(buff, "jpg", f);
    	} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Part 1.2(call):
		BufferedImage buff2=testing.Blending("/Users/israragheb/Desktop/Assignment 2/input.png", "/Users/israragheb/Desktop/Assignment 2/text.png", 0.7, 0.3);
		File f2= new File("/Users/israragheb/Desktop/Assignment 2/sample1.jpg");
	    try {
			ImageIO.write(buff2, "jpg", f2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Part 2 (call)(level 1)
		testing.downsampling("/Users/israragheb/Desktop/Assignment 2/ImageSeq", "/Users/israragheb/Desktop/Assignment 2/ImageSeq(a)",1);
		
		//Part 2 (call)(level 2)
		testing.downsampling("/Users/israragheb/Desktop/Assignment 2/ImageSeq", "/Users/israragheb/Desktop/Assignment 2/ImageSeq(a)",2);
		
		//Part 3a (call)
		testing.imageEnhancement("/Users/israragheb/Desktop/Assignment 2/input.png", "/Users/israragheb/Desktop/Assignment 2/", 1);
		
		//Part 3b (call)
		testing.imageEnhancement("/Users/israragheb/Desktop/Assignment 2/input.png", "/Users/israragheb/Desktop/Assignment 2/", 2);
		
		//Part 3c (call)
		testing.imageEnhancement("/Users/israragheb/Desktop/Assignment 2/input.png", "/Users/israragheb/Desktop/Assignment 2/", 3);
		
		//Part 3 (default call)
		testing.imageEnhancement("/Users/israragheb/Desktop/Assignment 2/input.png", "/Users/israragheb/Desktop/Assignment 2/", 4);


	}

}
