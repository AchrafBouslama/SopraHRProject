import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MenuFrontComponent } from './menu-front.component';
import { ParkingListComponent } from './parking-list/parking-list.component';
import { ParkingDetailsComponent } from './parking-details/parking-details.component';
import { ParkingPlacesComponent } from './parking-places/parking-places.component';
import { ClaimsComponent } from './claims/claims.component';
import { UserClaimsComponent } from './claims/user-claims/user-claims.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
  {
    path:'',
    component:MenuFrontComponent,
    children:[
  {path:'parking',component:ParkingListComponent},
  {path:'parking-detail/:id',component:ParkingDetailsComponent},
  {path:'parking-place/:id',component:ParkingPlacesComponent},
  {path:'claim',component:ClaimsComponent},
  {path:'userClaims',component:UserClaimsComponent},
  {path: 'profile',component:ProfileComponent}

]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MenuFrontRoutingModule { }
