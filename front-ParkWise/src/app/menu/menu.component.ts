import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService/auth.service';
import { UserService } from '../services/UserService/user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  constructor(public authService: AuthService, private router: Router,private userService:UserService) {}
  currentUser:any;
  ngOnInit(): void {
    const userId=this.authService.getCurrentUserId();
    this.userService.getUserId(parseInt(userId??'0')).subscribe((data)=>{
      this.currentUser=data;
    })
  }
  getImageUrl(filename: string): string {
    return `http://localhost:8080/api/file/get-image/${filename}`;
  }


}
