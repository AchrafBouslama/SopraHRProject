import { Bloc } from "./Bloc";
import { Parking } from "./parking";

export class Etage {
    id!: number;
    capaciteEtage!:number;
    numeroEtage!:number ;
    blocs!: Bloc[];
    parking!:Parking;

}
