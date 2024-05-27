
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Claim } from 'src/app/models/claim';
import { ClaimService } from 'src/app/services/ClaimService/claim-service.service';

@Component({
  selector: 'app-list-claim',
  templateUrl: './list-claim.component.html',
  styleUrls: ['./list-claim.component.css']
})
export class ListClaimComponent implements OnInit {
  constructor(private formBuilder: FormBuilder,private claimService: ClaimService,public dialog: MatDialog ,private router:Router ) { }

  claim:Claim=new Claim(); 
  claims:any;

  errorMessage: string = ''; // Message d'erreur pour la gestion des erreurs

  ngOnInit(): void {
    this.reloadData();
    
  }

  reloadData() {
    this.claimService.displayClaim().subscribe(data => {
      if (data) {
        this.claims=data
        console.log(this.claims);
      }
    });
  }


  redirectToResponseForm(claimId: number) {
    this.router.navigate(['/admin/add-response', claimId]);
  }


}
