import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { Etage } from 'src/app/models/etage';
import { Parking } from 'src/app/models/parking';
import { BlocService } from 'src/app/services/BlocService/bloc.service';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-modifier-bloc',
  templateUrl: './modifier-bloc.component.html',
  styleUrls: ['./modifier-bloc.component.css']
})
export class ModifierBlocComponent {
  bloc:Bloc=new Bloc(); 
  etages:any;
  editbloc:Bloc=new Bloc();
  etage:Etage=new Etage(); 
  id: any;

  constructor(private blocService:BlocService,private etageService: EtageService,private router:Router,private route:ActivatedRoute  ) { }

  ngOnInit(): void {
  
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.id = +idParam; 
        this.loadetages(() => {
          this.loadBloc(this.id);
        });
      }
    });
  }
  

  loadBloc(id:number):void{
    this.blocService.getBlocById(id).subscribe({
      next:(data)=>{
        console.log(data);
        this.bloc = data;
        this.etage = data.etage;
        this.bloc.etage.id = data.etage.id; 
        console.log(this.etage);
      },
    })
  }
  

  loadetages(callback?: Function): void {
    this.etageService.displayEtage().subscribe({
      next: (data: Etage[]) => {
        this.etages = data;
        if (callback) {
          callback(); 
        }
      },
      error: (error) => {
        console.error('Erreur lors du chargement des parkings :', error);
      }
    });
  }

  public addOrEditeBloc(bloc: Bloc) {
    this.blocService.updateBloc(this.id,bloc).subscribe({
      next: response => this.navigateToBlocList(),
      error: error => {alert('Vos avez dépassé la capacité total du Parking.Veuillez vérifier vos données ');
      console.error('Erreur lors de la mise à jour de l\'étage : ', error);},
      complete: () => this.bloc = new Bloc(),
  });
  
}
private navigateToBlocList(){
  this.router.navigate(['/ListBloc'])
}

}
