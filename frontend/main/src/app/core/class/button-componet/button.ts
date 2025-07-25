import { Input, ɵConsole, Directive } from '@angular/core';
import { Navigated } from '../http-resource/http-resource';
import { ActivatedRoute, Router } from '@angular/router';
import { RoutingUtilities } from '../../Util/routing/routing-utilities';
import { SubResourceService } from '../../services/service-crud-operations/sub-resource.service';
import { Smartwfm } from '../../Util/smartwfm/smartwfm';
import swal from 'sweetalert2';
export interface ButtonBackProps {
  default: boolean;
  url?: Navigated;
}

@Directive()
export abstract class ButtonView {
  @Input() obj: any;

  @Input() url: string;

  @Input() isTest = false;
  @Input() action: string = 'C';
  @Input() btnBack: ButtonBackProps = {
    default: true
  };
  constructor(private router?: Router, private activatedRoute?: ActivatedRoute,
    private subResourceService?: SubResourceService<any>) { }
  go() {
    if (!this.btnBack.default && this.subResourceService
      && this.router && this.activatedRoute) {
      if (this.btnBack && this.btnBack.url && this.btnBack.url.params) {
        RoutingUtilities.goToComponent(this.router, this.activatedRoute, this.btnBack.url.nextUrl, this.btnBack.url.params);
      } else {
        RoutingUtilities.goToComponentNoParams(this.router, this.activatedRoute, this.btnBack.url.nextUrl);
      }
    }
  }
  create() {
    console.log("BOTON****", this.obj);
    console.log("OBJETO-----", this.obj);
    console.log("URL+++++++", this.url);
    
    if (!this.isTest) {
      if (this.obj && this.url) {
        switch (this.action) {
          case 'C':
            this.subResourceService.create(this.obj, this.url).subscribe(response => {
              swal('Exitoso', Smartwfm.getSuccessMessage(response), 'success');
            }, error=>{
              swal('Alerta', error, 'warning');
            });
            break;
          case 'U':
            console.log("update: ",this.obj);
            this.subResourceService.update(this.obj, this.url).subscribe(response => {
              swal('Exitoso', Smartwfm.getSuccessMessage(response), 'success');
            });
            break;
          default:
            break;
        }
      }
    } else {
      swal('Exitoso', 'Creado exitosamente', 'success');
    }
  }

}

