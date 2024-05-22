import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { Etage } from 'src/app/models/etage';
import { Parking } from 'src/app/models/parking';
import { BlocService } from 'src/app/services/BlocService/bloc.service';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-parking-details',
  templateUrl: './parking-details.component.html',
  styleUrls: ['./parking-details.component.css']
})
export class ParkingDetailsComponent implements OnInit{
  parking!:Parking;
  etages:Etage[]=[];
  id: any;
  blocsByEtage:{[key:number]: Bloc[]}={};
  constructor(
    private route:ActivatedRoute,
    private parkingService:ParkService,
    private etageService:EtageService,
    private blocService:BlocService,
    private router:Router
  ){}
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.id = +idParam; 
        this.parkingService.getParkingById(this.id).subscribe(parking=>{

          this.parking=parking;
          console.log(parking)
          this.etageService.displayEtages(this.id).subscribe(etages=>{
            this.etages=etages;
            console.log(etages)
            this.etages.forEach(etage=>{
              this.blocService.displayBlocs(etage.id).subscribe(blocs=>{
                this.blocsByEtage[etage.id]=blocs;
              })
            })
          })
        })
    }
    })
  }
  navigateToParkingplacess(id:number){
    this.router.navigate(['/front/parking-place',id]);
  }


}
