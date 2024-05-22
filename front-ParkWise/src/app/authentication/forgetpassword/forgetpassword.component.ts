import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/AuthService/auth.service';

@Component({
  selector: 'app-forgetpassword',
  templateUrl: './forgetpassword.component.html',
  styleUrls: ['./forgetpassword.component.css']
})
export class ForgetpasswordComponent {
  forgotForm!:FormGroup;
  constructor(private fb:FormBuilder,private authService:AuthService){
    this.forgotForm=this.fb.group({
      email:['',[Validators.required,Validators.email]]
    });
  }
  onSubmit():void{
    if(this.forgotForm.valid){
      this.authService.forgetPassword(this.forgotForm.value.email).subscribe({
        next:()=>alert('Reset password link sent to your email'),
        error: (err)=>alert('Error sending reset link:'+(err.error?.message))
      })
    }
  }
}
