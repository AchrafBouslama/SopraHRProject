import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/AuthService/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {
  oldPassword = '';
  newPassword = '';
  confirmPassword = '';

  constructor(private authService: AuthService,private router:Router) {}

  resetPassword() {
    if (this.newPassword !== this.confirmPassword) {
      alert('New Password and Confirm New Password do not match.');
      return;
    }
  
    const userIdString = this.authService.getCurrentUserId();
    if (!userIdString) {
      alert('No user ID found. Please log in again.');
      return;
    }
  
    const userId = parseInt(userIdString);
    if (isNaN(userId)) {
      alert('Invalid user ID. Please log in again.');
      return;
    }
  
    if (!this.oldPassword || !this.newPassword || !this.confirmPassword) {
      alert('All fields are required.');
      return;
    }
  
    this.authService.changePassword(userId, this.oldPassword, this.newPassword, this.confirmPassword).subscribe({
      next: (response) => {
        
        this.authService.logout();
        this.router.navigate(['/signup']);
      },
      error: (error) => {
        const errorMessage = error.error.message || JSON.stringify(error.error);
        alert('Error changing password: ' + errorMessage);
      }
    });
  }
  
}