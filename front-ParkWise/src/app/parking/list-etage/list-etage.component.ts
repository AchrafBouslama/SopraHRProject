import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Etage } from 'src/app/models/etage';
import { EtageService } from 'src/app/services/EtageService/etage.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-list-etage',
  templateUrl: './list-etage.component.html',
  styleUrls: ['./list-etage.component.css']
})
export class ListEtageComponent {
  activeMenuItem: string = ''; // Variable pour suivre l'élément actif du menu

  // Méthode pour définir l'élément actif du menu
  setActiveMenuItem(menuItem: string) {
    this.activeMenuItem = menuItem;
  }

  
  etage:Etage=new Etage();
  etages: any;
  editetage:Etage=new Etage();
  id: any;
  numeroEtage!:number ;
  capaciteEtage!:number;
  parkings: any;

  constructor(private parkingService: ParkService,private etageService:EtageService,private router:Router) { }

  ngOnInit() {
    this.parkingService.displayParking().subscribe(data => {
      if (data) {
        this.parkings=data
        console.log(this.parkings);
      }
    });
    this.reloadData();
  }
  reloadData() {
    this.etageService.displayEtage().subscribe(data => {
      if (data) {
        console.log(data);
        this.etages=data
      }
    });
  }

  navigateToAddPage(){
    this.router.navigate(['/ajouterEtage'])
  }
  public deleteEtage(id: number): void {
    console.log(id,"dddddddddddddddddddddddddddd");
    
    this.etageService.deleteEtage(id).subscribe({
      next: response => this.etages = this.etages?.filter((s:Etage) => s.id != id),
      error: error => console.log(error),
      complete: () => console.log('Delete request completed.')
    });
  }
  

  public confirmDeleteEtage(id: number): void {
    if (window.confirm('Are you sure you want to delete this user?')) {
      this.deleteEtage(id);
    }
    
  }
  public editeEtage(id:number){
    this.router.navigate(['/admin/modifierEtage/'+id]);

  }



}
