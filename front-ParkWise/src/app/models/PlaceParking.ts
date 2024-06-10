import { Bloc } from "./Bloc";
import { Etage } from "./etage";
import { Reservation } from "./reservation";

export class PlaceParking {
    id!: number;
    numeroPlace!: string;
    estAccessibleHandicap!: boolean;
    estReservee!: boolean;
    typePlace!: string;
    bloc!: Bloc;
    reservation!:Reservation[];
    isSelected: boolean=false;
    x!: number;  
    y!: number; 
    isDisabled?:boolean;


}
