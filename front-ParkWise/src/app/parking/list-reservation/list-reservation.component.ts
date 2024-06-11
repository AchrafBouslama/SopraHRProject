import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { Reservation } from 'src/app/models/reservation';
import { ReservationService } from 'src/app/services/ReservationService/reservation.service';
import { UserService } from 'src/app/services/UserService/user.service';

@Component({
  selector: 'app-list-reservation',
  templateUrl: './list-reservation.component.html',
  styleUrls: ['./list-reservation.component.css']
})
export class ListReservationComponent {


  activeMenuItem: string = ''; // Variable pour suivre l'élément actif du menu

  // Méthode pour définir l'élément actif du menu
  setActiveMenuItem(menuItem: string) {
    this.activeMenuItem = menuItem;
  }

  
  reservation:Reservation=new Reservation();
  blocs: any;
  editbloc:Reservation=new Reservation();
  id: any;
  resevations!:any;
  users:any;
  constructor(private reservationService: ReservationService,private userService:UserService,private router:Router) { }

  ngOnInit() {
    this.userService.getUser().subscribe(data => {
      if (data) {
        this.users=data
        console.log(this.users);
      }
    });
    this.reloadData();
  }
   reloadData() {
    this.reservationService.displayReservations().subscribe(data => {
      if (data) {
        console.log(data);
        this.resevations=data
      }
    });
  }
  parseDate(dateStr:any):Date{
    console.log(dateStr);
    console.log(typeof dateStr);
    if(typeof dateStr==='string'){
      const parts=dateStr.split(',').map(part=>parseInt(part,10));
      return new Date(parts[0],parts[1]-1,parts[2],parts[3],parts[4],parts[5]);
    }
    return new Date();
    
  }

  



}
