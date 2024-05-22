import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserComponent } from './user/user.component';
import { MenuComponent } from './menu/menu.component';
import { SignUpComponent } from './authentication/sign-up/sign-up.component';
import { ListParkingComponent } from './parking/list-parking/list-parking.component';
import { AjouterUserComponent } from './user/ajouter-user/ajouter-user.component';
import { ModifierUtilisateurComponent } from './user/modifier-utilisateur/modifier-utilisateur.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DescriptionDialogComponent } from './description-dialog/description-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AjouterParkingComponent } from './parking/list-parking/ajouter-parking/ajouter-parking.component';
import { ModifierParkingComponent } from './parking/list-parking/modifier-parking/modifier-parking.component';
import { ListEtageComponent } from './parking/list-etage/list-etage.component';
import { AjouterEtageComponent } from './parking/list-etage/ajouter-etage/ajouter-etage.component';
import { ModifierEtageComponent } from './parking/list-etage/modifier-etage/modifier-etage.component';
import { ListBlocComponent } from './parking/list-bloc/list-bloc.component';
import { ListPlaceparkingComponent } from './parking/list-placeparking/list-placeparking.component';
import { AjouterBlocComponent } from './parking/list-bloc/ajouter-bloc/ajouter-bloc.component';
import { ModifierBlocComponent } from './parking/list-bloc/modifier-bloc/modifier-bloc.component';
import { AjouterPlaceparkingComponent } from './parking/list-placeparking/ajouter-placeparking/ajouter-placeparking.component';
import { ModifierPlaceparkingComponent } from './parking/list-placeparking/modifier-placeparking/modifier-placeparking.component';
import { ListReservationComponent } from './parking/list-reservation/list-reservation.component';
import { MenuFrontComponent } from './menu-front/menu-front.component';
import { ParkingListComponent } from './menu-front/parking-list/parking-list.component';
import { ParkingDetailsComponent } from './menu-front/parking-details/parking-details.component';
import { ParkingPlacesComponent } from './menu-front/parking-places/parking-places.component';
import { HeaderFrontComponent } from './menu-front/header-front/header-front.component';
import { FooterFrontComponent } from './menu-front/footer-front/footer-front.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { Page404Component } from './page404/page404.component';
import { ActivateaccountComponent } from './authentication/activateaccount/activateaccount.component';
import { ListClaimComponent } from './parking/list-claim/list-claim.component';
import { AddResponseComponent } from './parking/list-claim/add-response/add-response.component';
import { ForgetpasswordComponent } from './authentication/forgetpassword/forgetpassword.component';
import { NewPasswordComponent } from './authentication/new-password/new-password.component';
import { ResetPasswordComponent } from './authentication/reset-password/reset-password.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';


@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    MenuComponent,
    SignUpComponent,
    ListParkingComponent,
    AjouterUserComponent,
    ModifierUtilisateurComponent,
    DescriptionDialogComponent,
    AjouterParkingComponent,
    ModifierParkingComponent,
    ListEtageComponent,
    AjouterEtageComponent,
    ModifierEtageComponent,
    ListBlocComponent,
    ListPlaceparkingComponent,
    AjouterBlocComponent,
    ModifierBlocComponent,
    AjouterPlaceparkingComponent,
    ModifierPlaceparkingComponent,
    ListReservationComponent,
    MenuFrontComponent,
    ParkingListComponent,
    ParkingDetailsComponent,
    ParkingPlacesComponent,
    HeaderFrontComponent,
    FooterFrontComponent,
    UserProfileComponent,
    Page404Component,
    ActivateaccountComponent,
    ListClaimComponent,
    AddResponseComponent,
    ForgetpasswordComponent,
    NewPasswordComponent,
    ResetPasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    LeafletModule
    
      ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
