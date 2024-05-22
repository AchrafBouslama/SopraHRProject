import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/UserService/user.service';

@Component({
  selector: 'app-modifier-utilisateur',
  templateUrl: './modifier-utilisateur.component.html',
  styleUrls: ['./modifier-utilisateur.component.css']
})
export class ModifierUtilisateurComponent implements OnInit {
 
  user:User=new User(); 
  users:any;
  edituser:User=new User();
  id: any;
  roles: string[] = ['USER','ADMIN']; 
  ngOnInit(): void {
      this.route.paramMap.subscribe(params=>{
        this.id=params.get('id');
        if(this.id){
          this.loadUser(this.id);
        }
      })
  }
  constructor(private userService: UserService,private router:Router,private route:ActivatedRoute  ) { }
  loadUser(id:number):void{
    this.userService.getUserId(id).subscribe({
      next:(data)=>{
        this.user=data;
      },
    })
  
  }
  public addOrEditeUser(user:User){
    console.log("useruseruser",user);
    if(this.id){
      this.userService.updateUser(user).subscribe({
        next: response => this.navigateToUserList(),
        error: error => console.log(error),
        complete: () =>  this.user=new User(),
      });
    }
    
  }
  private navigateToUserList(){
    this.router.navigate(['/listUsers'])
}
  
}
