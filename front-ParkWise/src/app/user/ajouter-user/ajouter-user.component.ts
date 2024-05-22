import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/UserService/user.service';

@Component({
  selector: 'app-ajouter-user',
  templateUrl: './ajouter-user.component.html',
  styleUrls: ['./ajouter-user.component.css']
})
export class AjouterUserComponent {
  user:User=new User(); 
  users:any;
  edituser:User=new User();
  id: any;
  roles: string[] = ['USER','ADMIN']; 
  constructor(private userService: UserService,private router:Router  ) { }

  public addOrEditeUser(user:User){
    console.log("useruseruser",user);
    
      this.userService.createUser(user).subscribe({
        next: response => this.navigateToUserList(),
        error: error => console.log(error),
        complete: () =>  this.user=new User(),
      });
  }
  private navigateToUserList(){
      this.router.navigate(['/listUsers'])
  }
}
