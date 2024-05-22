import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthRequest } from 'src/app/models/auth-request';
import { AuthService } from 'src/app/services/AuthService/auth.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  signInButton!: HTMLElement | null;
  container!: HTMLElement | null;
  audioPlayer!: HTMLAudioElement;
  router!: any;
  email:string='';
  password:string='';
  user = {
    firstname:'' ,
    lastname: "",
    email:'',
  }
 

  constructor(private _auth:AuthService,private _router:Router) {}

  ngOnInit(): void {
    
  /*  this.audioPlayer = new Audio('assets/bruit.mp3'); 

  this.audioPlayer.addEventListener('ended', () => {
    setTimeout(() => {
      this.audioPlayer.currentTime = 0; 
      this.audioPlayer.play(); 
    }, 5000); 
  });
  this.audioPlayer.play();
  this.audioPlayer.play();
*/

  this.signInButton = document.getElementById('signIn');
    this.container = document.getElementById('container');
    if (this.signInButton && this.container) {
      this.signInButton.addEventListener('click', () => {
        this.container?.classList.remove('right-panel-active');
      });
    }
    const signUpButton = document.getElementById('signUp');
    if (signUpButton && this.container) {
      signUpButton.addEventListener('click', () => {
        this.container?.classList.add('right-panel-active');
      });
    }
  }
register (){
const userData={
  firstname:this.user.firstname,
  lastname: this.user.lastname,
  email:this.user.email
};

  this._auth.register(userData).subscribe();
  this._router.navigate(['/verification']);

}
login() {
  console.log(this.email);
  console.log(this.password);
  if (this.email && this.password) { 
    const authRequest: AuthRequest = { email: this.email, password: this.password };

    this._auth.login(authRequest).subscribe({
      next: (success) => {
        if (success) {
          const currentRole = this._auth.getCurrentRole();
          if (currentRole === "USER" || currentRole === "MANAGER") {
            this._router.navigate(['/front/parking']);
          } else if (currentRole === "ADMIN") {
            this._router.navigate(['/']);
          }
        } else {
          console.log("login fail");
        }
      },
      error: (error) => {
        console.log('login fail');
      }
    });
  }
}


}
