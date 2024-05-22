import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { icon, Layer, marker } from 'leaflet';
import { Parking } from 'src/app/models/parking';
import { ParkService } from 'src/app/services/parkService/park.service';
import * as L from "leaflet";
import { Location } from '@angular/common';

@Component({
  selector: 'app-parking-list',
  templateUrl: './parking-list.component.html',
  styleUrls: ['./parking-list.component.css']
})
export class ParkingListComponent implements OnInit {
  parkings: Parking[] = [];
  clickedParkingIndex: number | null = null; // Index du parking pour lequel le bouton "Copy" a été cliqué
  markers:Layer[]=[];
  map:any;
  homeCoords={
    lat:33.8869,
    lon:9.5375

  }
  markerIcon={
    icon: L.icon({
      iconSize:[24,41],
      iconAnchor:[10,41],
      popupAnchor:[2,-40],
      iconUrl:"https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
      shadowUrl:"https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png"
    })
  }
  options={
    layers:[
      L.tileLayer("http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",{
        maxZoom:18,
        attribution:""
      })
    ],
    zoom:5,
    center: L.latLng(this.homeCoords.lat,this.homeCoords.lon)
  }
  onMapReady(map:L.Map){
    this.map=map;
  }
  constructor(private parkService: ParkService, private router: Router,private location: Location) {}

  ngOnInit(): void {
    this.location.replaceState('/front/parking');


    this.parkService.displayParking().subscribe(data => {
      this.parkings = data;
      this.parkings.forEach(p=>{
        this.addMarker(p);
      })
    });
  }
  addMarker(parking:Parking){
    const popupInfo=`<b style="color: red;background-color:white">${
      parking.nom+' '+parking.capaciteTotale
    }</b>`
    this.markers.push(marker(
        [parking.latitude,parking.longitude],
        this.markerIcon
      ).bindPopup(popupInfo)
    );
  }
  copy(event:Event,latitude: number, longitude: number): void {
    event.stopPropagation();

    const textCopy = `${latitude},${longitude}`;
    const el = document.createElement('textarea');
    el.value = textCopy;
    document.body.appendChild(el);
    el.select();
    document.execCommand('copy');
    document.body.removeChild(el);
    this.clickedParkingIndex = this.parkings.findIndex(parking => parking.latitude === latitude && parking.longitude === longitude);
    setTimeout(() => {
      this.clickedParkingIndex = null;
    }, 1000); 
  }

  navigateToParkingdetails(id: number): void {
    this.router.navigate(['/front/parking-detail', id]);
  }

  getImageUrl(filename: string): string {
    return `http://localhost:8080/api/file/get-image/${filename}`;
  }

}
