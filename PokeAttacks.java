import java.util.*;
import java.io.*;
public class PokeAttacks{
	//sets up the variables
	private String pokemoninfo ="";
	private String[] pokemonarray;
	private int attack1d;
	private int attack2d;
	public PokeAttacks(Pokemon pokemon){ //takes in a Pokemon object
		pokemoninfo=pokemon.toString(); //sets pokemoninfo as the line from the data file
		pokemonarray=pokemoninfo.split(","); //splits the line from the data file at the commas
		//defines the attack names depending on how many attacks the pokemon has
		if (numoAtt()==1){
			attack1d=Integer.parseInt(pokemonarray[8]);	
		}
		else{
			attack1d=Integer.parseInt(pokemonarray[8]);
			attack2d=Integer.parseInt(pokemonarray[12]);	
		}	
	}
	public Integer numoAtt(){ //returns the number of attacks
		return Integer.parseInt(pokemonarray[5]);
	}
	public String attack1n(){ //returns the name of the first attack
		return pokemonarray[6];
	}
	public Integer attack1c(){ //returns the cost of the first attack
		return Integer.parseInt(pokemonarray[7]);
	}
	public Integer attack1d(){ //returns the damage that the first attack does
		return attack1d;
	}
	public String attack1s(){ //returns the special of the first attack
		if (pokemonarray[9].equals(" ")==false){
			return pokemonarray[9];	
		}
		else{
			return "No Special";
		}
	}
	public String attack2n(){ //returns the name of the second attack
		return pokemonarray[10];		
	}
	public Integer attack2c(){ //returns the cost of the second attack
		return Integer.parseInt(pokemonarray[11]);		
	}
	public Integer attack2d(){ //returns the damage that the second attack does
		return attack2d;	
	}
	public String attack2s(){ //returns the special of the second attack
		if (pokemonarray[13].equals(" ")==false){
			return pokemonarray[13];	
		}
		else{
			return "No Special";
		}
	}
	public String toString(){ //returns the name of the pokemon
		return pokemonarray[0];
	}
}


	