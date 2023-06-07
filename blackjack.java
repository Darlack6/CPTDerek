import arc.*;
import java.awt.image.BufferedImage;

public class blackjack{
	public static void main(String[] args){
		Console con = new Console();
		int[][] intArray = new int[52][3];
		int[][] intPlayer = new int[4][2];
		int[][] intDealer = new int[4][2];
		intArray=shuffle(intArray);
		for(int intCounter=0;intCounter<2;intCounter++){	
			intPlayer[intCounter][0]=intArray[intCounter][0];
			intPlayer[intCounter][1]=intArray[intCounter][1];
			intDealer[intCounter][0]=intArray[intCounter+2][0];
			intDealer[intCounter][1]=intArray[intCounter+2][1];
		}
		for(int i=0;i<52;i++){
			System.out.println(intArray[i][0]+"-"+intArray[i][1]+"-"+intArray[i][2]);
		}
		for(int intCounter=0;intCounter<2;intCounter++){
			con.println(intPlayer[intCounter][0]+"-"+intPlayer[intCounter][1]+"\n"+intDealer[intCounter][0]+"-"+intDealer[intCounter][1]);
		}
		BufferedImage playerCard1;
		playerCard1=cardPictures(con,intPlayer);
		con.drawImage(playerCard1,0,0);
		con.repaint();
	}
	public static int[][] shuffle(int intCards[][]){
		for(int i=0;i < 52;i++){
			int intRandom = (int)(Math.random()*100+0);
			if(i<13){
				intCards[i][0]=i+1;
				intCards[i][1]=0;
				intCards[i][2]=intRandom;
			}else if(i<26){
				intCards[i][0]=i-12;
				intCards[i][1]=1;
				intCards[i][2]=intRandom;
			}else if(i<39){
				intCards[i][0]=i-25;
				intCards[i][1]=2;
				intCards[i][2]=intRandom;
			}else{
				intCards[i][0]=i-38;
				intCards[i][1]=3;
				intCards[i][2]=intRandom;
			}
		}
		for(int intCounter2=0;intCounter2<51;intCounter2++){
			for(int intCounter=0;intCounter<51-intCounter2;intCounter++){
				int intTemp;
				int intCurrent=intCards[intCounter][2];
				int intNext=intCards[intCounter+1][2];
				
				if(intNext<intCurrent){
					intTemp=intCards[intCounter+1][2];
					intCards[intCounter+1][2]=intCards[intCounter][2];
					intCards[intCounter][2]=intTemp;
					
					intTemp=intCards[intCounter+1][1];
					intCards[intCounter+1][1]=intCards[intCounter][1];
					intCards[intCounter][1]=intTemp;
					
					intTemp=intCards[intCounter+1][0];
					intCards[intCounter+1][0]=intCards[intCounter][0];
					intCards[intCounter][0]=intTemp;
				}
			}
		}
		return intCards;
	}
	public static BufferedImage cardPictures(Console con, int intCards[][]){
		BufferedImage imgCard=null;
		if(intCards[1][0]==1){
			for(int intCounter2=0;intCounter2<=3;intCounter2++){
				if(intCards[intCounter2][1]==0){
					imgCard = con.loadImage("test.jpeg");
				}else if(intCards[intCounter2][1]==1){
					imgCard = con.loadImage("test.jpeg");
				}else if(intCards[intCounter2][1]==2){
					imgCard = con.loadImage("test.jpeg");
				}else if(intCards[intCounter2][1]==3){						
					imgCard = con.loadImage("test.jpeg");
				}	
			}
		}else if(intCards[1][0]==2){
			imgCard = con.loadImage("test.jpeg");
		}else{
			imgCard = con.loadImage("test.jpeg");
		}
	return imgCard;
	}
}
