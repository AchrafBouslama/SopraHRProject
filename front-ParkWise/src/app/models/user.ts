import { Role } from "./role";
export class User {
  iduserr!: number;
  firstname!: string ;
  lastname!: string ;
  email!:string ;
  password!:string ;
  role !:Role ;
  enabled!:Boolean;
}
