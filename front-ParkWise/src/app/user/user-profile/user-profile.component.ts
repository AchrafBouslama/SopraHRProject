import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/AuthService/auth.service';
import { UserService } from 'src/app/services/UserService/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  constructor(public authService: AuthService, private router: Router,private userService:UserService) {}
  currentUser:any;
  ngOnInit(): void {
    const userId=this.authService.getCurrentUserId();
    this.userService.getUserId(parseInt(userId??'0')).subscribe((data)=>{
      this.currentUser=data;
    })
  }


}
