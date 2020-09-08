package com.example.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class MontyHall{

    enum GoatOrCar{
        GOAT(0),
        CAR(1);

        public final int value;
        GoatOrCar(int value){
            this.value = value;
        }
    }
    @Autowired
    PropertyResolver props;

    @GetMapping("/play/{iterations}/{switch}")
    public String playMonty(@PathVariable("iterations") int iterations, @PathVariable("switch") boolean switchOptions) {
        int doorsCount = Integer.parseInt(props.doorCount);
        System.out.println("Count :" + doorsCount);
        int switched = 0;
        int notSwitched = 0;
        int winCount = 0;
        StringBuffer output = new StringBuffer();
        output.append("Iteration Door 1\t Door 2\t Door 3\t\t Selected\t Switched\t Win\n");
        output.append("-----------------------------------------------------------------\n");
        for(int plays =1; plays <= iterations; plays++) {
            int[] doors = new int[doorsCount];
            Arrays.stream(doors).forEach(door -> door = GoatOrCar.GOAT.value); //Assigning GOAT for all doors initially
            doors[getNextInt()] = GoatOrCar.CAR.value; // CAR in one random place

            output.append(plays + "\t\t");
            for(int i=0; i <3; i++){
                if(doors[i] == 0)
                    output.append("goat\t\t");
                else
                    output.append("car \t\t");
            }

            int choice = getNextInt(); //pick the random door
            int shown; //door shown by Monty
            do {
                shown = getNextInt();
            } while (doors[shown] == 1 || shown == choice);
            notSwitched += doors[choice];   //won by not switching
            switched += doors[3 - choice - shown];

            output.append(String.valueOf(choice+1) + "\t\t");
            output.append(String.valueOf(switchOptions) + "\t\t");

            if(switchOptions){
                if(doors[3 - choice - shown] == 1){
                    output.append("true" + "\n");
                    winCount++;
                }
                else{
                    output.append("false" + "\n");
                }
            }else{
                if(doors[choice] == 1){
                    output.append("true" + "\n");
                    winCount++;
                }else{
                    output.append("false" + "\n");
                }
            }
        }
        if( iterations <= 10000){
            output.append("Win Percentage: " + String.format("%.2f",(double)(100*winCount/(double)iterations)));
            return output.toString();
        }
        else{
            return "Win Percentage: " + String.format("%.2f", (double)(100*winCount/(double)iterations));
        }
    }

    private int getNextInt(){
        Random gen = new Random();
        return gen.nextInt(3);
    }
}
