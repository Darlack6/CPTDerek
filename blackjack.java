//Program Name: Blackjack
//Programmer name: Derek Lien
//Version number: 1.00
import arc.*;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Color;

public class blackjack{
	static int intUserBalance=1000;
	public static void main(String[] args){
		Console con = new Console("blackjack",1280,720);
		boolean blnContinousGame=true;
		int intMenu=homescreen(con); //fetch for menu choice
		while(intMenu!=0){//until play game is selected, keep fetching
			if(intMenu==0){
				break;
			}else if(intMenu==1){ //highscores creen
				slidetransition2(con); //slide transition
				con.repaint();
				highscores(con);
				intMenu=homescreen(con);
			}else if(intMenu==2){ //exit 
				con.closeConsole();
			}
		}
		slidetransition1(con); //slide transition
		con.repaint();
		String strUsername=name(con); //get user name
		if(strUsername.equals("strongertogether")){ //Easter egg: if name is strongertogether, user gets 1500
			intUserBalance=1500;
		}
		while(blnContinousGame==true && intUserBalance>0){ //only runs if continousgame is true and if the user's balance is greater than 0
			con.clear();
			//initializes variables
			int intPlayerSum=0,intDealerSum=0,intdoubleDownCount=0,intDealerCounter=2,intCount=2;;
			boolean blnCondition=true,blnLoseCondition=false,blnWinOffDeal=false,blnDealerBust=false;

			int intUserBet=bet(con,intUserBalance); //gets user bet
			while(intUserBet>intUserBalance){//if user enters a bet thats greater than their balance, refetch bet
				BufferedImage imgInvalidBet=con.loadImage("../images/notEnoughMoney.png");
				con.drawImage(imgInvalidBet,0,0);
				con.repaint();
				char chrCurrentKey=con.getChar();
				while(chrCurrentKey!='c'){
					chrCurrentKey=con.getChar();
					con.repaint();
				}
				intUserBet=bet(con,intUserBalance);
			}
			//initializes arrays
			int[][] intArray = new int[52][3];
			int[][] intPlayer = new int[6][2];
			int[][] intDealer = new int[6][2];
			intArray=shuffle(intArray);//get the shuffled deck after bubble sort
			for(int intCounter=0;intCounter<2;intCounter++){//deal cards
				intPlayer[intCounter][0]=intArray[intCounter][0];
				intPlayer[intCounter][1]=intArray[intCounter][1];
				intDealer[intCounter][0]=intArray[intCounter+2][0];
				intDealer[intCounter][1]=intArray[intCounter+2][1];
			}
			for(int i=0;i<52;i++){ //testing: prints out cards to console
				System.out.println(intArray[i][0]+"-"+intArray[i][1]+"-"+intArray[i][2]);
			}
			//set card images using function cardPictures()
			BufferedImage imgPlayerCard1=cardPictures(con,intPlayer[0][0],intPlayer[0][1]); 
			BufferedImage imgPlayerCard2=cardPictures(con,intPlayer[1][0],intPlayer[1][1]);
			BufferedImage imgDealerCard1=cardPictures(con,intDealer[0][0],intDealer[0][1]);
			BufferedImage imgBackside=con.loadImage("../cards/backside.png");
			BufferedImage imgTable=con.loadImage("../images/tablescreen.png");
			//draw table and cards
			con.drawImage(imgTable,0,1);
			con.drawImage(imgPlayerCard1,590,460);
			con.drawImage(imgPlayerCard2,610,470);
			con.drawImage(imgBackside,590,210);
			con.drawImage(imgDealerCard1,610,220);
			con.repaint();
			//establish card values 
			int intPlayerCardValue1=cardValues(intPlayer[0][0]);
			int intPlayerCardValue2=cardValues(intPlayer[1][0]);
			int intPlayerCardValue3=0;
			int intPlayerCardValue4=0;
			int intPlayerCardValue5=0;
			int intDealerCardValue1=cardValues(intDealer[0][0]);
			//get total value of player's cards
			intPlayerSum=intPlayerCardValue1+intPlayerCardValue2;
			//initializes player card images, set to null incase they are not used
			BufferedImage imgPlayerCard3=null;
			BufferedImage imgPlayerCard4=null;
			BufferedImage imgPlayerCard5=null;
			//prints the sum of player's and dealer's cards
			con.clear();
			con.println("Your sum: "+intPlayerSum);
			con.println("Dealer's sum: "+intDealerCardValue1);
			//While loop that runs until broken by blackjack,busting, or standing
			while(blnCondition==true){
				if(intPlayerCardValue1+intPlayerCardValue2==21){ //blackjack
					blnWinOffDeal=true;
					blnLoseCondition=false;
					break; //inorder to prevent other code from running
				}else if(intPlayerCardValue1+intPlayerCardValue2==9||intPlayerCardValue1+intPlayerCardValue2==10||intPlayerCardValue1+intPlayerCardValue2==11){//double down
					if((intUserBalance/intUserBet)>=2){//user is only able to double down if their bet is less than half their balance
						intdoubleDownCount++;//prevents double down screen showing up after the first time
						if(intdoubleDownCount==1){
							con.sleep(1000);
							int intChoice=doubleDown(con);
							if(intChoice==89){//[y], yes double down
								//deals player their third card
								intPlayer[intCount][0]=intArray[intCount+2][0];
								intPlayer[intCount][1]=intArray[intCount+2][1];
								imgPlayerCard3=cardPictures(con,intPlayer[intCount][0],intPlayer[intCount][1]);	
								intPlayerCardValue3=cardValues(intPlayer[intCount][0]);
								//resets player's sum
								intPlayerSum=intPlayerSum+intPlayerCardValue3;
								con.sleep(1000);
								//redraw images with player's third card
								con.drawImage(imgTable,0,0);
								con.drawImage(imgPlayerCard1,590,460); 
								con.drawImage(imgPlayerCard2,610,470);
								con.drawImage(imgPlayerCard3,630,460);

								con.drawImage(imgBackside,590,210);
								con.drawImage(imgDealerCard1,610,220);
								con.repaint();
								intUserBet=intUserBet*2; //double down bet
								intCount++;
								con.clear();
								//reprints the sum of player's and dealer's cards
								con.println("Your sum: "+intPlayerSum);
								con.println("Dealer's sum: "+intDealerCardValue1);
								con.repaint();
								break;
							}else if(intChoice==78){//[n], no double down
								//removes prompt
								con.sleep(1000);
								con.drawImage(imgTable,0,1);
								con.drawImage(imgPlayerCard1,590,460); 
								con.drawImage(imgPlayerCard2,610,470);
								con.drawImage(imgBackside,590,210);
								con.drawImage(imgDealerCard1,610,220);
								con.repaint();
							}
						}
					}
				}
				//gets user choice
				int intKeypress = con.getKey();
				if(intKeypress==72){//[h], hit
					intPlayer[intCount][0]=intArray[intCount+2][0]; //deals next card to user
					intPlayer[intCount][1]=intArray[intCount+2][1];
					if(intCount==2){ //if only two cards has been dealt, deal the third card
						imgPlayerCard3=cardPictures(con,intPlayer[intCount][0],intPlayer[intCount][1]);	//set third card picture
						intPlayerCardValue3=cardValues(intPlayer[intCount][0]); //set third card value
						intPlayerSum=intPlayerSum+intPlayerCardValue3; //reload player sum with third card
						//redraw images
						con.drawImage(imgTable,0,0);
						con.drawImage(imgPlayerCard1,590,460); 
						con.drawImage(imgPlayerCard2,610,470);
						con.drawImage(imgPlayerCard3,630,460);
						con.drawImage(imgBackside,590,210);
						con.drawImage(imgDealerCard1,610,220);
						con.repaint();
					}else if(intCount==3){ //if three cards has been dealt, deal the fourth card
						imgPlayerCard4=cardPictures(con,intPlayer[intCount][0],intPlayer[intCount][1]); //set fourth card picture
						intPlayerCardValue4=cardValues(intPlayer[intCount][0]);//set fourth card value
						intPlayerSum=intPlayerSum+intPlayerCardValue4; //reload player sum with fourth card
						//redraw images
						con.drawImage(imgTable,0,0);
						con.drawImage(imgPlayerCard1,570,460); 
						con.drawImage(imgPlayerCard2,590,470);
						con.drawImage(imgPlayerCard3,610,460);
						con.drawImage(imgPlayerCard4,630,470);
						con.drawImage(imgBackside,590,210);
						con.drawImage(imgDealerCard1,610,220);
						con.repaint();
					}else if(intCount==4){ //if four cards has been dealt, deal the fifth card
						imgPlayerCard5=cardPictures(con,intPlayer[intCount][0],intPlayer[intCount][1]); //set fifth card picture
						intPlayerCardValue5=cardValues(intPlayer[intCount][0]);//set fifth card value
						intPlayerSum=intPlayerSum+intPlayerCardValue5; //reload player sum with fifth card
						//redraw images
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
					intCount++; //add another count to the amount of cards
				}else if(intKeypress==83){//[s], stand
					blnCondition=false; //break out of loop
					blnLoseCondition=false; //user has not last yet
					System.out.println(intPlayerCardValue1+" "+intPlayerCardValue2+" "+intPlayerCardValue3+" "+intPlayerCardValue4+" "+intPlayerCardValue5); //testing
				}

				if(intCount==5){ //if user has 5 cards
					blnCondition=false; //break out of loop
					blnLoseCondition=false; //user has not lost yet
				}

				if(intPlayerSum>21){ //if bust
					//if user has an ace, make the ace value = 1 when about to bust
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
					}else{//if after ace has been adjusted and still sum over 21, user has busted
						blnCondition=false; //break out of loop
						blnLoseCondition=true; //user has lost
						System.out.println("lose"); //testing
					}
				}if(intPlayerSum==21){ //if blacjack
					blnCondition=false; //break out of loop
					blnLoseCondition=false; //user has won
				}
				//reprints sums
				con.clear();
				con.println("Your sum: "+intPlayerSum);
				con.println("Dealer's sum: "+intDealerCardValue1);
				con.repaint();
			}
			if(blnLoseCondition==true){//if user has lost
				intUserBet=bustScreen(con,intUserBet); //draws bust screen and sets the bet to negative
				blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame); //ask the user if they want to play again
			}else if(intPlayerSum==21){//if blackjack
				intUserBet=blackjackScreen(con,intUserBet, blnWinOffDeal); //draws the blackjack screen, sets bet to 2x
				blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame); //ask the user if they want to play again
			}else if(intCount==5){ //if the user has 5 cards
				intUserBet=fiveCards(con,intUserBet); //draws the 5 cards screen, set bet to 3x
				blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame); //ask the user if they want to play again
			}else if(blnLoseCondition==false){//dealer turn
				BufferedImage imgDealerCard2=cardPictures(con,intDealer[1][0],intDealer[1][1]); //sets dealer second card
				//initializes dealer's card images, set to null incase they are not used
				BufferedImage imgDealerCard3=null;
				BufferedImage imgDealerCard4=null;
				BufferedImage imgDealerCard5=null;
				int intDealerCardValue2=cardValues(intDealer[1][0]); //sets dealer's second card value
				intDealerSum=intDealerCardValue1+intDealerCardValue2; //adds dealer's second card to dealer sum
				//initializes dealer's values
				int intDealerCardValue3=0;
				int intDealerCardValue4=0;
				int intDealerCardValue5=0;
				//draw dealer's second card and redraw images
				con.drawImage(imgTable,0,0);
				con.drawImage(imgDealerCard2,590,210);
				con.drawImage(imgDealerCard1,610,220);
				//in order to user draw cards at the right place, looks at how many cards the user has
				if(intCount==2){
					con.drawImage(imgPlayerCard1,590,460); 
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
				if(intDealerSum>21){ //if dealer busts from pocket aces
					System.out.println("hello");
					intDealerCardValue1=1; //set first ace to value 1
					intDealerSum=intDealerCardValue1+intDealerCardValue2;
				}
				con.repaint();
				//reprint user's and dealer's balances
				con.clear();
				con.println("Your sum: "+intPlayerSum);
				con.println("Dealer's sum: "+intDealerSum);
				//continously makes dealer hit until dealer's sum is greater than 17
				while(intDealerSum<17){
					con.sleep(1000);
					//sets dealer cards to the next card after the user has taken from the deck
					intDealer[intDealerCounter][0]=intArray[intCount+intDealerCounter][0];
					intDealer[intDealerCounter][1]=intArray[intCount+intDealerCounter][1];
					if(intDealerCounter==2){//if only two cards has been dealt, deal the third card
						imgDealerCard3=cardPictures(con,intDealer[intDealerCounter][0],intDealer[intDealerCounter][1]);	
						intDealerCardValue3=cardValues(intDealer[intDealerCounter][0]);
						intDealerSum=intDealerSum+intDealerCardValue3;
						con.drawImage(imgTable,0,0);
						if(intCount==2){
							con.drawImage(imgPlayerCard1,590,460); 
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
					}else if(intDealerCounter==3){//if only three cards has been dealt, deal the fourth card
						imgDealerCard4=cardPictures(con,intDealer[intDealerCounter][0],intDealer[intDealerCounter][1]);
						intDealerCardValue4=cardValues(intDealer[intDealerCounter][0]);
						intDealerSum=intDealerSum+intDealerCardValue4;
						con.drawImage(imgTable,0,0);
						if(intCount==2){
							con.drawImage(imgPlayerCard1,590,460); 
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
					}else if(intDealerCounter==4){//if four cards has been dealt, deal the fifth card
						imgDealerCard5=cardPictures(con,intDealer[intDealerCounter][0],intDealer[intDealerCounter][1]);
						intDealerCardValue5=cardValues(intDealer[intDealerCounter][0]);
						intDealerSum=intDealerSum+intDealerCardValue5;
						con.drawImage(imgTable,0,0);
						if(intCount==2){
							con.drawImage(imgPlayerCard1,590,460); 
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
					intDealerCounter++; //add another count to the amount of cards
					if(intDealerSum>21){ //if dealer busts
						//if dealer  has an ace, make the ace value = 1 when about to bust
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
						}else{//if dealer sum is greater than 21, set bust condition to true
							blnDealerBust=true;
							break; //inorder to prevent other code from running
						}
					}	
					//reprint dealer's and player's sum
					con.clear();
					con.println("Your sum: "+intPlayerSum);
					con.println("Dealer's sum: "+intDealerSum);
					con.repaint();
				}
				//reprint dealer's and player's sum
				con.clear();
				con.println("Your sum: "+intPlayerSum);
				con.println("Dealer's sum: "+intDealerSum);
				con.repaint();
				con.sleep(1000);
				if(blnDealerBust==true){//if dealer busts
					blnLoseCondition=false;	//user has won
					System.out.println("Win off db"+intDealerSum+" "+intPlayerSum); //testing
					intUserBet=dealerBust(con,intUserBet); //draws the dealer bust screen, sets bet to 2x
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);	//ask the user to play again		
				}else if(intPlayerSum==intDealerSum){ //if sum is tied
					System.out.println("Tie"); //testing
					intUserBet=tie(con,intUserBet); //draws the tie screen, sets bet to 0
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame); //ask the user to play again
				}else if(intDealerSum==21){ //if dealer hits blackjack
					blnLoseCondition=true; //user has lost
					System.out.println("dealer blackjack");//testing
					intUserBet=dealerBlackjack(con, intUserBet);//draws the dealer blackjack screen, set bet to negative
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame); //ask the user to play again
				}else if(intDealerSum>intPlayerSum){
					blnLoseCondition=true; //user has lost
					System.out.println("lose"+intDealerSum+" "+intPlayerSum); //testing
					intUserBet=dealerWins(con,intUserBet); //draws the dealer win screen, set bet to negative
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame);//ask the user to play again
				}else if(intPlayerSum>intDealerSum){
					blnLoseCondition=false; //user has won
					System.out.println("Win >"+intDealerSum+" "+intPlayerSum); //testing
					intUserBet=userWins(con,intUserBet); //draws the user win screen, set bet to 2x
					blnContinousGame=playAgainScreen(con,intUserBet,blnLoseCondition,blnContinousGame); //ask the user to play again
				}
			}
			System.out.println(blnContinousGame);//testing
			if(intUserBalance==0){ //if the user balance is 0
				outOfMoney(con);//draws the out of money overlay
				sortHighscore(con,intUserBalance,strUsername); //sort users score
				con.closeConsole(); //exits game
			}
			if(blnContinousGame==false){ //if user exits game
				gameOver(con); //draws the game over screen
				sortHighscore(con,intUserBalance,strUsername); //sorts users score
				con.closeConsole(); //exits game
			}
		}
	}//homescreen function
	public static int homescreen(Console con){
		//initializes variables
		int inty=50;
		int intMenuChoice=0;
		BufferedImage imgMMBackground = con.loadImage("../images/mainmenu.png");
		BufferedImage imgSpades = con.loadImage("../images/pointer.png");
		//draw images to screen
		con.drawImage(imgMMBackground,0,0);
		con.drawImage(imgSpades,-610,inty);
		con.repaint();
		while(true){//constantly fetches for user's key
			int intKeypress = con.getKey();
			if(intKeypress==40){//[arrow key down], moves spades down
				if(inty==50){
					intMenuChoice=1;
					inty=inty+110;
				}else if(inty==160){
					intMenuChoice=2;
					inty=inty+75;
				}
				con.drawImage(imgMMBackground,0,0);
				con.drawImage(imgSpades,-610,inty);
			}else if(intKeypress==38){//[arrow key up], move spades up
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
				break; //[enter] breaks out of loop
			}
			con.repaint();
			con.sleep(33);
		}
		return intMenuChoice;
	}//highscores function
	public static void highscores(Console con){
		//initializes variables
		boolean blnCondition=true;
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",30);
		con.setDrawFont(fntBaron);
		TextInputFile txtHighscores = new TextInputFile("highscores.txt");
		int intCounter=1;
		int inty=150;
		//prints the top 7 scores to the screen
		for(int i=0;i<7;i++){
			String stringHighscore=txtHighscores.readLine(); //read highscore name
			int intHighscore=txtHighscores.readInt(); //read highscore score
			//makes the top name gold
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
		while(blnCondition==true){ //constantly fetches for key until [esc] is pressed
			int intKeypress = con.getKey();
			//System.out.println(intKeypress);
			if(intKeypress==27){
				txtHighscores.close();
				blnCondition=false;
			}
			con.sleep(33);
			con.repaint();
		}
	}
	public static String name(Console con){//gets user's name
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",40);
		con.setTextFont(fntBaron);	
		con.print("\n\n\n\n\n\n\n\n\n                                         ");
		String strUsername=con.readLine();
		return strUsername;
	}
	public static int bet(Console con,int intbalance){//gets user's bet
		//initializes variables
		con.clear();
		int intBet=-5; 
		BufferedImage imgBetScreen=con.loadImage("../images/betscreen.png");
		con.drawImage(imgBetScreen,0,0);
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",31);
		con.setTextFont(fntBaron);
		con.println("\n  your balance: "+intbalance);
		fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",40);
		con.setTextFont(fntBaron);
		//gets user's bet until it's greater than 0
		while(intBet<0){
			con.print("\n\n\n\n\n\n\n\n                                          $");
			intBet=con.readInt();
		}
		con.clear();
		return intBet;
	}
	public static int doubleDown(Console con){ //double down overlay
		//initializes variables
		BufferedImage imgDoubleDown=con.loadImage("../images/doubleDownScreen.png");
		con.drawImage(imgDoubleDown,0,0);
		con.repaint();
		int intKeypress=0;
		while(true){//runs until user choses
			intKeypress=con.getKey();
			if(intKeypress==89){ //[y] chooses to double down
				break;
			}else if(intKeypress==78){ //[n] chooses not to dobule down
				break;
			}
		}
		return intKeypress;
	}
	public static int bustScreen(Console con,int intBet){//bust screen overlay
		//initializes variables
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgLose=con.loadImage("../images/bustScreen.png");
		con.drawImage(imgLose,0,0);
		con.repaint();
		while(intKeypress!=67){
			intKeypress=con.getKey(); //[c] to continue
		}
		intBet=intBet*-1; //sets bet to negative
		return intBet;
	}
	public static int blackjackScreen(Console con,int intBet,boolean blnWin){ //blackjack overlay
		//initializes variables
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgWin=con.loadImage("../images/blackjackScreen.png");
		con.drawImage(imgWin,0,0);
		con.repaint();
		while(intKeypress!=67){ //[c] to continue
			intKeypress=con.getKey();
		}
		if(blnWin==true){ //if user got blackjack on first two cards
			intBet=intBet*3; //set bet to 3x
		}else{
			intBet=intBet*2; //set to 2x
		}
		return intBet;
	}
	public static int fiveCards(Console con,int intBet){ //if user won because 5 cards
		//initializes variables
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage img5cards=con.loadImage("../images/5cardScreen.png");
		con.drawImage(img5cards,0,0);
		con.repaint();
		while(intKeypress!=67){ //[c] to continue
			intKeypress=con.getKey();
		}
		intBet=intBet*3; //set bet to 3x
		return intBet;
	}
	public static int dealerWins(Console con,int intBet){ //dealer win overlay
		//initializes variables
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgDealerWins=con.loadImage("../images/dealerBeatUser.png");
		con.drawImage(imgDealerWins,0,0);
		con.repaint();
		while(intKeypress!=67){ //[c] to continue
			intKeypress=con.getKey();
		}
		intBet=intBet*-1; //set bet to negative
		return intBet;
	}
	public static int userWins(Console con,int intBet){ //user wins overlay
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgUserWins=con.loadImage("../images/userBeatDealer.png");
		con.drawImage(imgUserWins,0,0);
		con.repaint();
		while(intKeypress!=67){ //[c] to continue
			intKeypress=con.getKey();
		}
		intBet=intBet*2; //set bet to 2x
		return intBet;
	}
	public static int tie(Console con,int intBet){ //tie overlay
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgUserWins=con.loadImage("../images/tieScreen.png");
		con.drawImage(imgUserWins,0,0);
		con.repaint();
		while(intKeypress!=67){ //[c] to continue
			intKeypress=con.getKey();
		}
		intBet=0; //set bet to 0
		return intBet;
	}
	public static int dealerBlackjack(Console con,int intBet){// dealer blackjack screen
		//initalizes variables
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgDealerBlackjack=con.loadImage("../images/dealerBlackjack.png");
		con.drawImage(imgDealerBlackjack,0,0);
		con.repaint();
		while(intKeypress!=67){ //[c] to continue
			intKeypress=con.getKey();
		}
		intBet=intBet*-1; //set bet to negative
		return intBet;
	}
	public static int dealerBust(Console con,int intBet){//dealer busts screen
		//intializes variables
		int intKeypress=0;
		con.sleep(1000);
		BufferedImage imgDealerBust=con.loadImage("../images/dealerBust.png");
		con.drawImage(imgDealerBust,0,0);
		con.repaint();
		while(intKeypress!=67){ //[c] to continue
			intKeypress=con.getKey();
		}
		intBet=intBet*2; //set bet to 2x
		return intBet;
	}
	public static boolean playAgainScreen(Console con,int intBet,boolean blnCondition,boolean blnContinousGame){ //play again screen
		//initializes variables
		int intKeypress=' ';
		BufferedImage imgBalanceScreen=con.loadImage("../images/balanceScreen.png");
		BufferedImage imgDown=con.loadImage("../images/arrowDown.png");
		BufferedImage imgUp=con.loadImage("../images/arrowUp.png");
		con.clear();
		con.drawImage(imgBalanceScreen,0,0);
		Font fntBaron=con.loadFont("../fonts/BaronNeue-Regular.ttf",55);
		con.setTextFont(fntBaron);
		con.println("\n\n\n\n\n                   old Balance: $"+intUserBalance); //prints user's old balance
		intUserBalance=intUserBalance+intBet;
		con.println("\n                   new Balance: $"+intUserBalance);//prints user's new balance
		if(intBet==0){ //if tie
			System.out.println("tie");
		}else if(blnCondition==true){ //if user lost
			con.drawImage(imgDown,920,395); //draw arrow down
		}else if(blnCondition==false){ //if user won
			con.drawImage(imgUp,920,395); //draw arrow up
		}
		con.repaint();
		while(intKeypress!=80||intKeypress!=81){ //[p] or [q]
			intKeypress=con.getKey();
			System.out.println(intKeypress);
			if(intKeypress==81){ //[q]
				blnContinousGame=false; //exits game
				break;
			}else if(intKeypress==80){ //[p]
				blnContinousGame=true; //continues game
				break;
			}
		}
		return blnContinousGame;
	}
	public static void gameOver(Console con){//game over screen
		//initializes variables
		char chrKeypress=' ';
		BufferedImage imgEnd=con.loadImage("../images/endScreen.png");
		con.drawImage(imgEnd,0,0);
		con.clear();
		con.println("\n\n\n\n\n                  ending balance: $"+intUserBalance); //print ending balance
		while(chrKeypress!='e'){
			chrKeypress=con.getChar();
		}
	}
	public static void outOfMoney(Console con){ //out of money screen 
		//initializes variables
		char chrKeypress=' ';
		BufferedImage imgOutofMoney=con.loadImage("../images/outOfMoneyScreen.png");
		con.drawImage(imgOutofMoney,0,0);
		con.clear();
		while(chrKeypress!='q'){
			chrKeypress=con.getChar();
		}
	}
	public static void sortHighscore(Console con,int intScore,String strName){ //sort highscores
		//initializes variables
		TextInputFile txtHighscores = new TextInputFile("highscores.txt");
		int intCounter=0;
		int intCount=0;
		//gets total lines in file
		while(txtHighscores.eof()==false){
			String strHighscores=txtHighscores.readLine();
			int intHighscores=txtHighscores.readInt();
			intCount++;
		}
		txtHighscores.close();
		txtHighscores = new TextInputFile("highscores.txt");
		//initialize array
		String [] strArray= new String[intCount+1];
		int [] intArray = new int[intCount+1];
		//add name and score to arrays
		while(txtHighscores.eof()==false){
			String strHighscore=txtHighscores.readLine();
			int intHighscore=txtHighscores.readInt();
			strArray[intCounter]=strHighscore;
			intArray[intCounter]=intHighscore;
			intCounter++;
		}
		txtHighscores.close();
		System.out.println("before sort");
		//testing
		for(int i=0;i<7;i++){
			System.out.println(strArray[i]);
			System.out.println(intArray[i]);
		}
		//adds the user's name and score
		intArray[intCount]=intScore;
		strArray[intCount]=strName;
		
		for(int intCounter2=0;intCounter2<intCount+1;intCounter2++){ //bubble sort
			for(int intCounter3=0;intCounter3<intCount-intCounter2;intCounter3++){
				int intTemp;
				String strTemp;
				int intCurrent=intArray[intCounter3];
				int intNext=intArray[intCounter3+1];

				if(intNext>intCurrent){
					intTemp=intArray[intCounter3+1];
					intArray[intCounter3+1]=intArray[intCounter3];
					intArray[intCounter3]=intTemp;

					strTemp=strArray[intCounter3+1];
					strArray[intCounter3+1]=strArray[intCounter3];
					strArray[intCounter3]=strTemp;
				}
			}
		}
		//testing
		System.out.println("After sort"); 
		for(int i=0;i<intCount+1;i++){
			System.out.println(strArray[i]);
			System.out.println(intArray[i]);
		}
		//writes user's name and score to text file sorted
		TextOutputFile txtScores = new TextOutputFile("highscores.txt");
		for(int i=0;i<intCount+1;i++){
			txtScores.println(strArray[i]);
			txtScores.println(intArray[i]);
		}
		txtScores.close();
	}//shuffle array
	public static int[][] shuffle(int intCards[][]){
		//assigns the first column, value, using a for loop that goes from 1-52, assigns the second column, suit, and assigns third column with random num
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
		for(int intCounter2=0;intCounter2<51;intCounter2++){ //bubble sort
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
	}//sets the number value of the card
	public static int cardValues(int intCard){
		int intCardValue=0;
		if(intCard>=10){ //if face card or 10
			intCardValue=10; //value is 10
		}else if(intCard==1){ //if ace
			intCardValue=11; //value is 11
		}else{
			intCardValue=intCard; //if not face value or ace, return normal value
		}
		return intCardValue;
	}
	//set card image
	public static BufferedImage cardPictures(Console con, int intValue,int intSuit){
		BufferedImage imgCard=null;
		if(intValue==1){ //if 1
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/1.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/2.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/3.png");
			}else if(intSuit==3){ //if spades			
				imgCard = con.loadImage("../cards/4.png");
			}
		}else if(intValue==2){ //if 2
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/5.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/6.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/7.png");
			}else if(intSuit==3){ //if spades					
				imgCard = con.loadImage("../cards/8.png");
			}
		}else if(intValue==3){ //if 3
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/9.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/10.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/11.png");
			}else if(intSuit==3){ //if spades					
				imgCard = con.loadImage("../cards/12.png");
			}
		}else if(intValue==4){ //if 4
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/13.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/14.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/15.png");
			}else if(intSuit==3){ //if spades 					
				imgCard = con.loadImage("../cards/16.png");
			}
		}else if(intValue==5){ //if 5
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/17.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/18.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/19.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/20.png");
			}	
		}else if(intValue==6){ //if 6
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/21.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/22.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/23.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/24.png");
			}
		}else if(intValue==7){ //if 7
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/25.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/26.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/27.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/28.png");
			}
		}else if(intValue==8){ //if 8
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/29.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/30.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/31.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/32.png");
			}
		}else if(intValue==9){ //if 9
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/33.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/34.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/35.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/36.png");
			}
		}else if(intValue==10){ //if 10
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/37.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/tenofclubs.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/39.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/40.png");
			}
		}else if(intValue==11){ //if 11
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/41.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/42.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/43.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/44.png");
			}
		}else if(intValue==12){ //if 12
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/45.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/46.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/47.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/48.png");
			}
		}else if(intValue==13){ //if 13
			if(intSuit==0){ //if diamonds
				imgCard = con.loadImage("../cards/49.png");
			}else if(intSuit==1){ //if clubs
				imgCard = con.loadImage("../cards/50.png");
			}else if(intSuit==2){ //if hearts
				imgCard = con.loadImage("../cards/51.png");
			}else if(intSuit==3){ //if spades						
				imgCard = con.loadImage("../cards/52.png");
			}	
		}
	return imgCard;
	}
	public static void slidetransition1(Console con){ //slide transition for name screen
		//initializes variables
		int intcounter;
		BufferedImage imgNameScreen = con.loadImage("../images/nameScreen.png");
		con.clear();

		con.setDrawColor(new Color(0,0,0)); //custom RGB color
		con.clear();
		//draws a fill rectangle and moves its x position postively, creating a black rectangle to slide across the screen 
		for(intcounter=0;intcounter<1285;intcounter=intcounter+4){
			con.fillRect(0,0,intcounter,720);
			con.repaint();
			con.sleep(1);
		}for(intcounter=0;intcounter<1285;intcounter=intcounter+6){
			con.drawImage(imgNameScreen,0,0);
			con.fillRect(intcounter,0,1280,720);
			System.out.println(intcounter);
			con.repaint();
			con.sleep(1);
		}
	}
	public static void slidetransition2(Console con){ //slide transition for highscores
		//initializes variables
		int intcounter;
		BufferedImage imgHighscoreScreen = con.loadImage("../images/highscore.png");
		con.clear();

		con.setDrawColor(new Color(0,0,0)); //custom RGB color
		con.clear();
		//draws a fill rectangle and moves its x position postively, creating a black rectangle to slide across the screen 
		for(intcounter=0;intcounter<1285;intcounter=intcounter+4){
			con.fillRect(0,0,intcounter,720);
			con.repaint();
			con.sleep(1);
		}for(intcounter=0;intcounter<1285;intcounter=intcounter+6){
			con.drawImage(imgHighscoreScreen,0,0);
			con.fillRect(intcounter,0,1280,720);
			System.out.println(intcounter);
			con.repaint();
			con.sleep(1);
		}
	}
}