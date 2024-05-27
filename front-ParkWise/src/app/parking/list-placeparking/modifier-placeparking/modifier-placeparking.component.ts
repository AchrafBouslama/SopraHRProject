import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { PlaceParking } from 'src/app/models/PlaceParking';
import { Parking } from 'src/app/models/parking';
import { BlocService } from 'src/app/services/BlocService/bloc.service';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { PlaceParkService } from 'src/app/services/PlaceParkService/place-park.service';

@Component({
  selector: 'app-modifier-placeparking',
  templateUrl: './modifier-placeparking.component.html',
  styleUrls: ['./modifier-placeparking.component.css']
})
export class ModifierPlaceparkingComponent {
  placeparking: PlaceParking = new PlaceParking(); 
  blocs: any;
  editplaceparking: PlaceParking = new PlaceParking();
  bloc: Bloc = new Bloc(); 
  id: any;

  constructor(private blocService: BlocService, private placeParkingService: PlaceParkService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.id = +idParam; 
        this.loadblocs(() => {
          this.loadPlaceParking(this.id);
        });
      }
    });
  }
  

  loadPlaceParking(id: number): void {
    this.placeParkingService.getPlaceParkingById(id).subscribe({
      next:(data) => {
        console.log(data);
        this.placeparking = data;
        this.bloc = data.bloc;
        this.placeparking.bloc.id = data.bloc.id; 
        console.log(this.bloc);
      },
    })
  }
  

  loadblocs(callback?: Function): void {
    this.blocService.displayBloc().subscribe({
      next: (data: Parking[]) => {
        this.blocs = data;
        if (callback) {
          callback(); 
        }
      },
      error: (error) => {
        console.error('Erreur lors du chargement des blocs :', error);
      }
    });
  }

  public addOrEditePlaceParking(placeparking: PlaceParking) {
    this.placeParkingService.updatePlaceParking(this.id, placeparking).subscribe({
      next: response => this.navigateToBlocList(),
      error: error => {alert('Vos avez dépassé la capacité total du Parking.Veuillez vérifier vos données ');
                      console.error('Erreur lors de la mise à jour de l\'étage : ', error);},
      complete: () => this.bloc = new Bloc(),
    });
  }
  
  private navigateToBlocList(){
    this.router.navigate(['/admin/ListPlaceParking'])
  }
}
