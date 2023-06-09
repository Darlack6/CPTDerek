import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Color;

public class workingfile{
	public static void main(String[] args){
		Console con = new Console(1280,720);
		int intMenu=homescreen(con);
		while(intMenu!=0){
			if(intMenu==0){
				break;
			}else if(intMenu==1){
				highscores(con);
				intMenu=homescreen(con);
			}else if(intMenu==2){
				con.closeConsole();
			}
		}
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
		//for(int intCounter=0;intCounter<2;intCounter++){
			//con.println(intPlayer[intCounter][0]+"-"+intPlayer[intCounter][1]+"\n"+intDealer[intCounter][0]+"-"+intDealer[intCounter][1]);
		//}
		BufferedImage imgPlayerCard1=cardPictures(con,intPlayer[0][0],intPlayer[0][1]);
		BufferedImage imgPlayerCard2=cardPictures(con,intPlayer[1][0],intPlayer[1][1]);
	}
	
	public static int homescreen(Console con){
		int inty=50;
		int intMenuChoice=0;
		BufferedImage imgMMBackground = con.loadImage("../images/mainmenu.png");
		BufferedImage imgSpades = con.loadImage("../images/pointer.png");
		con.drawImage(imgMMBackground,0,0);
		con.drawImage(imgSpades,-610,inty);
		con.repaint();
		while(true){
			int intKeypress = con.getKey();
			if(intKeypress==40){
				if(inty==50){
					intMenuChoice=1;
					inty=inty+110;
				}else if(inty==160){
					intMenuChoice=2;
					inty=inty+75;
				}
				con.drawImage(imgMMBackground,0,0);
				con.drawImage(imgSpades,-610,inty);
			}else if(intKeypress==38){
				if(inty==160){
					intMenuChoice=0;
					inty=inty-110;
				}else if(inty==235){
					intMenuChoice=1;
					inty=inty-75;
				}
				con.drawImage(imgMMBackground,0,0);
				con.drawImage(imgSpades,-610,inty);
			}else if(intKeypress==10){
				break;
			}
			con.repaint();
			con.sleep(33);
		}
		return intMenuChoice;
	}
	
	public static void highscores(Console con){
		BufferedImage imgHighscoreScreen = con.loadImage("../images/highscore.png");
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",30);
		con.setDrawFont(fntBaron);
		TextInputFile txtHighscores = new TextInputFile("highscores.txt");
		con.drawImage(imgHighscoreScreen,0,0);
		
		int intCounter=1;
		int inty=150;
		while(txtHighscores.eof()==false){
			String stringHighscore=txtHighscores.readLine();
			int intHighscore=txtHighscores.readInt();
			
			if(intCounter==1){
				con.setDrawColor(new Color(217,160,101));
			}else{
				con.setDrawColor(Color.WHITE);
			}
			con.drawString(intCounter+". "+stringHighscore+" | "+intHighscore+" Dollars",480,inty);
			intCounter++;
			inty=inty+65;
		}
		con.repaint();
		while(true){
			int intKeypress = con.getKey();
			//System.out.println(intKeypress);
			if(intKeypress==27){
				break;
			}
			con.sleep(33);
			con.repaint();
		}
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
	public static BufferedImage cardPictures(Console con, int intValue,int intSuit){
		BufferedImage imgCard=null;
		if(intValue==1){
			if(intSuit==0){
				imgCard = con.loadImage("ace_of_diamonds.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("ace_of_clubs.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("ace_of_hearts.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("ace_of_diamonds.png");
			}	
		}else if(intValue==2){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==3){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==4){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==5){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}	
		}else if(intValue==6){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==7){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==8){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==9){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==10){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==11){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==12){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}
		}else if(intValue==13){
			if(intSuit==0){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==1){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==2){
				imgCard = con.loadImage("test.jpeg");
			}else if(intSuit==3){						
				imgCard = con.loadImage("test.jpeg");
			}	
		}
	return imgCard;
	}
}
