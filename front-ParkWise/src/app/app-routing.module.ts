import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuComponent } from './menu/menu.component';
import { UserComponent } from './user/user.component';
import { SignUpComponent } from './authentication/sign-up/sign-up.component';
import { ListParkingComponent } from './parking/list-parking/list-parking.component';
import { AjouterUserComponent } from './user/ajouter-user/ajouter-user.component';
import { ModifierUtilisateurComponent } from './user/modifier-utilisateur/modifier-utilisateur.component';
import { AjouterParkingComponent } from './parking/list-parking/ajouter-parking/ajouter-parking.component';
import { ModifierParkingComponent } from './parking/list-parking/modifier-parking/modifier-parking.component';
import { ListEtageComponent } from './parking/list-etage/list-etage.component';
import { AjouterEtageComponent } from './parking/list-etage/ajouter-etage/ajouter-etage.component';
import { ModifierEtageComponent } from './parking/list-etage/modifier-etage/modifier-etage.component';
import { ListBlocComponent } from './parking/list-bloc/list-bloc.component';
import { AjouterBlocComponent } from './parking/list-bloc/ajouter-bloc/ajouter-bloc.component';
import { ModifierBlocComponent } from './parking/list-bloc/modifier-bloc/modifier-bloc.component';
import { ListPlaceparkingComponent } from './parking/list-placeparking/list-placeparking.component';
import { AjouterPlaceparkingComponent } from './parking/list-placeparking/ajouter-placeparking/ajouter-placeparking.component';
import { ModifierPlaceparkingComponent } from './parking/list-placeparking/modifier-placeparking/modifier-placeparking.component';
import { ListReservationComponent } from './parking/list-reservation/list-reservation.component';
import { MenuFrontComponent } from './menu-front/menu-front.component';
import { ParkingListComponent } from './menu-front/parking-list/parking-list.component';
import { ParkingDetailsComponent } from './menu-front/parking-details/parking-details.component';
import { ParkingPlacesComponent } from './menu-front/parking-places/parking-places.component';
import { AuthGuard } from './services/auth.guard';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { Page404Component } from './page404/page404.component';
import { ActivateaccountComponent } from './authentication/activateaccount/activateaccount.component';
import { ListClaimComponent } from './parking/list-claim/list-claim.component';
import { AddResponseComponent } from './parking/list-claim/add-response/add-response.component';
import { ForgetpasswordComponent } from './authentication/forgetpassword/forgetpassword.component';
import { NewPasswordComponent } from './authentication/new-password/new-password.component';
import { ResetPasswordComponent } from './authentication/reset-password/reset-password.component';

const routes: Routes = [
  { path: '', redirectTo: '/signup', pathMatch: 'full' },

  {path : 'admin', component:MenuComponent, children : [
    { path :'listUsers',component: UserComponent,canActivate:[AuthGuard]},
    {path:'ajouterUser',component:AjouterUserComponent,canActivate:[AuthGuard]},
    {path:'modifierUser/:id',component:ModifierUtilisateurComponent},
    { path :'listParking',component: ListParkingComponent},
    { path :'ajouterParking',component: AjouterParkingComponent},
{ path: 'modifierParking/:id', component: ModifierParkingComponent },
{ path: 'ListEtage', component: ListEtageComponent },
{ path: 'ajouterEtage', component: AjouterEtageComponent },
{ path: 'modifierEtage/:id', component: ModifierEtageComponent },
{ path: 'ListBloc', component: ListBlocComponent },
{ path: 'ajouterBloc', component: AjouterBlocComponent },
{ path: 'modifierBloc/:id', component: ModifierBlocComponent },
{ path: 'ListPlaceParking', component: ListPlaceparkingComponent },
{ path: 'ajouterPlaceParking', component: AjouterPlaceparkingComponent },
{ path: 'modifierPlaceParking/:id', component: ModifierPlaceparkingComponent },
{ path: 'ListResevations', component: ListReservationComponent },

{path:'claim',component:ListClaimComponent},
{path:'add-response/:id',component:AddResponseComponent},






  ]},
  { path: 'profile', component: UserProfileComponent },
  {
    path:'front',
    loadChildren:()=>import('./menu-front/menu-front.module').then((m)=>m.MenuFrontModule),
  },

 
  { path: 'signup', component: SignUpComponent } ,
  {path: 'verification',component:ActivateaccountComponent},
  {path: 'forget',component:ForgetpasswordComponent},
  {path: 'newPassword/:token',component:NewPasswordComponent},
  {path: 'reset',component:ResetPasswordComponent},
  
  {path:'**',component:Page404Component},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
