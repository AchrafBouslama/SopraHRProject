import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { PlaceParking } from 'src/app/models/PlaceParking';
import { BlocService } from 'src/app/services/BlocService/bloc.service';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { PlaceParkService } from 'src/app/services/PlaceParkService/place-park.service';

@Component({
  selector: 'app-ajouter-placeparking',
  templateUrl: './ajouter-placeparking.component.html',
  styleUrls: ['./ajouter-placeparking.component.css']
})
export class AjouterPlaceparkingComponent {

  placeParking:PlaceParking=new PlaceParking(); 
  etages:any;
  editplaceParking:PlaceParking=new PlaceParking();
  bloc:Bloc=new Bloc(); 
  id: any;
  blocs!:any;
  constructor(private blocService: BlocService,private placeParkingService:PlaceParkService,private router:Router) { }

  ngOnInit() {
    this.placeParking.bloc=new Bloc();
    this.blocService.displayBloc().subscribe(data => {
      if (data) {
        this.blocs=data
        console.log(this.blocs);
      }
    });
  }

  public addOrEditePlaceParking(placeParking: PlaceParking) {
    console.log("blocblocbloc", placeParking);
    
    this.placeParkingService.addPlaceParkingToBloc(placeParking,placeParking.bloc.id).subscribe({
        next: response => this.navigateToBlocList(),
        error: error => console.log(error),
        complete: () => this.placeParking = new PlaceParking(),
    });
}
  private navigateToBlocList(){
      this.router.navigate(['/ListPlaceParking'])
  }



}
