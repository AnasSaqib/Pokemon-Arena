import java.util.*;
import java.io.*;
public class Pokemon{
	//sets up the variables
	private String pokemoninfo ="";
	private String[] pokemonarray;
	private String name;
	private int hp;
	private int orighp;
	private String type;
	private String resistance;
	private String weakness;
	public Pokemon(String pokeal){ //takes in a line from the data file
		pokemoninfo=pokeal;
		pokemonarray=pokemoninfo.split(","); //splits the line from the data file at the commas
		name=pokemonarray[0];
		orighp=Integer.parseInt(pokemonarray[1]);
		hp=Integer.parseInt(pokemonarray[1]);
		type=pokemonarray[2];
		resistance=pokemonarray[3];
		weakness=pokemonarray[4]; 	
	}
	public String getName(){ //returns the name
		return name;	
	} 
	public int getHp(){ //returns the hp
		return hp;
	}
	public String getType(){ //returns the type of the pokemon
		return type;
	} 
	public String getResis(){ //returns the resistance of the pokemon
		if(resistance.equals(" ")==true){
			return "No Resistance";
		}
		else{
			return resistance;
		}
	}
	public String getWeak(){ //returns the weakness of the pokemon
		if(weakness.equals(" ")==true){
			return "No Weakness";
		}
		else{
			return weakness;
		}
	}
	public String toString(){ //returns the line from the data file (corresponding to the pokemon)
		return pokemoninfo;
	}
	public void damage(int d,String attackname,Pokemon attpok){ //method for damage takes in the damage(d), the name of the attack being used(attackname) and the Pokemon object of the attacking pokemon 
		PokeAttacks pokeattack=new PokeAttacks(attpok);
		if (getWeak().equals(attpok.getType())==true){ //if the attack is super effective
			hp=Math.max(0,hp-(d*2)); //deals double damage
			System.out.println("\n"+attpok.getName()+" uses "+attackname+" and deals "+Math.max(0,d*2)+" damage. It's super effective!");	
		}
		else if (getResis().equals(attpok.getType())==true){ //if the attack is not very effective
			hp=Math.max(0,hp-(d/2)); //deals half damage
			System.out.println("\n"+attpok.getName()+" uses "+attackname+" and deals "+Math.max(0,d/2)+" damage. It's not very effective.");
		}
		else{ //deals normal damage
			hp=Math.max(0,hp-d);	
			System.out.println("\n"+attpok.getName()+" uses "+attackname+" and deals "+Math.max(0,d)+" damage");			
		}
	}
	public String hp(ArrayList<Pokemon> userpoke,Pokemon userpok,String turn,String stunstatus,String opponentstatus,String[] userstatus){ //method for displaying the defending pokemons hp
		if (getHp()<=0){ //checks if the defending pokemon is dead
    		System.out.println(getName()+" has fainted!");	
    		stunstatus="normal";	
    		if (turn.equals("user")){
    			return "dead";
    		}
    		else{
    			return "dead";
    		}     						    				
    	}
    	else{ //else displays the hp of the defending pokemon
    		System.out.println(getName()+" has "+getHp()+" hp left");
    		return "alive";
    	}
	}
	public void heal(){ //heals 20 hp (for end of each battle)
		if (hp>0){
			hp=Math.min(orighp,hp+20);	
		}
	}	
}