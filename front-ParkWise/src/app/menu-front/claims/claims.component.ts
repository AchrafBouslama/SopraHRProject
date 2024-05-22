import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Claim } from 'src/app/models/claim';
import { Status } from 'src/app/models/status';
import { AuthService } from 'src/app/services/AuthService/auth.service';
import { ClaimService } from 'src/app/services/ClaimService/claim-service.service';
import { UserService } from 'src/app/services/UserService/user.service';

@Component({
  selector: 'app-claims',
  templateUrl: './claims.component.html',
  styleUrls: ['./claims.component.css']
})
export class ClaimsComponent implements OnInit {
  currentUser: any;
  subject: string = ''; // Propriété pour le sujet
  description: string = ''; // Propriété pour la description

  constructor(
    public authService: AuthService,
    private router: Router,
    private userService: UserService,
    private claimService: ClaimService
  ) { }

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId();
    this.userService.getUserId(parseInt(userId ?? '0')).subscribe((data) => {
      this.currentUser = data;
    });
  }

  addClaimToUser(): void {
    // Vérifier si l'utilisateur est connecté
    this.authService.isLoggedIn.subscribe((loggedIn) => {
      if (!loggedIn) {
        // Rediriger vers la page de connexion si l'utilisateur n'est pas connecté
        this.router.navigate(['/login']);
        return;
      }
    });

    // Créer un objet Claim avec les détails de la réclamation
    const claim: Claim = {
      id: 0,
      subject: this.subject, // Utilisez la propriété subject de la classe
      description: this.description, // Utilisez la propriété description de la classe
      createdAt: new Date(), // Utiliser la date actuelle pour la création de la réclamation
      status: Status.EN_ATTENTE, // Définir l'état initial de la réclamation
      user: this.currentUser // Utiliser l'utilisateur actuellement connecté comme utilisateur de la réclamation
      
    };

    // Appeler la fonction addClaimToUser du service ClaimService pour ajouter la réclamation à l'utilisateur
    this.claimService.addClaimToUser(claim).subscribe(
      (response: any) => {
        // Gérer la réponse en cas de succès
        console.log('Claim added successfully:', response);
        // Réinitialiser les champs du formulaire ou afficher un message de succès à l'utilisateur
        this.subject = ''; // Réinitialiser la valeur du sujet
        this.description = ''; // Réinitialiser la valeur de la description
        this.router.navigate(['/front/parking']);

      },
      (error: any) => {
        // Gérer l'erreur en cas d'échec de l'ajout de la réclamation
        console.error('Error adding claim:', error);
        // Afficher un message d'erreur à l'utilisateur ou enregistrer l'erreur dans un fichier journal
      }
    );
  }
}