import { Component, OnInit } from '@angular/core';
import { GlobalVariable } from 'src/app/core/static/variables/url/URLImages';

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent implements OnInit {

  isRoleEjec: boolean = false;
  isRoleAlm: boolean = false;
  showSideBar: boolean = false;

  

  constructor() { }

  ngOnInit() {
    if( JSON.parse(localStorage.getItem('currentUser'))){
      let user = JSON.parse(localStorage.getItem('currentUser'));
 
    user.authorities.forEach(element => {
      if(element.authority === 'ROLE_EJECUTIVO'){
        this.isRoleEjec = true;
      }
      if(element.authority === 'ROLE_ALMACENISTA'){
        this.isRoleAlm = true;
      }
    });
    }
    

    
  }

 
}
