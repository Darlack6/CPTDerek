import arc.*;

public class blackjack{
	public static void main(String[] args){
		Console con = new Console();
		int[][] intArray = new int[52][3];
		intArray=shuffle(con,intArray);
		for(int i=0;i<52;i++){
			System.out.println(intArray[i][0]+"-"+intArray[i][1]+"-"+intArray[i][2]);
		}
	}
	public static int[][] shuffle(Console con,int intCards[][]){
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
		for(int intCounter2=0;intCounter2<52;intCounter2++){
			for(int intCounter=0;intCounter<52-intCounter2;intCounter++){
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
}
