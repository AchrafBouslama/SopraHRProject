import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Parking } from 'src/app/models/parking';
import { FileService } from 'src/app/services/file.service';
import { ParkService } from 'src/app/services/parkService/park.service';

@Component({
  selector: 'app-ajouter-parking',
  templateUrl: './ajouter-parking.component.html',
  styleUrls: ['./ajouter-parking.component.css']
})
export class AjouterParkingComponent implements OnInit {
  parkingForm!: FormGroup;
  imagePreview: string | ArrayBuffer | null = null;
  uploadProgress: number | null = null;
  fileToUpload: File | null = null; 

  constructor(
    private formBuilder: FormBuilder,
    private parkingService: ParkService,
    private router: Router,
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    this.parkingForm = this.formBuilder.group({
      nom: ['', Validators.required],
      adresse: ['', Validators.required],
      description: ['', Validators.required],
      capaciteTotale: ['', [Validators.required, Validators.min(0)]],
      latitude: ['', [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: ['', [Validators.required, Validators.min(-180), Validators.max(180)]]
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
    if (this.parkingForm.valid && this.fileToUpload) {
      this.uploadFileAndSubmitForm();
    } else {
      console.error('Form is invalid or file not selected');
    }
  }

  private uploadFileAndSubmitForm() {
    this.fileService.uploadFile(this.fileToUpload!).subscribe(
      uploadResponse => {
        this.addParking();
      },
      uploadError => {
        console.error('Error uploading file:', uploadError);
      }
    );
  }

  private addParking() {
    console.log(this.parkingForm.value)
    const newParking: Parking = {
      ...this.parkingForm.value,
      image: this.fileToUpload!.name
    };
    this.parkingService.addParking(newParking).subscribe(
      response => {
        this.router.navigate(['/admin/listParking']);
      },
      error => {
        console.error('Error adding Parking:', error);
      }
    );
  }
}
