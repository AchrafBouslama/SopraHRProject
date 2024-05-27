import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlaceParking } from 'src/app/models/PlaceParking';
import { Reservation } from 'src/app/models/reservation';
import { AuthService } from 'src/app/services/AuthService/auth.service';
import { ClaimService } from 'src/app/services/ClaimService/claim-service.service';
import { PlaceParkService } from 'src/app/services/PlaceParkService/place-park.service';
import { ReservationService } from 'src/app/services/ReservationService/reservation.service';
import { UserService } from 'src/app/services/UserService/user.service';

@Component({
  selector: 'app-parking-places',
  templateUrl: './parking-places.component.html',
  styleUrls: ['./parking-places.component.css']
})
export class ParkingPlacesComponent implements OnInit {
  placeParkings: PlaceParking[] = [];
  firstRow: PlaceParking[] = [];
  secondRowLeft: PlaceParking[] = [];
  secondRowRight: PlaceParking[] = [];
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
      });
    }
  }


  loadPlaceParkings(): void {
    this.placeParkingService.getPlaceParkingsByIdBloc(this.id).subscribe(parkingplace => {
      this.placeParkings = parkingplace;
      this.groupPlaceParkings();
      console.log(parkingplace);
    });
  }

  groupPlaceParkings(): void {
    const half = Math.ceil(this.placeParkings.length / 2);
    this.firstRow = this.placeParkings.slice(0, half);
    const secondHalf = this.placeParkings.slice(half, this.placeParkings.length);
    const secondHalfSplit = Math.ceil(secondHalf.length / 2);
    this.secondRowLeft = secondHalf.slice(0, secondHalfSplit);
    this.secondRowRight = secondHalf.slice(secondHalfSplit, secondHalf.length);
  }

  selectPlace(place: PlaceParking): void {
    if (!place.estReservee) {
      this.placeParkings.forEach(p => p.isSelected = false);
      place.isSelected = true;
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
          this.loadPlaceParkings();
        },
        error => {
          console.error("Erreur lors de la réservation: ", error);
        }
      );
    }
  }
    
  drawPath(selectedPlace: PlaceParking): void {
    const canvas = <HTMLCanvasElement>document.getElementById('pathCanvas');
    const context = canvas.getContext('2d');
    
    if (context) {
      context.clearRect(0, 0, canvas.width, canvas.height);
      const door = document.querySelector('.garage-door') as HTMLElement;
      const placeElement = document.querySelector(`.parking-space.selected`) as HTMLElement;
      console.log(door);
      console.log(placeElement)
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
  
        context.strokeStyle = 'blue';
        context.lineWidth = 10;
  
        const drawSegment = (x1: number, y1: number, x2: number, y2: number) => {
          context.beginPath();
          context.moveTo(x1, y1);
          context.lineTo(x2, y2);
          context.stroke();
        };
        const totalFrames = 100; 
        let currentFrame = 0;
  
        const animateLine = () => {
          if (currentFrame <= totalFrames) {
            const progress = currentFrame / totalFrames;
  
            let currentX: number, currentY: number;
  
            if (progress < 0.33) { 
              currentX = startX;
              currentY = startY + progress * 3 * (roadY - startY);
            } else if (progress < 0.66) { 
              currentX = startX + (progress - 0.33) * 3 * (endX - startX);
              currentY = roadY;
            } else { 
              currentX = endX;
              currentY = roadY + (progress - 0.66) * 3 * (endY - roadY);
            }
  
            context.clearRect(0, 0, canvas.width, canvas.height);
            context.beginPath();
            context.moveTo(startX, startY);
            if (progress >= 0.33) context.lineTo(startX, roadY);
            if (progress >= 0.66) context.lineTo(endX, roadY);
            context.lineTo(currentX, currentY);
            context.stroke();
  
            currentFrame++;
            requestAnimationFrame(animateLine);
          }
        };
  
        animateLine();
      }
    }
  }
  
  
  
  
  
  
}
