import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Color;

public class blackjack{
	static int intUserBalance=1000;
	public static void main(String[] args){
		Console con = new Console(1280,720);
		boolean blnContinousGame=true;
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
		while(blnContinousGame==true){
			con.clear();
			int intCount=2;
			int intDealerCounter=2;
			int intPlayerSum=0;
			int intDealerSum=0;
			boolean blnCondition=true;
			boolean blnLoseCondition=false;
			boolean blnWinOffDeal=false;
			boolean blnDealerBust=false;

			int intUserBet=bet(con);

			int[][] intArray = new int[52][3];
			int[][] intPlayer = new int[6][2];
			int[][] intDealer = new int[6][2];
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

			BufferedImage imgPlayerCard1=cardPictures(con,intPlayer[0][0],intPlayer[0][1]);
			BufferedImage imgPlayerCard2=cardPictures(con,intPlayer[1][0],intPlayer[1][1]);
			BufferedImage imgDealerCard1=cardPictures(con,intDealer[0][0],intDealer[0][1]);
			BufferedImage imgBackside=con.loadImage("../cards/backside.png");
			BufferedImage imgTable=con.loadImage("../images/tablescreen.png");
			
			con.setDrawColor(Color.BLACK);
			con.fillRect(0,0,1280,720);
			con.drawImage(imgTable,0,1);

			con.drawImage(imgPlayerCard1,590,460); //540 and 660
			con.drawImage(imgPlayerCard2,610,470);
			con.drawImage(imgBackside,590,210);
			con.drawImage(imgDealerCard1,610,220);
			con.repaint();
			
			int intPlayerCardValue1=cardValues(intPlayer[0][0]);
			int intPlayerCardValue2=cardValues(intPlayer[1][0]);
			int intPlayerCardValue3=0;
			int intPlayerCardValue4=0;
			int intPlayerCardValue5=0;
			intPlayerSum=intPlayerCardValue1+intPlayerCardValue2;
			System.out.println(intPlayerCardValue1+" "+intPlayerCardValue2);
		

			BufferedImage imgPlayerCard3=null;
			BufferedImage imgPlayerCard4=null;
			BufferedImage imgPlayerCard5=null;

			while(blnCondition==true){
				if(intPlayerCardValue1+intPlayerCardValue2==21){
					blnWinOffDeal=true;
					blnLoseCondition=false;
					break;
				}
				int intKeypress = con.getKey();
				System.out.println(intKeypress);

				if(intKeypress==72){
					intPlayer[intCount][0]=intArray[intCount+2][0];
					intPlayer[intCount][1]=intArray[intCount+2][1];
					if(intCount==2){
						imgPlayerCard3=cardPictures(con,intPlayer[intCount][0],intPlayer[intCount][1]);	
						intPlayerCardValue3=cardValues(intPlayer[intCount][0]);
						intPlayerSum=intPlayerSum+intPlayerCardValue3;
						con.drawImage(imgTable,0,0);
						
						con.drawImage(imgPlayerCard1,590,460); 
						con.drawImage(imgPlayerCard2,610,470);
						con.drawImage(imgPlayerCard3,630,460);

						con.drawImage(imgBackside,590,210);
						con.drawImage(imgDealerCard1,610,220);

						System.out.println(intPlayerCardValue3);
						con.repaint();
					}else if(intCount==3){
						imgPlayerCard4=cardPictures(con,intPlayer[intCount][0],intPlayer[intCount][1]);
						intPlayerCardValue4=cardValues(intPlayer[intCount][0]);
						intPlayerSum=intPlayerSum+intPlayerCardValue4;
						con.drawImage(imgTable,0,0);
						
						con.drawImage(imgPlayerCard1,570,460); 
						con.drawImage(imgPlayerCard2,590,470);
						con.drawImage(imgPlayerCard3,610,460);
						con.drawImage(imgPlayerCard4,630,470);

						con.drawImage(imgBackside,590,210);
						con.drawImage(imgDealerCard1,610,220);
						
						con.repaint();
					}else if(intCount==4){
						imgPlayerCard5=cardPictures(con,intPlayer[intCount][0],intPlayer[intCount][1]);
						intPlayerCardValue5=cardValues(intPlayer[intCount][0]);
						intPlayerSum=intPlayerSum+intPlayerCardValue5;
						con.drawImage(imgTable,0,0);
						
						con.drawImage(imgPlayerCard1,570,460); 
						con.drawImage(imgPlayerCard2,590,470);
						con.drawImage(imgPlayerCard3,610,460);
						con.drawImage(imgPlayerCard4,630,470);
						con.drawImage(imgPlayerCard5,650,460);

						con.drawImage(imgBackside,590,210);
						con.drawImage(imgDealerCard1,610,220);
						
						con.repaint();
					}
					intCount++;
				}else if(intKeypress==83){
					blnCondition=false;
					blnLoseCondition=false;
					System.out.println(intPlayerCardValue1+" "+intPlayerCardValue2+" "+intPlayerCardValue3+" "+intPlayerCardValue4+" "+intPlayerCardValue5);
				}

				if(intCount==5){
					blnCondition=false;
					blnLoseCondition=false;
				}

				if(intPlayerSum>21){
					if(intPlayerCardValue1==11){
						intPlayerCardValue1=1;
						intPlayerSum=intPlayerCardValue1+intPlayerCardValue2+intPlayerCardValue3+intPlayerCardValue4+intPlayerCardValue5;
					}else if(intPlayerCardValue2==11){
						intPlayerCardValue2=1;
						intPlayerSum=intPlayerCardValue1+intPlayerCardValue2+intPlayerCardValue3+intPlayerCardValue4+intPlayerCardValue5;
					}else if(intPlayerCardValue3==11){
						intPlayerCardValue3=1;
						intPlayerSum=intPlayerCardValue1+intPlayerCardValue2+intPlayerCardValue3+intPlayerCardValue4+intPlayerCardValue5;
					}else if(intPlayerCardValue4==11){
						intPlayerCardValue4=1;
						intPlayerSum=intPlayerCardValue1+intPlayerCardValue2+intPlayerCardValue3+intPlayerCardValue4+intPlayerCardValue5;
					}else if(intPlayerCardValue5==11){
						intPlayerCardValue5=1;
						intPlayerSum=intPlayerCardValue1+intPlayerCardValue2+intPlayerCardValue3+intPlayerCardValue4+intPlayerCardValue5;
					}else{
						blnCondition=false;
						blnLoseCondition=true;
						System.out.println("lose");
					}
				}if(intPlayerSum==21){
					blnCondition=false;
					blnLoseCondition=false;
				}
				con.println(intPlayerSum);
				con.repaint();
			}
			
			if(blnLoseCondition==true){
				intUserBet=bustScreen(con,intUserBet);
				System.out.println(intUserBet);
				blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);
			}else if(intPlayerSum==21){
				intUserBet=blackjackScreen(con,intUserBet, blnWinOffDeal);
				blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);
			}else if(intCount==5){
				intUserBet=Fivecards(con,intUserBet);
				blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);
			}else if(blnLoseCondition==false){//dealer turn
				BufferedImage imgDealerCard2=cardPictures(con,intDealer[1][0],intDealer[1][1]);
				BufferedImage imgDealerCard3=null;
				BufferedImage imgDealerCard4=null;
				BufferedImage imgDealerCard5=null;
				int intDealerCardValue1=cardValues(intDealer[0][0]);
				int intDealerCardValue2=cardValues(intDealer[1][0]);
				intDealerSum=intDealerCardValue1+intDealerCardValue2;
				int intDealerCardValue3=0;
				int intDealerCardValue4=0;
				int intDealerCardValue5=0;
				con.drawImage(imgDealerCard2,590,210);
				con.drawImage(imgDealerCard1,610,220);
				
				while(intDealerSum<17){
					con.sleep(1000);
					intDealer[intDealerCounter][0]=intArray[intCount+intDealerCounter][0];
					intDealer[intDealerCounter][1]=intArray[intCount+intDealerCounter][1];
					if(intDealerCounter==2){
						imgDealerCard3=cardPictures(con,intDealer[intDealerCounter][0],intDealer[intDealerCounter][1]);	
						intDealerCardValue3=cardValues(intDealer[intDealerCounter][0]);
						intDealerSum=intDealerSum+intDealerCardValue3;
						con.drawImage(imgTable,0,0);

						if(intCount==2){
							con.drawImage(imgPlayerCard1,590,460); //540 and 660
							con.drawImage(imgPlayerCard2,610,470);
						}else if(intCount==3){
							con.drawImage(imgPlayerCard1,590,460); 
							con.drawImage(imgPlayerCard2,610,470);
							con.drawImage(imgPlayerCard3,630,460);
						}else if(intCount==4){
							con.drawImage(imgPlayerCard1,570,460); 
							con.drawImage(imgPlayerCard2,590,470);
							con.drawImage(imgPlayerCard3,610,460);
							con.drawImage(imgPlayerCard4,630,470);
						}else if(intCount==5){					
							con.drawImage(imgPlayerCard1,570,460); 
							con.drawImage(imgPlayerCard2,590,470);
							con.drawImage(imgPlayerCard3,610,460);
							con.drawImage(imgPlayerCard4,630,470);
							con.drawImage(imgPlayerCard5,650,460);
						}

						con.drawImage(imgDealerCard2,590,210);
						con.drawImage(imgDealerCard1,610,220);
						con.drawImage(imgDealerCard3,630,210);

						con.repaint();
					}else if(intDealerCounter==3){
						imgDealerCard4=cardPictures(con,intDealer[intDealerCounter][0],intDealer[intDealerCounter][1]);
						intDealerCardValue4=cardValues(intDealer[intDealerCounter][0]);
						intDealerSum=intDealerSum+intDealerCardValue4;
						con.drawImage(imgTable,0,0);

						if(intCount==2){
							con.drawImage(imgPlayerCard1,590,460); //540 and 660
							con.drawImage(imgPlayerCard2,610,470);
						}else if(intCount==3){
							con.drawImage(imgPlayerCard1,590,460); 
							con.drawImage(imgPlayerCard2,610,470);
							con.drawImage(imgPlayerCard3,630,460);
						}else if(intCount==4){
							con.drawImage(imgPlayerCard1,570,460); 
							con.drawImage(imgPlayerCard2,590,470);
							con.drawImage(imgPlayerCard3,610,460);
							con.drawImage(imgPlayerCard4,630,470);
						}else if(intCount==5){					
							con.drawImage(imgPlayerCard1,570,460); 
							con.drawImage(imgPlayerCard2,590,470);
							con.drawImage(imgPlayerCard3,610,460);
							con.drawImage(imgPlayerCard4,630,470);
							con.drawImage(imgPlayerCard5,650,460);
						}
						con.drawImage(imgDealerCard2,590,210);
						con.drawImage(imgDealerCard1,610,220);
						con.drawImage(imgDealerCard3,630,210);
						con.drawImage(imgDealerCard4,650,220);
						
						con.repaint();
					}else if(intDealerCounter==4){
						imgDealerCard5=cardPictures(con,intDealer[intDealerCounter][0],intDealer[intDealerCounter][1]);
						intDealerCardValue5=cardValues(intDealer[intDealerCounter][0]);
						intDealerSum=intDealerSum+intDealerCardValue5;
						con.drawImage(imgTable,0,0);

						if(intCount==2){
							con.drawImage(imgPlayerCard1,590,460); //540 and 660
							con.drawImage(imgPlayerCard2,610,470);
						}else if(intCount==3){
							con.drawImage(imgPlayerCard1,590,460); 
							con.drawImage(imgPlayerCard2,610,470);
							con.drawImage(imgPlayerCard3,630,460);
						}else if(intCount==4){
							con.drawImage(imgPlayerCard1,570,460); 
							con.drawImage(imgPlayerCard2,590,470);
							con.drawImage(imgPlayerCard3,610,460);
							con.drawImage(imgPlayerCard4,630,470);
						}else if(intCount==5){					
							con.drawImage(imgPlayerCard1,570,460); 
							con.drawImage(imgPlayerCard2,590,470);
							con.drawImage(imgPlayerCard3,610,460);
							con.drawImage(imgPlayerCard4,630,470);
							con.drawImage(imgPlayerCard5,650,460);
						}
						con.drawImage(imgDealerCard2,590,210);
						con.drawImage(imgDealerCard1,610,220);
						con.drawImage(imgDealerCard3,630,210);
						con.drawImage(imgDealerCard4,650,220);
						con.drawImage(imgDealerCard5,670,210);
						
						con.repaint();
						
					}
					intDealerCounter++;
					if(intDealerSum>21){
						if(intDealerCardValue1==11){
							intDealerCardValue1=1;
							intDealerSum=intDealerCardValue1+intDealerCardValue2+intDealerCardValue3+intDealerCardValue4+intDealerCardValue5;
						}else if(intDealerCardValue2==11){
							intDealerCardValue2=1;
							intDealerSum=intDealerCardValue1+intDealerCardValue2+intDealerCardValue3+intDealerCardValue4+intDealerCardValue5;
						}else if(intDealerCardValue3==11){
							intDealerCardValue3=1;
							intDealerSum=intDealerCardValue1+intDealerCardValue2+intDealerCardValue3+intDealerCardValue4+intDealerCardValue5;
						}else if(intDealerCardValue4==11){
							intDealerCardValue4=1;
							intDealerSum=intDealerCardValue1+intDealerCardValue2+intDealerCardValue3+intDealerCardValue4+intDealerCardValue5;
						}else if(intDealerCardValue5==11){
							intDealerCardValue5=1;
							intDealerSum=intDealerCardValue1+intDealerCardValue2+intDealerCardValue3+intDealerCardValue4+intDealerCardValue5;
						}else{
							blnDealerBust=true;
							break;
						}
					}	
				}
				System.out.println(intDealerSum);
				con.repaint();
				con.sleep(1000);
				if(blnDealerBust==true){	
					blnLoseCondition=false;		
					System.out.println("Win off db"+intDealerSum+" "+intPlayerSum);
					intUserBet=dealerBust(con,intUserBet);
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);			
				}else if(intPlayerSum==intDealerSum){
					System.out.println("Tie");
					intUserBet=tie(con,intUserBet);
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);
				}else if(intDealerSum==21){
					blnLoseCondition=true;
					System.out.println("dealer blackjack");
					intUserBet=dealerBlackjack(con, intUserBet);
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);
				}else if(intDealerSum>intPlayerSum){
					blnLoseCondition=true;
					System.out.println("lose"+intDealerSum+" "+intPlayerSum);
					intUserBet=dealerWins(con,intUserBet);
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);
				}else if(intPlayerSum>intDealerSum){
					blnLoseCondition=false;
					System.out.println("Win >"+intDealerSum+" "+intPlayerSum);
					intUserBet=userWins(con,intUserBet);
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);
				}
			}
			System.out.println(blnContinousGame);
			if(blnContinousGame==false){
				gameOver(con);
				con.closeConsole();
			}
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

	public static int bet(Console con){
		int intBet=-5;
		BufferedImage imgBetScreen=con.loadImage("../images/betscreen.png");
		con.drawImage(imgBetScreen,0,0);
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",40);
		con.setTextFont(fntBaron);

		while(intBet<0){
			con.print("\n\n\n\n\n\n\n\n\n                                          $");
			intBet=con.readInt();
		}
		con.clear();
		return intBet;
	}
	
	public static int bustScreen(Console con,int intBet){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgLose=con.loadImage("../images/bustScreen.png");
		con.drawImage(imgLose,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		intBet=intBet*-1;
		return intBet;
	}

	public static int blackjackScreen(Console con,int intBet,boolean blnWin){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgWin=con.loadImage("../images/blackjackScreen.png");
		con.drawImage(imgWin,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		if(blnWin==true){
			intBet=intBet*3;
		}else{
			intBet=intBet*2;
		}
		return intBet;
	}
	public static int Fivecards(Console con,int intBet){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage img5cards=con.loadImage("../images/5cardScreen.png");
		con.drawImage(img5cards,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		intBet=intBet*3;
		return intBet;
	}
	public static int dealerWins(Console con,int intBet){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgDealerWins=con.loadImage("../images/dealerBeatUser.png");
		con.drawImage(imgDealerWins,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		intBet=intBet*-1;
		return intBet;
	}
	public static int userWins(Console con,int intBet){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgUserWins=con.loadImage("../images/userBeatDealer.png");
		con.drawImage(imgUserWins,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		intBet=intBet*2;
		return intBet;
	}
	public static int tie(Console con,int intBet){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgUserWins=con.loadImage("../images/tieScreen.png");
		con.drawImage(imgUserWins,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		intBet=0;
		return intBet;
	}
	public static int dealerBlackjack(Console con,int intBet){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgDealerBlackjack=con.loadImage("../images/dealerBlackjack.png");
		con.drawImage(imgDealerBlackjack,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		intBet=intBet*-1;
		return intBet;
	}
public static int dealerBust(Console con,int intBet){
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgDealerBust=con.loadImage("../images/dealerBust.png");
		con.drawImage(imgDealerBust,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey();
		}
		intBet=intBet*2;
		return intBet;
	}
	public static boolean playAgainScreen(Console con,int intBet,boolean blnCondition,boolean blnContinousGame){
		int intKeypress=' ';
		BufferedImage imgBalanceScreen=con.loadImage("../images/balanceScreen.png");
		BufferedImage imgDown=con.loadImage("../images/arrowDown.png");
		BufferedImage imgUp=con.loadImage("../images/arrowUp.png");
		con.clear();
		con.drawImage(imgBalanceScreen,0,0);
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",55);
		con.setTextFont(fntBaron);
		
		con.println("\n\n\n\n\n                   old Balance: $"+intUserBalance);
		intUserBalance=intUserBalance+intBet;
		con.println("\n                   new Balance: $"+intUserBalance);
		if(intBet==0){
			System.out.println("tie");
		}else if(blnCondition==true){
			con.drawImage(imgDown,900,395);
		}else if(blnCondition==false){
			con.drawImage(imgUp,920,395);
		}
		con.repaint();
		while(intKeypress!=80||intKeypress!=81){
			intKeypress=con.getKey();
			System.out.println(intKeypress);
			if(intKeypress==81){
				blnContinousGame=false;
				break;
			}else if(intKeypress==80){
				blnContinousGame=true;
				break;
			}
		}
		return blnContinousGame;
	}
	public static void gameOver(Console con){
		char chrKeypress=' ';
		BufferedImage imgEnd=con.loadImage("../images/endScreen.png");
		con.drawImage(imgEnd,0,0);
		con.clear();
		con.println("\n\n\n\n\n                 ending balance: $"+intUserBalance);
		while(chrKeypress!='e'){
			chrKeypress=con.getChar();
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
	public static int cardValues(int intCard){
		int intCardValue=0;
		if(intCard>=10){
			intCardValue=10;
		}else if(intCard==1){
			intCardValue=11;
		}else{
			intCardValue=intCard;
		}
		return intCardValue;
	}
	
	public static BufferedImage cardPictures(Console con, int intValue,int intSuit){
		BufferedImage imgCard=null;
		if(intValue==1){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/1.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/2.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/3.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/4.png");
			}
		}else if(intValue==2){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/5.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/6.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/7.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/8.png");
			}
		}else if(intValue==3){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/9.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/10.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/11.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/12.png");
			}
		}else if(intValue==4){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/13.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/14.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/15.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/16.png");
			}
		}else if(intValue==5){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/17.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/18.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/19.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/20.png");
			}	
		}else if(intValue==6){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/21.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/22.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/23.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/24.png");
			}
		}else if(intValue==7){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/25.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/26.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/27.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/28.png");
			}
		}else if(intValue==8){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/29.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/30.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/31.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/32.png");
			}
		}else if(intValue==9){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/33.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/34.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/35.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/36.png");
			}
		}else if(intValue==10){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/37.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/tenofclubs.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/39.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/40.png");
			}
		}else if(intValue==11){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/41.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/42.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/43.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/44.png");
			}
		}else if(intValue==12){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/45.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/46.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/47.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/48.png");
			}
		}else if(intValue==13){
			if(intSuit==0){
				imgCard = con.loadImage("../cards/49.png");
			}else if(intSuit==1){
				imgCard = con.loadImage("../cards/50.png");
			}else if(intSuit==2){
				imgCard = con.loadImage("../cards/51.png");
			}else if(intSuit==3){						
				imgCard = con.loadImage("../cards/52.png");
			}	
		}
	return imgCard;
	}
}
