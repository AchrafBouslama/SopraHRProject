import { Component, OnInit } from '@angular/core';
import { User } from '../models/user';
import { UserService } from '../services/UserService/user.service';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';


declare var $: any; // Déclarer jQuery si nécessaire

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  user:User=new User(); 
  users:any;
  edituser:User=new User();
  id: any;
  roles: string[] = ['USER','ADMIN']; 



  constructor(private userService: UserService ,private router:Router ) { }

  ngOnInit(): void {
    this.reloadData();
    
  }

  reloadData() {
    this.userService.getUser().subscribe(data => {
      if (data) {
        this.users=data
        console.log(this.users);
      }
    });
  }
  public editeUser(iduser:number){
    this.router.navigate(['/modifierUser/'+iduser]);
  }
  public addOrEditeUser(user:User){
    console.log("useruseruser",user);
    if(!this.id){
      this.userService.createUser(user).subscribe({
        next: response => this.reloadData(),
        error: error => console.log(error),
        complete: () =>  this.user=new User(),
      });
    }else{
      this.user.iduserr=this.id;
      this.userService.updateUser(user).subscribe({
        next: response => this.reloadData(),
        error: error => console.log(error),
        complete: () => this.user=new User(),
      });
      this.id=null;
    }
    
  }
  public deleteUser(id: number): void {
    console.log(id,"dddddddddddddddddddddddddddd");
    
    this.userService.deleteUser(id).subscribe({
      next: response => this.users = this.users?.filter((s:User) => s.iduserr != id),
      error: error => console.log(error),
      complete: () => console.log('Delete request completed.')
    });
  }
  public confirmDeleteUser(id: number): void {
  if (window.confirm('Are you sure you want to delete this user?')) {
    this.deleteUser(id);
  }
  
}
navigateToAddPage(){
  this.router.navigate(['/ajouterUser'])
}




}
