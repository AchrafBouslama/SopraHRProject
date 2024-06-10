import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaceParking } from 'src/app/models/PlaceParking';
import { Reservation } from 'src/app/models/reservation';
import { AuthService } from 'src/app/services/AuthService/auth.service';
import { PlaceParkService } from 'src/app/services/PlaceParkService/place-park.service';
import { ReservationService } from 'src/app/services/ReservationService/reservation.service';
import { UserService } from 'src/app/services/UserService/user.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-parking-places',
  templateUrl: './parking-places.component.html',
  styleUrls: ['./parking-places.component.css']
})
export class ParkingPlacesComponent implements OnInit {
  placeParkings: PlaceParking[] = [];
  firstRow: PlaceParking[] = [];
  secondRow: PlaceParking[] = [];
  id: any;
  private staticUserId = 6;
  reservedPlaces: number[] = [];
  currentUser: any;
  hasReservation = false;

  constructor(
    private reservationService: ReservationService,
    private placeParkingService: PlaceParkService,
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    public authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const userId = this.authService.getCurrentUserId();
    this.userService.getUserId(parseInt(userId ?? '0')).subscribe((data) => {
      this.currentUser = data;
      this.checkUserReservation();
    });
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.id = +idParam;
        this.loadPlaceParkings();
      }
    });
  }

  checkUserReservation(): void {
    if (this.currentUser && this.currentUser.iduserr) {
      this.reservationService.getUserReservations(this.currentUser.iduserr).subscribe((reservations) => {
        this.hasReservation = reservations.length > 0;
        if (this.hasReservation) {
          this.disablePlaceSelection();
        }
      });
    }
  }

  disablePlaceSelection(): void {
    this.placeParkings.forEach(place => {
      place.isDisabled = true;
    });
    this.cdr.detectChanges();
  }

  loadPlaceParkings(): void {
    this.placeParkingService.getPlaceParkingsByEtageId(this.id).subscribe(parkingplace => {
      this.placeParkings = parkingplace;
      this.groupPlaceParkings();
      this.cdr.detectChanges(); // Déclenche la détection de changement
      console.log(parkingplace);
    });
  }

  groupPlaceParkings(): void {
    const half = Math.ceil(this.placeParkings.length / 2);
    this.firstRow = this.placeParkings.slice(0, half);
    this.secondRow = this.placeParkings.slice(half);
  }

  selectPlace(place: PlaceParking): void {
    if (!this.hasReservation && !place.estReservee && !place.isDisabled) {
      this.placeParkings.forEach(p => p.isSelected = false);
      place.isSelected = true;
      this.cdr.detectChanges();
      this.drawPath(place);
    }
  }

  countSelectedPlaces(): number {
    return this.placeParkings.filter(place => place.isSelected).length;
  }

  makeReservation(): void {
    const selectedPlace = this.placeParkings.find(place => place.isSelected);
    if (selectedPlace && this.currentUser && this.currentUser.iduserr) {
      if (this.hasReservation) {
        console.error("Erreur: L'utilisateur a déjà une réservation active.");
        return;
      }

      const now = new Date();
      const endOfDay = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59);

      const reservationData: Partial<Reservation> = {
        placeParking: selectedPlace,
        debutReservation: now,
        finReservation: endOfDay,
        estActive: true,
        utilisateur: this.currentUser
      };

      this.reservationService.addReservationToPlaceParking(
        this.currentUser.iduserr,
        selectedPlace.id,
        reservationData as Reservation
      ).subscribe(
        response => {
          console.log("Réservation effectuée pour la place de parking: ", selectedPlace);
          this.reservedPlaces.push(selectedPlace.id);
          this.hasReservation = true; // Mettez à jour l'état de la réservation

          // Charger à nouveau les données des places de parking
          this.loadPlaceParkings();
          // Forcer la mise à jour de la vue
          this.cdr.detectChanges();
          this.clearPath();
          setTimeout(() => {
            const index = this.reservedPlaces.indexOf(selectedPlace.id);
            if (index !== -1) {
              this.reservedPlaces.splice(index, 1);
            }
          }, 5000);
        },
        error => {
          console.error("Erreur lors de la réservation: ", error);
        }
      );
    }
  }
  clearPath():void{
    const canvas = <HTMLCanvasElement>document.getElementById('pathCanvas');
    const context = canvas.getContext('2d');
    if(context){
      context.clearRect(0,0,canvas.width,canvas.height);
    }
    const movingCar=document.getElementById('movingCar') as HTMLElement;
    if(movingCar){
      movingCar.style.display='none';
    }
  }

  drawPath(selectedPlace: PlaceParking): void {
    const canvas = <HTMLCanvasElement>document.getElementById('pathCanvas');
    const context = canvas.getContext('2d');
    const movingCar = document.getElementById('movingCar') as HTMLElement;
    
    if (context && movingCar) {
      context.clearRect(0, 0, canvas.width, canvas.height);
      const door = document.querySelector('.garage-door') as HTMLElement;
      const placeElement = document.querySelector(`.parking-space.selected`) as HTMLElement;
  
      if (door && placeElement) {
        const doorRect = door.getBoundingClientRect();
        const placeRect = placeElement.getBoundingClientRect();
  
        const startX = doorRect.left + doorRect.width / 2;
        const startY = doorRect.top;
  
        const roadRect = document.querySelector('.road')!.getBoundingClientRect();
        const roadY = roadRect.top + roadRect.height / 2;
  
        const verticalRoadRect = document.querySelector('.vertical-road')!.getBoundingClientRect();
        const verticalRoadX = verticalRoadRect.left + verticalRoadRect.width / 2;
  
        const isTopRow = placeElement.parentElement!.previousElementSibling === null;
  
        const endX = placeRect.left + placeRect.width / 2;
        const endY = isTopRow ? placeRect.bottom : placeRect.top;
  
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
  
        context.strokeStyle = 'green';
        context.lineWidth = 6;
  
        const drawSegment = (x1: number, y1: number, x2: number, y2: number) => {
          context.beginPath();
          context.moveTo(x1, y1);
          context.lineTo(x2, y2);
          context.stroke();
        };
  
        context.beginPath();
        context.moveTo(startX, startY);
        context.lineTo(startX, roadY);
        context.lineTo(endX, roadY);
        context.lineTo(endX, endY);
        context.stroke();
  
        const totalFrames = 500;
        let currentFrame = 0;
  
        const animateCar = () => {
          if (currentFrame <= totalFrames) {
            const progress = currentFrame / totalFrames;
  
            let currentX: number, currentY: number;
            let directionClass = '';
  
            if (progress < 0.33) { 
              currentX = startX;
              currentY = startY + progress * 3 * (roadY - startY);
              directionClass = 'car-down'; 
            } else if (progress < 0.66) { 
              currentX = startX + (progress - 0.33) * 3 * (endX - startX);
              currentY = roadY;
              directionClass = 'car-right';
            } else {
              currentX = endX;
              currentY = roadY + (progress - 0.66) * 3 * (endY - roadY);
              directionClass = isTopRow ? 'car-down' : 'car-up';
            }
  
            movingCar.style.left = `${currentX}px`;
            movingCar.style.top = `${currentY}px`;
            movingCar.className = `fas fa-car moving-car ${directionClass}`;
            movingCar.style.display = 'block';
  
            currentFrame++;
            requestAnimationFrame(animateCar);
          }
        };
  
        animateCar(); 
      }
    }
  }

  cancelReservation(): void {
    const userId = this.currentUser.iduserr;
    this.reservationService.cancelUserReservation(userId).subscribe(
      response => {
        console.log("Réservation annulée avec succès.");
        // Mettez à jour l'état de la réservation et les places de parking
        this.hasReservation = false;
        this.placeParkings.forEach(place => {
          place.isDisabled = false;
        });
        this.loadPlaceParkings();
        this.cdr.detectChanges(); // Déclenche la détection de changement
      },
      error => {
        console.error("Erreur lors de l'annulation de la réservation: ", error);
      }
    );
  }
}