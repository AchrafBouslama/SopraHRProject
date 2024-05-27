import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Etage } from 'src/app/models/etage';
import { Parking } from 'src/app/models/parking';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-ajouter-etage',
  templateUrl: './ajouter-etage.component.html',
  styleUrls: ['./ajouter-etage.component.css']
})
export class AjouterEtageComponent {
  etage:Etage=new Etage(); 
  
  parkings:any;
  editetage:Etage=new Etage();
  parking:Parking=new Parking(); 
  id: any;
  constructor(private parkingService: ParkService,private etageService:EtageService,private router:Router) { }

  ngOnInit() {
    this.etage.parking=new Parking();
    this.parkingService.displayParking().subscribe(data => {
      if (data) {
        this.parkings=data
        console.log("***")
        console.log(this.parkings);

      }
    });
  }

  public addOrEditeEtage(etage: Etage) {
    console.log("parkingparkingparking", etage);
    
    this.etageService.addEtageToParking(etage,etage.parking.id).subscribe({
        next: response => this.navigateToParkingList(),
        error: error => console.log(error),
        complete: () => this.etage = new Etage(),
    });
}
  private navigateToParkingList(){
      this.router.navigate(['/admin/ListEtage'])
  }

}
