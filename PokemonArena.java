/**
 * @(#)PokemonArena.java
 *
 *
 * @Anas Saqib 
 * @version 1.00
 */
 
import java.util.*;
import java.io.*;
public class PokemonArena {
	private static ArrayList<Pokemon> pokeal = new ArrayList<Pokemon>(); //arraylist of all the pokemon from the pokemon text file (Pokemon objects)
	private static ArrayList<Pokemon> userpoke = new ArrayList<Pokemon>(); //arraylist of 4 user pokemon (Pokemon objects)
	private static int[] userpoken = {50,50,50,50}; //an array to keep track of the 4 user pokemons' energy
	private static int opponenten=50; //a variable for the opponent's (computer) energy
	private static int oldenergy; //used in the recharge method
	private static int numofopp=23; //used to keep track of the number of opponents that are left
	private static Pokemon userpok; //the user pokemon
	private static Pokemon opponent; //the opponent
	private static Scanner keyboard;
	private static int input; //used to get the numerical input from the user when performing actions
	private static int a=1; //used for a while loop when the user is picking his/her pokemon
	private static int w=0; //used to  
	private static int x; //used to get random numbers
	private static String stunstatus="normal"; //keeps track of the stun status of the pokemon whose turn it is
	private static String opponentstatus="alive"; //keeps track of whether the opponent is dead or alive
	private static String[] userstatus={"alive","alive","alive","alive"}; //keeps track of all the user pokemon and whether they are dead or alive
	private static String[] udstatus = {"normal","normal","normal","normal"}; //used to keep track of the disable status of all the userpokemon
	private static String cdstatus="normal"; //keeps track of the computer pokemon disable status
	private static String turn=" "; //keeps track of whose turn it is
    public static void main(String[]args)throws IOException{ //main loop, calls all the methods
    	keyboard= new Scanner(System.in);
    	loadPokemon();
    	choosePokemon();
    	battle();
    	if (numofopp==0){ //if the user beats the game
    		System.out.println("\nCongratulations, You are Trainer Supreme!");	
    	}
    	else{
    		System.out.println("All your pokemon have fainted, so you can no longer battle. You managed to defeat "+(23-numofopp+1)+ " out of "+ "23 opponents");
    	}
    }
    //loads all the pokemon and adds each line to an arraylist as a Pokemon object
    public static void loadPokemon()throws IOException{
    	Scanner Pokemons = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
    	String blankline = Pokemons.nextLine();
    	while(Pokemons.hasNextLine()){
			pokeal.add(new Pokemon(Pokemons.nextLine()));			
    	}	
    }
    //method for choosing pokemon
    public static void choosePokemon(){
    	System.out.printf("%-4s%-12s%-6s%-12s%-17s%-11s\n","#","NAME","HP","TYPE","RESISTANCE","WEAKNESS");
    	for (int i=0;i<pokeal.size();i++){ 
    		System.out.printf("%-4s%-12s%-6s%-12s%-17s%-11s\n",i+1,pokeal.get(i).getName(),pokeal.get(i).getHp(),pokeal.get(i).getType(),pokeal.get(i).getResis(),pokeal.get(i).getWeak());
    	}
    	System.out.println("\n");
    	
    	while (a<5){
    		System.out.println("Choose a pokemon by entering the corresponding number (1 - 27)-----You have chosen "+Integer.toString(a-1)+" out of "+4+" pokemon");
    		input=keyboard.nextInt();
    		if (input<28&&0<input&&userpoke.contains(pokeal.get(input-1))==false){
    			userpoke.add(pokeal.get(input-1));
    			System.out.println("You chose " + pokeal.get(input-1).getName());	
    			a+=1;
    		}
    		else{
    			if (input>27||input<=0){
    				System.out.println("Invalid number!");
    			}
    			else{
    				System.out.println("You have already chosen this pokemon!");
    			}
    		}
    	}
    	
    	System.out.println("\n"+"Your pokemon are:"+userpoke.get(0).getName()+", "+userpoke.get(1).getName()+", "+userpoke.get(2).getName()+", "+userpoke.get(3).getName());
    		
    		
    }
    
    //method that the battle occurs in
    public static void battle(){
    	pokeal.remove(userpoke.get(0)); //removes the userpokemon from the main list so that they arent picked as they opponent
    	pokeal.remove(userpoke.get(1));
    	pokeal.remove(userpoke.get(2));
    	pokeal.remove(userpoke.get(3));
    	while (numofopp>0&&userpokstatus(userstatus).equals("dead")==false){ //the main while loop that keeps the battle running untill there are no opponents left or all th user pokemon are dead
	    	Random number = new Random();
			x=(number.nextInt(numofopp)+1); //gets a random number from 1 - number of oppnents left (inclusivee)
			opponent=pokeal.get(x-1); //gets the opponent pokemon from the main list
			pokeal.remove(x-1); //removes the choses opponent from the main list so it doesnt get chosen as the opponent again
			numofopp-=1;
			System.out.println("\n"+"Prepare to battle!");
			delay(1500);
			System.out.println("\n" +"Your opponent is " + opponent.getName()+" hp-"+opponent.getHp()); //displays the opponent
	    	battlechoosepoke();
		    x=(number.nextInt(2)+1); //gets a random number to decide who goes first (1 or 2)
		   	if (x==1){ //if the random number is 1 then the user goes first
		   		turn="user";
		   		System.out.println("\n"+userpok.getName()+" goes first");
		   		delay(1500);
		   	}
		   	else{ //if the random number is 2 then the computer goes first
		   		System.out.println("\n"+opponent.getName()+" goes first");
		   		delay(1500);	
		   	}
		    while (opponentstatus.equals("dead")==false&&userpokstatus(userstatus).equals("dead")==false){ //loop for each round of battle. Ends when the opponent dies or if all the user pokemon are dead. 
		    	PokeAttacks oppopokpa=new PokeAttacks(opponent); //makes a pokeattacks object for the opponent and user pokemon that are fighting
			    PokeAttacks userpokpa=new PokeAttacks(userpok);
		    	if (userstatus[userpoke.indexOf(userpok)].equals("dead")){ //if the user's fighting pokemon dies then they choose another pokemon
		    		battlechoosepoke();
		    		opponenten=50;
		    		turn="computer";	
		    	}
		    	if (turn.equals("user")==true){ //if it is the users turn
		    		if (stunstatus.equals("stunned")==true){ //checks if the user's pokemon is stunned
		    			System.out.println("\n"+userpok.getName()+" is stunned and cannot perform an action!"); 
		    			stunstatus="normal";
		    			turn="computer"; //ends the user's turn
		    			userpoken[userpoke.indexOf(userpok)]+=10; //user's pokemon gains 10 energy because its the end of the turn
						if (userpoken[userpoke.indexOf(userpok)]>50){
							userpoken[userpoke.indexOf(userpok)]=50;
						}
						delay(2500);		
		    		}
		    		else{ //if the user's pokemon is not stunned
			    		while (true){ //loop for the users turn. Breaks when the user chooses an action
			    			oppopokpa=new PokeAttacks(opponent); //creats a pokeattacks object for the user's and opponent's pkemon
				    		userpokpa=new PokeAttacks(userpok);
				    		turn="user";
					    	System.out.println("\nChoose an action (enter the corresponding number)");
					    	actions(); //displays the actions (attack, retreat and pass)
					    	input=keyboard.nextInt();
					    	if (input ==1){ //if the chosen action is attack, prints out the attacks of the pokemon that the user is using
						    	if (userpokpa.numoAtt()==1){
							    	System.out.println("\n1: "+userpokpa.attack1n()+" - " + userpokpa.attack1s()+" (damage "+userpokpa.attack1d()+")"+" (energy required "+userpokpa.attack1c()+")");
							    }
							    else{
							    	System.out.println("\n1: "+userpokpa.attack1n()+" - " + userpokpa.attack1s()+" (damage "+userpokpa.attack1d()+")" +" (energy required "+userpokpa.attack1c()+")");
							    	System.out.println("2: "+userpokpa.attack2n()+" - " + userpokpa.attack2s()+" (damage "+userpokpa.attack2d()+")"+" (energy required "+userpokpa.attack2c()+")");
							    }
							    System.out.println("Your pokemon has "+userpoken[userpoke.indexOf(userpok)]+" energy and "+userpok.getHp()+" health"); //displays the users health and energy
						    	input=keyboard.nextInt();
						    	if (input==1){ //if the first attack is chosen
						    		if (userpoken[userpoke.indexOf(userpok)]>=userpokpa.attack1c()){ //checks if the user hase enough energy to perform the chosen attack
						    			attack(userpokpa,oppopokpa,userpok,opponent,userpoken[userpoke.indexOf(userpok)],input);
							    		userpoken[userpoke.indexOf(userpok)]+=10;
								    	if (userpoken[userpoke.indexOf(userpok)]>50){
								    		userpoken[userpoke.indexOf(userpok)]=50;
								    	}				    			
						    			turn="computer";
					    				delay(2500);
						    			break;
						    		}
						    		else{
						    			System.out.println("\n"+userpok.getName()+" does not have enough energy to use "+userpokpa.attack1n());
						    		}	
						    	}
						    	else{ //if the second attack is chosen
						    		if (userpoken[userpoke.indexOf(userpok)]>=userpokpa.attack2c()){ //checks if the user hase enough energy to perform the chosen attack
						    			attack(userpokpa,oppopokpa,userpok,opponent,userpoken[userpoke.indexOf(userpok)],input);
							    		userpoken[userpoke.indexOf(userpok)]+=10;
								    	if (userpoken[userpoke.indexOf(userpok)]>50){
								    		userpoken[userpoke.indexOf(userpok)]=50;
								    	}
						    			turn="computer";
					    				delay(2500);
						    			break;
						    		}
						    		else{
						    			System.out.println("\n"+userpok.getName()+" does not have enough energy to use "+userpokpa.attack2n());
						    		}
						    	}
							    	
					    	}
					    	else if (input==2){ //if the user chooses to retreat
					    		System.out.println(userpok.getName()+" retreats");
					    		battlechoosepoke(); //gets the user to pick the pokemon that he/she wishes to battle with
					    		userpoken[userpoke.indexOf(userpok)]+=10;
						    	if (userpoken[userpoke.indexOf(userpok)]>50){
						    		userpoken[userpoke.indexOf(userpok)]=50;
						    	}
					    		turn="computer";
					    		delay(2500);
						    	break;
					    	}
					    	else{ //if the user chooses to pass
					    		System.out.println("\n"+userpok.getName()+ " passes");
					    		userpoken[userpoke.indexOf(userpok)]+=10;
						    	if (userpoken[userpoke.indexOf(userpok)]>50){
						    		userpoken[userpoke.indexOf(userpok)]=50;
						    	}
					    		turn="computer"; //ends the user's turn
					    		delay(1000);
					    		break;	
					    	}
					    	
				    	} 
		    		}	
		    	}		
		    	else{ //if it is the computer's turn
		    		if (stunstatus.equals("stunned")==true){ //checks if the computer is stunned
		    			System.out.println("\n"+opponent.getName()+" is stunned and cannot perform an action!"); 
		    			stunstatus="normal";
		    			turn="user"; //ends the computer's turn
		    			opponenten+=10;
				    	if (opponenten>50){
				    		opponenten=50;
				    	}
				    	delay(2500);		
		    		}
		    		else{ //if the computer is not stunned
			    		turn="computer";
			    		if (oppopokpa.numoAtt()==1){ //if the computer pokemon has one attack
			    			if (opponenten>=oppopokpa.attack1c()){ //if the computer has enough energy to perform the attack
			    				attack(oppopokpa,userpokpa,opponent,userpok,opponenten,1);	
			    			}
			    			else{ 
			    				System.out.println("\n"+opponent.getName()+" passes");
			    			}
			    		}
			    		else{ //if the computer has two attacks
			    			x=(number.nextInt(2)+1); //gets a random number (1 or 2)
			    			if (x==1){ //if the random number is 1 then the first attack gets priority
			    				if(opponenten>=oppopokpa.attack1c()){ //if the computer has enough energy for its first attack, it uses it
			    					attack(oppopokpa,userpokpa,opponent,userpok,opponenten,1);	
			    				}
			    				else if (opponenten>=oppopokpa.attack2c()){ //else it uses its second attack
			    					attack(oppopokpa,userpokpa,opponent,userpok,opponenten,2);		
			    				}
			    				else{ //if it does not have enough energy for either attack then it passes
			    					System.out.println("\n"+opponent.getName()+" passes");	
			    				}
			    			}
			    			else { //if the random number is 2 then the second attack gets priority
			    				if (opponenten>=oppopokpa.attack2c()){
			    					attack(oppopokpa,userpokpa,opponent,userpok,opponenten,2);
			    				}
			    				else if (opponenten>=oppopokpa.attack1c()){ 
			    					attack(oppopokpa,userpokpa,opponent,userpok,opponenten,1);		
			    				}
			    				else{
			    					System.out.println("\n"+opponent.getName()+" passes");	
			    				}	
			    			}		
			    		}
				    	opponenten+=10; //opponents energy goes up by ten
				    	if (opponenten>50){
				    		opponenten=50;
				    	}	    		
			    		turn="user"; //ends computer's turn
			    		delay(2500);
		    	
		    		}
		    	}
		    }
		    //after each battle
		    for (int r=0;r<4;r++){ //all the user's pokemon get 50 energy
		    	userpoken[r]=50;
		    }
			opponenten=50; //the opponent gets 50 energy
			for (int p=0;p<4;p++){ //heals all the user's pokemon
				userpoke.get(p).heal();				
			}
		    stunstatus="normal";
			opponentstatus="alive";
			//resets all the disable status
			udstatus[0]="normal";
			udstatus[1]="normal";
			udstatus[2]="normal";
			udstatus[3]="normal";
			cdstatus="normal";
			turn=" ";
			System.out.println("\nYou have defeated "+(23-numofopp)+ " out of "+ "23 opponents"); //displays how many pokemon the user has defeated
			delay(1000);
    	}	   	 
    }
    public static void attack(PokeAttacks pokeattack,PokeAttacks pokeattackopp,Pokemon attpok,Pokemon opponent,int atten,int attacknum){ //takes in the Pokeattacks and Pokemon object of the attacking pokemon and the defending pokemon, the attacking pokemon's energy and the number of the attack that is being used
	    	if (attacknum==1){ //if the first attack of the attacking pokemon is being used
	    		if (turn.equals("user")==true){ //if it is the user's turn then the user's pokemon's energy is subtracted
	    			userpoken[userpoke.indexOf(userpok)]-=pokeattack.attack1c();
	    		}
	    		else{ //else, the opponent's energy is subtracted
	    			opponenten-=pokeattack.attack1c();
	    		}
	    		if (pokeattack.attack1s().equals("No Special")==true){ //if the attack has no special
	    			//applies damage to the appropriate pokemon
	    			if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){ //if it is the user's turn then checks if the user's pokemon is disabled
	    				opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);	
	    			}
	    			else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){ //otherwise checks if the computer is disabled
	    				opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);	
	    			}
	    			else{ //if the attacking pokemon isn ot disabled, then normal damage is applied
	    				opponent.damage(pokeattack.attack1d(),pokeattack.attack1n(),attpok);
	    			}
	    			if (turn.equals("user")==true){
    					opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    				}
    				else{
    					userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   				 	}	
	    		}
	    		//calls the appropriate method depending on the attack's special
	    		if (pokeattack.attack1s().equals("stun")==true){
	    			stun(1,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack1s().equals("wild card")==true){
	    			wildcard(1,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack1s().equals("wild storm")==true){
	    			wildstorm(1,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack1s().equals("disable")==true){
	    			disable(1,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack1s().equals("recharge")==true){
	    			atten=recharge(1,atten,pokeattack,attpok,opponent);
	    			if (turn.equals("computer")==true){
	    				opponenten=atten;	
	    			}
	    			else{
	    				userpoken[userpoke.indexOf(userpok)]=atten;
	    			}
	    		}
	    	}
	    	else{ //if the second attack of the attacking pokemon is being used
	    		if (turn.equals("user")==true){
	    			userpoken[userpoke.indexOf(userpok)]-=pokeattack.attack2c();
	    		}
	    		else{
	    			opponenten-=pokeattack.attack2c();
	    		}
	    		if (pokeattack.attack2s().equals("No Special")==true){
	    			if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
	    				opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);
	    			}
	    			else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
	    				opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);	
	    			}
	    			else{
	    				opponent.damage(pokeattack.attack2d(),pokeattack.attack2n(),attpok);
	    			}
	    			if (turn.equals("user")==true){
    					opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    				}
    				else{
    					userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   				 	}	
	    		}
	    		if (pokeattack.attack2s().equals("stun")==true){
	    			stun(2,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack2s().equals("wild card")==true){
	    			wildcard(2,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack2s().equals("wild storm")==true){
	    			wildstorm(2,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack2s().equals("disable")==true){
	    			disable(2,pokeattack,attpok,opponent);
	    		}
	    		if (pokeattack.attack2s().equals("recharge")==true){
	    			atten=recharge(2,atten,pokeattack,attpok,opponent);
	    			if (turn.equals("computer")==true){
	    				opponenten=atten;	
	    			}
	    			else{
	    				userpoken[userpoke.indexOf(userpok)]=atten;
	    			}
	    		}
	    }
    }
    
    public static void stun(int attacknum, PokeAttacks pokeattack,Pokemon attpok,Pokemon opponent){ //method for attacks that have stun as their special
    	if (attacknum==1){ //if the attack is the first attack of the attacking pokemon
    		//applies damage depending on the disabled status of the defending pokemon
    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
	    	}
	    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
	    	}
	    	else{
    			opponent.damage(pokeattack.attack1d(),pokeattack.attack1n(),attpok);
	    	}
    	}
    	else { //if the attack is the second attack of the attacking pokemon 
    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);	
	    	}
	    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
	    	}
	    	else{
    			opponent.damage(pokeattack.attack2d(),pokeattack.attack2n(),attpok);
	    	}	
    	}
    	if (turn.equals("user")==true){
    		opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    	}
    	else{
    		userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   		}
    	Random number = new Random();
    	x=(number.nextInt(2)+1); //generates a random number to determine whether or not the defending pokemon is stunned
    	if (x==1&&opponentstatus.equals("dead")==false&&userstatus.equals("dead")==false){
    		stunstatus="stunned";
    		System.out.println(opponent.getName()+" is stunned!");
    	}	
    }
    public static void wildcard(int attacknum,PokeAttacks pokeattack,Pokemon attpok,Pokemon opponent){ //method for attacks that have wildcard as their special
    	Random number = new Random();
    	x=(number.nextInt(2)+1); //gets a random number to determine whether the attack hits or misses
    	if (attacknum==1){ //if the attack is the first attack of the attacking pokemon
    		if (x==1){ //if the attack hits
    			//applies the damage depending on the disable status
	    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
		    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
		    	}
		    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
		    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
		    	}
		    	else{
	    			opponent.damage(pokeattack.attack1d(),pokeattack.attack1n(),attpok);
		    	}	
    		}
    		else{ //if the attack misses
    			System.out.println("\n"+attpok.getName()+" uses "+pokeattack.attack1n()+" but the attack misses!");	
    		}
    	}
    	else{ //if the attack is the second attack of the attacking pokemon
    		if (x==1){ //if the attack hits
    			//applies the damage depending on the disable status
	    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
		    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
		    	}
		    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
		    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
		    	}
		    	else{
	    			opponent.damage(pokeattack.attack2d(),pokeattack.attack2n(),attpok);		    	
		    	}	
    		}
    		else{ //if the attack misses
    			System.out.println("\n"+attpok.getName()+" uses "+pokeattack.attack2n()+" but the attack misses!");
    		}	
    	}
    	if (turn.equals("user")==true){
    		opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    	}
    	else{
    		userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   		}	
    }
    public static void wildstorm(int attacknum,PokeAttacks pokeattack,Pokemon attpok,Pokemon opponent){ //method for attacks that have wildcard as their special
    	Random number = new Random();
    	x=(number.nextInt(2)+1); //gets a random number to determine whether the attack hits or not (1 or 2)
    	if (attacknum==1){ //if the attack is the first attack of the attacking pokemon
    		if (x==1){ //if the random number is 1 the attack starts
    			while (x!=2&&opponent.getHp()>0){ //while the random number is 1 and the opponent is not dead
    				//applies the damage depending on the disable status
		    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
			    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
			    	}
			    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
			    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);	
			    	}
			    	else{
		    			opponent.damage(pokeattack.attack1d(),pokeattack.attack1n(),attpok);
			    	}
					if (turn.equals("user")==true){
    					opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    				}
    				else{
   				 		userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   					}
    				x=(number.nextInt(2)+1); //gets a new random number to determine whether the attack keeps hitting or not	
    			}
    		}
    		else{ //if the number is 2 then the attack misses
    			System.out.println("\nThe attack missed!");
    			System.out.println(opponent.getName()+" has "+opponent.getHp()+" hp left");	
    		}
    	}
    	else{ //if the attack is the second attack of the attacking pokemon
    		if (x==1){
    			while (x!=2&&opponent.getHp()>0){
		    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
			    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);	
			    	}
			    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
			    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
			    	}
			    	else{
		    			opponent.damage(pokeattack.attack2d(),pokeattack.attack2n(),attpok);
			    	}
    				if (turn.equals("user")==true){
    					opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    				}
    				else{
    					userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   					}
    				x=(number.nextInt(2)+1);		
    			}
    		}
    		else{
    			System.out.println("\nThe attack missed!");
    			System.out.println(opponent.getName()+" has "+opponent.getHp()+" hp left");	
    		}
    	}
    		
    }
    public static void disable(int attacknum,PokeAttacks pokeattack,Pokemon attpok,Pokemon opponent){ //method for attacks that have disable as their special
    	if (attacknum==1){ //if the attack is the first attack of the attacking pokemon
    		//applies the damage depending on the disable status
    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
	    	}
	    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
	    	}
	    	else{
    			opponent.damage(pokeattack.attack1d(),pokeattack.attack1n(),attpok);
	    	}
    	}
    	else{ //if the attack is the second attack of the attacking pokemon
    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
	    	}
	    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
	    	}
	    	else{
    			opponent.damage(pokeattack.attack2d(),pokeattack.attack2n(),attpok);
	    	}	
    	}
    	if (turn.equals("user")==true){
    		opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    	}
    	else{
    		userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   		}
    	//disables the appropriate pokemon
    	if (turn.equals("computer")==true){
	    	if (udstatus[userpoke.indexOf(opponent)].equals("normal")==true){
	    		System.out.println(opponent.getName()+" is disabled! Its attacks will now do 10 less damage");
	    		udstatus[userpoke.indexOf(opponent)]="disabled";	
	    	}
	    	else{
	    		System.out.println(opponent.getName()+" is already disabled!");
	    	}
   		}
   		else{
	    	if (cdstatus.equals("normal")==true){
	    		System.out.println(opponent.getName()+" is disabled! Its attacks will now do 10 less damage");
	    		cdstatus="disabled";	
	    	}
	    	else{
	    		System.out.println(opponent.getName()+" is already disabled!");
	    	}   			
   		}
    }
    public static int recharge(int attacknum,int energy,PokeAttacks pokeattack,Pokemon attpok,Pokemon opponent){ //method for attacks that have disable as their special
    	if (attacknum==1){ //if the attack is the first attack of the attacking pokemon
    		//applies the damage depending on the disable status
    		if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
	    	}
	    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack1d()-10,0),pokeattack.attack1n(),attpok);		
	    	}
	    	else{
    			opponent.damage(pokeattack.attack1d(),pokeattack.attack1n(),attpok);
	    	}	
    	}
    	else{ //if the attack is the second attack of the attacking pokemon
			if (turn.equals("user")==true&&udstatus[userpoke.indexOf(userpok)].equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
	    	}
	    	else if (turn.equals("computer")==true&&cdstatus.equals("disabled")){
	    		opponent.damage(Math.max(pokeattack.attack2d()-10,0),pokeattack.attack2n(),attpok);		
	    	}
	    	else{
    			opponent.damage(pokeattack.attack2d(),pokeattack.attack2n(),attpok);
	    	}
    	}
    	if (turn.equals("user")==true){
    		opponentstatus=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);	
    	}
    	else{
    		userstatus[userpoke.indexOf(userpok)]=opponent.hp(userpoke,userpok,turn,stunstatus,opponentstatus,userstatus);
   		}
    	oldenergy=energy;
    	energy=Math.min(50,energy+20); //increases the energy by 20
    	System.out.println(attpok.getName()+" recharges "+ (energy-oldenergy) +" energy");    
    	return energy;		
    }
    public static void actions(){ //method for display the actions
    	System.out.println("1: Attack");
    	System.out.println("2: Retreat");
    	System.out.println("3: Pass");
    }
    public static String userpokstatus(String[] userstatus){ //checks whether all the user's pokemon are dead
    	if (userstatus[0].equals("dead")==true&&userstatus[1].equals("dead")==true&&userstatus[2].equals("dead")==true&&userstatus[3].equals("dead")==true){
    		return "dead";	
    	}
    	else{
    		return "alive";
    	}
    }
    public static void battlechoosepoke(){ //method for the user to choose his/her pokemon for the battle
    	System.out.println("\nChoose the pokemon you wish to battle with (enter the corresponding number)");	
    	for (int q=1;q<5;q++){ //displays the four user's pokemon
    		System.out.println(q+": "+userpoke.get(q-1).getName()+" hp-"+userpoke.get(q-1).getHp());  			
    	}
    	while (w<1){ //runs the loop untill the user inputs a valid number and chooses a pokemon
    		input=keyboard.nextInt(); //gets a number as an input (1-4)
    		if (input<5&&0<input){ //checks if the number is between 1 and 4
    			if (userpoke.get(input-1).getHp()==0){
    				System.out.println("This pokemon has fainted and can no longer battle!");
    			}
    			else{
    				userpok=userpoke.get(input-1);
    				System.out.println("\n"+userpoke.get(input-1).getName()+", I choose you");	
    				w+=1;
    			}
    		}
    		else{
    			System.out.println("Invalid number!");			
    		}
    	}
    	w=0;
    }
    public static void delay (long len) //method for delay
    {
		try
		{
		    Thread.sleep (len);
		}
		catch (InterruptedException ex)
		{
		    System.out.println("I hate when my sleep is interrupted");
		}
    }	   
}

    
    	