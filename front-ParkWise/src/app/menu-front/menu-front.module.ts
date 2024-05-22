import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MenuFrontRoutingModule } from './menu-front-routing.module';
import { HomeComponent } from './home/home.component';
import { ClaimsComponent } from './claims/claims.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserClaimsComponent } from './claims/user-claims/user-claims.component';
import { ProfileComponent } from './profile/profile.component';
import { SidebarProfileComponent } from './sidebar-profile/sidebar-profile.component';


@NgModule({
  declarations: [
    HomeComponent,
    ClaimsComponent,
    UserClaimsComponent,
    ProfileComponent,
    SidebarProfileComponent,
  
    
  ],
  imports: [
    CommonModule,
    MenuFrontRoutingModule,
    FormsModule ,
    ReactiveFormsModule
  ]
})
export class MenuFrontModule { }
