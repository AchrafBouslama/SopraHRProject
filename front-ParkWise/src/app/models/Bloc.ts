import { Etage } from "./etage";
import { PlaceParking } from "./PlaceParking";

export class Bloc {
    id!: number;
    identifiantBloc!:number ;
    placesParking!:PlaceParking[];
    etage!:Etage;
}
