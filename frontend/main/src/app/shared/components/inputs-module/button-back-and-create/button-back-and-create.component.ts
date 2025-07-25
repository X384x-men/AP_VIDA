import { Component, OnInit, ChangeDetectionStrategy, Inject } from '@angular/core';
import { ButtonView } from '../../../../core/class/button-componet/button';
import { SubResourceService } from '../../../../core/services/service-crud-operations/sub-resource.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-button-back-and-create',
  template: `
  <mat-action-row>
  <button class="btn btn-success" (click)='create()' [translate]='"button.save"'></button>
  <button  class="btn btn-danger" *appHideShow='btnBack.default'
   appBackButton [translate]='"button.back"'></button>
  </mat-action-row><br/><br/><br/>`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonBackAndCreateComponent extends ButtonView implements OnInit {
  constructor(private _router: Router, private _activatedRoute: ActivatedRoute,
              @Inject('ServiceResource') private _subResourceService: SubResourceService<any>) {
    super(_router, _activatedRoute, _subResourceService);
  }
  ngOnInit() {
  }

}
