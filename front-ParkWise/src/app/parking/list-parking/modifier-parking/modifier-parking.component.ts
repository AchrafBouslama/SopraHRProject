import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Parking } from 'src/app/models/parking';
import { FileService } from 'src/app/services/file.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-modifier-parking',
  templateUrl: './modifier-parking.component.html',
  styleUrls: ['./modifier-parking.component.css']
})
export class ModifierParkingComponent implements OnInit {
  parkingForm!: FormGroup;
  imagePreview: string | ArrayBuffer | null = null;
  fileToUpload: File | null = null;
  id!: number;

  constructor(
    private formBuilder: FormBuilder,
    private parkService: ParkService,
    private router: Router,
    private route: ActivatedRoute,
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.loadParking(this.id);
    });

    this.parkingForm = this.formBuilder.group({
      nom: ['', Validators.required],
      adresse: ['', Validators.required],
      description: ['', Validators.required],
      capaciteTotale: ['', [Validators.required, Validators.min(0)]],
      latitude: ['', [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: ['', [Validators.required, Validators.min(-180), Validators.max(180)]]
    });
  }

  loadParking(id: number) {
    this.parkService.getParkingById(id).subscribe(data => {
      this.parkingForm.patchValue(data);
      this.imagePreview = data.image;  // Assuming the API returns a path or a base64 string
    });
  }

  onFileSelected(event: Event): void {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if (fileList) {
      this.fileToUpload = fileList[0];
      const reader = new FileReader();
      reader.onload = () => this.imagePreview = reader.result;
      reader.readAsDataURL(this.fileToUpload);
    }
  }

  onSubmit() {
    if (this.parkingForm.valid) {
      this.uploadFileAndUpdateParking();
    } else {
      console.error('Form is invalid');
    }
  }

  uploadFileAndUpdateParking() {
    if (this.fileToUpload) {
      this.fileService.uploadFile(this.fileToUpload).subscribe(
        uploadResponse => {
          this.updateParking();
        },
        uploadError => {
          console.error('Error uploading file:', uploadError);
        }
      );
    } else {
      this.updateParking();
    }
  }

  updateParking() {
    const updatedParking: Parking = {
      id:this.id,
      ...this.parkingForm.value,
      
      image: this.fileToUpload ? this.fileToUpload.name : this.imagePreview  // Handle cases where the image may not change
    };
    this.parkService.addParking(updatedParking).subscribe(
      response => {
        this.router.navigate(['/listParking']);
      },
      error => {
        console.error('Error updating parking:', error);
      }
    );
  }
}
