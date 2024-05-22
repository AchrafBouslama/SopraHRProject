import { Role } from "./role";

export class AuthResponse {
    id!: number;
    token!:string ;
    role!:Role;
    firstname!:string;
    lastname!:string;
    
}