import { Component, OnInit, Inject, ViewEncapsulation } from '@angular/core';
import { GlobalVariable, UsuarioAcceso } from 'src/app/core/static/variables/url/URLImages';
import { SubResourceService } from 'src/app/core/services/service-crud-operations/sub-resource.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-main-dashboard',
  templateUrl: './main-dashboard.component.html',
  styleUrls: ['./main-dashboard.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MainDashboardComponent implements OnInit {
  apvidaBackground  = GlobalVariable.BACKGROUND_IMG_APVIDA;
  userApp: any;
  userAdmin: any;
  nameUser: any = '';
  isHelp: boolean;

  constructor( @Inject('ServiceResource') private subResourceService: SubResourceService<any>, private _router:Router   ) {
    this.isHelp = false;
  }

  ngOnInit() {
    this.userApp = JSON.parse(localStorage.getItem('currentUser'));
    this.userAdmin =  JSON.parse(localStorage.getItem('currentUserAdmin'));
    this.infoUser(this.userApp.username);
  }

  goToLink(){
    window.open('http://apvida.mx/gem/movimientos.php?RFC=' + this.userApp.username.toUpperCase(), '_blank');
  }

  goSolicitudes = () => {
    this._router.navigateByUrl('/angular/list-solicitudes')
  }

  toggleHelp(): void { this.isHelp = !this.isHelp; }

  infoUser(rfc){
    this.subResourceService.read(UsuarioAcceso.USUARIO_NOMBRE_AP, {user: rfc })
    .subscribe(data=>{
      localStorage.setItem('nameUserAP', JSON.stringify(data.message));
      this.nameUser = data.message;
    }, error=>{
      console.log(error);
    });
  }

}
