import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/AuthService/auth.service';

@Component({
  selector: 'app-activateaccount',
  templateUrl: './activateaccount.component.html',
  styleUrls: ['./activateaccount.component.css']
})
export class ActivateaccountComponent {
  message:string='';
  public oneTimePassword={
    data1:"",
    data2:"",
    data3:"",
    data4:""
  }
  isOkey:boolean=true;
  code!:number;
  constructor(private authService:AuthService,private router:Router){}
  triggerBackSpace(data:string,box:string){
      let StringfyData:string|null;
      if(data){
        StringfyData=data.toString();
      }else{
        StringfyData=null;
      }
      if(box=='digit-4' && StringfyData==null){
        document.getElementById('digit-3')?.focus();
      }else if(box=='digit-3' && StringfyData==null){
        document.getElementById('digit-2')?.focus();
      }else if(box=='digit-2' && StringfyData==null){
        document.getElementById('digit-1')?.focus();
      }
  }
  ValueChanged(data:string,box:string){
    if(box=='digit-1' && data.length>0){
      document.getElementById('digit-2')?.focus();
    }else if(box=='digit-2' && data.length>0){
      document.getElementById('digit-3')?.focus();
    }else if(box=='digit-3' && data.length>0){
      document.getElementById('digit-4')?.focus();
    }else{
      return;
    }

  }
  verifyUser(){
    const userId=localStorage.getItem("id");
    const verificationCode=`${this.oneTimePassword.data1}${this.oneTimePassword.data2}${this.oneTimePassword.data3}${this.oneTimePassword.data4}`
    if(userId){
      this.authService.verifyUser(parseInt(userId),parseInt(verificationCode)).subscribe({
        next:(response)=>{
          this.router.navigate(['/signup']);
        },
        error: (error)=>{
          console.error("Verification failed",error);
        }
      })
    }
    else{
      console.error("User id is missing");
    }

  }
}
