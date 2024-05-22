import { PlaceParking } from "./PlaceParking";
import { User } from "./user";

export class Reservation {
    id!: number;
    placeParking!: PlaceParking; 
    debutReservation!: Date; 
    finReservation!: Date; 
    estActive!: boolean;
    utilisateur!: User;


  
}
