import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Bloc } from 'src/app/models/Bloc';
import { Etage } from 'src/app/models/etage';
import { BlocService } from 'src/app/services/BlocService/bloc.service';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-list-bloc',
  templateUrl: './list-bloc.component.html',
  styleUrls: ['./list-bloc.component.css']
})
export class ListBlocComponent {

  activeMenuItem: string = ''; // Variable pour suivre l'élément actif du menu

  // Méthode pour définir l'élément actif du menu
  setActiveMenuItem(menuItem: string) {
    this.activeMenuItem = menuItem;
  }

  
  bloc:Bloc=new Bloc();
  blocs: any;
  editbloc:Bloc=new Bloc();
  id: any;
  etages!:any;
  constructor(private blocService: BlocService,private etageService:EtageService,private router:Router) { }

  ngOnInit() {
    this.etageService.displayEtage().subscribe(data => {
      if (data) {
        this.etages=data
        console.log(this.etages);
      }
    });
    this.reloadData();
  }

  
  reloadData() {
    this.blocService.displayBloc().subscribe(data => {
      if (data) {
        console.log(data);
        this.blocs=data
      }
    });
  }

  navigateToAddPage(){
    this.router.navigate(['/ajouterBloc'])
  }
  public deleteBloc(id: number): void {
    console.log(id,"dddddddddddddddddddddddddddd");
    
    this.blocService.deleteBloc(id).subscribe({
      next: response => this.blocs = this.blocs?.filter((s:Bloc) => s.id != id),
      error: error => console.log(error),
      complete: () => console.log('Delete request completed.')
    });
  }
  

  public confirmDeleteBloc(id: number): void {
    if (window.confirm('Are you sure you want to delete this user?')) {
      this.deleteBloc(id);
    }
    
  }
  public editeBloc(id:number){
    this.router.navigate(['/modifierBloc/'+id]);

  }






}
