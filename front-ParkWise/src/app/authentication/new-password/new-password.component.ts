import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/AuthService/auth.service';

@Component({
  selector: 'app-new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.css']
})
export class NewPasswordComponent implements OnInit{
  newPasswordForm:FormGroup;
  constructor(private fb: FormBuilder,private authService:AuthService,private route:ActivatedRoute,private router:Router){
    this.newPasswordForm=this.fb.group({
      newPassword:['',[Validators.required,Validators.minLength(6)]],
      confirmNewPassword:['',Validators.required],
    },{
      validator:this.mustMatch('newPassword','confirmNewPassword')
    
    });
  }
  ngOnInit(): void {
    const token =this.route.snapshot.paramMap.get('token');
    this.newPasswordForm.addControl('token',this.fb.control(token,Validators.required))
  }
  onSubmit():void{
    console.log(this.newPasswordForm.value)
    if (this.newPasswordForm.valid){
      this.authService.setNewPassword(
        this.newPasswordForm.get('token')?.value,
        this.newPasswordForm.get('newPassword')?.value
    ).subscribe({
      next: ()=>this.router.navigate(['/signup']),
      error: (err)=>alert('Failed to change password:'+err.error?.message)
    })
    }
  }
  private mustMatch(controlName:string,matchingControllName:string){
    return (formGroup:FormGroup)=>{
      const control=formGroup.controls[controlName];
      const matchingControl=formGroup.controls[matchingControllName];
      if(matchingControl.errors && !matchingControl.errors['mustMatch']){
        return;
      }
      if(control.value !== matchingControl.value){
        matchingControl.setErrors({mustMatch:true});
      }else{
        matchingControl.setErrors(null);
      }
    }
  }
}
