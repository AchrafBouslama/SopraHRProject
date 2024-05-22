import { ResponseClaim } from "./responseClaim";
import { Status } from "./status";
import { User } from "./user";


export class Claim {
    id!: number;
    user!: User; 
    subject!: string;
    description!: string;
    createdAt!: Date;
    status!: Status;
    claimResponse?:ResponseClaim;
  }
