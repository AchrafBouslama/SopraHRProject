import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { AuthService } from 'src/app/services/AuthService/auth.service';
import { FileService } from 'src/app/services/file.service';
import { UserService } from 'src/app/services/UserService/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  constructor(public authService: AuthService, private router: Router,private userService:UserService,private formBuilder: FormBuilder,private fileService:FileService) {}
  currentUser:any;
  user:User=new User(); 
  users:any;
  edituser:User=new User();
  id: any;
  userForm!: FormGroup;
  imagePreview: string | ArrayBuffer | null = null;
  uploadProgress: number | null = null;
  fileToUpload: File | null = null; 
  ngOnInit(): void {
    const userId=this.authService.getCurrentUserId();
    this.userService.getUserId(parseInt(userId??'0')).subscribe((data)=>{
      this.currentUser=data;
      this.userForm = this.formBuilder.group({
        role: [this.currentUser.role],
        email: [this.currentUser.email, Validators.required],
        firstname: [this.currentUser.firstname, Validators.required],
        lastname: [this.currentUser.lastname, Validators.required],
        
      });
    })
    
  }
  getImageUrl(filename: string): string {
    return `http://localhost:8080/api/file/get-image/${filename}`;
  }
  public addOrEditeUser(user:User){
    console.log("useruseruser",user);
    if(this.id){
      this.userService.updateUser(user).subscribe({
        next: response => console.log("edited"),
        error: error => console.log(error),
        complete: () =>  this.user=new User(),
      });
    }
    
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
    if (this.userForm.valid && this.fileToUpload) {
      this.uploadFileAndSubmitForm();
    } else if(this.userForm.valid) {
      const userId=this.authService.getCurrentUserId();
    const updatedUser: User = {
      iduserr:userId,
      ...this.userForm.value
    };
    console.log(updatedUser)
    this.userService.updateUser(updatedUser).subscribe(
      response => {
        this.router.navigate(['/front/parking']);
      },
      error => {
        console.error('Error adding Parking:', error);
      }
    );
    }
  }

  private uploadFileAndSubmitForm() {
    this.fileService.uploadFile(this.fileToUpload!).subscribe(
      uploadResponse => {
        this.updateProfile();
      },
      uploadError => {
        console.error('Error uploading file:', uploadError);
      }
    );
  }

  private updateProfile() {
    
    const userId=this.authService.getCurrentUserId();
    const updatedUser: User = {
      iduserr:userId,
      ...this.userForm.value,
      role: this.currentUser.role,
      image: this.fileToUpload!.name
    };
    console.log(updatedUser)
    this.userService.updateUser(updatedUser).subscribe(
      response => {
        this.router.navigate(['/front/parking']);
      },
      error => {
        console.error('Error adding Parking:', error);
      }
    );
  }
}
