import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ClaimService } from 'src/app/services/ClaimService/claim-service.service';
import { ResponseClaim } from 'src/app/models/responseClaim';

@Component({
  selector: 'app-add-response',
  templateUrl: './add-response.component.html',
  styleUrls: ['./add-response.component.css']
})
export class AddResponseComponent implements OnInit {
  responseClaim: ResponseClaim = new ResponseClaim(); // Déclaration de la propriété response

  constructor(
    private claimService: ClaimService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  // Méthode pour envoyer la réponse à la réclamation
  public addOrEditeResponse() {
    console.log("blocblocbloc", this.responseClaim);
    
    // Récupérer l'identifiant de la réclamation depuis l'URL
    const claimId = this.route.snapshot.params['id'];

    this.claimService.addResponseToClaim(this.responseClaim, claimId).subscribe({
      next: (response: any) => this.navigateToClaimList(),
      error: (error: any) => console.log(error),
      complete: () => this.responseClaim = new ResponseClaim(),
    });
  }

  navigateToClaimList(){
    this.router.navigate(['/admin/claim']);
  }
  
}
