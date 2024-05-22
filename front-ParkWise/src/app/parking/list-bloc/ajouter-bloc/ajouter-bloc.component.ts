import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { Etage } from 'src/app/models/etage';
import { Parking } from 'src/app/models/parking';
import { BlocService } from 'src/app/services/BlocService/bloc.service';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-ajouter-bloc',
  templateUrl: './ajouter-bloc.component.html',
  styleUrls: ['./ajouter-bloc.component.css']
})
export class AjouterBlocComponent {

  bloc:Bloc=new Bloc(); 
  etages:any;
  editbloc:Bloc=new Bloc();
  etage:Etage=new Etage(); 
  id: any;
  constructor(private blocService: BlocService,private etageService:EtageService,private router:Router) { }

  ngOnInit() {
    this.bloc.etage=new Etage();
    this.etageService.displayEtage().subscribe(data => {
      if (data) {
        this.etages=data
        console.log(this.etages);
      }
    });
  }

  public addOrEditeBloc(bloc: Bloc) {
    console.log("blocblocbloc", bloc);
    
    this.blocService.addBlocToEtage(bloc,bloc.etage.id).subscribe({
        next: response => this.navigateToBlocList(),
        error: error => console.log(error),
        complete: () => this.bloc = new Bloc(),
    });
}
  private navigateToBlocList(){
      this.router.navigate(['/ListBloc'])
  }


}
