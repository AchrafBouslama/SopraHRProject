import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { PlaceParking } from 'src/app/models/PlaceParking';
import { BlocService } from 'src/app/services/BlocService/bloc.service';
import { PlaceParkService } from 'src/app/services/PlaceParkService/place-park.service';

@Component({
  selector: 'app-list-placeparking',
  templateUrl: './list-placeparking.component.html',
  styleUrls: ['./list-placeparking.component.css']
})
export class ListPlaceparkingComponent {


  
  activeMenuItem: string = ''; // Variable pour suivre l'élément actif du menu

  // Méthode pour définir l'élément actif du menu
  setActiveMenuItem(menuItem: string) {
    this.activeMenuItem = menuItem;
  }

  
  placeParking:PlaceParking=new PlaceParking();
  blocs: any;
  editplaceparking:PlaceParking=new PlaceParking();
  id: any;
  etages!:any;
  placeparkingss!:any;
  constructor(private blocService: BlocService,private placeParkingService:PlaceParkService,private router:Router) { }

  ngOnInit() {
    this.blocService.displayBloc().subscribe(data => {
      if (data) {
        this.blocs=data
        console.log(this.blocs);
      }
    });
    this.reloadData();
  }

  
  reloadData() {
    this.placeParkingService.displayPlaceParking().subscribe(data => {
      if (data) {
        console.log(data);
        this.placeparkingss=data
      }
    });
  }

  navigateToAddPage(){
    this.router.navigate(['/admin/ajouterPlaceParking'])
  }
  public deletePlaceParking(id: number): void {
    console.log(id,"dddddddddddddddddddddddddddd");
  
    this.placeParkingService.deletePlaceParking(id).subscribe({
      next: response => {
        this.blocs = this.blocs?.filter((s:PlaceParking) => s.id != id);
        // Mettre à jour this.placeparkingss en supprimant l'élément supprimé de la liste
        this.placeparkingss = this.placeparkingss.filter((s: PlaceParking) => s.id !== id);
      },
      error: error => console.log(error),
      complete: () => console.log('Delete request completed.')
    });
  }
  
  public confirmDeletePlaceParking(id: number): void {
    if (window.confirm('Are you sure you want to delete this user?')) {
      this.deletePlaceParking(id);
    }
    
  }
  public editePlaceParking(id:number){
    this.router.navigate(['/admin/modifierPlaceParking/'+id]);

  }

}
