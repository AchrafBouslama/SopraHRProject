import { Component, OnInit } from '@angular/core';
import { Parking } from 'src/app/models/parking';
import { ParkService } from 'src/app/services/parkService/park.service';
import * as bootstrap from 'bootstrap';
import { MatDialog } from '@angular/material/dialog';
import { DescriptionDialogComponent } from 'src/app/description-dialog/description-dialog.component';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';

declare var $: any; // Déclarer jQuery si nécessaire


@Component({
  selector: 'app-list-parking',
  templateUrl: './list-parking.component.html',
  styleUrls: ['./list-parking.component.css']
})
export class ListParkingComponent implements OnInit {
  
  parking:Parking=new Parking(); 
  parkings:any;
  editparking:Parking=new Parking();
  id: any;
  selectedDescription:string='';
  constructor(private formBuilder: FormBuilder,private parkingService: ParkService,public dialog: MatDialog ,private router:Router ) { }

  ngOnInit(): void {
    this.reloadData();
    
  }

  reloadData() {
    this.parkingService.displayParking().subscribe(data => {
      if (data) {
        this.parkings=data
        console.log(this.parkings);
      }
    });
  }
  public editeParking(id:number){
    this.router.navigate(['/modifierParking/'+id]);

  }
 /* public addOrEditeUser(parking:Parking ){
    console.log("parkingggggggggg",parking);
    if(!this.id){
      this.parkingService.addParking(parking).subscribe({
        next: response => this.reloadData(),
        error: error => console.log(error),
        complete: () =>  this.parking=new Parking(),
      });
    }else{
      this.parking.id=this.id;
      this.parkingService.updateParking(parking).subscribe({
        next: response => this.reloadData(),
        error: error => console.log(error),
        complete: () => this.parking=new Parking(),
      });
      this.id=null;
    }
    
  }*/
  public deleteUser(id: number): void {
    console.log(id,"dddddddddddddddddddddddddddd");
    
    this.parkingService.deleteParking(id).subscribe({
      next: response => this.parkings = this.parkings?.filter((s:Parking) => s.id != id),
      error: error => console.log(error),
      complete: () => console.log('Delete request completed.')
    });
  }
  public confirmDeleteParking(id: number): void {
  if (window.confirm('Are you sure you want to delete this user?')) {
    this.deleteUser(id);
  }
  
}
openFullDescription(event: Event, description: string) {
  event.preventDefault();
  this.dialog.open(DescriptionDialogComponent, {
    data: description
  });
}

getImageUrl(filename: string): string {
  return `http://localhost:8080/api/file/get-image/${filename}`;
}
navigateToAddPage(){
  this.router.navigate(['/ajouterParking'])
}





}
