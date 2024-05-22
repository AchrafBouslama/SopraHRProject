import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Claim } from 'src/app/models/claim';
import { ResponseClaim } from 'src/app/models/responseClaim';
import { ClaimService } from 'src/app/services/ClaimService/claim-service.service';

@Component({
  selector: 'app-user-claims',
  templateUrl: './user-claims.component.html',
  styleUrls: ['./user-claims.component.css']
})
export class UserClaimsComponent {
  errorMessage!: string;
  i!: any;

  constructor(private formBuilder: FormBuilder, private claimService: ClaimService, public dialog: MatDialog, private router: Router) { }

  claim: Claim = new Claim();
  claims: Claim[] = []; // Déclaration du type de claims comme un tableau de Claim

  ngOnInit(): void {
    this.reloadData();
  }

  reloadData() {
    this.claimService.getClaimsByCurrentUser().subscribe(data => {
        if (data) {
            this.claims = data;

            // Charger les réponses pour toutes les réclamations
            this.claims.forEach(claim => {
                if (!claim.claimResponse) { // Ajouter une condition pour vérifier si la réponse doit être affichée initialement
                    this.claimService.getResponsesByClaimId(claim.id).subscribe(
                        (responses: ResponseClaim[]) => {
                            claim.claimResponse = responses[0]; // Supposons que vous récupérez une seule réponse pour chaque réclamation
                        },
                        (error) => {
                            this.errorMessage = 'Erreur lors de la récupération de la réponse : ' + error; // Gestion des erreurs
                        }
                    );
                }
            });

            console.log(this.claims);
        }
    });
}


  redirectToResponseForm(claimId: number) {
    this.router.navigate(['/add-response', claimId]);
  }

  toggleResponse(claim: Claim): void {
    if (!claim.claimResponse) {
      // Si la réponse n'est pas déjà chargée, chargez-la depuis le service
      this.claimService.getResponsesByClaimId(claim.id).subscribe(
        (responses: ResponseClaim[]) => {
          claim.claimResponse = responses[0];
          // Supposons que vous récupérez une seule réponse pour cette réclamation
        },
        (error) => {
          this.errorMessage = 'Erreur lors de la récupération de la réponse : ' + error; // Gestion des erreurs
        }
      );
    } else {
      claim.claimResponse = undefined; // Réinitialiser la réponse pour masquer
    }
  }
}
