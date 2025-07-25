import { LoaderComponentService } from '../../services/loader-componet/loader-component.service';
import { AddComponentDirective } from 'src/app/shared/Directives/add-component/add-component.directive';
import { ViewChild, Output, EventEmitter, Directive } from '@angular/core';
import { PostItem } from './post-item';
import { Type } from '@angular/core';
import { RoutingUtilities } from '../../Util/routing/routing-utilities';
import { ActivatedRoute, Router } from '@angular/router';
import { LoaderComponet } from '../../interface/loader-component/loader-componet';


@Directive()
export abstract class LoaderComponent {
  @ViewChild(AddComponentDirective) protected adHost: AddComponentDirective;
  @Output() protected event = new EventEmitter<any>();
  protected urlRemove: string;
  constructor(private router: Router, private activatedRoute: ActivatedRoute, private loader: LoaderComponentService) {
  }
  load(type: Type<any>, data?: any, componentParams?: any): LoaderComponet {
    return this.loader.loadComponent(this.adHost, new PostItem(type, componentParams), data);
  }
  remove() {
    RoutingUtilities.goToComponentNoParams(this.router, this.activatedRoute, this.urlRemove);
  }
  removeComponent(type: Type<any>, data: any, componentParams: any, index: number): LoaderComponet {
    return this.loader.removeAndReloadComponent(this.adHost, new PostItem(type, componentParams), index, data);
  }
  emit() {
    // this.loader.getOutPut();
  }
}
