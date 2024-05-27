import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Etage } from 'src/app/models/etage';
import { Parking } from 'src/app/models/parking';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-modifier-etage',
  templateUrl: './modifier-etage.component.html',
  styleUrls: ['./modifier-etage.component.css']
})
export class ModifierEtageComponent {
  etage:Etage=new Etage(); 
  parkings:any;
  editetage:Etage=new Etage();
  parking:Parking=new Parking(); 
  id: any;

  constructor(private parkingService:ParkService,private etageService: EtageService,private router:Router,private route:ActivatedRoute  ) { }

  ngOnInit(): void {
  
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.id = +idParam; 
        this.loadParkings(() => {
          this.loadEtage(this.id);
        });
      }
    });
  }
  

  loadEtage(id:number):void{
    this.etageService.getEtageById(id).subscribe({
      next:(data)=>{
        console.log(data);
        this.etage = data;
        this.parking = data.parking;
        this.etage.parking.id = data.parking.id; 
        console.log(this.parking);
      },
    })
  }
  

  loadParkings(callback?: Function): void {
    this.parkingService.displayParking().subscribe({
      next: (data: Parking[]) => {
        this.parkings = data;
        if (callback) {
          callback(); 
        }
      },
      error: (error) => {
        console.error('Erreur lors du chargement des parkings :', error);
      }
    });
  }

  public addOrEditeEtage(etage: Etage) {
    this.etageService.updateEtage(this.id,etage).subscribe({
      next: response => this.navigateToParkingList(),
      error: error => {alert('Vos avez dépassé la capacité total du Parking.Veuillez vérifier vos données ');
      console.error('Erreur lors de la mise à jour de l\'étage : ', error);},
      complete: () => this.etage = new Etage(),
  });
  
}
private navigateToParkingList(){
  this.router.navigate(['/admin/ListEtage'])
}
}
