import { Etage } from "./etage";

export class Parking {
    id!: number;
    image!:string;
    nom!: string ;
    adresse!: string ;
    capaciteTotale!:number ;
    description!:string ;
    latitude!:number;
    longitude!:number;
    etages!: Etage[];
    

}
