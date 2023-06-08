import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class workingfile{
	public static void main(String[] args){
		Console con = new Console(1280,720);
		int intMenu=homescreen(con);
		if(intMenu==1){
			highscores(con);
		}
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
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",35);
		con.setDrawFont(fntBaron);
		TextInputFile highscores = new TextInputFile("highscores.txt");
		
		con.drawImage(imgHighscoreScreen,0,0);
		con.repaint();
	}
}
